//后台管理员登录

$(function() {

    // 取得最顶层URL
    var topHref = '';
    try {
        topHref = top.location.href;
    } catch (ex) {
        // 跨域的场合，异常处理
    }

    // 外层frame不是登陆页面的场合，把登陆页面显示到最顶层
    if (topHref !== location.href) {
        top.location.href = location.href;
        return;
    }
});

/**
 * 后台管理员用户登录处理
 */
function generalLogin() {
    // var username = $("#username").val();
    // var password = $("#password").val();
    //
    // if (username == "") {
    // window.top.showErrorMessage('登录用户不能为空', true, 3, true);
    // // window.top.showErrorMessage('手机号是必须项目<br>换行测试', true, 3, true);
    // return;
    // }
    //
    // if (password == "") {
    // window.top.showErrorMessage('密码不能为空', true, 3);
    // return;
    // }

    // 统一移除popover提示消息层
    $("#platform010101Form").validate().destroy();
    // validate初始化
    $("#platform010101Form").validate();
    $("#username").attr('required', '');
    $("#password").attr('required', '');
    // $("#txt_bankCode").attr('required', '');
    // 有验证错误时返回
    if (!$("#platform010101Form").valid()) {
        return;
    }

    // 表单数据转换成JS对象
    var formData = new FormData(document.getElementById('platform010101Form'));
    // formData.append("loginId", username);
    // formData.append("password", password);

    // 管理员用户登陆验证处理
    var checkGeneralLoginXHR = new XMLHttpRequest();
    checkGeneralLoginXHR.open("POST", getAdminContextPath('checkGeneralLogin'));
    checkGeneralLoginXHR.responseType = "json";
    checkGeneralLoginXHR.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    checkGeneralLoginXHR.onloadstart = function() {
        window.top.layuiLoading();
    }
    checkGeneralLoginXHR.onload = function() {
        var checkResponseText = this.response;
        if (typeof (this.response) === 'string') {
            checkResponseText = JSON.parse(this.response);
        }
        if (checkResponseText.success === true) { // 验证通过的场合
            // 管理员用户登陆处理
            var generalLoginXHR = new XMLHttpRequest();
            generalLoginXHR.open("POST", getAdminContextPath('login'));
            generalLoginXHR.responseType = "json";
            generalLoginXHR.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            generalLoginXHR.onloadstart = function() {
                window.top.layuiLoading();
            }
            generalLoginXHR.onload = function() {
                var loginResponseText = this.response;
                if (typeof (this.response) === 'string') {
                    loginResponseText = JSON.parse(this.response);
                }
                if (loginResponseText.status && loginResponseText.status == 200) {
                    window.top.location.href = getAdminContextPath('ps99/ps9901');
                    return;
                }

                window.top.layuiRemoveLoading();
                $("#username-tip .icon_wrong").html(loginResponseText.msg);
                $("#username-tip").show();
            };
            // 发送请求
            generalLoginXHR.send(formData);

        } else { // 验证失败的场合
            window.top.layuiRemoveLoading();
            $("#username-tip .icon_wrong").html(checkResponseText.msg);
            $("#username-tip").show();
            window.top.showErrorMessage(checkResponseText.msg, false, 3);
        }
    };
    // 发送请求
    checkGeneralLoginXHR.send(formData);

}
