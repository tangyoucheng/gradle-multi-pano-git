<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!--  -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz03/platformz0302.js"></script>
</head>
<body>
    <div class="card-body">
        <!-- 检索条件 -->
		<form:form id="platformz0302FormSearch" action="/" modelAttribute="platformz0302Form" style="width:100%">
            <div class=" card-body form-row bg-white mb-3 m-0">
                <div class=" col-sm-10">
                    <!-- 检索条件 -->
                    <div class="form-row m-0 align-items-center">
                        <input type="hidden" class="form-control" id="txt_serverTypeId" name="serverTypeId">
                        <!-- input 输入框 start -->
                        <label class="col-form-label col-sm-0" for="txt_value">值：</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control form-control-sm mb-0" id="txt_value" name="value"
                                onkeydown="if(event.keyCode==13){event.keyCode=0;event.returnValue=false;}">
                        </div>
                        <!-- input 输入框 end -->
                    </div>
                </div>
                <div class=" col-sm-2 form-row  button-group justify-content-end">
                    <button type="button" id="platformz0302Btn_query" class="btn cis-btn cis-btn-primary mr-2">
                        <span>查询</span>
                    </button>
                    <button type="button" id="platformz0302Btn_clean" class="btn cis-btn cis-btn-white">
                        <span>清空</span>
                    </button>
                </div>
            </div>
        </form:form>
        <div class="card-body bg-white pt-2 position-relative">
            <div id="toolbar">
                <button id="platformz0302Btn_new" type="button" class="btn cis-btn cis-btn-primary mr-2">
                    <c:out value="新增"></c:out>
                </button>
                <div class="btn-group cis-btn-dropdown form-row m-0">
                    <button type="button" class="btn cis-btn cis-btn-white dropdown-toggle" data-toggle="dropdown">
                        <span>批量操作</span>
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu mt-2">
                        <li class="dropdown-item" id="platformz0302Btn_delete">
                            <span>删除 </span>
                        </li>
                    </ul>
                </div>
            </div>
            <table id="table-affairsIdInfo" data-toolbar="#toolbar">
                <thead>
                    <tr>
                        <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                        <th data-field="affairsId" data-visible="false">ID</th>
                        <th data-field="dispFlag" data-halign="center" data-align="left">排序号</th>
                        <th data-field="value" data-halign="center" data-align="left">值</th>
                        <th data-field="remark" data-halign="center" data-align="left">备注</th>
                        <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="platformz0302FormAjaxSubmit" action="/" modelAttribute="platformz0302Form">
    </form:form>
</body>
</html>