/**
 * 全景图地图操作
 */
// 自定义热点雷达初始角度的全局变量
var offsetNum;
// 自定义视角点
var positionAth = "";
var positionAtv = "";
$(document).ready(function() {
    // 自定义工具条设定完成弹窗关闭
    $('#button-set-tool-confirm').click(function() {
        eval("$('#pano0105SetToolFinish').imuiDialog('close')");
        $('#pano0105Form').submit();
    });

    // radioBox的点击事件
    // $("input[name='commandType']").click(function() {
    // choiceCommand();
    // });
    // 功能选择下拉框
    $("#commandType").change(function() {
        choiceCommand();
    });
    choiceCommand();

    // 自定义工具条按钮点击操作
    $('#edit-buttons').click(function() {
        openPano0209();
    });

    // 展览目录编辑
    $('#edit-director').click(function() {
        openPano0308();
    });

    // 展览效果编辑
    $('#edit-effect').click(function() {
        openPano0109();
    });

    // 点击导航图编辑按钮
    $('#edit-mini-map-button').click(function() {

        setLookAtForEdit(); // 取得当前视角点

        var targetUrl = 'pano0106/';
        var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
        urlParam['returnTargetIframe'] = window.name;

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
                location.reload(true);
            }
        });
    });

    // 导航图热点编辑
    $('#edit-map-hotspot-button').click(function() {
        setLookAtForEdit(); // 取得当前视角点

        var targetUrl = 'pano0107/';
        var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
        urlParam['returnTargetIframe'] = window.name;

        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '导航图热点编辑',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            success : function(layero, index) {
                layer.close(layer.index); // 关闭操作菜单
            },
            end : function() {
                location.reload(true);
            }
        });

        $('#edit-map').submit();

    });
    // 雷达角度左旋
    $('#left').click(function() {
        var krpano = document.getElementById("pano0105KrpanoNewObject");
        offsetNum -= 10;
        krpano.set('layer[radar].heading', offsetNum + '');
    });
    // 雷达角度右旋
    $('#right').click(function() {
        var krpano = document.getElementById("pano0105KrpanoNewObject");
        offsetNum += 10;
        krpano.set('layer[radar].heading', offsetNum + '');
    });
    // 雷达角度设定

    $('#set_radar').click(function() {
        doRadarByTime()
    });

    // 浮动信息设定
    $("#set_flow_info").click(function() {
        // 打开浮动信息的选择提示框
        eval("$('#pano0105FlowInfoCommandChoice').imuiDialog('open')");
    });

    // 推荐路线信息设定
    $('#set_common_info').click(function() {
        setCommonInfo();
    });

    // 在浮动信息的选择提示框中，点击选择图片浮动信息和文字浮动信息分别的操作
    $('#button-image-confirm').click(function() {
        editFlowInfo_Image();
    });
    $('#button-text-confirm').click(function() {
        editFlowInfo_Text();
    });

    // 保存浮动效果位置的操作
    $('#save_flow_info').click(function() {
        saveFlowInfoOffset();
    });

    // 浮动效果层点击后选择菜单上的按钮点击的事件
    $('#button-delete-layer').click(function() {
        deleteLayer();
    });
    $('#button-edit-layer-size').click(function() {
        // 编辑前检查该层是否被保存，没有保存则提示保存
        var _ajaxUrl = getMemberContextPath('pano0105/doCheckFlowInfoLayer');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {

                layer.close(layer.index); // 关闭弹出菜单

                var data = result.obj
                if (data == 'true') {
                    callPano0209();
                } else {
                    var showMessage = '请先保存浮动效果！';
                    window.top.showErrorMessage(showMessage, false, 3);
                }
            }
        });

    });

    // 初始化场景信息
    doInitPanoInfo()
});

