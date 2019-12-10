<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="form-group form-row" id="div_previewMateria">
    <label class="col-form-label col-sm-1 text-left" for="txt_notes">变更前图文</label>
    <div class="col-sm-3">
        <img id="oldImage" src="${pano0303Form.oldmaterialPath}?temp=<%=Math.random()%>"
            style="width: 120px; height: 120px;" />
    </div>
    <div class="col-sm-3">
        <form:textarea id="txt_notes" path="oldTextInfo" cssClass="form-control" htmlEscape="true" maxlength="2000"
            rows="3" placeholder="" style="overflow: auto;" readonly="true" />
    </div>
</div>

<div class="form-group form-row" id="div_previewMateria">
    <label class="col-form-label col-sm-1 text-left" for="txt_notes">变更后图文</label>
    <div class="col-sm-3">
        <img id="tagPicture" src="" style="width: 120px; height: 120px; display: none;" />
    </div>
    <div class="col-sm-3">
        <form:textarea id="txt_notes" path="textInfo" cssClass="form-control" htmlEscape="true" maxlength="2000"
            rows="3" placeholder="" style="overflow: auto;" />
    </div>
</div>

<div class="form-group form-row" id="material_file_upload_tr">
    <label class="col-form-label col-sm-1 text-left" for="txt_source">文件上传</label>
    <div class="col-sm-11">
        <div class="h_pic_list clearfix">
            <ul id="imageBox">
                <c:forEach var="ImgData" items="${pano0201FormTODO.houseImgList }">
                    <li class="success_show">
                        <div class="img_btm_bg clearfix" id="${ImgData.filePath }" onclick="removeImg(this)">
                            <span class="delete_text">删除</span>
                        </div>
                        <div class="room_img_show">
                            <img src='<c:out value="${ImgData.filePath }"></c:out>?v=<%=Math.random()%>'>
                        </div>
                    </li>
                </c:forEach>
                <li id="thumbnails">
                    <div id="fileNow" style="display: block;">
                        <input class="file_input" id="filesInput" multiple="" name="pano0303UploadFile" type="file">
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

