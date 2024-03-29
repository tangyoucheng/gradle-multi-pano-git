<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司社区一览，弹出页面 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/platform01/platform019903.js"></script>
</head>
<body>
    <div class="card-body">
        <!-- 检索条件 -->
        <form:form id="platform019903FormSearch" action="/" modelAttribute="platform019903Form">
            <input type="hidden" id="returnTargetIframe" name="returnTargetIframe" value="${platform019903Form.returnTargetIframe }">
            <label class="col-form-label form-group col-sm-1" for="txt_priority">社区名：</label>
            <div class="col-sm-auto input-group">
                <input type="text" class="form-control" id="txt_value" name="value">
                <div class="input-group-append btn-group-sm">
                    <button type="button" id="platform0301Btn_query" class="btn platform-btn-danger badge font-weight-normal">
                        <i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
        </form:form>
        <div id="toolbar" class="btn-group">
        </div>
        <table id="table-departmentInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="departmentId"   data-visible="false">社区ID</th>
                    <th data-field="departmentName" data-halign="center" data-align="left">社区名</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</body>
</html>