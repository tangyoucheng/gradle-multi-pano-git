<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 后台管理员登陆 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<!-- 信息显示组件 -->
<link href="static/framework/plugins/overhang/1.0.5/overhang.css" rel="stylesheet" />
<script src="static/framework/plugins/overhang/1.0.5/overhang.js"></script>
<script src="static/framework/plugins/overhang/messageUtil.js"></script>
<script src="static/sh/js/common/common.js"></script>
<link href="static/sh/css/sh01/sh010101.css?v=<%=Math.random()%>" rel="stylesheet" />
</head>
<body>
    <form:form action="/" modelAttribute="sh010101Form">
    </form:form>
    <div class="contentBox">
        <div>
            <img src="<c:url value='/static/sh/images/sh01/rig.jpg'/>" width="500" alt="">
        </div>
        <div class="bs_lef">
            <div class="bs_con">
                <img src="<c:url value='/static/sh/images/sh01/logo.png'/>" class="logo">
                <div class="phone ">
                    <input type="text" id="username" name="username" class="loginuser" autocomplete="off" />
                    <div class="dh_successIcon"></div>
                </div>
                <div class="pwd">
                    <input type="password" name="password" id="password" class="loginpwd" />
                    <div class="dh_successIcon"></div>
                </div>
                <div class="sign_in">
                    <input id="testBtn" name="" type="button" class="loginbtn" value="登录" onclick="generalLogin();" />
                    
                    
                </div>
            </div>
        </div>
    </div>
</body>
<script src="static/sh/js/sh01/sh010101.js?v=<%=Math.random()%>"></script>
</html>