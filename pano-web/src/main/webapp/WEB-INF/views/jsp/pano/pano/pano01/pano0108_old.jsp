<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/pano/pano/common/common.jsp"%>
<!-- 全景图多边形编辑 -->
<!DOCTYPE html >
<html>
<head>
<pano:head></pano:head>
<script src="static/pano/pano/js/pano01/pano0108.js"></script>
<script src="static/pano/pano/js/pano01/pano0108_callback.js"></script>
<!-- <script src="static/pano/pano/js/pano02/pano0108_old.js"></script> -->
</head>
<body>
    <div class="card-body">
        <div class="card">
            <div class="card-body">
                <form:form id="pano0108FormAdd" class="form-horizontal" modelAttribute="pano0108Form">

                    <input type="hidden" name="spotInfoListJson" id="spotInfoListJson"
                        value='${pano0108Form.spotInfoListJson}' />

                    <input type="hidden" name="expositionId" id="expositionId" value='${pano0108Form.expositionId}' />
                    <input type="hidden" name="expositionName" id="expositionName"
                        value='${pano0108Form.expositionName}' />
                    <input type="hidden" name="panoramaId" id="panoramaId" value='${pano0108Form.panoramaId}' />
                    <input type="hidden" name="panoramaName" id="panoramaName" value='${pano0108Form.panoramaName}' />
                    <input type="hidden" name="panoramaPath" id="panoramaPath" value='${pano0108Form.panoramaPath}' />
                    <input type="hidden" name="positionAthForEdit" value="${pano0108Form.positionAthForEdit}" />
                    <input type="hidden" name="positionAtvForEdit" value="${pano0108Form.positionAtvForEdit}" />
                    <input type="hidden" name="theLastedSceneHotspotId" value="${pano0108Form.theLastedSceneHotspotId}" />
                    <input type="hidden" name="currentRecommendHotspotId"
                        value="${pano0108Form.currentRecommendHotspotId}" />

                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName">场景名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="txt_panoramaName" name="panoramaName"
                                value="${pano0108Form.panoramaName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group form-row">
                        <label class="col-form-label col-sm-1 text-left" for="txt_panoramaName"></label>
                        <div class="col-sm-5">
                            <button type="button" id="btn_add" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                <c:out value="添加"></c:out>
                            </button>
                            <button type="button" id="btn_entry" class="btn pano-btn-danger">
                                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                                <c:out value="保存"></c:out>
                            </button>
                        </div>
                    </div>

                    <div class="form-group form-row">
                        <div class="col-sm-9">
                            <div id="pano0108Pano" style="width: 100%; height: calc(100vh - 85px);"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- Ajax提交用的Form -->
    <form:form id="pano0108FormAjaxSubmit" action="/" modelAttribute="pano0108Form">
        <input type="hidden" name="panoramaId" value="${pano0108Form.panoramaId}" />
        <input type="hidden" name="expositionId" value="${pano0108Form.expositionId}" />
        <input type="hidden" name="expositionName" value="${pano0108Form.expositionName}" />
    </form:form>
</body>
<!-- 热点单击事件指令框 -->
<div id="pano0108HotspotCommandChoice" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-edit-icon" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="更换热点图片"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-edit-size" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="调整热点大小"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-delete-hotspot" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="删除热点"></c:out>
            </button>
        </div>
    </div>
</div>

<!-- 单点音频热点单击事件指令框 -->
<div id="pano0108HotspotSoundCommandChoice" title="热点操作" class="text-center" style="display: none;">
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-sound-hotspot-image" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="预览"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-sound-hotspot-icon" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="更换热点图片"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-sound-hotspot-size" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="调整热点大小"></c:out>
            </button>
        </div>
    </div>
    <div class="col-sm-12 form-row">
        <div class="btn-group mt-2 mb-2">
            <button type="button" id="button-delete-hotspot" class="btn pano-btn-danger">
                <span class="glyphicon glyphicon-save" aria-hidden="true"></span>
                <c:out value="删除热点"></c:out>
            </button>
        </div>
    </div>
