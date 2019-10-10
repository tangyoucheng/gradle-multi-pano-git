//=================================
//热点大小,浮动层大小，场景工具条编辑 
//=================================
// 自定义pluginName和hotspotName
//var _pluginName;
var _spotName;
// 定义大小变量
var hotspotScale;

$(document).ready(function() {

    doMakePano0209krpano();

    $("select[name='hotspotScale']").on('changed.bs.select', function(e) {
        // 刷新下拉框
        selectRefresh();
    });

    if ($('#commandTypeFromPano0105').val() == '' || $('#commandTypeFromPano0105').val() == null) {
        $('#expoGoSceneInfoText').attr("disabled", "true");
    }

    // 保存处理
    $('#button-pano0209-confirm').click(function() {
        set0209LookAtForEdit();
        if ($('#commandTypeFromPano0105').val() == 'buttons') {
            // 调用Pano0105保存工具条方法
            reloadButtonsBar_pano0209();

            // 询问框
            var currentConfirmIndex = window.top.layer.confirm('正在当前工具条状态，是否继续？', {
                icon : 3,
                title : '提示信息',
                btn : [ '确认', '取消' ]
            // 按钮
            }, function() {// 确认操作
                // 关闭确认对话框
                window.top.layer.close(currentConfirmIndex);

                // 做成FormData对象
                var ajaxSubmitFormData = form2js($("#pano0209Form")[0]);
                ajaxSubmitFormData['selectedButtons'] = _selectedButtons;
                ajaxSubmitFormData['expositionId'] = ajaxSubmitFormData['expositionIdForPano0209'];

                $.ajax({
                    type : 'post',
                    traditional : true,
                    data : ajaxSubmitFormData,
                    dataType : 'json',
                    url : getMemberContextPath('pano0105/doSaveButtonsBar'),
                    success : function(result) {
                        if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                            // 自页面关闭
                            var index = parent.layer.getFrameIndex(window.name);
                            window.top.layer.close(index);
                        }
                    }
                })
            }, function() {// 取消操作
            });

        } else {

            // 询问框
            var currentConfirmIndex = window.top.layer.confirm('正在保存当前数据，是否继续？', {
                icon : 3,
                title : '提示信息',
                btn : [ '确认', '取消' ]
            // 按钮
            }, function() {// 确认操作
                // 关闭确认对话框
                window.top.layer.close(currentConfirmIndex);

                if ($('#pano0203OperateFlag').val() == 'size') {
                    // 选中的值传给前页面
                    var returnObject = {};
                    returnObject['hotspotScale'] = hotspotScale;
                    window.parent.frames[$('#returnTargetIframe').val()].setPano0209ReturnObject(returnObject);

                    // 关闭确认对话框
                    window.top.layer.close(currentConfirmIndex);
                    // 自页面关闭
                    var index = parent.layer.getFrameIndex(window.name);
                    window.top.layer.close(index);
                    return;
                }

                var returnTargetIframe = window.parent.frames[$('#returnTargetIframe').val()];

                // 做成FormData对象
                var ajaxSubmitFormData = form2js($("#pano0209Form")[0]);
                ajaxSubmitFormData['hotspotScale'] = hotspotScale;
                // var hotspotInfoData =
                // $.param(hotspotInfoList.serializeObject("hotspotInfoList"))
                // ajaxSubmitFormData = $.extend({}, hotspotInfoData,
                // ajaxSubmitFormData);

                $.ajax({
                    type : 'post',
                    traditional : true,
                    data : ajaxSubmitFormData,
                    dataType : 'json',
                    url : getMemberContextPath('pano0209/doSaveHotspotScale'),
                    success : function(result) {
                        if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                            var data = result.obj;
                            if (data == 'recommend_info') {// 推荐路线信息情况
                                // $("#back-to-ic0105").submit();
                            }

                            if (data == 'true_hotspot') {// 推荐热点
                                // $("#back-to-ic0104").submit();

                                // 选中的值传给前页面
                                var returnObject = {};
                                returnObject['hotspotScale'] = hotspotScale;
                                returnTargetIframe.setPano0209ReturnObject(returnObject);
                            }

                            if (data == 'true_guid_hotspot') {// 推荐路径点
                                // $('#recommendHotspotIdBackTo0104').val($('#ic0209hotspotId').val());
                                // $("#back-to-ic0104").submit();

                                // 选中的值传给前页面
                                var returnObject = {};
                                returnObject['hotspotScale'] = hotspotScale;
                                returnTargetIframe.setPano0209ReturnObject(returnObject);
                            }

                            if (data == 'false_guid_hotspot') {// 不是推荐路径点
                                // $('#recommendHotspotIdBackTo0104').val('');
                                // $("#back-to-ic0104").submit();

                                // 选中的值传给前页面
                                var returnObject = {};
                                returnObject['hotspotScale'] = hotspotScale;
                                returnTargetIframe.setPano0209ReturnObject(returnObject);
                            }

                            if (data == 'true_layer') {// 浮动信息层
                                // $("#back-to-ic0105").submit();
                            }

                            // 自页面关闭
                            var index = parent.layer.getFrameIndex(window.name);
                            window.top.layer.close(index);
                        }
                    }
                })
            }, function() {// 取消操作
            });
        }
    });

    hotspotScale = $("select[name='hotspotScale']").val();
    // 如果热点不是导航热点，隐藏推荐路径点设置的功能
    if (PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE != $('#pano0209HotspotType').val()) {
        $('#recommendTr').hide();
        $('#tooltipTr').hide();
        if ($('#commandTypeFromPano0105').val() != 'commonInfo') {
            $('#editExpoGoSceneInfoTr').hide();
        }
    } else {
        $('#recommendInfoTh').hide();
        $('#recommendInfoTd').hide();
    }

    // 如果热点已是推荐线路点，设置其在页面加载后为选中状态
    if ($('#recommendInfo').val() != null && $('#recommendInfo').val() != '') {
        $('#recommendFlag').attr("checked", "true");
        // _recommendInfo = $('#expoRecommendInfo').val();
        _recommendInfo = "推荐路线";
        load_pano0209();
    } else {
        _recommendInfo = '';
    }
    // 推荐路线勾选框点击事件
    $('#recommendFlag').click(function() {
        var _krpano = document.getElementById("pano0209PanoId");
        if ($('#recommendFlag')[0].checked) {
            $('#recommendInfoTh').show();
            $('#recommendInfoTd').show();
            // _recommendInfo = $('#expoRecommendInfo').val();
            _recommendInfo = "推荐路线";
            load_pano0209();
        } else {
            $('#recommendInfoTh').hide();
            $('#recommendInfoTd').hide();
            _recommendInfo = "";
            load_pano0209();
        }
    });

    // 区分浮动层大小调整，工具条自定义和热点大小调整的画面
    // editPage_pano0209();
    

});

