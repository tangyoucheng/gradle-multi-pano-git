var pano0105Interval = setInterval(function() {
    showMiniMap()
}, 1000);
var pano0105MapHotspotInterval;
var pano0105ButtonsInterval = setInterval(function() {
    showCurrentButtonsBar()
}, 1000);
var pano0105FlowInfoInterval = setInterval(function() {
    showCurrentFlowInfoLayer()
}, 1000);
var pano0105RadarInterval = null;

/**
 * 全景图地图操作
 */
// 自定义热点雷达初始角度的全局变量
var offsetNum;
// 自定义视角点
var positionAth = "";
var positionAtv = "";
$(document).ready(function() {
    // 自定义工具条设定完成弹窗关闭
    $('#button-set-tool-confirm').click(function() {
        eval("$('#pano0105SetToolFinish').imuiDialog('close')");
        $('#pano0105Form').submit();
    });

    // 隐藏功能按钮
    hideButtons();
    $("input[name='commandType']").attr("checked", false);

    // radioBox的点击事件
    $("input[name='commandType']").click(function() {
        choiceCommand();
    });

    // 自定义工具条按钮点击操作
    $('#edit-buttons').click(function() {
        openPano0209();
    });

    // 展览目录编辑
    $('#edit-director').click(function() {
        openPano0308();
    });

    // 展览效果编辑
    $('#edit-effect').click(function() {
        openPano0109();
    });

    // 返回处理
    $('#back-button,#confirm-button').click(function() {
        if ($('#panoramaId').val() != null && $('#panoramaId').val() != "") {
            setLookAtForEdit();
        }

        pano0105DoBack();
    });

    // 点击导航图编辑按钮
    $('#edit-mini-map-button').click(function() {
        $('#edit-map').submit();
    });

    // 点击热点编辑按钮
    $('#edit-map-hotspot-button').click(function() {
        $('#edit-hotspot').submit();
    });
    // 雷达角度左旋
    $('#left').click(function() {
        var krpano = document.getElementById("pano0105KrpanoSWFObject");
        offsetNum -= 10;
        krpano.set('layer[radar].heading', offsetNum + '');
    });
    // 雷达角度右旋
    $('#right').click(function() {
        var krpano = document.getElementById("pano0105KrpanoSWFObject");
        offsetNum += 10;
        krpano.set('layer[radar].heading', offsetNum + '');
    });
    // 雷达角度设定

    $('#set_radar').click(function() {
        if (pano0105RadarInterval == null) {
            pano0105RadarInterval = setInterval(function() {
                doRadarByTime()
            }, 500);
        }
    });

    $('#radar_dialog-confirm-button,#layer_dialog-confirm-button').click(function() {
        closeDialog();
    });

    // 浮动信息设定
    $("#set_flow_info").click(function() {
        // 打开浮动信息的选择提示框
        eval("$('#pano0105FlowInfoCommandChoice').imuiDialog('open')");
    });

    // 推荐路线信息设定
    $('#set_common_info').click(function() {
        setCommonInfo();
    });

    // 在浮动信息的选择提示框中，点击选择图片浮动信息和文字浮动信息分别的操作
    $('#button-image-confirm').click(function() {
        editFlowInfo_Image();
    });
    $('#button-text-confirm').click(function() {
        editFlowInfo_Text();
    });

    // 保存浮动效果位置的操作
    $('#save_flow_info').click(function() {
        saveFlowInfoOffset();
    });

    // 浮动效果层点击后选择菜单上的按钮点击的事件
    $('#button-delete-layer').click(function() {
        deleteLayer();
    });
    $('#button-edit-layer-size').click(function() {
        // 编辑前检查该层是否被保存，没有保存则提示保存
        var _ajaxUrl = getMemberContextPath('pano0105/doCheckFlowInfoLayer');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                if (data == 'true') {
                    callPano0209();
                } else {
                    eval("$('#pano0105FlowInfoEidtChoice').imuiDialog('close')");
                    imuiAlert("请先保存浮动效果");
                }
            }
        });

    });
});

