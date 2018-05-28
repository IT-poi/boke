blog.controller('reviewController', function($rootScope, $scope, $http, $uibModal, $state) {

    $scope.userId = -1;

    $scope.init = function () {
        $('[data-toggle="tooltip"]').tooltip();
    };

    /**
     * 删除评论
     * @param reviewId
     */
    $scope.delete = function (reviewId) {
        $http.post($rootScope.baseUrl + "/api/1/review/delete/" + reviewId).success(function (result) {
            if (result.status == 0) {
                $scope.reviewConfig.onChange();
            }else {
                UIToastr.error(result.message, "删除失败！");
            }
        });
    };

    $scope.replay = function (reviewId, articleId, userName, content) {
        var modalInstance = $uibModal.open({
            // animation: true,
            // ariaLabelledBy: 'modal-title',
            // ariaDescribedBy: 'modal-body',
            templateUrl: 'modal/review-replay-modal.html',
            controller: 'ReviewReplayModalCtrl',
            size: 'md',
            // appendTo: parentElem,
            resolve: {
                params: function () {
                    return {
                        "id": reviewId,
                        "userName": userName,
                        "reviewContent": content,
                        "articleId": articleId
                    };
                }
            }
        });
        modalInstance.result.then(function (result) {
            $scope.reviewConfig.onChange();
        }, function () {
            console.log("close...");
        });
    };


    $scope.reviewConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 10,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            App.blockUI({animate: true, target: '#table'});
            var param = {
                "pageNum": this.currentPage,
                "pageSize": this.itemsPerPage
            };
            $http.post($rootScope.baseUrl + "/api/0/review/list",JSON.stringify(param)).success(function (result) {
                App.unblockUI('#table');
                $scope.reviews = result.data.data;
                $scope.reviewConfig.totalItems = result.data.total;
            });
        }
    };
    $scope.$watch(function () {
        if ($rootScope.userInfo ===undefined) {
            return null;
        }else {
            return $rootScope.userInfo.userId;
        }
        return $rootScope.userInfo.userId;
    }, function(newVal, oldVal) {
        $scope.userId = newVal;
    });

});
blog.filter('splitcharacters', function () {
    return function (input, chars) {
        if (isNaN(chars)) return input;
        if (chars <= 0) return '';
        if (input && input.length > chars) {
            var prefix = input.substring(0, chars / 2);
            var postfix = input.substring(input.length - chars / 2, input.length);
            return prefix + '...' + postfix;
        }
        return input;
    };
}).filter('characters', function () {
    return function (input, chars, breakOnWord) {
        if (isNaN(chars)) return input;
        if (chars <= 0) return '';
        if (input && input.length > chars) {
            input = input.substring(0, chars);

            if (!breakOnWord) {
                var lastspace = input.lastIndexOf(' ');
                //get last space
                if (lastspace !== -1) {
                    input = input.substr(0, lastspace);
                }
            } else {
                while (input.charAt(input.length - 1) === ' ') {
                    input = input.substr(0, input.length - 1);
                }
            }
            return input + '…';
        }
        return input;
    };
});