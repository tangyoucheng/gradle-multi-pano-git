<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 公司管理 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platform/js/platform03/platform0399.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-header">
                <label>检索条件</label>
            </div>
            <div class="card-body">
                <!-- 检索条件 -->
                <form:form id="platform0399FormSearch" action="/" modelAttribute="platform0399Form">
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1" for="txt_companyName">公司名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_companyName" name="companyName">
                        </div>
                    </div>
                </form:form>
                <div class="form-row">
                    <label class="col-form-label col-sm-1">&nbsp;</label>
                    <div class="col-sm-auto">
                        <button type="button" id="platform0399Btn_query" class="btn platform-btn-danger">
                            <span class="glyphicon glyphicon-search"></span>
                            <span>检索</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="toolbar" class="btn-group">
            <button id="platform0399Btn_new" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新建"></c:out>
            </button>
            <button id="platform0399Btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
        </div>
        <table id="table-companyInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field=companyName data-halign="center" data-align="left">公司名</th>
                    <th data-field="createUserId" data-halign="center" data-align="left">创建者</th>
                    <th data-field="createDate" data-halign="center" data-align="left">创建时间</th>
                    <th data-field="lastUpdateUserId" data-halign="center" data-align="left">更新者</th>
                    <th data-field="lastUpdateDate" data-halign="center" data-align="left">更新时间</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platform0399FormAjaxSubmit" action="/" modelAttribute="platform0399Form">
    </form:form>
</body>
</html>