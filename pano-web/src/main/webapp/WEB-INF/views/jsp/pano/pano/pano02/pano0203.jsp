<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景单点热点编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano02/pano0203.js"></script>
<script src="static/pano/pano/js/pano02/pano0203_callback.js"></script>
<!-- <script src="static/pano/pano/js/pano02/pano0203_old.js"></script> -->
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0203FormAdd" class="form-horizontal" modelAttribute="pano0203Form">
                    <input type="hidden" name="panoramaId" value="${pano0203Form.panoramaId}" />
                    <input type="hidden" name="expositionId" value="${pano0203Form.expositionId}" />
                    <input type="hidden" name="expositionName" value="${pano0203Form.expositionName}" />
                    <input type="hidden" name="hotspotInfoListJson" id="hotspotInfoListJson"
                        value='${pano0203Form.hotspotInfoListJson}' />
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">场景名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0203Form.panoramaName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName"></label>
                        <div class="col-sm-5">
                            <button type="button" id="btn_add" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                <c:out value="添加"></c:out>
                            </button>
                            <button type="button" id="btn_entry" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="保存"></c:out>
                            </button>
                        </div>
                    </div>

                    <div class="form-group form-row">
                        <div class="col-sm-9">
                            <div id="pano0203Pano" style="width: 100%; height: calc(100vh - 85px);"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0203FormAjaxSubmit" action="/" modelAttribute="pano0203Form">
        <input type="hidden" name="panoramaId" value="${pano0203Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0203Form.expositionId}" />
        <input type="hidden" name="expositionName" value="${pano0203Form.expositionName}" />
    </form:form>
</body>
<!-- 热点单击事件指令框 -->
<div id="pano0203HotspotCommandChoice" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-edit-icon" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="更换热点图片"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-edit-size" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="调整热点大小"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-delete-hotspot" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="删除热点"></c:out>
            </button>
        </div>
    </div>
</div>

<!-- 单点音频热点单击事件指令框 -->
<div id="pano0203HotspotSoundCommandChoice" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-sound-hotspot-image" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="预览"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-sound-hotspot-icon" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="更换热点图片"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-sound-hotspot-size" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="调整热点大小"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-delete-hotspot" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="删除热点"></c:out>
            </button>
        </div>
    </div>
</div>
</html>
