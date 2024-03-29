// ================================================
// 素材一览页面的处理
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
    $('#btn_move').prop('disabled', selectionsLength == 0);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table ';
    jqueryEvent = jqueryEvent + 'check-all.bs.table uncheck-all.bs.table';
    $('#table-material-info').on(jqueryEvent, function() {
        var selectionsLength = $('#table-material-info').bootstrapTable('getSelections').length;
        $('#btn_delete').prop('disabled', selectionsLength == 0);
        $('#btn_edit').prop('disabled', selectionsLength != 1);
        $('#btn_move').prop('disabled', selectionsLength == 0);
    });

    // 检索处理
    $("#btn_query").click(function() {
        // 检索处理
        searchData();
    });

    // 添加处理
    $("#btn_add").click(function() {
        var targetUrl = 'pano0301/';
        var urlParam = form2js($("#pano0302FormSearch")[0]);
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

    // 删除
    $("#btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0302FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($('#table-material-info').bootstrapTable('getSelections'), function(row) {
                // materialId前台传入后台的数据
                return row.materialId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0302/doDelete'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // 重新检索画面
                        searchData();
                    }
                }
            // ,
            // error : function() {
            // }
            });
        }, function() {
            // 取消操作
        });

    });

    // 转移处理
    $("#btn_move").click(function() {

        // 定义转移素材时的提示信息
        var confirmMsg;
        if ($('#materialBelongType').val() == PanoConstants.VAL_MATERIAL_BELONGTYPE_EXPOSITION) {
            confirmMsg = "正在将选中素材转移为公共素材，是否继续？";
        } else {
            confirmMsg = "正在将选中素材转移为所在展览素材，是否继续？";
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm(confirmMsg, {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);

            // 确认操作
            var ajaxFormData = {};
            ajaxFormData = $.extend({}, ajaxFormData, form2js($("#pano0302FormSearch")[0]));

            $.ajax({
                url : getMemberContextPath('pano0302/doMoveMaterials'),
                type : "post",
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

    });

    // 检索处理
    searchData();

});

function searchData() {
    // 先销毁表格
    $('#table-material-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-material-info').bootstrapTable({
        url : getMemberContextPath('pano0302/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'materialName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0302FormSearch")[0]);
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
    var openUrl = 'pano0303/';
    var urlParam = form2js($("#pano0302FormSearch")[0]);
    urlParam['materialId'] = tableRowInfo.materialId;
    urlParam['materialName'] = tableRowInfo.materialName;
    urlParam['materialTypeId'] = tableRowInfo.materialTypeId;
    // 做成FormData对象
    // var ajaxFormData = new
    // FormData(document.getElementById("pano0102FormAdd"));
    openUrl = openUrl + '?' + $.param(urlParam);
    openUrl = getMemberContextPath(openUrl);
    window.top.layer.open({
        title : '编辑素材',
        type : 2,
        area : [ '90%', '100%' ], // 宽高
        content : [ openUrl, 'yes' ],// iframe层出现滚动条
        end : function() {
            // location.reload(true);
            searchData();
        }
    });
}