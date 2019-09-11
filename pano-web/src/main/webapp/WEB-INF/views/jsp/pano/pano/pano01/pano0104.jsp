<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 展览场景编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>

<!-- table拖拽组件 -->
<script src="static/framework/plugins/tablednd/1.0.3/jquery.tablednd.js"></script>
<script src="static/framework/plugins/bootstrap-table/1.15.4/extensions/reorder-rows/bootstrap-table-reorder-rows.js"></script>

<link href="static/pano/pano/common/template/staticvtour/js/magnific/magnific-popup.css" rel="stylesheet"
    type="text/css" />
<script type="text/javascript" src="static/pano/pano/common/template/staticvtour/js/magnific/jquery.magnific-popup.js"></script>


<script type="text/javascript">
    // 当前页面的全局变量
    var currentTableRowInfo;// 左侧表格当前场景的相关信息
    var currentPanoramaInfo = {};// 引擎渲染的当前场景的相关信息
    var currentHotspotInfo = {};// 当前热点的相关信息

    var selectedHotspotInfo = {};// 选中的当前热点信息
    var newOrderList;// 待保存的排序数据
    var pano0104CurrentForm;// 当前表单的最新数据
</script>

<link href="static/pano/pano/css/pano01/pano0104.css" rel="stylesheet" type="text/css" />

<script src="static/pano/pano/js/pano01/pano0104.js"></script>
<!-- <script src="static/pano/pano/js/pano01/pano0104_old.js"></script> -->
<script src="static/pano/pano/js/pano01/pano0104_panoOnloadcomplete.js"></script>
<script src="static/pano/pano/js/pano01/pano0104_topOperate.js"></script>
<script src="static/pano/pano/js/pano01/pano0104_bodyOperate.js"></script>
<script src="static/pano/pano/js/pano01/pano0104_search.js"></script>
<script src="static/pano/pano/js/pano01/pano0104_callback.js"></script>

