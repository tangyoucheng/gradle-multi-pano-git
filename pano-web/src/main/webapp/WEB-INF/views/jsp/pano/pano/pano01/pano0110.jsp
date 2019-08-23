<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展厅一览画面 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano01/pano0110.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0110FormSearch" class="form-horizontal" modelAttribute="pano0110Form">
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionId">编号</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionId" name="expositionId"
                                value="${expositionId}" required>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${expositionName}">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionStartDate">开展时间</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionStartDate"
                                name="expositionStartDate" required readonly="readonly">
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionEndDate">撤展时间</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionEndDate" name="expositionEndDate"
                                required readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">状态</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="expositionStatus" name="expositionStatus"
                                selectedValue="${pano0110Form.expositionStatus}"
                                list="${pano0110Form.expositionStatusList}" className="form-control" blank="false" />
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">类型</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="expositionType" name="expositionType"
                                selectedValue="${pano0110Form.expositionType}" list="${pano0110Form.expositionTypeList}"
                                className="form-control" blank="false" />
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-5">
                            <button type="button" id="btn_query" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-search"></span>
                                <c:out value="查询"></c:out>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <table id="table-exposition-info" data-unique-id="id">
            <thead>
                <tr>
                    <th data-field="rowNumber" data-halign="center" data-align="center">行号</th>
                    <th data-field="expositionName" data-halign="center" data-align="left">名称</th>
                    <th data-field="expositionId" data-halign="center" data-align="left">编号</th>
                    <th data-field="status" data-visible="false">状态</th>
                    <th data-field="expositionStatusName" data-halign="center" data-align="left">状态</th>
                    <th data-field="expositionType" data-visible="false">类型</th>
                    <th data-field="expositionTypeName" data-halign="center" data-align="left">类型</th>
                    <th data-field="expositionStartDate" data-halign="center" data-align="left">开展时间</th>
                    <th data-field="expositionEndDate" data-halign="center" data-align="left">撤展时间</th>
                    <th data-field="releaseDate" data-halign="center" data-align="left">发布时间</th>
                    <th data-field="vrFlag" data-visible="false">VR标识</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>

    </div>

    <!-- submit处理 START -->
    <form id="pano0110FormAjaxSubmit" action="">
        <input type="hidden" id="pano0110FormAjax_selectedExpositionId" name="selectedExpositionId" />
        <input type="hidden" name="startDate" value="${pano0110Form.expositionStartDate}" />
        <input type="hidden" name="endDate" value="${pano0110Form.expositionEndDate}" />
    </form>
    <!-- submit处理 END -->
</body>

</html>