function doRadarByTime() {
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    if (!krpano.get('layer[radar].visible')) {
        return false;
    }
    clearInterval(pano0105RadarInterval);
    pano0105RadarInterval = null;

    imuiConfirm('是否保存当前热点的雷达角度', '确认', function() {
        var _ajaxUrl = getMemberContextPath('pano0105/doSaveRadarHeading');
        $('#radarHeading').val(offsetNum + '');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                if (data == 'false') {
                    imuiAlert("操作失败");
                } else {
                    // 重新读取最新的地图热点信息
                    var recordData = JSON.parse(data);
                    eval("$('#pano0105Finish').imuiDialog('open')");
                    $('#miniMapSpotInfoListJson').val(recordData.miniMapSpotInfoListJson);
                    loadMapHotspot();
                }
            }
        });
    });
}

// 指令radiobox的选择操作
function choiceCommand() {
    var radioboxSelected = $("input[name='commandType']:checked").val();
    hideButtons();
    // 选中导航图操作
    if (radioboxSelected == PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_MINMAP) {
        showMiniMapButtons();
    }
    // 选中浮动信息操作
    if (radioboxSelected == PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_FLOW_INFO) {
        showFlowInfoButtons();
    }
    // 选中工具条操作
    if (radioboxSelected == PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_TOOL) {
        showButtonBarButtons();
    }
}

// 隐藏画面上除radiobox外的全部功能按钮
function hideButtons() {
    $('#edit-mini-map-button').hide();
    $('#edit-map-hotspot-button').hide();
    $('#left').hide();
    $('#right').hide();
    $('#set_radar').hide();
    $('#set_common_info').hide();
    $('#set_flow_info').hide();
    $('#save_flow_info').hide();
    $('#edit-buttons').hide();
    $('#edit-director').hide();
    $('#edit-effect').hide();
}

// 显示导航图操作相关的功能按钮
function showMiniMapButtons() {
    $('#edit-mini-map-button').show();
    // 如果有地图，才显示更多操作
    if ($('#miniMapCheck').val() == 'true') {
        $('#edit-map-hotspot-button').show();
        $('#left').show();
        $('#right').show();
        $('#set_radar').show();
    }
}

// 显示设定浮动效果的相关功能按钮
function showFlowInfoButtons() {
    $('#set_common_info').show();
    $('#set_flow_info').show();
    $('#save_flow_info').show();
}

// 显示设定场景功能条的相关功能按钮
function showButtonBarButtons() {
    if ($('#panoramaId').val() != null && $('#panoramaId').val() != "") {
        $('#edit-buttons').show();
        $('#edit-director').show();
    }

    $('#edit-effect').show();
}

// 前往设置推荐路线信息的画面
function setCommonInfo() {
    setLookAtForEdit();
    $("#editButtonsOrFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0209'),
        title : '设置公共信息',
        modal : true,
        width : 800,
        height : 600,
        parameter : {
            LastedHotspotIdFrom0105 : $('#pano0105lastHotspotId').val(),
            currentHotspotIdFrom0105 : $('#pano0105CurrentHotspotId').val(),
            commandTypeFromPano0105 : 'commonInfo',
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            expositionIdForPano0209 : $('#expositionId').val(),
            panoramaIdForPano0209 : $('#panoramaId').val()
        },
        close : function(event, ui) {
            // 编辑画面关闭时，停止计时器
            clearInterval(ic0209RecommendInterval);
        }
    });
    return false;

}
// 前往自定义工具条按钮的画面
function openPano0209() {
    setLookAtForEdit();
    $("#editButtonsOrFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0209'),
        title : '自定义工具条',
        modal : true,
        width : 800,
        height : 700,
        parameter : {
            LastedHotspotIdFrom0105 : $('#pano0105lastHotspotId').val(),
            currentHotspotIdFrom0105 : $('#pano0105CurrentHotspotId').val(),
            commandTypeFromPano0105 : 'buttons',
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            expositionIdForPano0209 : $('#expositionId').val(),
            panoramaIdForPano0209 : $('#panoramaId').val()
        }
    });
    return false;
}

