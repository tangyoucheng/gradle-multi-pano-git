var ic0104_loadHotspotInfo_Interval = setInterval(function() {
    loadHotspotInfo();
}, 500);
var ic0104_loadHotspotInfo_Interval_processFlag = false;
var ic0104_showRecommendInfo_Interval;
var ic0104_showRecommendInfo_Interval_processFlag = false;
var ic0104_showMiniMap_Interval = setInterval(function() {
    showMiniMap()
}, 1000);
var ic0104_showMiniMap_Interval_processFlag = false;
var ic0104_loadMapHotspot_Interval;
var ic0104_loadMapHotspot_Interval_processFlag = false;
/**
 * 全景图热点显示
 */
$(document).ready(
    function() {

        // 进入画面时检查是不是直接复制url到新浏览器打开的窗口，如果是，则会缺少前画面传入的展览id，此时提示用户重新由一览进入展览
        if ($('#expositionId').val() == null || $('#expositionId').val() == '') {
            imuiShowWarningMessage('页面缺少必要数据，请在当前浏览器窗口退回至“360VR管理”画面重新点击对应展览的“场景编辑”进入。')
        }

        // 为通过导航热点迁移到全景图后的编辑场景操作做准备
        $("#ic0202panoramaId").val($('#panoramaId').val());

        // 新建全景图处理
        $('#button-vtour-map').click(function() {
            callAddVtourMapPage();
        });

        // 页面初期化保存当前场景应当播放的歌曲的path
        $('#currentSoundPath').val($('#backGroundSoundPath').val());

        // 设为首场景
        $('#button-startScene').click(function() {
            imuiConfirm('是否将此场景设为首场景?', '确认', function() {
                var _ajaxUrl = getMemberContextPath('pano0104/setFirstSence');
                var param = $("#ic0104Form").serialize();
                jQuery.post(_ajaxUrl, param, function(data) {
                    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                        if (data == "yes") {
                            eval("$('#ic0301SetFirstSenceFinish').imuiDialog('open')");
                        }
                    }
                });
            });
        });

        // 新建素材按钮
        $('#button-add-material').click(function() {
            // 呼出新建素材画面
            $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
                url : getMemberContextPath('pano0301'),
                title : '素材登记',
                modal : true,
                width : 900,
                height : 800,
                parameter : {
                    expositionId : $("#expositionId").val(),
                    expositionName : $("#expositionName").val(),
                    currentPanoramaId : $("#panoramaId").val(),
                    currentMusic : $('#backGroundSoundPath').val(),
                    openFromIc0104 : "yes"
                },
                close : function(event, ui) {
                    // 关闭画面时，停止0103音乐，恢复0104音乐播放
                    var ic0301krpano = document.getElementById("ic0301KrpanoSWFObject");
                    if (ic0301krpano != null && undefined != ic0301krpano.get) {
                        ic0301krpano.call('stopallsounds();');
                        ic0301krpano.call('closevideo();');
                    }
                    var ic0104krpano = document.getElementById("pano0104KrpanoNewObject");
                    if (ic0104krpano != null && undefined != ic0104krpano.get) {
                        ic0104krpano.call('playsound(bgsnd,' + $('#backGroundSoundPath').val() + ', 0,0); ');
                    }
                    $('#popupPage').remove();
                }
            });
            return false;
        });

        // 素材一览按钮
        $('#button-edit-material').click(function() {
            $('#ic0104-edit-material').submit();
        });
        // 设为首场景设定完成弹窗关闭
        $('#button-set-firstSence-confirm').click(function() {
            eval("$('#ic0301SetFirstSenceFinish').imuiDialog('close')");
        });

        // 场景一览处理
        $('#button-edit-pano').click(function() {
            $('#panoramaIdForIc0206').val($('#panoramaId').val());
            $('#ic0104-edit-pano').submit();
        });

        // 展览浮动层操作
        $('#button-edit-exposition-layer').click(function() {
            // 得到当前视角的中心点
            setLookAtForEdit();
            $('#ic0105positionAthForEdit').val(positionAth);
            $('#ic0105positionAtvForEdit').val(positionAtv);
            $("#ic0104-edit-exposition-layer").submit();
        });
        // 编辑图的基本信息处理
        $('#button-update-map').click(function() {
            callUpdateMapPage();
        });

        // 热点编辑处理
        $('#button-edit-hotspot').click(function() {
            // 得到当前视角的中心点
            setLookAtForEdit();
            $('#ic0203positionAthForEdit').val(positionAth);
            $('#ic0203positionAtvForEdit').val(positionAtv);
            $('#edit-pano-hotspot').submit();
        });

        // 多边形热点编辑处理
        $('#button-edit_polygon-hotspot').click(function() {
            // 得到当前视角的中心点
            setLookAtForEdit();
            $('#ic0108positionAthForEdit').val(positionAth);
            $('#ic0108positionAtvForEdit').val(positionAtv);
            $('#edit-polygon-hotspot').submit();
        });

        // 预览已编辑好的普通热点上的素材信息
        $('#button-preview-confirm').click(
            function() {
                eval("$('#ic0104HotspotCommandChoice').imuiDialog('close')");

                doPreviewHotspotInfo(_hotspotGalleryName, _isLinkHotspot, _linkAddress, _hotspotIdForSetOrEidtMaterial,
                    _isSoundHotspot, _isVideoHotspot, _videoPrviewPath);
            });
        // 预览已编辑好的多边形热点上的素材信息
        $('#button-previewPolygon-confirm').click(
            function() {
                eval("$('#ic0104HotspotCommandChoice3').imuiDialog('close')");
                doPreviewHotspotInfo(_hotspotGalleryName, _isLinkHotspot, _linkAddress, _hotspotIdForSetOrEidtMaterial,
                    _isSoundHotspot, _isVideoHotspot, _videoPrviewPath);
            });

        // 编辑已有热点的相关信息
        $('#button-edit-confirm').click(function() {
            eval("$('#ic0104HotspotCommandChoice').imuiDialog('close')");
            doEditHotspotInfo(_hotspotIdForSetOrEidtMaterial);
        });
        // 编辑已有多边形热点的信息
        $('#button-editPolygon-confirm').click(function() {
            eval("$('#ic0104HotspotCommandChoice3').imuiDialog('close')");
            doEditHotspotInfo(_hotspotIdForSetOrEidtMaterial);
        });
        // 编辑已有普通热点的大小
        $('#button-editSize-confirm').click(function() {
            eval("$('#ic0104HotspotCommandChoice').imuiDialog('close')");
            callIc0209();
        });
        // 编辑已有导航热点的大小
        $('#button-editSize-confirms').click(function() {
            eval("$('#ic0104HotspotCommandChoice2').imuiDialog('close')");
            callIc0209();
        });

        // 编辑已有导航热点的场景信息
        $('#button-to-editPanoramaInfo').click(function() {
            eval("$('#ic0104HotspotCommandChoice2').imuiDialog('close')");
            setGuideHostspotLink(_hotspotIdForSetOrEidtMaterial);
        });

        // 编辑暂无场景信息的导航热点内容
        $('#new-hotspot-button-to-add').click(function() {
            setGuideHostspotLink(_hotspotIdForSetOrEidtMaterial);
        });

        // 编辑暂无信息图的普通热点内容
        $('#new-hotspot-button-edit-confirm').click(function() {
            setNormalHostspotLink(_hotspotIdForSetOrEidtMaterial);
        });

        // 编辑暂无场景信息的导航热点大小
        $('#new-hotspot-button-editSize-confirms').click(function() {
            eval("$('#ic0104NewHotspotCommandChoice2').imuiDialog('close')");
            callIc0209();
        });

        // 编辑暂无信息图的普通热点大小
        $('#new-hotspot-button-editSize-confirm').click(function() {
            eval("$('#ic0104NewHotspotCommandChoice').imuiDialog('close')");
            callIc0209();
        });

        // 导航热点跳转
        $('#button-to-confirm').click(
            function() {
                // 角度设定
                setLookAtForEdit();
                // 保存上个导航热点的ID
                $('#theLastedSceneHotspotId').val(_theLastedSceneHotspotId);
                eval("$('#ic0104HotspotCommandChoice2').imuiDialog('close')");
                var krpano = document.getElementById("pano0104KrpanoNewObject");
                var _ajaxUrl = getMemberContextPath('pano0104/doShowHostspotInfo');
                var param = $("#ic0104Form").serialize();
                jQuery.post(_ajaxUrl, param, function(data) {
                    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                        if (data != '' && data != null) {
                            var recordData = JSON.parse(data);
                            if (recordData != '' && recordData != null) {
                                $('#lastHotspotIdTo0203').val(_theLastedSceneHotspotId);
                                $('#lastHotspotIdTo0206').val(_theLastedSceneHotspotId);
                                $('#lastHotspotIdTo0105').val(_theLastedSceneHotspotId);
                                // 保存当前场景应当显示的推荐路线点的ID
                                $('#currentRecommendHotspotId').val(recordData.currentRecommendHotspotId);
                                // 如果有场景视角点
                                if (recordData.panoramaHlookat != '' && recordData.panoramaHlookat != null) {
                                    var settings = "";
                                    settings = settings + '&view.hlookat=' + recordData.panoramaHlookat;
                                    settings = settings + '&view.vlookat=' + recordData.panoramaVlookat;
                                    // 视角点存在的场合
                                    krpano.call('loadpano(../' + recordData.panoramaPath + ',' + settings
                                            + ',MERGE, BLEND(2))');
                                } else {
                                    krpano.call('loadpano(../' + recordData.panoramaPath + ',null,MERGE, BLEND(2))');
                                }
                                krpano.set('hotspot[recommedInfoHotspot].visible', false);
                                krpano.set('layer[tooltip].visible', false);
                                panoramaIdForDelete = recordData.panoramaId;
                                $("#panoramaId").val(recordData.panoramaId);
                                $("#ic0302PanoramaId").val(recordData.panoramaId);
                                $('#selectedMiniMapHotspotId').val(recordData.selectedMiniMapHotspotId);
                                $("#ic0203PanoramaId").val(recordData.panoramaId);
                                $("#ic0105PanoramaId").val(recordData.panoramaId);
                                $("#ic0202panoramaId").val(recordData.panoramaId);
                                $("#ic0108PanoramaId").val(recordData.panoramaId);
                                $("#spotInfoListJson").val(JSON.stringify(recordData.spotInfoList));
                                $("#panoramaSelect").val(recordData.panoramaId);
                                $("#titleInfo").html(
                                    recordData.expositionName + "&nbsp;——&nbsp;" + recordData.panoramaName);
                                loadHotspotInfo();
                                // 设定地图热点雷达相关
                                if (recordData.selectedMiniMapHotspotId != undefined
                                        && recordData.selectedMiniMapHotspotId.length != 0) {
                                    krpano.set('layer[radar].parent', 'v_' + recordData.selectedMiniMapHotspotId);
                                    krpano.set('layer[radar].visible', true);
                                    krpano.set('layer[activespot].visible', true);
                                    if (recordData.radarHeading != undefined && recordData.radarHeading.length != 0) {
                                        krpano.set('layer[radar].heading', recordData.radarHeading);
                                        $('#radarHeading').val(recordData.radarHeading)

                                    }
                                } else {
                                    krpano.set('layer[radar].visible', false);
                                    krpano.set('layer[activespot].visible', false);
                                    $('#radarHeading').val('');
                                }

                                // 检查有无场景独立音乐
                                if (recordData.hadIndependSound == 'yes') {
                                    krpano.call('stopallsounds();');
                                }
                                // 播放场景音乐
                                krpano.call('playsound(bgsnd,' + recordData.backGroundSoundPath + ', 0,0); ');
                                $('#currentSoundPath').val(recordData.backGroundSoundPath);
                                // 更新地图热点zorder
                                ic0104_loadMapHotspot_Interval = setInterval(function() {
                                    loadMapHotspot()
                                }, 1000);

                            }
                        }

                    }
                });
            });

    });

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
// 热点大小
var hotspotScale;
// 自定义图文
var _isTextimg;

