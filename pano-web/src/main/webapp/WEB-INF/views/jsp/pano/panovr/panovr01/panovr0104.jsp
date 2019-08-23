<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展览场景编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>

<script src="framework/minicolors/jquery.minicolors.js"></script>
<link href="framework/minicolors/jquery.minicolors.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="framework/panorama/template/staticvtour/js/magnific/jquery.magnific-popup.js"></script>
<link href="framework/panorama/template/staticvtour/js/magnific/magnific-popup.css" rel="stylesheet" type="text/css" />

<script src="static/pano/panovr/js/panovr01/panovr0104.js"></script>

</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="panoVr0104Form" class="form-horizontal" modelAttribute="panoVr0104Form">
                    <input type="hidden" id="selectedHotspotId" name="selectedHotspotId"
                        value="${panoVr0104Form.selectedHotspotId}" />
                    <input type="hidden" id="selectedMiniMapHotspotId" name="selectedMiniMapHotspotId"
                        value="${panoVr0104Form.selectedMiniMapHotspotId}" />
                    <input type="hidden" id="spotInfoListJson" name="spotInfoListJson"
                        value='${panoVr0104Form.spotInfoListJson}' />
                    <input type="hidden" id=" pointList" name=" pointList" value='${panoVr0104Form.pointList}' />
                    <input type="hidden" id="startSceneFlag" name="startSceneFlag"
                        value="${panoVr0104Form.startSceneFlag}" />
                    <input type="hidden" id="expositionId" name="expositionId" value='${panoVr0104Form.expositionId}' />
                    <input type="hidden" id="expositionName" name="expositionName"
                        value="${panoVr0104Form.expositionName}" />
                    <input type="hidden" id="panoramaPath" name="panoramaPath" value="${panoVr0104Form.panoramaPath}" />
                    <input type="hidden" id="panoramaId" name="panoramaId" value="${panoVr0104Form.panoramaId}" />
                    <input type="hidden" id="panoramaName" name="panoramaName" value="" />
                    <input type="hidden" id="theLastPanoramaId" name="theLastPanoramaId"
                        value="${panoVr0104Form.theLastPanoramaId}" />
                    <input type="hidden" id="vr0202panoramaId" value="" />
                    <input type="hidden" id="panoramaVlookat" name="panoramaVlookat"
                        value="${panoVr0104Form.panoramaVlookat}" />
                    <input type="hidden" id="panoramaHlookat" name="panoramaHlookat"
                        value="${panoVr0104Form.panoramaHlookat}" />
                    <input type="hidden" name="expositionMapId" id="expositionMapId"
                        value="${panoVr0104Form.expositionMapId}" />
                    <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${panoVr0104Form.miniMapCheck}" />
                    <input type="hidden" name="expositionMapPath" id="expositionMapPath"
                        value="${panoVr0104Form.expositionMapPath}" />
                    <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson"
                        value='${panoVr0104Form.miniMapSpotInfoListJson}' />
                    <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson"
                        value='${panoVr0104Form.spotInfoListSubmitJson}' />
                    <input type="hidden" name="hotspotTooltip" id="hotspotTooltip"
                        value='${panoVr0104Form.hotspotTooltip}' />
                    <input type="hidden" name="backGroundSoundPath" id="backGroundSoundPath"
                        value='${panoVr0104Form.backGroundSoundPath}' />
                    <input type="hidden" name="vr0209hotspotId" id="vr0209hotspotId"
                        value="${panoVr0104Form.selectedHotspotId}" />
                    <input type="hidden" name="radarHeading" id="radarHeading" value="${panoVr0104Form.radarHeading}" />
                    <input type="hidden" name="theLastedSceneHotspotId" id="theLastedSceneHotspotId"
                        value="${panoVr0104Form.theLastedSceneHotspotId}" />
                    <input type="hidden" name="currentRecommendHotspotId" id="currentRecommendHotspotId"
                        value="${panoVr0104Form.currentRecommendHotspotId}" />
                    <input type="hidden" name="expositionRecommendInfo" id="expositionRecommendInfo"
                        value="${panoVr0104Form.expositionRecommendInfo}" />
                    <input type="hidden" id="currentSoundPath" value='' />
                    <input type="hidden" id="vr0104DeleteFlgFromVr0202" value="" />
                    <input type="hidden" name="thisFlagIsFromIc0110" value="${thisFlagIsFromIc0110 }" />
                    <input type="hidden" name="expoGoSceneTooltipInfo" id="expoGoSceneTooltipInfo"
                        value="${panoVr0104Form.expoGoSceneTooltipInfo}" />
                    <div class="form-group form-row text-center">
                        <div class="col-sm-12">
                            <div id="toolbar" class="btn-group mt-2 mb-2">
                                <button id="btn_add_pano" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    <c:out value="新增"></c:out>
                                </button>
                                <button id="btn_edit_pano" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    <c:out value="编辑"></c:out>
                                </button>
                                <button id="btn_edit_pano" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    <c:out value="保存排序"></c:out>
                                </button>
                                <button id="btn_delete_pano" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                                    <c:out value="删除"></c:out>
                                </button>
                            </div>
                            <div class="btn-group mt-2 mb-2">
                                <button id="btn_add" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    <c:out value="新增素材"></c:out>
                                </button>
                                <button id="btn_delete" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                                    <c:out value="素材一览"></c:out>
                                </button>
                            </div>
                            <div class="btn-group mt-2 mb-2">
                                <button id="btn_add" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    <c:out value="新增"></c:out>
                                </button>
                                <button id="btn_edit" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    <c:out value="编辑"></c:out>
                                </button>
                                <button id="btn_delete" type="button" class="btn pano-btn-danger">
                                    <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                                    <c:out value="删除"></c:out>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <div class="col-sm-1">
                            <table id="table-panorama-info" data-unique-id="id">
                                <thead>
                                    <tr>
                                        <th data-field="panoramaName" data-halign="center" data-align="left">场景一览</th>
                                        <th data-field="panoramaId" data-visible="false">编号</th>
                                        <th data-field="panoramaSoundId" data-visible="false">声音编码</th>
                                        <th data-field="panoramaHlookat" data-visible="false">垂直位置</th>
                                        <th data-field="panoramaVlookat" data-visible="false">水平位置</th>
                                        <th data-field="startSceneFlag" data-visible="false">首场景标识</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class="col-sm-10">
                            <c:if
                                test='${not empty panoramaPath and panoramaSelectInfo != null and panoramaSelectInfo.size() != 0}'>
                                <div id="panovr0104Pano" style="width: 100%; height: 600px;">
                                    <c:out value='<script type="text/javascript">eval("doInitPano()");</script>'></c:out>
                                </div>

                            </c:if>
                        </div>
                        <div id="operating-area-right" class="col-sm-1">右侧操作区域</div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>


