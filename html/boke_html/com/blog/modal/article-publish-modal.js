blog.controller('ModalInstanceCtrl', function ($rootScope, $scope, $http, $uibModalInstance, params) {
    console.log(params);

    $http.post($rootScope.baseUrl + "/api/1/article/category/list").success(function (result) {
        if (result.status == 0) {
            $scope.categories = result.data;
            for (var i = 0; i < $scope.categories.length; i++) {
                var obj = $scope.categories[i];
                if (obj.id === params.categoryId){
                    $scope.category = obj;
                }
            }
        }
    });

    $scope.init = function () {
        $scope.article = params;
        $scope.privacy = $scope.article.status === 2;
        $scope.category = {};
        var tags = params.labelNames;
        $('[data-toggle="tags"]').tags(tags);
    };

    /**
     * 发布或保存文章
     */
    $scope.ok = function (status) {
        var param = $scope.article;
        if(status === 1) { //发布
            if ($scope.privacy) {//私密文章
                param.status = 2;
            }else {
                param.status = status;
                param.picUrl = isBlank($scope.article.picUrl) ? "images/4.jpg" : $scope.article.picUrl;
            }
        }
        param.categoryId = $scope.category.id;
        param.labelNames = $('[data-toggle="tags"]').val();
        console.log(param);
        $http.post($rootScope.baseUrl + "/api/1/manage/article/sou",JSON.stringify(param)).success(function (result) {
            if (result.status == 0) {
                $scope.article = result.data;
                UIToastr.success("发布成功");
                $uibModalInstance.close($scope.article);
            }
        });
    };

    function check() {
        if (isBlank($scope.article.htmlContent) || isBlank($scope.article.markdown) || isBlank($scope.article.title)) {
            UIToastr.error("文章内容不能为空！");
            return;
        }
    }

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});