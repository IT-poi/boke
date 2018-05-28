/**
 * Created by inori on 2018/4/15.
 */
blog.controller('searchController', function($rootScope, $scope, $http, $state, $stateParams) {
    $scope.category = {};
    $scope.keywords = $stateParams.keywords;
    $scope.lastArticles = $rootScope.lastArticles;
    $scope.category.name = $scope.keywords;

    $scope.list = function () {
        var param = {
            "pageNum": $scope.articleConfig.currentPage,
            "pageSize": $scope.articleConfig.itemsPerPage,
            "keywords": $scope.keywords
        };
        $http.post($rootScope.baseUrl + "/api/0/home/article/elasticsearch",JSON.stringify(param)).success(function (result) {
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


    $scope.$watch(function(){
        return $rootScope.lastArticles;
    }, function (newValue, oldValue) {
        console.log(newValue);
        $scope.lastArticles = newValue;
    }, true);

});