/**
 * 全景图热点显示
 */

// 自定义gallery层名
var _hotspotGalleryName;
// 自定义热点下是否是视频
var _isVideoHotspot = '';
// 自定义视频地址
var _videoPrviewPath;
// 自定义热点下是否是外部链接
var _isLinkHotspot = '';
// 自定义热点链接地址
var _linkAddress;
// 自定义场景跳转后上个场景的导航点ID
var _theLastedSceneHotspotId;
// 自定义场景跳转后推荐热线点ID
var _currentRecommendHotspotId;
// 热点大小
var hotspotScale;
// 自定义图文
var _isTextimg;

$(document).ready(function() {
});

// krpano加载完处理
function doPano0104KrpanoOnloadcomplete() {
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        window.top.removekrpanoMask();
        return false;
    }

    // 图上加载热点处理
    loadPano0104HotspotInfo();
    // if ($('#hotspotInfoListJson').val() == undefined ||
    // $('#hotspotInfoListJson').val().length == 0) {
    // window.top.removekrpanoMask();
    // return false;
    // }
    // var hotspotInfoList = JSON.parse($('#hotspotInfoListJson').val());
    // if (hotspotInfoList == null || hotspotInfoList.length <= 0) {
    // window.top.removekrpanoMask();
    // return false;
    // }

    // 移除遮盖层
    window.top.removekrpanoMask();
}

