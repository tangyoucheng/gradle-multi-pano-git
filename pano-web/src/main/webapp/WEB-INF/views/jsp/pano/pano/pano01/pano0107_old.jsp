<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic01.Ic0107Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>展览导航图热点编辑</title>
</imui:head>

<script src="cdic/js/ic01/ic0107.js"></script>

<div id="addMiniMap"></div>
<div id="setMiniMap"></div>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>展览导航图热点编辑</h1>
</div>
    <div class="imui-form-container" style="width:97%">
        <!-- コンテンツエリア -->
        <imui:dialog id="ic0107Finish" modal="true" autoOpen="false" onClose="ic0107DoBack">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>热点编辑成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>
        
        <form id="ic0107Form" action="ic01/ic0107"  method="post">
            <input type="hidden" name="expositionId" id="expositionId" value='${expositionId}' />
            <input type="hidden" name="panoramaId" id="panoramaId" value='${panoramaId}' />
            <input type="hidden" name="panoramaPath" id="panoramaPath" value='${panoramaPath}' />
            <input type="hidden" name="expositionName" id="expositionName" value="${expositionName}"/>
            <input type="hidden" name="expositionMapId" id="expositionMapId" value="${expositionMapId}"/>
            <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${miniMapCheck}"/>
            <input type="hidden" name="expositionMapPath" id="expositionMapPath" value="${expositionMapPath}"/>
            <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson" value='${miniMapSpotInfoListJson}'/>
            <input type="hidden" name="spotInfoListSubmitJson" id="spotInfoListSubmitJson" value='${spotInfoListSubmitJson}'/>
            <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
            <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
            
            <table class="imui-form">
                  <tr>
                     <th style="width: 20%;"><label>当前场景名称 :</label></th>
                     <td style="width: auto;"><label>${panoramaName}</label></td>
                 </tr>
             </table>

            <table style="width: 100%">
                <tr>

                <td class="align-L">
                    <input type="button" value="新增导航图上热点" id="add-mini-map-hotspot-button" class="imui-medium-button" />
                    &nbsp;&nbsp;
                                                               热点样式
                    <fw:select id="styleSelect" name="hotspotStyle" selectedValue="${hotspotStyle}" list="${hotspotStyleList}" blank="false" style="width: 120px;" />
                </td>
                    <td class="align-R" style="padding-bottom: 6px;">
                     <input type="button" value="保存" id="save-button" class="imui-medium-button" />
                    &nbsp;&nbsp;<input type="button" value="返回" id="back-button" class="imui-medium-button" />
                </td>
                </tr>
            </table>
            
            <div id="ic0107Pano" style=" width:100%;height:600px;" >
                <script type="text/javascript">
                    if($('#panoramaPath').val().length > 0){
                        var xmlPath = $('#panoramaPath').val()
                        embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                            , id:'ic0107KrpanoSWFObject'
                            , xml:xmlPath
                            , target:"ic0107Pano",wmode:"opaque"
                            , flash: "only" 
                            , passQueryParameters:true
                            , bgcolor:"#f7f7f7"});
                    }
                </script>
            </div>
            
        </form>
    </div>
    
<form id="back-form" method="POST" action="ic01/ic0105/doSearchFromIc0107">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>

