<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr01.Vr0105Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>整体效果操作</title>
</imui:head>

<script src="product_vr/js/vr01/vr0105.js"></script>

<!-- popup処理 START -->
<div id="addPanorama"></div>
<div id="addFlowInfo"></div>
<div id="editButtonsOrFlowInfo"></div>
<!-- popup処理 END -->
<!-- 画面タイトル -->
<div class="imui-title">
    <h1>整体效果操作</h1>
</div>
    <div class="imui-form-container" style="width:95%">
       <!-- 自定义工具条设定 -->
    <imui:dialog id="vr0105SetToolFinish" modal="true" autoOpen="false" >
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>自定义工具条设定成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-set-tool-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    
       <imui:dialog id="vr0105Finish" modal="true" autoOpen="false" onClose="closeDialog();">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>设定成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="radar_dialog-confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>
        
        <imui:dialog id="vr0105SetLayerFinish" modal="true" autoOpen="false" onClose="closeDialog();">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>浮动信息位置设定成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="layer_dialog-confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>
        
        <imui:dialog id="vr0105FlowInfoCommandChoice" title="浮动信息种类选择" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="浮动信息(图片)" id="button-image-confirm" class="imui-medium-button" />
                <br></br>
                <input type="button" value="浮动信息(文字)" id="button-text-confirm" class="imui-medium-button" />
                <br></br>
            </div>
        </imui:dialog>
        
        <imui:dialog id="vr0105FlowInfoEidtChoice" title="浮动信息操作选择" modal="true" autoOpen="false" width="220">
            <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                <input type="button" value="编辑浮动信息层大小" id="button-edit-layer-size" class="imui-medium-button" />
                <br></br>
                <input type="button" value="删除该浮动信息" id="button-delete-layer" class="imui-medium-button" />
                <br></br>
            </div>
        </imui:dialog>
        
        <form id="vr0105Form" action="vr01/vr0105"  method="post">
            <input type="hidden" name="expositionId" id="expositionId" value='${expositionId}' />
            <input type="hidden" name="positionAthForEdit" id="vr0105positionAthForEdit" value="${positionAthForEdit}"/>
            <input type="hidden" name="positionAtvForEdit" id="vr0105positionAtvForEdit" value="${positionAtvForEdit}"/>
            <input type="hidden" name="panoramaId" id="panoramaId" value='${panoramaId}' />
            <input type="hidden" name="panoramaPath" id="panoramaPath" value='${panoramaPath}' />
            <input type="hidden" name="expositionName" id="expositionName" value="${expositionName}"/>
            <input type="hidden" name="expositionMapId" id="expositionMapId" value="${expositionMapId}"/>
            <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${miniMapCheck}"/>
            <input type="hidden" name="expositionMapPath" id="expositionMapPath" value="${expositionMapPath}"/>
            <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson" value='${miniMapSpotInfoListJson}'/>
            <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson" value='${spotInfoListSubmitJson}'/>
            <input type="hidden" name="selectedHotspotId" id="selectedHotspotId" value="${selectedHotspotId}"/>
            <input type="hidden" name="radarHeading" id="radarHeading" value="${radarHeading}"/>
            <input type="hidden" name="flowInfoFileId" id="flowInfoFileId" value="${flowInfoFileId}"/>
            <input type="hidden" name="flowInfoFileX" id="flowInfoFileX" value="${flowInfoFileX}"/>
            <input type="hidden" name="flowInfoFileY" id="flowInfoFileY" value="${flowInfoFileY}"/>
            <input type="hidden" name="flowInfoFilePath" id="flowInfoFilePath" value="${flowInfoFilePath}"/>
            <input type="hidden" name="flowInfoFileInfo" id="flowInfoFileInfo" value="${flowInfoFileInfo}"/>
            <input type="hidden" name="flowInfoType" id="flowInfoType" value="${flowInfoType}"/>
            <input type="hidden" name="flowInfoFileScale" id="flowInfoFileScale" value="${flowInfoFileScale}"/>
            <input type="hidden" name="selectedButtons" id="selectedButtons" value='${selectedButtons}' />
            <input type="hidden" name="theLastedSceneHotspotId"  id="vr0105lastHotspotId" value="${theLastedSceneHotspotId}"/>
            <input type="hidden" name="currentRecommendHotspotId" id="vr0105CurrentHotspotId" value="${currentRecommendHotspotId}"/>
            
            <table class="imui-form">
                  <tr>
                     <th style="width: 20%;"><label>当前场景名称 :</label></th>
                     <td style="width: auto%;"><label  id="vr0105title">${panoramaName}</label></td>
                 </tr>
             </table>

            <table style="width: 100%">
                <tr>
                    <td style="width:360px;">
                        <fw:radio name="commandType" selectedValue="${commandType}" list="${commandTypeList}" />
                    </td>
                    <td>
                        <input type="button" value="导航图编辑" id="edit-mini-map-button" class="imui-medium-button" />
                        <input type="button" value="导航图热点编辑" id="edit-map-hotspot-button" class="imui-medium-button" />
                        <input type="button" value="雷达左旋" id="left" class="imui-medium-button" />
                        <input type="button" value="雷达右旋" id="right" class="imui-medium-button" />
                        <input type="button" value="设定雷达位置" id="set_radar" class="imui-medium-button" />
                        <input type="button" value="设置公共信息" id="set_common_info" class="imui-medium-button" />
                        <input type="button" value="添加浮动效果" id="set_flow_info" class="imui-medium-button" />
                        <input type="button" value="保存浮动效果位置" id="save_flow_info" class="imui-medium-button" />
                        <input type="button" value="自定义工具条" id="edit-buttons" class="imui-medium-button" />
                        <input type="button" value="展览目录编辑" id="edit-director" class="imui-medium-button" />
                        <input type="button" value="展览效果编辑" id="edit-effect" class="imui-medium-button" />
                    </td>
                    <td class="align-R" style="padding-bottom: 6px;">
                        <input type="button" value="返回" id="back-button" class="imui-medium-button" />
                    </td>
                </tr>
            </table>
            <div id="vr0105Pano" style=" width:100%;height:600px;" >
                <script type="text/javascript">
                    if($('#panoramaPath').val().length > 0){
                        var xmlPath = $('#panoramaPath').val()
                        var settings = {};
                        embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                            , id:'vr0105KrpanoSWFObject'
                            , xml:xmlPath
                            , target:"vr0105Pano",wmode:"opaque"
                            , flash: "only" 
                            , passQueryParameters:true
                            , bgcolor:"#f5f5f5"
                            , vars:settings});
                    }
                </script>
            </div>
            
        </form>
    </div>
    
<form id="back-form" method="POST" action="vr01/vr0104/doSearchFromVr0105">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="positionAthForEdit" id="0105BackAth" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="0105BackAtv" value="${positionAtvForEdit}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- 前往导航图编辑画面 -->
<form id="edit-map" method="POST" action="vr01/vr0106">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionMapId"  value="${expositionMapId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- 前往导航图上热点编辑画面 -->
<form id="edit-hotspot" method="POST" action="vr01/vr0107">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
<!-- 通过导航图上的热点前往场景画面 -->
<form id="go-to-vr0104-panorama" method="POST" action="vr01/vr0104/doShowHostspotInfoFromMiniMap">
    <input type="hidden" name="selectedHotspotId"  value="${selectedHotspotId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
