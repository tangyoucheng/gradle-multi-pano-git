<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic02.Ic0205Form"%>
<%@ page import="java.util.*"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>热点素材信息编辑画面</title>
</imui:head>

<script src="cdic/js/ic02/ic0205.js"></script>

<!-- コンテンツエリア -->
<div id="ic0205Div" class="imui-form-container" style="width:95%;">

     <imui:dialog id="ic0205Finish" modal="true" autoOpen="false" onClose="ic0205DoBack">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>热点素材信息编辑完成。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-ic0205Finish" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <imui:dialog id="ic0205imagePreview" modal="true" autoOpen="false" width="400">
       <img id="imagePreview" src="" style="width: 100%;height: auto;display: none;" />
    </imui:dialog>
    <imui:dialog id="ic0205soundPreview" modal="true" autoOpen="false" width="400" onClose="ic0205StopSound">
        <div id="ic0205SoundPreviewDiv" style="width: 100%; height: auto; display: none;"></div>
    </imui:dialog>
    <imui:dialog id="ic0205videoPreview" modal="true" autoOpen="false" width="600" height="320" onClose="stopVideo">
       <div id="videoPreview" style="width:100%;height:280px;">
       </div>
    </imui:dialog>
    <form id="ic0205Form" action="ic02/ic0205"  method="post" >
        <input type="hidden" name="selectedHotspotId" value="${selectedHotspotId}"/>
        <input type="hidden" name="expositionId" id="expositionId" value="${expositionId}"/>
        <input type="hidden" name="selectedMaterialId" id="selectedMaterialId" value= "${selectedMaterialId}" />
        <input type="hidden" name="hotspotUrlInfoJson" id="hotspotUrlInfoJson" value= "${hotspotUrlInfoJson}" />
        <input type="hidden" name="isPolygon" id="isPolygon" value= "${isPolygon}" />
        <input type="hidden" name="polygonFillcolor" id="polygonFillcolor" value= "${polygonFillcolor}" />
        <input type="hidden" name="polygonFillalpha" id="polygonFillalpha" value= "${polygonFillalpha}" />
        <input type="hidden" name="positionAthForEdit" id="positionAthForEdit" value="${positionAthForEdit}"/>
        <input type="hidden" name="positionAtvForEdit" id="positionAtvForEdit" value="${positionAtvForEdit}"/>
        <input type="hidden" name="ic0205TheLastedSceneHotspotId"  value="${ic0205TheLastedSceneHotspotId}"/>
        <input type="hidden" name="ic0205RecommendHotspotId" value="${ic0205RecommendHotspotId}"/>
        <input type="hidden" name="ic0104urlType" id="urlType" value="${urlType}"/>
        <input type="hidden" name="existedSoundId" id="existedSoundId" value="${existedSoundId}"/>
        <input type="hidden" name="existedVideoId" id="existedVideoId" value="${existedVideoId}"/>
        <input type="hidden" name="theCommandFromRadiobox" id="theCommandFromRadiobox" value="${theCommandFromRadiobox}"/>

        <div class="materiaContainer">
            <div style="font-weight:bold;color:red;margin-bottom: 5px;">
               <html:errors ></html:errors>
            </div> 
            <div class="imui-gadget" style="margin-top: 8px;">
                <div class="imui-chapter-title" style="background-color:rgb(251, 251, 251);">
                    <h2>素材一览</h2>
                </div >
                <div class="vtourContainer">
                    <table class="imui-form-search-condition">
                         <tbody >
                              <tr>
                                 <th style="width: 20%;"><label >场景图上热点提示信息 :</label>
                                 </th>
                                 <td>
                                    <imui:textbox id="texbox" name="hotspotTooltip" value="${hotspotTooltip}" style="width:98%;" autofocus="true"/>               
                                 </td>
                             </tr>
                             <tr>
                                 <c:if test="${isPolygon == 'true' }">
                                 <th style="width: 20%;"><label >设置多边形颜色及透明度 :</label>
                                 </th>
                                     <td>
                                        <input type="text" id="ic0205_polygon_property" data-opacity="${polygonFillalpha}" value="${polygonFillcolor}">
                                     </td>
                                </c:if>
                             </tr>
                             <tr>
                                <th style="width: 20%;"><label >选择素材种类：</label>
                                </th>
                                <td id="urlTypeChoice">
                                   <fw:radio name="urlType" selectedValue="${urlType}" list="${urlTypeList}" />
                                </td>
                             </tr>
                             <tr id="materialBelongTypeTr">
                                <th><label>素材归属:</label></th>
                                <td>
                                     <fw:radio name="materialBelongType" selectedValue="${materialBelongType}" list="${materialBelongTypeList}" />
                                </td>
                             </tr>
                             <tr id="linkAddressTr">
                                 <th style="width: 20%;"><label >输入外部链接 :</label>
                                 </th>
                                 <td>
                                    <imui:textbox id="LinkTexbox" name="externalLinkAddress" value="${externalLinkAddress}" style="width:98%;" autofocus="true"/>               
                                 </td>
                             </tr>
                             <tr id="materialSearch">
                                <th><label>素材筛选:</label></th>
                                <td>
                                     <input type="text" id="searchText" size="30"/>
                                </td>
                             </tr>
                        </tbody>
                    </table>
                </div>
                <table id='ic0205pageTable' style="width: 100%;">
                    <tr>
                        <td>
                            <fwui:page formId="ic0205Form" formAction="ic02/ic0205/doPage" ajaxPageMethod="doAjaxPage" uniqueFlag="true"/>
                        </td>
                    </tr>
                </table>
                <table id="imageTable">
                  <tr>
                    <td style="width: 50%;">
                    <div style="width: 100%; height: 361px; margin: 0px; overflow: hidden;">
                        <table id="ic0205ListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                        <thead>
                            <tr>
                                <th class="align-C valign-M" style="width:20%;" >ID</th>
                                <th class="align-C valign-M" style="width:20%;" >素材名</th>
                                <th class="align-C valign-M" style="width:auto;" >说明</th>
                                <th class="align-C valign-M" style="width:50px;" >预览</th>
                                <th class="align-C valign-M" style="width:50px;" >选择</th>
                            </tr>
                        </thead>
                        <tbody>
                             <c:forEach items="${materialInfo}" var="searchResult" varStatus="searchResultStatus">
                                <tr id="ic0205Tr_${searchResultStatus.index}" onclick="setSelectedTrColor(this,'${searchResult.materialId}');" style="height:10px;cursor: pointer;">
                                    <td title="${searchResult.materialId}" class="align-l valign-M" >${searchResult.materialId}</td>
                                    <td title="${searchResult.materialName}"  class="align-l valign-M">${searchResult.materialName}</td>
                                    <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                                    <td class="align-C valign-M" style="width:55px;" >
                                        <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${searchResult.materialPath}')"/>
                                    </td>
                                    <td class="align-C valign-M" style="width:55px;" >
                                        <input type="button" value="选择" class="imui-button imui-running-button" onclick="doSelect(this,'${searchResult.materialPath}')"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        </table>
                    </div>
                </td>
                <td>&nbsp;</td>
                <td>
                    <div style="width: 100%; height: 370px; margin: 0px; overflow: hidden;">
                       
                        <table id="ic0205SelectedListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                        <thead>
                            <tr>
                                <th class="align-C valign-M" style="width:20%;" >已选素材ID</th>
                                <th class="align-C valign-M" style="width:20%;" >已选素材名</th>
                                <th class="align-C valign-M" style="width:auto;" >说明</th>
                                <th class="align-C valign-M" style="width:50px;" >顺序</th>
                                <th class="align-C valign-M" style="width:50px;" >预览</th>
                                <th class="align-C valign-M" style="width:50px;" >取消</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${existedMaterialInfo != null and existedMaterialInfo.size() != 0 }">
                                <c:forEach items="${existedMaterialInfo}" var="searchResult" varStatus="searchResultStatus">
                                    <tr id="ic0205TrRight_${searchResult.materialId}" style="height:10px;" >
                                        <td title="${searchResult.materialId}" class="align-l valign-M" >${searchResult.materialId}</td>
                                        <td title="${searchResult.materialName}"  class="align-l valign-M">${searchResult.materialName}</td>
                                        <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                                        <td class="align-C valign-M" style="width:50px;">
                                            <input id="ic0205TrRight_${searchResult.materialId}_sortKey" type="text" value="${searchResult.sortKey}" style="width:30px;text-align:right;" maxlength="2"/>
                                        </td>
                                        <td class="align-C valign-M" style="width:55px;" >
                                            <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${searchResult.materialPath}')"/>
                                        </td>
                                        <td class="align-C valign-M" style="width:50px;">
                                           <input type="button" value="取消" class="imui-button imui-running-button" onclick="doCancel('ic0205TrRight_${searchResult.materialId}')"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                        </table>
                    </div>
                </td>
                </table>
                
                <div  id="vedioTable" style="width: 100%; height: 361px; margin: 0px; overflow: hidden;">
                    <table id="ic0205VedioListDataTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                         <thead>
                             <tr>
                                 <th class="align-C valign-M" style="width:20%;" >ID</th>
                                 <th class="align-C valign-M" style="width:20%;" >素材名</th>
                                 <th class="align-C valign-M" style="width:auto;" >说明</th>
                                 <th class="align-C valign-M" style="width:50px;" >预览</th>
                             </tr>
                         </thead>
                         <tbody>
                              <c:forEach items="${videoMaterialInfo}" var="videoSearchResult" varStatus="videoSearchResultStatus">
                                     <tr id="ic0205Tr_${videoSearchResultStatus.index}" onclick="setSelectedTrColor(this,'${videoSearchResult.materialId}');" style="height:10px;cursor: pointer;">
                                         <td title="${videoSearchResult.materialId}" class="align-l valign-M" >${videoSearchResult.materialId}</td>
                                         <td title="${videoSearchResult.materialName}"  class="align-l valign-M">${videoSearchResult.materialName}</td>
                                         <td title="${videoSearchResult.notes}" class="align-l valign-M" >${videoSearchResult.notes}</td>
                                         <td class="align-C valign-M" style="width:55px;" >
                                             <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('${videoSearchResult.materialPath}')"/>
                                         </td>
                                     </tr>
                             </c:forEach>
                         </tbody>
                    </table>
                </div>
                
                <div id="musicTable">
                     <table id="ic0205ListMusicTable" style="width: 100%;" border="1" class="imui-table-sort eq_nowrap_hidden">
                         <thead>
                             <tr>
                                 <th class="align-C valign-M" style="width:20%;" >ID</th>
                                 <th class="align-C valign-M" style="width:20%;" >素材名</th>
                                 <th class="align-C valign-M" style="width:auto;" >说明</th>
                                 <th class="align-C valign-M" style="width:50px;" >试听</th>
                             </tr>
                         </thead>
                         <tbody>
                              <c:forEach items="${materialInfo}" var="searchResult" varStatus="searchResultStatus">
                                 <tr id="ic0205Tr_${searchResultStatus.index}" onclick="setSelectedTrColor(this,'${searchResult.materialId}');" style="height:10px;cursor: pointer;">
                                     <td title="${searchResult.materialId}" class="align-l valign-M" >${searchResult.materialId}</td>
                                     <td title="${searchResult.materialName}"  class="align-l valign-M">${searchResult.materialName}</td>
                                     <td title="${searchResult.notes}" class="align-l valign-M" >${searchResult.notes}</td>
                                     <td class="align-C valign-M" style="width:55px;" >
                                         <input type="button" value="试听" class="imui-button imui-running-button" onclick="doPlaySound_Ic0205('${searchResult.materialPath}')"/>
                                     </td>
                                 </tr>
                             </c:forEach>
                         </tbody>
                     </table>
                </div>
                <div id="text_image">
					<imui:multiDragbox id="textImg" name="textImg" selectedName="selectList" values="${textImgList}" deselectedTitle="可选素材" selectedTitle="已选素材" width="400px" height="300px"/>
                    <script type="text/javascript">
                        if(!text_img_map) var text_img_map = {};
                        <c:forEach items="${textImgList}" var="dto" varStatus="s">
                             text_img_map['${dto.materialId}'] = {imgPath:'${dto.materialPath}', expositionId :'${dto.expositionId}'};
                        </c:forEach>
                    </script>
                </div>
                <div class="align-R" style="padding-top: 5px;">
                    <!-- 确定 -->
                    <input id="ic0205-confirm-button" value='确定' class="imui-medium-button" type="button" />
                </div>
            </div>   
        </div>
    </form>
</div>

<!-- submit处理 START -->
<form method="POST" action="ic01/ic0104/doSearchFromIc0205" id="ic0104-refresh-from-ic0205">
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${currentPanoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="selectedHotspotId" value= "${selectedHotspotId}" />
    <input type="hidden" name="positionAthForEdit"  value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit"  value="${positionAtvForEdit}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${ic0205TheLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${ic0205RecommendHotspotId}"/>
</form>

<form method="POST" action="vr01/vr0104/doSearchFromIc0205" id="vr0104-refresh-from-ic0205">
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${currentPanoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="selectedHotspotId" value= "${selectedHotspotId}" />
    <input type="hidden" name="positionAthForEdit"  value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit"  value="${positionAtvForEdit}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${ic0205TheLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${ic0205RecommendHotspotId}"/>
</form>
<!-- submit处理 END -->
