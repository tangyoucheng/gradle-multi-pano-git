<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 管理员密码变更 -->
<!DOCTYPE html>
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/ps99/ps990101.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <form:form modelAttribute="ps990101Form" class="form-horizontal">
                    <!-- 当前密码 -->
                    <div class="form-group form-row">
                        <label class="control-label text-right col-sm-1" for="txt_bankNameCn"> 当前密码 </label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="txt_bankNameCn" name="currentPassword"
                                value="${currentPassword}" maxlength="255" required="required">
                        </div>
                    </div>
                    <!-- 新密码 -->
                    <div class="form-group form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_bankNameEn"> 新密码 </label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="txt_bankNameEn" name="newPassword"
                                value="${newPassword}" maxlength="255" required="required">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <!-- 新密码(确认用) -->
                        <label class="col-form-label text-right col-sm-1" for="txt_bankCode"> 新密码(确认用) </label>
                        <div class="col-sm-3">
                            <input type="password" class="form-control" id="txt_bankCode" name="newPasswordConfirm"
                                value="${newPasswordConfirm}" maxlength="255" required="required">
                        </div>
                    </div>
                    <div class="form-row">
                        <label class="col-form-label text-right col-sm-1">&nbsp;</label>
                        <div class="col-sm-1">
                            <!-- 变更 -->
                            <button type="button" id="btn_update" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="变更"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>