// 初始化场景信息
function doInitPanoInfo() {
    window.top.krpanoMaskLoading();
    // 引擎配置文件路径
    var xmlSessionFilePath = $('#panoramaPath').val() + "?temp=" + Math.random();
    // 初始化参数
    var panoSettings = {};
    var panoramaHlookat = $('#pano0105positionAthForEdit').val();
    var panoramaVlookat = $('#pano0105positionAtvForEdit').val();
    if (panoramaHlookat != '' && panoramaHlookat != null && panoramaVlookat != '' && panoramaVlookat != null) {
        panoSettings['view.hlookat'] = panoramaVlookat;
        panoSettings['view.vlookat'] = panoramaVlookat;
    }
    // 启动引擎
    removepano("pano0105KrpanoNewObject");
    embedpano({
        swf : PanoConstants.VAL_EMBEDPANO_SWF,
        id : 'pano0105KrpanoNewObject',
        xml : xmlSessionFilePath,
        target : "pano0105Pano",
        wmode : "transparent",
        html5 : "prefer",
        passQueryParameters : true,
        vars : panoSettings,
        onready : window.top.removekrpanoMask
    });

    // 图上加载热点处理
    // loadPano0104HotspotInfo(result.hotspotInfoList);

}

function doRadarByTime() {
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if (!krpano.get('layer[radar].visible')) {
        return false;
    }

    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('正登录当前当前热点的雷达角度，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() {
        window.top.layer.close(currentConfirmIndex);

        // 确认操作
        // var ajaxFormData = {};
        // ajaxFormData = $.extend({}, ajaxFormData,
        // form2js($("#pano0201FormAdd")[0]));
        $('#radarHeading').val(offsetNum + '');
        var ajaxFormData = form2js($("#pano0105Form")[0]);

        $.ajax({
            url : getMemberContextPath('pano0105/doSaveRadarHeading'),
            type : "post",
            traditional : true,
            dataType : "json",
            data : ajaxFormData,
            success : function(result) {
                if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                    location.reload(true);
                }
            }
        // ,
        // error : function(result) {
        // window.top.layuiRemoveLoading();
        // window.top.layer.alert(result.status);
        // }
        });
    }, function() {
        // 取消操作
    });

    // imuiConfirm('是否保存当前热点的雷达角度', '确认', function() {
    // var _ajaxUrl = getMemberContextPath('pano0105/doSaveRadarHeading');
    // $('#radarHeading').val(offsetNum + '');
    // var param = $("#pano0105Form").serialize();
    // jQuery.post(_ajaxUrl, param, function(data) {
    // if (CommonUtilJs.processAjaxSuccessAfter(data)) {
    // if (data == 'false') {
    // imuiAlert("操作失败");
    // } else {
    // // 重新读取最新的地图热点信息
    // var recordData = JSON.parse(data);
    // eval("$('#pano0105Finish').imuiDialog('open')");
    // $('#miniMapSpotInfoListJson').val(recordData.miniMapSpotInfoListJson);
    // loadMapHotspot();
    // }
    // }
    // });
    // });
}

// 指令radiobox的选择操作
function choiceCommand() {
    var radioboxSelected = $("#commandType").val();
    ;
    hideButtons();
    // 选中导航图操作
    if (radioboxSelected == PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_MINMAP) {
        showMiniMapButtons();
    }
    // 选中浮动信息操作
    if (radioboxSelected == PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_FLOW_INFO) {
        showFlowInfoButtons();
    }
    // 选中工具条操作
    if (radioboxSelected == PanoConstants.VAL_EXPOSITION_COOMMANDTYPE_TOOL) {
        showButtonBarButtons();
    }
}

// 隐藏画面上除radiobox外的全部功能按钮
function hideButtons() {
    $('#edit-mini-map-button').hide();
    $('#edit-map-hotspot-button').hide();
    $('#left').hide();
    $('#right').hide();
    $('#set_radar').hide();
    $('#set_common_info').hide();

    // $('#set_flow_info').hide();
    $('#button-image-confirm').hide();
    $('#button-text-confirm').hide();

    $('#save_flow_info').hide();
    $('#edit-buttons').hide();
    $('#edit-director').hide();
    $('#edit-effect').hide();
}

// 显示导航图操作相关的功能按钮
function showMiniMapButtons() {
    $('#edit-mini-map-button').show();
    // 如果有地图，才显示更多操作
    if ($('#miniMapCheck').val() == 'true') {
        $('#edit-map-hotspot-button').show();
        $('#left').show();
        $('#right').show();
        $('#set_radar').show();
    }
}

// 显示设定浮动效果的相关功能按钮
function showFlowInfoButtons() {
    $('#set_common_info').show();

    // $('#set_flow_info').show();
    $('#button-image-confirm').show();
    $('#button-text-confirm').show();

    $('#save_flow_info').show();
}

// 显示设定场景功能条的相关功能按钮
function showButtonBarButtons() {
    if ($('#panoramaId').val() != null && $('#panoramaId').val() != "") {
        $('#edit-buttons').show();
        $('#edit-director').show();
    }

    $('#edit-effect').show();
}

