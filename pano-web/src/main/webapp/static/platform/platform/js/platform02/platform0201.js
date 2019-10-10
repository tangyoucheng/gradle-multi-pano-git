// 管理员管理

$(function() {
    var tableAdminInfo = $('#table-adminInfo');

    // bootstrapTable初始化
    tableAdminInfo.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableAdminInfo.bootstrapTable('getSelections').length;
    $('#platform0201Btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableAdminInfo.on(jqueryEvent, function() {
        var selectionsLength = tableAdminInfo.bootstrapTable('getSelections').length;
        $('#platform0201Btn_delete').prop('disabled', !selectionsLength);
    });
    // 新建
    $("#platform0201Btn_new").click(function() {
        var targetUrl = 'platform020101/';
        window.top.layer.open({
            title : '新建管理员用户',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '90%' ], // 宽高
            content : [ getAdminContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 删除
    $("#platform0201Btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platform0201FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-adminInfo").bootstrapTable('getSelections'), function(row) {
                return row.adminId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getAdminContextPath('platform0201/doDelete'),
                success : function(result) {
                    if (result.success) {
                        window.top.layer.alert(result.msg, function(index) {
                            // 关闭当前层
                            window.top.layer.close(index);
                            // 重新检索画面
                            searchData();
                        });
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

    searchData();
});

function searchData() {
    // 先销毁表格
    $('#table-adminInfo').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-adminInfo').bootstrapTable({
        url : getAdminContextPath('platform0201/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'adminLoginId',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platform0201FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "adminId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'adminLoginId',
            sortable : true
        }, {
            field : 'adminName',
            sortable : true
        }, {
            field : 'adminEmail',
            sortable : true
        }, {
            field : 'adminId'
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
    var targetUrl = 'platform020102/';
    targetUrl = targetUrl + '?adminId=' + tableRowInfo.adminId;
    window.top.layer.open({
        title : '编辑管理员用户',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getAdminContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
}