// 下拉列表里选中场景后跳转
function ic0104SelectRefresh() {
    $('#theLastedSceneHotspotId').val('');
    $('#currentRecommendHotspotId').val('');
    // 清空选择热点ID BUG#13
    $('#selectedHotspotId').val('');
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    var _ajaxUrl = getMemberContextPath('pano0104/doShowFromPanoSelected');
    $('#panoramaId').val($('#panoramaSelect').val());
    var param = $("#ic0104Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            if (data != '' && data != null) {
                var recordData = JSON.parse(data);
                if (recordData != '' && recordData != null) {
                    // 第一视角存在时,加载第一视角
                    var settings = "";
                    if (recordData.panoramaHlookat != "" && recordData.panoramaVlookat != "") {
                        settings = settings + '&view.hlookat=' + recordData.panoramaHlookat;
                        settings = settings + '&view.vlookat=' + recordData.panoramaVlookat;
                    }
                    krpano.call('loadpano(../' + recordData.panoramaPath + ',' + settings + ',null,MERGE, BLEND(2))');
                    krpano.set('hotspot[recommedInfoHotspot].visible', false);
                    krpano.set('layer[tooltip].visible', false);
                    panoramaIdForDelete = recordData.panoramaId;
                    $("#panoramaId").val(recordData.panoramaId);
                    $('#selectedMiniMapHotspotId').val(recordData.selectedMiniMapHotspotId);
                    $("#ic0302PanoramaId").val(recordData.panoramaId);
                    $("#ic0203PanoramaId").val(recordData.panoramaId);
                    $("#ic0105PanoramaId").val(recordData.panoramaId);
                    $("#ic0202panoramaId").val(recordData.panoramaId);
                    $("#ic0108PanoramaId").val(recordData.panoramaId);
                    $("#spotInfoListJson").val(JSON.stringify(recordData.spotInfoList));
                    $("#panoramaSelect").val(recordData.panoramaId);
                    $("#titleInfo").html(recordData.expositionName + "&nbsp;——&nbsp;" + recordData.panoramaName);
                    loadHotspotInfo();
                    if (recordData.selectedMiniMapHotspotId != undefined
                            && recordData.selectedMiniMapHotspotId.length != 0) {
                        krpano.set('layer[radar].parent', 'v_' + recordData.selectedMiniMapHotspotId);
                        krpano.set('layer[radar].visible', true);
                        krpano.set('layer[activespot].visible', true);
                        if (recordData.radarHeading != undefined && recordData.radarHeading.length != 0) {
                            krpano.set('layer[radar].heading', recordData.radarHeading);
                            $('#radarHeading').val(recordData.radarHeading)
                        }
                    } else {
                        krpano.set('layer[radar].visible', false);
                        krpano.set('layer[activespot].visible', false);
                        $('#radarHeading').val('');
                    }
                    // 检查有无场景独立音乐
                    if (recordData.hadIndependSound == 'yes') {
                        krpano.call('stopallsounds();');
                    }
                    krpano.call('stopsounds(bgsnd);');
                    krpano.call('playsound(bgsnd,' + recordData.backGroundSoundPath + ', 0,0);');
                    $('#currentSoundPath').val(recordData.backGroundSoundPath);
                    // 更新地图热点zorder
                    ic0104_loadMapHotspot_Interval = setInterval(function() {
                        loadMapHotspot()
                    }, 1000);
                }
            }
        }
    });
}