function doMakePano0209krpano() {
    var pano0209Krpano = document.getElementById("pano0209videoPreviewKrpano");
    if (pano0209Krpano == null || undefined == pano0209Krpano.get) {
        var xmlPath = $('#pano0209panoramaPath').val() + "?temp=" + Math.random();
        embedpano({
            swf : PanoConstants.VAL_EMBEDPANO_SWF,
            id : "pano0209PanoId",
            xml : xmlPath,
            target : "pano0209Pano",
            wmode : "transparent",
            html5 : "prefer",
            bgcolor : "#f5f5f5",
            passQueryParameters : true
        });
    }
}

// 自定义推荐信息
var _recommendInfo;

// 区分浮动层大小调整，工具条自定义和热点大小调整的画面
function doPano0209KrpanoOnloadcomplete() {
    // 调整浮动层大小
    if ($('#commandTypeFromPano0105').val() == 'flowInfo') {
        loadLayer();
        $('#buttons-list').hide();
        $('#buttonsTitleDiv').hide();
        $('#editRecommendTitleDiv').hide();
        // $('#editRecommendInfoTr').hide();

        // 显示检索条件区域
        $('#divCondition').show();
        return false;
    }

    // 自定义工具条
    if ($('#commandTypeFromPano0105').val() == 'buttons') {
        pano0209ShowCurrentButtonsBar()
        $('#sizeTr').hide();
        $('#recommendTr').hide();
        $('#sizeTitleDiv').hide();
        $('#editRecommendTitleDiv').hide();
        // $('#editRecommendInfoTr').hide();
        $('#tooltipTr').hide();
        $('#pano0209Pano').css("height", "100px");

        // 显示检索条件区域
        $('#divCondition').show();
        return false;
    }

    // 编辑推荐路线信息
    if ($('#commandTypeFromPano0105').val() == 'commonInfo') {
        pano0209ShowCurrentButtonsBar()
        $('#sizeTr').hide();
        $('#buttons-list').hide();
        $('#recommendTr').hide();
        $('#buttonsTitleDiv').hide();
        $('#sizeTitleDiv').hide();
        $('#tooltipTr').hide();
        $('#pano0209Pano').css("height", "280px");
        loadRecommendInfo_pano0209();

        // 显示检索条件区域
        $('#divCondition').show();
        return false;
    }

    // 预览0203音频热点效果
    if ($('#pano0203OperateFlag').val() == 'preview') {
        $('#buttons-list').hide();
        $('#buttonsTitleDiv').hide();
        // $('#editRecommendInfoTr').hide();
        $('#editRecommendTitleDiv').hide();
        $('#sizeTitleDiv').hide();
        $('#sizeTr').hide();
        $("#button-pano0209-confirm").hide();
        loadMusicHotspotFromPano0203();

        // 显示检索条件区域
        $('#divCondition').show();
        return false;
    }

    // 热点大小
    // 分0203和0104操作两种情况
    if ($('#pano0203OperateFlag').val() == 'size') {
        loadHotspotFromPano0203();
        $('#buttons-list').hide();
        $('#buttonsTitleDiv').hide();
        // $('#editRecommendInfoTr').hide();
        $('#editRecommendTitleDiv').hide();
        $('#sizeTitleDiv').show();
        $('#sizeTr').show();
        $("#button-pano0209-confirm").show();
    } else {
        load_pano0209();
        $('#buttons-list').hide();
        $('#buttonsTitleDiv').hide();
        // $('#editRecommendInfoTr').hide();
        $('#editRecommendTitleDiv').hide();
        $('#sizeTitleDiv').show();
        $('#sizeTr').show();
        $("#button-pano0209-confirm").show();
    }

    // 显示检索条件区域
    $('#divCondition').show();
}

