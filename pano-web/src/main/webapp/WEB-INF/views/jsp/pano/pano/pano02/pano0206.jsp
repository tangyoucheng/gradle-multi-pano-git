<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic02.Ic0206Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>热点素材信息编辑画面</title>
</imui:head>

<script src="cdic/js/ic02/ic0206.js"></script>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>展览场景一览</h1>
</div>

<!-- upload処理 START -->
<div id="updatePanoramaPage"></div>
<!-- upload処理 END -->
<imui:dialog id="ic0206imagePreview" modal="true" autoOpen="false" width="400">
      <div id="ic0206PreviewPano" style=" width:100%;height:300px;display:none;" >
    </div>
</imui:dialog>

<!-- コンテンツエリア -->
<div class="imui-form-container" style="width:95%;">
    
    <form id="ic0206Form" action="ic02/ic0206"  method="post" >
        <input type="hidden" name="expositionId" id="expositionId" value="${expositionId}"/>
        <input type="hidden" name="expositionName" id="expositionName" value="${expositionName}"/>
        <input type="hidden" name="ic0206PanoramaId" id="ic0206PanoramaId"  value="${ic0206PanoramaId}"/>
        <input type="hidden" name="panoramaInfoJson" id="panoramaInfoJson"  value="${panoramaInfoJson}"/>
        <input type="hidden" name="theLastedSceneHotspotId" id="ic0104LastedSceneHotspotId" value="${theLastedSceneHotspotId}"/>
        <input type="hidden" name="currentRecommendHotspotId" id="ic0104CurrentHotspotId" value="${currentRecommendHotspotId}"/>
        <input type="hidden" name="expositionMapId"  value="${expositionMapId}"/>
            <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251)">
                <!-- 検索条件 -->
                <h2>检索条件</h2>
            </div>
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div>
            
            <table class="imui-form-search-condition">
               <tbody >
                   <tr>
                        <th class="" style="width:15%;"><label>场景ID ：</label></th>
                        <td style="width:35%;vertical-align:middle" >
                            <imui:textbox id="textbox" name="panoramaId" value="${panoramaId}" autofocus="true"/>
                        </td>
                        <th class="" style="width:15%;"><label>场景名 ：</label></th>
                        <td style="width:35%;vertical-align:middle" >
                            <imui:textbox id="textbox" name="panoramaName" value="${panoramaName}" autofocus="true"/>
                        </td>
                    </tr>
               </tbody>
            </table>
            <div class="align-R" >
                <!-- 検索 -->
               <input id="search-button" value='检索' class="imui-medium-button" type="button" />
                &nbsp;&nbsp;
                <input id="ic0206-back-button" value='返回' class="imui-medium-button" type="button" />
            </div>
    
            <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251); margin-top: 10px" >
               <h2>场景一览</h2>
            </div >
            <div style="width: 100%; height: 379px; margin: 0px; overflow-y:scroll">              
                   <table id="ic0206ListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                   <thead>
                       <tr>
                           <th class="align-C valign-M" style="width:10%;" >场景编辑</th>
                           <th class="align-C valign-M" style="width:15%;" >场景ID</th>
                           <th class="align-C valign-M" style="width:15%;" >场景名称</th>
                           <th class="align-C valign-M" style="width:5%;" >场景显示顺序</th>
                           <th class="align-C valign-M" style="width:auto;" >说明</th>
                           <th class="align-C valign-M" style="width:55px;" >预览</th>
                       </tr>
                   </thead>
                   <tbody>
                        <c:forEach items="${panoramaInfo}" var="searchResult" varStatus="searchResultStatus">
                           <tr id="${searchResult.panoramaId}" onclick="setTrColor('${searchResult.panoramaId}');" style="height:10px;cursor: pointer;">
                               <td class="align-C valign-M" style="width:20px; cursor: pointer;" 
                                    onclick="editPanorama('${searchResult.panoramaId}')">
                                   <span class="im-ui-icon-common-16-settings"></span>
                               </td>
                               <td title="${searchResult.panoramaId}" class="align-l valign-M" >${searchResult.panoramaId}</td>
                               <td title="${searchResult.panoramaName}"  class="align-l valign-M">${searchResult.panoramaName}</td>
                               <td title="${searchResult.panoramaSortKey}"  id="${searchResult.panoramaId}_sortKey" class="align-l valign-M">${searchResult.panoramaSortKey}</td>
                               <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                               <td class="align-C valign-M" style="width:55px;" >
                                   <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${searchResult.panoramaPath}')"/>
                               </td>
                           </tr>
                       </c:forEach>
                   </tbody>
                   </table>
           </div>
           <c:if test="${not empty panoramaInfo}">
               <div class="align-R" style="margin: 10px" id="confirmDiv">
                    <input id="up-button" value='顺序上移' class="imui-medium-button" type="button" />
                    <input id="down-button" value='顺序下移' class="imui-medium-button" type="button" />
                    <input id="confirm-button" value='确定' class="imui-medium-button" type="button" />
               </div>
           </c:if>
    </form>
</div>

<!-- submit处理 START -->
<form id="back-to-ic0104" method="POST" action="ic01/ic0104/doSearchFromIc0206">
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" id="backToIc0104PanoramaId" name="panoramaId"  value=""/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- submit处理 END -->
<!-- 删除可视化信息处理 -->
<form id="ic0206-delete-map" method="POST" action="ic02/ic0206/doDeleteScene">
    <input type="hidden" name="ic0206PanoramaId" id="ic0206PanoramaId"  value="${ic0206PanoramaId}"/>
    <input type="hidden" name="expositionId" value='${expositionId}' />
    <input type="hidden" name="panoramaIdForDelete" id="deletePanorama" value='' />
    <input type="hidden" name="expositionMapId" value="${expositionMapId}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- 从编辑画面返回的处理 -->
<form id="ic0206-refresh-from-ic0202" method="POST" action="ic02/ic0206/doSearchFromIc0202">
    <input type="hidden" name="ic0206PanoramaId" id="ic0206PanoramaId"  value="${ic0206PanoramaId}"/>
    <input type="hidden" name="expositionId" id="expositionId-from-ic0202" value="${expositionId}" />
    <input type="hidden" name="expositionName" id="expositionName-from-ic0202" value="${expositionName}" />
    <input type="hidden" name="theLastedSceneHotspotId" id="hotspotIdFromIc0102" value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" id="currentHotspotIdFromIc0102" value="${currentRecommendHotspotId}"/>
</form>
<!-- 从保存排序状态的画面返回 -->
<form id="ic0206-refresh-from-edit-sortkey" method="POST" action="ic02/ic0206/doSearchFromEditSortKey">
    <input type="hidden" name="ic0206PanoramaId" id="ic0206PanoramaId"  value="${ic0206PanoramaId}"/>
    <input type="hidden" name="expositionId" id="expositionId-from-ic0202" value="${expositionId}" />
    <input type="hidden" name="expositionName" id="expositionName-from-ic0202" value="${expositionName}" />
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>