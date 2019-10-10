<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 管理员管理新增 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz01/platformz010101.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz010101Form" class="form-horizontal">
                    <!-- 用户登陆ID -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberLoginId">用户登陆ID</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberLoginId" name="memberLoginId"
                                value="${platformz010101Form.memberLoginId}" required="required">
                        </div>
                    </div>
                    <!-- 用户名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberName"> 用户名 </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberName" name="memberName"
                                value="${platformz010101Form.memberName}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 密码 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberPassword"> 密码 </label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="txt_memberPassword" name="memberPassword"
                                value="${platformz010101Form.memberPassword}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 角色 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode">角色</label>
                        <div class="col-sm-2">
                            <cis:select name="rolesId" id="select_member_role" style="height:100px;"
                                className="form-control" multiple="true" blank="false"></cis:select>
                        </div>
                        <div class="col-sm-1 btn-toolbar flex-column justify-content-between">
                            <div>
                                <button type="button" id="platformz010101Btn_addRole"
                                    class="btn platform-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div>
                                <button type="button" id="platformz010101Btn_deleteRole"
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
                                value="${platformz010101Form.memberEmail}" maxlength="255">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-auto">
                            <!-- 登录 -->
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
</body>
</html>