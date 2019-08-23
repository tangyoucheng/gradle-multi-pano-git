/**
 * 全景图热点显示
 */
$(document).ready(
    function() {

        // ********** 已有信息图的普通热点编辑 START
        // 预览已编辑好的普通热点上的素材信息
        $('#button-preview-confirm').click(
            function() {
                layer.close(layer.index); // 关闭操作菜单
                doPreviewHotspotInfo(_hotspotGalleryName, _isLinkHotspot, _linkAddress,
                    selectedHotspotInfo['hotspotId'], _isSoundHotspot, _isVideoHotspot, _videoPrviewPath);
            });

        // 编辑已有热点的相关信息
        $('#button-edit-confirm').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            doEditHotspotInfo(selectedHotspotInfo['hotspotId']);
        });
        // 编辑已有普通热点的大小
        $('#button-editSize-confirm').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            callPano0209();
        });
        // ********** 已有信息图的普通热点编辑 END

        // ********** 暂无信息图的普通热点编辑 START
        // 编辑暂无信息图的普通热点内容
        $('#new-hotspot-button-edit-confirm').click(function() {
            setNormalHostspotLink(selectedHotspotInfo['hotspotId']);
        });

        // 编辑暂无信息图的普通热点大小
        $('#new-hotspot-button-editSize-confirm').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            callPano0209();
        });
        // ********** 暂无信息图的普通热点编辑 END

        // ********** 已有场景的导航热点编辑 START
        // 导航热点跳转
        $('#button-to-confirm').click(
            function() {
                layer.close(layer.index); // 关闭操作菜单
                setLookAtForEdit(); // 取得当前视角点

                var krpano = document.getElementById("pano0104KrpanoNewObject");
                var _ajaxUrl = getMemberContextPath('pano0104/doShowHostspotInfo');

                var param = form2js($("#pano0104FormAjaxSubmit")[0]);
                param['selectedHotspotId'] = selectedHotspotInfo['hotspotId'];
                // 保存上个导航热点的ID
                param['theLastedSceneHotspotId'] = _theLastedSceneHotspotId;

                jQuery.post(_ajaxUrl, param, function(data) {
                    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                        if (data != '' && data != null) {
                            pano0104CurrentForm = data.obj;
                            var recordData = data.obj;
                            if (recordData != '' && recordData != null) {
                                // 保存当前场景应当显示的推荐路线点的ID
                                $('#currentRecommendHotspotId').val(recordData.currentRecommendHotspotId);
                                // 如果有场景视角点
                                if (recordData.panoramaHlookat != '' && recordData.panoramaHlookat != null) {
                                    var settings = "";
                                    settings = settings + '&view.hlookat=' + recordData.panoramaHlookat;
                                    settings = settings + '&view.vlookat=' + recordData.panoramaVlookat;
                                    // 视角点存在的场合
                                    krpano.call('loadpano(../' + recordData.panoramaPath + "?temp=" + Math.random()
                                            + ',' + settings + ',MERGE, BLEND(2))');
                                } else {
                                    krpano.call('loadpano(../' + recordData.panoramaPath + "?temp=" + Math.random()
                                            + ',null,MERGE, BLEND(2))');
                                }
                                krpano.set('hotspot[recommedInfoHotspot].visible', false);
                                krpano.set('layer[tooltip].visible', false);
                                // panoramaIdForDelete = recordData.panoramaId;
                                // $('#selectedMiniMapHotspotId').val(recordData.selectedMiniMapHotspotId);
                                // loadHotspotInfo();
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
                                if (recordData.backGroundSoundPath != null && recordData.backGroundSoundPath != "") {
                                    krpano.call('playsound(bgsnd,' + recordData.backGroundSoundPath + ', 0,0); ');
                                }
                                // $('#currentSoundPath').val(recordData.backGroundSoundPath);
                                // 更新地图热点zorder
                                // pano0104_loadMapHotspot_Interval =
                                // setInterval(function() {
                                // loadMapHotspot()
                                // }, 1000);

                            }
                        }

                    }
                });
            });

        // 编辑已有导航热点的场景信息
        $('#button-to-editPanoramaInfo').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            setGuideHostspotLink(selectedHotspotInfo['hotspotId']);
        });
        // 编辑已有导航热点的大小
        $('#button-editSize-confirms').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            callPano0209();
        });
        // ********** 已有场景的导航热点编辑 END

        // ********** 暂无信息图的普通热点编辑 START
        // 编辑暂无场景信息的导航热点内容
        $('#new-hotspot-button-to-add').click(function() {
            setGuideHostspotLink(selectedHotspotInfo['hotspotId']);
        });
        // 编辑暂无场景信息的导航热点大小
        $('#new-hotspot-button-editSize-confirms').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            callPano0209();
        });
        // ********** 暂无信息图的普通热点编辑 END

        // ********** 多边形热点编辑 START
        // 预览已编辑好的多边形热点上的素材信息
        $('#button-previewPolygon-confirm').click(
            function() {
                layer.close(layer.index); // 关闭操作菜单
                doPreviewHotspotInfo(_hotspotGalleryName, _isLinkHotspot, _linkAddress,
                    selectedHotspotInfo['hotspotId'], _isSoundHotspot, _isVideoHotspot, _videoPrviewPath);
            });
        // 编辑已有多边形热点的信息
        $('#button-editPolygon-confirm').click(function() {
            layer.close(layer.index); // 关闭操作菜单
            doEditHotspotInfo(selectedHotspotInfo['hotspotId']);
        });
        // ********** 多边形热点编辑 END

    });

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
    currentPanoramaInfo['positionAth'] = positionAth;
    currentPanoramaInfo['positionAtv'] = positionAtv;
}

