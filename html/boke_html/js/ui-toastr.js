var UIToastr = function () {
    return {
        init: function () {
            toastr.options = {
                closeButton: true, //显示右上角小X
                // debug: true,
                positionClass: 'toast-top-right', //显示位置
                onclick: null, //点击事件
                showDuration: 1000, //持续时间
                hideDuration: 1000, //消失时间
                timeOut: 3000,
                extendedTimeOut: 1000,
                showEasing: 'swing',
                hideEasing: 'linear',
                showMethod: 'fadeIn',
                hideMethod: 'fadeOut'
            };
        },
        //main function to initiate the module
        success: function (message, title) {
            title = title === undefined ? "" : title;
            var $toast = toastr['success'](message, title); // Wire up an event handler to a button in the toast, if it exists
            if ($toast.find('#okBtn').length) {
                $toast.delegate('#okBtn', 'click', function () {
                    alert('you clicked me. i was toast #' + toastIndex + '. goodbye!');
                    $toast.remove();
                });
            }
            if ($toast.find('#surpriseBtn').length) {
                $toast.delegate('#surpriseBtn', 'click', function () {
                    alert('Surprise! you clicked me. i was toast #' + toastIndex + '. You could perform an action here.');
                });
            }
        },
        info: function (message, title) {
            title = title === undefined ? "" : title;
            var $toast = toastr['info'](message, title); // Wire up an event handler to a button in the toast, if it exists
            if ($toast.find('#okBtn').length) {
                $toast.delegate('#okBtn', 'click', function () {
                    alert('you clicked me. i was toast #' + toastIndex + '. goodbye!');
                    $toast.remove();
                });
            }
            if ($toast.find('#surpriseBtn').length) {
                $toast.delegate('#surpriseBtn', 'click', function () {
                    alert('Surprise! you clicked me. i was toast #' + toastIndex + '. You could perform an action here.');
                });
            }
        },
        error: function (message, title) {
            title = title === undefined ? "" : title;
            var $toast = toastr['error'](message, title); // Wire up an event handler to a button in the toast, if it exists
            if ($toast.find('#okBtn').length) {
                $toast.delegate('#okBtn', 'click', function () {
                    alert('you clicked me. i was toast #' + toastIndex + '. goodbye!');
                    $toast.remove();
                });
            }
            if ($toast.find('#surpriseBtn').length) {
                $toast.delegate('#surpriseBtn', 'click', function () {
                    alert('Surprise! you clicked me. i was toast #' + toastIndex + '. You could perform an action here.');
                });
            }
        },
        warning: function (message, title) {
            title = title === undefined ? "" : title;
            var $toast = toastr['warning'](message, title); // Wire up an event handler to a button in the toast, if it exists
            if ($toast.find('#okBtn').length) {
                $toast.delegate('#okBtn', 'click', function () {
                    alert('you clicked me. i was toast #' + toastIndex + '. goodbye!');
                    $toast.remove();
                });
            }
            if ($toast.find('#surpriseBtn').length) {
                $toast.delegate('#surpriseBtn', 'click', function () {
                    alert('Surprise! you clicked me. i was toast #' + toastIndex + '. You could perform an action here.');
                });
            }
        }
    };

}();

jQuery(document).ready(function() {
    UIToastr.init();
});