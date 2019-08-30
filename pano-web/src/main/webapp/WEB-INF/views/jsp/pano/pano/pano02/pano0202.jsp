<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<link href="static/platform/common/css/publish.css" rel="stylesheet" type="text/css" />
<script src="static/pano/pano/js//pano02/pano0202.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0202FormAdd" class="form-horizontal" modelAttribute="pano0202Form">
                    <input type="hidden" name="panoramaId" value="${pano0202Form.panoramaId}" />
                    <input type="hidden" name="expositionId" value="${pano0202Form.expositionId}" />
                    <input type="hidden" name="panoramaSoundId" value="${pano0201Form.panoramaSoundId}" />
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0202Form.panoramaName}" required>
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_thumbNote">缩略图信息</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_thumbNote" name="thumbNote"
                                value="${pano0202Form.thumbNote}">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaSoundName">当前场景音乐</label>
                        <div class="col-sm-4 input-group">
                            <input class="form-control rounded" id="txt_panoramaSoundName" name="panoramaSoundName"
                                type="text" value="${panoramaSoundName }" readonly="readonly" />
                            <div class="input-group-append" style="height: calc(1.5em + .75rem + 2px);">
                                <button type="button" id="btn_panoramaSound_search" class="btn pano-btn-danger">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left">变更前</label>
                        <div class="col-sm-5">
                            <div id="pano0202OldPano" style="width: 280px; height: 220px;"></div>
                        </div>
                        <label class="col-form-label col-sm-1 text-left">变更后</label>
                        <div class="col-sm-5">
                            <div id="pano0202NewPano" style="width: 280px; height: 220px;"></div>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">场景登记</label>
                        <div class="col-sm-11">
                            <div class=" h_pic_list clearfix">
                                <ul id="imageBox">
                                    <c:forEach var="ImgData" items="${pano0202FormTODO.houseImgList }">
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
                                            <input class="file_input" id="filesInput" multiple="" name="panoImg"
                                                type="file" accept="image/jpg,image/jpeg">
                                            <div class="h_add_pic layui-upload-drag pl-0 pr-0">
                                                <i class="layui-icon">&#xe67c;</i>
                                                <br>
                                                <c:out value="点击上传图片（可多选）"></c:out>
                                                <strong>
                                                    <font class="text-danger">(&nbsp;jpg&nbsp;jpeg&nbsp;)</font>
                                                </strong>
                                                <br>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
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
                        <div class="col-sm-9">
                            <button type="button" id="btn_entry" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="登录"></c:out>
                            </button>
                            <button type="button" id="btn_delete" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="删除"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- submit处理 START -->
    <form id="pano0202FormUpdate" action="">
        <input type="hidden" name="panoramaId" value="${pano0202Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0202Form.expositionId}" />
        <input type="hidden" name="panoramaSoundId" value="${pano0201Form.panoramaSoundId}" />
    </form>
    <!-- submit处理 END -->
</body>
</html>


