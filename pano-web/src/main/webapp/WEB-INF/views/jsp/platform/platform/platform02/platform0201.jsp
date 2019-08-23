<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 管理员管理 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/platform02/platform0201.js"></script>
</head>
<body>
    <div class="card-body">
        <!-- 检索条件 -->
        <form:form id="platform0201FormSearch" action="/" modelAttribute="platform0201Form">
        </form:form>
        <div id="toolbar" class="btn-group">
            <button id="platform0201Btn_new" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新建"></c:out>
            </button>
            <button id="platform0201Btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
        </div>
        <table id="table-adminInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="adminLoginId" data-halign="center" data-align="left">用户登陆ID</th>
                    <th data-field="adminName" data-halign="center" data-align="left">用户名</th>
                    <th data-field="adminEmail" data-halign="center" data-align="left">邮箱</th>
                    <th data-field="adminId" data-visible="false">用户编码</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platform0201FormAjaxSubmit" action="/" modelAttribute="platform0201Form">
    </form:form>
</body>
</html>