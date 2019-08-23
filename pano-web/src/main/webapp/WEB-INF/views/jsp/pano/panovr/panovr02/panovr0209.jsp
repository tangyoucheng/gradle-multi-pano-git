<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr02.Vr0209Form"%>
<%@ page import="cn.com.cdic.cnst.CommonConstants"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>
<imui:head>
    <title>基本信息编辑</title>
</imui:head>
<script src="product_vr/js/vr02/vr0209.js"></script>

<div class="imui-form-container" style="width: 95%;">
    <form id="vr0209Form" action="vr02/vr0209"  method="post">
        <input type="hidden" id="panoramaIdForVr0209" name="panoramaIdForVr0209" value="${panoramaIdForVr0209}"/>
        <input type="hidden" id="expositionIdForVr0209" name="expositionIdForVr0209" value="${expositionIdForVr0209}"/>
        <input type="hidden" name="expositionName" value="${expositionName}"/>
        <input type="hidden" name="vr0209hotspotId" id="vr0209hotspotId" value="${vr0209hotspotId}"/>
        <input type="hidden" name="panoramaName" value="${panoramaName}"/>
        <input type="hidden" name="panoramaPath" id="vr0209panoramaPath" value="${panoramaPath}"/>
        <input type="hidden" name="hotspotScale" id="hotspotScale" value="${hotspotScale}"/>
        <input type="hidden" name="vr0209HotspotType" id="vr0209HotspotType" value="${vr0209HotspotType}"/>
        <input type="hidden" name="hotspotAth" id="hotspotAth" value="${hotspotAth}"/>
        <input type="hidden" name="hotspotAtv" id="hotspotAtv" value="${hotspotAtv}"/>
        <input type="hidden" name="positionAthForEdit"  value="${positionAthForEdit}"/>
        <input type="hidden" name="positionAtvForEdit"  value="${positionAtvForEdit}"/>
        <input type="hidden" name="vr0209flowFileId" id="vr0209flowFileId" value="${vr0209flowFileId}"/>
        <input type="hidden" name="vr0209flowFileType" id="vr0209flowFileType" value="${vr0209flowFileType}"/>
        <input type="hidden" name="vr0209flowFilePath" id="vr0209flowFilePath" value="${vr0209flowFilePath}"/>
        <input type="hidden" name="vr0209flowFileInfo" id="vr0209flowFileInfo" value="${vr0209flowFileInfo}"/>
        <input type="hidden" name="vr0209layerX" id="vr0209layerX" value="${vr0209layerX}"/>
        <input type="hidden" name="vr0209layerY" id="vr0209layerY" value="${vr0209layerY}"/>
        <input type="hidden" name="commandTypeFromVr0105" id="commandTypeFromVr0105" value='${commandTypeFromVr0105}'/>
        <input type="hidden" name="vr0209TheLastedSceneHotspotId" id="vr0209TheLastedSceneHotspotId" value='${vr0209TheLastedSceneHotspotId}'/>
        <input type="hidden" name="recommendInfo" id="recommendInfo" value="${recommendInfo}"/>
        <input type="hidden" name="vr0209RecommendHotspotId" id="vr0209RecommendHotspotId" value="${vr0209RecommendHotspotId}"/>
        <input type="hidden" name="lastedHotspotIdFrom0105"  value="${lastedHotspotIdFrom0105}"/>
        <input type="hidden" name="currentHotspotIdFrom0105" value="${currentHotspotIdFrom0105}"/>
        <input type="hidden" name="expoRecommendInfo" id="expoRecommendInfo" value="${expoRecommendInfo}"/>
        <input type="hidden" name="vr0203HotspotName" id="vr0203HotspotName" value="${vr0203HotspotName}"/>
        <input type="hidden" name="vr0203HotspotAth" id="vr0203HotspotAth" value="${vr0203HotspotAth}"/>
        <input type="hidden" name="vr0203HotspotAtv" id="vr0203HotspotAtv" value="${vr0203HotspotAtv}"/>
        
        <input type="hidden" name="vr0203MusicHotspot" id="vr0203MusicHotspot" value="${vr0203MusicHotspot}"/>
        <input type="hidden" name="firsthotspotImageIdVr0203" id="firsthotspotImageIdVr0203" value="${firsthotspotImageIdVr0203}"/>
        <input type="hidden" name="seconfhotspotImageIdVr0203" id="seconfhotspotImageIdVr0203" value="${seconfhotspotImageIdVr0203}"/>
        <input type="hidden" name="firstSortKeyVr0203" id="firstSortKeyVr0203" value="${firstSortKeyVr0203}"/>
        <input type="hidden" name="secondSortKeyVr0203" id="secondSortKeyVr0203" value="${secondSortKeyVr0203}"/>
        <input type="hidden" name="seconfhotspotImageUrlVr0203" id="seconfhotspotImageUrlVr0203" value="${seconfhotspotImageUrlVr0203}"/>
        
        <input type="hidden" id="hotspotImageInfoJson" name="hotspotImageInfoJson" value='${hotspotImageInfoJson}'/>
        <input type="hidden" id="firstImageInfoJson" name="firstImageInfoJson" value='${firstImageInfoJson}'/>
        <input type="hidden" id="secondImageInfoJson" name="secondImageInfoJson" value='${secondImageInfoJson}'/>
        
            
        <div id="buttonsTitleDiv" class="imui-chapter-title" style="background-color:rgb(251, 251, 251); margin-top: 10px" >
           <h2>工具条按钮一览</h2>
        </div >  
        
        <div id="sizeTitleDiv" class="imui-chapter-title" style="background-color:rgb(251, 251, 251); margin-top: 10px" >
           <h2>大小选择</h2>
        </div >
        
        <div id="editRecommendTitleDiv" class="imui-chapter-title" style="background-color:rgb(251, 251, 251); margin-top: 10px" >
           <h2>编辑公共信息</h2>
        </div >
        
        <div id="buttonslistDiv" style="width: 100%; height: 360px; margin: 0px; overflow-y:scroll">
            <input type="button" value="全选" id="selectAll" class="imui-button imui-running-button" />
            <input type="button" value="全解除" id="cancelAll" class="imui-button imui-running-button" />
             <table id="vr0209ListDataTable" style="width: 100%; margin-top:2px;" border="1" class="imui-table-sort eq_nowrap_hidden" >
             <thead>
                 <tr>
                     <th class="align-C valign-M" style="width:30px;" >选择</th>
                     <th class="align-C valign-M" style="width:auto;" >按钮名称</th>
                 </tr>
             </thead>
             <tbody>
                  <c:forEach items="${buttonsInfo}" var="searchResult" varStatus="searchResultStatus">
                     <tr id="vr0205Tr_${searchResultStatus.index}" style="height:10px;">
                         <td align="center"><input type="checkbox" id="${searchResult.buttonName}" name="chkBox" value="${searchResult.buttonName}"/></td>
                         <td title="${searchResult.buttonName_CN}"  class="align-l valign-M">${searchResult.buttonName_CN}</td>
                     </tr>
                 </c:forEach>
             </tbody>
             </table>
         </div>  
        <table class="imui-form">
            <tr id="sizeTr">
                <c:if test='${not empty vr0209flowFileId}'>
                    <th style="width:15%;"><label>浮动层大小</label></th>
                </c:if>
                <c:if test='${not empty vr0209hotspotId}'>
                    <th style="width:15%;"><label>热点大小</label></th>
                </c:if>
                <td colspan="3">
                    <fw:select id="hotspotSizeInfo" name="hotspotSizeInfo"  selectedValue="${hotspotScale}" list="${hotspotSizeInfo}" onChange="selectRefresh()" blank="true" style="width: 180px;"/>
                </td>
            </tr>

            <tr id="recommendTr" >
                <th style="width:15%;"><label>推荐路线点设定</label></th>
                <td colspan="3" >
                    <input type="checkbox" id="recommendFlag" name="recommendFlag" value="1"/>设为推荐路线点
                </td>
            </tr>
            <!-- 推荐路线信息为文字时
            <tr id="editRecommendInfoTr" >
                <th style="width:18%;"><label class="imui-required">编辑推荐路线信息</label></th>
                <td colspan="3" >
                   <imui:textbox id="recommendInfoText" name="expoRecommendInfo" value="${expoRecommendInfo}" style="width:98%;" autofocus="true" maxLength="100" />
                </td>
            </tr> -->
            
             <tr id="tooltipTr" >
                <th style="width:16%;"><label>提示信息</label></th>
                <td colspan="3" >
                   <imui:textbox id="expoHotspotTooltipInfo" name="expoHotspotTooltipInfo" value="${expoHotspotTooltipInfo}" style="width:98%;" autofocus="true" maxLength="100" />
                </td>
            </tr>
            <tr id="editExpoGoSceneInfoTr" >
                <th style="width:18%;"><label >切换场景的<br>公共提示信息</label></th>
                <td colspan="3" >
                   <imui:textArea id="ExpoGoSceneInfoText" name="ExpoGoSceneInfo" value="${ExpoGoSceneInfo}" rows="2" style="width:98%;overflow:hidden"></imui:textArea>
                </td>
            </tr>
        </table>
        
        <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251); margin-top: 10px" >
           <h2>推荐路线效果预览</h2>
        </div >  
        <div id="vr0209Pano" style=" width:100%;height:380px;" >
        </div>
        <div class="imui-operation-parts">
              <input type="button" value="确定" id="button-vr0209-confirm" class="imui-medium-button" />
        </div>
     </form>
    </div>
    <form id="back-form" method="POST" action="vr01/vr0104/">
   
    </form>
 <!-- 保存和取消处理 -->
