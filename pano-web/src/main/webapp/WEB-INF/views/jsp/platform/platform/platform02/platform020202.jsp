<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 角色管理编辑 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/platform02/platform020202.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platform020202Form" class="form-horizontal">
                    <!-- 角色Id -->
                    <input type="hidden" class="form-control" id="txt_roleId" name="roleId"
                        value="${platform020202Form.roleId}">
                    <!-- 角色名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_roleName">角色名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_roleName" name="roleName"
                                value="${platform020202Form.roleName}" maxlength="255" required="required">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-1">
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