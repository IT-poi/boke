blog.controller('contactController', function($rootScope, $scope, $http, $state, $stateParams) {
    $scope.lastArticles = $rootScope.lastArticles;
    $scope.website = localStorage.website == undefined ? "" : localStorage.website;
    $scope.name = localStorage.userName;
    $scope.email = localStorage.email;

    $scope.contactList = [{url:"www.baidu.com",name:"张山", floor:1, content:"你好啊，我是回复"},
        {url:"www.baidu.com",name:"张山", floor:1, content:"你好啊，我是评论"}];


    $scope.contactConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 5,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            var param = {
                "pageNum": this.currentPage,
                "pageSize": this.itemsPerPage
            };
            $http.post($rootScope.baseUrl + "/api/0/contact/list", JSON.stringify(param)).success(function (result) {
                $scope.contactList = result.data.data;
                $scope.contactConfig.totalItems = result.data.total;
            });
        }
    };

    /**
     * 发表留言
     */
    $scope.submit = function () {
        if ($scope.name && $scope.email && $scope.subject && $scope.message) {
            localStorage.userName = $scope.name;
            localStorage.email = $scope.email;
            localStorage.website = $scope.website;
            var param = {
                subject:$scope.subject,
                content:$scope.message,
                userId:$rootScope.userId,
                name:$scope.name,
                email:$scope.email
            };
            $http.post($rootScope.baseUrl + "/api/0/contact/add", JSON.stringify(param)).success(function (response) {
                if(response.status == 0){
                    UIToastr.success("发表留言成功！");
                    $scope.subject = "";
                    $scope.message = "";
                    $scope.contactConfig.onChange();
                }else {
                    UIToastr.error("系统异常，请稍后再试！", "留言失败");
                }
            });
        }
    };

    $scope.$watch(function(){
        return $rootScope.lastArticles;
    }, function (newValue, oldValue) {
        console.log(newValue);
        $scope.lastArticles = newValue;
    }, true);
});

