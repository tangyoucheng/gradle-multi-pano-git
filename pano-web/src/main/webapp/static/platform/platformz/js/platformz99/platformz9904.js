// 用户登陆日志管理
var tableLoginInfo;
$(function() {
    tableLoginInfo = $('#table-loginInfo');

    // bootstrapTable初始化
    tableLoginInfo.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableLoginInfo.bootstrapTable('getSelections').length;
    $('#Platformz9904Btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableLoginInfo.on(jqueryEvent, function() {
        var selectionsLength = tableLoginInfo.bootstrapTable('getSelections').length;
        $('#Platformz9904Btn_delete').prop('disabled', !selectionsLength);
    });

    // 检索
    $("#Platformz9904Btn_query").click(function() {
        searchData();
    });

    // 删除
    $("#Platformz9904Btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#Platformz9904FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map(tableLoginInfo.bootstrapTable('getSelections'), function(row) {
                return row.infoId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('Platformz9904/doDelete'),
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

    // 清空
    $("#Platformz9904Btn_clear").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会清空所有的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#Platformz9904FormAjaxSubmit")[0]);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('Platformz9904/doClear'),
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

    if (window.location.protocol.startWith('http')) {
        searchData();
    }
});

function searchData() {
    // 先销毁表格
    tableLoginInfo.bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    tableLoginInfo.bootstrapTable({
        url : getMemberContextPath('Platformz9904/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'loginTime',// 初始化的时候排序的字段
        sortOrder : "desc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#Platformz9904FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "loginId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'loginId',
            sortable : true
        }, {
            field : 'ipAddress'
        }, {
            field : 'browser'
        }, {
            field : 'operatingSystem'
        }, {
            field : 'status',
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                if (PlatformConstants.flagStatus_Enable === tableRowInfo.status) {
                    showData = showData + "<span class='badge badge-primary'>成功</span>";
                } else {
                    showData = showData + "<span class='badge badge-danger'>失败</span>";
                }
                return showData;
            }
        }, {
            field : 'msg'
        }, {
            field : 'loginTime',
            sortable : true
        }, {
            field : 'infoId'
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

