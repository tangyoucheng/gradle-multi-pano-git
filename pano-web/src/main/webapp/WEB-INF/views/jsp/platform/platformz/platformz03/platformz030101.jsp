<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!--  -->
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<script src="static/platform/platformz/js/platformz03/platformz030101.js"></script>
</head>
<body><!-- 工作量新增顶部Tool start -->
    <div class="tool-header">
        <div class="form-row mb-1">
            <h5>字典表</h5>
        </div>
        <div class="form-row form-group button-group btn-group">
            <div class="col-sm-auto">
                <!-- 新增保存 -->
                <button type="button" id="btn_entry" class="btn cis-btn cis-btn-primary mr-2">
                    <span>保存</span>
                </button>
                <button type="button" id="btn_close" class="btn cis-btn cis-btn-white mr-2">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <!-- 工作量新增顶部Tool end -->
    <div class="card-body pt-6">
        <div class="card card-default">
            <div class="card-header">
                <span class="line-lift">字典表信息</span>
            </div>
            <div class="card-body">
                <form:form action="/" modelAttribute="platformz030101Form" class="form-horizontal">
                    <!-- 值 -->
                    <div class="form-mb form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_value">值 </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_value" name="value"
                                value="${platformz030101Form.value}" maxlength="50" required="required">
                        </div>
                    </div>
                    <!-- 排序号 -->
                    <div class="form-mb form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_dispFlag">排序号：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="txt_dispFlag" name="dispFlag"
                                value="${platformz030101Form.dispFlag}" maxlength="3" onblur="dispFlagFormat(this)">
                        </div>
                    </div>
                    <!-- 备注 -->
                    <div class="form-mb form-row">
                        <label class="col-form-label text-right col-sm-1" for="txt_remark"> 备注 </label>
                        <div class="col-sm-4">
                            <textarea class="form-control" name="remark" rows="7" id="txt_remark" maxlength="100">${platformz030101Form.remark}</textarea>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>