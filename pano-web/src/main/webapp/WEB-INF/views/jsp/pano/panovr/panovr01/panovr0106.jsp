<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr01.Vr0106Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>展览导航图编辑</title>
</imui:head>

<script src="product_vr/js/vr01/vr0106.js"></script>

<div id="addMiniMap"></div>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>展览导航图编辑</h1>
</div>
    <div class="imui-form-container" style="width:95%">
        <!-- コンテンツエリア -->
        <imui:dialog id="vr0106Finish" modal="true" autoOpen="false" onClose="vr0106DoBack">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>导航图操作成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>
        <!-- 删除导航图 -->
        <imui:dialog id="vr0106DeleteFinish" modal="true" autoOpen="false" onClose="vr0106DoBack">
            <div class="imui-form-container-narrow" style="border: none;">
                <h2>删除成功！</h2>
                <div class="imui-operation-parts">
                    <input type="button" value="确定" id="delete-confirm-button" class="imui-medium-button" />
                </div>
            </div>
        </imui:dialog>
     
        <form id="vr0106Form" action="vr01/vr0106"  method="post">
            <input type="hidden" name="expositionId" id="expositionId" value='${expositionId}' />
            <input type="hidden" name="panoramaId" id="panoramaId" value='${panoramaId}' />
            <input type="hidden" name="panoramaPath" id="panoramaPath" value='${panoramaPath}' />
            <input type="hidden" name="expositionName" id="expositionName" value="${expositionName}"/>
            <input type="hidden" name="expositionMapId" id="expositionMapId" value="${expositionMapId}"/>
            <input type="hidden" name="vr0106expositionMapName" id="vr0106expositionMapName" value= "${vr0106expositionMapName}" />
            <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${miniMapCheck}"/>
            <input type="hidden" name="expositionMapPath" id="expositionMapPath" value="${expositionMapPath}"/>
            <input type="hidden" name="miniMapSpotInfoListJson" id="miniMapSpotInfoListJson" value="${miniMapSpotInfoListJson}"/>
            <input type="hidden" name="theLastedSceneHotspotId"  id="vr0106LasthotspotId" value="${theLastedSceneHotspotId}"/>
            <input type="hidden" name="currentRecommendHotspotId" id="vr0106CurrenthotspotId" value="${currentRecommendHotspotId}"/>
            
            <table class="imui-form">
                  <tr>
                     <th style="width: 20%;"><label>当前场景名称 :</label></th>
                     <td style="width: auto;"><label>${panoramaName}</label></td>
                 </tr>
             </table>

            <table style="width: 100%">
                <tr>

                <td class="align-L">
                    <input type="button" value="新建导航图" id="add-mini-map-button" class="imui-medium-button" />
                    &nbsp;&nbsp;
                     <input type="button" value="为当前展览选择导航图" id="set-mini-map-button" class="imui-medium-button" />
                    <c:if test="${miniMapCheck == true }">
                        <input type="button" value="删除当前导航图" id="delete-mini-map-button" class="imui-medium-button" />
                    </c:if>
                </td>
                    <td class="align-R" style="padding-bottom: 6px;">
                    <input type="button" value="返回" id="back-button" class="imui-medium-button" />
                </td>
                </tr>
            </table>
            
            <div id="vr0106Pano" style=" width:100%;height:600px;" >
                <script type="text/javascript">
                    if($('#panoramaPath').val().length > 0){
                        var xmlPath = $('#panoramaPath').val()
                        embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                            , id:'vr0106KrpanoSWFObject'
                            , xml:xmlPath
                            , target:"vr0106Pano",wmode:"opaque"
                            , flash: "only" 
                            , passQueryParameters:true
                            , bgcolor:"#f5f5f5"});
                    }
                </script>
            </div>
            
        </form>
    </div>
    
<form id="back-form" method="POST" action="vr01/vr0105/doSearchFromVr0106">
    <input type="hidden" name="panoramaId"  value="${panoramaId}"/>
    <input type="hidden" name="expositionId"  value="${expositionId}"/>
    <input type="hidden" name="expositionName"  value="${expositionName}"/>
    <input type="hidden" name="theLastedSceneHotspotId"  value="${theLastedSceneHotspotId}"/>
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}"/>
</form>
