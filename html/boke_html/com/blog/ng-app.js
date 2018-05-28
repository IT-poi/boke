var blog = angular.module('blog', ['ui.router', 'ngSanitize', 'ui.bootstrap']);
blog.controller('appController', function($rootScope, $scope, $location, $http, $state) {
    $rootScope.baseUrl = "http://193.112.112.136:8080/boke-core";
    $rootScope.userId = 1;
    $rootScope.userName = "admin";
    $rootScope.lastArticles = [];
    $http.get($rootScope.baseUrl + "/api/0/article/category/"+  $rootScope.userName+"/list/0").success(function (response) {
        if(response.status == 0){
            $scope.technologys = response.data;
        }
    });
    $http.get($rootScope.baseUrl + "/api/0/article/category/"+  $rootScope.userName+"/list/1").success(function (response) {
        if(response.status == 0){
            $scope.lifes = response.data;
        }
    });
    $http.get($rootScope.baseUrl + "/api/0/admin/"+  $rootScope.userName+"/info").success(function (response) {
        if(response.status == 0){
            $scope.user = response.data;
        }
    });

    $scope.search = function (e) {
        var keycode = window.event ? e.keyCode : e.which;//获取按键编码
        if (keycode == 13) {
            $state.go("search",{"keywords":$scope.keywords});
        }
    };


    /**
     * 最近发布的5篇文章
     */
    $scope.initLastArticles = function () {
        var param = {
            "pageNum": 1,
            "pageSize": 5,
            "status": 1
        };
        $http.post($rootScope.baseUrl + "/api/0/home/article/list",JSON.stringify(param)).success(function (result) {
            if (result.status == 0) {
                $rootScope.lastArticles = result.data.data;
            }
        });
    };
    $scope.initLastArticles(); //最新发布的文章
});