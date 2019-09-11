/**
 * 图片热点编辑
 */
$(document).ready(function() {

    // 多边形热点编辑手册按钮
    $('#btn_show_manual').click(function() {
        var targetUrl = 'pano0309/';
        var urlParam = {};
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '编辑手册',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '520px', '360px' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 确定登录按钮按下，录入热点信息
    $('#btn_entry').click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存当前多边形热点，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);

            var hotspotInfoList = getHotspotInfoList();
            // 提交后台
            var _ajaxUrl = getMemberContextPath('pano0108/doSave');
            $('#spotInfoListJson').val(JSON.stringify(hotspotInfoList));
            var param = $("#pano0108FormAdd").serialize();
            jQuery.post(_ajaxUrl, param, function(data) {
                if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                    location.reload(true);
                }
            });

        }, function() {
            // 取消操作
        });

    });

    // 初始化场景信息
    doInitPanoInfo();
});

// 初始化场景信息
function doInitPanoInfo() {
    // 如果flash插件没有启用的场合，遮盖层不能消失。所以暂时注释掉此处理。
    // window.top.krpanoMaskLoading();
    var ajaxSubmitFormData = form2js($("#pano0108FormAjaxSubmit")[0]);
    // 引擎配置文件路径
    var xmlfilePath = "pano0108/";
    xmlfilePath = xmlfilePath + ajaxSubmitFormData['expositionId'];
    xmlfilePath = xmlfilePath + "/panoramas/";
    xmlfilePath = xmlfilePath + ajaxSubmitFormData['panoramaId'];
    xmlfilePath = xmlfilePath + "/show_l.xml";
    var xmlSessionFilePath = window.top.getSessionFileEditPath(xmlfilePath);
    // 启动引擎
    removepano("pano0108KrpanoNewObject");
    embedpano({
        swf : PanoConstants.VAL_EMBEDPANO_SWF,
        id : 'pano0108KrpanoNewObject',
        xml : xmlSessionFilePath,
        target : "pano0108Pano",
        wmode : "opaque",
        flash : "only",
        passQueryParameters : true,
        bgcolor : "#f5f5f5"
    // ,onready : doKrpanOonready
    });

}

// 取得所有热点对象
function getHotspotInfoList() {
    var hotspotInfoList = [];
    var krpano = document.getElementById("pano0108KrpanoNewObject");

    for (var m = 0; m < krpano.get('hotspot.count'); m++) {
        var polygonPointInfoList = [];

        var polygonPointCount = krpano.get('hotspot[' + m + '].point').count
        if (polygonPointCount > 2) {
            // 多边形组成点信息
            for (var n = 0; n < krpano.get('hotspot[' + m + '].point').count; n++) {

                var _polygonPointAtv = krpano.get('hotspot[' + m + '].point[' + n + '].atv').toString();
                var _polygonPointAth = krpano.get('hotspot[' + m + '].point[' + n + '].ath').toString();
                var polygonPointAtv = "";
                var polygonPointAth = "";
                if (Number(_polygonPointAth) >= 0) {
                    // 正数的场合
                    polygonPointAth = Math.round(parseFloat(_polygonPointAth));
                } else {
                    // 负数的场合
                    polygonPointAth = _polygonPointAth.substring(0, 1)
                            + Math.round(parseFloat(_polygonPointAth.substring(1)));
                }
                if (Number(_polygonPointAtv) >= 0) {
                    // 正数的场合
                    polygonPointAtv = Math.round(parseFloat(_polygonPointAtv));
                } else {
                    // 负数的场合
                    polygonPointAtv = _polygonPointAtv.substring(0, 1)
                            + Math.round(parseFloat(_polygonPointAtv.substring(1)));
                }
                var hotspotPoint = {};
                hotspotPoint['polygonPointId'] = krpano.get('hotspot[' + m + '].point[' + n + '].name').split('v_')
                        .join('');
                hotspotPoint['polygonPointAth'] = polygonPointAth;
                hotspotPoint['polygonPointAtv'] = polygonPointAtv;
                polygonPointInfoList.push(hotspotPoint);
            }
        }

        if (polygonPointInfoList.length != 0) {
            var mapHotspot = {};
            mapHotspot['hotspotId'] = krpano.get('hotspot[' + m + '].name').split('v_').join('');
            mapHotspot['hotspotPointListJson'] = JSON.stringify(polygonPointInfoList);
            hotspotInfoList.push(mapHotspot);
        }
    }
    return hotspotInfoList;
}