// 显示现有状态下的工具条样式
function pano0209ShowCurrentButtonsBar() {

    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get) {
        return false;
    }

    // 2.重构并顺序排列选中的工具条按钮的x坐标
    var strs = [];
    var selectedButtons = $('#selectedButtons').val();
    if (selectedButtons.indexOf(",") != -1) {
        strs = selectedButtons.split(',');
    }
    // 坐标变量
    for (var i = 0; i < strs.length; i++) {
        if (strs[i].length == 0) {
            return false;
        }
        _krpano.set('layer[' + strs[i] + '].x', i * 40);
        _krpano.set('layer[' + strs[i] + '].visible', true);
        // 让对应单选框保持选中
        $('#' + strs[i]).attr("checked", true);
    }

    _krpano.set('layer[defaultskin_buttons].width', strs.length * 40);
}

// 展览工具条选中的按钮名称组合
var _selectedButtons = '';
// 设定工具条的显示
function reloadButtonsBar_pano0209() {
    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get) {
        return false;
    }

    _selectedButtons = '';
    // 坐标变量
    var _positionX = 0;

    // 还款一览
    var buttonsListTable = $('#buttons-list');
    jQuery.each(buttonsListTable.bootstrapTable('getData'), function(i, item) {
        var _chkBoxId = item.buttonName;
        if (item.checkbox === true) {
            _krpano.set('layer[' + _chkBoxId + '].x', _positionX);
            _krpano.set('layer[' + _chkBoxId + '].visible', true);
            _positionX += 40;
            // 拼接字符串
            _selectedButtons = _selectedButtons + _chkBoxId + ",";
        } else {
            _krpano.set('layer[' + _chkBoxId + '].visible', false);
        }
    });
    
    _krpano.set('layer[defaultskin_buttons].width', _positionX);

}