var positionAth = "";
var positionAtv = "";
// 跳转编辑画面前取得当前视角点
function setLookAtForEdit() {
    // 得到当前场景的中心点
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
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
}

// 呼出热点编辑画面
function callIc0209() {
    setLookAtForEdit();
    $('#ic0209hotspotId').val(_hotspotIdForSetOrEidtMaterial);
    $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
        url : getMemberContextPath('pano0209'),
        title : '编辑热点基本信息',
        modal : true,
        width : 820,
        height : 760,
        parameter : {
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            ic0209TheLastedSceneHotspotId : $('#theLastedSceneHotspotId').val(),
            ic0209RecommendHotspotId : $('#currentRecommendHotspotId').val(),
            expositionIdForIc0209 : $('#expositionId').val(),
            panoramaIdForIc0209 : $('#panoramaId').val(),
            ic0209hotspotId : $('#ic0209hotspotId').val()
        },
        close : function(event, ui) {
            $('#popupPage').remove();
        }
    });
    return false;
}

// 呼出新建首个全景图画面
function callAddVtourMapPage() {
    $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
        url : getMemberContextPath('pano0201'),
        title : '场景登记',
        modal : true,
        width : 850,
        height : 750,
        parameter : {
            expositionId : $("#expositionId").val(),
            expositionName : $("#expositionName").val()
        },
        close : function(event, ui) {
            $('#popupPage').remove();
        }
    });
    return false;
}

