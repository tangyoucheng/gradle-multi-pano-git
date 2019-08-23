<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic03.Ic0304Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp"%>

<imui:head>
    <title>场景上地图登录</title>
</imui:head>

<script src="cdic/js/ic03/ic0304.js"></script>
<imui:dialog id="ic0304AddFinish" modal="true" autoOpen="false" onClose="ic0304DoBack">
    <div class="imui-form-container-narrow" style="border: none;">
        <h2>地图登录完成。</h2>
        <div class="imui-operation-parts">
            <input type="button" value="确定" id="ic0304-button-finish-confirm" class="imui-medium-button" />
        </div>
    </div>
</imui:dialog>

<div class="imui-form-container" style="width:95%;">

    <form id="ic0304Form" action="COMFileUpload/uploadAjax"  method="post" enctype="multipart/form-data">
    <input type="hidden" name="expositionId" id="expositionId" value= "${expositionId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="panoramaId" value= "${panoramaId}" />
    <input type="hidden" name="expositionMapId" id="expositionMapId" value= "${expositionMapId}" />
    <input type="hidden" name="lasthotspotIdFrom0106" value= "${lasthotspotIdFrom0106}" />
    <input type="hidden" name="currenthotspotIdFrom0106" value= "${currenthotspotIdFrom0106}" />
    <input type="hidden" name="existedExpositionMapId" value= "${existedExpositionMapId}" />
    <input type="hidden" name="mapCheck" id="mapCheck" value= "" />
        <table class="imui-form">
            <tr>
                <th><label>所在展览名称</label></th>
                <td>${expositionName}</td>
            </tr>
            <tr>
                <th style="width:20%;"><label class="imui-required">场景地图ID</label></th>
                <td><imui:textbox id="expositionMapId" name="expositionMapId" value="${expositionMapId}" style="width:98%;" autofocus="true"/></td>
            </tr>
            <tr>
                <th style="width:20%;"><label class="imui-required">场景地图名称</label></th>
                <td><imui:textbox id="expositionMapName" name="expositionMapName" value="${expositionMapName}" style="width:98%;" autofocus="true"/></td>
            </tr>
            <tr>
                <th><label>地图上传</label></th>
                <td >
                    <imui:fileUpload outerFormId="ic0304Form" onSuccess="callbackIc0304Success" onBeforeSend="callbackIc0304BeforeSend" onRemove="callbackIc0304Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="1"/>
                    <input type="hidden" name="upload_key" value="${expositionMapId}" />
                </td>
            </tr>
            <tr>
                <th><label>地图预览</label></th>
                <td >
                    <img id="miniMap" src="" style="width: 200px;height: 200px;display: none;" />
                </td>
            </tr>
            <tr>
                <th><label>备注</label></th>
                <td>
                    <imui:textArea id="textarea" name="notes" rows="10" style="width:98%" value="${notes}"></imui:textArea>
                </td>
        
             </tr>
        </table>
    
        <div class="imui-operation-parts">
            <input type="button" value="确定" id="ic0304-confirm-button" class="imui-medium-button" />
        </div> 
    </form>
    
</div>

<!-- submit处理 START -->
<!-- 返回处理 -->
<form method="POST" action="home" id="back-form">
</form>
<!-- 非VR展览登录成功后画面再显示处理 -->
<form method="POST" action="ic01/ic0106/doSearchFromIc0304" id="ic0106-refresh-from-ic0304">
    <input type="hidden" name="theLastedSceneHotspotId" value= "${lasthotspotIdFrom0106}" />
    <input type="hidden" name="currentRecommendHotspotId" value= "${currenthotspotIdFrom0106}" />
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${panoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="ic0106expositionMapName" id="expositionMapName" value= "${expositionMapName}" />
    <input type="hidden" name="expositionMapId" id="expositionMapIdForIc0106" value=""/>
</form>
<!-- VR展览登录成功后画面再显示处理 -->
<form method="POST" action="vr01/vr0106/doSearchFromIc0304" id="vr0106-refresh-from-ic0304">
    <input type="hidden" name="theLastedSceneHotspotId" value= "${lasthotspotIdFrom0106}" />
    <input type="hidden" name="currentRecommendHotspotId" value= "${currenthotspotIdFrom0106}" />
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${panoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" name="vr0106expositionMapName" id="expositionMapName" value= "${expositionMapName}" />
    <input type="hidden" name="expositionMapId" id="expositionMapIdForVr0106" value=""/>
</form>
<!-- submit处理 END -->
