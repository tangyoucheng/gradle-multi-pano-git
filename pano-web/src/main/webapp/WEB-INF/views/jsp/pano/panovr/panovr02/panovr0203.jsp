<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr02.Vr0203Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>场景热点编辑</title>
</imui:head>

<script src="product_vr/js/vr02/vr0203.js"></script>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>场景热点编辑</h1>
</div>

<!-- popup処理 START -->
<div id="popupPage"></div>
<!-- popup処理 END -->
    <div class="imui-form-container" style="width:95%">
        <!-- コンテンツエリア -->
        <imui:dialog id="vr0203Finish" modal="true" autoOpen="false" onClose="vr0203DoBack">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>热点信息编辑成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>
        <!-- 热点单击事件指令框 -->
        <imui:dialog id="vr0203HotspotCommandChoice" title="热点操作" modal="true" autoOpen="false" width="220">
                <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                    <input type="button" value="更换热点图片" id="button-edit-icon" class="imui-medium-button" />
                    <br></br>
                    <input type="button" value="调整热点大小" id="button-edit-size" class="imui-medium-button" />
                    <br></br>
                    <input type="button" value="删除热点" id="button-delete-hotspot" class="imui-medium-button" />
                </div>
        </imui:dialog>
        
        <!-- 单点音频热点单击事件指令框 -->
        <imui:dialog id="vr0203HotspotCommandChoice2" title="热点操作" modal="true" autoOpen="false" width="220">
                <div class="imui-operation-parts" style="margin-left:30px;text-align: left;">
                    <input type="button" value="预览" id="button-other-image" class="imui-medium-button" />
                    <br></br>
                    <input type="button" value="更换热点图片" id="button-other-icon" class="imui-medium-button" />
                    <br></br>
                    <input type="button" value="调整热点大小" id="button-other-size" class="imui-medium-button" />
                    <br></br>
                    <input type="button" value="删除热点" id="button-delete-other" class="imui-medium-button" />
                </div>
        </imui:dialog>
        
        <form id="vr0203Form" action="vr02/vr0203"  method="post">
            <input type="hidden" id="hotspotIdForCheck" name="hotspotIdForCheck" value="${hotspotIdForCheck}" />
            <input type="hidden" name="spotInfoListJson" id="spotInfoListJson" value='${spotInfoListJson}' />
            <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson" value='${spotInfoListSubmitJson}' />
            <input type="hidden" name="expositionId" id="expositionId" value='${expositionId}' />
            <input type="hidden" name="panoramaId" id="panoramaId" value='${panoramaId}' />
            <input type="hidden" name="panoramaPath" id="panoramaPath" value='${panoramaPath}' />
            <input type="hidden" name="positionAthForEdit" id="positionAthForEdit" value="${positionAthForEdit}"/>
            <input type="hidden" name="positionAtvForEdit" id="positionAtvForEdit" value="${positionAtvForEdit}"/>
            <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
            <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
            <input type="hidden" id="ic0208seconfMaterialId" name="ic0208seconfMaterialId" value="${ic0208seconfMaterialId}"/>
            <input type="hidden" name="hotspotUrlInfoJsonIc0208" id="hotspotUrlInfoJsonIc0208" value="${hotspotUrlInfoJsonIc0208}"/>
            
            <input type="hidden" id="flag" value='' />
            
            <table class="imui-form">
                  <tr>
                     <th style="width: 20%;"><label>当前场景名称 :</label></th>
                     <td style="width: auto;"><label>${panoramaName}</label></td>
                 </tr>
             </table>

            <table style="width: 100%">
                <tr>
                    <td class="align-L">
                        <input type="button" value="单点热点" id="normal-hotspot-button" class="imui-medium-button" />
                        &nbsp;&nbsp;
                        <input type="button" value="多边形热点" id="polygon-hotspot-button" class="imui-medium-button" />
                        &nbsp;&nbsp;
                        <input type="button" value="场景切换热点" id="guide-hotspot-button" class="imui-medium-button" />
                        &nbsp;&nbsp;
                        <input type="button" value="LOGO信息" id="logo-hotspot-button" class="imui-medium-button" />
                    </td>
                    <td class="align-R" style="padding-bottom: 6px;">
                        <input type="button" value="保存" id="save-button" class="imui-medium-button" />
                        &nbsp;&nbsp;<input type="button" value="返回" id="back-button" class="imui-medium-button" />
                    </td>
                </tr>
            </table>
            
            <div id="vr0203Pano" style=" width:100%;height:600px;" >
                <script type="text/javascript">
                    if($('#panoramaPath').val().length > 0){
                        var xmlPath = $('#panoramaPath').val()
                        embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                            , id:'vr0203KrpanoSWFObject'
                            , xml:xmlPath
                            , target:"vr0203Pano",wmode:"opaque"
                            , flash: "only" 
                            , passQueryParameters:true
                            , bgcolor:"#f5f5f5"});
                    }
                </script>
            </div>
            
        </form>
    </div>
    
<form id="back-form" method="POST" action="vr01/vr0104/doSearchFromVr0203">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="positionAthForEdit" id="0203BackAth" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="0203BackAtv" value="${positionAtvForEdit}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>

<!-- 多边形热点编辑按钮按下时的处理 -->
<form id="polygon-hotspot" method="POST" action="vr01/vr0108">
    <input type="hidden" name="positionAthForEdit" id="vr0108positionAthForEdit" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="vr0108positionAtvForEdit" value="${positionAtvForEdit}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" id="vr0108PanoramaId" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
