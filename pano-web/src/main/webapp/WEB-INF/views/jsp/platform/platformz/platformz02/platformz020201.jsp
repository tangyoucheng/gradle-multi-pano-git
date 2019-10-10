<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司角色管理新增 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz02/platformz020201.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz020201Form" class="form-horizontal">
                    <!-- 角色名 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_roleName">公司角色名 </label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_roleName" name="roleName"
                                value="${platformz020201Form.roleName}" maxlength="255" required="required">
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