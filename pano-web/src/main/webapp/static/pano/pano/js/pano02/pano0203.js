/**
 * 全景图热点编辑
 */
// 选中的当前热点信息
var selectedHotspotInfo = {};
// 初期化处理。
$(document).ready(function() {

    // 添加单点热点
    $('#btn_add').click(function() {
        var targetUrl = 'pano0208/';
        var urlParam = form2js($("#pano0203FormAjaxSubmit")[0]);
        urlParam['returnTargetIframe'] = window.name;
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '选择单点热点',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });

    // 弹出菜单中删除热点
    $('#button-delete-hotspot').click(function() {
        pano0203RemoveHotspot(selectedHotspotInfo['hotspotName']);
    });

    // 弹出菜单中编辑热点图片
    $('#button-edit-icon').click(function() {
        var targetUrl = 'pano0208/';
        var urlParam = form2js($("#pano0203FormAjaxSubmit")[0]);
        urlParam['returnTargetIframe'] = window.name;
        urlParam['hotspotOldType'] = selectedHotspotInfo['materialTypeId'];
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '选择单点热点',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });

    // 弹出菜单中编辑热点大小
    $('#button-edit-size').click(function() {
        layer.close(layer.index);
        // 当前视角点
        setLookAtForEdit();
        
        var targetUrl = 'pano0209/';
        var urlParam = form2js($("#pano0203FormAjaxSubmit")[0]);
        urlParam['returnTargetIframe'] = window.name;
        urlParam['pano0203OperateFlag'] = 'size';
        urlParam['pano0203MusicHotspot'] = 'false';
        urlParam['positionAthForEdit'] = selectedHotspotInfo['positionAth'];
        urlParam['positionAtvForEdit'] = selectedHotspotInfo['positionAtv'];
        urlParam['pano0203HotspotName'] = selectedHotspotInfo['hotspotName'];
        urlParam['pano0203HotspotAth'] = selectedHotspotInfo['hotspotAth'];
        urlParam['pano0203HotspotAtv'] = selectedHotspotInfo['hotspotAtv'];
        // 保留一位效数
        urlParam['hotspotScale'] = accounting.formatNumber(selectedHotspotInfo['hotspotScale'], 1, '').toString();
        urlParam['pano0209hotspotId'] = selectedHotspotInfo['hotspotImageId'];
        urlParam['expositionIdForPano0209'] = urlParam['expositionId'];
        urlParam['panoramaIdForPano0209'] = urlParam['panoramaId'];
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '选择单点热点',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });

    // 保存按钮按下，录入热点信息
    $('#btn_entry').click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存当前数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            var hotspotInfoList = [];
            var krpano = document.getElementById("pano0203KrpanoNewObject");

            for (var m = 0; m < krpano.get('hotspot.count'); m++) {
                var hotspotImageTypeId = krpano.get('hotspot[' + m + '].hotspotImageTypeId');
                // 检查该热点类型
                var hotspotType = "";
                if (hotspotImageTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE) {
                    // 导航热点
                    hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE;
                }
                if (hotspotImageTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_IMAGE) {
                    // 普通热点
                    hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_IMAGE;
                }
                if (hotspotImageTypeId == "2") {
                    // 音频热点
                    hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC;
                }
                if (hotspotImageTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_LOGO) {
                    // LOGO热点
                    hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_LOGO;
                }
                var _positionAth = krpano.get('hotspot[' + m + '].ath').toString();
                var _positionAtv = krpano.get('hotspot[' + m + '].atv').toString();
                var positionAth = "";
                var positionAtv = "";
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

                var recordData = {};
                recordData['hotspotId'] = krpano.get('hotspot[' + m + '].name').split('v_').join('');
                recordData['hotspotAth'] = positionAth;
                recordData['hotspotAtv'] = positionAtv;
                recordData['hotspotScale'] = krpano.get('hotspot[' + m + '].scale');
                recordData['hotspotType'] = hotspotType;

                if (krpano.get('hotspot[' + m + '].hasSecondImage') == 'yes') {
                    recordData['hotspotImageId'] = krpano.get('hotspot[' + m + '].firstImageId');
                    recordData['secondHotspotImageId'] = krpano.get('hotspot[' + m + '].secondImageId');
                    recordData['firstSortkey'] = krpano.get('hotspot[' + m + '].firstSortkey');
                    recordData['secondSortkey'] = krpano.get('hotspot[' + m + '].secondSortkey');

                } else {
                    recordData['hotspotImageId'] = krpano.get('hotspot[' + m + '].hotspotImageId');
                }

                hotspotInfoList.push(recordData);

            }
            // 做成FormData对象
            var ajaxSubmitFormData = form2js($("#pano0203FormAjaxSubmit")[0]);
            // var hotspotInfoData =
            // $.param(hotspotInfoList.serializeObject("hotspotInfoList"))
            var hotspotInfoData = hotspotInfoList.serializeObject("hotspotInfoList")
            ajaxSubmitFormData = $.extend({}, hotspotInfoData, ajaxSubmitFormData);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0203/doSave'),
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

    });

    // 初始化场景信息
    doInitPanoInfo();
});

