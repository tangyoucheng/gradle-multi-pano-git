<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展览导航图编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>

<script src="static/pano/pano/js/pano01/pano0106.js"></script>
<script src="static/pano/pano/js/pano01/pano0106_panoOnloadcomplete.js"></script>
<!-- <script src="static/pano/pano/js/pano01/pano0106_old.js"></script> -->

</head>

<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body ">
                <form:form id="pano0106Form" class="form-horizontal" modelAttribute="pano0106Form">
                    <input type="hidden" name="expositionId" id="expositionId" value='${pano0106Form.expositionId}' />
                    <input type="hidden" name="panoramaId" id="panoramaId" value='${pano0106Form.panoramaId}' />
                    <input type="hidden" name="panoramaPath" id="panoramaPath" value='${pano0106Form.panoramaPath}' />
                    <input type="hidden" name="expositionName" id="expositionName"
                        value="${pano0106Form.expositionName}" />
                    <input type="hidden" name="expositionMapId" id="expositionMapId"
                        value="${pano0106Form.expositionMapId}" />
                    <input type="hidden" name="pano0106expositionMapName" id="pano0106expositionMapName"
                        value="${pano0106Form.pano0106expositionMapName}" />
                    <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${pano0106Form.miniMapCheck}" />
                    <input type="hidden" name="expositionMapPath" id="expositionMapPath"
                        value="${pano0106Form.expositionMapPath}" />
                    <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson"
                        value="${pano0106Form.miniMapSpotInfoListJson}" />
                    <input type="hidden" name="theLastedSceneHotspotId" id="pano0106LasthotspotId"
                        value="${pano0106Form.theLastedSceneHotspotId}" />
                    <input type="hidden" name="currentRecommendHotspotId" id="pano0106CurrenthotspotId"
                        value="${pano0106Form.currentRecommendHotspotId}" />

                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">当前场景名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0106Form.panoramaName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">&nbsp;</label>
                        <div class="col-sm-11">
                            <button type="button" id="set-mini-map-button" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="为当前展览选择导航图"></c:out>
                            </button>
                            <c:if test="${pano0106Form.miniMapCheck == true }">
                                <button type="button" id="delete-mini-map-button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                    <c:out value="删除当前导航图"></c:out>
                                </button>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <div class="col-sm-12">
                            <div id="pano0106Pano" style="width: 100%; height: 600px;"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0106FormAjaxSubmit" action="/" modelAttribute="pano0106Form">
        <input type="hidden" name="panoramaId" value="${pano0106Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0106Form.expositionId}" />
        <input type="hidden" name="expositionMapId" value="${pano0106Form.expositionMapId}" />
        <input type="hidden" name="expositionName" value="${pano0106Form.expositionName}" />
        <input type="hidden" name="theLastedSceneHotspotId" value="${pano0106Form.theLastedSceneHotspotId}" />
        <input type="hidden" name="currentRecommendHotspotId" value="${pano0106Form.currentRecommendHotspotId}" />
    </form:form>
</body>

</html>

