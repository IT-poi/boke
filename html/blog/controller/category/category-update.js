blog.controller('categoryDetailController', function($rootScope, $scope, $http, $state, $stateParams) {
    $scope.categoryAlias = $stateParams.alias;
    $scope.readonly = $stateParams.readonly;
    $scope.category = {};
    var url = $rootScope.baseUrl + "/api/0/article/category/"+$scope.categoryAlias;
    $http.get(url).success(function (result) {
        if (result.status == 0) {
            $scope.category = result.data;
        }
    });


    $scope.update = function () {
        var url = "/api/1/article/category/update";
        $http.post($rootScope.baseUrl + url,JSON.stringify($scope.category)).success(function (result) {
            if(result.status == 0) {
                UIToastr.success("更新成功！");
            }
        });
    };


    $scope.list = function (status) {
        var param = {
            "pageNum": $scope.articleConfig.currentPage,
            "pageSize": $scope.articleConfig.itemsPerPage,
            "status": status,
            "alias": $scope.categoryAlias
        };
        $http.post($rootScope.baseUrl + "/api/1/manage/article/list",JSON.stringify(param)).success(function (result) {
            // App.unblockUI('#sample_1');
            $scope.articles = result.data.data;
            $scope.articleConfig.totalItems = result.data.total;
        });
    };

    $scope.articleConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 5,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            var param = {
                "pageNum": this.currentPage,
                "pageSize": this.itemsPerPage,
                "alias": $scope.categoryAlias

            };
            $http.post($rootScope.baseUrl + "/api/1/manage/article/list",JSON.stringify(param)).success(function (result) {
                $scope.articles = result.data.data;
                $scope.articleConfig.totalItems = result.data.total;
            });
        }
    };
});