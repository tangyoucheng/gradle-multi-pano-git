<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 管理员管理 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz01/platformz0101.js"></script>
</head>
<body>
    <div class="card-body">
        <!-- 检索条件 -->
        <form:form id="platformz0101FormSearch" action="/" modelAttribute="platformz0101Form">
        </form:form>
        <div id="toolbar" class="btn-group">
            <button id="platformz0101Btn_new" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新建"></c:out>
            </button>
            <button id="platformz0101Btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
        </div>
        <table id="table-memberInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="memberLoginId" data-halign="center" data-align="left">用户登陆ID</th>
                    <th data-field="memberName" data-halign="center" data-align="left">用户名</th>
                    <th data-field="memberEmail" data-halign="center" data-align="left">邮箱</th>
                    <th data-field="memberId" data-visible="false">用户编码</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platformz0101FormAjaxSubmit" action="/" modelAttribute="platformz0101Form">
    </form:form>
</body>
</html>