<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 社区编辑 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/platform01/platform019902.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platform019902Form" class="form-horizontal">
                    <div class="form-group form-row">
                        <!-- 社区Id -->
                        <input type="hidden" class="form-control" id="txt_departmentId" name="departmentId"
                            value="${platform019902Form.departmentId}">
                        <!-- 社区名 -->
                        <label class="col-form-label text-right col-sm-1" for="txt_departmentName"> 社区名 </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_departmentName" name=departmentName
                                value="${platform019902Form.departmentName}" maxlength="255" required="required">
                        </div>
                        <!-- 上级社区名 -->
                        <label class="col-form-label col-sm-1" for="txt_parentDepartmentName">上级社区</label>
                        <div class="col-sm-4">
                            <input type="hidden" class="form-control" id="txt_parentDepartmentId"
                                name="parentDepartmentId" value="${platform019902Form.parentDepartmentId}" maxlength="200"
                                readonly="readonly">
                            <input type="text" class="form-control" id="txt_parentDepartmentName"
                                name="parentDepartmentName" value="${platform019902Form.parentDepartmentName}"
                                maxlength="200" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-auto">
                            <!-- 登录 -->
                            <button type="button" id="btn_update" class="btn platform-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="登录"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>