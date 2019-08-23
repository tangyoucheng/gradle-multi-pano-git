//操作历史共通
$(function() {
    var tableOperateLog = $('#table-operateLog');

    // bootstrapTable初始化
    tableOperateLog.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableOperateLog.bootstrapTable('getSelections').length;
    $('#operateLogBtn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableOperateLog.on(jqueryEvent, function() {
        var selectionsLength = tableOperateLog.bootstrapTable('getSelections').length;
        $('#operateLogBtn_delete').prop('disabled', !selectionsLength);
    });
    searchData();
});

function searchData() {
    // 先销毁表格
    $('#table-operateLog').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-operateLog').bootstrapTable({
        url : getMemberContextPath('operateLog/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'moduleTitle',// 初始化的时候排序的字段
        sortOrder : "desc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#operateLogFormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "operateId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            field : 'moduleId',
        }, {
            field : 'recordId',
        }, {
            field : 'operateId',
        }, {
            field : 'operateTime',
        }, {
            field : 'operateName'
        }, {
            field : 'businessType'
        }, {
            field : 'businessTypeName'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-view' : function(e, value, tableRowInfo, index) {
                    doView(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 编辑按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn cis-btn-blue font-12 p-1 row-view"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;查看';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 编辑
function doView(tableRowInfo) {
    var targetUrl = 'operateLogView/';
    targetUrl = targetUrl + '?operateId=' + tableRowInfo.operateId;
    window.top.layer.open({
        title : '查看',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
}