// 加载热点
function load_pano0209() {
    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get || $('#hotspotImageInfoJson').val() == '') {
        return false;
    }
    var newspot = 'v_' + $('#pano0209hotspotId').val();
    _krpano.call("stopdelayedcall('" + newspot + "');");
    _krpano.call('removehotspot(' + newspot + ')')
    _krpano.call('addhotspot(' + newspot + ')');
    // 初期显示时保持热点图片的原始尺寸的设定
    _krpano.set('hotspot[' + newspot + '].zoom', true);
    _krpano.set('hotspot[' + newspot + '].handcursor', false);
    var ath = $('#hotspotAth').val();
    var atv = $('#hotspotAtv').val();
    _krpano.set('hotspot[' + newspot + '].ath', ath);
    _krpano.set('hotspot[' + newspot + '].atv', atv);
    _krpano.set('hotspot[' + newspot + '].zorder', "2");
    if (hotspotScale != "" && hotspotScale != null) {
        _krpano.set('hotspot[' + newspot + '].scale', hotspotScale);
    } else {
        _krpano.set('hotspot[' + newspot + '].scale', "1.0");
    }

    var hotspotImageInfoObject = JSON.parse($('#hotspotImageInfoJson').val());
    if (hotspotImageInfoObject == null) {
        return false;
    }

    var hotspotStyle = null;
    var hotspotStyleOnloaded = null;
    var hotspotImagePath = hotspotImageInfoObject.hotspotImagePath;
    if (hotspotImageInfoObject.hasPngImage == 'true') {
        var gifstyle = 'gifstyle' + newspot.split('v_').join('');
        _krpano.call('addstyle(' + gifstyle + ')');
        _krpano.set('style[' + gifstyle + '].name', gifstyle);

        var _hotspotImageUrlForGif = hotspotImagePath.substring(0, hotspotImagePath.lastIndexOf(".")) + ".png";
        _krpano.set('style[' + gifstyle + '].url', _hotspotImageUrlForGif);
        _krpano.set('style[' + gifstyle + '].crop', '0|0|' + hotspotImageInfoObject.gifWidth + '|'
                + hotspotImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle + '].framewidth', hotspotImageInfoObject.gifWidth);
        _krpano.set('style[' + gifstyle + '].frameheight', hotspotImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle + '].frame', '0');
        _krpano.set('style[' + gifstyle + '].lastframe', hotspotImageInfoObject.gifFrame);

        hotspotStyleOnloaded = 'hotspot_animate(' + hotspotImageInfoObject.gifDelayTime + ');'
        hotspotStyle = gifstyle;

    } else {
        _krpano.set('hotspot[' + newspot + '].url', hotspotImagePath);
    }

    // 判断该热点是否是已被设为推荐路线的导航点
    if (PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE == $('#pano0209HotspotType').val()) {
        // 为该热点增加plugin
        if (_recommendInfo != null && _recommendInfo != '') {
            // 推荐路线信息为图片时
            _krpano.set('hotspot[recommedInfoHotspot].visible', true);
            _krpano.set('hotspot[recommedInfoHotspot].ath', ath);
            _krpano.set('hotspot[recommedInfoHotspot].atv', atv);

            // 推荐路线信息为文字时
            // if(hotspotStyleOnloaded == null){
            // hotspotStyleOnloaded =
            // "showrecommend(3,'recommedInfoPlugin','"+newspot+"');";
            // } else {
            // hotspotStyleOnloaded = hotspotStyleOnloaded +
            // "showrecommend(3,'recommedInfoPlugin','"+newspot+"');";
            // }
            // _krpano.set('style[recommedInfoPluginStyle].onloaded',hotspotStyleOnloaded);
            //           
            // if(hotspotStyle == null){
            // hotspotStyle = 'recommedInfoPluginStyle';
            // } else {
            // hotspotStyle = hotspotStyle + '|'+'recommedInfoPluginStyle';
            // }

        } else {
            _krpano.set('hotspot[recommedInfoHotspot].visible', "false");
            // _krpano.call("stopdelayedcall('"+newspot+"');");
        }

    }

    if (hotspotStyle != null) {
        var hotspotStyleOnloadeds = hotspotStyleOnloaded.split(';');
        if (hotspotStyleOnloadeds.length < 3) {
            _krpano.set('style[' + hotspotStyle + '].onloaded', hotspotStyleOnloaded);
        }
        _krpano.call('hotspot[' + newspot + '].loadstyle(' + hotspotStyle + ');');

    }

}

