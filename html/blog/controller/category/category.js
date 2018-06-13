blog.controller('categoryController', function($rootScope, $scope, $http, $state) {
    $scope.nCategory = {};
    $scope.nCategory.parentId = "0";
    init();

    /**
     * 添加分类
     */
    $scope.add = function () {
        console.log($rootScope.userInfo);
        $http.post($rootScope.baseUrl + "/api/1/article/category/add",JSON.stringify($scope.nCategory)).success(function (result) {
            if(result.status == 0) {
                init();
                UIToastr.success("添加成功");
            }
        });
    };

    /**
     * 查看分类详情
     * @param id
     */
    $scope.detail = function (alias) {
        $state.go("category.detail",{"readonly":true, "alias":alias});
    };

    /**
     * 删除分类
     *
     * @param categoryId
     */
    $scope.delete = function (categoryId) {
        $http.post($rootScope.baseUrl + "/api/1/article/category/delete/"+categoryId).success(function (result) {
            if (result.status == 0) {
                init();
            }
        });
    };

    function init () {
        $http.post($rootScope.baseUrl + "/api/1/article/category/list").success(function (result) {
            console.log(result);
            $scope.categorys = result.data;
        });
    }
    // $scope.categoryConfig = {
    //     align: "right",
    //     currentPage: 1,
    //     itemsPerPage: 10,
    //     pagesLength: 9,
    //     search: true,
    //     showDetail:false,
    //     onChange: function() {
    //         var param = {
    //             "pageNum": this.currentPage,
    //             "pageSize": this.itemsPerPage
    //         };
    //         $http.post($rootScope.baseUrl + "/api/1/article/category/list").success(function (result) {
    //             $scope.categorys = result.data.data;
    //             $scope.categoryConfig.totalItems = result.data.total;
    //         });
    //     }
    // };
});