// 前往设置推荐路线信息的画面
function setCommonInfo() {
    setLookAtForEdit();

    var targetUrl = 'pano0209/';
    var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['commandTypeFromPano0105'] = 'commonInfo';
    urlParam['positionAthForEdit'] = positionAth;
    urlParam['positionAtvForEdit'] = positionAtv;
    urlParam['expositionIdForPano0209'] = urlParam['expositionId'];
    urlParam['panoramaIdForPano0209'] = urlParam['panoramaId'];
    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '设置公共信息',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            // location.reload(true);
        }
    });
}
// 前往自定义工具条按钮的画面
function openPano0209() {
    setLookAtForEdit();

    var targetUrl = 'pano0209/';
    var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['commandTypeFromPano0105'] = 'buttons';
    urlParam['positionAthForEdit'] = positionAth;
    urlParam['positionAtvForEdit'] = positionAtv;
    urlParam['expositionIdForPano0209'] = urlParam['expositionId'];
    urlParam['panoramaIdForPano0209'] = urlParam['panoramaId'];
    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '自定义工具条',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            location.reload(true);
        }
    });
}

// 前往展览目录编辑画面
function openPano0308() {
    setLookAtForEdit(); // 取得当前视角点
    var targetUrl = 'pano0308/';
    var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '展览目录编辑',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            location.reload(true);
        }
    });
}

// 前往展览效果编辑画面
function openPano0109() {
    $("#editButtonsOrFlowInfo").imuiPageDialog({
        url : getMemberContextPath('pano0109'),
        title : '展览效果编辑',
        modal : true,
        width : 900,
        height : 305,
        position : [ "center", 180 ],
        parameter : {
            expositionId : $('#expositionId').val(),
            panoramaId : $('#panoramaId').val()
        },
        close : function(event, ui) {
            // 关闭画面时，停止0109音乐
            var pano0109newVideokrpano1 = document.getElementById("newPano1Id");
            if (pano0109newVideokrpano1 != null && undefined != pano0109newVideokrpano1.get) {
                pano0109newVideokrpano1.call('videoplayer_close();');
            }
            var pano0109newVideokrpano2 = document.getElementById("newPanoId");
            if (pano0109newVideokrpano2 != null && undefined != pano0109newVideokrpano2.get) {
                pano0109newVideokrpano2.call('videoplayer_close();');
            }
            var pano0109oldVideokrpano = document.getElementById("oldPanoId");
            if (pano0109oldVideokrpano != null && undefined != pano0109oldVideokrpano.get) {
                pano0109oldVideokrpano.call('videoplayer_close();');
            }

        }
    });
    return false;
}

// 呼出选择图片浮动效果素材的画面
function editFlowInfo_Image() {

    var targetUrl = 'pano0208/';
    var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['hotspotOldType'] = PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '选择浮动效果',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            // location.reload(true);
        }
    });
}

// 呼出选择文字浮动效果素材的画面
function editFlowInfo_Text() {

    var targetUrl = 'pano0208/';
    var urlParam = form2js($("#pano0105FormAjaxSubmit")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['hotspotOldType'] = PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '选择浮动效果',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            // location.reload(true);
        }
    });
}

var layerName;
// 浮动层点击事件
function pano0105LayerClick(_layerName) {
    layerName = _layerName;
    layer.open({
        id : 'LAY_pano0105OpenMenuLayer', // 设定一个id，防止重复弹出
        title : '热点操作', // false不显示标题栏
        type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        area : [ '260px', 'auto' ], // 宽高
        content : $('#pano0105FlowInfoEidtChoice'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        end : function() {
            // location.reload(true);
            // searchData();
            $('#pano0105FlowInfoEidtChoice').hide();
        }
    });

}
// 浮动层拖拽事件
function pano0105LayerDraglayer() {
    $("input[name='commandType'][value=2]").attr("checked", 'checked');
    choiceCommand();
}

// 删除浮动层的操作
function deleteLayer() {

    layer.close(layer.index); // 关闭弹出菜单

    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('正在删除当前浮动信息层，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() {
        window.top.layer.close(currentConfirmIndex);

        // 做成FormData对象
        var ajaxSubmitFormData = form2js($("#pano0105FormAjaxSubmit")[0]);

        $.ajax({
            type : 'post',
            traditional : true,
            data : ajaxSubmitFormData,
            dataType : 'json',
            url : getMemberContextPath('pano0105/doDeleteFlowInfo'),
            success : function(result) {
                if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                    var krpano = document.getElementById("pano0105KrpanoNewObject");
                    krpano.call('removelayer(' + layerName + ')');
                }
            }
        })

    }, function() {
        // 取消操作
    });
}

