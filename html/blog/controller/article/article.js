blog.controller('articleController', function($rootScope, $scope, $http, $state) {

    // UIToastr.success("查询成功！");
    // UIToastr.error("查询成功！");
    // UIToastr.info("查询成功！");
    // UIToastr.warning("查询成功！");

    $scope.categoryDetail = function (alias) {
        $state.go("category.detail",{"readonly":true, "alias":alias});
    };

    $scope.list = function (status) {
        var param = {
            "pageNum": $scope.articleConfig.currentPage,
            "pageSize": $scope.articleConfig.itemsPerPage,
            "status": status
        };
        $http.post($rootScope.baseUrl + "/api/1/manage/article/list",JSON.stringify(param)).success(function (result) {
            // App.unblockUI('#sample_1');
            $scope.articles = result.data.data;
            $scope.articleConfig.totalItems = result.data.total;
        });
    };

    $scope.delete = function (articleId, status) {
        if (status == -1) { //回收站的直接删除
            $http.post($rootScope.baseUrl + "/api/1/manage/article/delete/"+articleId).success(function (result) {
                if (result.status == 0) {
                    $scope.articleConfig.onChange();
                }
            });
        }else { //其他地方的放入回收站
            $http.post($rootScope.baseUrl + "/api/1/manage/article/recycle/"+articleId).success(function (result) {
                if (result.status == 0) {
                    $scope.articleConfig.onChange();
                }
            });
        }
    };

    //恢复文章为草稿
    $scope.recover = function (articleId) {
        $http.post($rootScope.baseUrl + "/api/1/manage/article/recover/"+articleId).success(function (result) {
            if (result.status == 0) {
                $scope.articleConfig.onChange();
            }
        });
    };

    $scope.articleConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 10,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            App.blockUI({animate: true, target: '#table'});
            console.log(this.currentPage);
            console.log(this);
            var param = {
                "pageNum": this.currentPage,
                "pageSize": this.itemsPerPage
            };
            $http.post($rootScope.baseUrl + "/api/1/manage/article/list",JSON.stringify(param)).success(function (result) {
                App.unblockUI('#table');
                $scope.articles = result.data.data;
                $scope.articleConfig.totalItems = result.data.total;
            });
        }
    };

});
blog.filter('articleStatus', function() { //可以注入依赖
    return function(text) {
        switch (text) {
            case 0:
                return "草稿";
            case 1:
                return "已发布";
            case 2:
                return "私密";
            default :
                return "回收站";
        }
    }
});