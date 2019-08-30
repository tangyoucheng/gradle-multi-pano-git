/**
 * 全景图热点显示
 */
$(document).ready(function() {

    // bootstrapTable初始化
    $("#table-panorama-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });

    // 检索全景处理
    searchData();

});

// 检索场景信息
function searchData() {
    // 先销毁表格
    $('#table-panorama-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-panorama-info').bootstrapTable({
        url : getMemberContextPath('pano0104/doSearchPano'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : undefined, // 工具按钮用哪个容器
        pagination : false, // 是否显示分页（*）
        sortable : true, // 是否启用排序
        sortName : 'panoramaName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0104Form")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "panoramaId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            field : 'panoramaSortKey'
        }, {
            field : 'panoramaName',
            sortable : true,
            width : 100,
        }, {
            field : 'expositionId'
        }, {
            field : 'panoramaId'
        }, {
            field : 'panoramaSoundId'
        }, {
            field : 'panoramaHlookat'
        }, {
            field : 'panoramaVlookat'
        }, {
            field : 'startSceneFlag'
        }, ],
        onDblClickRow : function(tableRowInfo, $element) {
            $('.selected').removeClass('selected');
            $($element).addClass('selected');
            currentTableRowInfo = tableRowInfo;
            currentPanoramaInfo = tableRowInfo;
            selectedHotspotInfo = {};// 选中的当前热点信息
            
            // 场景处理按钮制御
            $('#btn_edit_pano').prop('disabled', !currentTableRowInfo);
            $('#btn_delete_pano').prop('disabled', !currentTableRowInfo);
            // 初始化场景
            doInitPanoInfo(tableRowInfo);
        },
        onLoadSuccess : function(result) {

            // 场景处理按钮制御
            $('#btn_update_panoOrder').prop('disabled', true);
            $('#btn_edit_pano').prop('disabled', true);
            $('#btn_delete_pano').prop('disabled', true);

            if (result.rows.length == 0) {
                removepano("pano0104KrpanoNewObject");
                return;
            }

            var dblClickRow = $('#table-panorama-info tbody').find('> tr[data-index]:eq(0) > td:eq(0)');
            if (!currentTableRowInfo) {
                dblClickRow.dblclick();
                return;
            }
            // 保持之前选中的场景
            jQuery.each(result.rows, function(i, item) {
                if (currentTableRowInfo.panoramaId == item.panoramaId) {
                    dblClickRow = $('#table-panorama-info tbody').find('> tr[data-index]:eq(' + i + ') > td:eq(0)');
                }
            });
            dblClickRow.dblclick();
        },
        // 当选中行，拖拽时的哪行数据，并且可以获取这行数据的上一行数据和下一行数据
        onReorderRowsDrag : function(table, row) {
            return false;
        },
        // 拖拽完成后的这条数据，并且可以获取这行数据的上一行数据和下一行数据
        onReorderRowsDrop : function(table, row) {
            return false;
        },
        // 当拖拽结束后，整个表格的数据
        onReorderRow : function(newData) {
            newOrderList = newData;
            $('#btn_update_panoOrder').prop('disabled', !newOrderList);
            return false;
        }
    });
};

// 初始化场景信息
function doInitPanoInfo(tableRowInfo) {

    window.top.krpanoMaskLoading();

    // 表单数据转换成JS对象
    var ajaxSubmitFormData = form2js($("#pano0104FormAjaxSubmit")[0]);
    ajaxSubmitFormData['panoramaId'] = currentTableRowInfo.panoramaId;

    $.ajax({
        type : 'post',
        traditional : true,
        data : ajaxSubmitFormData,
        dataType : 'json',
        url : getMemberContextPath('pano0104/doSearchPanoElementInfo'),
        success : function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                pano0104CurrentForm = result.obj;
                // 引擎配置文件路径
                var xmlfilePath = "pano0104/";
                xmlfilePath = xmlfilePath + tableRowInfo.expositionId;
                xmlfilePath = xmlfilePath + "/panoramas/";
                xmlfilePath = xmlfilePath + tableRowInfo.panoramaId;
                xmlfilePath = xmlfilePath + "/show_l.xml?temp=" + Math.random();
                var xmlSessionFilePath = window.top.getSessionFileEditPath(xmlfilePath);
                // 初始化参数
                var panoSettings = {};
                if (tableRowInfo.panoramaHlookat != '' && tableRowInfo.panoramaHlookat != null
                        && tableRowInfo.panoramaVlookat != '' && tableRowInfo.panoramaVlookat != null) {
                    panoSettings['view.hlookat'] = tableRowInfo.panoramaHlookat;
                    panoSettings['view.vlookat'] = tableRowInfo.panoramaVlookat;
                }
                // 启动引擎
                removepano("pano0104KrpanoNewObject");
                embedpano({
                    swf : PanoConstants.VAL_EMBEDPANO_SWF,
                    id : 'pano0104KrpanoNewObject',
                    xml : xmlSessionFilePath,
                    target : "pano0104Pano",
                    wmode : "transparent",
                    html5 : "prefer",
                    passQueryParameters : true,
                    vars : panoSettings,
                    onready : window.top.removekrpanoMask
                });

                // 图上加载热点处理
                // loadPano0104HotspotInfo(result.hotspotInfoList);
            }
        }
    })

}