// 图上加载热点处理
function loadHotspotInfo() {
    if (ic0104_loadHotspotInfo_Interval_processFlag) {
        return false;
    }

    if ($('#spotInfoListJson').val() == undefined || $('#spotInfoListJson').val().length == 0) {
        return false;
    }
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    krpano.set('hotspot[recommedInfoHotspot].visible', false);

    var spotInfoList = JSON.parse($('#spotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }

    ic0104_loadHotspotInfo_Interval_processFlag = true;
    var pointList;
    // 读取已有的热点信息
    krpano.set('layer[radar].visible', false);
    $(spotInfoList)
            .each(
                function(index, recordData) {
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
                        krpano.set('hotspot[' + newspot + '].onclick',
                            'js(showVtourPolygonHotspotInfo(get(name),get(galleryname),'
                                    + recordData.externalLinkAddress + ',' + recordData.videoPath + '))');
                        krpano.set('hotspot[' + newspot + '].fillcolor', '0x489620');
                        krpano.set('hotspot[' + newspot + '].fillalpha', '0.4');
                        krpano.set('hotspot[' + newspot + '].bordercolor', '0x489620');
                        // 如果多边形有颜色和透明度
                        if (recordData.polygonFillcolor != null && recordData.polygonFillcolor != ''
                                && recordData.polygonFillalpha != null && recordData.polygonFillalpha != '') {
                            krpano.set('hotspot[' + newspot + '].fillalpha', 0);
                            krpano.set('hotspot[' + newspot + '].borderalpha', 0);

                            // 设置鼠标悬停事件
                            var hotspotHover = 'set(hotspot[' + newspot + '].fillcolor,' + recordData.polygonFillcolor
                                    + ');';
                            hotspotHover = hotspotHover + 'set(hotspot[' + newspot + '].fillalpha,'
                                    + recordData.polygonFillalpha + ');';
                            hotspotHover = hotspotHover + 'set(hotspot[' + newspot + '].borderwidth,0);';
                            krpano.set('hotspot[' + newspot + '].onhover', hotspotHover);

                            // 设置鼠标离开事件
                            var hotspotOut = 'set(hotspot[' + newspot + '].onout,';
                            hotspotOut = hotspotOut + 'callToolTipOutEvent();set(hotspot[' + newspot
                                    + '].fillalpha,0);';
                            hotspotOut = hotspotOut + 'set(hotspot[' + newspot + '].borderalpha,0););';
                            var hotspotOut = hotspotOut + 'set(hotspot[' + newspot + '].fillalpha,0);';
                            hotspotOut = hotspotOut + 'set(hotspot[' + newspot + '].borderalpha,0);';
                            krpano.set('hotspot[' + newspot + '].onloaded', hotspotOut);
                        }
                        // 循环每一个多边形的点
                        if (recordData.pointLists != null && recordData.pointLists.length != 0) {
                            pointList = recordData.pointLists;
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
                        krpano.set('hotspot[' + newspot + '].onclick',
                            'js(showVtourNormalHotspotInfo(get(name),get(galleryname),'
                                    + recordData.externalLinkAddress + ',' + recordData.videoPath + '))');
                    }
                    // 音频热点
                    if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                        krpano.set('hotspot[' + newspot + '].galleryname', 'v_' + recordData.hotspotId + '_gallery');
                        krpano.set('hotspot[' + newspot + '].onclick',
                            'js(showVtourNormalHotspotInfo(get(name),get(galleryname),'
                                    + recordData.externalLinkAddress + ',' + recordData.videoPath + '))');
                    }
                    // LOGO热点
                    if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_LOGO) {
                        krpano.set('hotspot[' + newspot + '].onclick', 'js(setLogoHotspotSize(get(name));)');
                    }
                    // 判断当前热点显示图片是否是gif并有对应的png图片
                    if (recordData.hasPngImage == 'true') {
                        var gifstyle = 'gifstyle' + recordData.hotspotId;
                        krpano.call('addstyle(' + gifstyle + ')');
                        krpano.set('style[' + gifstyle + '].name', gifstyle);
                        recordData.hotspotImagePath = recordData.hotspotImagePath.substring(0,
                            recordData.hotspotImagePath.lastIndexOf("."))
                                + ".png";
                        krpano.set('style[' + gifstyle + '].url', recordData.hotspotImagePath);
                        krpano.set('style[' + gifstyle + '].crop', '0|0|' + recordData.gifWidth + '|'
                                + recordData.gifHeight);
                        krpano.set('style[' + gifstyle + '].framewidth', recordData.gifWidth);
                        krpano.set('style[' + gifstyle + '].frameheight', recordData.gifHeight);
                        krpano.set('style[' + gifstyle + '].frame', '0');
                        krpano.set('style[' + gifstyle + '].lastframe', recordData.gifFrame);
                        // 如果在gif热点中，是被选为推荐线路点的导航热点，则在其onloaded方法中追加showrecommend方法
                        if (_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE
                                && recordData.recommendInfo != null && recordData.recommendInfo != '') {
                            krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + recordData.gifDelayTime
                                    + ');showrecommend(3,recommedInfoHotspot)');
                            krpano.set('hotspot[' + newspot + '].onloaded', '');
                        } else {
                            krpano.set('style[' + gifstyle + '].onloaded', 'hotspot_animate(' + recordData.gifDelayTime
                                    + ');');
                        }
                        krpano.call('hotspot[' + newspot + '].loadstyle(' + gifstyle + ');');
                    } else {
                        // 显示数据库中的热点图片
                        if (recordData.hotspotImagePath != null && recordData.hotspotImagePath != '') {
                            krpano.set('hotspot[' + newspot + '].url', recordData.hotspotImagePath);
                        }
                    }
                    if (recordData.hotspotId == $('#currentRecommendHotspotId').val()) {
                        // 显示推荐路线hotspot
                        ic0104_showRecommendInfo_Interval = setInterval(function() {
                            showRecommendInfo();
                        }, 500);
                    }
                    // 初期显示时保持热点图片的原始尺寸的设定
                    krpano.set('hotspot[' + newspot + '].zoom', 'true');
                });
    showMiniMap();
    clearInterval(ic0104_loadHotspotInfo_Interval);
    ic0104_loadHotspotInfo_Interval_processFlag = false;

}
// 显示当前场景应当出现的推荐路线点
function showRecommendInfo() {

    if (ic0104_showRecommendInfo_Interval_processFlag) {
        return false;
    }

    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    ic0104_showRecommendInfo_Interval_processFlag = true;
    if ($('#currentRecommendHotspotId').val() != null && $('#currentRecommendHotspotId').val() != ''
            && krpano.get("hotspot[v_" + $('#currentRecommendHotspotId').val() + "].name") != '') {
        // 推荐路线信息为图片时
        krpano.set('hotspot[recommedInfoHotspot].visible', true);
        // 推荐路线信息为文字时
        // krpano.set('plugin[recommedInfoPlugin].parent',"hotspot[v_" +
        // $('#currentRecommendHotspotId').val() + "]");
        // krpano.set('plugin[recommedInfoPlugin].html',$('#expositionRecommendInfo').val());
    }

    clearInterval(ic0104_showRecommendInfo_Interval);
    ic0104_showRecommendInfo_Interval_processFlag = false;
}

