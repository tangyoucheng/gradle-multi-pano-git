<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic03.Ic0305Form"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>场景导航图设置画面</title>
</imui:head>

<script src="cdic/js/ic03/ic0305.js"></script>
<div id="editMap"></div>
<!-- コンテンツエリア -->
<div class="imui-form-container" style="width:95%;">

     <imui:dialog id="ic0305Finish" modal="true" autoOpen="false" onClose="ic0305DoBack">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>场景导航图设置成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-ic0305Finish" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <imui:dialog id="ic0305imagePreview" modal="true" autoOpen="false" width="385" >
         <img id="imagePreview" src="" style="width: 100%;height: auto;display: none;" />
    </imui:dialog>
    
    <form id="ic0305Form" action="ic03/ic0305"  method="post" >
        <input type="hidden" name="selectedExpositionMapId" id="selectedExpositionMapId" value= "${selectedExpositionMapId}" />
        <input type="hidden" name="expositionId" value= "${expositionId}" />
        <input type="hidden" name="expositionName" value= "${expositionName}" />
        <input type="hidden" name="panoramaId" value= "${panoramaId}" />
        <input type="hidden" name="lasthotspotIdFrom0106" value= "${lasthotspotIdFrom0106}" />
        <input type="hidden" name="currenthotspotIdFrom0106" value= "${currenthotspotIdFrom0106}" />
        <input type="hidden" name="existedExpositionMapId" id="existedExpositionMapId" value="${existedExpositionMapId}"/>
        <div class="materiaContainer">
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div> 
            <div class="imui-gadget" style="margin-top: 8px;">
                <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251);">
                    <h2>导航图一览</h2>
                </div>
                
                <div style="width: 100%; height: 340px; margin: 0px; overflow: hidden;">
                    <table id="ic0305ListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                    <thead>
                        <tr>
                            <th class="align-C valign-M" style="width:20%;" >名称</th>
                            <th class="align-C valign-M" style="width:auto;" >说明</th>
                            <th class="align-C valign-M" style="width:55px;" >编辑</th>
                            <th class="align-C valign-M" style="width:55px;" >预览</th>
                        </tr>
                    </thead>
                    <tbody>
                         <c:forEach items="${expositionMapInfo}" var="searchResult" varStatus="searchResultStatus">
                            <tr id="ic0305Tr_${searchResultStatus.index}" onclick="setSelectedTrColor(this,'${searchResult.expositionMapId}');" style="height:10px;cursor: pointer;">
                                <td title="${searchResult.expositionMapName}"  class="align-l valign-M">${searchResult.expositionMapName}</td>
                                <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                                  <td class="align-C valign-M" style="width:55px;" >
                                    <input type="button" value="编辑" class="imui-button imui-running-button" onclick="doEditMap('${searchResult.expositionMapId}')"/>
                                </td>
                                <td class="align-C valign-M" style="width:55px;" >
                                    <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${searchResult.expositionMapId}')"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    </table>
                </div>
                <c:if test="${not empty expositionMapInfo}">
	                <fwui:page formId="ic0305Form" formAction="ic03/ic0305/doPage" ajaxPageMethod="doAjaxPage" uniqueFlag="true"/>
	                <div class="align-R" style="padding-top: 5px;">
	                    <!-- 确定 -->
	                    <input id="ic0305-confirm-button" value='确定' class="imui-medium-button" type="button" />
	                </div>
                </c:if>
            </div>   
        </div>
    </form>
</div>

<!-- submit处理 START -->
<form method="POST" action="ic01/ic0106/doSearchFromIc0305" id="ic0106-refresh-from-ic0305">
    <input type="hidden" name="theLastedSceneHotspotId" value= "${lasthotspotIdFrom0106}" />
    <input type="hidden" name="currentRecommendHotspotId" value= "${currenthotspotIdFrom0106}" />
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${panoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="expositionMapId" id="expositionMapIdFromIc0305"  />
</form>

<form method="POST" action="vr01/vr0106/doSearchFromIc0305" id="vr0106-refresh-from-ic0305">
    <input type="hidden" name="theLastedSceneHotspotId" value= "${lasthotspotIdFrom0106}" />
    <input type="hidden" name="currentRecommendHotspotId" value= "${currenthotspotIdFrom0106}" />
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${panoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="expositionMapId" id="expositionMapIdFromVr0305"  />
</form>
<!-- submit处理 END -->
