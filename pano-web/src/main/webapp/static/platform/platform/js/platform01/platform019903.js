// 公司社区一栏--弹出页面

$(function() {
    var tableDepartmentInfo = $('#table-departmentInfo');

    // bootstrapTable初始化
    tableDepartmentInfo.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableDepartmentInfo.bootstrapTable('getSelections').length;
    $('#platform019903Btn_selected').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableDepartmentInfo.on(jqueryEvent, function() {
        var selectionsLength = tableDepartmentInfo.bootstrapTable('getSelections').length;
        $('#platform019903Btn_selected').prop('disabled', !selectionsLength);
    });

    // 选择处理
    $('#platform019903Btn_selected').click(function() {
        var returnObject = [];
        var recoedObject = {};
        var arrselections = tableDepartmentInfo.bootstrapTable('getSelections');

        jQuery.each(arrselections, function(i, item) {
            recoedObject = {};
            recoedObject['departmentId'] = item.departmentId;
            recoedObject['departmentName'] = item.departmentName;
            returnObject.push(recoedObject);
        });
        // 选中的值传给前页面
        window.parent.frames[$('#returnTargetIframe').val()].setCis019903ReturnObject(returnObject);
        // 自页面关闭
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });
    searchData();
});

function searchData() {
    // 先销毁表格
    $('#table-departmentInfo').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-departmentInfo').bootstrapTable({
        url : getAdminContextPath('platform0199/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        pagination : false, // 是否显示分页（*）
        sortable : true, // 是否启用排序
        sortName : 'departmentName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platform019903FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        // height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId : "departmentId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'departmentId',
            sortable : true
        }, {
            field : 'departmentName',
            sortable : true  
        },{
            field : 'rowOperate',
            events : {
                'click .row-choose' : function(e, value, tableRowInfo, index) {
                    doChoose(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 选择按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn platform-btn-blue font-12 p-1 row-choose"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;选择';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

//编辑
function doEdit(tableRowInfo) {
    var returnObject = [];
    var recoedObject = {};
    var arrselections = tableDepartmentInfo.bootstrapTable('getSelections');

    jQuery.each(arrselections, function(i, item) {
        recoedObject = {};
        recoedObject['departmentId'] = item.departmentId;
        recoedObject['departmentName'] = item.departmentName;
        returnObject.push(recoedObject);
    });
    // 选中的值传给前页面
    window.parent.frames[$('#returnTargetIframe').val()].setCis019903ReturnObject(returnObject);
    // 自页面关闭
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
