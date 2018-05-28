blog.controller('articleDetailController', function($rootScope, $scope, $http, $state, $stateParams) {
    $scope.articleId = $stateParams.id;
    $scope.lastArticles = $rootScope.lastArticles;
    $scope.article = {};
    $scope.replayId = null;

    $scope.website = localStorage.website == undefined ? "" : localStorage.website;
    $scope.name = localStorage.userName;
    $scope.email = localStorage.email;
    var url = $rootScope.baseUrl + "/api/0/home/article/"+$scope.articleId;
    $http.get(url).success(function (result) {
        if (result.status == 0) {
            $scope.article = result.data;
            // $scope.article.htmlContent = HtmlUtil.htmlDecodeByRegExp($scope.article.htmlContent);
            $('#article_content').html($scope.article.htmlContent);
        }else {
            $state.go("404");
        }
    });

    $scope.reviewConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 5,
        pagesLength: 9,
        search: true,
        showDetail: false,
        onChange: function() {
            var param = {
                "pageNum": this.currentPage,
                "pageSize": this.itemsPerPage,
                "articleId": $scope.articleId
            };
            $http.post($rootScope.baseUrl + "/api/0/review/list", JSON.stringify(param)).success(function (result) {
                $scope.reviewList = result.data.data;
                $scope.reviewConfig.totalItems = result.data.total;
            });
        }
    };

    /**
     * 回复评论
     *
     * @param id
     * @param content
     * @returns {boolean}
     */
    $scope.replay = function (id, content) {
        localStorage.userName = $scope.name;
        localStorage.email = $scope.email;
        localStorage.website = $scope.website;
        if (!$scope.name || !$scope.email || !content) {
            UIToastr.error("请填写用户名、邮箱和回复内容", "回复失败！");
            return false;
        }
        var param = {
            content:content,
            userName: $scope.name,
            email:$scope.email,
            parentId: id,
            articleId:$scope.articleId
        };
        $http.post($rootScope.baseUrl + "/api/0/review/add", JSON.stringify(param)).success(function (response) {
            if(response.status == 0){
                UIToastr.success("回复成功！");
                $scope.subject = "";
                $scope.message = "";
                $scope.reviewConfig.onChange();
                return true;
            }else {
                UIToastr.error("系统异常，请稍后再试！", "评论失败");
                return false;
            }
        });
        return false;
    };

    /**
     * 发表评论
     */
    $scope.submit = function (parentId) {
        if ($scope.name && $scope.email && $scope.message) {
            localStorage.userName = $scope.name;
            localStorage.email = $scope.email;
            localStorage.website = $scope.website;
            var param = {
                content:$scope.message,
                userName: $scope.name,
                email:$scope.email,
                parentId:parentId,
                articleId:$scope.articleId
            };
            $http.post($rootScope.baseUrl + "/api/0/review/add", JSON.stringify(param)).success(function (response) {
                if(response.status == 0){
                    UIToastr.success("发表评论成功！");
                    $scope.subject = "";
                    $scope.message = "";
                    $scope.reviewConfig.onChange();
                }else {
                    UIToastr.error("系统异常，请稍后再试！", "评论失败");
                }
            });
        }
    };

    $scope.praise = function (reviewId) {
    };

    $scope.$watch(function(){
        return $rootScope.lastArticles;
    }, function (newValue, oldValue) {
        console.log(newValue);
        $scope.lastArticles = newValue;
    }, true);

});

