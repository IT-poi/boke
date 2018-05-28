blog.controller('contactController', function($rootScope, $scope, $http, $uibModal, $state) {

    $scope.init = function () {
        $('[data-toggle="tooltip"]').tooltip();
    };

    $scope.contactConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 10,
        pagesLength: 9,
        search: true,
        showDetail:false,
        onChange: function() {
            $scope.list(0);
        }
    };

    /**
     * 忽略文章
     *
     * @param contactId
     */
    $scope.ignore = function (contactId) {
        $http.post($rootScope.baseUrl + "/api/1/contact/ignore/" + contactId).success(function (result) {
            if (result.status == 0) {
                $scope.list();
            }else {
                UIToastr.error(result.message, "忽略失败！");
            }
        });
    };

    /**
     * 删除文章
     *
     * @param contactId
     */
    $scope.delete = function (contactId) {
        $http.post($rootScope.baseUrl + "/api/1/contact/delete/" + contactId).success(function (result) {
            if (result.status == 0) {
                $scope.list();
            }else {
                UIToastr.error(result.message, "删除失败！");
            }
        });
    };

    //回复留言
    $scope.replay = function (contactId, userName, content) {
        var modalInstance = $uibModal.open({
            // animation: true,
            // ariaLabelledBy: 'modal-title',
            // ariaDescribedBy: 'modal-body',
            templateUrl: 'modal/contact-replay-modal.html',
            controller: 'ContactReplayModalCtrl',
            size: 'md',
            // appendTo: parentElem,
            resolve: {
                params: function () {
                    return {
                        "contactId": contactId,
                        "userName": userName,
                        "content": content
                    };
                }
            }
        });
        modalInstance.result.then(function (result) {
            $scope.list();
        }, function () {
            console.log("close...");
        });
    };

    $scope.list = function (replay, ignore) {
        ignore = ignore === undefined ? 0 : ignore;
        App.blockUI({animate: true, target: '#table'});
        var param = {
            "pageNum": this.currentPage,
            "pageSize": this.itemsPerPage,
            "ignore": ignore,
            "replay": replay
        };
        $http.post($rootScope.baseUrl + "/api/1/contact/list",JSON.stringify(param)).success(function (result) {
            App.unblockUI('#table');
            $scope.contacts = result.data.data;
            $scope.contactConfig.totalItems = result.data.total;
        });
    }

});
blog.filter('replay', function() { //可以注入依赖
    return function(text) {
        switch (text) {
            case 0:
                return "未回复";
            case 1:
                return "已回复";
            default :
                return "未回复";
        }
    }
});
// blog.filter('splitcharacters', function () {
//     return function (input, chars) {
//         if (isNaN(chars)) return input;
//         if (chars <= 0) return '';
//         if (input && input.length > chars) {
//             var prefix = input.substring(0, chars / 2);
//             var postfix = input.substring(input.length - chars / 2, input.length);
//             return prefix + '...' + postfix;
//         }
//         return input;
//     };
// }).filter('characters', function () {
//     return function (input, chars, breakOnWord) {
//         if (isNaN(chars)) return input;
//         if (chars <= 0) return '';
//         if (input && input.length > chars) {
//             input = input.substring(0, chars);
//
//             if (!breakOnWord) {
//                 var lastspace = input.lastIndexOf(' ');
//                 //get last space
//                 if (lastspace !== -1) {
//                     input = input.substr(0, lastspace);
//                 }
//             } else {
//                 while (input.charAt(input.length - 1) === ' ') {
//                     input = input.substr(0, input.length - 1);
//                 }
//             }
//             return input + '…';
//         }
//         return input;
//     };
// });