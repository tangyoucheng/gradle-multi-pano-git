<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展览导航图热点编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>

<script src="static/pano/pano/js/pano01/pano0107.js"></script>
<script src="static/pano/pano/js/pano01/pano0107_panoOnloadcomplete.js"></script>
<!-- <script src="static/pano/pano/js/pano01/pano0107_old.js"></script> -->

</head>

<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body ">
                <form:form id="pano0107Form" class="form-horizontal" modelAttribute="pano0107Form">
                    <input type="hidden" name="expositionId" id="expositionId" value='${pano0107Form.expositionId}' />
                    <input type="hidden" name="panoramaId" id="panoramaId" value='${pano0107Form.panoramaId}' />
                    <input type="hidden" name="panoramaPath" id="panoramaPath" value='${pano0107Form.panoramaPath}' />
                    <input type="hidden" name="expositionName" id="expositionName"
                        value="${pano0107Form.expositionName}" />
                    <input type="hidden" name="expositionMapId" id="expositionMapId"
                        value="${pano0107Form.expositionMapId}" />
                    <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${pano0107Form.miniMapCheck}" />
                    <input type="hidden" name="expositionMapPath" id="expositionMapPath"
                        value="${pano0107Form.expositionMapPath}" />
                    <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson"
                        value='${pano0107Form.miniMapSpotInfoListJson}' />
                    <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson"
                        value='${pano0107Form.spotInfoListSubmitJson}' />
                    <input type="hidden" name="theLastedSceneHotspotId" value="${pano0107Form.theLastedSceneHotspotId}" />
                    <input type="hidden" name="currentRecommendHotspotId"
                        value="${pano0107Form.currentRecommendHotspotId}" />

                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">当前场景名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0107Form.panoramaName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">热点样式</label>
                        <div class="col-sm-4">
                            <platform:bootstrap-select id="styleSelect" name="hotspotStyle"
                                selectedValue="${pano0107Form.hotspotStyle}" list="${pano0107Form.hotspotStyleList}"
                                blank="false"></platform:bootstrap-select>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">&nbsp;</label>
                        <div class="col-sm-4">
                            <button type="button" id="add-mini-map-hotspot-button" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                <c:out value="新增"></c:out>
                            </button>
                            <button type="button" id="save-button" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="保存"></c:out>
                            </button>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <div class="col-sm-12">
                            <div id="pano0107Pano" style="width: 100%; height: 600px;"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0107FormAjaxSubmit" action="/" modelAttribute="pano0107Form">
        <input type="hidden" name="panoramaId" value="${pano0107Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0107Form.expositionId}" />
        <input type="hidden" name="expositionMapId" value="${pano0107Form.expositionMapId}" />
        <input type="hidden" name="expositionName" value="${pano0107Form.expositionName}" />
        <input type="hidden" name="theLastedSceneHotspotId" value="${pano0107Form.theLastedSceneHotspotId}" />
        <input type="hidden" name="currentRecommendHotspotId" value="${pano0107Form.currentRecommendHotspotId}" />
    </form:form>
</body>

</html>

