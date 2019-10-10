<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano02/pano0209.js"></script>
<script src="static/pano/pano/js/pano02/pano0209_search.js"></script>
</head>
<body>
    <form:form id="pano0209Form" class="form-horizontal" modelAttribute="pano0209Form">

        <input type="hidden" id="returnTargetIframe" name="returnTargetIframe"
            value="${pano0209Form.returnTargetIframe}">

        <input type="hidden" id="panoramaIdForPano0209" name="panoramaIdForPano0209"
            value="${pano0209Form.panoramaIdForPano0209}" />
        <input type="hidden" id="expositionIdForPano0209" name="expositionIdForPano0209"
            value="${pano0209Form.expositionIdForPano0209}" />
        <input type="hidden" name="expositionName" value="${pano0209Form.expositionName}" />
        <input type="hidden" name="pano0209hotspotId" id="pano0209hotspotId" value="${pano0209Form.pano0209hotspotId}" />
        <input type="hidden" name="panoramaName" value="${pano0209Form.panoramaName}" />
        <input type="hidden" name="panoramaPath" id="pano0209panoramaPath" value="${pano0209Form.panoramaPath}" />
        <input type="hidden" name="hotspotScale" id="hotspotScale" value="${pano0209Form.hotspotScale}" />
        <input type="hidden" name="pano0209HotspotType" id="pano0209HotspotType"
            value="${pano0209Form.pano0209HotspotType}" />
        <input type="hidden" name="hotspotAth" id="hotspotAth" value="${pano0209Form.hotspotAth}" />
        <input type="hidden" name="hotspotAtv" id="hotspotAtv" value="${pano0209Form.hotspotAtv}" />
        <input type="hidden" name="positionAthForEdit" value="${pano0209Form.positionAthForEdit}" />
        <input type="hidden" name="positionAtvForEdit" value="${pano0209Form.positionAtvForEdit}" />
        <input type="hidden" name="pano0209flowFileId" id="pano0209flowFileId"
            value="${pano0209Form.pano0209flowFileId}" />
        <input type="hidden" name="pano0209flowFileType" id="pano0209flowFileType"
            value="${pano0209Form.pano0209flowFileType}" />
        <input type="hidden" name="pano0209flowFilePath" id="pano0209flowFilePath"
            value="${pano0209Form.pano0209flowFilePath}" />
        <input type="hidden" name="pano0209flowFileInfo" id="pano0209flowFileInfo"
            value="${pano0209Form.pano0209flowFileInfo}" />
        <input type="hidden" name="pano0209layerX" id="pano0209layerX" value="${pano0209Form.pano0209layerX}" />
        <input type="hidden" name="pano0209layerY" id="pano0209layerY" value="${pano0209Form.pano0209layerY}" />
        <input type="hidden" name="commandTypeFromPano0105" id="commandTypeFromPano0105"
            value='${pano0209Form.commandTypeFromPano0105}' />
        <input type="hidden" name="pano0209TheLastedSceneHotspotId" id="pano0209TheLastedSceneHotspotId"
            value='${pano0209Form.pano0209TheLastedSceneHotspotId}' />
        <input type="hidden" name="recommendInfo" id="recommendInfo" value="${pano0209Form.recommendInfo}" />
        <input type="hidden" name="pano0209RecommendHotspotId" id="pano0209RecommendHotspotId"
            value="${pano0209Form.pano0209RecommendHotspotId}" />
        <input type="hidden" name="lastedHotspotIdFrom0105" value="${pano0209Form.lastedHotspotIdFrom0105}" />
        <input type="hidden" name="currentHotspotIdFrom0105" value="${pano0209Form.currentHotspotIdFrom0105}" />
        <input type="hidden" name="expoRecommendInfo" id="expoRecommendInfo" value="${pano0209Form.expoRecommendInfo}" />
        <input type="hidden" name="pano0203HotspotName" id="pano0203HotspotName"
            value="${pano0209Form.pano0203HotspotName}" />
        <input type="hidden" name="pano0203HotspotAth" id="pano0203HotspotAth"
            value="${pano0209Form.pano0203HotspotAth}" />
        <input type="hidden" name="pano0203HotspotAtv" id="pano0203HotspotAtv"
            value="${pano0209Form.pano0203HotspotAtv}" />

        <input type="hidden" name="pano0203MusicHotspot" id="pano0203MusicHotspot"
            value="${pano0209Form.pano0203MusicHotspot}" />
        <input type="hidden" name="firsthotspotImageIdPano0203" id="firsthotspotImageIdPano0203"
            value="${pano0209Form.firsthotspotImageIdPano0203}" />
        <input type="hidden" name="seconfhotspotImageIdPano0203" id="seconfhotspotImageIdPano0203"
            value="${pano0209Form.seconfhotspotImageIdPano0203}" />
        <input type="hidden" name="firstSortKeyPano0203" id="firstSortKeyPano0203" value="${firstSortKeyPano0203}" />
        <input type="hidden" name="secondSortKeyPano0203" id="secondSortKeyPano0203"
            value="${pano0209Form.secondSortKeyPano0203}" />

        <input type="hidden" id="hotspotImageInfoJson" name="hotspotImageInfoJson"
            value='${pano0209Form.hotspotImageInfoJson}' />
        <input type="hidden" id="firstImageInfoJson" name="firstImageInfoJson"
            value='${pano0209Form.firstImageInfoJson}' />
        <input type="hidden" id="secondImageInfoJson" name="secondImageInfoJson"
            value='${pano0209Form.secondImageInfoJson}' />
        <input type="hidden" id="pano0203OperateFlag" name="pano0203OperateFlag"
            value='${pano0209Form.pano0203OperateFlag}' />

        <input type="hidden" name="buttonsInfoJson" id="buttonsInfoJson" value='${pano0209Form.buttonsInfoJson}' />
        <input type="hidden" name="selectedButtons" id="selectedButtons" value='${pano0209Form.selectedButtons}' />

        <div id="divCondition" class="card-body" style="display: none;">
            <div class="card">
                <div id="buttonsTitleDiv" class="card-header">工具条按钮一览</div>
                <div id="sizeTitleDiv" class="card-header">大小选择</div>
                <div id="editRecommendTitleDiv" class="card-header">编辑公共信息</div>
                <div class="card-body pb-0">

                    <table id="buttons-list" data-unique-id="id">
                        <thead>
                            <tr>
                                <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                                <th data-field="rowNumber" data-halign="center" data-align="center">行号</th>
                                <th data-field="buttonName" data-halign="center" data-align="left">按钮名称</th>

                                <th data-field="buttonName" data-visible="false">按钮代码</th>
                            </tr>
                        </thead>
                    </table>

                    <div id="sizeTr" class="form-group form-row">
                        <c:if test='${not empty pano0209Form.pano0209flowFileId}'>
                            <label class="col-sm-1 col-form-label" for="hotspotScale">浮动层大小</label>
                        </c:if>
                        <c:if test='${not empty pano0209Form.pano0209hotspotId}'>
                            <label class="col-sm-1 col-form-label" for="hotspotScale">热点大小</label>
                        </c:if>
                        <div class="col-sm-4">
                            <platform:bootstrap-select id="hotspotScale" name="hotspotScale"
                                selectedValue="${pano0209Form.hotspotScale}" list="${pano0209Form.hotspotSizeInfo}"
                                blank="false"></platform:bootstrap-select>
                        </div>
                    </div>

                    <div id="recommendTr" class="form-group form-row">
                        <label class="col-sm-1 col-form-label" for="recommendFlag">推荐路线点</label>
                        <div class="col-sm-4">
                            <div class="form-control border-0 pl-0">
                                <input type="checkbox" id="recommendFlag" name="recommendFlag" value="1" class="" />
                            </div>
                        </div>
                    </div>

                    <div id="tooltipTr" class="form-group form-row">
                        <label class="col-sm-1 col-form-label" for="txt_expoHotspotTooltipInfo">提示信息</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_expoHotspotTooltipInfo"
                                name="expoHotspotTooltipInfo" value="${pano0209Form.expoHotspotTooltipInfo}"
                                maxLength="100">
                        </div>
                    </div>

                    <div id="editExpoGoSceneInfoTr" class="form-group form-row">
                        <label class="col-sm-1 col-form-label" for="txt_notes">
                            <c:out value="切换场景的"></c:out>
                            <br>
                            <c:out value=" 公共提示信息"></c:out>
                        </label>
                        <div class="col-sm-4">
                            <form:textarea id="txt_notes" path="expoGoSceneInfo" cssClass="form-control"
                                htmlEscape="true" maxlength="2000" rows="2" placeholder="" style="overflow: auto;" />
                        </div>
                    </div>

                    <div class="form-group form-row pt-2 pb-0">
                        <label class="col-form-label col-sm-1 text-left" for=""></label>
                        <div class="col-sm-5">
                            <button type="button" id="button-pano0209-confirm" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="确定"></c:out>
                            </button>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div class="card-body pt-0">
            <div class="card">
                <div id="imui-chapter-title" class="card-header">效果预览</div>
                <div class="card-body">
                    <div id="pano0209Pano" style="width: 100%; height: 380px;"></div>
                </div>
            </div>
        </div>
    </form:form>

    <form id="back-form" method="POST" action="pano01/pano0104/"></form>

    <!-- 保存和取消处理 -->
    <form id="back-to-pano0104" method="POST" action="pano01/pano0104/doSearchFromPano0209">
        <input type="hidden" name="panoramaId" id="panoramaIdTo0104" value="${panoramaIdForPano0209}" />
        <input type="hidden" name="expositionId" id="expositionIdTo0209" value="${expositionIdForPano0209}" />
        <input type="hidden" name="expositionName" value="${expositionName}" />
        <input type="hidden" name="positionAthForEdit" id="0209BackAth" value="${positionAthForEdit}" />
        <input type="hidden" name="positionAtvForEdit" id="0209BackAtv" value="${positionAtvForEdit}" />
        <input type="hidden" name="currentRecommendHotspotId" id="recommendHotspotIdBackTo0104"
            value="${pano0209RecommendHotspotId }" />
        <input type="hidden" name="theLastedSceneHotspotId" value="${pano0209TheLastedSceneHotspotId}" />
    </form>
    <form id="back-to-pano0105" method="POST" action="pano01/pano0105">
        <input type="hidden" name="panoramaId" id="panoramaIdTo0104" value="${panoramaIdForPano0209}" />
        <input type="hidden" name="expositionId" id="expositionIdTo0209" value="${expositionIdForPano0209}" />
        <input type="hidden" name="expositionName" value="${expositionName}" />
        <input type="hidden" name="theLastedSceneHotspotId" value="${lastedHotspotIdFrom0105}" />
        <input type="hidden" name="currentRecommendHotspotId" value="${currentHotspotIdFrom0105}" />
    </form>

</body>
</html>