/**
 * 场景地图上热点的操作
 */
$(document).ready(function() {

});

// krpano加载完处理
function doPano0107KrpanoOnloadcomplete() {
    var krpano = document.getElementById("pano0107KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        window.top.removekrpanoMask();
        return false;
    }

    // 显示导航图
    showMiniMap();
    // 显示导航图上的热点
    loadMapHotspot()

    // 移除遮盖层
    window.top.removekrpanoMask();
}

// 显示导航图
function showMiniMap() {

    var krpano = document.getElementById("pano0107KrpanoNewObject");
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
        krpano.set('layer[' + newlayer + '].x', 10);
        krpano.set('layer[' + newlayer + '].y', 10);
        krpano.set('layer[' + newlayer + '].bgcolor', '0xCCCCCC');
        krpano.set('layer[' + newlayer + '].bgalpha', '0.5');
        krpano.set('layer[' + newlayer + '].scalechildren', true);
        krpano.set('layer[' + newlayer + '].maskchildren', true);
        krpano.set('layer[' + newlayer + '].handcursor', false);
    }

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
    var krpano = document.getElementById("pano0107KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    // 读取已有的热点信息
    $(spotInfoList).each(function(index, recordData) {

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
        krpano.set('layer[' + newlayer + '].ondown', 'draglayer();');
        krpano.set('layer[' + newlayer + '].onup', 'js(doChidLayerOnUp(layer_nimi_map_container,get(name)));');
        krpano.set('layer[' + newlayer + '].onclick', 'js(pano0107RemoveLayer(get(name)))');
    });
    clearInterval(pano0107HotspotInterval);
}
// 删除层
function pano0107RemoveLayer(_hotspotName) {

    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() { // 确认操作
        window.top.layer.close(currentConfirmIndex);

        var krpano = document.getElementById("pano0107KrpanoNewObject");
        krpano.call('removelayer(' + _hotspotName + ')')

    }, function() {
        // 取消操作
    });
}

// 子层不能拖拽到父层之外
function doChidLayerOnUp(parentLayer, childerLayer) {

    var krpano = document.getElementById("pano0107KrpanoNewObject");
    var childerLayerMaxX = parseFloat(krpano.get('layer[' + parentLayer + '].imagewidth')) - 15;
    var childerLayerX = parseFloat(krpano.get('layer[' + childerLayer + '].x'));
    var childerLayerMaxY = parseFloat(krpano.get('layer[' + parentLayer + '].imageheight')) - 15;
    var childerLayerY = parseFloat(krpano.get('layer[' + childerLayer + '].y'));

    if (childerLayerX < 0) {
        krpano.set('layer[' + childerLayer + '].x', 5);
    }
    if (childerLayerY < 0) {
        krpano.set('layer[' + childerLayer + '].y', 5);
    }
    if (childerLayerMaxX < childerLayerX) {
        krpano.set('layer[' + childerLayer + '].x', childerLayerMaxX);
    }
    if (childerLayerMaxY < childerLayerY) {
        krpano.set('layer[' + childerLayer + '].y', childerLayerMaxY);
    }
}
