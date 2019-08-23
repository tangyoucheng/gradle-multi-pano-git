<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr02.Vr0204Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>场景切换热点编辑画面</title>
</imui:head>

<script src="product_vr/js/vr02/vr0204.js"></script>

<!-- コンテンツエリア -->
<div class="imui-form-container" style="width:95%;">

     <imui:dialog id="vr0204Finish" modal="true" autoOpen="false" onClose="vr0204DoBack">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>热点链接编辑完成。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-vr0204Finish" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>  
     <imui:dialog id="vr0204imagePreview" modal="true" autoOpen="false" width="400" >
         <div id="vr0204Pano" style=" width:100%;height:auto;display: none;" >
          </div>
    </imui:dialog>
    
    
    <form id="vr0204Form" action="vr02/vr0204"  method="post" >
        <input type="hidden" name="selectedHotspotId" value="${selectedHotspotId}"/>
        <input type="hidden" name="currentPanoramaId" id="currentPanoramaId" value="${currentPanoramaId}"/>
        <input type="hidden" name="expositionId" id="expositionId" value="${expositionId}"/>
        <input type="hidden" name="selectedPanoramaId" id="selectedPanoramaId" value="${selectedPanoramaId}"/>
        <input type="hidden" name="positionAthForEdit" id="positionAthForEdit" value="${positionAthForEdit}"/>
        <input type="hidden" name="positionAtvForEdit" id="positionAtvForEdit" value="${positionAtvForEdit}"/>
        <input type="hidden" name="vr0204LastHotspotId" id="vr0204LastHotspotId" value="${vr0204LastHotspotId}"/>
        <input type="hidden" name="vr0204CurrentHotspotId" id="vr0204CurrentHotspotId" value="${vr0204CurrentHotspotId}"/>
        <input type="hidden" name="expoGoSceneTooltip" id="expoGoSceneTooltip" value="${expoGoSceneTooltip}"/>
        
        <div class="materiaContainer">
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div> 
            <div class="imui-gadget" style="margin-top: 8px;">
                <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251);">
                    <h2>全景图一览</h2>
                </div >
                
                <div style="width: 100%; height: 370px; margin: 0px; overflow: hidden;">
                   
                    <table id="vr0204ListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                    <thead>
                        <tr>
                            <th class="align-C valign-M" style="width:20%;" >ID</th>
                            <th class="align-C valign-M" style="width:20%;" >名称</th>
                            <th class="align-C valign-M" style="width:auto;" >说明</th>
                            <th class="align-C valign-M" style="width:55px;" >预览</th>
                        </tr>
                    </thead>
                    <tbody>
                         <c:forEach items="${panoramaList}" var="searchResult" varStatus="searchResultStatus">
                            <tr id="vr0204Tr_${searchResultStatus.index}" onclick="setSelectedTrColor(this,'${searchResult.panoramaId}','${searchResult.panoramaName}');" style="height:10px;cursor: pointer;">
                                <td title="${searchResult.panoramaId}" class="align-l valign-M" >${searchResult.panoramaId}</td>
                                <td title="${searchResult.panoramaName}"  class="align-l valign-M">${searchResult.panoramaName}</td>
                                <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                                <td class="align-C valign-M" style="width:55px;" >
                                    <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${searchResult.panoramaPath}')"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    </table>
                </div>
                <fwui:page formId="vr0204Form" formAction="vr02/vr0204/doPage" ajaxPageMethod="doAjaxPage" uniqueFlag="true"/>
                <div class="align-R" style="padding-top: 5px;">
                    <!-- 确定 -->
                    <input id="vr0204-confirm-button" value='确定' class="imui-medium-button" type="button" />
                </div>
            </div>   
        </div>
    </form>
</div>
<!-- submit处理 START -->
<form method="POST" action="vr01/vr0104/doSearchFromVr0204" id="vr0104-refresh-from-vr0204">
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${currentPanoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="selectedHotspotId" value= "${selectedHotspotId}" />
    <input type="hidden" name="positionAthForEdit"  value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit"  value="${positionAtvForEdit}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${vr0204LastHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${vr0204CurrentHotspotId}"/>
</form>
<!-- submit处理 END -->

