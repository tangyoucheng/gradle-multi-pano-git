/**
 * 全景图热点显示
 */
$(document).ready(function() {
    // 设定第一视角处理
    $('#btn_set_lookat').click(function() {
        // 检查展览ID是否存在
        if (!currentTableRowInfo.expositionId) {
            return true;
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存当前视角，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);

            setLookAtForEdit(); // 取得当前视角点

            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
            ajaxSubmitFormData['panoramaId'] = currentTableRowInfo.panoramaId;
            ajaxSubmitFormData['panoramaHlookat'] = currentPanoramaInfo['positionAth'];
            ajaxSubmitFormData['panoramaVlookat'] = currentPanoramaInfo['positionAtv'];
            
            ajaxSubmitFormData['selectedHotspotId'] = selectedHotspotInfo['hotspotId'];

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0104/doSaveLookatPoint'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        searchData();
                    }
                }
            })
        }, function() {// 取消操作
        });
    });
    // 编辑单点热点处理
    $('#btn_edit_hotspot').click(function() {
        // 检查展览ID是否存在
        if (!currentTableRowInfo.expositionId) {
            return true;
        }
        
        var targetUrl = 'pano0203/';
        var urlParam = {};
        urlParam['expositionId'] = currentTableRowInfo.expositionId;
        urlParam['panoramaId'] = currentTableRowInfo.panoramaId;
        urlParam['panoramaName'] = currentTableRowInfo.panoramaName;
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '单点热点编辑',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });
    // 编辑多边形热点处理
    $('#btn_edit_hotspot_polygon').click(function() {
        // 检查展览ID是否存在
        if (!currentTableRowInfo.expositionId) {
            return true;
        }
        
        var targetUrl = 'pano0108/';
        var urlParam = {};
        urlParam['expositionId'] = currentTableRowInfo.expositionId;
        urlParam['panoramaId'] = currentTableRowInfo.panoramaId;
        urlParam['panoramaName'] = currentTableRowInfo.panoramaName;
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '多边形热点编辑',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });
    // 编辑整体效果处理
    $('#btn-edit-exposition-layer').click(function() {
        // 检查展览ID是否存在
        if (!currentTableRowInfo.expositionId) {
            return true;
        }
        
        var targetUrl = 'pano0105/';
        var urlParam = {};
        urlParam['expositionId'] = currentTableRowInfo.expositionId;
        urlParam['panoramaId'] = currentTableRowInfo.panoramaId;
        urlParam['panoramaName'] = currentTableRowInfo.panoramaName;
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '编辑整体效果',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });
});

