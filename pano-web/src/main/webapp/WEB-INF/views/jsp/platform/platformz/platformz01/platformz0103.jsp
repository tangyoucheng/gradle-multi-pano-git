<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 菜单管理 -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<!-- zTree组件引用 -->
<link href="static/framework/plugins/zTree/3.5.19/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="static/framework/plugins/zTree/3.5.19/js/jquery.ztree.core.min.js"></script>
<script src="static/framework/plugins/zTree/3.5.19/js/jquery.ztree.excheck.min.js"></script>
<script src="static/platform/platformz/js/platformz01/platformz0103.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card card-default">
            <div class="card-body">
                <div class="form-row">
                    <!-- 身份-->
                    <div class="col-sm-2">
                        <table id="roleList" class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-center">角色</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${platformz0103Form.roleList }" var="roleItem" varStatus="roleItemStatus">
                                    <tr id="roleCode_${roleItemStatus.index}"
                                        onclick="setTrColor('roleCode_${roleItemStatus.index}','${roleItem.roleId}');"
                                        style="cursor: pointer;">
                                        <td>${roleItem.roleName }</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-sm-1">&nbsp;</div>
                    <!-- 菜单 -->
                    <div class="col-sm-6">
                        <ul id="zTree" class="ztree"></ul>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-sm-auto">
                        <!-- 保存 -->
                        <button id="Btn_save" type="button" class="btn pano-btn-danger">
                            <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                            <c:out value="保存"></c:out>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platformz0103Form">
        <input type="hidden" id="menuListJson" value='${menuListJson}' name="menuListJson" />
        <input type="hidden" id="roleCode" name="roleCode" />
    </form:form>
</body>
</html>