// logo热点click事件
function setLogoHotspotSize(_hotspotName) {
    _hotspotIdForSetOrEidtMaterial = _hotspotName.split('v_').join('');
    callIc0209();
}

// 显示导航热点下的信息
function showVtourGuideHotspotInfo(_hotspotName) {

    var hotspotId = _hotspotName.split('v_').join('');
    var _ajaxUrl = getMemberContextPath('pano0104/doCheckHostspotInfo');
    $("#selectedHotspotId").val(hotspotId);
    var param = $("#ic0104Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            // 数据被删除的场合
            if (data == 'delete') {
                imuiAlert('当前热点已被其他用户删除！');
            }
            // 热点下没有链接信息
            if (data == '') {
                _hotspotIdForSetOrEidtMaterial = hotspotId;
                _theLastedSceneHotspotId = hotspotId;
                eval("$('#ic0104NewHotspotCommandChoice2').imuiDialog('open')");

            }
            // 热点下没有链接信息或链接全景图的场合
            if (data == PanoConstants.VAL_HOTSPOT_URL_TYPE_PANORAMA) {
                _hotspotIdForSetOrEidtMaterial = hotspotId;
                _theLastedSceneHotspotId = hotspotId;
                eval("$('#ic0104HotspotCommandChoice2').imuiDialog('open')");
            }
        }
    });
}

