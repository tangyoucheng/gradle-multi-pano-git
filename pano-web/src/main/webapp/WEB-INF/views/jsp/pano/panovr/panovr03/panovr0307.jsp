<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr03.Vr0307Form"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>场景地图设置画面</title>
</imui:head>

<script src="product_vr/js/vr03/vr0307.js"></script>

<!-- コンテンツエリア -->
<div class="imui-form-container" style="width:95%;">

     <imui:dialog id="vr0307Finish" modal="true" autoOpen="false" onClose="vr0307DoBack">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>热点场景设置成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-vr0307Finish" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <imui:dialog id="vr0307imagePreview" modal="true" autoOpen="false" width="400" >
         <div id="vr0107Pano" style=" width:100%;height:auto;display: none;" >
          </div>
    </imui:dialog>
    
    <form id="vr0307Form" action="vr03/vr0307"  method="post" >
        <input type="hidden" name="selectedPanoramaId" id="selectedPanoramaId" value= "${selectedPanoramaId}" />
        <input type="hidden" name="selectedHotspotId" id="selectedHotspotId" value= "${selectedHotspotId}" />
        <input type="hidden" name="expositionId" id="expositionId" value= "${expositionId}" />
        <input type="hidden" name="expositionName" id="expositionName" value= "${expositionName}" />
        <input type="hidden" name="currentPanoramaId" id="currentPanoramaId" value= "${currentPanoramaId}" />
        <input type="hidden" id="previewPanoramaPath" value= "" />

        <div class="materiaContainer">
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div> 
            <div class="imui-gadget" style="margin-top: 8px;">
                <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251);">
                    <h2>场景一览</h2>
                </div>
                
                <div style="width: 100%; height: 400px; margin: 0px; overflow: hidden;">
                    <table id="vr0307ListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                    <thead>
                        <tr>
                            <th class="align-C valign-M" style="width:20%;" >名称</th>
                            <th class="align-C valign-M" style="width:auto;" >说明</th>
                            <th class="align-C valign-M" style="width:55px;" >预览</th>
                        </tr>
                    </thead>
                    <tbody>
                         <c:forEach items="${panoramaInfo}" var="searchResult" varStatus="searchResultStatus">
                            <tr id="vr0307Tr_${searchResultStatus.index}" onclick="setSelectedTrColor(this,'${searchResult.panoramaId}');" style="height:10px;cursor: pointer;">
                                <td title="${searchResult.panoramaName}"  class="align-l valign-M">${searchResult.panoramaName}</td>
                                <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                                <td class="align-C valign-M" style="width:55px;" >
                                    <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${searchResult.panoramaId}')"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    </table>
                </div>
                <fwui:page formId="vr0307Form" formAction="vr03/vr0307/doPage" ajaxPageMethod="doAjaxPage" uniqueFlag="true"/>
                <div class="align-R" style="padding-top: 5px;">
                    <!-- 确定 -->
                    <input id="vr0307-confirm-button" value='确定' class="imui-medium-button" type="button" />
                </div>
            </div>   
        </div>
    </form>
</div>

<!-- submit处理 START -->
<form method="POST" action="vr01/vr0105/doSearchFromVr0307" id="vr0105-refresh-from-vr0307">
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" id="panoramaIdFormVr0307" value= "${currentPanoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
</form>
<!-- submit处理 END -->
