//素材检索页面，选中素材后返回方法
function setPano0208ReturnObject(returnObject) {
    // 设定增加的热点
    if (returnObject.length == 1) { // 非音频的场合
        var hotspotInfo = returnObject[0];

        var krpano = document.getElementById("pano0203KrpanoNewObject");
        var newspot = 'newspot' + krpano.get('hotspot.count');

        newspot = 'v_' + newspot;
        krpano.call('addhotspot(' + newspot + ')');

        var gifstyle = 'gifstyle' + Math.random();
        krpano.call('addstyle(' + gifstyle + ')');
        krpano.set('style[' + gifstyle + '].name', gifstyle);
        var rebornAth = krpano.get('hotspot[' + newspot + '].ath');
        var rebornAtv = krpano.get('hotspot[' + newspot + '].atv');
        var rebornScale = krpano.get('hotspot[' + newspot + '].scale');
        krpano.call('removehotspot(' + newspot + ')');
        krpano.call('addhotspot(' + newspot + ')');
        krpano.set('hotspot[' + newspot + '].ath', rebornAth);
        krpano.set('hotspot[' + newspot + '].atv', rebornAtv);
        krpano.set('hotspot[' + newspot + '].scale', rebornScale);
        krpano.set('hotspot[' + newspot + '].ondown', 'draghotspot();');
        krpano.set('hotspot[' + newspot + '].zorder', "3");
        krpano.set('hotspot[' + newspot + '].zoom', true);

        var hotspotImageUrl = window.top.getSessionFileEditPath(hotspotInfo.materialPath);
        // 判断当前热点显示图片是否是gif并有对应的png图片
        if (hotspotInfo.hasPngImage == 'true') {
            
            var hotspotImageUrlForGif = hotspotImageUrl.substring(0, hotspotImageUrl.lastIndexOf(".")) + ".png";
            // 添加随机数，防止因为缓存，不显示最新图片
            hotspotImageUrlForGif = hotspotImageUrlForGif + '?temp=';
            hotspotImageUrlForGif = hotspotImageUrlForGif + Math.random();
            krpano.set('style[' + gifstyle + '].url', hotspotImageUrlForGif);

            krpano.set('style[' + gifstyle + '].crop', '0|0|' + hotspotInfo.gifWidth + '|' + hotspotInfo.gifHeight);
            krpano.set('style[' + gifstyle + '].framewidth', hotspotInfo.gifWidth);
            krpano.set('style[' + gifstyle + '].frameheight', hotspotInfo.gifHeight);
            krpano.set('style[' + gifstyle + '].frame', '0');
            krpano.set('style[' + gifstyle + '].lastframe', hotspotInfo.gifFrameCount);
            krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + hotspotInfo.gifDelayTime + ');');
            krpano.call('hotspot[' + newspot + '].loadstyle(' + gifstyle + ');');
        } else {
            krpano.set('hotspot[' + newspot + '].url', hotspotImageUrl);
        }

        krpano.set('hotspot[' + newspot + '].onclick', 'js(pano0203OpenHotspotCommandChoiceLayer(get(name),'
                + hotspotInfo.materialTypeId + ',get(ath),get(atv),get(scale),' + hotspotInfo.materialId + '))');
        krpano.set('hotspot[' + newspot + '].hotspotImageId', hotspotInfo.materialId);
        krpano.set('hotspot[' + newspot + '].hotspotImageTypeId', hotspotInfo.materialTypeId);

    }
    if (returnObject.length == 2) { // 音频的场合

    }
}

// 热点调整大小处理返回方法
function setPano0209ReturnObject(returnObject) {
    var krpano = document.getElementById("pano0203KrpanoNewObject");
    krpano.set('hotspot[' + selectedHotspotInfo['hotspotName'] + '].scale', returnObject['hotspotScale']);

}