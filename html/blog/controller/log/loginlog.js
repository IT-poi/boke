blog.controller('loginlogController', function($rootScope, $scope, $http) {

    var checkall=document.getElementsByName("checkbox[]");
    //全选
    $scope.selectAll = function(){
        console.log("111");
        for(var $i=0;$i<checkall.length;$i++){
            checkall[$i].checked=true;
        }
    };

    $scope.loginlogConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 10,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            App.blockUI({animate: true, target: '#table'});
            var param = {
                "pageNum": this.currentPage,
                "pageSize": this.itemsPerPage
            };
            $http.post($rootScope.baseUrl + "/api/1/user/login/list",JSON.stringify(param)).success(function (result) {
                App.unblockUI('#table');
                $scope.loginlogs = result.data.data;
                $scope.loginlogConfig.totalItems = result.data.total;
            });
        }
    };

});