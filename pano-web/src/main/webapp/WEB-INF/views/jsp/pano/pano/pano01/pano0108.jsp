<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 全景图多边形编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano01/pano0108.js"></script>
<script src="static/pano/pano/js/pano01/pano0108_panoOnloadcomplete.js"></script>
<!-- <script src="static/pano/pano/js/pano02/pano0108_old.js"></script> -->
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0108FormAdd" class="form-horizontal" modelAttribute="pano0108Form">

                    <input type="hidden" name="spotInfoListJson" id="spotInfoListJson"
                        value='${pano0108Form.spotInfoListJson}' />

                    <input type="hidden" name="expositionId" id="expositionId" value='${pano0108Form.expositionId}' />
                    <input type="hidden" name="expositionName" id="expositionName"
                        value='${pano0108Form.expositionName}' />
                    <input type="hidden" name="panoramaId" id="panoramaId" value='${pano0108Form.panoramaId}' />
                    <input type="hidden" name="panoramaName" id="panoramaName" value='${pano0108Form.panoramaName}' />
                    <input type="hidden" name="panoramaPath" id="panoramaPath" value='${pano0108Form.panoramaPath}' />
                    <input type="hidden" name="positionAthForEdit" value="${pano0108Form.positionAthForEdit}" />
                    <input type="hidden" name="positionAtvForEdit" value="${pano0108Form.positionAtvForEdit}" />
                    <input type="hidden" name="theLastedSceneHotspotId" value="${pano0108Form.theLastedSceneHotspotId}" />
                    <input type="hidden" name="currentRecommendHotspotId"
                        value="${pano0108Form.currentRecommendHotspotId}" />

                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">场景名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0108Form.panoramaName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName"></label>
                        <div class="col-sm-5">
                            <button type="button" id="btn_show_manual" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                <c:out value="编辑手册"></c:out>
                            </button>
                            <button type="button" id="btn_entry" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="保存"></c:out>
                            </button>
                        </div>
                    </div>

                    <div class="form-group form-row">
                        <div class="col-sm-9">
                            <div id="pano0108Pano" style="width: 100%; height: calc(100vh - 190px);"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0108FormAjaxSubmit" action="/" modelAttribute="pano0108Form">
        <input type="hidden" name="panoramaId" value="${pano0108Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0108Form.expositionId}" />
        <input type="hidden" name="expositionName" value="${pano0108Form.expositionName}" />
    </form:form>
</body>

</div>
</html>
