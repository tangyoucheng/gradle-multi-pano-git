/**
 * 全景图地图编辑操作
 */
$(document).ready(function() {

    // 删除成功后处理
    $('#delete-confirm-button').click(function() {
        $('#pano0106Form').submit();
    });

    // 添加导航图
    $('#add-mini-map-button').click(function() {
        addMiniMap();
    });

    // 为当前展览选择导航图
    $('#set-mini-map-button').click(function() {
        setMiniMap();
    });

    // 删除导航图
    $('#delete-mini-map-button').click(function() {
        deleteMiniMap();
    });

    // 初始化场景信息
    doInitPanoInfo()

});

// 初始化场景信息
function doInitPanoInfo() {
    window.top.krpanoMaskLoading();
    // 引擎配置文件路径
    var xmlSessionFilePath = $('#panoramaPath').val();
    // 初始化参数
    var panoSettings = {};
    // var panoramaHlookat = $('#pano0106positionAthForEdit').val();
    // var panoramaVlookat = $('#pano0106positionAtvForEdit').val();
    // if (panoramaHlookat != '' && panoramaHlookat != null && panoramaVlookat
    // != '' && panoramaVlookat != null) {
    // panoSettings['view.hlookat'] = panoramaVlookat;
    // panoSettings['view.vlookat'] = panoramaVlookat;
    // }
    // 启动引擎
    removepano("pano0106KrpanoNewObject");
    embedpano({
        swf : PanoConstants.VAL_EMBEDPANO_SWF,
        id : 'pano0106KrpanoNewObject',
        xml : xmlSessionFilePath,
        target : "pano0106Pano",
        wmode : "transparent",
        html5 : "prefer",
        passQueryParameters : true,
        vars : panoSettings,
        onready : window.top.removekrpanoMask
    });

    // 图上加载热点处理
    // loadPano0104HotspotInfo(result.hotspotInfoList);

}

// 呼出新建导航图的页面
function addMiniMap() {
    $("#addMiniMap").imuiPageDialog({
        url : getMemberContextPath('pano0304'),
        title : '新建导航图',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            lasthotspotIdFrom0106 : $('#pano0106LasthotspotId').val(),
            currenthotspotIdFrom0106 : $('#pano0106CurrenthotspotId').val(),
            expositionId : $("#expositionId").val(),
            expositionName : $("#expositionName").val(),
            panoramaId : $("#panoramaId").val(),
            existedExpositionMapId : $("#expositionMapId").val(),
        }
    });
    return false
}

// 呼出为展览选定地图的页面
function setMiniMap() {

    var targetUrl = 'pano0305/';
    var urlParam = form2js($("#pano0106FormAjaxSubmit")[0]);
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

}
// 删除导航图操作
function deleteMiniMap() {

    // 询问框
    var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
        icon : 3,
        title : '提示信息',
        btn : [ '确认', '取消' ]
    // 按钮
    }, function() { // 确认操作
        window.top.layer.close(currentConfirmIndex);

        // 表单数据转换成JS对象
        var ajaxSubmitFormData = form2js($("#pano0106FormAjaxSubmit")[0]);
        $.ajax({
            type : 'post',
            traditional : true,
            data : ajaxSubmitFormData,
            dataType : 'json',
            url : getMemberContextPath('pano0106/doDelete'),
            success : function(result) {
                if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                    location.reload(true);
                }
            }
        // ,
        // error : function() {
        // }
        });
    }, function() {
        // 取消操作
    });
}
