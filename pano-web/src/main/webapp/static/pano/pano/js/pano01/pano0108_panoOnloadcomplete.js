/**
 * 图片多边形热点编辑
 */
$(document).ready(function() {
});

// krpano加载完处理
function doPano0108KrpanoOnloadcomplete() {
    loadHotspotInfo();

    // 移除遮盖层 如果flash插件没有启用的场合，遮盖层不能消失。所以暂时注释掉此处理。
    // window.top.removekrpanoMask();
}

// 图上加载热点处理
function loadHotspotInfo() {
    if ($('#spotInfoListJson').val() == undefined || $('#spotInfoListJson').val().length == 0) {
        return false;
    }
    var spotInfoList = JSON.parse($('#spotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }
    var krpano = document.getElementById("pano0108KrpanoNewObject");
    if (undefined == krpano.get) {
        return false;
    }

    // 读取已有的热点信息
    $(spotInfoList).each(function(index, hotspotData) {
        var newspot = 'v_' + hotspotData.hotspotId;
        krpano.call('addhotspot(' + newspot + ')');
        // 多边形点的设定
        $(hotspotData.pointList).each(function(pointIndex, pointData) {
            krpano.set('hotspot[' + newspot + '].point[' + pointIndex + '].ath', pointData.polygonPointAth);
            krpano.set('hotspot[' + newspot + '].point[' + pointIndex + '].atv', pointData.polygonPointAtv);
        });
        // 初期显示时保持热点图片的原始尺寸的设定
        krpano.set('hotspot[' + newspot + '].zoom', true);
        krpano.set('hotspot[' + newspot + '].onclick', 'js(pano0108RemoveHotspot(get(name));)');
        krpano.set('hotspot[' + newspot + '].bordercolor', '0x489620');
        krpano.set('hotspot[' + newspot + '].fillalpha', '0.4');
        krpano.set('hotspot[' + newspot + '].fillcolor', '0x489620');
        // return false;
    });
}

// 删除热点
function pano0108RemoveHotspot(_hotspotName) {
    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('正在删除当前多边形热点，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() {
        window.top.layer.close(currentConfirmIndex);

        var krpano = document.getElementById("pano0108KrpanoNewObject");
        krpano.call('removehotspot(' + _hotspotName + ')')

        var showMessage = '删除成功！';
        window.top.showSuccessMessage(showMessage, false, 3);
    }, function() {
        // 取消操作
    });
}