// 前往展览目录编辑画面
function openPano0308() {
    $("#editButtonsOrFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0308'),
        title : '展览目录编辑',
        modal : true,
        width : 640,
        height : 665,
        parameter : {
            expositionId_0308 : $('#expositionId').val()
        }
    });
    return false;
}

// 前往展览效果编辑画面
function openPano0109() {
    $("#editButtonsOrFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0109'),
        title : '展览效果编辑',
        modal : true,
        width : 900,
        height : 305,
        position : [ "center", 180 ],
        parameter : {
            expositionId : $('#expositionId').val(),
            panoramaId : $('#panoramaId').val()
        },
        close : function(event, ui) {
            // 关闭画面时，停止0109音乐
            var pano0109newVideokrpano1 = document.getElementById("newPano1Id");
            if (pano0109newVideokrpano1 != null && undefined != pano0109newVideokrpano1.get) {
                pano0109newVideokrpano1.call('videoplayer_close();');
            }
            var pano0109newVideokrpano2 = document.getElementById("newPanoId");
            if (pano0109newVideokrpano2 != null && undefined != pano0109newVideokrpano2.get) {
                pano0109newVideokrpano2.call('videoplayer_close();');
            }
            var pano0109oldVideokrpano = document.getElementById("oldPanoId");
            if (pano0109oldVideokrpano != null && undefined != pano0109oldVideokrpano.get) {
                pano0109oldVideokrpano.call('videoplayer_close();');
            }

        }
    });
    return false;
}

// 显示现有状态下的工具条样式
function showCurrentButtonsBar() {

    var timeFlag
    if (timeFlag) {
        return false;
    }

    if ($('#selectedButtons').val() == null || undefined == $('#selectedButtons').val()) {
        return false;
    }

    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    timeFlag = true;

    // 2.重构并顺序排列选中的工具条按钮的x坐标
    var strs = [];
    strs = $('#selectedButtons').val().split(',');
    // 坐标变量
    for (var i = 0; i < strs.length; i++) {
        krpano.set('layer[' + strs[i] + '].x', i * 40);
        krpano.set('layer[' + strs[i] + '].visible', true);
        // 让对应单选框保持选中
        $('#' + strs[i]).attr("checked", true);
    }

    krpano.set('layer[defaultskin_buttons].width', strs.length * 40);
    timeFlag = false;
    clearInterval(pano0105ButtonsInterval);
}

// 保存工具条
function doSaveButtonsBar(_selectedButtons) {
    imuiConfirm('是否保存当前工具条状态', '确认', function() {
        $('#selectedButtons').val(_selectedButtons);
        var _ajaxUrl = getMemberContextPath('pano0105/doSaveButtonsBar');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                if (data == 'true') {
                    eval("$('#pano0105SetToolFinish').imuiDialog('open')");
                }
            }
        });
    });
}

// 关闭弹窗
function closeDialog() {
    eval("$('#pano0105Finish').imuiDialog('close')");
    eval("$('#pano0105SetLayerFinish').imuiDialog('close')");
}

