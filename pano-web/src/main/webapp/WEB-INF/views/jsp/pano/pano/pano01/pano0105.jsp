<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 整体效果操作 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>

<script src="static/pano/pano/js/pano01/pano0105.js"></script>
<script src="static/pano/pano/js/pano01/pano0105_panoOnloadcomplete.js"></script>
<script src="static/pano/pano/js/pano01/pano0105_callback.js"></script>
<!-- <script src="static/pano/pano/js/pano01/pano0105_old.js"></script> -->

</head>

<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body ">
                <form:form id="pano0105Form" class="form-horizontal" modelAttribute="pano0105Form">
                    <input type="hidden" name="expositionId" id="expositionId" value='${pano0105Form.expositionId}' />
                    <input type="hidden" name="positionAthForEdit" id="pano0105positionAthForEdit"
                        value="${pano0105Form.positionAthForEdit}" />
                    <input type="hidden" name="positionAtvForEdit" id="pano0105positionAtvForEdit"
                        value="${pano0105Form.positionAtvForEdit}" />
                    <input type="hidden" name="panoramaId" id="panoramaId" value='${pano0105Form.panoramaId}' />
                    <input type="hidden" name="panoramaPath" id="panoramaPath" value='${pano0105Form.panoramaPath}' />
                    <input type="hidden" name="expositionName" id="expositionName"
                        value="${pano0105Form.expositionName}" />
                    <input type="hidden" name="expositionMapId" id="expositionMapId"
                        value="${pano0105Form.expositionMapId}" />
                    <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${pano0105Form.miniMapCheck}" />
                    <input type="hidden" name="expositionMapPath" id="expositionMapPath"
                        value="${pano0105Form.expositionMapPath}" />
                    <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson"
                        value='${pano0105Form.miniMapSpotInfoListJson}' />
                    <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson"
                        value='${pano0105Form.spotInfoListSubmitJson}' />
                    <input type="hidden" name="selectedHotspotId" id="selectedHotspotId"
                        value="${pano0105Form.selectedHotspotId}" />
                    <input type="hidden" name="radarHeading" id="radarHeading" value="${pano0105Form.radarHeading}" />
                    <input type="hidden" name="flowInfoFileId" id="flowInfoFileId"
                        value="${pano0105Form.flowInfoFileId}" />
                    <input type="hidden" name="flowInfoFileX" id="flowInfoFileX" value="${pano0105Form.flowInfoFileX}" />
                    <input type="hidden" name="flowInfoFileY" id="flowInfoFileY" value="${pano0105Form.flowInfoFileY}" />
                    <input type="hidden" name="flowInfoFilePath" id="flowInfoFilePath"
                        value="${pano0105Form.flowInfoFilePath}" />
                    <input type="hidden" name="flowInfoFileInfo" id="flowInfoFileInfo"
                        value="${pano0105Form.flowInfoFileInfo}" />
                    <input type="hidden" name="flowInfoType" id="flowInfoType" value="${pano0105Form.flowInfoType}" />
                    <input type="hidden" name="flowInfoFileScale" id="flowInfoFileScale"
                        value="${pano0105Form.flowInfoFileScale}" />
                    <input type="hidden" name="selectedButtons" id="selectedButtons"
                        value='${pano0105Form.selectedButtons}' />
                    <input type="hidden" name="theLastedSceneHotspotId" id="pano0105lastHotspotId"
                        value="${pano0105Form.theLastedSceneHotspotId}" />
                    <input type="hidden" name="currentRecommendHotspotId" id="pano0105CurrentHotspotId"
                        value="${pano0105Form.currentRecommendHotspotId}" />

                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">当前场景名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0105Form.panoramaName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">&nbsp;</label>
                        <div class="col-sm-11">
                            <platform:bootstrap-select id="commandType" name="commandType"
                                selectedValue="${pano0105Form.commandType}" list="${pano0105Form.commandTypeList}"
                                blank="false"></platform:bootstrap-select>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">&nbsp;</label>
                        <div class="col-sm-11">

                            <!-- 导航图 -->
                            <button type="button" id="edit-mini-map-button" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="导航图编辑"></c:out>
                            </button>
                            <button type="button" id="edit-map-hotspot-button" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="导航图热点编辑"></c:out>
                            </button>
                            <button type="button" id="left" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="雷达左旋"></c:out>
                            </button>
                            <button type="button" id="right" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="雷达右旋"></c:out>
                            </button>
                            <button type="button" id="set_radar" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="设定雷达位置"></c:out>
                            </button>

                            <!-- 浮动效果 -->
                            <button type="button" id="set_common_info" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="设置公共信息"></c:out>
                            </button>
                            <button type="button" id="button-image-confirm" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="浮动信息(图片)"></c:out>
                            </button>
                            <button type="button" id="button-text-confirm" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="浮动信息(文字)"></c:out>
                            </button>
                            <button type="button" id="save_flow_info" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="保存浮动效果位置"></c:out>
                            </button>

                            <!-- 场景工具 -->
                            <button type="button" id="edit-buttons" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="自定义工具条"></c:out>
                            </button>
                            <button type="button" id="edit-director" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="展览目录编辑"></c:out>
                            </button>
                            <button type="button" id="edit-effect" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="展览效果编辑"></c:out>
                            </button>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <div class="col-sm-12">
                            <div id="pano0105Pano" style="width: 100%; height: 600px;"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0105FormAjaxSubmit" action="/" modelAttribute="pano0105Form">
        <input type="hidden" name="panoramaId" value="${pano0105Form.panoramaId}" />
        <input type="hidden" name="panoramaName" value="${pano0105Form.panoramaName}" />
        <input type="hidden" name="expositionId" value="${pano0105Form.expositionId}" />
        <input type="hidden" name="expositionMapId" value="${pano0105Form.expositionMapId}" />
        <input type="hidden" name="expositionName" value="${pano0105Form.expositionName}" />
        <input type="hidden" name="theLastedSceneHotspotId" value="${pano0105Form.theLastedSceneHotspotId}" />
        <input type="hidden" name="currentRecommendHotspotId" value="${pano0105Form.currentRecommendHotspotId}" />
        <input type="hidden" name="selectedButtons" value="${pano0105Form.selectedButtons}" />
    </form:form>
</body>


<!-- 浮动信息种类选择-->
<div id="pano0105HotspotCommandChoice" title="浮动信息种类选择" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-image-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="浮动信息(图片)"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-text-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="浮动信息(文字)"></c:out>
            </button>
        </div>
    </div>
</div>
<!-- 浮动信息操作选择-->
<div id="pano0105FlowInfoEidtChoice" title="浮动信息操作选择" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-edit-layer-size" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑浮动信息层大小"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-delete-layer" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="删除该浮动信息"></c:out>
            </button>
        </div>
    </div>
</div>
</html>

