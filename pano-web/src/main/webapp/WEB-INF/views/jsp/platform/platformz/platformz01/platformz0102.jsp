<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 角色管理 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz01/platformz0102.js"></script>
</head>
<body>
    <div class="card-body">
        <!-- 检索条件 -->
        <form:form id="platformz0102FormSearch" action="/" modelAttribute="platformz0102Form">
        </form:form>
        <div id="toolbar" class="btn-group">
            <button id="platformz0102Btn_new" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新建"></c:out>
            </button>
            <button id="platformz0102Btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
        </div>
        <table id="table-rolesInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="roleName" data-halign="center" data-align="left">角色名</th>
                    <th data-field="roleId" data-visible="false">编号</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platformz0102FormAjaxSubmit" action="/" modelAttribute="platformz0102Form">
    </form:form>
</body>
</html>