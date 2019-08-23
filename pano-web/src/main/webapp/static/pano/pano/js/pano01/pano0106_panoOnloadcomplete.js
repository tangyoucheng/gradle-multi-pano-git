/**
 * 全景图地图编辑操作
 */
$(document).ready(function() {

});

// krpano加载完处理
function doPano0106KrpanoOnloadcomplete() {
    var krpano = document.getElementById("pano0106KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        window.top.removekrpanoMask();
        return false;
    }

    // 显示导航图
    showMiniMap();

    // 移除遮盖层
    window.top.removekrpanoMask();
}
// 显示导航图
function showMiniMap() {

    var krpano = document.getElementById("pano0106KrpanoNewObject");
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
        // krpano.set('layer['+newlayer+'].ondown','draglayer();');
    }

}