// 初始化场景信息
function doInitPanoInfo() {
    window.top.krpanoMaskLoading();
    var ajaxSubmitFormData = form2js($("#pano0203FormAjaxSubmit")[0]);
    // 引擎配置文件路径
    var xmlfilePath = "pano0203/";
    xmlfilePath = xmlfilePath + ajaxSubmitFormData['expositionId'];
    xmlfilePath = xmlfilePath + "/panoramas/";
    xmlfilePath = xmlfilePath + ajaxSubmitFormData['panoramaId'];
    xmlfilePath = xmlfilePath + "/show_l.xml";
    var xmlSessionFilePath = window.top.getSessionFileEditPath(xmlfilePath);
    // 启动引擎
    removepano("pano0203KrpanoNewObject");
    embedpano({
        swf : PanoConstants.VAL_EMBEDPANO_SWF,
        id : 'pano0203KrpanoNewObject',
        xml : xmlSessionFilePath,
        target : "pano0203Pano",
        wmode : "transparent",
        html5 : "prefer",
        passQueryParameters : true
    // ,onready : doKrpanOonready
    });

}
// krpano加载完处理
function doPano0203KrpanoOnloadcomplete() {
    var krpano = document.getElementById("pano0203KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        window.top.removekrpanoMask();
        return false;
    }

    // 图上加载热点处理
    if ($('#hotspotInfoListJson').val() == undefined || $('#hotspotInfoListJson').val().length == 0) {
        window.top.removekrpanoMask();
        return false;
    }

    var hotspotInfoList = JSON.parse($('#hotspotInfoListJson').val());
    if (hotspotInfoList == null || hotspotInfoList.length <= 0) {
        window.top.removekrpanoMask();
        return false;
    }

    // var krpano = document.getElementById("pano0203KrpanoNewObject");
    // if (krpano == null || undefined == krpano.get) {
    // window.top.removekrpanoMask();
    // return false;
    // }

    // 读取已有的热点信息
    jQuery.each(hotspotInfoList, function(i, recordData) {
        var _hotspotType = recordData.hotspotType;
        var newspot = 'v_' + recordData.hotspotId;
        krpano.call('addhotspot(' + newspot + ')');
        // 初期显示时保持热点图片的原始尺寸的设定
        krpano.set('hotspot[' + newspot + '].zoom', true);

        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_POLYGON) {// 多边形热点
            krpano.call('addhotspot(' + newspot + ')');
            krpano.set('hotspot[' + newspot + '].zorder', "1");
            // 多边形点的设定
            $(recordData.pointList).each(function(pointIndex, pointData) {
                krpano.set('hotspot[' + newspot + '].point[' + pointIndex + '].ath', pointData.polygonPointAth);
                krpano.set('hotspot[' + newspot + '].point[' + pointIndex + '].atv', pointData.polygonPointAtv);
            });
            krpano.set('hotspot[' + newspot + '].onclick', 'js(pano0203RemoveHotspot(get(name));)');
            krpano.set('hotspot[' + newspot + '].bordercolor', '0x489620');
            krpano.set('hotspot[' + newspot + '].fillalpha', '0.4');
            krpano.set('hotspot[' + newspot + '].fillcolor', '0x489620');
        } else {// 普通热点

            // APP服务器侧的路径
            var hotspotImagePath = window.top.getSessionFileEditPath(recordData.hotspotImagePath);
            krpano.set('hotspot[' + newspot + '].zorder', "2");
            // 判断当前热点显示图片是否是gif并有对应的png图片
            if (recordData.hasPngImage == 'true') {
                var gifstyle = 'gifstyle' + recordData.hotspotId;
                krpano.call('addstyle(' + gifstyle + ')');
                krpano.set('style[' + gifstyle + '].name', gifstyle);
                var hotspotImagePathForGif = hotspotImagePath.substring(0, hotspotImagePath.lastIndexOf(".")) + ".png";
                krpano.set('style[' + gifstyle + '].url', hotspotImagePathForGif);
                krpano.set('style[' + gifstyle + '].crop', '0|0|' + recordData.gifWidth + '|' + recordData.gifHeight);
                krpano.set('style[' + gifstyle + '].framewidth', recordData.gifWidth);
                krpano.set('style[' + gifstyle + '].frameheight', recordData.gifHeight);
                krpano.set('style[' + gifstyle + '].frame', '0');
                krpano.set('style[' + gifstyle + '].lastframe', recordData.gifFrameCount);
                krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + recordData.gifDelayTime + ');');
                krpano.call('hotspot[' + newspot + '].loadstyle(' + gifstyle + ');');
            } else {
                // 显示数据库中的热点图片
                if (hotspotImagePath != null && hotspotImagePath != '') {
                    krpano.set('hotspot[' + newspot + '].url', hotspotImagePath);
                }
            }
            krpano.set('hotspot[' + newspot + '].ath', recordData.hotspotAth);
            krpano.set('hotspot[' + newspot + '].atv', recordData.hotspotAtv);

            // 判断是否是单点音频热点
            if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                krpano.set('hotspot[' + newspot + '].hasSecondImage', 'yes');
                krpano.set('hotspot[' + newspot + '].firstImageId', recordData.firstHotspotImageId);
                krpano.set('hotspot[' + newspot + '].secondImageId', recordData.secondHotspotImageId);
                krpano.set('hotspot[' + newspot + '].firstSortkey', recordData.firstSortkey);
                krpano.set('hotspot[' + newspot + '].secondSortkey', recordData.secondSortkey);

                krpano.set('hotspot[' + newspot + '].onclick', 'js(pano0203OpenHotspotCommandChoice2Div(get(name),'
                        + recordData.materialTypeId + ',get(ath),get(atv),get(scale),'
                        + recordData.secondHotspotImageId + ',' + recordData.hotspotImageId + ','
                        + recordData.firstSortkey + ',' + recordData.secondSortkey + '))');
                krpano.set('hotspot[' + newspot + '].hotspotImageTypeId', '2');
            } else {
                krpano.set('hotspot[' + newspot + '].onclick', 'js(pano0203OpenHotspotCommandChoiceLayer(get(name),'
                        + recordData.materialTypeId + ',get(ath),get(atv),get(scale),' + recordData.hotspotImageId
                        + '))');
                krpano.set('hotspot[' + newspot + '].hotspotImageTypeId', recordData.materialTypeId);
            }

            krpano.set('hotspot[' + newspot + '].ondown', 'draghotspot();');
            krpano.set('hotspot[' + newspot + '].hotspotImageId', recordData.hotspotImageId);

            // 初期显示时保持热点图片的原始尺寸的设定
            // krpano.set('hotspot['+newspot+'].zoom',true);
            var hotspotScale = recordData.hotspotScale
            if (hotspotScale != "" && hotspotScale != null) {
                krpano.set('hotspot[' + newspot + '].scale', hotspotScale);
            } else {
                krpano.set('hotspot[' + newspot + '].scale', "1.0");
            }
        }

    });
    // 移除遮盖层
    window.top.removekrpanoMask();
}
// 删除热点
function pano0203RemoveHotspot(_hotspotName) {
    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('正在删除当前热点，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() {
        window.top.layer.close(currentConfirmIndex);
        layer.close(layer.index);
        var krpano = document.getElementById("pano0203KrpanoNewObject");
        krpano.call('removehotspot(' + _hotspotName + ')');
        var showMessage = '删除成功！';
        window.top.showSuccessMessage(showMessage, false, 3);
    }, function() {
        // 取消操作
    });
}

