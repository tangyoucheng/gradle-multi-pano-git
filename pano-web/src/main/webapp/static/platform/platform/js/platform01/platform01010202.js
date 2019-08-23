//会员登录

$(function() {

    // 取得top的URL
    var topHref = '';
    try {
        topHref = top.location.href;
    } catch (ex) {
        // 异常的场合
    }

    // 当前URL不是top的URL的场合
    if (topHref !== location.href) {
        top.location.href = location.href;
        return;
    }

    $("#closeUserLoginDialog").click(function() {
        // 关闭自页面
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);
    });

    // 登录跳转事件
    $("#toLogin").click(function() {
        // 关闭自页面
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);

        var targetUrl = getMemberContextPath('platform01010202/');
        window.top.layer.open({
            title : false, // 不显示标题栏
            type : 2,
            closeBtn : 0, // 不显示关闭按钮
            area : [ '638px', '293px' ], // 宽高
            // area : [ '698px', '366px' ], // 宽高
            content : [ targetUrl, 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });
    // 注册跳转事件
    $("#toRegister").click(function() {
        // 关闭自页面
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);

        var targetUrl = getMemberContextPath('platform01010201/');
        window.top.layer.open({
            title : false, // 不显示标题栏
            type : 2,
            closeBtn : 0, // 不显示关闭按钮
            area : [ '638px', '468px' ], // 宽高
            // area : [ '698px', '366px' ], // 宽高
            content : [ targetUrl, 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });
    /*
     * 登录框中的 所有事件
     */
    $("#putongTab").click(function() {
        $("#putongTab").addClass("underL1");
        $("#duanxingTab").removeClass("underL1");
        $("#generalLogin").show();
        $("#mobileLogin").hide();
    });
    $("#duanxingTab").click(function() {
        $("#duanxingTab").addClass("underL1");
        $("#putongTab").removeClass("underL1");
        $("#mobileLogin").show();
        $("#generalLogin").hide();
    });
    // 检测输入信息
    $(".r_input_1").blur(function() {
        var length = $(this).val().length;
        if (length == 0) {
            var id = $(this).attr("id");
            $("#" + id + "-tip").show();
        } else {
            var id = $(this).attr("id");
            $("#" + id + "-tip").hide();
        }
    });
});
// 刷新验证码
function refreshCaptcha() {
    // <img id="captchaImg" style="height: 48px; width: 130px;"
    // src="images/captcha"
    // onclick="refreshCaptcha()">
    $('#captchaImg').attr('src', getContextPath("images/captcha?" + new Date().getTime()));
}
/**
 * 会员普通登录 手机号 和 密码登录
 */
function generalLogin() {
//    var username = $("#username").val();
//    var password = $("#password").val();

    // 统一移除popover提示消息层
    $("#platform01010202Form-generalLogin").validate().destroy();
    // validate初始化
    $("#platform01010202Form-generalLogin").validate();
     $("#username").attr('required', '');
     $("#password").attr('required', '');
    // $("#txt_bankCode").attr('required', '');
    // 有验证错误时返回
    if (!$("#platform01010202Form-generalLogin").valid()) {
        return;
    }

    // if (username == "") {
    // $("#username-tip").show();
    // // window.top.showSuccessMessage('手机号是必须项目<br>sdfd', true, 3, true);
    // // window.top.showWarningMessage('手机号是必须项目',true);
    // window.top.showErrorMessage('手机号是必须项目', true, 3);
    // return;
    // }
    // if (password == "") {
    // $("#password-tip").show();
    // window.top.showErrorMessage('密码是必须项目', true, 3);
    // return;
    // }

    // 表单数据转换成JS对象
    var formData = new FormData(document.getElementById('platform01010202Form-generalLogin'));
//    formData.append("loginId", username);
//    formData.append("password", password);

    // 会员登陆验证处理
    var checkGeneralLoginXHR = new XMLHttpRequest();
    checkGeneralLoginXHR.open("POST", getContextPath('/checkGeneralLogin'));
    checkGeneralLoginXHR.responseType = "json";
    checkGeneralLoginXHR.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    checkGeneralLoginXHR.onloadstart = function() {
        window.top.layuiLoading();
    }
    checkGeneralLoginXHR.onload = function(result) {
        var checkResponseText = this.response;
        if (typeof (this.response) === 'string') {
            checkResponseText = JSON.parse(this.response);
        }
        if (checkResponseText.success === true) { // 验证通过的场合
            // 会员登陆处理
            var generalLoginXHR = new XMLHttpRequest();
            generalLoginXHR.open("POST", getMemberContextPath('login'));
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
                    var targetUrl = '' + $('#targetUrl').val();
                    window.top.location.href = getMemberContextPath(targetUrl);
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

/**
 * 短信倒计时
 */
var countdown = 120;

function settime(val) {
    if (countdown == 0) {
        val.removeAttribute("disabled");
        val.value = "免费获取验证码";
        countdown = 120;
    } else {
        val.setAttribute("disabled", true);
        val.value = "重新发送(" + countdown + ")";
        countdown--;
        setTimeout(function() {
            settime(val)
        }, 1000)
    }
}
// 获取登录动态验证码
function getLoginWord(val) {
    var phone = $("#phone").val();
    if (phone != "") {
        var regExp = /^(86)?((13\d{9})|(15[0,1,2,3,5,6,7,8,9]\d{8})|(18[0,5,6,7,8,9]\d{8}))$/;
        if (!regExp.test(phone)) {
            $("#phone-tip .icon_wrong").html("手机格式错误!");
            $("#phone-tip ").show();
        } else {
            var data = {
                phone : phone,
            }
            $.ajax({
                type : 'post',
                data : data,
                dataType : 'json',
                url : getContextPath('login/sendSms'),
                success : function(data) {

                }
            });
            settime(val);
        }
    } else {
        $("#phone-tip").show();
    }
}

// 获取注册验证码
function getRegisterWord(val) {
    var phone = $("#phone2").val();
    if (phone != "") {
        var regExp = /^(86)?((13\d{9})|(15[0,1,2,3,5,6,7,8,9]\d{8})|(18[0,5,6,7,8,9]\d{8}))$/;
        if (!regExp.test(phone)) {
            $("#phone2-tip .icon_wrong").html("手机格式错误!");
            $("#phone2-tip ").show();
        } else {
            var data = {
                phone : phone,
            }
            $.ajax({
                type : 'post',
                data : data,
                dataType : 'json',
                url : getContextPath('login/sendSms'),
                success : function(data) {

                }
            });
            settime(val);
        }
    } else {
        $("#phone2-tip").show();
    }
}

/**
 * 验证码登录
 */
function mobileLogin() {
    var phone = $("#phone").val();
    var word = $("#word").val();
    if (phone != "" && word != "") {
        // 封装JSON数据 提交
        var data = {
            phone : phone,
            word : word
        }
        // 发送请求
        $.ajax({
            type : 'post',
            data : data,
            dataType : 'json',
            url : getContextPath('/mobileLogin'),
            success : function(data) {
                if (data.success) {
                    console.log("登录成功");
                    location.reload(true);
                } else {
                    if (data.message.indexOf("验证码") != -1) {
                        $("#word-tip .icon_wrong").html(data.message);
                        $("#word-tip").show();
                    } else {
                        $("#phone-tip .icon_wrong").html(data.message);
                        $("#phone-tip").show();
                    }
                }
            }
        })
    } else if (phone == "") {
        $("#phone-tip").show();
    } else {
        $("#word-tip").show();
    }
}

/**
 * 注册
 */
function mobileRegister() {
    var phone = $("#phone2").val();
    var word = $("#word2").val();
    if (phone != "" && word != "") {
        // 封装JSON数据 提交
        var data = {
            phone : phone,
            word : word
        }
        // 发送请求
        $.ajax({
            type : 'post',
            data : data,
            dataType : 'json',
            url : getContextPath('/mobileRegister'),
            success : function(data) {
                if (data.success) {
                    location.reload(true);
                } else {
                    if (data.message.indexOf("验证码") != -1) {
                        $("#word2-tip .icon_wrong").html(data.message);
                        $("#word2-tip").show();
                    } else {
                        $("#phone2-tip .icon_wrong").html(data.message);
                        $("#phone2-tip").show();
                    }
                }
            }
        })
    } else if (phone == "") {
        $("#phone2-tip").show();
    } else {
        $("#word2-tip").show();
    }
}