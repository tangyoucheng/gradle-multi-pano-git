<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展览登记 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano01/pano0101.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0101FormAdd" class="form-horizontal" modelAttribute="pano0101Form">
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionId">编号</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_expositionId" name="expositionId"
                                value="${pano0101Form.expositionId}" required>
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${expositionName}" required>
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
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">展览类型</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="expositionType" name="expositionType"
                                selectedValue="${pano0101Form.expositionType}" list="${pano0101Form.expositionTypeList}"
                                className="form-control" blank="false" required="true" />
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">展览模式</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="vrFlag" name="vrFlag" selectedValue="${pano0101Form.vrFlag}"
                                list="${pano0101Form.vrFlagList}" className="form-control" blank="false" required="true" />
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_source">状态</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="expositionStatus" name="expositionStatus"
                                selectedValue="${pano0101Form.expositionStatus}"
                                list="${pano0101Form.expositionStatusList}" className="form-control" blank="false"
                                required="true" />
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">展览状态说明</label>
                        <div class="col-sm-5">
                            <form:textarea id="txt_expositionStatusNotes" path="expositionStatusNotes"
                                cssClass="form-control" htmlEscape="true" maxlength="2000" rows="3" placeholder=""
                                style="overflow: auto;" />
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">展览说明</label>
                        <div class="col-sm-5">
                            <form:textarea id="txt_expositionNotes" path="expositionNotes" cssClass="form-control"
                                htmlEscape="true" maxlength="2000" rows="3" placeholder="" style="overflow: auto;" />
                        </div>
                    </div>

                    <div class="form-row">
                        <label class="col-form-label col-sm-1">&nbsp;</label>
                        <div class="col-sm-5">
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