<form id="back-to-vr0104" method="POST" action="vr01/vr0104/doSearchFromVr0209">
   <input type="hidden" name="panoramaId"  id="panoramaIdTo0104" value="${panoramaIdForVr0209}"/>
   <input type="hidden" name="expositionId" id="expositionIdTo0209" value="${expositionIdForVr0209}"/>
   <input type="hidden" name="expositionName"  value="${expositionName}"/>  
   <input type="hidden" name="positionAthForEdit" id="0209BackAth" value="${positionAthForEdit}"/>
   <input type="hidden" name="positionAtvForEdit" id="0209BackAtv" value="${positionAtvForEdit}"/>
   <input type="hidden" name="currentRecommendHotspotId" id="recommendHotspotIdBackTo0104" value="${vr0209RecommendHotspotId }"/>
   <input type="hidden" name="theLastedSceneHotspotId" value="${vr0209TheLastedSceneHotspotId}"/>
</form>
<form id="back-to-vr0105" method="POST" action="vr01/vr0105">
   <input type="hidden" name="panoramaId"  id="panoramaIdTo0104" value="${panoramaIdForVr0209}"/>
   <input type="hidden" name="expositionId" id="expositionIdTo0209" value="${expositionIdForVr0209}"/>
   <input type="hidden" name="expositionName"  value="${expositionName}"/>  
   <input type="hidden" name="theLastedSceneHotspotId"  value="${lastedHotspotIdFrom0105}"/>
   <input type="hidden" name="currentRecommendHotspotId" value="${currentHotspotIdFrom0105}"/>
</form>