// 显示导航图
function showMiniMap() {

    var timeFlag
    if (timeFlag) {
        return false;
    }

    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    timeFlag = true;
    // 查看当前展览有无导航图，有即显示
    var check = $('#miniMapCheck').val();
    if (check == 'true') {
        var newlayer = 'layer_nimi_map_container';
        krpano.call('addlayer(' + newlayer + ')');
        krpano.set('layer[' + newlayer + '].url', $('#expositionMapPath').val());
        krpano.set('layer[' + newlayer + '].keep', true);
        krpano.set('layer[' + newlayer + '].align', 'rightbottom');
        krpano.set('layer[' + newlayer + '].zorder', '2');
        krpano.set('layer[' + newlayer + '].x', 10);
        krpano.set('layer[' + newlayer + '].y', 10);
        krpano.set('layer[' + newlayer + '].bgcolor', '0xCCCCCC');
        krpano.set('layer[' + newlayer + '].bgalpha', '0.5');
        krpano.set('layer[' + newlayer + '].scalechildren', true);
        krpano.set('layer[' + newlayer + '].maskchildren', true);
        krpano.set('layer[' + newlayer + '].handcursor', false);
    }
    // 读取导航图上热点
    pano0105MapHotspotInterval = setInterval(function() {
        loadMapHotspot()
    }, 1000)
    clearInterval(pano0105Interval);
    timeFlag = false;
}

// 呼出选择图片浮动效果素材的画面
function editFlowInfo_Image() {
    eval("$('#pano0105FlowInfoCommandChoice').imuiDialog('close')");
    $("#addFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0208'),
        title : '选择浮动效果',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            expositionId : $("#expositionId").val(),
            materialTypeId : PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE
        }
    });
    return false;
}

// 呼出选择文字浮动效果素材的画面
function editFlowInfo_Text() {
    eval("$('#pano0105FlowInfoCommandChoice').imuiDialog('close')");
    $("#addFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0208'),
        title : '选择浮动效果',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            expositionId : $("#expositionId").val(),
            materialTypeId : PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT
        }
    });
    return false;
}

// 返回方法
function pano0105DoBack() {
    $("#back-form").submit();
}

// 显示导航图上的热点
function loadMapHotspot() {
    var timeFlag
    if (timeFlag) {
        return false;
    }

    if ($('#miniMapSpotInfoListJson').val() == undefined || $('#miniMapSpotInfoListJson').val().length == 0) {
        return false;
    }
    var spotInfoList = JSON.parse($('#miniMapSpotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    timeFlag = true;

    // 读取已有的热点信息
    $(spotInfoList).each(
        function(index, recordData) {
            var newlayer = 'v_' + recordData.expositionMapHotspotId;
            krpano.call('addlayer(' + newlayer + ')');
            krpano.set('layer[' + newlayer + '].parent', 'layer_nimi_map_container');
            if (recordData.expositionMapHotspotUrl != '' && recordData.expositionMapHotspotUrl != null) {
                krpano.set('layer[' + newlayer + '].url', recordData.expositionMapHotspotUrl);
            } else {
                krpano.set('layer[' + newlayer + '].url', FrameworkConstants.VAL_IMAGE_MAPPOINT);
            }
            krpano.set('layer[' + newlayer + '].align', 'rightbottom');
            krpano.set('layer[' + newlayer + '].bgalpha', '1.0');
            krpano.set('layer[' + newlayer + '].x', recordData.expositionMapHotspotX);
            krpano.set('layer[' + newlayer + '].y', recordData.expositionMapHotspotY);
            krpano.set('layer[' + newlayer + '].handcursor', true);
            krpano.set('layer[' + newlayer + '].onclick', 'activatespot(' + newlayer
                    + ',0);js(showMiniMapHotspotInfo(get(name));)');
            krpano.set('layer[' + newlayer + '].keep', true);
        });
    if ($('#selectedHotspotId').val() != undefined && $('#selectedHotspotId').val().length != 0) {
        // 被选中的地图热点zorder设为2，未被选中设为3，保证雷达扇形不覆盖在其他热点上
        $(spotInfoList).each(function(index, recordData) {
            var newlayer = 'v_' + recordData.expositionMapHotspotId;
            krpano.set('layer[' + newlayer + '].zorder', '3');
        });
        krpano.set('layer[v_' + $('#selectedHotspotId').val() + '].zorder', '2');
        krpano.set('layer[radar].parent', 'V_' + $('#selectedHotspotId').val());
        krpano.set('layer[radar].heading', $('#radarHeading').val());
        krpano.set('layer[radar].visible', true);
        offsetNum = parseInt($('#radarHeading').val());
        krpano.set('layer[activespot].visible', true);
    }

    clearInterval(pano0105MapHotspotInterval);
}

// 显示导航图上热点下的信息
function showMiniMapHotspotInfo(_hotspotName, _radarHeading) {
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    // 点击该热点后，保存其当前雷达的位置,并在地图上显示位置
    offsetNum = parseInt(_radarHeading);
    // 先置空雷达
    krpano.set('layer[radar].visible', false);
    var hotspotId = _hotspotName.split('v_').join('');
    var _ajaxUrl = getMemberContextPath('pano0105/doCheckHostspotInfo');
    $("#selectedHotspotId").val(hotspotId);
    var param = $("#pano0105Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            // 热点下链接了全景图的场合
            if (data != '' && data != null) {
                var recordData = JSON.parse(data);
                if (recordData.panoramaPath != '' && recordData.panoramaPath != null) {
                    krpano.call('loadpano(../' + recordData.panoramaPath + ',null,MERGE, BLEND(2))');
                    if (recordData.radarHeading != null && recordData.radarHeading.length != 0) {
                        $('#radarHeading').val(recordData.radarHeading);
                    } else {
                        $('#radarHeading').val("0");
                    }
                    // 设定地图热点和雷达角度
                    pano0105MapHotspotInterval = setInterval(function() {
                        loadMapHotspot()
                    }, 1000)
                    $('#pano0105title').html(recordData.panoramaName);
                }
            } else {
                callAddPanoramaLink(hotspotId);
            }
        }
    });
}

