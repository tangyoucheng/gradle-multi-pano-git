<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 素材一览画面 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano03/pano0302.js"></script>
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0302FormSearch" class="form-horizontal" modelAttribute="pano0302Form">
                    <input type="hidden" name="expositionId" value="${pano0302Form.expositionId}" />

                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_expositionName">展览名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_expositionName" name="expositionName"
                                value="${pano0302Form.expositionName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialId">编号</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_materialId" name="materialId"
                                value="${pano0302Form.materialId}">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_materialName">名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_materialName" name="materialName"
                                value="${pano0302Form.materialName}">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="materialBelongType">素材归属种类</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="materialBelongType" name="materialBelongType"
                                selectedValue="${pano0302Form.materialBelongType}"
                                list="${pano0302Form.materialBelongTypeList}" className="form-control" blank="false" />
                        </div>
                        <label class="col-form-label col-sm-1 text-left" for="materialTypeId">素材类别</label>
                        <div class="col-sm-2">
                            <platform:bootstrap-select id="materialTypeId" name="materialTypeId"
                                selectedValue="${pano0302Form.materialTypeId}"
                                list="${pano0302Form.materialTypeSelectInfo}" className="form-control" blank="false" />
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

        <div id="toolbar" class="btn-group mt-2 mb-2">
            <button id="btn_add" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <c:out value="新增"></c:out>
            </button>
            <button id="btn_edit" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                <c:out value="编辑"></c:out>
            </button>
            <button id="btn_delete" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                <c:out value="删除"></c:out>
            </button>
            <button id="btn_move" type="button" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                <c:out value="转移"></c:out>
            </button>
        </div>

        <table id="table-material-info" data-unique-id="id">
            <thead>
                <tr>
                    <th data-field="checkbox" data-checkbox="true" data-halign="center" data-align="center"></th>
                    <th data-field="rowNumber" data-halign="center" data-align="center">行号</th>
                    <th data-field="materialId" data-halign="center" data-align="left">编码</th>
                    <th data-field="materialName" data-halign="center" data-align="left">名称</th>
                    <th data-field="notes" data-halign="center" data-align="left">说明</th>
                    <th data-field="materialTypeId" data-visible="false">素材类别</th>
                    <th data-field="materialPath" data-visible="false">路径</th>
                    <th data-field="gifWidth" data-visible="false">gif图宽度</th>
                    <th data-field="gifHeight" data-visible="false">gif图高度</th>
                    <th data-field="gifFrameCount" data-visible="false">gif图帧数</th>
                    <th data-field="gifDelayTime" data-visible="false">gif图时间间隔</th>
                    <th data-field="hasPngImage" data-visible="false">Png图标识</th>
                    <th data-field="flowTextInfo" data-visible="false">文字浮动信息内容</th>
                    <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                </tr>
            </thead>
        </table>

    </div>

    <!-- submit处理 START -->
    <form method="POST" action="" id="goto-ic0103-form"></form>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0302FormAjaxSubmit" action="/" modelAttribute="pano0302Form">
        <input type="hidden" name="expositionId" value="${pano0302Form.expositionId}" />
        <input type="hidden" name="expositionName" value="${pano0302Form.expositionName}" />
    </form:form>

    <!-- submit处理 END -->
</body>

</html>
