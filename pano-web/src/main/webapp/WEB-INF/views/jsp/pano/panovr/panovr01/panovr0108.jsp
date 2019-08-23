<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr01.Vr0108Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>全景图多边形编辑</title>
</imui:head>

<script src="product_vr/js/vr01/vr0108.js"></script>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>全景图多边形编辑</h1>
</div>
    <div class="imui-form-container" style="width:95%">
        <!-- コンテンツエリア -->
        <imui:dialog id="vr0108Finish" modal="true" autoOpen="false" onClose="vr0108DoBack">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>多边形信息编辑成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>

        <form id="vr0108Form" action="vr01/vr0108"  method="post">
            
            <input type="hidden" name="spotInfoListJson" id="spotInfoListJson" value='${spotInfoListJson}' />

            <input type="hidden" name="expositionId" id="expositionId" value='${expositionId}' />
            <input type="hidden" name="expositionName" id="expositionName" value='${expositionName}' />
            <input type="hidden" name="panoramaId" id="panoramaId" value='${panoramaId}' />
            <input type="hidden" name="panoramaName" id="panoramaName" value='${panoramaName}' />
            <input type="hidden" name="panoramaPath" id="panoramaPath" value='${panoramaPath}' />
            <input type="hidden" name="positionAthForEdit"  value="${positionAthForEdit}"/>
            <input type="hidden" name="positionAtvForEdit"  value="${positionAtvForEdit}"/>
            <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
            <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
            
            <table class="imui-form">
                  <tr>
                     <th style="width: 20%;"><label>当前全景图的名称 :</label></th>
                     <td style="width: auto;"><label>${panoramaName}</label></td>
                 </tr>
             </table>

            <table style="width: 100%">
               <tr>
                <td class="align-L">
                </td>
                    <td class="align-R" style="padding-bottom: 6px;">
                    <input type="button" value="编辑手册" id="edit-button" class="imui-medium-button" />
                    &nbsp;&nbsp;<input type="button" value="保存" id="save-button" class="imui-medium-button" />
                    &nbsp;&nbsp;<input type="button" value="返回" id="back-button" class="imui-medium-button" />
                </td>
                </tr>
            </table>

            <div id="vr0108Pano" style=" width:100%;height:600px;" >
                <script type="text/javascript">
                    if($('#panoramaPath').val().length > 0){
                        var xmlPath = $('#panoramaPath').val()
                        embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                            , id:'vr0108KrpanoSWFObject'
                            , xml:xmlPath
                            , target:"vr0108Pano"
                            , wmode:"opaque"
                            , flash: "only" 
                            , passQueryParameters:true
                            , bgcolor:"#f5f5f5"});
                    }
                </script>
            </div>

        </form>
    </div>
    
<form id="back-form" method="POST" action="vr02/vr0203/doSearchFromVr0108">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="positionAthForEdit" id="0108BackAth" value="${positionAthForEdit}"/>
    <input type="hidden" name="positionAtvForEdit" id="0108BackAtv" value="${positionAtvForEdit}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