// 呼出热点编辑画面
function callPano0209() {
    setLookAtForEdit(); // 取得当前视角点

    var targetUrl = 'pano0209/';
    var urlParam = form2js($("#pano0104FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['expositionIdForPano0209'] = currentTableRowInfo['expositionId'];

    urlParam['panoramaIdForPano0209'] = currentPanoramaInfo['panoramaId'];
    urlParam['positionAthForEdit'] = currentPanoramaInfo['positionAth'];
    urlParam['positionAtvForEdit'] = currentPanoramaInfo['positionAtv'];

    urlParam['pano0209hotspotId'] = selectedHotspotInfo['hotspotId'];
    urlParam['pano0209TheLastedSceneHotspotId'] = _theLastedSceneHotspotId;
    urlParam['pano0209RecommendHotspotId'] = _currentRecommendHotspotId;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '编辑热点基本信息',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            // searchData();
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
        // 停止pano0104背景音乐播放
        krpano.call('stopallsounds();');
        // 设置视频关闭时恢复pano0104背景音乐播放
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

// 编辑已有热点的素材相关信息
function doEditHotspotInfo(_hotspotId) {

    // setLookAtForEdit(); // 取得当前视角点

    var targetUrl = 'pano0205/';
    var urlParam = form2js($("#pano0104FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['currentPanoramaId'] = currentPanoramaInfo['panoramaId'];

    urlParam['positionAthForEdit'] = currentPanoramaInfo['positionAth'];
    urlParam['positionAtvForEdit'] = currentPanoramaInfo['positionAtv'];

    urlParam['selectedHotspotId'] = selectedHotspotInfo['hotspotId'];

    urlParam['pano0209TheLastedSceneHotspotId'] = _theLastedSceneHotspotId;
    urlParam['pano0209RecommendHotspotId'] = _currentRecommendHotspotId;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '编辑已有热点的素材相关信息',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            searchData();
        }
    });

}
// 呼出为热点增加素材图的弹窗页面
function setNormalHostspotLink(_selectedHotspotId) {

    // setLookAtForEdit(); // 取得当前视角点

    var targetUrl = 'pano0205/';
    var urlParam = form2js($("#pano0104FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['currentPanoramaId'] = currentPanoramaInfo['panoramaId'];

    urlParam['positionAthForEdit'] = currentPanoramaInfo['positionAth'];
    urlParam['positionAtvForEdit'] = currentPanoramaInfo['positionAtv'];

    urlParam['selectedHotspotId'] = selectedHotspotInfo['hotspotId'];

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '导航热点链接信息设定',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            searchData();
        }
    });
}

// 呼出为导航热点添加全景图的画面
function setGuideHostspotLink(_selectedHotspotId) {

    setLookAtForEdit(); // 取得当前视角点

    var targetUrl = 'pano0204/';
    var urlParam = form2js($("#pano0104FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['currentPanoramaId'] = currentPanoramaInfo['panoramaId'];

    urlParam['positionAthForEdit'] = currentPanoramaInfo['positionAth'];
    urlParam['positionAtvForEdit'] = currentPanoramaInfo['positionAtv'];

    urlParam['selectedHotspotId'] = selectedHotspotInfo['hotspotId'];

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '导航热点链接信息设定',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            searchData();
        }
    });
}

// 图文信息预览
function doOpenTextImgPage() {
    var url = 'static/pano/pano/common/template/html/index.html?id=' + selectedHotspotInfo['hotspotId'];
    $.magnificPopup.open({
        items : {
            src : url
        },
        type : 'iframe',
        alignTop : false
    });
}

// 图文类型 动态生成JS数据
function getJsPath() {
    return getMemberContextPath('pano0104/doGetTextImgData?expositionId=') + currentTableRowInfo['expositionId'];
}
