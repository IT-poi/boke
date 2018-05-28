blog.controller('apilogController', function($rootScope, $scope, $http) {

    $scope.apiLog = {};

    var checkall=document.getElementsByName("checkbox[]");
    //全选
    $scope.selectAll = function(){
        for(var $i=0;$i<checkall.length;$i++){
            checkall[$i].checked=$scope.checkAll;
        }
    };


    $scope.init = function () {
        $('[data-toggle="tooltip"]').tooltip();
    };

    $scope.delete = function (logId) {
        $http.post($rootScope.baseUrl + "/api/1/apilog/delete/" + logId).success(function (result) {
            if (result.status == 0) {
                $scope.apilogConfig.onChange();
            }else {
                UIToastr.error(result.message, "删除失败！");
            }
        });
    };

    $scope.deleteMore = function () {
        var ids = "";
        for(var $i=0;$i<checkall.length;$i++){
            if ( checkall[$i].checked) {
                ids = ids.concat(checkall[$i].value, ",");
            }
        }
        $http.post($rootScope.baseUrl + "/api/1/apilog/delete/" + ids).success(function (result) {
            if (result.status == 0) {
                $scope.apilogConfig.onChange();
            }else {
                UIToastr.error(result.message, "删除失败！");
            }
        });
    };

    $scope.apilogConfig = {
        align: "right",
        currentPage: 1,
        itemsPerPage: 15,
        pagesLength: 9,
        search: true,
        showDetail:true,
        onChange: function() {
            $scope.list();
        }
    };

    $scope.list = function () {
        App.blockUI({animate: true, target: '#table'});
        var param = {
            "pageNum": $scope.apilogConfig.currentPage,
            "pageSize": $scope.apilogConfig.itemsPerPage,
            "clazz": $scope.apiLog.clazz,
            "httpMethod": $scope.apiLog.httpMethod,
            "ip": $scope.apiLog.ip,
            "method": $scope.apiLog.method,
            "sorter": null,
            "url": $scope.apiLog.url,
            "userId": $scope.apiLog.userId
        };
        $http.post($rootScope.baseUrl + "/api/1/apilog/list",JSON.stringify(param)).success(function (result) {
            App.unblockUI('#table');
            $scope.apilogs = result.data.data;
            $scope.apilogConfig.totalItems = result.data.total;
        });
    };

});