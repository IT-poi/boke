/**
 * 分页插件封装s-pagination.js
 * @date 2016-05-06
 * @author Peter
 */

blog.directive('sPagination',[function(){//自定义指令
    return {
        restrict: 'E',//仅限元素名调用
        template: '<div ng-class="page-list-{{conf.align}}" style="text-align: right">' +
        '<ul class="pagination" style="margin: 0px" ng-show="conf.totalItems > 0">' +
        '<li ng-class="{disabled: conf.currentPage == 1}" ng-click="prevPage()"><span>上一页</span></li>' +
        '<li ng-repeat="item in pageList track by $index" ng-class="{active: item == conf.currentPage, separate: item == \'...\'}" ' +
        'ng-click="changeCurrentPage(item)">' +
        '<span>{{ item }}</span>' +
        '</li>' +
        '<li ng-class="{disabled: conf.currentPage == conf.numberOfPages}" ng-click="nextPage()"><span>下一页</span></li>' +
        '<li ng-show="conf.showDetail">'+
        '<span style="margin-left: 10px; padding-top: 4px; padding-bottom: 4px">当前&nbsp;<input type="text" ng-model="jumpPageNum" maxlength="6" id="gotoval" style="width:40px;padding-left:10px;"  ng-keyup="jumpToPage($event)"/>&nbsp;页 &nbsp;&nbsp;' +
        '每页&nbsp;<select ng-model="conf.itemsPerPage" ng-options="option for option in conf.perPageOptions "></select>&nbsp;条&nbsp;&nbsp;' +
        '共{{ conf.totalItems }}条</span>' +
        '</li>' +
        '</ul>' +
        '<div class="no-items" ng-show="conf.totalItems <= 0">没有数据...</div>' +
        '</div>',
        replace: true,
        scope: {
            conf: '='//双向绑定数据
        },
        link: function(scope, element, attrs){
            // 变更当前页
            scope.changeCurrentPage = function(item) {
                if(item == '...'){
                    return;
                }else{
                    scope.conf.currentPage = item;
                }
            };
            
            // 定义分页的长度必须为奇数 (default:5)
            scope.conf.pagesLength = parseInt(scope.conf.pagesLength) ? parseInt(scope.conf.pagesLength) : 5 ;
            if(scope.conf.pagesLength % 2 === 0){
                // 如果不是奇数的时候处理一下
                scope.conf.pagesLength = scope.conf.pagesLength -1;
            }

            // conf.erPageOptions
            if(!scope.conf.perPageOptions){
                scope.conf.perPageOptions = [5, 10, 15, 20, 30, 50];
            }
            var w = $(".content-wrapper").width();
            if(w>768){
            scope.showPageFlag=true;
            }else{
              scope.showPageFlag=false;
            }
            // pageList数组
            function getPagination(newValue, oldValue) {
                //新增属性search   用于附加搜索条件改变时触发
                if(newValue[1] != oldValue[1] || newValue[2] != oldValue[2]) {
                    scope.conf.search = true;
                }
                // conf.currentPage
                scope.conf.currentPage = parseInt(scope.conf.currentPage) ? parseInt(scope.conf.currentPage) : 1;

                // conf.totalItems
                scope.conf.totalItems = parseInt(scope.conf.totalItems) ? parseInt(scope.conf.totalItems) : 0;
                // conf.itemsPerPage (default:15)
                scope.conf.itemsPerPage = parseInt(scope.conf.itemsPerPage) ? parseInt(scope.conf.itemsPerPage) : 15;

                // numberOfPages
                scope.conf.numberOfPages = Math.ceil(scope.conf.totalItems/scope.conf.itemsPerPage);

                // judge currentPage > scope.numberOfPages
                if(scope.conf.currentPage < 1){
                    scope.conf.currentPage = 1;
                }

                // 如果分页总数>0，并且当前页大于分页总数
                if(scope.conf.numberOfPages > 0 && scope.conf.currentPage > scope.conf.numberOfPages){
                    scope.conf.currentPage = scope.conf.numberOfPages;
                }

                // jumpPageNum
                scope.jumpPageNum = scope.conf.currentPage;

                // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                var perPageOptionsLength = scope.conf.perPageOptions.length;
                // 定义状态
                var perPageOptionsStatus;
                for(var i = 0; i < perPageOptionsLength; i++){
                    if(scope.conf.perPageOptions[i] == scope.conf.itemsPerPage){
                        perPageOptionsStatus = true;
                    }
                }
                // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                if(!perPageOptionsStatus){
                    scope.conf.perPageOptions.push(scope.conf.itemsPerPage);
                }

                // 对选项进行sort
                scope.conf.perPageOptions.sort(function(a, b){return a-b});

                scope.pageList = [];
                if(scope.conf.numberOfPages <= scope.conf.pagesLength){
                    // 判断总页数如果小于等于分页的长度，若小于则直接显示
                    for(i =1; i <= scope.conf.numberOfPages; i++){
                        scope.pageList.push(i);
                    }
                }else{
                    // 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）
                    // 计算中心偏移量
                    var offset = (scope.conf.pagesLength - 1)/2;
                    if(scope.conf.currentPage <= offset){
                        // 左边没有...
                        for(i =1; i <= offset +1; i++){
                            scope.pageList.push(i);
                        }
                        scope.pageList.push('...');
                        scope.pageList.push(scope.conf.numberOfPages);
                    }else if(scope.conf.currentPage > scope.conf.numberOfPages - offset){
                        scope.pageList.push(1);
                        scope.pageList.push('...');
                        for(i = offset + 1; i >= 1; i--){
                            scope.pageList.push(scope.conf.numberOfPages - i);
                        }
                        scope.pageList.push(scope.conf.numberOfPages);
                    }else{
                        // 最后一种情况，两边都有...
                        scope.pageList.push(1);
                        scope.pageList.push('...');

                        for(i = Math.ceil(offset/2) ; i >= 1; i--){
                            scope.pageList.push(scope.conf.currentPage - i);
                        }
                        scope.pageList.push(scope.conf.currentPage);
                        for(i = 1; i <= offset/2; i++){
                            scope.pageList.push(scope.conf.currentPage + i);
                        }

                        scope.pageList.push('...');
                        scope.pageList.push(scope.conf.numberOfPages);
                    }
                }
              
                if(scope.conf.onChange){

                    //请求数据
                    if(scope.conf.search) {
                        scope.conf.onChange();
                        scope.conf.search = false;
                    }

                }
                scope.$parent.conf = scope.conf;
            }

            // prevPage
            scope.prevPage = function(){
                if(scope.conf.currentPage > 1){
                    scope.conf.currentPage -= 1;
                }
            };
            // nextPage
            scope.nextPage = function(){
                if(scope.conf.currentPage < scope.conf.numberOfPages){
                    scope.conf.currentPage += 1;
                }
            };

            // 跳转页
            scope.jumpToPage = function(){
    
                scope.jumpPageNum = (scope.jumpPageNum).toString().replace(/[^0-9]/g,'');
                if(scope.jumpPageNum !== ''){
                    scope.conf.currentPage = scope.jumpPageNum;
                }
            };

            scope.$watch(function() {
                if(!scope.conf.totalItems) {
                    scope.conf.totalItems = 0;
                }
                if(angular.isUndefined(scope.conf.search)) {
                    scope.conf.search = false;
                }

                var newValue = [scope.conf.totalItems, scope.conf.currentPage, scope.conf.itemsPerPage, scope.conf.search];
                return newValue;
            }, getPagination, true);
        }
    };
}]);