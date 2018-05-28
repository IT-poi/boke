var blog = angular.module('blog', ['ui.router', 'ngSanitize', 'ui.bootstrap']);
blog.controller('appController', function($rootScope, $scope, $http, $state) {
    $rootScope.baseUrl = "http://193.112.112.136:8080/boke-core";
    $http.get($rootScope.baseUrl + "/api/1/permission/admin/info").success(function (response) {
        if(response.status == 0){
            $scope.userInfo = response.data;
            $rootScope.userInfo = response.data;
        }
    });
    $scope.logout = function () {
        $http.post($rootScope.baseUrl + "/api/1/permission/admin/logout").success(function (response) {
            if (response.status == 0) {
                window.location.href = "login_2.html";
            }
        });
    };
    $scope.updateUser = function () {
        if ($scope.userInfo.userPwdNew !== $scope.userInfo.userPwdNewSure) {
            UIToastr.error("请确认两次密码不一样！");
            return;
        }
        $http.post($rootScope.baseUrl + "/api/1/permission/admin/update", JSON.stringify($scope.userInfo)).success(function (response) {
            if (response.status == 0) {
                UIToastr.success("修改成功");
                $scope.userInfo.userPwdOld = null;
                $scope.userInfo.userPwdNew = null;
                $scope.userInfo.userPwdNewSure = null;
            }
        });
    };
});