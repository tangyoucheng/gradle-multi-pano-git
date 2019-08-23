<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic01.Ic0104Form"%>
<%@ page import="cn.com.cdic.cnst.CommonConstants"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<script type="text/javascript" src="framework/minicolors/jquery.minicolors.js"></script>
<link href="framework/minicolors/jquery.minicolors.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="framework/panorama/template/staticvtour/js/magnific/jquery.magnific-popup.js"></script>
<link href="framework/panorama/template/staticvtour/js/magnific/magnific-popup.css" rel="stylesheet" type="text/css" />
<style type="text/css">
a {
    outline: 0;
    border: 0px solid transparent!important;
}
</style>
<imui:head>
    <title>展览场景编辑</title>
</imui:head>
<script src="cdic/js/ic01/ic0104.js"></script>
<%     
  request.setAttribute("START_SCENE_FLAG_NO",CommonConstants.START_SCENE_FLAG_NO); 
%>
<!-- 画面タイトル -->
<div class="imui-title">
    <h1>展览场景编辑</h1>
</div>

<!-- コンテンツエリア -->
<div class="imui-form-container-narrow" style="width:95%">
    <!-- 初始角度设定 -->
    <imui:dialog id="ic0301SetLookAtFinish" modal="true" autoOpen="false" >
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>初始角度设定成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-set-lookat-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <!-- 首场景设定 -->
    <imui:dialog id="ic0301SetFirstSenceFinish" modal="true" autoOpen="false" >
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>首场景设定成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-set-firstSence-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
   <!-- 已有信息图的普通热点编辑 -->
    <imui:dialog id="ic0104HotspotCommandChoice" title="热点操作" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="预览热点内容" id="button-preview-confirm" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑热点内容" id="button-edit-confirm" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑热点基本信息" id="button-editSize-confirm" class="imui-medium-button" />
                <br></br>
            </div>
    </imui:dialog>
    <!-- 暂无信息图的普通热点编辑 -->
    <imui:dialog id="ic0104NewHotspotCommandChoice" title="热点操作" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="添加热点内容" id="new-hotspot-button-edit-confirm" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑热点基本信息" id="new-hotspot-button-editSize-confirm" class="imui-medium-button" />
                <br></br>
            </div>
    </imui:dialog>
     <!-- 已有场景的导航热点编辑 -->
    <imui:dialog id="ic0104HotspotCommandChoice2" title="热点操作" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="跳转到对应场景" id="button-to-confirm" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑场景信息" id="button-to-editPanoramaInfo" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑热点基本信息" id="button-editSize-confirms" class="imui-medium-button" />
                <br></br>
            </div>
    </imui:dialog>
    <!-- 暂无场景的导航热点编辑 -->
    <imui:dialog id="ic0104NewHotspotCommandChoice2" title="热点操作" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="添加对应场景" id="new-hotspot-button-to-add" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑热点基本信息" id="new-hotspot-button-editSize-confirms" class="imui-medium-button" />
                <br></br>
            </div>
    </imui:dialog>
     <!-- 多边形热点编辑 -->
     <imui:dialog id="ic0104HotspotCommandChoice3" title="热点操作" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="预览热点内容" id="button-previewPolygon-confirm" class="imui-medium-button" />
                <br></br>
                <input type="button" value="编辑热点内容" id="button-editPolygon-confirm" class="imui-medium-button" />
                <br></br>
            </div>
    </imui:dialog>

    <form id="ic0104Form" action="ic01/ic0104"  method="post">
        <input type="hidden" id="selectedHotspotId" name="selectedHotspotId" value="${selectedHotspotId}" />
        <input type="hidden" id="selectedMiniMapHotspotId" name="selectedMiniMapHotspotId" value="${selectedMiniMapHotspotId}" />
        <input type="hidden" id="spotInfoListJson" name="spotInfoListJson" value='${spotInfoListJson}' />
        <input type="hidden" id=" pointList" name=" pointList" value='${pointList}' />
        <input type="hidden" id="startSceneFlag" name="startSceneFlag" value="${startSceneFlag}" />
        <input type="hidden" id="expositionId" name="expositionId" value='${expositionId}' />
        <input type="hidden" id="expositionName" name="expositionName" value="${expositionName}" />
        <input type="hidden" id="panoramaPath" name="panoramaPath" value="${panoramaPath}" />
        <input type="hidden" id="panoramaId" name="panoramaId" value="${panoramaId}" />
        <input type="hidden" id="panoramaName" name="panoramaName" value="" />
        <input type="hidden" id="theLastPanoramaId" name="theLastPanoramaId" value="${theLastPanoramaId}" />
        <input type="hidden" id="ic0202panoramaId" value="" />
        <input type="hidden" id="panoramaVlookat" name="panoramaVlookat" value="${panoramaVlookat}" />
        <input type="hidden" id="panoramaHlookat" name="panoramaHlookat" value="${panoramaHlookat}" />
        <input type="hidden" name="expositionMapId" id="expositionMapId" value="${expositionMapId}"/>
        <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${miniMapCheck}"/>
        <input type="hidden" name="expositionMapPath" id="expositionMapPath" value="${expositionMapPath}"/>
        <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson" value='${miniMapSpotInfoListJson}'/>
        <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson" value='${spotInfoListSubmitJson}'/>
        <input type="hidden" name="hotspotTooltip" id="hotspotTooltip" value='${hotspotTooltip}'/>
        <input type="hidden" name="backGroundSoundPath" id="backGroundSoundPath" value='${backGroundSoundPath}'/>
        <input type="hidden" name="ic0209hotspotId"  id ="ic0209hotspotId"  value="${selectedHotspotId}"/>
        <input type="hidden" name="radarHeading"  id ="radarHeading"  value="${radarHeading}"/>
        <input type="hidden" name="theLastedSceneHotspotId"  id ="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
        <input type="hidden" name="currentRecommendHotspotId"  id ="currentRecommendHotspotId"  value="${currentRecommendHotspotId}"/>
        <input type="hidden" name="expositionRecommendInfo"  id ="expositionRecommendInfo"  value="${expositionRecommendInfo}"/>
        <input type="hidden" id="currentSoundPath" value=''/>
        <input type="hidden" id ="ic0104DeleteFlgFromIc0202"  value=""/>
        <input type="hidden" name="thisFlagIsFromIc0102"  value="${thisFlagIsFromIc0102 }"/>
        <input type="hidden" name="expoGoSceneTooltipInfo"  id ="expoGoSceneTooltipInfo"  value="${expoGoSceneTooltipInfo}"/>
        
        <table class="imui-form">
            <tr>
                <th style="width: auto;" class="align-L">
                    <label id='titleInfo' style="float: left;">${expositionName}&nbsp;——&nbsp;${panoramaName}</label>
                    <fw:select id="panoramaSelect" name="panoramaId"  selectedValue="${panoramaId}" list="${panoramaSelectInfo}" onChange="ic0104SelectRefresh()" blank="false" style="min-width: 120px;float:right;"/>
                </th>
            </tr>
        </table>
        <table style="width: 100%">
            <tr>
            <td class="align-L" style="padding-bottom: 6px;">
            	<!-- 判断是否存在场景与场景路径 -->
                <c:if test="${not empty panoramaPath and panoramaSelectInfo != null and panoramaSelectInfo.size() != 0 }">
                    <input type="button" value="设为第一视角" id="button-set-lookat" class="imui-medium-button" />
             &nbsp;<input type="button" value="设为首场景" id="button-startScene" class="imui-medium-button" />
                </c:if>
            </td>
            <td class="align-C" style="padding-bottom: 6px;">
                <input type="button" value="素材登记" id="button-add-material" class="imui-medium-button" />
                &nbsp;<input type="button" value="素材一览" id="button-edit-material" class="imui-medium-button" />
            </td>
            <td class="align-C" style="padding-bottom: 6px;">
                <input type="button" value="场景登记" id="button-vtour-map" class="imui-medium-button" />
             <c:if test="${not empty panoramaPath and panoramaSelectInfo != null and panoramaSelectInfo.size() != 0 }">
                  &nbsp;<input type="button" value="场景信息编辑" id="button-update-map" class="imui-medium-button" />
                 &nbsp;<input type="button" value="场景一览" id="button-edit-pano" class="imui-medium-button" />
             </c:if>
            </td>
            <td class="align-C" style="padding-bottom: 6px;">
             <c:if test="${not empty panoramaPath and panoramaSelectInfo != null and panoramaSelectInfo.size() != 0 }">
                  <input style="margin-left:30px" type="button" value="热点编辑" id="button-edit-hotspot" class="imui-medium-button" />
                  <!--  &nbsp;&nbsp;&nbsp;<input type="button" value="多边形热点编辑" id="button-edit_polygon-hotspot" class="imui-medium-button" />-->
                  &nbsp;
             </c:if>
             <input type="button" value="整体效果" id="button-edit-exposition-layer" class="imui-medium-button" />
            </td>
            <td class="align-R" style="padding-bottom: 6px;">
             <input style="margin-left:30px" type="button" value="返回" id="back-to-ic0102" class="imui-medium-button" />
            </td>
             
            </tr>
        </table>
        
        <c:if test='${not empty panoramaPath and panoramaSelectInfo != null and panoramaSelectInfo.size() != 0}'>
            <div id="ic0104Pano" style=" width:100%;height:600px;" >
                <script type="text/javascript">
                    var xmlPath = $('#panoramaPath').val()
                    var settings = {};
                    if($('#panoramaHlookat').val().length != 0
                            && $('#panoramaVlookat').val().length != 0){
                        settings['view.hlookat'] = $('#panoramaHlookat').val();
                        settings['view.vlookat'] = $('#panoramaVlookat').val();
                    }
                    embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                        , id:'ic0104KrpanoSWFObject'
                        , xml:xmlPath
                        , target:"ic0104Pano",wmode:"opaque"
                        , flash: "only" 
                        , passQueryParameters:true
                        , bgcolor:"#f5f5f5"
                        , vars:settings});
                    function doOpenTextImgPage(){
                    	var url = 'framework/panorama/template/html/index.html?id='+$("#selectedHotspotId").val();
                        $.magnificPopup.open({
                            items: {
                              src: url
                            },
                            type: 'iframe',
                            alignTop: false
                        });
                    }
                </script>
            </div>
            
        </c:if>
        
    </form>