// 图上加载热点处理
function loadPano0104HotspotInfo() {

    var krpano = document.getElementById("pano0104KrpanoNewObject");
    krpano.set('hotspot[recommedInfoHotspot].visible', false);
    var pointList;
    // 读取已有的热点信息
    krpano.set('layer[radar].visible', false);
    jQuery.each(pano0104CurrentForm.hotspotInfoList, function(i, recordData) {
        var _hotspotType = recordData.hotspotType;
        var newspot = 'v_' + recordData.hotspotId;
        krpano.call('addhotspot(' + newspot + ')');
        krpano.set('layer[tooltip].visible', true);
        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE) {
            if (recordData.hotspotTooltip == null || recordData.hotspotTooltip == '') {
                krpano.set('hotspot[' + newspot + '].tooltip', $('#expoGoSceneTooltipInfo').val());
            } else {
                krpano.set('hotspot[' + newspot + '].tooltip', recordData.hotspotTooltip);
            }
        } else {
            krpano.set('hotspot[' + newspot + '].tooltip', recordData.hotspotTooltip);
        }

        krpano.call('hotspot[' + newspot + '].loadstyle(tooltip)');

        // 多边形热点
        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_POLYGON) {
            // 设置多边形热点基本属性
            krpano.set('hotspot[' + newspot + '].zorder', "1");
            krpano.set('hotspot[' + newspot + '].galleryname', 'v_' + recordData.hotspotId + '_gallery');
            krpano.set('hotspot[' + newspot + '].onclick', 'js(showVtourPolygonHotspotInfo(get(name),get(galleryname),'
                    + recordData.externalLinkAddress + ',' + recordData.videoPath + '))');
            krpano.set('hotspot[' + newspot + '].fillcolor', '0x489620');
            krpano.set('hotspot[' + newspot + '].fillalpha', '0.4');
            krpano.set('hotspot[' + newspot + '].bordercolor', '0x489620');
            // 如果多边形有颜色和透明度
            if (recordData.polygonFillcolor != null && recordData.polygonFillcolor != ''
                    && recordData.polygonFillalpha != null && recordData.polygonFillalpha != '') {
                krpano.set('hotspot[' + newspot + '].fillcolor', recordData.polygonFillcolor);
                krpano.set('hotspot[' + newspot + '].fillalpha', 0.1);
                krpano.set('hotspot[' + newspot + '].borderalpha', 0.1);

                // 设置鼠标悬停事件
                var hotspotHover = 'set(hotspot[' + newspot + '].fillcolor,' + recordData.polygonFillcolor + ');';
                hotspotHover = hotspotHover + 'set(hotspot[' + newspot + '].fillalpha,' + recordData.polygonFillalpha
                        + ');';
                hotspotHover = hotspotHover + 'set(hotspot[' + newspot + '].borderwidth,0);';
                krpano.set('hotspot[' + newspot + '].onhover', hotspotHover);

                // 设置鼠标离开事件
                var hotspotOut = 'set(hotspot[' + newspot + '].onout,';
                hotspotOut = hotspotOut + 'callToolTipOutEvent();set(hotspot[' + newspot + '].fillalpha,0.1);';
                hotspotOut = hotspotOut + 'set(hotspot[' + newspot + '].borderalpha,0.1););';
                var hotspotOut = hotspotOut + 'set(hotspot[' + newspot + '].fillalpha,0.1);';
                hotspotOut = hotspotOut + 'set(hotspot[' + newspot + '].borderalpha,0.1);';
                krpano.set('hotspot[' + newspot + '].onloaded', hotspotOut);
            }
            // 循环每一个多边形的点
            if (recordData.pointList != null && recordData.pointList.length != 0) {
                pointList = recordData.pointList;
                $(pointList).each(
                    function(indexs, recordDatas) {
                        krpano.set('hotspot[' + newspot + '].point[' + newspot + indexs + '].ath',
                            recordDatas.polygonPointAth);
                        krpano.set('hotspot[' + newspot + '].point[' + newspot + indexs + '].atv',
                            recordDatas.polygonPointAtv);
                    });
            }
        } else {
            krpano.set('hotspot[' + newspot + '].ath', recordData.hotspotAth);
            krpano.set('hotspot[' + newspot + '].atv', recordData.hotspotAtv);
            krpano.set('hotspot[' + newspot + '].zorder', "2");
            var Scale = recordData.hotspotScale;
            if (Scale != "" & Scale != null) {
                krpano.set('hotspot[' + newspot + '].scale', Scale);
            } else {
                krpano.set('hotspot[' + newspot + '].scale', "1.0");
            }
        }
        // 导航热点
        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE) {
            krpano.set('hotspot[' + newspot + '].onclick', 'js(showVtourGuideHotspotInfo(get(name)))');
            // 读取导航热点中被设置为推荐路线点的提示信息
            if (recordData.recommendInfo != null && recordData.recommendInfo != '') {
                // 推荐路线信息为图片时
                krpano.set('hotspot[recommedInfoHotspot].ath', recordData.hotspotAth);
                krpano.set('hotspot[recommedInfoHotspot].atv', recordData.hotspotAtv);
                // 推荐路线信息为文字时
                // krpano.set('hotspot[v_'+$('#currentRecommendHotspotId').val()+'].onloaded','showrecommend(3,recommedInfoPlugin)');
            }
        }
        // 普通热点
        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_IMAGE) {
            krpano.set('hotspot[' + newspot + '].galleryname', 'v_' + recordData.hotspotId + '_gallery');
            krpano.set('hotspot[' + newspot + '].onclick', 'js(showVtourNormalHotspotInfo(get(name),get(galleryname),'
                    + recordData.externalLinkAddress + ',' + recordData.videoPath + '))');
        }
        // 音频热点
        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
            krpano.set('hotspot[' + newspot + '].galleryname', 'v_' + recordData.hotspotId + '_gallery');
            krpano.set('hotspot[' + newspot + '].onclick', 'js(showVtourNormalHotspotInfo(get(name),get(galleryname),'
                    + recordData.externalLinkAddress + ',' + recordData.videoPath + '))');
        }
        // LOGO热点
        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_LOGO) {
            krpano.set('hotspot[' + newspot + '].onclick', 'js(setLogoHotspotSize(get(name));)');
        }

        // APP服务器侧的路径
        var hotspotImagePath = null
        if (recordData.hotspotImagePath != null && recordData.hotspotImagePath.length > 0) {
            hotspotImagePath = window.top.getSessionFileEditPath(recordData.hotspotImagePath);
        }
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
            // 如果在gif热点中，是被选为推荐线路点的导航热点，则在其onloaded方法中追加showrecommend方法
            if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE && recordData.recommendInfo != null
                    && recordData.recommendInfo != '') {
                krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + recordData.gifDelayTime
                        + ');showrecommend(3,recommedInfoHotspot)');
                krpano.set('hotspot[' + newspot + '].onloaded', '');
            } else {
                krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + recordData.gifDelayTime + ');');
            }
            krpano.call('hotspot[' + newspot + '].loadstyle(' + gifstyle + ');');
        } else {
            // 显示数据库中的热点图片
            if (hotspotImagePath != null && hotspotImagePath != '') {
                krpano.set('hotspot[' + newspot + '].url', hotspotImagePath);
            }
        }
        if (recordData.hotspotId == $('#currentRecommendHotspotId').val()) {
            // 显示推荐路线hotspot
            showRecommendInfo();
        }
        // 初期显示时保持热点图片的原始尺寸的设定
        krpano.set('hotspot[' + newspot + '].zoom', 'true');
    });
    showMiniMap();

}

