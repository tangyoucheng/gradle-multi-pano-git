<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 会员查看 -->
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
                    <div class="form-group form-row">
                        <div class="col-sm-6">
                            <!-- 会员ID -->
                            <input type="hidden" class="form-control" id="txt_memberId" name="memberId"
                                value="${platformz020101Form.memberId}" required="required">
                            <!-- 会员名 -->
                            <div class="form-group form-row">
                                <label class="col-form-label text-right col-sm-2" for="txt_memberName"> 会员名： </label>
                                <div class="col-form-label text-left col-sm-6">
                                    <label>${platformz020101Form.memberName}</label>
                                </div>
                            </div>
                            <!-- 登录编号 -->
                            <div class="form-group form-row">
                                <label class="col-form-label text-right col-sm-2" for="txt_memberLoginId"> 登录编号：
                                </label>
                                <div class="col-form-label text-left col-sm-6">
                                    <label>${platformz020101Form.memberLoginId}</label>
                                </div>
                            </div>
                            <!-- 邮箱 -->
                            <div class="form-group form-row">
                                <label class="col-form-label text-right col-sm-2" for="txt_memberEmail"> 邮箱 ：</label>
                                <div class="col-form-label text-left col-sm-6">
                                    <label>${platformz020101Form.memberEmail}</label>
                                </div>
                            </div>
                            <!-- 手机号 -->
                            <div class="form-group form-row">
                                <label class="col-form-label text-right col-sm-2" for="txt_memberPhone"> 手机号 :</label>
                                <div class="col-form-label text-left col-sm-6">
                                    <label>${platformz020101Form.memberPhone}</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <cis:profilePicture id="memberProfilePicture" style="width:150px; height:150px;"
                                src="${platformz020101Form.memberProfilePicture }"></cis:profilePicture>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>