</div>


<!-- submit处理 START -->
<!--返回一览-->
<form method="POST" action="ic01/ic0110/doSearchFromEdit" id="back-form" >
    <input type="hidden" name="condition_expositionId" value="${condition_expositionId}"/>
    <input type="hidden" name="condition_expositionName" value="${condition_expositionName}"/>
    <input type="hidden" name="pageStartRowNoFromIc0104" id="ic0104PageStartRowNo" value="${pageStartRowNoFromIc0104}"/>
    <input type="hidden" name="pageStartRowNo"  value="${pageStartRowNo}"/>
</form>

<!-- 删除可视化信息处理 -->
<form id="ic0104-delete-map" method="POST" action="ic01/ic0104/doDeleteMap">
    <input type="hidden" name="expositionId" value='${expositionId}' />
    <input type="hidden" name="panoramaId" id="deletePanorama" value='${panoramaId}' />
    <input type="hidden" name="expositionMapId" value="${expositionMapId}"/>
</form>

<!-- 新建全景图首图画面返回处理 -->
<form id="ic0104-refresh-from-ic0201" method="POST" action="ic01/ic0104/doSearchFromIc0201">
    <input type="hidden" name="panoramaId" id="panoramaId-from-ic0201" value="${panoramaId}" />
    <input type="hidden" name="expositionId" id="expositionId-from-ic0201" value="${expositionId}" />
    <input type="hidden" name="expositionName" id="expositionName-from-ic0201" value="${expositionName}" />