// 打开热点操作指令框
function pano0203OpenHotspotCommandChoiceLayer(_hotspotName, _materialTypeId, _ath, _atv, _hotspotScale,
        _hotspotImageId) {

    selectedHotspotInfo['hotspotName'] = _hotspotName;
    selectedHotspotInfo['materialTypeId'] = _materialTypeId;
    selectedHotspotInfo['hotspotAth'] = _ath;
    selectedHotspotInfo['hotspotAtv'] = _atv;
    selectedHotspotInfo['hotspotScale'] = _hotspotScale;
    selectedHotspotInfo['hotspotImageId'] = _hotspotImageId;
    layer.open({
        id : 'LAY_pano0203OpenHotspotCommandChoiceLayer', // 设定一个id，防止重复弹出
        title : '热点操作', // false不显示标题栏
        type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        area : [ '260px', 'auto' ], // 宽高
        content : $('#pano0203HotspotCommandChoice'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        end : function() {
            // location.reload(true);
            // searchData();
            $('#pano0203HotspotCommandChoice').hide();
        }
    });
}

// 跳转画面前取得当前视角点
function setLookAtForEdit() {

    // 得到当前场景的中心点
    var krpano = document.getElementById("pano0203KrpanoNewObject");
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
    selectedHotspotInfo['positionAth'] = positionAth;
    selectedHotspotInfo['positionAtv'] = positionAtv;
}