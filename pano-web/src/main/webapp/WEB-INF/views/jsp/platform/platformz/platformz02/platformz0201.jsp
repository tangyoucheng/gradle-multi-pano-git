<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司用户列表 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz02/platformz0201.js"></script>
</head>
<body>
    <div class="card-body">
    <div class="card card-default">
            <div class="card-header">
                <label>检索条件</label>
            </div>
            <div class="card-body">
                <!-- 检索条件 -->
                <form:form id="platformz0201FormSearch" action="/" modelAttribute="platformz0201Form">
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1" for="txt_memberLoginId">用户登录ID</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberLoginIde" name="memberLoginId">
                        </div>
                        <label class="col-form-label col-sm-1" for="txt_memberNamee">用户名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_memberName" name="memberName">
                        </div>
                    </div>
                </form:form>
                <div class="form-row">
                    <label class="col-form-label col-sm-1">&nbsp;</label>
                    <div class="col-sm-auto">
                        <button type="button" id="platformz0201Btn_query" class="btn cis-btn-blue">
                            <span class="glyphicon glyphicon-search"></span>
                            <span>检索</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="toolbar" class="btn-group">
            <button id="platformz0201Btn_new" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新建"></c:out>
            </button>
            <button id="platformz0201Btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
        </div>
        <table id="table-memberInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="memberLoginId" data-halign="center" data-align="left">用户登陆ID</th>
                    <th data-field="memberName" data-halign="center" data-align="left">用户名</th>
                    <th data-field="memberPhone" data-halign ="center" data-align="left">手机号码</th>
                    <th data-field="memberEmail" data-halign="center" data-align="left">邮箱</th>
                    <th data-field="memberId" data-visible="false">用户ID编码</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platformz0201FormAjaxSubmit" action="/" modelAttribute="platformz0201Form">
    </form:form>
</body>
</html>