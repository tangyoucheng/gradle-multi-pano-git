<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 管理员管理编辑-->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/platform02/platform020102.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platform020102Form" class="form-horizontal">
                    <!-- Id -->
                    <input type="hidden" class="form-control" id="txt_adminId" name="adminId"
                        value="${platform020102Form.adminId}">
                    <!-- 用户登陆ID -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_adminLoginId">用户登陆ID</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_adminLoginId" name="adminLoginId"
                                value="${platform020102Form.adminLoginId}" readonly="readonly">
                        </div>
                    </div>
                    <!-- 用户名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_adminName">用户名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_adminName" name="adminName"
                                value="${platform020102Form.adminName}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 角色 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode">角色</label>
                        <div class="col-sm-2">
                            <platform:select name="rolesId" id="select_admin_role" list="${platform020102Form.rolesInfo }"
                                style="height:100px;" className="form-control" multiple="true" blank="false"></platform:select>
                        </div>
                        <div class="col-sm-1 btn-toolbar flex-column justify-content-between">
                            <div>
                                <button type="button" id="platform020102Btn_addRole"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div>
                                <button type="button" id="platform020102Btn_deleteRole"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-minus"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- 邮箱 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_adminEmail">邮箱</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_adminEmail" name="adminEmail"
                                value="${platform020102Form.adminEmail}" maxlength="255">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-3">
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