// 点击导航图上热点时的处理
function callAddPanoramaLink(_selectedHotspotId) {
    $("#addPanorama").imuiPageDialog({
        url : getMemberContextPath('pano0307'),
        title : '为热点添加场景图',
        modal : true,
        width : 820,
        height : 600,
        parameter : {
            selectedHotspotId : _selectedHotspotId,
            expositionId : $("#expositionId").val(),
            currentPanoramaId : $("#panoramaId").val(),
            expositionName : $("#expositionName").val()
        }
    });
    return false;
}

// 显示已有的浮动信息层
function showCurrentFlowInfoLayer() {
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    // 如果存在浮动信息层
    if ($('#flowInfoFileId').val() != null && $('#flowInfoFileId').val() != '') {
        var krpano = document.getElementById("pano0105KrpanoSWFObject");
        var newlayer = 'flowInfoLayer';
        krpano.call('addlayer(' + newlayer + ')');
        krpano.set('layer[' + newlayer + '].handcursor', true);
        krpano.set('layer[' + newlayer + '].border', false);
        krpano.set('layer[' + newlayer + '].keep', true);
        krpano.set('layer[' + newlayer + '].align', 'righttop');
        krpano.set('layer[' + newlayer + '].ondown', 'draglayer();js(pano0105LayerDraglayer());');
        krpano.set('layer[' + newlayer + '].x', $('#flowInfoFileX').val());
        krpano.set('layer[' + newlayer + '].y', $('#flowInfoFileY').val());
        krpano.set('layer[' + newlayer + '].scale', $('#flowInfoFileScale').val());
        // 如果是图片浮动信息
        if ($('#flowInfoType').val() == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE) {
            krpano.set('layer[' + newlayer + '].url', $('#flowInfoFilePath').val());
        } else {
            // 如果是文字浮动信息
            krpano.set('layer[' + newlayer + '].url', '%SWFPATH%/plugins/textfield.swf');
            krpano.set('layer[' + newlayer + '].html', $('#flowInfoFileInfo').val());
            krpano.set('layer[' + newlayer + '].css',
                'text-align:center; font-family:Arial; font-weight:bold; font-size:'
                        + parseFloat($('#flowInfoFileScale').val()) * 36 + 'px;');
            krpano.set('layer[' + newlayer + '].scalechildren', true);
        }
        krpano.set('layer[' + newlayer + '].backgroundalpha', "0");
        krpano.set('layer[' + newlayer + '].height', "auto");
        krpano.set('layer[' + newlayer + '].width', "auto");
        krpano.set('layer[' + newlayer + '].onclick', 'js(pano0105LayerClick(get(name)));');
    }

    clearInterval(pano0105FlowInfoInterval);
}