var _hotspotIdForSetOrEidtMaterial;

var _isSoundHotspot;
// 显示音频热点和普通热点下的信息
function showVtourNormalHotspotInfo(_hotspotName, _hotspotGallery, _externalLinkAddress, _videoPath) {
    var hotspotId = _hotspotName.split('v_').join('');
    var _ajaxUrl = getMemberContextPath('pano0104/doCheckHostspotInfo');
    $("#selectedHotspotId").val(hotspotId);
    var param = $("#ic0104Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            // 热点下没有链接信息
            if (data == '') {
                _hotspotIdForSetOrEidtMaterial = hotspotId;
                eval("$('#ic0104NewHotspotCommandChoice').imuiDialog('open')");
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
                _hotspotIdForSetOrEidtMaterial = hotspotId;
                eval("$('#ic0104HotspotCommandChoice').imuiDialog('open')");
            }
        }
    });
}

// 显示多边形热点下的信息
function showVtourPolygonHotspotInfo(_hotspotName, _hotspotGallery, _externalLinkAddress, _videoPath) {
    var hotspotId = _hotspotName.split('v_').join('');
    var _ajaxUrl = getMemberContextPath('pano0104/doCheckHostspotInfo');
    $("#selectedHotspotId").val(hotspotId);
    var param = $("#ic0104Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
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
                _hotspotIdForSetOrEidtMaterial = hotspotId;
                eval("$('#ic0104HotspotCommandChoice3').imuiDialog('open')");
            }
        }
    });
}

// 预览热点上的素材信息
function doPreviewHotspotInfo(_hotspotGallery, _isLinkHotspot, _linkAddress, _hotspotIdForSetOrEidtMaterial,
        _isSoundHotspot, _isVideoHotspot, _videoPrviewPath) {
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    // 如果热点下是外部链接，打开弹窗预览网页
    if (_isLinkHotspot == 'yes' && _linkAddress != '') {
        krpano.call("openurl('" + _linkAddress + "',_blank);");
    } else if (_isSoundHotspot == 'yes') {
        // 音频热点
        callic0310(_hotspotIdForSetOrEidtMaterial);
    } else if (_isVideoHotspot == 'yes') {
        // 热点下是视频
        krpano.call('closevideo();');
        // 停止ic0104背景音乐播放
        krpano.call('stopallsounds();');
        // 设置视频关闭时恢复ic0104背景音乐播放
        krpano.set('layer[bgVideoClose].onclick', 'closevideo();js(playSoundBeforeCloseVideo());');
        // 播放视频
        var videoUrl = getMemberContextPath("" + _videoPrviewPath);
        krpano.call('openvideo(' + videoUrl + ');');
    } else if (_isTextimg == 'yes') {
        // 热点下是图文，打开图文
        krpano.call('js(doOpenTextImgPage())');
    } else {
        // 热点下是素材信息图，打开gallery
        krpano.call('show_gallery(' + _hotspotGallery + ')');
    }

}