// 保存浮动信息层的位置
function saveFlowInfoOffset() {
    var krpano = document.getElementById("pano0105KrpanoNewObject");
    if ($('#flowInfoFileId').val() == null || $('#flowInfoFileId').val() == ''
            || krpano.get('layer[flowInfoLayer].name') == null) {
        var showMessage = '请先添加一个浮动效果';
        window.top.showErrorMessage(showMessage, false, 3);
        return false;
    }

    var _positionX = krpano.get('layer[flowInfoLayer].x').toString();
    var _positionY = krpano.get('layer[flowInfoLayer].y').toString();
    var positionX = "";
    var positionY = "";
    // 平面图的场合
    if (Number(_positionX) >= 0) {
        // 正数的场合
        positionX = Math.round(parseFloat(_positionX) * 1000) / 1000;
    } else {
        // 负数的场合
        positionX = _positionX.substring(0, 1) + Math.round(parseFloat(_positionX.substring(1)) * 1000) / 1000;
    }
    if (Number(_positionY) >= 0) {
        // 正数的场合
        positionY = Math.round(parseFloat(_positionY) * 1000) / 1000;
    } else {
        // 负数的场合
        positionY = _positionY.substring(0, 1) + Math.round(parseFloat(_positionY.substring(1)) * 1000) / 1000;
    }

    $('#flowInfoFileX').val(positionX);
    $('#flowInfoFileY').val(positionY);

    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('正在保存当前浮动信息层信息，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() {
        window.top.layer.close(currentConfirmIndex);

        var _ajaxUrl = getMemberContextPath('pano0105/doSaveFlowInfoOffset');
        var param = $("#pano0105Form").serialize();
        jQuery.post(_ajaxUrl, param, function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                location.reload(true);
            }
        });

    }, function() {
        // 取消操作
    });

}

// 跳转画面前取得当前视角点
function setLookAtForEdit() {
    $("#pano0105BackAth").val('');
    $("#pano0105BackAtv").val('');

    // 得到当前场景的中心点
    var krpano = document.getElementById("pano0105KrpanoNewObject");
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
    $("#pano0105BackAth").val(positionAth);
    $("#pano0105BackAtv").val(positionAtv);
}

// 前往调整浮动信息层大小的画面
function callPano0209() {
    var krpano = document.getElementById("pano0105KrpanoNewObject");

    var layerScale = krpano.get('layer[flowInfoLayer].scale').toString();
    var layerX = krpano.get('layer[flowInfoLayer].x').toString();
    var layerY = krpano.get('layer[flowInfoLayer].y').toString();

    setLookAtForEdit(); // 取得当前视角点

    var targetUrl = 'pano0209/';
    var urlParam = form2js($("#pano0105Form")[0]);
    urlParam['returnTargetIframe'] = window.name;
    urlParam['expositionIdForPano0209'] = urlParam['expositionId'];

    urlParam['panoramaIdForPano0209'] = urlParam['panoramaId'];
    urlParam['positionAthForEdit'] = positionAth;
    urlParam['positionAtvForEdit'] = positionAtv;

    urlParam['LastedHotspotIdFrom0105'] = urlParam['theLastedSceneHotspotId'];
    urlParam['currentHotspotIdFrom0105'] = urlParam['currentRecommendHotspotId'];
    urlParam['commandTypeFromPano0105'] = 'flowInfo';
    urlParam['pano0209flowFileId'] = urlParam['flowInfoFileId'];
    urlParam['pano0209flowFileType'] = urlParam['flowInfoType'];
    urlParam['pano0209flowFilePath'] = urlParam['flowInfoFilePath'];
    urlParam['pano0209flowFileInfo'] = urlParam['flowInfoFileInfo'];
    urlParam['hotspotScale'] = layerScale;
    urlParam['pano0209layerX'] = layerX;
    urlParam['pano0209layerY'] = layerY;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '编辑热点基本信息',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            location.reload(true);
        }
    });
}
