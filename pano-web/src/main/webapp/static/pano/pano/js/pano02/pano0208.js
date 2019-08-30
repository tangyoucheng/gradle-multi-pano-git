// ================================================
// 为热点添加素材图片加载时的处理
// 【入力】
// 【返却】
// 【作成】
// 【更新】
// 【概要】
// ================================================
$(document).ready(function() {
    // bootstrapTable初始化
    $("#table-material-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = $('#table-material-info').bootstrapTable('getSelections').length;
    $('#btn_delete').prop('disabled', selectionsLength == 0);
    $('#btn_edit').prop('disabled', selectionsLength != 1);
    $('#btn_choose').prop('disabled', selectionsLength == 0);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table ';
    jqueryEvent = jqueryEvent + 'check-all.bs.table uncheck-all.bs.table';
    $('#table-material-info').on(jqueryEvent, function() {
        var selectionsLength = $('#table-material-info').bootstrapTable('getSelections').length;
        $('#btn_delete').prop('disabled', selectionsLength == 0);
        $('#btn_edit').prop('disabled', selectionsLength != 1);
        $('#btn_choose').prop('disabled', selectionsLength == 0);
    });

    // 检索处理
    $("#btn_query").click(function() {
        // 检索处理
        searchData();
    });

    // 添加处理
    $("#btn_add").click(function() {
        var targetUrl = 'pano0301/';
        var urlParam = form2js($("#pano0208FormSearch")[0]);
        // 做成FormData对象
        // var ajaxFormData = new
        // FormData(document.getElementById("pano0102FormAdd"));
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '添加素材',
            type : 2,
            closeBtn : 1, // 不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 选择处理
    $("#btn_choose").click(function() {
        var hotspotTypeChoice = $('#hotspotTypeChoice').val();
        var tableRowsInfo = $('#table-material-info').bootstrapTable('getSelections');
        if (hotspotTypeChoice == PanoConstants.VAL_MATERIAL_TYPE_SOUND && tableRowsInfo.length != 2) {
            var errorMessage = '音频热点必须选择两张图！';
            checkErrors.push(errorMessage);
        }

        var returnObjects = [];
        jQuery.each(tableRowsInfo, function(i, tableRowInfo) {
            var returnObject = {};
            returnObject['hotspotTypeId'] = hotspotTypeChoice;
            returnObject['materialId'] = tableRowInfo.materialId;
            returnObject['materialName'] = tableRowInfo.materialName;
            returnObject['notes'] = tableRowInfo.notes;
            returnObject['materialTypeId'] = tableRowInfo.materialTypeId;
            returnObject['materialPath'] = tableRowInfo.materialPath;
            returnObject['gifWidth'] = tableRowInfo.gifWidth;
            returnObject['gifHeight'] = tableRowInfo.gifHeight;
            returnObject['gifFrameCount'] = tableRowInfo.gifFrameCount;
            returnObject['gifDelayTime'] = tableRowInfo.gifDelayTime;
            returnObject['hasPngImage'] = tableRowInfo.hasPngImage;
            returnObject['flowTextInfo'] = tableRowInfo.flowTextInfo;
            returnObjects.push(returnObject);
        });
        // 选中的值传给前页面
        window.parent.frames[$('#returnTargetIframe').val()].setPano0208ReturnObject(returnObjects);
        // 自页面关闭
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    // 检索处理
    searchData();

});

function searchData() {
    // 先销毁表格
    $('#table-material-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-material-info').bootstrapTable({
        url : getMemberContextPath('pano0208/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'materialName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0208FormSearch")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "materialId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'rowNumber',
            width : 50,
            formatter : function(value, tableRowInfo, index) {
                var options = $('#table-material-info').bootstrapTable("getOptions");
                return (options.pageNumber - 1) * (options.pageSize) + index + 1;
            }
        }, {
            field : 'materialId',
            sortable : true
        }, {
            field : 'materialName',
            sortable : true
        }, {
            field : 'notes'
        }, {
            field : 'materialTypeId'
        }, {
            field : 'materialPath'
        }, {
            field : 'gifWidth'
        }, {
            field : 'gifHeight'
        }, {
            field : 'gifFrameCount'
        }, {
            field : 'gifDelayTime'
        }, {
            field : 'hasPngImage'
        }, {
            field : 'flowTextInfo'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-edit' : function(e, value, tableRowInfo, index) {
                    doEdit(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 编辑按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-edit"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;编辑';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 编辑
function doEdit(tableRowInfo) {
    var openUrl = '/pano03/pano0303';
    openUrl = openUrl + '?expositionId=' + tableRowInfo.expositionId;
    openUrl = openUrl + '&outletsCode=' + tableRowInfo.outletsCode;
    openUrl = openUrl + '&bankAccountCode=' + tableRowInfo.bankAccountCode;
    openUrl = getMemberContextPath(openUrl);
    window.top.layer.open({
        title : '编辑素材',
        type : 2,
        area : [ '80%', '100%' ], // 宽高
        content : [ openUrl, 'yes' ],// iframe层出现滚动条
        end : function() {
            // location.reload(true);
            searchData();
        }
    });
}