</div>
</html>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic01.Ic0108Form"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp"%>

<imui:head>
    <title>全景图多边形编辑</title>
</imui:head>

<script src="cdic/js/ic01/ic0108.js"></script>

<!-- 画面タイトル -->
<div class="imui-title">
    <h1>全景图多边形编辑</h1>
</div>
<div class="imui-form-container" style="width: 95%">
    <!-- コンテンツエリア -->
    <imui:dialog id="ic0108Finish" modal="true" autoOpen="false" onClose="ic0108DoBack">
        <div class="imui-form-container-narrow" style="border: none;">
            <h2>多边形信息编辑成功！</h2>
            <div class="imui-operation-parts">
                <input type="button" value="确定" id="confirm-button" class="imui-medium-button" />
            </div>
        </div>
    </imui:dialog>

    <form id="ic0108Form" action="ic01/ic0108" method="post">

        <input type="hidden" name="spotInfoListJson" id="spotInfoListJson" value='${spotInfoListJson}' />

        <input type="hidden" name="expositionId" id="expositionId" value='${expositionId}' />
        <input type="hidden" name="expositionName" id="expositionName" value='${expositionName}' />
        <input type="hidden" name="panoramaId" id="panoramaId" value='${panoramaId}' />
        <input type="hidden" name="panoramaName" id="panoramaName" value='${panoramaName}' />
        <input type="hidden" name="panoramaPath" id="panoramaPath" value='${panoramaPath}' />
        <input type="hidden" name="positionAthForEdit" value="${positionAthForEdit}" />
        <input type="hidden" name="positionAtvForEdit" value="${positionAtvForEdit}" />
        <input type="hidden" name="theLastedSceneHotspotId" value="${theLastedSceneHotspotId}" />
        <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}" />

        <table class="imui-form">
            <tr>
                <th style="width: 20%;">
                    <label>当前全景图的名称 :</label>
                </th>
                <td style="width: auto;">
                    <label>${panoramaName}</label>
                </td>
            </tr>
        </table>

        <table style="width: 100%">
            <tr>
                <td class="align-L"></td>
                <td class="align-R" style="padding-bottom: 6px;">
                    <input type="button" value="编辑手册" id="edit-button" class="imui-medium-button" />
                    &nbsp;&nbsp;
                    <input type="button" value="保存" id="save-button" class="imui-medium-button" />
                    &nbsp;&nbsp;
                    <input type="button" value="返回" id="back-button" class="imui-medium-button" />
                </td>
            </tr>
        </table>

        <div id="ic0108Pano" style="width: 100%; height: 600px;">
            <script type="text/javascript">
                                                    if ($('#panoramaPath').val().length > 0) {
                                                        var xmlPath = $('#panoramaPath').val()
                                                        embedpano({
                                                            swf : FrameworkConstants.VAL_EMBEDPANO_SWF,
                                                            id : 'ic0108KrpanoSWFObject',
                                                            xml : xmlPath,
                                                            target : "ic0108Pano",
                                                            wmode : "opaque",
                                                            flash : "only",
                                                            passQueryParameters : true,
                                                            bgcolor : "#f5f5f5"
                                                        });
                                                    }
                                                </script>
        </div>

    </form>
</div>

<form id="back-form" method="POST" action="ic02/ic0203/doSearchFromIc0108">
    <input type="hidden" name="panoramaId" value="${panoramaId}" />
    <input type="hidden" name="expositionId" value="${expositionId}" />
    <input type="hidden" name="expositionName" value="${expositionName}" />
    <input type="hidden" name="positionAthForEdit" id="0108BackAth" value="${positionAthForEdit}" />
    <input type="hidden" name="positionAtvForEdit" id="0108BackAtv" value="${positionAtvForEdit}" />
    <input type="hidden" name="theLastedSceneHotspotId" value="${theLastedSceneHotspotId}" />
    <input type="hidden" name="currentRecommendHotspotId" value="${currentRecommendHotspotId}" />
</form>
