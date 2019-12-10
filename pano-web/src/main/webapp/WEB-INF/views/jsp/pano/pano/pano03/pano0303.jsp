<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<%@ page import="cn.com.platform.framework.code.*"%>
<!-- 场景的素材编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<link href="static/platform/common/css/publish.css" rel="stylesheet" type="text/css" />
<script src="static/pano/pano/js/pano03/pano0303.js"></script>
<script src="static/pano/pano/js/pano03/pano0303sound.js"></script>
<script src="static/pano/pano/js/pano03/pano0303video.js"></script>
<script src="static/pano/pano/js/pano03/pano0303text.js"></script>
<script src="static/pano/pano/js/pano03/pano0303image.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0303FormAdd" class="form-horizontal" modelAttribute="pano0303Form">
                    <input type="hidden" name="expositionId" value="${pano0303Form.expositionId}" />
                    <input type="hidden" name="currentPanoramaId" value="${pano0303Form.currentPanoramaId}" />
                    <input type="hidden" name="materialId" value="${pano0303Form.materialId}" />
                    <input type="hidden" name="materialBelongType" value="${pano0303Form.materialBelongType}" />
                    <input type="hidden" name="materialTypeId" value="${pano0303Form.materialTypeId}" />
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialName">名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="txt_materialName" name="materialName"
                                value="${pano0303Form.materialName}" required>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">展览名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${pano0303Form.expositionName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialBelongType">素材归属</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="materialBelongType" name="materialBelongType"
                                selectedValue="${pano0303Form.materialBelongType}"
                                list="${pano0303Form.materialBelongTypeList}" className="form-control" blank="false"
                                disabled="true" />
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_hotspotTypeChoice">类型</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="materialTypeId" name="materialTypeId"
                                selectedValue="${pano0303Form.materialTypeId}" list="${pano0303Form.materialTypeList}"
                                className="form-control" blank="false" disabled="true" />
                        </div>
                    </div>
                    <!--素材个别属性编辑区  START-->
                    <c:choose>
                        <%--声音的场合--%>
                        <c:when test="${pano0303Form.materialTypeId eq MaterialType.SOUND.toString()}">
                            <%@ include file="pano0303sound.jsp"%>
                        </c:when>
                        <%--视频的场合--%>
                        <c:when test="${pano0303Form.materialTypeId eq MaterialType.VIDEO.toString()}">
                            <%@ include file="pano0303video.jsp"%>
                        </c:when>
                        <%--文字浮动信息层的场合--%>
                        <c:when test="${pano0303Form.materialTypeId eq MaterialType.FLOW_INFO_TEXT.toString()}">
                            <%@ include file="pano0303flowtext.jsp"%>
                        </c:when>
                        <%--图文信息的场合--%>
                        <c:when test="${pano0303Form.materialTypeId eq MaterialType.IMAGE_TEXT.toString()}">
                            <%@ include file="pano0303imagetext.jsp"%>
                        </c:when>
                        <%--上记以外的场合--%>
                        <c:otherwise>
                            <%@ include file="pano0303image.jsp"%>
                        </c:otherwise>
                    </c:choose>
                    <!--素材个别属性编辑区  END-->
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_notes">说明</label>
                        <div class="col-sm-6">
                            <form:textarea id="txt_notes" path="notes" cssClass="form-control" htmlEscape="true"
                                maxlength="2000" rows="3" placeholder="" style="overflow: auto;" />
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-6">
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
    <form:form id="pano0303FormAjaxSubmit" action="/" modelAttribute="pano0303Form">
        <input type="hidden" name="expositionId" value="${pano0303Form.expositionId}" />
        <input type="hidden" name="currentPanoramaId" value="${pano0303Form.currentPanoramaId}" />
        <input type="hidden" name="materialId" value="${pano0303Form.materialId}" />
    </form:form>
    <!-- submit处理 END -->
</body>
</html>
