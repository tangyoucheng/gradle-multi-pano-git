
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展览目录编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano03/pano0308.js"></script>

<link href="static/framework/plugins/x-editable/1.5.3/bootstrap4-editable/css/bootstrap-editable.css" rel="stylesheet">
<script src="static/framework/plugins/x-editable/1.5.3/bootstrap4-editable/js/bootstrap-editable.js"></script>

<script src="static/framework/plugins/bootstrap-table/1.15.4/extensions/editable/bootstrap-table-editable.js"></script>


<!-- <link href="static/framework/plugins/bootstrap-table/1.15.4/extensions/cell-input/bootstrap-table-cell-input.css" -->
<!--     rel="stylesheet"> -->
<!-- <script src="static/framework/plugins/bootstrap-table/1.15.4/extensions/cell-input/bootstrap-table-cell-input.js"></script> -->
</head>
<body>
    <div class="card-body">
        <div id="toolbar" class="btn-group mt-2 mb-2">
            <button id="btn_update" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                <c:out value="保存"></c:out>
            </button>
        </div>
        <table id="table-panorama-info" data-use-row-attr-func="true" data-reorderable-rows="true">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="panoramaSortKey" data-halign="center" data-align="center">顺序</th>
                    <th data-field="panoramaName" data-halign="center" data-align="left">场景名</th>
                    <th data-field="panoramaPath" data-halign="center" data-align="left">缩略图</th>
                    <th data-field="thumbNote" data-editable="true" data-halign="center" data-align="left">缩略图信息</th>
                    <th data-field="expositionId" data-visible="false">展览编号</th>
                    <th data-field="panoramaId" data-visible="false">场景编号</th>
                    <th data-field="thumbnailShowFlag" data-visible="false">缩略图显示标识</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0308FormAjaxSubmit" action="/" modelAttribute="pano0308Form">
        <input type="hidden" name="expositionId" value="${pano0308Form.expositionId}" />
    </form:form>
</body>
</html>

