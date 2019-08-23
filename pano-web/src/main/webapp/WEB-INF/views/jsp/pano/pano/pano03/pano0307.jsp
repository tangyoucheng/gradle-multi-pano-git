
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景地图设置画面 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano03/pano0307.js"></script>


</head>
<body>
    <div class="card-body">

        <div id="toolbar" class="btn-group mt-2 mb-2">
            <button id="btn_choose" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                <c:out value="选择"></c:out>
            </button>
        </div>
        <table id="table-panorama-info" data-use-row-attr-func="true" data-reorderable-rows="true">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="panoramaSortKey" data-halign="center" data-align="center">顺序</th>
                    <th data-field="panoramaName" data-halign="center" data-align="left">场景名</th>
                    <th data-field="expositionId" data-visible="false">展览编号</th>
                    <th data-field="panoramaId" data-visible="false">场景编号</th>
                    <th data-field="panoramaSoundId" data-visible="false">声音编码</th>
                    <th data-field="panoramaHlookat" data-visible="false">垂直位置</th>
                    <th data-field="panoramaVlookat" data-visible="false">水平位置</th>
                    <th data-field="startSceneFlag" data-visible="false">首场景标识</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0307FormAjaxSubmit" action="/" modelAttribute="pano0307Form">
        <input type="hidden" name="expositionId" value="${pano0307Form.expositionId}" />
        <input type="hidden" name="currentPanoramaId" value="${pano0307Form.currentPanoramaId}" />
        <input type="hidden" name="selectedHotspotId" value="${pano0307Form.selectedHotspotId}" />
    </form:form>
</body>
<!-- 预览全景图 -->
<div id="pano0307OpenLayer" title="预览" class="text-center" style="display: none;">
    <div class="form-group col-sm-12 d-flex align-items-center align-content-center">
        <div id="pano0307Pano" style="width: 100%; height: 300px;"></div>
    </div>
</div>
</html>

