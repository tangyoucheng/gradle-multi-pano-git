//会员注册

$(function() {

    $("#closeUserRegisterDialog").click(function() {
        // 关闭自页面
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);
    });

    // 登录跳转事件
    $("#toLogin").click(function() {
        // 关闭自页面
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);

//        var targetUrl = getMemberContextPath('platform01010202/');
//        window.top.layer.open({
//            title : false, // 不显示标题栏
//            type : 2,
//            closeBtn : 0, // 不显示关闭按钮
//            area : [ '638px', '293px' ], // 宽高
//            // area : [ '698px', '366px' ], // 宽高
//            content : [ targetUrl, 'yes' ],
//            end : function() {
//                // searchData();
//            }
//        });
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
    // 注册跳转事件
    $("#RegisterBtnTwo").click(function() {
    });
});

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
 * 注册
 */
function nameRegister() {


    // 统一移除popover提示消息层
    $("#platform01010201Form").validate().destroy();
    // validate初始化
    $("#platform01010201Form").validate();
     $("#txt_memberName").attr('required', '');
     $("#txt_memberLoginId").attr('required', '');
     $("#txt_memberPassword").attr('required', '');
     $("#txt_memberPasswordConfirm").attr('required', '');
     $("#txt_captchaText").attr('required', '');
    // 有验证错误时返回
    if (!$("#platform01010201Form").valid()) {
        return;
    }
    
    var targetUrl = 'platform01010201/checkRegister';
    var formData = form2js($("#platform01010201Form")[0]);
    $.ajax({
        type : 'post',
        traditional : true,
        data : formData,
        dataType : 'json',
        url : getMemberContextPath(targetUrl),
        success : function(result) {
            // 获取后台验证信息
            if (result.obj != null &&　result.obj.length > 0) {
                var checkErrors = [];
                jQuery.each(result.obj, function(i, checkInfo) {
                    checkErrors.push(checkInfo);
                });
                // 显示信息
                if (checkErrors.length > 0) {
                    window.top.showErrorMessage(checkErrors, false, 3);
                }
            } else {
                // 注册成功
                CommonUtilJs.processAjaxSuccessAfter(result);
                // 会员登陆
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
                var loginFormData = new FormData($("#platform01010201Form")[0]);
                loginFormData.append("loginId", $('#txt_memberLoginId').val());
                loginFormData.append("password", $('#txt_memberPassword').val());
                generalLoginXHR.send(loginFormData);
            }
        }
    });
}

// 刷新验证码
function refreshCaptcha() {
 // <img id="captchaImg" style="height: 48px; width: 130px;"
 // src="images/captcha"
 // onclick="refreshCaptcha()">
 $('#captchaImg').attr('src', getContextPath("images/captcha?" + new Date().getTime()));
}