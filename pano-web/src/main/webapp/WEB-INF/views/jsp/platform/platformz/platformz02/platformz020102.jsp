<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司用户编辑-->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz02/platformz020102.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz020102Form" class="form-horizontal">
                    <!-- Id -->
                    <input type="hidden" class="form-control" id="txt_memberId" name="memberId"
                        value="${platformz020102Form.memberId}">
                    <!-- 用户登陆ID -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberLoginId">公司用户登陆ID</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberLoginId" name="memberLoginId"
                                value="${platformz020102Form.memberLoginId}" readonly="readonly">
                        </div>
                    </div>
                    <!-- 用户名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberName">用户名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberName" name="memberName"
                                value="${platformz020102Form.memberName}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 公司角色 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode">角色</label>
                        <div class="col-sm-2">
                            <cis:select name="rolesId" id="select_role" list="${platformz020102Form.rolesInfo }"
                                style="height:100px;" className="form-control" multiple="true" blank="false"></cis:select>
                        </div>
                        <div class="col-sm-1 btn-toolbar flex-column justify-content-between">
                            <div>
                                <button type="button" id="platformz020102Btn_addRole"
                                    class="btn cis-btn-blue font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div>
                                <button type="button" id="platformz020102Btn_deleteRole"
                                    class="btn cis-btn-blue font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-minus"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- 社区 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode">社区选择</label>
                        <div class="col-sm-2">
                            <cis:select name="departmentsId" id="select_department" list="${platformz020102Form.departmentsInfo }" style="height:100px;"
                                className="form-control" multiple="true" blank="false"></cis:select>
                        </div>
                        <div class="col-sm-1 btn-toolbar flex-column justify-content-between">
                            <div>
                            <!-- 社区添加 -->
                                <button type="button" id="platformz020102Btn_addDepartment"
                                    class="btn ciicsc-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <div>
                            <!-- 社区删除 -->
                                <button type="button" id="platformz020102Btn_deleteDepartment"
                                    class="btn ciicsc-btn-danger font-12 p-1 row-edit">
                                    <i class="glyphicon glyphicon-minus"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- 邮箱 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_memberEmail">邮箱</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberEmail" name="memberEmail"
                                value="${platformz020102Form.memberEmail}" maxlength="255">
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