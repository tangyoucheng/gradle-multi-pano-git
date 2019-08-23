/**
 * 场景地图上热点的操作
 */
$(document).ready(function() {

    // 添加导航图上的热点
    $('#add-mini-map-hotspot-button').click(function() {
        addMapHotspot();
    });

    // 保存热点操作
    $('#save-button').click(function() {
        saveMapHotspot();
    });

    // 初始化场景信息
    doInitPanoInfo()

});

// 初始化场景信息
function doInitPanoInfo() {
    window.top.krpanoMaskLoading();
    // 引擎配置文件路径
    var xmlSessionFilePath = $('#panoramaPath').val();
    // 初始化参数
    var panoSettings = {};
    // var panoramaHlookat = $('#pano0107positionAthForEdit').val();
    // var panoramaVlookat = $('#pano0107positionAtvForEdit').val();
    // if (panoramaHlookat != '' && panoramaHlookat != null && panoramaVlookat
    // != '' && panoramaVlookat != null) {
    // panoSettings['view.hlookat'] = panoramaVlookat;
    // panoSettings['view.vlookat'] = panoramaVlookat;
    // }
    // 启动引擎
    removepano("pano0107KrpanoNewObject");
    embedpano({
        swf : PanoConstants.VAL_EMBEDPANO_SWF,
        id : 'pano0107KrpanoNewObject',
        xml : xmlSessionFilePath,
        target : "pano0107Pano",
        wmode : "transparent",
        html5 : "prefer",
        passQueryParameters : true,
        vars : panoSettings,
        onready : window.top.removekrpanoMask
    });

    // 图上加载热点处理
    // loadPano0104HotspotInfo(result.hotspotInfoList);

}

// 添加小地图上的热点
function addMapHotspot() {
    var krpano = document.getElementById("pano0107KrpanoNewObject");
    var newlayer = 'newlayer' + new Date().getTime() + krpano.get('layer.count');
    krpano.call('addlayer(' + newlayer + ')');
    // TODO 测试图片
    krpano.set('layer[' + newlayer + '].parent', 'layer_nimi_map_container');
    krpano.set('layer[' + newlayer + '].url', $('#styleSelect').val());
    krpano.set('layer[' + newlayer + '].align', 'rightbottom');
    krpano.set('layer[' + newlayer + '].bgalpha', '1.0');
    krpano.set('layer[' + newlayer + '].x', 10);
    krpano.set('layer[' + newlayer + '].y', 10);
    krpano.set('layer[' + newlayer + '].handcursor', true);
    krpano.set('layer[' + newlayer + '].ondown', 'draglayer();');
    krpano.set('layer[' + newlayer + '].onup', 'js(doChidLayerOnUp(layer_nimi_map_container,get(name)));');
    krpano.set('layer[' + newlayer + '].onclick', 'js(pano0107RemoveLayer(get(name)))');
}

// 保存导航图上的热点
function saveMapHotspot() {

    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('正登录当前页面的数据，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() { // 确认操作
        window.top.layer.close(currentConfirmIndex);
        
        var hotspotInfoList = [];

        var krpano = document.getElementById("pano0107KrpanoNewObject");
        for (var m = 0; m < krpano.get('layer.count'); m++) {
            var _parentLayer = '';
            if (krpano.get('layer[' + m + '].parent') != null) {
                _parentLayer = krpano.get('layer[' + m + '].parent').toString();
            }
            // 判断是否有父层，有父层即为导航图热点
            if (_parentLayer != '' && _parentLayer.indexOf('layer_nimi_map_container') != -1) {
                var _positionX = krpano.get('layer[' + m + '].x').toString();
                var _positionY = krpano.get('layer[' + m + '].y').toString();
                var _url = krpano.get('layer[' + m + '].url');
                var positionX = "";
                var positionY = "";
                // 平面图的场合
                if (Number(_positionX) >= 0) {
                    // 正数的场合
                    positionX = Math.round(parseFloat(_positionX) * 1000) / 1000;
                } else {
                    // 负数的场合
                    positionX = _positionX.substring(0, 1) + Math.round(parseFloat(_positionX.substring(1)) * 1000)
                            / 1000;
                }
                if (Number(_positionY) >= 0) {
                    // 正数的场合
                    positionY = Math.round(parseFloat(_positionY) * 1000) / 1000;
                } else {
                    // 负数的场合
                    positionY = _positionY.substring(0, 1) + Math.round(parseFloat(_positionY.substring(1)) * 1000)
                            / 1000;
                }
                var recordData = {};
                recordData['expositionMapHotspotId'] = krpano.get('layer[' + m + '].name').split('v_').join('');
                recordData['expositionMapHotspotX'] = positionX;
                recordData['expositionMapHotspotY'] = positionY;
                recordData['expositionMapHotspotUrl'] = _url;
                hotspotInfoList.push(recordData);
            }

        }

        // 做成FormData对象
        var ajaxSubmitFormData = form2js($("#pano0107FormAjaxSubmit")[0]);
        // var hotspotInfoData =
        // $.param(hotspotInfoList.serializeObject("hotspotInfoList"))
        var hotspotInfoData = hotspotInfoList.serializeObject("miniMapSpotInfoList")
        ajaxSubmitFormData = $.extend({}, hotspotInfoData, ajaxSubmitFormData);

        $.ajax({
            type : 'post',
            traditional : true,
            data : ajaxSubmitFormData,
            dataType : 'json',
            url : getMemberContextPath('pano0107/doSave'),
            success : function(result) {
                if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                    // 关闭确认对话框
                    window.top.layer.close(currentConfirmIndex);
                    // 自页面关闭
                    var index = parent.layer.getFrameIndex(window.name);
                    window.top.layer.close(index);
                }
            }
        })

    }, function() {
        // 取消操作
    });

}