// 选择浮动信息素材后，显示浮动信息层
function pano0105ShowFlowInfoLayer(_flowInfoFileId, _filePath, _flowInfoType, _flowTextInfo) {
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    // 移除旧的layer
    krpano.call('removelayer(flowInfoLayer)')
    // 添加新的layer
    $('#flowInfoFileId').val(_flowInfoFileId);
    $('#flowInfoType').val(_flowInfoType);
    $('#flowInfoFilePath').val(_filePath);
    $('#flowInfoFileInfo').val(_flowTextInfo);

    var newlayer = 'flowInfoLayer';
    krpano.call('addlayer(' + newlayer + ')');
    krpano.set('layer[' + newlayer + '].handcursor', true);
    krpano.set('layer[' + newlayer + '].border', false);
    krpano.set('layer[' + newlayer + '].keep', true);
    krpano.set('layer[' + newlayer + '].align', 'righttop');
    krpano.set('layer[' + newlayer + '].ondown', 'draglayer();js(pano0105LayerDraglayer());');
    krpano.set('layer[' + newlayer + '].x', 0);
    krpano.set('layer[' + newlayer + '].y', 0);
    krpano.set('layer[' + newlayer + '].scale', "1.0");
    $('#flowInfoFileScale').val("1.0");
    // 如果是图片浮动信息
    if (_flowInfoType == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE) {
        krpano.set('layer[' + newlayer + '].url', _filePath);
    } else {
        // 如果是文字浮动信息
        krpano.set('layer[' + newlayer + '].url', '%SWFPATH%/plugins/textfield.swf');
        krpano.set('layer[' + newlayer + '].html', _flowTextInfo);
        krpano.set('layer[' + newlayer + '].css',
            'text-align:center; font-family:Arial; font-weight:bold; font-size:36px;');
        krpano.set('layer[' + newlayer + '].scalechildren', true);
    }
    krpano.set('layer[' + newlayer + '].backgroundalpha', "0");
    krpano.set('layer[' + newlayer + '].height', "auto");
    krpano.set('layer[' + newlayer + '].width', "auto");
    krpano.set('layer[' + newlayer + '].onclick', 'js(pano0105LayerClick(get(name)));');
}

var layerName;
// 浮动层点击事件
function pano0105LayerClick(_layerName) {
    eval("$('#pano0105FlowInfoEidtChoice').imuiDialog('open')");
    layerName = _layerName;

}
// 浮动层拖拽事件
function pano0105LayerDraglayer() {
    $("input[name='commandType'][value=2]").attr("checked", 'checked');
    choiceCommand();
}

// 删除浮动层的操作
function deleteLayer() {
    eval("$('#pano0105FlowInfoEidtChoice').imuiDialog('close')");
    imuiConfirm('是否移除当前浮动信息层', '确认', function() {
        var krpano = document.getElementById("pano0105KrpanoSWFObject");
        krpano.call('removelayer(' + layerName + ')')
        var _ajaxUrl = getMemberContextPath('pano0105/doDeleteFlowInfo');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                if (data == 'true') {
                    eval("$('#pano0105SetLayerFinish').imuiDialog('open')");
                } else {
                    imuiAlert("操作失败");
                }
            }
        });
    });
}

