<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司角色一览，弹出页面 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz02/platformz020203.js"></script>
</head>
<body>
    <div class="card-body">
        <!-- 检索条件 -->
        <form:form id="platformz020203FormSearch" action="/" modelAttribute="platformz020203Form">
            <input type="hidden" id="returnTargetIframe" name="returnTargetIframe" value="${platformz020203Form.returnTargetIframe }">
        </form:form>
        <div id="toolbar" class="btn-group">
            <button id="platformz020203Btn_selected" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                <c:out value="选择"></c:out>
            </button>
        </div>
        <table id="table-rolesInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="roleName" data-halign="center" data-align="left">角色名</th>
                    <th data-field="roleId" data-visible="false">编号</th>
                </tr>
            </thead>
        </table>
    </div>
</body>
</html>