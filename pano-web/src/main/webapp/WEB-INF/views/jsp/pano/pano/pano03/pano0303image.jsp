<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="form-group form-row" id="div_previewMateria">
    <label class="col-form-label col-sm-1 text-left" for="txt_notes">变更前</label>
    <div class="col-sm-5">
        <img id="oldImage" src="${pano0303Form.oldmaterialPath}?temp=<%=Math.random()%>" style="width: 120px; height: 120px;" />
    </div>
</div>

<div class="form-group form-row" id="div_old_sprite_info" style="display: none;">
    <label class="col-form-label text-left col-sm-1">变更前帧动画:</label>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">帧宽</span>
        </div>
        <input class="form-control" type="text" id="txt_oldGifWidth" name="oldGifWidth" maxlength="3"
            value="${pano0303Form.oldGifWidth}" onblur="numFormat(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">像素</span>
        </div>
    </div>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">帧高</span>
        </div>
        <input class="form-control" type="text" id="txt_oldGifHeight" name="oldGifHeight" maxlength="3"
            value="${pano0303Form.oldGifHeight}" onblur="numFormat(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">像素</span>
        </div>
    </div>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">张数</span>
        </div>
        <input class="form-control" type="text" id="txt_oldGifFrameCount" name="oldGifFrameCount" maxlength="2"
            value="${pano0303Form.oldGifFrameCount}" onblur="numFormat(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">张</span>
        </div>
    </div>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">延迟</span>
        </div>
        <input class="form-control" type="text" id="txt_oldGifDelayTime" name="oldGifDelayTime" maxlength="5"
            value="${pano0303Form.oldGifDelayTime}" onblur="numFormatDecimal2(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">秒</span>
        </div>
    </div>
</div>
<div class="form-group form-row" id="div_previewMateria">
    <label class="col-form-label col-sm-1 text-left" for="txt_notes">变更后</label>
    <div class="col-sm-5">
        <img id="tagPicture" src="" style="width: 120px; height: 120px; display: none;" />
    </div>
</div>
<div class="form-group form-row" id="div_sprite_info" style="display: none;">
    <label class="col-form-label text-left col-sm-1">竖直帧动画:</label>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">帧宽</span>
        </div>
        <input class="form-control" type="text" id="txt_gifWidth" name="gifWidth" maxlength="3"
            value="${pano0303Form.gifWidth}" onblur="numFormat(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">像素</span>
        </div>
    </div>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">帧高</span>
        </div>
        <input class="form-control" type="text" id="txt_gifHeight" name="gifHeight" maxlength="3"
            value="${pano0303Form.gifHeight}" onblur="numFormat(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">像素</span>
        </div>
    </div>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">张数</span>
        </div>
        <input class="form-control" type="text" id="txt_gifFrameCount" name="gifFrameCount" maxlength="2"
            value="${pano0303Form.gifFrameCount}" onblur="numFormat(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">张</span>
        </div>
    </div>
    <div class="col-sm-2 input-group input-group-sm">
        <div class="input-group-prepend d-inline">
            <span class="input-group-text">延迟</span>
        </div>
        <input class="form-control" type="text" id="txt_gifDelayTime" name="gifDelayTime" maxlength="5"
            value="${pano0303Form.gifDelayTime}" onblur="numFormatDecimal2(this);" />
        <div class="input-group-append d-inline">
            <span class="input-group-text">秒</span>
        </div>
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
                        <input class="file_input" id="imageFilesInput" multiple="" name="pano0303UploadFile" type="file">
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

