<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 导航图编辑画面 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<link href="static/platform/common/css/publish.css" rel="stylesheet" type="text/css" />
<script src="static/pano/pano/js//pano03/pano0306.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0306FormAdd" class="form-horizontal" modelAttribute="pano0306Form">
                    <input type="hidden" name="pano0306ExpositionId" value="${pano0306Form.pano0306ExpositionId}" />
                    <input type="hidden" name="pano0306ExpositionMapId" value="${pano0306Form.pano0306ExpositionMapId}" />
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_pano0306ExpositionMapName">导航图名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_pano0306ExpositionMapName"
                                name="pano0306ExpositionMapName" value="${pano0306Form.pano0306ExpositionMapName}"
                                required="required"">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_pano0306ExpositionMapName">变更前</label>
                        <div class="col-sm-11">
                            <div id="pano0306OldMapDiv" style="width: 100%; height: 260px;">
                                <img id="pano0306OldMapPreview" src="${pano0306Form.pano0306ExpositionMapPath}"
                                    style="width: 300px; height: 250px;" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_pano0306ExpositionMapName">变更后</label>
                        <div class="col-sm-11">
                            <div class="h_pic_list clearfix">
                                <ul id="imageBox">
                                    <c:forEach var="ImgData" items="${pano0306FormTODO.houseImgList }">
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

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-11">
                            <button type="button" id="button-pano0306-update" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-update" aria-hidden="true"></span>
                                <c:out value="更新"></c:out>
                            </button>
                            <button type="button" id="button-pano0306-delete" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-delete" aria-hidden="true"></span>
                                <c:out value="删除"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- submit处理 START -->
    <form:form id="pano0306FormUpdate" modelAttribute="pano0306Form">
        <input type="hidden" name="pano0306ExpositionId" value="${pano0306Form.pano0306ExpositionId}" />
        <input type="hidden" name="pano0306ExpositionMapId" value="${pano0306Form.pano0306ExpositionMapId}" />
    </form:form>
    <!-- submit处理 END -->
</body>
</html>


