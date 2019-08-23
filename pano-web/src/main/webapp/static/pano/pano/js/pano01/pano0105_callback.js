//素材检索页面，选中素材后返回方法
function setPano0208ReturnObject(returnObject) {

    var hotspotInfo = returnObject[0]; // 非音频的场合
    
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    // 移除旧的layer
    krpano.call('removelayer(flowInfoLayer)')
    // 添加新的layer
    $('#flowInfoFileId').val(hotspotInfo['materialId']);
    $('#flowInfoType').val(hotspotInfo['materialTypeId']);
    $('#flowInfoFilePath').val(hotspotInfo['materialPath']);
    $('#flowInfoFileInfo').val(hotspotInfo['flowTextInfo']);

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
    if (hotspotInfo['materialTypeId'] == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE) {
        var materialPath = window.top.getSessionFileEditPath(hotspotInfo['materialPath']);
        krpano.set('layer[' + newlayer + '].url', materialPath);
    } else {
        // 如果是文字浮动信息
        krpano.set('layer[' + newlayer + '].url', '%SWFPATH%/plugins/textfield.swf');
        krpano.set('layer[' + newlayer + '].html', hotspotInfo['flowTextInfo']);
        krpano.set('layer[' + newlayer + '].css',
            'text-align:center; font-family:Arial; font-weight:bold; font-size:36px;');
        krpano.set('layer[' + newlayer + '].scalechildren', true);
    }
    krpano.set('layer[' + newlayer + '].backgroundalpha', "0");
    krpano.set('layer[' + newlayer + '].height', "auto");
    krpano.set('layer[' + newlayer + '].width', "auto");
    krpano.set('layer[' + newlayer + '].onclick', 'js(pano0105LayerClick(get(name)));');
}

// 热点调整大小处理返回方法
function setPano0209ReturnObject(returnObject) {
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    krpano.set('hotspot[' + selectedHotspotInfo['hotspotName'] + '].scale', returnObject['hotspotScale']);

}