// 显示地图
function showMiniMap() {
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    // 查看当前展览有无地图，有即显示
    var check = pano0104CurrentForm.miniMapCheck;
    if (check || check == 'true') {
        var newlayer = 'layer_nimi_map_container';
        krpano.call('addlayer(' + newlayer + ')');
        krpano.set('layer[' + newlayer + '].url', pano0104CurrentForm.expositionMapPath);
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
    // 开始读取地图上的点的计时器
    loadMapHotspot();
}

// 地图上热点雷达角度全局变量
var thisRadarHeading;
// 显示小地图上的热点
function loadMapHotspot() {
    var miniMapSpotInfoList = pano0104CurrentForm.miniMapSpotInfoList;
    if (miniMapSpotInfoList == null || miniMapSpotInfoList.length == 0) {
        return false;
    }
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    // 读取已有的热点信息
    jQuery.each(miniMapSpotInfoList, function(index, recordData) {
        var newlayer = 'v_' + recordData.expositionMapHotspotId;
        thisRadarHeading = recordData.expositionMapHotspotHeading;
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
    var selectedMiniMapHotspotId = pano0104CurrentForm.selectedMiniMapHotspotId;
    if (selectedMiniMapHotspotId != undefined && selectedMiniMapHotspotId.length != 0) {
        // 被选中的地图热点zorder设为2，未被选中设为3，保证雷达扇形不覆盖在其他热点上
        jQuery.each(miniMapSpotInfoList, function(index, recordData) {
            var newlayer = 'v_' + recordData.expositionMapHotspotId;
            krpano.set('layer[' + newlayer + '].zorder', '3');
        });
        krpano.set('layer[v_' + selectedMiniMapHotspotId + '].zorder', '2');

        var radarHeading = pano0104CurrentForm.radarHeading;
        if (radarHeading != null && radarHeading != 0) {
            krpano.set('layer[radar].heading', pano0104CurrentForm.radarHeading);
        } else {
            krpano.set('layer[radar].heading', '0');
        }
        krpano.set('layer[radar].parent', 'v_' + selectedMiniMapHotspotId);
        krpano.set('layer[radar].visible', true);
        krpano.set('layer[activespot].visible', true);
    }

}
// 显示导航图上热点下的信息
function showMiniMapHotspotInfo(_hotspotName) {
    var hotspotId = _hotspotName.split('v_').join('');

    // var ajaxFormData = {};
    // ajaxFormData = $.extend({}, ajaxFormData,
    // form2js($("#pano0201FormAdd")[0]));
    // $('#radarHeading').val(offsetNum + '');
    var ajaxFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
    ajaxFormData['selectedMiniMapHotspotId'] = hotspotId;

    var krpano = document.getElementById("pano0104KrpanoNewObject");

    $.ajax({
        url : getMemberContextPath('pano0104/doShowMiniMapHotspotInfo'),
        type : "post",
        traditional : true,
        dataType : "json",
        data : ajaxFormData,
        success : function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                pano0104CurrentForm = result.obj;
                // 热点下链接了全景图的场合
                if (pano0104CurrentForm.panoramaPath != '' && pano0104CurrentForm.panoramaPath != null) {
                    krpano.call('loadpano(../' + pano0104CurrentForm.panoramaPath + ',null,MERGE, BLEND(2))');
                    krpano.set('hotspot[recommedInfoHotspot].visible', false);
                    krpano.set('layer[tooltip].visible', false);
                    $("#panoramaId").val(pano0104CurrentForm.panoramaId);

                    $("#titleInfo").html(
                        pano0104CurrentForm.expositionName + "&nbsp;——&nbsp;" + pano0104CurrentForm.panoramaName);
                    // loadPano0104HotspotInfo();
                    // if (pano0104CurrentForm.radarHeading != null &&
                    // pano0104CurrentForm.radarHeading.length != 0) {
                    // $('#radarHeading').val(pano0104CurrentForm.radarHeading);
                    // } else {
                    // $('#radarHeading').val("0");
                    // }
                    // 设定地图热点和雷达角度
                    // loadMapHotspot();

                    // 检查有无场景独立音乐
                    if (pano0104CurrentForm.hadIndependSound == 'yes') {
                        krpano.call('stopallsounds();');
                    }
                    krpano.call('stopsounds(bgsnd);');
                    krpano.call('playsound(bgsnd,' + pano0104CurrentForm.backGroundSoundPath + ', 0,0); ');
                    // $('#currentSoundPath').val(pano0104CurrentForm.backGroundSoundPath);
                }

            }
        }
    // ,
    // error : function(result) {
    // window.top.layuiRemoveLoading();
    // window.top.layer.alert(result.status);
    // }
    });
}

// 显示当前场景应当出现的推荐路线点
function showRecommendInfo() {

    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    if ($('#currentRecommendHotspotId').val() != null && $('#currentRecommendHotspotId').val() != ''
            && krpano.get("hotspot[v_" + $('#currentRecommendHotspotId').val() + "].name") != '') {
        // 推荐路线信息为图片时
        krpano.set('hotspot[recommedInfoHotspot].visible', true);
        // 推荐路线信息为文字时
        // krpano.set('plugin[recommedInfoPlugin].parent',"hotspot[v_" +
        // $('#currentRecommendHotspotId').val() + "]");
        // krpano.set('plugin[recommedInfoPlugin].html',$('#expositionRecommendInfo').val());
    }

}

// 场景上触发的相关事件处理 *******************################### START

// logo热点click事件
function setLogoHotspotSize(_hotspotName) {
    var hotspotId = _hotspotName.split('v_').join('');
    selectedHotspotInfo['hotspotName'] = _hotspotName;
    selectedHotspotInfo['hotspotId'] = hotspotId;
    callPano0209();
}

// 显示导航热点下的信息
function showVtourGuideHotspotInfo(_hotspotName) {

    var hotspotId = _hotspotName.split('v_').join('');
    selectedHotspotInfo['hotspotName'] = _hotspotName;
    selectedHotspotInfo['hotspotId'] = hotspotId;

    var _ajaxUrl = getMemberContextPath('pano0104/doCheckHostspotInfo');
    // 表单数据转换成JS对象
    var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
    ajaxSubmitFormData['panoramaId'] = currentTableRowInfo.panoramaId;
    ajaxSubmitFormData['selectedHotspotId'] = hotspotId;
    jQuery.post(_ajaxUrl, ajaxSubmitFormData, function(resultData) {
        if (CommonUtilJs.processAjaxSuccessAfter(resultData)) {
            var data = resultData.obj;
            // 数据被删除的场合
            if (data == 'delete') {
                var showMessage = '当前热点已被其他用户删除！';
                window.top.showErrorMessage(showMessage, false, 3);
            }
            // 热点下没有链接信息
            if (data == '') {
                _theLastedSceneHotspotId = hotspotId;
                layer.open({
                    id : 'LAY_pano0104OpenHotspotCommandChoiceLayer', // 设定一个id，防止重复弹出
                    title : '热点操作', // false不显示标题栏
                    type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    area : [ '260px', 'auto' ], // 宽高
                    content : $('#pano0104NewHotspotCommandChoice2'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    end : function() {
                        // location.reload(true);
                        // searchData();
                        $('#pano0104NewHotspotCommandChoice2').hide();
                    }
                });

            }
            // 热点下没有链接信息或链接全景图的场合
            if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_PANORAMA) {
                _theLastedSceneHotspotId = hotspotId;
                layer.open({
                    id : 'LAY_pano0104OpenHotspotCommandChoiceLayer', // 设定一个id，防止重复弹出
                    title : '热点操作', // false不显示标题栏
                    type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    area : [ '260px', 'auto' ], // 宽高
                    content : $('#pano0104HotspotCommandChoice2'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    end : function() {
                        // location.reload(true);
                        // searchData();
                        $('#pano0104HotspotCommandChoice2').hide();
                    }
                });
            }
        }
    });
}

var _isSoundHotspot;
// 显示音频热点和普通热点下的信息
function showVtourNormalHotspotInfo(_hotspotName, _hotspotGallery, _externalLinkAddress, _videoPath) {
    var hotspotId = _hotspotName.split('v_').join('');
    selectedHotspotInfo['hotspotName'] = _hotspotName;
    selectedHotspotInfo['hotspotId'] = hotspotId;

    var _ajaxUrl = getMemberContextPath('pano0104/doCheckHostspotInfo');
    // 表单数据转换成JS对象
    var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
    ajaxSubmitFormData['panoramaId'] = currentTableRowInfo.panoramaId;
    ajaxSubmitFormData['selectedHotspotId'] = hotspotId;
    jQuery.post(_ajaxUrl, ajaxSubmitFormData, function(resultData) {
        if (CommonUtilJs.processAjaxSuccessAfter(resultData)) {
            var data = resultData.obj;
            // 热点下没有链接信息
            if (data == '') {
                layer.open({
                    id : 'LAY_pano0104OpenHotspotCommandChoiceLayer', // 设定一个id，防止重复弹出
                    title : '热点操作', // false不显示标题栏
                    type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    area : [ '260px', 'auto' ], // 宽高
                    content : $('#pano0104NewHotspotCommandChoice'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    end : function() {
                        // location.reload(true);
                        // searchData();
                        $('#pano0104NewHotspotCommandChoice').hide();
                    }
                });
            }
            // 热点下链接素材图的场合
            if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK
                    || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND
                    || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO
                    || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
                // 取得热点ID并弹出指令选择对话框
                _linkAddress = '';
                _isLinkHotspot = '';
                _isSoundHotspot = '';
                _isVideoHotspot = '';
                _isTextimg = '';

                // 为外部链接
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK) {
                    _linkAddress = _externalLinkAddress;
                    _isLinkHotspot = "yes";
                }
                // 是音频热点
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {
                    _isSoundHotspot = "yes";

                }
                // 如果是视频
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO) {
                    _isVideoHotspot = "yes";
                    _videoPrviewPath = _videoPath;
                }
                // 如果是图文
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
                    _isTextimg = "yes";
                }
                _hotspotGalleryName = _hotspotGallery;
                layer.open({
                    id : 'LAY_pano0104OpenHotspotCommandChoiceLayer', // 设定一个id，防止重复弹出
                    title : '热点操作', // false不显示标题栏
                    type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    area : [ '260px', 'auto' ], // 宽高
                    content : $('#pano0104HotspotCommandChoice'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    end : function() {
                        // location.reload(true);
                        // searchData();
                        $('#pano0104HotspotCommandChoice').hide();
                    }
                });
            }
        }
    });
}

