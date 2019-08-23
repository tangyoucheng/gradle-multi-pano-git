<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 社区管理 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<!-- bootstraptable-treeview组件引用 -->
<script src="static/framework/plugins/bootstrap-table/bootstraptable-treeview.js"></script>
<script src="static/platform/platformz/js/platformz01/platformz0199.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-header">
                <label>检索条件</label>
            </div>
            <div class="card-body">
                <!-- 检索条件 -->
                <form:form id="platformz0199FormSearch" action="/" modelAttribute="platformz0199Form">
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1" for="txt_departmentName">社区名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="txt_departmentName" name="departmentName">
                        </div>
                    </div>
                </form:form>
                <div class="form-row">
                    <label class="col-form-label col-sm-1">&nbsp;</label>
                    <div class="col-sm-auto">
                        <button type="button" id="platformz0199Btn_query" class="btn ciicsc-btn-danger">
                            <span class="glyphicon glyphicon-search"></span>
                            <span>检索</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="toolbar" class="btn-group">
            <button id="platformz0199Btn_new" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新建"></c:out>
            </button>
            <button id="platformz0199Btn_delete" type="button" class="btn cis-btn-blue">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
        </div>
        <table id="table-departmentInfo" data-toolbar="#toolbar">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="departmentId"   data-halign="center" data-align="left">社区ID</th>
                    <th data-field="departmentHierarchy"   data-halign="center" data-align="left">社区级别</th>
                    <th data-field="departmentName" data-halign="center" data-align="left">社区名</th>
                    <th data-field="createUserId" data-halign="center" data-align="left">创建者</th>
                    <th data-field="createDate" data-halign="center" data-align="left">创建时间</th>
                    <th data-field="lastUpdateUserId" data-halign="center" data-align="left">更新者</th>
                    <th data-field="lastUpdateDate" data-halign="center" data-align="left">更新时间</th>
                    <th data-field="leafDepartmentName" data-visible="false">叶子节点社区名</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platformz0199FormAjaxSubmit" action="/" modelAttribute="platformz0199Form">
    </form:form>
</body>
</html>