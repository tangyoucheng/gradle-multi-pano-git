<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic01.Ic0109Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>展览特效信息编辑</title>
</imui:head>

<script src="cdic/js/ic01/ic0109.js"></script>
<div id="ic0109SetSound"></div>

<!-- コンテンツエリア -->
<div class="imui-form-container" style="width: 90%;margin-right:25px;">
	<imui:dialog id="ic0109Finish" modal="true" autoOpen="false" onClose="confirmOnclick">
	        <div class="imui-form-container-narrow" style="border: none;">
	            <h2>更新操作成功！</h2>
	            <div class="imui-operation-parts">
	                <input type="button" value="确定" id="button-finished-confirm" class="imui-medium-button" />
	            </div>
	        </div>
	</imui:dialog>
    <div class="imui-chapter-title">
        <h2>展览特效信息编辑</h2>
    </div>
     
    <input type="hidden" id="hidPageSubmitResult" value="${pageSubmitResult}" />
    <form id="ic0109Form" action="COMFileUpload/uploadAjax"  method="post" enctype="multipart/form-data">
    <input type="hidden" id="expositionId" name="expositionId" value="${expositionId}" />
    <input type="hidden" id="oldmaterialPath" name="oldmaterialPath" value="${oldmaterialPath}" />
    <input type="hidden" name="condition_expositionId" value="${condition_expositionId}"/>
    <input type="hidden" name="condition_expositionName" value="${condition_expositionName}"/>
    <input type="hidden" name="expositionSoundName" id="expositionSoundName" value="${expositionSoundName}"/>
    <input type="hidden" id="materialIdFromIc0208" name="materialIdFromIc0208" value="${materialIdFromIc0208}" />
    <input type="hidden" id="preloadFileUploadSuccess" name="preloadFileUploadSuccess" value="${preloadFileUploadSuccess}" />

        <table class="imui-form">
            <tr>
                <th style="width:20%;"><label>编号:</label></th>
                <td colspan="3">${expositionId}</td>
            </tr>
            <tr>
                <th><label>名称:</label></th>
                <td colspan="3">${expositionName}</td>
            </tr>
            <tr>
                <th ><label>音乐:</label></th>
                <td width="30%" >
                    <input type="checkbox" id="expoSoundFlag" name="expoSoundFlag" value="3"/>为展览设置音乐
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input id="setExpoSound" type="button" value="选择音乐" class="imui-button imui-running-button" onclick="doSetPanoBackGroundSound()"/>
                </td>
                <th width="20%"><label>音乐名:</label></th>
                <td id="soundName"><label id="soundNameLabel">${expositionSoundName}</label></td>
            </tr>
            <tr id="expoTypeTr">
                <th ><label>类型:</label></th>
                <td colspan="3">
                    <fw:radio name="expositionType" selectedValue="${expositionType}" list="${expositionTypeList}" disabled="true"/> 
                </td>
            </tr>
            <tr>
                <th><label >预加载文件:</label></th>
                <td width="30%" >
                    <input type="checkbox" id="preloadFlag" name="preloadFlag" value="1"/>为展览设置预加载文件
                </td>
                <td colspan="2">
                    <fw:radio name="preloadFileType" selectedValue="${preloadFileType}" list="${preloadFileTypeList}" disabled="true"/>
                </td>
            </tr>
            <tr id="preloadFileUploadTr">
                <th><label>预加载文件上传:</label></th>
                <td colspan="3">
                    <imui:fileUpload outerFormId="ic0109Form" onBeforeSend="callbackIc0109BeforeSend" onSuccess="callbackIc0109Success" onRemove="callbackIc0109Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="1"/>
                    <input type="hidden" name="upload_key" value="${expositionId}" />
                </td>
            </tr>
            <tr id="picturesTr">
                <c:if test="${not empty oldmaterialPath}">
	                <th><label>原预加载文件预览:</label></th>
	                <td colspan="1" style="width:30%;">
	                    <c:if test="${preloadFileType eq 0 && not empty oldmaterialPath}">
	                        <img id="oldPicture" src="${oldmaterialPath}?temp=<%=Math.random()%>" style="width: 200px;height: 200px;" />
	                    </c:if>
	                    <c:if test="${preloadFileType eq 1 && not empty oldmaterialPath}">
	                        <div id="oldPano" style="width:200px;height:200PX;">
		                         <script type="text/javascript">
			                         embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
			                               ,xml:"framework/panorama/template/video/videopreview.xml" 
			                               ,id: "oldPanoId",wmode:"opaque"
			                               ,target:"oldPano"
			                               ,flash: "only"
			                               ,passQueryParameters:true
			                               });
			                         lookOldVideoFile($("#oldmaterialPath").val());
		                         </script>
	                        </div>
	                    </c:if>
	                </td>
	                <th style="width:20%;"><label>新预加载文件预览:</label></th>
	                <td colspan="1">
	                    <img id="newPicture" src="" style="width: 200px;height: 200px;" />
	                    <div id="newPano" style="width:200px;height:200PX;"></div>
	                </td>
                </c:if>
                <c:if test="${empty oldmaterialPath}">
	                <th style="width:20%;"><label>预加载文件预览:</label></th>
	                <td colspan="3">
	                    <img id="newPicture1" src="" style="width: 200px;height: 200px;" />
	                    <div id="newPano1" style="width:200px;height:200PX;"></div>
	                </td>
                </c:if>
            </tr>  
        </table>
        <div class="imui-operation-parts">
            <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />   
        </div> 
    </form>
    
</div>
<script type="text/javascript">
    // 文件上传按钮显示名字
    $(".imui-fileupload-add-text").find("span").each(function(){
        $(this).text("上传文件");
    })
</script>
<!-- submit处理 START -->
<!-- 登录成功后画面再显示处理 -->
<form method="POST" action="ic01/ic0105" id="success-form">
    <input type="hidden" name="expositionId" value="${expositionId}"/>
    <input type="hidden" name="panoramaId" value="${panoramaId}"/>
</form>
<form id="downloadExpositionForm" action="ic01/ic0109/doDownload">
    <input type="hidden" id="expositionId" name="expositionId" value=""/>
    <input type="hidden" id="oldmaterialPath" name="oldmaterialPath" value="${oldmaterialPath}"/>
</form>
<!-- submit处理 END -->