</head>
<body>

    <header class="header">
        <div class="logo">
            <p class="brand">SceneDesigner</p>
            <p class="desc">场景编辑</p>
        </div>
        <button id="btn_add_pano" class="btn pano-btn-outline-danger option-icon">
            <span class="glyphicon glyphicon-plus"></span>
            <div>新增</div>
        </button>
        <div class="option-icon" style="width: 40px;">&nbsp;|</div>
        <button id="btn_edit_pano" class="btn pano-btn-outline-danger option-icon">
            <span class="fas fa-edit"></span>
            <div>编辑</div>
        </button>
        <button id="btn_delete_pano" class="btn pano-btn-outline-danger option-icon">
            <span class="glyphicon glyphicon-trash"></span>
            <div>删除</div>
        </button>
        <div class="option-icon" style="width: 40px;">&nbsp;|</div>
        <button id="btn_update_panoOrder" type="button" class="btn pano-btn-outline-danger option-icon">
            <span class="fas fa-sort" aria-hidden="true"></span>
            <div>排序</div>
        </button>
        <div class="option-icon" style="width: 40px;">&nbsp;|</div>
        <button id="btn_material_list" class="btn pano-btn-outline-danger option-icon">
            <span class="fas fa-coins"></span>
            <div>素材</div>
        </button>
        <button id="btn_close" class="btn pano-btn-outline-danger option-icon float-right">
            <span class="glyphicon glyphicon-remove"></span>
            <div>关闭</div>
        </button>
    </header>

    <div class="card">
        <div class="card-body">
            <form:form id="pano0104Form" class="form-horizontal" modelAttribute="pano0104Form">
                <input type="hidden" name="expositionId" value='${pano0104Form.expositionId}' />
                <input type="hidden" name="expositionName" value='${pano0104Form.expositionName}' />
                <input type="hidden" name="miniMapCheck" id="miniMapCheck" value="${pano0104Form.miniMapCheck}" />
                <div class="form-group form-row">
                    <div class="col-sm-1" style="min-width: 150px !important; max-width: 150px !important;">
                        <table id="table-panorama-info" data-use-row-attr-func="true" data-reorderable-rows="true">
                            <thead>
                                <tr>
                                    <th data-field="panoramaSortKey" data-halign="center" data-align="center">顺序</th>
                                    <th data-field="panoramaName" data-halign="center" data-align="left">场景名</th>
                                    <th data-field="expositionId" data-visible="false">展览编号</th>
                                    <th data-field="panoramaId" data-visible="false">场景编号</th>
                                    <th data-field="panoramaSoundId" data-visible="false">声音编码</th>
                                    <th data-field="panoramaHlookat" data-visible="false">垂直位置</th>
                                    <th data-field="panoramaVlookat" data-visible="false">水平位置</th>
                                    <th data-field="startSceneFlag" data-visible="false">首场景标识</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="col-sm-10" style="max-width: calc(100vw - 370px) !important;">
                        <div id="pano0104Pano" style="width: 100%; height: calc(100vh - 150px);"></div>
                    </div>
                    <div id="operating-area-right" class="col-sm-1"
                        style="min-width: 170px !important; max-width: 170px !important;">
                        <div class="btn-group">
                            <button id="btn_set_lookat" type="button" class="btn pano-btn-danger">
                                <span class="far fa-save" aria-hidden="true"></span>
                                <c:out value="设定第一视角"></c:out>
                            </button>
                        </div>
                        <div class="btn-group mt-2">
                            <button id="btn_edit_hotspot" type="button" class="btn pano-btn-danger">
                                <span class="fas fa-edit" aria-hidden="true"></span>
                                <c:out value="单点热点"></c:out>
                            </button>
                        </div>
                        <div class="btn-group mt-2">
                            <button id="btn_edit_hotspot_polygon" type="button" class="btn pano-btn-danger">
                                <span class="fas fa-edit" aria-hidden="true"></span>
                                <c:out value="多边形热点"></c:out>
                            </button>
                        </div>
                        <div class="btn-group mt-2">
                            <button id="btn-edit-exposition-layer" type="button" class="btn pano-btn-danger">
                                <span class="fas fa-edit" aria-hidden="true"></span>
                                <c:out value="整体效果"></c:out>
                            </button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0104FormAjaxSubmit" action="/" modelAttribute="pano0104Form">
        <input type="hidden" name="expositionId" value='${pano0104Form.expositionId}' />
        <input type="hidden" name="expositionName" value='${pano0104Form.expositionName}' />
    </form:form>
</body>


<!-- 已有信息图的普通热点编辑 -->
<div id="pano0104HotspotCommandChoice" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-preview-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="预览热点内容"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-edit-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑热点内容"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-editSize-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑热点基本信息"></c:out>
            </button>
        </div>
    </div>
</div>
<!-- 暂无信息图的普通热点编辑 -->
<div id="pano0104NewHotspotCommandChoice" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="new-hotspot-button-edit-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="添加热点内容"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="new-hotspot-button-editSize-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑热点基本信息"></c:out>
            </button>
        </div>
    </div>
</div>
<!-- 已有场景的导航热点编辑 -->
<div id="pano0104HotspotCommandChoice2" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-to-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="跳转到对应场景"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-to-editPanoramaInfo" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑场景信息"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-editSize-confirms" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑热点基本信息"></c:out>
            </button>
        </div>
    </div>
</div>
<!-- 暂无场景的导航热点编辑 -->
<div id="pano0104NewHotspotCommandChoice2" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="new-hotspot-button-to-add" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="添加对应场景"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="new-hotspot-button-editSize-confirms" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑热点基本信息"></c:out>
            </button>
        </div>
    </div>
</div>
<!-- 多边形热点编辑 -->
<div id="pano0104HotspotCommandChoice3" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-previewPolygon-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="预览热点内容"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-editPolygon-confirm" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="编辑热点内容"></c:out>
            </button>
        </div>
    </div>
</div>
</html>


