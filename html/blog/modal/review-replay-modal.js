blog.controller('ReviewReplayModalCtrl', function ($rootScope, $scope, $http, $uibModalInstance, params) {
    $scope.review = params;

    $scope.ok = function () {
        if (isBlank($scope.review.content)) {
            UIToastr.error("请填写回复内容", "回复失败！");
            return false;
        }
        var param = {
            content:$scope.review.content,
            userName: $rootScope.userInfo.userNickName,
            userId: $rootScope.userInfo.id,
            email:$rootScope.userInfo.email,
            parentId: $scope.review.id,
            articleId:$scope.review.articleId
        };
        $http.post($rootScope.baseUrl + "/api/0/review/add", JSON.stringify(param)).success(function (result) {
            if (result.status == 0) {
                UIToastr.success("回复成功！");
                $uibModalInstance.close();
            }else {
                UIToastr.success(result.message, "回复失败！");
            }
        });
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});