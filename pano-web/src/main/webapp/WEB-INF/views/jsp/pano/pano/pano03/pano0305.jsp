
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景切换热点编辑画面 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano03/pano0305.js"></script>


</head>
<body>
    <div class="card-body">

        <div id="toolbar" class="btn-group mt-2 mb-2">
            <button id="btn_add" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新增"></c:out>
            </button>
            <button id="btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
            <button id="btn_choose" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                <c:out value="选择"></c:out>
            </button>
        </div>
        <table id="table-expositionMap-info" data-use-row-attr-func="true" data-reorderable-rows="true">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="expositionMapName" data-halign="center" data-align="center">名称</th>
                    <th data-field="notes" data-halign="center" data-align="left">说明</th>
                    <th data-field="expositionMapId" data-visible="false">编号</th>
                    <th data-field="expositionMapPath" data-visible="false">路径</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0305FormAjaxSubmit" action="/" modelAttribute="pano0305Form">
        <input type="hidden" name="expositionId" value="${pano0305Form.expositionId}" />
        <input type="hidden" name="panoramaId" value="${pano0305Form.panoramaId}" />
    </form:form>
</body>
<!-- 预览全景图 -->
<div id="pano0305OpenLayer" title="预览" class="text-center" style="display: none;">
    <div class="form-group col-sm-12 d-flex align-items-center align-content-center">
        <img id="imagePreview" src="" style="width: 100%; height: auto; display: none;" />
    </div>
</div>
</html>