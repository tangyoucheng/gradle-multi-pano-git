
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 热点素材信息编辑画面 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano02/pano0205.js"></script>

<link href="static/framework/plugins/minicolors/jquery.minicolors.css" rel="stylesheet">
<script src="static/framework/plugins/minicolors/jquery.minicolors.min.js"></script>
<!-- table拖拽组件 -->
<script src="static/framework/plugins/tablednd/1.0.3/jquery.tablednd.js"></script>
<script src="static/framework/plugins/bootstrap-table/1.15.4/extensions/reorder-rows/bootstrap-table-reorder-rows.js"></script>

<script type="text/javascript">
    // 当前页面的全局变量
    var newOrderList = [];// 待保存的排序数据
</script>

</head>
<body>
    <div class="card-body">

        <form:form id="pano0205Form" class="form-horizontal" modelAttribute="pano0205Form">
            <input type="hidden" name="selectedHotspotId" value="${pano0205Form.selectedHotspotId}" />
            <input type="hidden" name="expositionId" id="expositionId" value="${pano0205Form.expositionId}" />
            <input type="hidden" name="selectedMaterialId" id="selectedMaterialId"
                value="${pano0205Form.selectedMaterialId}" />
            <input type="hidden" name="hotspotUrlInfoJson" id="hotspotUrlInfoJson"
                value='${pano0205Form.hotspotUrlInfoJson}' />

            <input type="hidden" id="existedMaterialInfoJson" value='${pano0205Form.existedMaterialInfoJson}' />

            <input type="hidden" name="isPolygon" id="isPolygon" value="${pano0205Form.isPolygon}" />
            <input type="hidden" name="polygonFillcolor" id="polygonFillcolor" value="${pano0205Form.polygonFillcolor}" />
            <input type="hidden" name="polygonFillalpha" id="polygonFillalpha" value="${pano0205Form.polygonFillalpha}" />
            <input type="hidden" name="positionAthForEdit" id="positionAthForEdit"
                value="${pano0205Form.positionAthForEdit}" />
            <input type="hidden" name="positionAtvForEdit" id="positionAtvForEdit"
                value="${pano0205Form.positionAtvForEdit}" />
            <input type="hidden" name="ic0104urlType" id="urlType" value="${pano0205Form.urlType}" />
            <input type="hidden" name="existedSoundId" id="existedSoundId" value="${pano0205Form.existedSoundId}" />
            <input type="hidden" name="existedVideoId" id="existedVideoId" value="${pano0205Form.existedVideoId}" />
            <input type="hidden" name="theCommandFromRadiobox" id="theCommandFromRadiobox"
                value="${pano0205Form.theCommandFromRadiobox}" />

            <div class="form-group form-row col-centered">
                <label class="col-form-label col-sm-1 text-left">&nbsp;</label>
                <button id="pano0205-confirm-button" type="button" class="btn pano-btn-danger">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                    <c:out value="确定"></c:out>
                </button>
            </div>


            <div class="form-group form-row">
                <label class="col-form-label col-sm-1 text-left" for="txt_hotspotTooltip">场景图上热点提示信息</label>
                <div class="col-sm-4">
                    <input class="form-control rounded" id="txt_hotspotTooltip" name="hotspotTooltip" type="text"
                        value="${pano0205Form.hotspotTooltip }" />
                </div>
            </div>
            <c:if test="${pano0205Form.isPolygon == 'true' }">
                <div class="form-group form-row">
                    <label class="col-form-label col-sm-1 text-left" for="pano0205_polygon_property">设置多边形颜色及透明度</label>
                    <div class="col-sm-4 input-group">
                        <input class="form-control rounded" id="pano0205_polygon_property"
                            data-opacity="${pano0205Form.polygonFillalpha}" type="text"
                            value="${pano0205Form.polygonFillcolor}" />
                    </div>
                </div>
            </c:if>

            <div class="form-group form-row">
                <label class="col-form-label col-sm-1 text-left" for="materialBelongType">素材归属</label>
                <div class="col-sm-4 input-group">
                    <platform:bootstrap-select id="materialBelongType" name="materialBelongType"
                        selectedValue="${pano0205Form.materialBelongType}" list="${pano0205Form.materialBelongTypeList}"
                        className="form-control" blank="false" required="true" />
                </div>
                <label class="col-form-label col-sm-1 text-left" for="urlType">素材种类</label>
                <div class="col-sm-4 input-group">
                    <platform:bootstrap-select id="urlType" name="urlType" selectedValue="${pano0205Form.urlType}"
                        list="${pano0205Form.urlTypeList}" className="form-control" blank="false" required="true" />
                </div>
            </div>

            <div id="linkAddressTr" class="form-group form-row">
                <label class="col-form-label col-sm-1 text-left" for="txt_externalLinkAddress">外部链接</label>
                <div class="col-sm-4 input-group">
                    <input class="form-control rounded" id="txt_externalLinkAddress" name="externalLinkAddress"
                        type="text" value="${pano0205Form.externalLinkAddress }" />
                </div>
            </div>

            <div id="materialTable" class="form-group form-row">
                <div class="col-sm-6">
                    <table id="table-material-info" data-use-row-attr-func="true" data-reorderable-rows="true">
                        <thead>
                            <tr>
                                <th data-field="materialId" data-halign="center" data-align="left">编号</th>
                                <th data-field="materialName" data-halign="center" data-align="left">素材名</th>
                                <th data-field="notes" data-halign="center" data-align="left">说明</th>
                                <th data-field="materialPath" data-visible="false">路径</th>
                                <th data-field="expositionId" data-visible="false">展览编号</th>
                                <th data-field="materialTypeId" data-visible="false">素材类型</th>
                                <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div class="col-sm-6">
                    <table id="table-selected-material-info" data-use-row-attr-func="true" data-reorderable-rows="true">
                        <thead>
                            <tr>
                                <th data-field="materialId" data-halign="center" data-align="left">编号</th>
                                <th data-field="materialName" data-halign="center" data-align="left">素材名</th>
                                <th data-field="notes" data-halign="center" data-align="left">说明</th>
                                <th data-field="materialPath" data-visible="false">路径</th>
                                <th data-field="expositionId" data-visible="false">展览编号</th>
                                <th data-field="materialTypeId" data-visible="false">素材类型</th>
                                <th data-field="rowOperate" data-halign="center" data-align="center">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>

        </form:form>

    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0205FormAjaxSubmit" action="/" modelAttribute="pano0205Form">
        <input type="hidden" name="expositionId" value="${pano0205Form.expositionId}" />
        <input type="hidden" name="currentPanoramaId" value="${pano0205Form.currentPanoramaId}" />
        <input type="hidden" name="selectedHotspotId" value="${pano0205Form.selectedHotspotId}" />
    </form:form>
</body>
<!-- 预览 -->
<div id="pano0205imagePreview" title="预览" style="display: none;">
    <img id="imagePreview" src="" style="width: 100%; height: auto; display: none;" />
</div>
<div id="pano0205soundPreview" title="预览" style="display: none;">
    <div id="pano0205SoundPreviewDiv" style="width: 100%; height: auto; display: none;"></div>
</div>
<div id="pano0205videoPreview" title="预览" style="display: none;">
    <div id="videoPreview" style="width: 100%; height: 280px;"></div>
</div>

</html>