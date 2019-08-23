//================================================
//展览一览画面检索
//================================================
$(document).ready(function() {

    // 日期
    laydate.render({
        elem : '#txt_expositionStartDate',
        type : 'date',
        // range : '至',
        format : 'yyyy-MM-dd',
        trigger : 'click' // 采用click弹出
    });

    laydate.render({
        elem : '#txt_expositionEndDate',
        type : 'date',
        format : 'yyyy-MM-dd',
        trigger : 'click' // 采用click弹出
    });

    // 检索处理
    $("#btn_query").click(function() {
        // 检索处理
        searchData();
    });
    // 添加处理
    $("#btn_add").click(function() {
        var targetUrl = 'pano0101/';
        var urlParam = form2js($("#pano0102FormAdd")[0]);
        // 做成FormData对象
        // var ajaxFormData = new
        // FormData(document.getElementById("pano0102FormAdd"));
        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '添加展览',
            type : 2,
            closeBtn : 1, // 不显示关闭按钮
            area : [ '90%', '90%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // bootstrapTable初始化
    $("#table-exposition-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = $('#table-exposition-info').bootstrapTable('getSelections').length;
    $('#btn_delete').prop('disabled', selectionsLength != 1);
    $('#btn_edit').prop('disabled', selectionsLength != 1);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table ';
    jqueryEvent = jqueryEvent + 'check-all.bs.table uncheck-all.bs.table';
    $('#table-exposition-info').on(jqueryEvent, function() {
        var selectionsLength = $('#table-exposition-info').bootstrapTable('getSelections').length;
        $('#btn_delete').prop('disabled', selectionsLength != 1);
        $('#btn_edit').prop('disabled', selectionsLength != 1);
    });

    // 检索处理
    searchData();

});

// 编辑展览按钮按下时的处理
function editExposition(_expositionName, _expositionId) {
    $("#hidExpositionName").val(_expositionName);
    $("#hidExpositionId").val(_expositionId);
    $("#goto-ic0103-form").submit();
}

function searchData() {
    // 先销毁表格
    $('#table-exposition-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-exposition-info').bootstrapTable({
        url : getMemberContextPath('pano0102/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'expositionId',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0102FormSearch")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "expositionId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'rowNumber',
            width : 50,
            formatter : function(value, tableRowInfo, index) {
                var options = $('#table-exposition-info').bootstrapTable("getOptions");
                return (options.pageNumber - 1) * (options.pageSize) + index + 1;
            }
        }, {
            field : 'expositionName',
            sortable : true
        }, {
            field : 'expositionId',
            sortable : true
        }, {
            field : 'status',
            sortable : true
        }, {
            field : 'expositionStatusName',
            sortable : true
        }, {
            field : 'expositionStartDate',
            sortable : true
        }, {
            field : 'expositionEndDate'
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
    var targetUrl = 'pano0103/';
    targetUrl = targetUrl + '?expositionId=' + tableRowInfo.expositionId;
    window.top.layer.open({
        title : '编辑展览',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
}
