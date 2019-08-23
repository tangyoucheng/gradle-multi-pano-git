<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic03.Ic0302Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>素材信息编辑画面</title>
</imui:head>

<script src="cdic/js/ic03/ic0302.js"></script>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>素材一览</h1>
</div>

<!-- upload処理 START -->
<div id="editMaterials"></div>
<!-- upload処理 END -->
<imui:dialog id="ic0302imagePreview" modal="true" autoOpen="false" width="400">
  <img id="imagePreview" src="" style="width: 100%;height: auto;display: none;" />
</imui:dialog>

<!-- コンテンツエリア -->
<div id="ic0302Div" class="imui-form-container" style="width:88%;">
     <imui:dialog id="ic0302MoveMaterialFinish" modal="true" autoOpen="false" onClose="doAjaxPage">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>素材转移成功！</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-move-finished-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <form id="ic0302Form" action="ic03/ic0302"  method="post" >
        <input type="hidden" name="selectedHotspotId" value="${selectedHotspotId}"/>
        <input type="hidden" name="expositionId" id="expositionId" value="${expositionId}"/>
        <input type="hidden" name="expositionName" id="expositionName" value="${expositionName}"/>
        <input type="hidden" name="selectedMaterialId" id="selectedMaterialId" value= "${selectedMaterialId}" />
        <input type="hidden" id="selectedMaterialPath" value= "" />
        <input type="hidden" name="ic0302PanoramaId" id="ic0302PanoramaId"  value="${ic0302PanoramaId}"/>
        <input type="hidden" id="openFromIc0104" name="openFromIc0104" value= "${openFromIc0104}" />
        
            <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251)">
                <!-- 検索条件 -->
                <h2>检索条件</h2>
            </div>
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div>
            
            <table class="imui-form-search-condition">
               <tbody >
                   <c:if test="${not empty openFromIc0104}">
                       <tr>
                            <th style="width:12%;"><label>展览:</label></th>
                            <td style="width:auto;vertical-align:middle" colspan="3">
                                ${expositionName}
                            </td>
                       </tr>
                   </c:if>
                   <tr>
                        <th class="" style="width:12%;"><label>ID ：</label></th>
                        <td style="width:auto;vertical-align:middle" >
                            <imui:textbox id="materialIdtextbox" name="materialId" value="${materialId}" autofocus="true"/>
                        </td>
                        <th class="" style="width:12%;"><label>名称 ：</label></th>
                        <td style="width:auto;vertical-align:middle" >
                            <imui:textbox id="materialNametextbox" name="materialName" value="${materialName}" autofocus="true"/>
                        </td>
                    </tr>
                        <tr>
                            <th class="" style="width:12%;"><label>种类 ：</label></th>
                            <td style="width:auto;vertical-align:middle" >
                                <fw:select id="materialTypeId" name="materialTypeId" selectedValue="${materialTypeId}"  list="${materialTypeSelectInfo}" blank="false" style="width: 155px;"/>
                            </td>
                            <c:if test="${not empty openFromIc0104}">
                                <th class="" style="width:12%;"><label>归属:</label></th>
                                <td style="width:auto;vertical-align:middle">
                                    <fw:radio name="materialBelongType" selectedValue="${materialBelongType}" list="${materialBelongTypeList}" />
                                </td>
                            </c:if>
                        </tr>
               </tbody>
            </table>
            <div class="align-R" >
                <input id="move-material-button" value="转移" class="imui-medium-button" type="button" />
                &nbsp;&nbsp;
                <!-- 検索 -->
                <input id="search-button" value='检索' class="imui-medium-button" type="button" />
                &nbsp;&nbsp;
                <input id="ic0302-back-button" value='返回' class="imui-medium-button" type="button" />
            </div>
    
            <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251); margin-top: 10px" >
                       <h2>素材一览</h2>
            </div >
            <div style="width: 100%; height: 370px; margin: 0px; overflow: hidden;">  
                    <c:if test="${not empty openFromIc0104}">
                        <div class="align-R" style="margin-bottom: 2px;">
                            <input type="button" value="全选" class="imui-button imui-running-button " onclick="selectAllChkbox()"/>
                            <input type="button" value="全解除" class="imui-button imui-running-button " onclick="cancelAllChkbox()"/>
                        </div>            
                    </c:if>
                   <table id="ic0302ListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                   <thead>
                       <tr>
                           <th class="align-C valign-M" style="width:50px;" >编辑</th>
                           <th class="align-C valign-M" style="width:15%;" >ID</th>
                           <th class="align-C valign-M" style="width:16%;" >名称</th>
                           <th class="align-C valign-M" style="width:15%;">种类</th>
                           <th class="align-C valign-M" style="width:auto;" >说明</th>
                           <c:if test="${not empty openFromIc0104}">
                               <th class="align-C valign-M" style="width:7%;" >许可</th>
                           </c:if>
                       </tr>
                   </thead>
                   <tbody>
                        <c:forEach items="${materialInfo}" var="searchResult" varStatus="searchResultStatus">
                           <tr id="ic0302Tr_${searchResultStatus.index}"  style="height:10px;cursor: pointer;">
                               <td class="align-C valign-M" style="width:40px; cursor: pointer;" 
                                    onclick="editMaterial('${searchResult.materialId}','${searchResult.materialName}')">
                                   <span class="im-ui-icon-common-16-settings"></span>
                               </td>
                               <td title="${searchResult.materialId}" class="align-l valign-M" >${searchResult.materialId}</td>
                               <td title="${searchResult.materialName}"  class="align-l valign-M">${searchResult.materialName}</td>
                               <td title="${searchResult.materialTypeId}" class="align-l valign-M" >${searchResult.materialTypeId}</td>
                               <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                               <c:if test="${not empty openFromIc0104}">
	                               <td class="align-C valign-M">
	                                   <input id="${searchResult.materialId}_chkbox" type="checkbox" name="materialInfo[${searchResultStatus.index}].isSelected" value="${searchResult.isSelected }" onclick="doSelect('${searchResult.materialId}_chkbox');"/>
	                                   <input type="hidden" name="materialInfo[${searchResultStatus.index}].materialId" value="${searchResult.materialId}"/>
	                                   <input type="hidden" name="materialInfo[${searchResultStatus.index}].materialTypeId" value="${searchResult.materialTypeId}"/>
	                               </td>
                               </c:if>
                           </tr>
                       </c:forEach>
                   </tbody>
                   </table>
           </div>
           <div id="ic0302pageDiv">
               <fwui:page formId="ic0302Form" formAction="ic03/ic0302/doPage" ajaxPageMethod="doAjaxPage" uniqueFlag="true"/>
           </div>
    </form>
</div>

<!-- submit处理 START -->
<!-- 返回处理 -->
<form method="POST" action="home" id="back-form"></form>

<form id="back-to-ic0104" method="POST" action="ic01/ic0104/doSearchFromIc0302">
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" id="backToIc0104PanoramaId" name="panoramaId"  value="${ic0302PanoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
</form>
<form id="back-to-vr0104" method="POST" action="vr01/vr0104/doSearchFromIc0302">
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" id="backToVr0104PanoramaId" name="panoramaId"  value="${ic0302PanoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
</form>
<!-- submit处理 END -->