// 显示多边形热点下的信息
function showVtourPolygonHotspotInfo(_hotspotName, _hotspotGallery, _externalLinkAddress, _videoPath) {
    var hotspotId = _hotspotName.split('v_').join('');
    selectedHotspotInfo['hotspotName'] = _hotspotName;
    selectedHotspotInfo['hotspotId'] = hotspotId;

    var _ajaxUrl = getMemberContextPath('pano0104/doCheckHostspotInfo');
    // 表单数据转换成JS对象
    var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
    ajaxSubmitFormData['panoramaId'] = currentTableRowInfo.panoramaId;
    ajaxSubmitFormData['selectedHotspotId'] = hotspotId;
    jQuery.post(_ajaxUrl, ajaxSubmitFormData, function(resultData) {
        if (CommonUtilJs.processAjaxSuccessAfter(resultData)) {
            var data = resultData.obj;
            // 热点下没有链接信息
            if (data == '') {
                setNormalHostspotLink(hotspotId);
            }
            // 热点下链接素材图的场合
            if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK
                    || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO
                    || data == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
                // 取得热点ID并弹出指令选择对话框
                _linkAddress = '';
                _isLinkHotspot = '';
                _isSoundHotspot = '';
                _isVideoHotspot = '';
                _isTextimg = '';
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK) {
                    _linkAddress = _externalLinkAddress;
                    _isLinkHotspot = "yes";
                }
                // 如果是视频
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO) {
                    _isVideoHotspot = "yes";
                    _videoPrviewPath = _videoPath;
                }
                // 如果是图文
                if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
                    _isTextimg = "yes";
                }
                _hotspotGalleryName = _hotspotGallery;
                layer.open({
                    id : 'LAY_pano0104OpenHotspotCommandChoiceLayer', // 设定一个id，防止重复弹出
                    title : '热点操作', // false不显示标题栏
                    type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    area : [ '260px', 'auto' ], // 宽高
                    content : $('#pano0104HotspotCommandChoice3'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    end : function() {
                        // location.reload(true);
                        // searchData();
                        $('#pano0104HotspotCommandChoice3').hide();
                    }
                });
            }
        }
    });
}
// 场景上触发的相关事件处理 *******************################### END
