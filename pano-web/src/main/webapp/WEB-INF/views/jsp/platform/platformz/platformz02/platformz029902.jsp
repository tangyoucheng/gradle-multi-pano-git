<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司编辑 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz02/platformz029902.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz029902Form" class="form-horizontal">
                    <input type="hidden" name="editBeforeDataJson" value='${platformz029902Form.editBeforeDataJson}'>
                    <!-- 公司Id -->
                    <input type="hidden" class="form-control" id="txt_companyId" name="companyId"
                        value="${platformz029902Form.companyId}">
                    <!-- 公司名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_companyName">公司名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_companyName" name="companyName"
                                value="${platformz029902Form.companyName}" maxlength="255" required="required">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-auto">
                            <!-- 更新 -->
                            <button type="button" id="btn_update" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="更新"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>