/**
 * Created by inori on 2018/4/15.
 */
blog.controller('articleController', function($rootScope, $scope, $http, $state, $stateParams) {
    $scope.category = {};
    $scope.categoryAlias = $stateParams.alias;
    $scope.lastArticles = $rootScope.lastArticles;
    if(isBlank($scope.categoryAlias)) {
        $scope.category.name = "HOME";
        $scope.category.id = null;
    }else{
        $http.get($rootScope.baseUrl + "/api/0/article/category/" + $scope.categoryAlias).success(function (result) {
            if (result.status == 0) {
                $scope.category = result.data;
            }else {
                $state.go("404");
            }
        });
    }


    $scope.list = function () {
        if($scope.category.id === undefined) {
            return;
        }
        var param = {
            "pageNum": $scope.articleConfig.currentPage,
            "pageSize": $scope.articleConfig.itemsPerPage,
            "status": 1,
            "categoryId": $scope.category.id
        };
        $http.post($rootScope.baseUrl + "/api/0/home/article/list",JSON.stringify(param)).success(function (result) {
            // App.unblockUI('#sample_1');
            if (result.status == 0) {
                $scope.articles = result.data.data;
                $scope.articleConfig.totalItems = result.data.total;
            }
        });
    };

    $scope.articleConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 8,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            $scope.list();
            // var param = {
            //     "pageNum": this.currentPage,
            //     "pageSize": this.itemsPerPage,
            //     "status": 1
            // };
            // $http.post($rootScope.baseUrl + "/api/0/home/article/list",JSON.stringify(param)).success(function (result) {
            //     $scope.articles = result.data.data;
            //     $scope.articleConfig.totalItems = result.data.total;
            // });
        }
    };

    /**
     * 观察分类是否改变
     */
    $scope.$watch(function () {
        return $scope.category.id;
    }, function(newValue,oldValue){
        if (newValue !== null && newValue !== undefined) {
            $scope.articleConfig.onChange();
        }
    },true);

    $scope.$watch(function(){
        return $rootScope.lastArticles;
    }, function (newValue, oldValue) {
        console.log(newValue);
        $scope.lastArticles = newValue;
    }, true);

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