<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 场景上地图登录 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<link href="static/platform/common/css/publish.css" rel="stylesheet" type="text/css" />
<script src="static/pano/pano/js//pano03/pano0304.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0304FormAdd" class="form-horizontal" modelAttribute="pano0304Form">
                    <input type="hidden" name="panoramaId" value="${pano0304Form.panoramaId}" />
                    <input type="hidden" name="expositionId" value="${pano0304Form.expositionId}" />
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">所在展览名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${pano0304Form.expositionName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionMapId">场景地图ID</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_expositionMapId" name="expositionMapId"
                                value="${pano0304Form.expositionMapId}" readonly="readonly">
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionMapName">场景地图名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_expositionMapName" name="expositionMapName"
                                value="${pano0304Form.expositionMapName}">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">地图</label>
                        <div class="col-sm-11">
                            <div class="h_pic_list clearfix">
                                <ul id="imageBox">
                                    <c:forEach var="ImgData" items="${pano0304FormTODO.houseImgList }">
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
                                                type="file" accept="image/jpg,image/jpeg,image/png">
                                            <div class="h_add_pic layui-upload-drag pl-0 pr-0">
                                                <i class="layui-icon">&#xe67c;</i>
                                                <br>
                                                <c:out value="点击上传图片"></c:out>
                                                <strong>
                                                    <font class="text-danger">(&nbsp;jpg&nbsp;jpeg&nbsp;png&nbsp;)</font>
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
                        <label class="col-form-label col-sm-1 text-left" for="txt_notes">备注</label>
                        <div class="col-sm-5">
                            <form:textarea id="txt_notes" path="notes" cssClass="form-control" htmlEscape="true"
                                maxlength="2000" rows="3" placeholder="" style="overflow: auto;" />
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-11">
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
    <form id="pano0304FormUpdate" action="">
        <input type="hidden" name="panoramaId" value="${pano0304Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0304Form.expositionId}" />
    </form>
    <!-- submit处理 END -->
</body>
</html>
