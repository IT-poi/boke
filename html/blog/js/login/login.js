function init(){
    $.backstretch([
            "assets/pages/media/bg/1.jpg",
            "assets/pages/media/bg/2.jpg",
            "assets/pages/media/bg/3.jpg",
            "assets/pages/media/bg/4.jpg"],
        {fade:1000,duration:10000})
}
init();
function login() {
    var username = $("input[name='username']").val();
    var password = $("input[name='password']").val();

}

function tijiao() {
    var userName = $("input[name='fUserName']").val();
    var email = $("input[name='fEmail']").val();
    $.ajax({
        type: "post",
        url: 'http://193.112.112.136:8080/boke-core/api/0/admin/reset',
        async: true, // 使用异步方式
        // 1 需要使用JSON.stringify 否则格式为 a=2&b=3&now=14...
        // 2 需要强制类型转换，否则格式为 {"a":"2","b":"3"}
        data: JSON.stringify({
            userName: userName,
            email: email
        }),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            App.unblockUI();
            console.log(data);
            if (data.status == 0) {
                var mess = data.data;
                $("#fAlertValue").html(mess);
                $("#ftishi").removeClass("alert-danger");
                $("#ftishi").show();
            }else {
                $("#fAlertValue").html(data.message);
                $("#ftishi").addClass("alert-danger");
                $("#ftishi").show();
            }
        }, // 注意不要在此行增加逗号
        error: function (error) {
            App.unblockUI();
        }
    });

}
// var UIBlockUI = function () {
//     var o = function () {
//         $("#blockui_sample_1_1").click(function () {
//             App.blockUI({target: "#blockui_sample_1_portlet_body"}), window.setTimeout(function () {
//                 App.unblockUI("#blockui_sample_1_portlet_body")
//             }, 2e3)
//         }), $("#blockui_sample_1_2").click(function () {
//             App.blockUI({target: "#blockui_sample_1_portlet_body", boxed: !0}), window.setTimeout(function () {
//                 App.unblockUI("#blockui_sample_1_portlet_body")
//             }, 2e3)
//         }), $("#blockui_sample_1_3").click(function () {
//             App.blockUI({target: "#blockui_sample_1_portlet_body", animate: !0}), window.setTimeout(function () {
//                 App.unblockUI("#blockui_sample_1_portlet_body")
//             }, 2e3)
//         })
//     }, e = function () {
//         $("#blockui_sample_2_1").click(function () {
//             App.blockUI(), window.setTimeout(function () {
//                 App.unblockUI()
//             }, 2e3)
//         }), $("#blockui_sample_2_2").click(function () {
//             App.blockUI({boxed: !0}), window.setTimeout(function () {
//                 App.unblockUI()
//             }, 2e3)
//         }), $("#blockui_sample_2_3").click(function () {
//             App.startPageLoading({message: "Please wait..."}), window.setTimeout(function () {
//                 App.stopPageLoading()
//             }, 2e3)
//         }), $("#blockui_sample_2_4").click(function () {
//             App.startPageLoading({animate: !0}), window.setTimeout(function () {
//                 App.stopPageLoading()
//             }, 2e3)
//         })
//     }, c = function () {
//         $("#blockui_sample_3_1_0").click(function () {
//             App.blockUI({
//                 target: "#basic",
//                 overlayColor: "none",
//                 cenrerY: !0,
//                 animate: !0
//             }), window.setTimeout(function () {
//                 App.unblockUI("#basic")
//             }, 2e3)
//         }), $("#blockui_sample_3_1").click(function () {
//             App.blockUI({target: "#blockui_sample_3_1_element", overlayColor: "none", animate: !0})
//         }), $("#blockui_sample_3_1_1").click(function () {
//             App.unblockUI("#blockui_sample_3_1_element")
//         }), $("#blockui_sample_3_2").click(function () {
//             App.blockUI({target: "#blockui_sample_3_2_element", boxed: !0})
//         }), $("#blockui_sample_3_2_1").click(function () {
//             App.unblockUI("#blockui_sample_3_2_element")
//         })
//     }, l = function () {
//         $("#blockui_sample_4_1").click(function () {
//             App.blockUI({
//                 target: "#blockui_sample_4_portlet_body",
//                 boxed: !0,
//                 message: "Processing..."
//             }), window.setTimeout(function () {
//                 App.unblockUI("#blockui_sample_4_portlet_body")
//             }, 2e3)
//         }), $("#blockui_sample_4_2").click(function () {
//             App.blockUI({target: "#blockui_sample_4_portlet_body", iconOnly: !0}), window.setTimeout(function () {
//                 App.unblockUI("#blockui_sample_4_portlet_body")
//             }, 2e3)
//         }), $("#blockui_sample_4_3").click(function () {
//             App.blockUI({
//                 target: "#blockui_sample_4_portlet_body",
//                 boxed: !0,
//                 textOnly: !0
//             }), window.setTimeout(function () {
//                 App.unblockUI("#blockui_sample_4_portlet_body")
//             }, 2e3)
//         })
//     };
//     return {
//         init: function () {
//             o(), e(), c(), l()
//         }
//     }
// }();
// jQuery(document).ready(function () {
//     UIBlockUI.init()
// });