// 保存浮动信息层的位置
function saveFlowInfoOffset() {
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    if ($('#flowInfoFileId').val() == null || $('#flowInfoFileId').val() == ''
            || krpano.get('layer[flowInfoLayer].name') == null) {
        imuiAlert("请先添加一个浮动效果");
        return false;
    }

    var _positionX = krpano.get('layer[flowInfoLayer].x').toString();
    var _positionY = krpano.get('layer[flowInfoLayer].y').toString();
    var positionX = "";
    var positionY = "";
    // 平面图的场合
    if (Number(_positionX) >= 0) {
        // 正数的场合
        positionX = Math.round(parseFloat(_positionX) * 1000) / 1000;
    } else {
        // 负数的场合
        positionX = _positionX.substring(0, 1) + Math.round(parseFloat(_positionX.substring(1)) * 1000) / 1000;
    }
    if (Number(_positionY) >= 0) {
        // 正数的场合
        positionY = Math.round(parseFloat(_positionY) * 1000) / 1000;
    } else {
        // 负数的场合
        positionY = _positionY.substring(0, 1) + Math.round(parseFloat(_positionY.substring(1)) * 1000) / 1000;
    }

    $('#flowInfoFileX').val(positionX);
    $('#flowInfoFileY').val(positionY);

    imuiConfirm('是否保存当前浮动信息层的位置', '确认', function() {
        var _ajaxUrl = getMemberContextPath('pano0105/doSaveFlowInfoOffset');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                if (data == 'true') {
                    eval("$('#pano0105SetLayerFinish').imuiDialog('open')");
                } else {
                    imuiAlert("操作失败");
                }
            }
        });
    });
}

// 跳转画面前取得当前视角点
function setLookAtForEdit() {
    $("#0105BackAth").val('');
    $("#0105BackAtv").val('');

    // 得到当前场景的中心点
    var krpano = document.getElementById("pano0105KrpanoSWFObject");
    var _positionAth = krpano.get('view.hlookat').toString();
    var _positionAtv = krpano.get('view.vlookat').toString();
    // 全景图的场合
    if (Number(_positionAth) >= 0) {
        // 正数的场合
        positionAth = Math.round(parseFloat(_positionAth));
    } else {
        // 负数的场合
        positionAth = _positionAth.substring(0, 1) + Math.round(parseFloat(_positionAth.substring(1)));
    }
    if (Number(_positionAtv) >= 0) {
        // 正数的场合
        positionAtv = Math.round(parseFloat(_positionAtv));
    } else {
        // 负数的场合
        positionAtv = _positionAtv.substring(0, 1) + Math.round(parseFloat(_positionAtv.substring(1)));
    }
    $("#0105BackAth").val(positionAth);
    $("#0105BackAtv").val(positionAtv);
}

// 前往调整浮动信息层大小的画面
function callPano0209() {
    var krpano = document.getElementById("pano0105KrpanoSWFObject");

    var layerScale = krpano.get('layer[flowInfoLayer].scale').toString();
    var layerX = krpano.get('layer[flowInfoLayer].x').toString();
    var layerY = krpano.get('layer[flowInfoLayer].y').toString();

    eval("$('#pano0105FlowInfoEidtChoice').imuiDialog('close')");
    setLookAtForEdit();
    $("#editButtonsOrFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0209'),
        title : '编辑浮动信息层大小',
        modal : true,
        width : 900,
        height : 800,
        parameter : {
            LastedHotspotIdFrom0105 : $('#pano0105lastHotspotId').val(),
            currentHotspotIdFrom0105 : $('#pano0105CurrentHotspotId').val(),
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            commandTypeFromPano0105 : 'flowInfo',
            expositionIdForPano0209 : $('#expositionId').val(),
            panoramaIdForPano0209 : $('#panoramaId').val(),
            ic0209flowFileId : $('#flowInfoFileId').val(),
            ic0209flowFileType : $('#flowInfoType').val(),
            ic0209flowFilePath : $('#flowInfoFilePath').val(),
            ic0209flowFileInfo : $('#flowInfoFileInfo').val(),
            hotspotScale : layerScale,
            ic0209layerX : layerX,
            ic0209layerY : layerY
        }
    });
    return false;
}