// 音频热点预览
function callic0310(hotspotId) {
    // 检查是否有0104的音乐在播放，如果有，则先停止0104的音乐播放,此方法针对于从0104点进场景音乐编辑后的情况
    var ic0104Krpano = document.getElementById("pano0104KrpanoNewObject");
    if (ic0104Krpano != null && undefined != ic0104Krpano.get) {
        ic0104Krpano.call('stopallsounds();');
    }
    setLookAtForEdit();
    $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
        url : getMemberContextPath('pano0310'),
        title : '预览音频热点效果',
        modal : true,
        width : 690,
        height : 600,
        parameter : {
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            ic0310panoramaId : $("#panoramaId").val(),
            ic0310selectedHotspotId : hotspotId
        },
        close : function(event, ui) {
            ic0104StopSound();
            $('#popupPage').remove();
        }
    });
    return false
}

// 设置关闭试听画面，登录成功和关闭检索画面时停止音乐的操作
function ic0104StopSound() {
    // 检索当前场景是否有背景音乐，有救播放背景音乐
    var ic0104krpano = document.getElementById("pano0104KrpanoNewObject");
    if (ic0104krpano != null && undefined != ic0104krpano.get) {
        ic0104krpano.call('playsound(bgsnd,' + $('#currentSoundPath').val() + ', 0,0); ');
    }

}

// 关闭视频后播放当前背景音乐
function playSoundBeforeCloseVideo() {
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    krpano.call('playsound(bgsnd,' + $('#currentSoundPath').val() + ', 0,0);');
}

// 编辑已有热点的素材相关信息
function doEditHotspotInfo(_hotspotId) {
    // 传往编辑画面的视角点坐标
    setLookAtForEdit();
    $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
        url : getMemberContextPath('pano0205'),
        title : '编辑已有热点的素材相关信息',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            selectedHotspotId : _hotspotId,
            ic0205TheLastedSceneHotspotId : $('#theLastedSceneHotspotId').val(),
            ic0205RecommendHotspotId : $('#currentRecommendHotspotId').val(),
            expositionId : $("#expositionId").val(),
            currentPanoramaId : $("#panoramaId").val(),
            expositionName : $("#expositionName").val()
        },
        close : function(event, ui) {
            $('#popupPage').remove();
        }
    });
    return false
}

// 呼出为导航热点添加全景图的画面
function setGuideHostspotLink(_selectedHotspotId) {
    setLookAtForEdit();
    $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
        url : getMemberContextPath('pano0204'),
        title : '导航热点链接信息设定',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            ic0204LastHotspotId : $('#theLastedSceneHotspotId').val(),
            ic0204CurrentHotspotId : $('#currentRecommendHotspotId').val(),
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            selectedHotspotId : _selectedHotspotId,
            expositionId : $("#expositionId").val(),
            currentPanoramaId : $("#panoramaId").val(),
            expositionName : $("#expositionName").val()
        },
        close : function(event, ui) {
            $('#popupPage').remove();
        }
    });
    return false
}

// 呼出为热点增加素材图的弹窗页面
function setNormalHostspotLink(_selectedHotspotId) {
    setLookAtForEdit();
    $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
        url : getMemberContextPath('pano0205'),
        title : '热点素材链接信息设定',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            positionAthForEdit : positionAth,
            positionAtvForEdit : positionAtv,
            selectedHotspotId : _selectedHotspotId,
            expositionId : $("#expositionId").val(),
            currentPanoramaId : $("#panoramaId").val(),
            expositionName : $("#expositionName").val()
        },
        close : function(event, ui) {
            $('#popupPage').remove();
        }
    });
    return false
}

