<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr02.Vr0202Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>场景编辑画面</title>
</imui:head>

<script src="product_vr/js/vr02/vr0202.js"></script>
<div id="setSound"></div>

<!-- コンテンツエリア -->
<div class="imui-form-container" style="width: 95%;">

     <imui:dialog id="vr0202UpdFinish" modal="true" autoOpen="false" onClose="vr0202DoBack">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>场景编辑处理完成。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="vr0202-button-finish-confirm" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <!-- 场景删除 -->
    <imui:dialog id="vr0201DeleteFinishGotoVr0104" modal="true" autoOpen="false" >
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>场景删除成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-delete-goto-vr0104" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>
    <!-- 场景删除 -->
    <imui:dialog id="vr0201DeleteFinishGotoVr0206" modal="true" autoOpen="false" >
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>场景删除成功。</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="button-delete-goto-vr0206" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>

    <form id="vr0202Form" action="COMFileUpload/uploadAjax"  method="post" enctype="multipart/form-data">
        <input type="hidden" id="vr0202panoramaId" name="panoramaId" value="${panoramaId}"/>
        <input type="hidden" name="expositionName" value="${expositionName}"/>
        <input type="hidden" name="expositionId" id="vr0202expositionId" value="${expositionId}"/>
        <input type="hidden" id="vr0202panoramaPath" name="panoramaPath" value="${panoramaPath}"/>
        <input type="hidden" id="newPanoramaPath" name="newPanoramaPath" value="${newPanoramaPath}"/>
        <input type="hidden" id="materialIdFromIc0208" name="materialIdFromIc0208" value="${materialIdFromIc0208}"/>
        <input type="hidden" id="panoramaIdForDelete" name="panoramaIdForDelete" value="${panoramaIdForDelete}"/>
        <input type="hidden" id="flagFromParentPage" name="flagFromParentPage" value="${flagFromParentPage}"/>
        <input type="hidden" id="isStartScene" name="isStartScene" value="${isStartScene}"/>
        <input type="hidden" name="vr0202TheLastedSceneHotspotId" id="lastHotspotIdFromVr0104" value="${vr0202TheLastedSceneHotspotId}"/>
        <input type="hidden" name="vr0202CurrentRecommendHotspotId" id="currentHotspotIdFromVr0104" value="${vr0202CurrentRecommendHotspotId}"/>
        <input type="hidden" id="vr0202subFolderName" name="vr0202subFolderName" value="" />
        
        <table class="imui-form">
            <tr style="line-height: 23px;">
                <th style="width: 90px;"><label class="imui-required">场景名称:</label></th>
                <td><imui:textbox id="vr0202panoramaName" name="panoramaName" value="${panoramaName}" style="width:90%;" autofocus="true"/></td>
                <td></td>
                <td><input type="checkbox" id="startSceneFlag" name="startSceneFlag" value="1"/>首场景</td>
            </tr>
            <tr style="line-height: 23px;">
                <th style="width: 90px;"><label>当前场景音乐:</label></th>
                <td id="soundName"><label>${panoramaBackGroundSoundName}</label></td>
                <td></td>
                <td class="align-L valign-M" style="width:150px;" >
                   <input type="checkbox" id="vr0202PanoMusicSelect" name="panoMusicSelect" value="1">为当前场景设置音乐
                   &nbsp;&nbsp;
                   <input type="button" id="edittMusicButton" value="编辑场景音乐" class="imui-button imui-running-button" onclick="doSetPanoBackGroundSound()"/>
                </td>
            </tr>
            <tr>
                <th style="width: 80px;"><label>左眼变更前:</label></th>
                <td style="width: 40%;">
                    <div id="vr0202OldPano_L" style=" width:100%;height:300px;" >
                        <script type="text/javascript">
                            var xmlPath = $('#vr0202panoramaPath').val()+"/show_l.xml"
                            embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                                , xml:xmlPath
                                , target:"vr0202OldPano_L",wmode:"opaque"
                                , flash: "only" 
                                , bgcolor:"#f5f5f5"
                                , passQueryParameters:true});
                        </script>
                    </div>
                </td>
                <th><label>左眼变更后:</label></th>
                <td style="width: 40%;">
                    <div id="vr0202NewPano_L" style=" width:100%;height:300px;display:none;" >
                    </div>
                </td>
            </tr>
            <tr>
                <th><label>左眼场景编辑:</label></th>
                <td colspan="3" id="vr0202FileUpload_L_td">
                    <imui:fileUpload id="vr0202FileUpload_L" outerFormId="vr0202Form" onBeforeSend="callbackVr0202BeforeSend" onSuccess="callbackVr0202Success" onRemove="callbackVr0202Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="6" />
                    <input type="hidden" name="upload_key" value="${panoramaId}" />
                </td>
            </tr>
            
            <tr>
                <th style="width: 80px;"><label>右眼变更前:</label></th>
                <td style="width: 40%;">
                    <div id="vr0202OldPano_R" style=" width:100%;height:300px;" >
                        <script type="text/javascript">
                            var xmlPath = $('#vr0202panoramaPath').val()+"/show_r.xml"
                            embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                                , xml:xmlPath
                                , target:"vr0202OldPano_R",wmode:"opaque"
                                , flash: "only" 
                                , bgcolor:"#f5f5f5"
                                , passQueryParameters:true});
                        </script>
                    </div>
                </td>
                <th><label>右眼变更后:</label></th>
                <td style="width: 40%;">
                    <div id="vr0202NewPano_R" style=" width:100%;height:300px;display:none;" >
                    </div>
                </td>
            </tr>
            <tr>
                <th><label>右眼场景编辑:</label></th>
                <td colspan="3" id="vr0202FileUpload_R_td">
                    <imui:fileUpload id="vr0202FileUpload_R" inputName="local_file2" outerFormId="vr0202Form" onBeforeSend="callbackVr0202BeforeSend" onSuccess="callbackVr0202Success" onRemove="callbackVr0202Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="6" />
                </td>
            </tr>
            
            <tr>
                <th><label>说明:</label></th>
                <td colspan="3">
                    <imui:textArea name="notes" rows="10" style="width:98%" value="${notes}"></imui:textArea>
                </td>
        
             </tr>
        </table>
    
        <div class="imui-operation-parts">
              <input type="button" value="确定" id="button-vr0202-confirm" class="imui-medium-button" />
              &nbsp;&nbsp;
              <input type="button" value="删除当前场景" id="button-delete-map" class="imui-medium-button" />
        </div>
    </form>
    
</div>

<script type="text/javascript">
    // 文件上传按钮显示名字
    $("#vr0202FileUpload_L-add-files").text("添加图片");
    $("#vr0202FileUpload_R-add-files").text("添加图片");
</script>

