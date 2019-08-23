<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 管理员门户首页 -->
<!DOCTYPE html>
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/ps99/ps990102.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form modelAttribute="ps990101Form" class="form-horizontal">
                    <div class="form-group form-row">
                        <div class="col-sm-12 text-right">
                            <img src="<%=request.getContextPath()%>/static/platform/platform/images/ps99/icon_time.gif" width="13" height="13" />
                            <span id="timeNow" style="margin-right: 15px;"></span>
                        </div>
                    </div>
                    <div class="form-group form-row form-inline">
                        <div class="col-sm-auto">
                            <img src="<%=request.getContextPath()%>/static/platform/platform/images/ps99/perPhoto_no.jpg"
                                style="width: 74px; height: 74px; border-radius: 50px;">
                        </div>
                        <div style="width: 40px;">&nbsp;</div>
                        <div class="col-sm-auto align-middle">
                            <security:authentication property='principal.userDisplayName' />
                            &nbsp;&nbsp; 您好,欢迎回来！
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>
