<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司用户新增 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz02/platformz020101.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz020101Form" class="form-horizontal">
                    <!-- 用户登陆ID -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberLoginId">用户登陆ID</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberLoginId" name="memberLoginId"
                                value="${platformz020101Form.memberLoginId}" required="required">
                        </div>
                    </div>
                    <!-- 用户名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberName"> 用户名 </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberName" name="memberName"
                                value="${platformz020101Form.memberName}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 密码 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberPassword"> 密码 </label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="txt_memberPassword" name="memberPassword"
                                value="${platformz020101Form.memberPassword}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 角色 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode">公司角色</label>
                        <div class="col-sm-2">
                            <cis:select name="rolesId" id="select_role" style="height:100px;"
                                className="form-control" multiple="true" blank="false"></cis:select>
                        </div>
                        <div class="col-sm-1 btn-toolbar flex-column justify-content-between">
                            <div>
                            <!-- 公司角色添加 -->
                                <button type="button" id="platformz020101Btn_addRole"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div>
                            <!-- 公司角色删除 -->
                                <button type="button" id="platformz020101Btn_deleteRole"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-minus"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- 社区 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode">社区选择</label>
                        <div class="col-sm-2">
                            <cis:select name="departmentsId" id="select_department" style="height:100px;"
                                className="form-control" multiple="true" blank="false"></cis:select>
                        </div>
                        <div class="col-sm-1 btn-toolbar flex-column justify-content-between">
                            <div>
                            <!-- 社区添加 -->
                                <button type="button" id="platformz020101Btn_addDepartment"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div>
                            <!-- 社区删除 -->
                                <button type="button" id="platformz020101Btn_deleteDepartment"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-minus"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- 邮箱 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberEmail"> 邮箱 </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberEmail" name="memberEmail"
                                value="${platformz020101Form.memberEmail}" maxlength="255">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-4">
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