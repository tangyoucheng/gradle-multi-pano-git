// 热点调整大小处理返回方法
function setPano0209ReturnObject(returnObject) {
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    krpano.set('hotspot[' + selectedHotspotInfo['hotspotName'] + '].scale', returnObject['hotspotScale']);

}