// 由热点编辑画面呼出的热点大小调整，读取热点
function loadHotspotFromPano0203() {
    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get || $('#hotspotImageInfoJson').val() == '') {
        return false;
    }

    var newspot = $('#pano0203HotspotName').val();
    _krpano.call('removehotspot(' + newspot + ')')
    _krpano.call('addhotspot(' + newspot + ')');
    // 初期显示时保持热点图片的原始尺寸的设定
    _krpano.set('hotspot[' + newspot + '].zoom', true);
    _krpano.set('hotspot[' + newspot + '].handcursor', false);
    var ath = $('#pano0203HotspotAth').val();
    var atv = $('#pano0203HotspotAtv').val();
    _krpano.set('hotspot[' + newspot + '].ath', ath);
    _krpano.set('hotspot[' + newspot + '].atv', atv);
    _krpano.set('hotspot[' + newspot + '].zorder', "2");
    if (hotspotScale != "" && hotspotScale != null) {
        _krpano.set('hotspot[' + newspot + '].scale', hotspotScale);
    } else {
        _krpano.set('hotspot[' + newspot + '].scale', "1.0");
    }

    var hotspotImageInfoObject = JSON.parse($('#hotspotImageInfoJson').val());
    if (hotspotImageInfoObject == null) {
        return false;
    }

    var hotspotImagePath = hotspotImageInfoObject.hotspotImagePath;
    if (hotspotImageInfoObject.hasPngImage == 'true') {
        var gifstyle = 'gifstyle' + newspot.split('v_').join('');
        _krpano.call('addstyle(' + gifstyle + ')');
        _krpano.set('style[' + gifstyle + '].name', gifstyle);
        var _hotspotImageUrlForGif = hotspotImagePath.substring(0, hotspotImagePath.lastIndexOf(".")) + ".png";
        _krpano.set('style[' + gifstyle + '].url', _hotspotImageUrlForGif);
        _krpano.set('style[' + gifstyle + '].crop', '0|0|' + hotspotImageInfoObject.gifWidth + '|'
                + hotspotImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle + '].framewidth', hotspotImageInfoObject.gifWidth);
        _krpano.set('style[' + gifstyle + '].frameheight', hotspotImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle + '].frame', '0');
        _krpano.set('style[' + gifstyle + '].lastframe', hotspotImageInfoObject.gifFrame);
        _krpano
                .set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + hotspotImageInfoObject.gifDelayTime
                        + ');');
        _krpano.call('hotspot[' + newspot + '].loadstyle(' + gifstyle + ');');

    } else {
        _krpano.set('hotspot[' + newspot + '].url', hotspotImagePath);
    }

}

