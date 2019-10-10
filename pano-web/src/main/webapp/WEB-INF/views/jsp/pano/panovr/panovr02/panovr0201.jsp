<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景登记 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/panovr02/panovr0201.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0102FormSearch" class="form-horizontal" modelAttribute="pano0102Form">
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionId">编号</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionId" name="expositionId"
                                value="${expositionId}" required>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${expositionName}">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionStartDate">开展时间</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionStartDate"
                                name="expositionStartDate" required readonly="readonly">
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionEndDate">撤展时间</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionEndDate" name="expositionEndDate"
                                required readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">状态</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="expositionStatus" name="expositionStatus"
                                selectedValue="${pano0102Form.expositionStatus}"
                                list="${pano0102Form.expositionStatusList}" className="form-control" blank="false" />
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">类型</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="expositionType" name="expositionType"
                                selectedValue="${pano0102Form.expositionType}" list="${pano0102Form.expositionTypeList}"
                                className="form-control" blank="false" />
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-5">
                            <button type="button" id="btn-panovr0201-confirm" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-search"></span>
                                <c:out value="确定"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <!-- submit处理 START -->
    <form method="POST" action="" id="goto-ic0103-form"></form>
    <form id="pano0102FormAdd" action="">
        <input type="hidden" name="startDate" value="${pano0102Form.expositionStartDate}" />
        <input type="hidden" name="endDate" value="${pano0102Form.expositionEndDate}" />
    </form>

    <!-- submit处理 END -->
</body>

</html>





<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr02.Vr0201Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp"%>

<imui:head>
    <title>场景登记</title>
</imui:head>
<script src="product_vr/js/vr02/vr0201.js"></script>

<div id="vr0201SetSound"></div>
<imui:dialog id="vr0201AddFinish" modal="true" autoOpen="false" onClose="vr0201DoBack">
    <div class="imui-form-container-narrow" style="border: none;">
        <h2>场景信息登记完成。</h2>
        <div class="imui-operation-parts">
            <input type="button" value="确定" id="vr0201-button-finish-confirm" class="imui-medium-button" />
        </div>
    </div>
</imui:dialog>


<div class="imui-form-container" style="width: 95%;">

    <form id="vr0201Form" action="COMFileUpload/uploadAjax" method="post" enctype="multipart/form-data">
        <input type="hidden" id="vr0201_panoramaId" name="panoramaId" value="${panoramaId}" />
        <input type="hidden" id="expositionId" name="expositionId" value="${expositionId}" />
        <input type="hidden" id="panoramaSortKey" name="panoramaSortKey" value="${panoramaSortKey}" />
        <input type="hidden" id="materialIdFromIc0208" name="materialIdFromIc0208" value="${materialIdFromIc0208}" />
        <input type="hidden" id="panoCheck"  value="" />
        <input type="hidden" id="subFolderName" name="subFolderName" value="" />
        
        <table class="imui-form" style="height: 50px">
                <tr style="line-height: 23px;">
                    <th style="width:120px"><label class="imui-required">场景名称:</label></th>
                    <td><input name="panoramaName" id="vr0201_panoramaName" value="${panoramaName}" style="width:45%;"/></td>
                    <td><input type="checkbox" id="startSceneFlag" name="startSceneFlag" value="1"/>首场景</td>
                </tr>
                
                <tr style="line-height: 23px;">
                    <th><label>当前场景音乐:</label></th>
                    <td id="soundName" style="width:45%;"><label></label></td>
                    <td >
                       <input type="checkbox" id="vr0201PanoMusicSelect" name="panoMusicSelect" value="1">为当前场景设置音乐
                       &nbsp;&nbsp;
                       <input type="button" id="vr0201SelectMusicButton" value="选择场景音乐" class="imui-button imui-running-button" onclick="doSetPanoBackGroundSound()"/>
                    </td>
                </tr>
                <tr>
                    <th><label>左眼场景预览:</label></th>
                       <td colspan="2">
                         <div id="vr0201NewPano_L" style=" width:280px;height:220px;display:none;" >
                         </div>
                      </td>
                </tr>
                <tr>
                    <th ><label>左眼场景登记:</label></th>
                    <td colspan="2" id="vr0201FileUpload_L_td">
                        <imui:fileUpload id="vr0201FileUpload_L" inputName="local_file" outerFormId="vr0201Form" onBeforeSend="callbackVr0201BeforeSend" onSuccess="callbackVr0201Success" onRemove="callbackVr0201Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="6"/>
                        <input  type="hidden" name="upload_key" value="${panoramaId}"/>
                    </td>
                </tr>
                <tr>
                    <th><label>右眼场景预览:</label></th>
                       <td colspan="2">
                         <div id="vr0201NewPano_R" style=" width:280px;height:220px;display:none;" >
                         </div>
                      </td>
                </tr>
                <tr>
                    <th ><label>右眼场景登记:</label></th>
                    <td colspan="2" id="vr0201FileUpload_R_td">
                        <imui:fileUpload id="vr0201FileUpload_R" inputName="local_file2" outerFormId="vr0201Form" onBeforeSend="callbackVr0201BeforeSend" onSuccess="callbackVr0201Success" onRemove="callbackVr0201Remove" enableDelete="true" autoUpload="true" fileNameInputWidth="300" maxNumberOfFiles="6"/>
                    </td>
                </tr>
                <tr>
                    <th><label>说明:</label></th>
                    <td colspan="2">
                    <textarea name="notes" id="vr0201Notes" style="width: 98%;height:80px;"  rows="10"></textarea>
                </td>
            </tr>
        </table>
        <div class="imui-operation-parts">
             <input type="button" value="确定" id="button-vr0201-confirm" class="imui-medium-button" />
        </div>
    </form>
</div>
    
<!-- submit处理 START -->

<!-- submit处理 END -->

<script type="text/javascript">
    // 文件上传显示名字更改
    $("#vr0201FileUpload_L-add-files").text("添加图片");
    $("#vr0201FileUpload_R-add-files").text("添加图片");
</script>