// 显示地图
function showMiniMap() {

    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get || ic0104_showMiniMap_Interval_processFlag) {
        return false;
    }
    ic0104_showMiniMap_Interval_processFlag = true;
    // 查看当前展览有无地图，有即显示
    var check = $('#miniMapCheck').val();
    if (check == 'true') {
        var newlayer = 'layer_nimi_map_container';
        krpano.call('addlayer(' + newlayer + ')');
        krpano.set('layer[' + newlayer + '].url', $('#expositionMapPath').val());
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
    ic0104_loadMapHotspot_Interval = setInterval(function() {
        loadMapHotspot()
    }, 1000);
    clearInterval(ic0104_showMiniMap_Interval);
    ic0104_showMiniMap_Interval_processFlag = false;
}
// 地图上热点雷达角度全局变量
var thisRadarHeading;
// 显示小地图上的热点
function loadMapHotspot() {
    if (ic0104_loadMapHotspot_Interval_processFlag) {
        return false;
    }

    if ($('#miniMapSpotInfoListJson').val() == undefined || $('#miniMapSpotInfoListJson').val().length == 0) {
        return false;
    }
    var spotInfoList = JSON.parse($('#miniMapSpotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }

    ic0104_loadMapHotspot_Interval_processFlag = true;

    // 读取已有的热点信息
    $(spotInfoList).each(
        function(index, recordData) {
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
    if ($('#selectedMiniMapHotspotId').val() != undefined && $('#selectedMiniMapHotspotId').val().length != 0) {
        // 被选中的地图热点zorder设为2，未被选中设为3，保证雷达扇形不覆盖在其他热点上
        $(spotInfoList).each(function(index, recordData) {
            var newlayer = 'v_' + recordData.expositionMapHotspotId;
            krpano.set('layer[' + newlayer + '].zorder', '3');
        });
        krpano.set('layer[v_' + $('#selectedMiniMapHotspotId').val() + '].zorder', '2');
        krpano.set('layer[radar].heading', $('#radarHeading').val());
        krpano.set('layer[radar].parent', 'v_' + $('#selectedMiniMapHotspotId').val());
        krpano.set('layer[radar].visible', true);
        krpano.set('layer[activespot].visible', true);
    }

    clearInterval(ic0104_loadMapHotspot_Interval);
    ic0104_loadMapHotspot_Interval_processFlag = false;
}
// 删除当前场景时的当前场景PanoramaId
var panoramaIdForDelete;
// 显示导航图上热点下的信息
function showMiniMapHotspotInfo(_hotspotName) {
    var krpano = document.getElementById("pano0104KrpanoNewObject");
    $('#theLastedSceneHotspotId').val('');
    $('#currentRecommendHotspotId').val('');
    // 先置空雷达
    var hotspotId = _hotspotName.split('v_').join('');
    var _ajaxUrl = getMemberContextPath('pano0104/doShowMiniMapHotspotInfo');
    $("#selectedMiniMapHotspotId").val(hotspotId);
    var param = $("#ic0104Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            if (data != '' && data != null) {
                var recordData = JSON.parse(data);
                // 热点下链接了全景图的场合
                if (recordData != '' && recordData != null) {
                    $('#theLastedSceneHotspotId').val('');
                    krpano.call('loadpano(../' + recordData.panoramaPath + ',null,MERGE, BLEND(2))');
                    krpano.set('hotspot[recommedInfoHotspot].visible', false);
                    krpano.set('layer[tooltip].visible', false);
                    panoramaIdForDelete = recordData.panoramaId;
                    $("#panoramaId").val(recordData.panoramaId);
                    $("#ic0302PanoramaId").val(recordData.panoramaId);
                    $("#ic0203PanoramaId").val(recordData.panoramaId);
                    $("#ic0105PanoramaId").val(recordData.panoramaId);
                    $("#ic0202panoramaId").val(recordData.panoramaId);
                    $("#ic0108PanoramaId").val(recordData.panoramaId);
                    $("#spotInfoListJson").val(JSON.stringify(recordData.spotInfoList));
                    $("#panoramaSelect").val(recordData.panoramaId);
                    $("#titleInfo").html(recordData.expositionName + "&nbsp;——&nbsp;" + recordData.panoramaName);
                    loadHotspotInfo();
                    if (recordData.radarHeading != null && recordData.radarHeading.length != 0) {
                        $('#radarHeading').val(recordData.radarHeading);
                    } else {
                        $('#radarHeading').val("0");
                    }
                    // 设定地图热点和雷达角度
                    ic0104_loadMapHotspot_Interval = setInterval(function() {
                        loadMapHotspot()
                    }, 1000);

                    // 检查有无场景独立音乐
                    if (recordData.hadIndependSound == 'yes') {
                        krpano.call('stopallsounds();');
                    }
                    krpano.call('stopsounds(bgsnd);');
                    krpano.call('playsound(bgsnd,' + recordData.backGroundSoundPath + ', 0,0); ');
                    $('#currentSoundPath').val(recordData.backGroundSoundPath);
                }
            } else {
                imuiAlert("该热点暂无场景信息");
                krpano.set('layer[radar].visible', false);
                krpano.set('layer[activespot].visible', false);
                $('#selectedMiniMapHotspotId').val('')
            }
        }
    });
}
// 图文类型 动态生成JS数据
function getJsPath() {
    return getMemberContextPath('pano0104/doGetTextImgData?expositionId=') + $("#expositionId").val();
}