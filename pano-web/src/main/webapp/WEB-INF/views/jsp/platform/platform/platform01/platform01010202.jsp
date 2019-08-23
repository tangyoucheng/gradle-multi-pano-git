<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 会员登录 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/dialog.css'/>" />
<script src="static/sh/js/sh01/sh01010202.js"></script>
</head>
<body>
    <form:form action="/" modelAttribute="sh01010202Form">
        <input type="hidden" id="targetUrl" name="targetUrl" value="${sh01010202Form.targetUrl }">
    </form:form>
    <!--登录框-->
    <div id="userLoginDialog" class="login-register-box ui-dialog-content ui-widget-content"
        style="width: auto; min-height: 120px; height: auto;">
        <div class="w_001">
            <div id="closeUserLoginDialog" class="r_closed">
                <span></span>
            </div>
            <div class="clearfix">
                <div class="r_mian_l">
                    <ul class="r_tab clearfix">
                        <li id="putongTab" class="percent_50 tc cur underL1">普通登录</li>
                        <li id="duanxingTab" class="percent_50 tc cur ">短信快捷登录</li>
                    </ul>
                    <!--普通登录-->
                    <div style="display: block;" id="generalLogin">
                        <ul class="register_list">
                            <li>
                                <div class="h_30"></div>
                                <div class="pr">
                                    <div class="r_tel_ico"></div>
                                    <input id="username" class="r_input_1" autocomplete="off" placeholder="手机号"
                                        type="text" />
                                </div>
                            </li>
                            <li>
                                <div class="h_30">
                                    <div id="username-tip" class="r_error_tip" style="display: none;">
                                        <span class="icon_wrong">请输入手机号</span>
                                    </div>
                                </div>
                                <div class="pr">
                                    <div class="r_pwd_ico"></div>
                                    <input type="password" id="password" class="r_input_1" autocomplete="off"
                                        placeholder="密码">
                                    <div class="h_30">
                                        <div id="password-tip" class="r_error_tip" style="display: none;">
                                            <span class="icon_wrong">请输入密码</span>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <a id="loginBtn" class="r_input mt_10" onclick="generalLogin()">登录</a>
                    </div>
                    <!--短信登录-->
                    <div style="display: none;" id="mobileLogin">
                        <ul class="register_list">
                            <li>
                                <div class="h_30"></div>
                                <div class="pr">
                                    <div class="r_telepho_ico"></div>
                                    <input id="phone" class="r_input_1" autocomplete="off" placeholder="手机号" type="text" />
                                </div>
                            </li>
                            <li>
                                <div class="h_30">
                                    <div id="phone-tip" class="r_error_tip" style="display: none;">
                                        <span class="icon_wrong"> 请输入手机号码</span>
                                    </div>
                                </div>
                                <div class="pr">
                                    <div class="r_vali_ico"></div>
                                    <input id="word" type="text" class="r_input_1 r_input_small" autocomplete="off"
                                        placeholder="动态验证码">
                                    <input type="button" class="btn have-nb" id="btn" value="免费获取验证码"
                                        onclick="getLoginWord(this)" />
                                    <div class="h_30">
                                        <div id="word-tip" class="r_error_tip" style="display: none;">
                                            <span class="icon_wrong"> 请输入动态验证码</span>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <a id="loginBtnTwo" class="r_input mt_10" onclick="mobileLogin()">登录</a>
                    </div>
                </div>
                <div class="r_mian_r">
                    <h5>合作网站账户登录</h5>
                    <p>
                        <c:out value="还没有注册？"></c:out>
                        <a id="toRegister" href="javascript:void(0);" style="color: #f05b72;">立即注册</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>