</form>
<!-- 全景图基本信息编辑画面返回处理 -->
<form id="ic0104-refresh-from-ic0202" method="POST" action="ic01/ic0104/doSearchFromIc0202">
    <input type="hidden" name="panoramaId" id="panoramaId-from-ic0202" value="${panoramaId}" />
    <input type="hidden" name="expositionId" id="expositionId-from-ic0202" value="${expositionId}" />
    <input type="hidden" name="expositionName" id="expositionName-from-ic0202" value="${expositionName}" />
    <input type="hidden" name="theLastedSceneHotspotId" id="last-hotspotId-from-0202"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" id="current-hotspotId-from-0202" value="${currentRecommendHotspotId}"/>
</form>


<!-- 展览整体效果处理-->
<form id="ic0104-edit-exposition-layer" method="POST" action="ic01/ic0105">
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="positionAthForEdit" id="ic0105positionAthForEdit" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="ic0105positionAtvForEdit" value="${positionAtvForEdit}"/>
    <input type="hidden" id="ic0105PanoramaId" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId" id="lastHotspotIdTo0105" value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- 热点编辑按钮按下时的处理 -->
<form id="edit-pano-hotspot" method="POST" action="ic02/ic0203">
    <input type="hidden" name="positionAthForEdit" id="ic0203positionAthForEdit" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="ic0203positionAtvForEdit" value="${positionAtvForEdit}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" id="ic0203PanoramaId" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId" id="lastHotspotIdTo0203" value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- 多边形热点编辑按钮按下时的处理 -->
<form id="edit-polygon-hotspot" method="POST" action="ic01/ic0108">
    <input type="hidden" name="positionAthForEdit" id="ic0108positionAthForEdit" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="ic0108positionAtvForEdit" value="${positionAtvForEdit}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" id="ic0108PanoramaId" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
</form>
<!-- 素材一览按下处理 -->
<form id="ic0104-edit-material" method="POST" action="ic03/ic0302">
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="ic0302PanoramaId" id="ic0302PanoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="openFromIc0104" value= "yes" />
</form>
<!-- 场景一览按下处理 -->
<form id="ic0104-edit-pano" method="POST" action="ic02/ic0206">
    <input type="hidden" name="expositionMapId"  value="${expositionMapId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="ic0206PanoramaId" id="panoramaIdForIc0206"  value=""/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId" id="lastHotspotIdTo0206" value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- submit处理 END -->