// 由热点编辑画面呼出的音频热点效果，读取热点
function loadMusicHotspotFromPano0203() {
    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get || $('#firstImageInfoJson').val() == ''
            || $('#secondImageInfoJson').val() == '') {
        return false;
    }

    var newspot_1 = $('#pano0203HotspotName').val() + "_1";
    var newspot_2 = $('#pano0203HotspotName').val() + "_2";
    _krpano.call('addhotspot(' + newspot_1 + ')');
    _krpano.call('addhotspot(' + newspot_2 + ')');
    // 初期显示时保持热点图片的原始尺寸的设定
    var ath = $('#pano0203HotspotAth').val();
    var atv = $('#pano0203HotspotAtv').val();
    _krpano.set('hotspot[' + newspot_1 + '].zoom', true);
    _krpano.set('hotspot[' + newspot_1 + '].ath', ath);
    _krpano.set('hotspot[' + newspot_1 + '].atv', atv);
    _krpano.set('hotspot[' + newspot_1 + '].zorder', "2");
    _krpano.set('hotspot[' + newspot_2 + '].zoom', true);
    _krpano.set('hotspot[' + newspot_2 + '].ath', ath);
    _krpano.set('hotspot[' + newspot_2 + '].atv', atv);
    _krpano.set('hotspot[' + newspot_2 + '].zorder', "2");
    _krpano.set('hotspot[' + newspot_2 + '].visible', "false");

    if (hotspotScale != "" && hotspotScale != null) {
        _krpano.set('hotspot[' + newspot_1 + '].scale', hotspotScale);
        _krpano.set('hotspot[' + newspot_2 + '].scale', hotspotScale);
    } else {
        _krpano.set('hotspot[' + newspot_1 + '].scale', "1.0");
        _krpano.set('hotspot[' + newspot_2 + '].scale', "1.0");
    }

    var firstImageInfoObject = JSON.parse($('#firstImageInfoJson').val());
    if (firstImageInfoObject != null && firstImageInfoObject.hasPngImage == 'true') {
        var gifstyle = 'gifstyle' + $('#pano0209hotspotId').val().split('v_').join('') + "_1";
        _krpano.call('addstyle(' + gifstyle + ')');
        _krpano.set('style[' + gifstyle + '].name', gifstyle);
        var firstImageUrlPath = firstImageInfoObject.hotspotImagePath;
        var _hotspotImageUrlForGif = firstImageUrlPath.substring(0, firstImageUrlPath.lastIndexOf(".")) + ".png";
        _krpano.set('style[' + gifstyle + '].url', _hotspotImageUrlForGif);
        _krpano.set('style[' + gifstyle + '].crop', '0|0|' + firstImageInfoObject.gifWidth + '|'
                + firstImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle + '].framewidth', firstImageInfoObject.gifWidth);
        _krpano.set('style[' + gifstyle + '].frameheight', firstImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle + '].frame', '0');
        _krpano.set('style[' + gifstyle + '].lastframe', firstImageInfoObject.gifFrame);
        _krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + firstImageInfoObject.gifDelayTime + ');');
        _krpano.call('hotspot[' + newspot_1 + '].loadstyle(' + gifstyle + ');');

    } else {
        _krpano.set('hotspot[' + newspot_1 + '].url', firstImageInfoObject.hotspotImagePath);
    }

    var secondImageInfoObject = JSON.parse($('#secondImageInfoJson').val());
    if (secondImageInfoObject != null && secondImageInfoObject.hasPngImage == 'true') {
        var gifstyle_2 = 'gifstyle' + $('#pano0209hotspotId').val().split('v_').join('') + "_2";
        _krpano.call('addstyle(' + gifstyle_2 + ')');
        _krpano.set('style[' + gifstyle_2 + '].name', gifstyle_2);
        var secondImageUrlPath = secondImageInfoObject.hotspotImagePath;
        var _hotspotImageUrlForGif = secondImageUrlPath.substring(0, secondImageUrlPath.lastIndexOf(".")) + ".png";
        _krpano.set('style[' + gifstyle_2 + '].url', _hotspotImageUrlForGif);
        _krpano.set('style[' + gifstyle_2 + '].crop', '0|0|' + secondImageInfoObject.gifWidth + '|'
                + secondImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle_2 + '].framewidth', secondImageInfoObject.gifWidth);
        _krpano.set('style[' + gifstyle_2 + '].frameheight', secondImageInfoObject.gifHeight);
        _krpano.set('style[' + gifstyle_2 + '].frame', '0');
        _krpano.set('style[' + gifstyle_2 + '].lastframe', secondImageInfoObject.gifFrame);
        _krpano.set('style[' + gifstyle_2 + '].onloaded', 'hotspot_animate(' + secondImageInfoObject.gifDelayTime
                + ');');
        _krpano.call('hotspot[' + newspot_2 + '].loadstyle(' + gifstyle_2 + ');');
    } else {
        _krpano.set('hotspot[' + newspot_2 + '].url', secondImageInfoObject.hotspotImagePath);
    }

    // 音频热点的url切换
    switchFlg = true;
    _krpano.set('hotspot[' + newspot_1 + '].onclick', 'js(changeUrl_pano0209(' + newspot_1 + ',' + newspot_2 + '))');
    _krpano.set('hotspot[' + newspot_2 + '].onclick', 'js(changeUrl_pano0209(' + newspot_1 + ',' + newspot_2 + '))');

}

