<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic03.Ic0303Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp"%>

<imui:head>
    <title>素材编辑</title>
</imui:head>

<script src="cdic/js/ic03/ic0303.js"></script>
<imui:dialog id="ic0303AddFinish" modal="true" autoOpen="false" >
    <div class="imui-form-container-narrow" style="border: none;">
        <h2>素材编辑完成。</h2>
        <div class="imui-operation-parts">
            <input type="button" value="确定" id="ic0303-button-finish-confirm" class="imui-medium-button" />
        </div>
    </div>
</imui:dialog>

<div class="imui-form-container" style="width:95%;">

    <form id="ic0303Form" action="COMFileUpload/uploadAjax"  method="post" enctype="multipart/form-data">
    <input type="hidden" id="ic0303materialId" name="materialId" value="${materialId}"/>
    <input type="hidden" name="expositionId" value= "${expositionId}" />
    <input type="hidden" name="panoramaId" value= "${currentPanoramaId}" />
    <input type="hidden" name="expositionName" value= "${expositionName}" />
    <input type="hidden" id="oldmaterialPath" name="oldmaterialPath" value= "${oldmaterialPath}" />
    <input type="hidden" id="ic0303materialTypeId" name="materialTypeId" value= "${materialTypeId}" />
    <input type="hidden" id="materiaIdForSearch" name="materiaIdForSearch" value= "${materiaIdForSearch}" />
    <input type="hidden" id="materialNameForSearch" name="materialNameForSearch" value= "${materialNameForSearch}" />
    <input type="hidden" id="materialNameTypeForSearch" name="materialNameTypeForSearch" value= "${materialNameTypeForSearch}" />
    <input type="hidden" name="hiddenPanoramaId" id="hiddenPanoramaId"  value="${hiddenPanoramaId}"/>
    <input type="hidden" name="hiddenExpositionId" id="hiddenExpositionId"  value="${hiddenExpositionId}"/>
    <input type="hidden" name="hiddenExpositionName" id="hiddenExpositionName"  value="${hiddenExpositionName}"/>
    <input type="hidden" name="hiddenPageStartRowNo" id="hiddenPageStartRowNo"  value="${hiddenPageStartRowNo}"/>
    <input type="hidden" name="hiddenMaterialBelongType" id="hiddenMaterialBelongType"  value="${hiddenMaterialBelongType}"/>
 
    <input type="hidden" id="pictureCheck" value= "" />
    

        <table class="imui-form">
            <tr>
                <th><label>ID:</label></th>
                <td style="width:88%;"><label>${materialId}</label></td>
            </tr>
            <tr>
                <th><label class="imui-required">名称:</label></th>
                <td><imui:textbox id="materialNameTexbox" name="materialName" value="${materialName}" style="width:98%;" autofocus="true"/></td>
            </tr>
            <tr>
                <th><label>种类:</label></th>
                <td>
                    <fw:radio name="materialTypeId" selectedValue="${materialTypeId}" list="${icWMMaterialList}" disabled="true" />
                </td>
            </tr>
            <c:if test="${materialTypeId != '8'}">
                <tr>
                    <th><label>上传文件:</label></th>
                     <td>
                        <imui:fileUpload outerFormId="ic0303Form" onBeforeSend="callbackIc0303BeforeSend" onSuccess="callbackIc0303Success" onRemove="callbackIc0303Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="1"/>
                        <input type="hidden" name="upload_key" value="${uplod_key}" />
                    </td>
                </tr>
            </c:if>
            <c:if test="${materialTypeId == '8'}">
                <tr>
                    <th><label class="imui-required">输入新文字:</label></th>
                     <td>
                        <imui:textbox id="textflowInfoTexbox" name="textflowInfo" value="${oldTextflowInfo}" style="width:90%;" autofocus="true"></imui:textbox>
                        
                        <input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreviewTextinfo()"/>
                    </td>
                </tr>
            </c:if>
            <c:if test="${materialTypeId != '2' && materialTypeId != '8' && materialTypeId != '3'}">
                <tr id="oldImageTr">
                    <th><label>原素材预览:</label></th>
                    <td >
                        <img id="oldImage" src="${oldmaterialPath}?temp=<%=Math.random()%>" style="width: 120px;height: 120px;" />
                    </td>
                </tr>
                <tr id="newImageTr">
                <th><label>新素材预览:</label></th>
                <td >
                    <img id="newImage" src="" style="width: 120px;height: 120px;display: none;" />
                </td>
            </tr>
            </c:if>
            <c:if test="${materialTypeId == '8' }">
                <tr id="oldTextTr">
                    <th><label>原素材预览:</label></th>
                    <td >
                        <div id="oldtextDiv" style="font-size:18px" class="align-C" >${oldTextflowInfo}</div>
                    </td>
                </tr>
                <tr id="newTextTr">
                <th><label>新素材预览:</label></th>
                <td >
                    <div id="newtextDiv" style="font-size:18px" class="align-C" ></div>
                </td>
            </tr>
            </c:if>
            <c:if test="${materialTypeId == '2' || materialTypeId == '3'}">
                <tr id="oldSoundTr">
                   <th><label>原媒体文件:</label></th>
                    <td style="width: 60%;height:40%;">
                        <div id="oldSoundPreview" style="width:100%;height:300px;">
                         <script type="text/javascript">
                         embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                               ,xml:"framework/panorama/template/sound/soundpreview.xml"  
                               ,id: "oldSoundPanorama"
                               ,target:"oldSoundPreview"
                               ,wmode:"opaque"
                               ,flash: "only"
                               ,passQueryParameters:true
                               });
                         </script>
                        </div>
                    </td>
                <tr>
                <tr id="newSoundTr">
                <th><label>新媒体文件: </label></th>
                <td style="width: 60%;height:40%;">
                    <div id="newSoundPreview" style="width:100%;height:300px;">
                        <script type="text/javascript">
                         embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                               ,xml:"framework/panorama/template/sound/soundpreview.xml"  
                               ,id: "newSoundPanorama"
                               ,target:"newSoundPreview"
                               ,wmode:"opaque"
                               ,flash: "only"
                               ,passQueryParameters:true
                               });
                         </script>
                    </div>
                </td>
            </tr>
            </c:if>
            <c:if test="${materialTypeId == '9'}">
	            <tr id="text_info_tr">
	                <th><label>文本:</label></th>
	                <td >
	                    <imui:richtextbox name="textInfo" value="${textInfo}" plugins="textcolor,colorpicker,code,lineheight" toolbar1="newdocument,bold,italic,underline,alignleft,aligncenter,alignright,fontsizeselect,forecolor,backcolor,cut,copy,paste,undo,redo,lineheightselect,code"/>
	                </td>
	            </tr>
            </c:if>
            <tr>
                <th><label>说明:</label></th>
                <td>
                    <imui:textArea id="textarea" name="notes" rows="3" style="width:98%" value="${notes}"></imui:textArea>
                </td>
             </tr>
        </table>
        <div class="imui-operation-parts">
            <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />
            &nbsp;&nbsp;
            <input type="button" value="删除" id="delete-button" class="imui-medium-button" />
        </div> 
    </form>
</div>
<!-- 返回0302检索画面 -->
<form id="back-to-ic0302" method="POST" action="ic03/ic0302">
    <input type="hidden" name="openFromIc0104"  id="ic0104FlgFromIc0302" value=""/>
    <input type="hidden" name="materialId"  value="${materiaIdForSearch}"/>
    <input type="hidden" name="materialName"  value="${materialNameForSearch}"/>
    <input type="hidden" name="materialTypeId"  value="${materialNameTypeForSearch}"/>
    <input type="hidden" name="ic0302PanoramaId"  value="${hiddenPanoramaId}"/>
    <input type="hidden" name="expositionId"  value="${hiddenExpositionId}"/>
    <input type="hidden" name="expositionName"  value="${hiddenExpositionName}"/>
    <input type="hidden" name="pageStartRowNo"  value="${hiddenPageStartRowNo}"/>
    <input type="hidden" name="materialBelongType"  value="${hiddenMaterialBelongType}"/>
    
</form>
