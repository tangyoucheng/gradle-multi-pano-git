<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 会员注册 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/dialog.css'/>" />
<script src="static/sh/js/sh01/sh01010201.js"></script>
</head>
<body>
    <form:form action="/" modelAttribute="sh01010201Form">
    </form:form>
    <!--注册框-->
    <div id="userRegisterDialog" class="login-register-box ui-dialog-content ui-widget-content"
        style="width: auto; min-height: 120px; height: auto;">
        <div class="w_001">
            <div id="closeUserRegisterDialog" class="r_closed">
                <span></span>
            </div>
            <div class="clearfix">
                <div class="r_mian_l">
                    <ul class="r_tab clearfix">
                        <li class="percent_50 tc cur">注册账号</li>
                    </ul>
                    <!--短信登录-->
                    <div style="display: block;" id="mobileRegister">
                        <ul class="register_list">
                            <li>
                                <div class="h_30"></div>
                                <div class="pr">
                                    <div class="r_telepho_ico"></div>
                                    <input id="phone2" class="r_input_1" autocomplete="off" placeholder="手机号"
                                        type="text" />
                                </div>
                            </li>
                            <li>
                                <div class="h_30">
                                    <div id="phone2-tip" class="r_error_tip" style="display: none;">
                                        <span class="icon_wrong">请输入手机号码</span>
                                    </div>
                                </div>
                                <div class="pr">
                                    <div class="r_vali_ico"></div>
                                    <input id="word2" type="text" class="r_input_1 r_input_small" autocomplete="off"
                                        placeholder="动态验证码">
                                    <input type="button" class="btn have-nb" value="免费获取验证码"
                                        onclick="getRegisterWord(this)" />
                                    <div class="h_30">
                                        <div id="word2-tip" class="r_error_tip" style="display: none;">
                                            <span class="icon_wrong">请输入动态验证码</span>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <a id="RegisterBtnTwo" class="r_input mt_10" onclick="mobileRegister()">立即注册</a>
                    </div>
                </div>
                <div class="r_mian_r">
                    <h5>合作网站账户登录</h5>
                    <p>
                        <c:out value="已有账号？"></c:out>
                        <a id="toLogin" href="javascript:void(0);" style="color: #f05b72;">立即登录</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>