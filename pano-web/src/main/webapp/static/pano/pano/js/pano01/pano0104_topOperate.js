/**
 * 全景图热点显示
 */
$(document).ready(function() {

    // 新增场景
    $('#btn_add_pano').click(function() {
        var targetUrl = 'pano0201/';
        var urlParam = {};
        urlParam['expositionId'] = currentTableRowInfo.expositionId;
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '新增场景',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 编辑场景
    $('#btn_edit_pano').click(function() {
        var targetUrl = 'pano0202/';
        var urlParam = {};
        urlParam['expositionId'] = currentTableRowInfo.expositionId;
        urlParam['panoramaId'] = currentTableRowInfo.panoramaId;
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '编辑场景',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 删除场景
    $('#btn_delete_pano').click(function() {
        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正删除当前页面的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {// 确认操作
            window.top.layer.close(currentConfirmIndex);
            var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
            ajaxSubmitFormData['panoramaId'] = currentTableRowInfo.panoramaId;
            ajaxSubmitFormData['expositionId'] = currentTableRowInfo.expositionId;
            $.ajax({
                url : getMemberContextPath('pano0104/doDeletePano'),
                type : "post",
                traditional : true,
                dataType : "json",
                data : ajaxSubmitFormData,
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        searchData();
                    }
                }
            });
        }, function() {// 取消操作
        });

    });

    // 保存场景排序
    $("#btn_update_panoOrder").click(function() {
        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存当前排序，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map(newOrderList, function(row) {
                return row.panoramaId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0104/doUpdateOrder'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        searchData();
                    }
                }
            })
        }, function() {// 取消操作
        });
    });

    // 素材一览
    $("#btn_material_list").click(function() {
        var targetUrl = 'pano0302/';

        var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
        var urlParam = {};
        urlParam['expositionId'] = ajaxSubmitFormData['expositionId'];
        urlParam['expositionName'] = ajaxSubmitFormData['expositionName'];
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '素材一览',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 关闭处理
    $('#btn_close').click(function() {
        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在关闭当前编辑页面，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 关闭确认框
            window.top.layer.close(currentConfirmIndex);
            // 关闭自页面
            var index = parent.layer.getFrameIndex(window.name);
            window.top.layer.close(index);

        }, function() {
            // 取消操作
        });
    });
});
