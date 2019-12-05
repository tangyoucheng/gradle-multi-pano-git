<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景的素材登记 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<link href="static/platform/common/css/publish.css" rel="stylesheet" type="text/css" />
<script src="static/pano/pano/js/pano03/pano0301.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0301FormAdd" class="form-horizontal" modelAttribute="pano0301Form">
                    <input type="hidden" name="expositionId" value="${pano0301Form.expositionId}" />
                    <input type="hidden" name="currentPanoramaId" value="${pano0301Form.currentPanoramaId}" />
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialId">编号</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_materialId" name="materialId"
                                value="${pano0301Form.materialId}" required>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialName">名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_materialName" name="materialName"
                                value="${pano0301Form.materialName}" required>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">展览名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${pano0301Form.expositionName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialBelongType">素材归属</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="materialBelongType" name="materialBelongType"
                                selectedValue="${pano0301Form.materialBelongType}"
                                list="${pano0301Form.materialBelongTypeList}" className="form-control" blank="false" />
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_hotspotTypeChoice">类型</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="materialTypeId" name="materialTypeId"
                                selectedValue="${pano0301Form.materialTypeId}" list="${pano0301Form.materialTypeList}"
                                className="form-control" blank="false" />
                        </div>
                    </div>
                    <div class="form-group form-row" id="material_file_upload_tr">
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">文件上传</label>
                        <div class="col-sm-11">
                            <div class="h_pic_list clearfix">
                                <ul id="imageBox">
                                    <c:forEach var="ImgData" items="${pano0201FormTODO.houseImgList }">
                                        <li class="success_show">
                                            <div class="img_btm_bg clearfix" id="${ImgData.filePath }"
                                                onclick="removeImg(this)">
                                                <span class="delete_text">删除</span>
                                            </div>
                                            <div class="room_img_show">
                                                <img
                                                    src='<c:out value="${ImgData.filePath }"></c:out>?v=<%=Math.random()%>'>
                                            </div>
                                        </li>
                                    </c:forEach>
                                    <li id="thumbnails">
                                        <div id="fileNow" style="display: block;">
                                            <input class="file_input" id="filesInput" multiple=""
                                                name="pano0301UploadFile" type="file">
                                            <div class="h_add_pic layui-upload-drag pl-0 pr-0">
                                                <i class="layui-icon">&#xe67c;</i>
                                                <br>
                                                <c:out value="点击上传文件"></c:out>
                                                <br>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row" id="div_text_flow_info">
                        <label class="col-form-label col-sm-1 text-left" for="txt_textflowInfo">浮动信息(文字)内容</label>
                        <div class="col-sm-5 input-group">
                            <input type="text" class="form-control" id="txt_textflowInfo" name="textflowInfo"
                                value="${pano0301Form.textflowInfo}" required>
                            <div class="input-group-append input-group-append-height-sm">
                                <button type="button" id="pano0301Btn_searchBankAccount" class="btn pano-btn-danger">
                                    <i class="glyphicon glyphicon-eye-open"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row" id="div_previewMateria">
                        <label class="col-form-label col-sm-1 text-left" for="txt_notes">预览</label>
                        <div class="col-sm-5">
                            <img id="tagPicture" src="" style="width: 120px; height: 120px; display: none;" />
                        </div>
                    </div>
                    <div class="form-group form-row" id="div_previewText">
                        <label class="col-form-label col-sm-1 text-left" for="txt_notes">预览</label>
                        <div class="col-sm-5">
                            <div id="textDiv" class="align-C"></div>
                        </div>
                    </div>
                    <div class="form-group form-row" id="div_sounds">
                        <label class="col-form-label col-sm-1 text-left" for="txt_notes">预览</label>
                        <div class="col-sm-5">
                            <div id="soundPrevPano" style="width: 100%; height: 300px;">
                                <script type="text/javascript">
                                                                                                                                    embedpano({
                                                                                                                                        swf : PanoConstants.VAL_EMBEDPANO_SWF,
                                                                                                                                        xml : "static/pano/pano/common/template/sound/soundpreview.xml",
                                                                                                                                        id : "pano0301KrpanoSWFObject",
                                                                                                                                        target : "soundPrevPano",
                                                                                                                                        wmode : "opaque",
                                                                                                                                        flash : "only",
                                                                                                                                        passQueryParameters : true
                                                                                                                                    });
                                                                                                                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row" id="div_text_info">
                        <label class="col-form-label col-sm-1 text-left" for="txt_textInfo">文本</label>
                        <div class="col-sm-5">
                            <form:textarea id="txt_notes" path="textInfo" cssClass="form-control" htmlEscape="true"
                                maxlength="2000" rows="3" placeholder="" style="overflow: auto;" />
                        </div>
                    </div>
                    <div class="form-group form-row" id="div_sprite_info" style="display: none;">
                        <label class="col-form-label text-left col-sm-1">竖直帧动画:</label>
                        <div class="col-sm-2 input-group input-group-sm">
                            <div class="input-group-prepend d-inline">
                                <span class="input-group-text">帧宽</span>
                            </div>
                            <input class="form-control" type="text" id="txt_gifWidth" name="gifWidth" maxlength="3"
                                value="${pano0301Form.gifWidth}" onblur="numFormat(this);" />
                            <div class="input-group-append d-inline">
                                <span class="input-group-text">像素</span>
                            </div>
                        </div>
                        <div class="col-sm-2 input-group input-group-sm">
                            <div class="input-group-prepend d-inline">
                                <span class="input-group-text">帧高</span>
                            </div>
                            <input class="form-control" type="text" id="txt_gifHeight" name="gifHeight" maxlength="3"
                                value="${pano0301Form.gifHeight}" onblur="numFormat(this);" />
                            <div class="input-group-append d-inline">
                                <span class="input-group-text">像素</span>
                            </div>
                        </div>
                        <div class="col-sm-2 input-group input-group-sm">
                            <div class="input-group-prepend d-inline">
                                <span class="input-group-text">张数</span>
                            </div>
                            <input class="form-control" type="text" id="txt_gifFrameCount" name="gifFrameCount" maxlength="2"
                                value="${pano0301Form.gifFrameCount}" onblur="numFormat(this);" />
                            <div class="input-group-append d-inline">
                                <span class="input-group-text">张</span>
                            </div>
                        </div>
                        <div class="col-sm-2 input-group input-group-sm">
                            <div class="input-group-prepend d-inline">
                                <span class="input-group-text">延迟</span>
                            </div>
                            <input class="form-control" type="text" id="txt_gifDelayTime" name="gifDelayTime" maxlength="5"
                                value="${pano0301Form.gifDelayTime}" onblur="numFormatDecimal2(this);" />
                            <div class="input-group-append d-inline">
                                <span class="input-group-text">秒</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_notes">说明</label>
                        <div class="col-sm-5">
                            <form:textarea id="txt_notes" path="notes" cssClass="form-control" htmlEscape="true"
                                maxlength="2000" rows="3" placeholder="" style="overflow: auto;" />
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-5">
                            <button type="button" id="btn_entry" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="登录"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- submit处理 START -->
    <form:form id="pano0301FormAjaxSubmit" action="/" modelAttribute="pano0301Form">
        <input type="hidden" name="expositionId" value="${pano0301Form.expositionId}" />
        <input type="hidden" name="currentPanoramaId" value="${pano0301Form.currentPanoramaId}" />
        <input type="hidden" name="materialId" value="${pano0301Form.materialId}" />
    </form:form>
    <!-- submit处理 END -->
</body>
</html>
