/**
 * 全景图地图操作
 */

$(document).ready(function() {
});

// krpano加载完处理
function doPano0105KrpanoOnloadcomplete() {
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        window.top.removekrpanoMask();
        return false;
    }

    // 显示导航图
    showMiniMap();
    // 显示现有状态下的工具条样式
    showCurrentButtonsBar();
    // 显示已有的浮动信息层
    showCurrentFlowInfoLayer();

    // 移除遮盖层
    // window.top.removekrpanoMask();
}

// 显示现有状态下的工具条样式
function showCurrentButtonsBar() {

    if ($('#selectedButtons').val() == null || undefined == $('#selectedButtons').val()) {
        return false;
    }

    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    // 2.重构并顺序排列选中的工具条按钮的x坐标
    var strs = [];
    var selectedButtons = $('#selectedButtons').val();
    if (selectedButtons.indexOf(",") != -1) {
        strs = selectedButtons.split(',');
    }
    // 坐标变量
    for (var i = 0; i < strs.length; i++) {
        if (strs[i].length == 0) {
            return false;
        }
        krpano.set('layer[' + strs[i] + '].x', i * 40);
        krpano.set('layer[' + strs[i] + '].visible', true);
        // 让对应单选框保持选中
        $('#' + strs[i]).attr("checked", true);
    }

    krpano.set('layer[defaultskin_buttons].width', strs.length * 40);
}

// 显示导航图
function showMiniMap() {

    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

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
    loadMapHotspot()
}

// 显示导航图上的热点
function loadMapHotspot() {
    if ($('#miniMapSpotInfoListJson').val() == undefined || $('#miniMapSpotInfoListJson').val().length == 0) {
        return false;
    }
    var spotInfoList = JSON.parse($('#miniMapSpotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    // 读取已有的热点信息
    jQuery.each(spotInfoList, function(index, recordData) {
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
        jQuery.each(spotInfoList, function(index, recordData) {
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

}

// 显示导航图上热点下的信息
function showMiniMapHotspotInfo(_hotspotName, _radarHeading) {
    var hotspotId = _hotspotName.split('v_').join('');
    $('#selectedHotspotId').val(hotspotId);
    
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    // 点击该热点后，保存其当前雷达的位置,并在地图上显示位置
    offsetNum = parseInt(_radarHeading);
    // 先置空雷达
    krpano.set('layer[radar].visible', false);

    var ajaxSubmitFormData = form2js($("#pano0105FormAjaxSubmit")[0]);
    ajaxSubmitFormData['selectedHotspotId'] = hotspotId

    $.ajax({
        type : 'post',
        traditional : true,
        data : ajaxSubmitFormData,
        dataType : 'json',
        url : getMemberContextPath('pano0105/doCheckHostspotInfo'),
        success : function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                var recordData = result.obj;
                // 热点下链接了全景图的场合
                if (recordData.panoramaPath != '' && recordData.panoramaPath != null) {
                    krpano.call('loadpano(../' + recordData.panoramaPath + ',null,MERGE, BLEND(2))');
                    if (recordData.radarHeading != null && recordData.radarHeading.length != 0) {
                        $('#radarHeading').val(recordData.radarHeading);
                    } else {
                        $('#radarHeading').val("0");
                    }
                    // 设定地图热点和雷达角度
                    loadMapHotspot()
                    $('#txt_panoramaName').val(recordData.panoramaName);
                } else {
                    callAddPanoramaLink(hotspotId);
                }
            }
        }
    })

}

// 点击导航图上热点时的处理
function callAddPanoramaLink(_selectedHotspotId) {

    var targetUrl = 'pano0307/';
    var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['currentPanoramaId'] = urlParam['panoramaId'];
    urlParam['selectedHotspotId'] = _selectedHotspotId;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '为热点添加场景图',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            location.reload(true);
        }
    });
}

// 显示已有的浮动信息层
function showCurrentFlowInfoLayer() {
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    // 如果存在浮动信息层
    if ($('#flowInfoFileId').val() != null && $('#flowInfoFileId').val() != '') {
        var krpano = document.getElementById("pano0105KrpanoNewObject");
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

}
