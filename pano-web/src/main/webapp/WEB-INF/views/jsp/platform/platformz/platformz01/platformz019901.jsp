<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 社区新增 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz01/platformz019901.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz019901Form" class="form-horizontal">
                    <!-- 社区名 -->
                    <div class="form-group form-row">
                    
                    <input type="hidden" id="returnTargetIframe" name="returnTargetIframe"
                        value="${platformz019901Form.returnTargetIframe }">
                        <!-- 社区层级 -->
                    <input type="hidden" name="departmentHierarchy" value="${platformz019901Form.departmentHierarchy}" />
                    
                        <label class="col-form-label text-right col-sm-1" for="txt_departmentName"> 社区名 </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_departmentName" name="departmentName"
                                value="${platformz019901Form.departmentName}" maxlength="255" required="required">
                        </div>
                     <!-- 上级社区名 -->
                        <label class="col-form-label col-sm-1" for="txt_parentDepartmentName">上级社区</label>
                        <div class="col-sm-4">
                            <input type="hidden" class="form-control" id="txt_parentDepartmentId" name="parentDepartmentId"
                                value="${platformz019901Form.parentDepartmentId}" maxlength="200" readonly="readonly">
                            <input type="text" class="form-control" id="txt_parentDepartmentName" name="parentDepartmentName"
                                value="${platformz019901Form.parentDepartmentName}" maxlength="200" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-auto">
                            <!-- 登录 -->
                            <button type="button" id="btn_entry" class="btn platform-btn-danger">
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