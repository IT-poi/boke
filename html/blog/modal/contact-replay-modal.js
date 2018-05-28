blog.controller('ContactReplayModalCtrl', function ($rootScope, $scope, $http, $uibModalInstance, params) {
    $scope.contact = params;

    /**
     * 发布或保存文章
     */
    $scope.ok = function () {
        check();
        var param = {
            "contactId": $scope.contact.contactId,
            "replayContext": $scope.contact.replayContext
        };
        $http.post($rootScope.baseUrl + "/api/1/contact/replay",JSON.stringify(param)).success(function (result) {
            if (result.status == 0) {
                UIToastr.success("回复成功！");
                $uibModalInstance.close();
            }
        });
    };

    function check() {
        if (isBlank($scope.contact.replayContext)) {
            UIToastr.error("回复内容不能为空！");
            return;
        }
    }

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});