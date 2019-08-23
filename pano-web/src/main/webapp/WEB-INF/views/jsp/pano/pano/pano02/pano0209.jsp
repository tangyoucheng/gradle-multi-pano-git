<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano02/pano0209.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">

                <form:form id="pano0209Form" class="form-horizontal" modelAttribute="pano0209Form">
                    <input type="hidden" id="returnTargetIframe" name="returnTargetIframe"
                        value="${pano0209Form.returnTargetIframe}">

                    <input type="hidden" id="panoramaIdForPano0209" name="panoramaIdForPano0209"
                        value="${pano0209Form.panoramaIdForPano0209}" />
                    <input type="hidden" id="expositionIdForPano0209" name="expositionIdForPano0209"
                        value="${pano0209Form.expositionIdForPano0209}" />
                    <input type="hidden" name="expositionName" value="${pano0209Form.expositionName}" />
                    <input type="hidden" name="pano0209hotspotId" id="pano0209hotspotId"
                        value="${pano0209Form.pano0209hotspotId}" />
                    <input type="hidden" name="panoramaName" value="${pano0209Form.panoramaName}" />
                    <input type="hidden" name="panoramaPath" id="pano0209panoramaPath"
                        value="${pano0209Form.panoramaPath}" />
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
                    <input type="hidden" name="pano0209layerX" id="pano0209layerX"
                        value="${pano0209Form.pano0209layerX}" />
                    <input type="hidden" name="pano0209layerY" id="pano0209layerY"
                        value="${pano0209Form.pano0209layerY}" />
                    <input type="hidden" name="commandTypeFromPano0105" id="commandTypeFromPano0105"
                        value='${pano0209Form.commandTypeFromPano0105}' />
                    <input type="hidden" name="pano0209TheLastedSceneHotspotId" id="pano0209TheLastedSceneHotspotId"
                        value='${pano0209Form.pano0209TheLastedSceneHotspotId}' />
                    <input type="hidden" name="recommendInfo" id="recommendInfo" value="${pano0209Form.recommendInfo}" />
                    <input type="hidden" name="pano0209RecommendHotspotId" id="pano0209RecommendHotspotId"
                        value="${pano0209Form.pano0209RecommendHotspotId}" />
                    <input type="hidden" name="lastedHotspotIdFrom0105" value="${pano0209Form.lastedHotspotIdFrom0105}" />
                    <input type="hidden" name="currentHotspotIdFrom0105"
                        value="${pano0209Form.currentHotspotIdFrom0105}" />
                    <input type="hidden" name="expoRecommendInfo" id="expoRecommendInfo"
                        value="${pano0209Form.expoRecommendInfo}" />
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
                    <input type="hidden" name="firstSortKeyPano0203" id="firstSortKeyPano0203"
                        value="${firstSortKeyPano0203}" />
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

                    <input type="hidden" name="selectedButtons" id="selectedButtons"
                        value='${pano0209Form.selectedButtons}' />


                    <div id="buttonsTitleDiv" class="imui-chapter-title"
                        style="background-color: rgb(251, 251, 251); margin-top: 10px">
                        <h2>工具条按钮一览</h2>
                    </div>

                    <div id="sizeTitleDiv" class="imui-chapter-title"
                        style="background-color: rgb(251, 251, 251); margin-top: 10px">
                        <h2>大小选择</h2>
                    </div>

                    <div id="editRecommendTitleDiv" class="imui-chapter-title"
                        style="background-color: rgb(251, 251, 251); margin-top: 10px">
                        <h2>编辑公共信息</h2>
                    </div>

                    <div id="buttonslistDiv" style="width: 100%; height: 360px; margin: 0px; overflow-y: scroll">
                        <input type="button" value="全选" id="selectAll" class="imui-button imui-running-button" />
                        <input type="button" value="全解除" id="cancelAll" class="imui-button imui-running-button" />
                        <table id="pano0209ListDataTable" style="width: 100%; margin-top: 2px;" border="1"
                            class="imui-table-sort eq_nowrap_hidden">
                            <thead>
                                <tr>
                                    <th class="align-C valign-M" style="width: 30px;">选择</th>
                                    <th class="align-C valign-M" style="width: auto;">按钮名称</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${pano0209Form.buttonsInfo}" var="searchResult"
                                    varStatus="searchResultStatus">
                                    <tr id="pano0205Tr_${searchResultStatus.index}" style="height: 10px;">
                                        <td align="center">
                                            <input type="checkbox" id="${searchResult.buttonName}" name="chkBox"
                                                value="${searchResult.buttonName}" />
                                        </td>
                                        <td title="${searchResult.buttonName_CN}" class="align-l valign-M">${searchResult.buttonName_CN}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <table class="imui-form">
                        <tr id="sizeTr">
                            <c:if test='${not empty pano0209Form.pano0209flowFileId}'>
                                <th style="width: 15%;">
                                    <label>浮动层大小</label>
                                </th>
                            </c:if>
                            <c:if test='${not empty pano0209Form.pano0209hotspotId}'>
                                <th style="width: 15%;">
                                    <label>热点大小</label>
                                </th>
                            </c:if>
                            <td colspan="3">
                                <platform:bootstrap-select id="hotspotScale" name="hotspotScale"
                                    selectedValue="${pano0209Form.hotspotScale}" list="${pano0209Form.hotspotSizeInfo}"
                                    blank="false"></platform:bootstrap-select>
                            </td>
                        </tr>

                        <tr id="recommendTr">
                            <th style="width: 15%;">
                                <label>推荐路线点设定</label>
                            </th>
                            <td colspan="3">
                                <input type="checkbox" id="recommendFlag" name="recommendFlag" value="1" />
                                设为推荐路线点
                            </td>
                        </tr>

                        <tr id="tooltipTr">
                            <th style="width: 16%;">
                                <label>提示信息</label>
                            </th>
                            <td colspan="3">
                                <input type="text" class="form-control" id="txt_expoHotspotTooltipInfo"
                                    name="expoHotspotTooltipInfo" value="${pano0209Form.expoHotspotTooltipInfo}"
                                    maxLength="100">
                            </td>
                        </tr>
                        <tr id="editExpoGoSceneInfoTr">
                            <th style="width: 18%;">
                                <label>
                                    切换场景的
                                    <br>
                                    公共提示信息
                                </label>
                            </th>
                            <td colspan="3">
                                <form:textarea id="txt_notes" path="expoGoSceneInfo" cssClass="form-control"
                                    htmlEscape="true" maxlength="2000" rows="2" placeholder="" style="overflow: auto;" />
                            </td>
                        </tr>
                    </table>

                    <div class="imui-operation-parts">
                        <input type="button" value="确定" id="button-pano0209-confirm" class="imui-medium-button" />
                    </div>

                    <div class="imui-chapter-title" style="background-color: rgb(251, 251, 251); margin-top: 10px">
                        <h2>效果预览</h2>
                    </div>
                    <div id="pano0209Pano" style="width: 100%; height: 380px;"></div>
                </form:form>
            </div>
        </div>

    </div>
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