var switchFlg;
// 更换音频热点的图片
function changeUrl_pano0209(newspot_1, newspot_2) {
    var _krpano = document.getElementById("pano0209PanoId");
    if (switchFlg) {
        _krpano.set('hotspot[' + newspot_1 + '].visible', 'false');
        _krpano.set('hotspot[' + newspot_2 + '].visible', 'true');
        switchFlg = false;
    } else {
        _krpano.set('hotspot[' + newspot_1 + '].visible', 'true');
        _krpano.set('hotspot[' + newspot_2 + '].visible', 'false');
        switchFlg = true;
    }
}

// 在编辑推荐路线点信息时预览效果
function loadRecommendInfo_pano0209() {
    set0209LookAtForEdit();
    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get) {
        return false;
    }
    _krpano.set('layer[defaultskin_buttons].visible', "false");
    // 推荐路线信息为文字时
    // _krpano.set('plugin[recommedInfoPlugin].html',$('#recommendInfoText').val());
    // _krpano.set('plugin[recommedInfoPlugin].visible',"true");
    // 推荐路线信息为图片时
    _krpano.set('hotspot[recommedInfoHotspot].visible', true);
    _krpano.set('hotspot[recommedInfoHotspot].ath', $("#0209BackAth").val());
    _krpano.set('hotspot[recommedInfoHotspot].atv', $("#0209BackAtv").val());

}

// 加载浮动信息层
function loadLayer() {
    var _krpano = document.getElementById("pano0209PanoId");
    if (_krpano == null || undefined == _krpano.get) {
        return false;
    }

    var newlayer = 'flowInfoLayer';
    _krpano.call('addlayer(' + newlayer + ')');
    _krpano.set('layer[' + newlayer + '].border', false);
    _krpano.set('layer[' + newlayer + '].align', 'righttop');
    _krpano.set('layer[' + newlayer + '].x', $('#pano0209layerX').val());
    _krpano.set('layer[' + newlayer + '].y', $('#pano0209layerY').val());
    _krpano.set('layer[' + newlayer + '].scale', hotspotScale);
    // 如果是图片浮动信息
    if ($('#pano0209flowFileType').val() == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE) {
        _krpano.set('layer[' + newlayer + '].url', $('#pano0209flowFilePath').val());
    } else {
        // 如果是文字浮动信息
        _krpano.set('layer[' + newlayer + '].url', '%SWFPATH%/plugins/textfield.swf');
        _krpano.set('layer[' + newlayer + '].html', $('#pano0209flowFileInfo').val());
        _krpano.set('layer[' + newlayer + '].css', 'text-align:center; font-family:Arial; font-weight:bold; font-size:'
                + parseFloat(hotspotScale) * 36 + 'px;');
    }
    _krpano.set('layer[' + newlayer + '].backgroundalpha', "0");
    _krpano.set('layer[' + newlayer + '].height', "auto");
    _krpano.set('layer[' + newlayer + '].width', "auto");

}

// 刷新热点大小
function selectRefresh() {
    // 如果是浮动信息层

    hotspotScale = $("select[name='hotspotScale']").val();
    if ($('#commandTypeFromPano0105').val() == 'flowInfo') {
        loadLayer();
    } else if ($('#pano0203OperateFlag').val() == 'size') {
        // 普通热点与音频热点
        loadHotspotFromPano0203();
    } else {
        load_pano0209();
    }
}

// 预览提示信息
// function recommendPreview(){
// var _krpano = document.getElementById("pano0209PanoId");
// _krpano.set('plugin['+_pluginName+'].html',$('#recommendInfotext').val());
// _krpano.call('hotspot['+_spotName+'].onloaded');
// }

// 跳转画面前取得当前视角点
function set0209LookAtForEdit() {
    $("#0209BackAth").val('');
    $("#0209BackAtv").val('');
    var positionAth = "";
    var positionAtv = "";
    // 得到当前场景的中心点
    var _krpano = document.getElementById("pano0209PanoId");
    var _positionAth = _krpano.get('view.hlookat').toString();
    var _positionAtv = _krpano.get('view.vlookat').toString();
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
    $("#0209BackAth").val(positionAth);
    $("#0209BackAtv").val(positionAtv);
}
