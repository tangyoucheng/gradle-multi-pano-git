// 在线用户管理
var tableOnlineUser;
$(function() {
    tableOnlineUser = $('#table-onlineUser');

    // bootstrapTable初始化
    tableOnlineUser.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableOnlineUser.bootstrapTable('getSelections').length;
    $('#platform9901Btn_forceLogout').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableOnlineUser.on(jqueryEvent, function() {
        var selectionsLength = tableOnlineUser.bootstrapTable('getSelections').length;
        $('#platform9901Btn_forceLogout').prop('disabled', !selectionsLength);
    });

    // 检索
    $("#platform9901Btn_query").click(function() {
        searchData();
    });

    // 强退
    $("#platform9901Btn_forceLogout").click(
        function() {

            // 询问框
            var currentConfirmIndex = window.top.layer.confirm('本操作会强退选中的数据，是否继续？', {
                icon : 3,
                title : '提示信息',
                btn : [ '确认', '取消' ]
            // 按钮
            }, function() { // 确认操作
                // 选中的在线用户信息
                var onlineUserList = [];
                $.map(tableOnlineUser.bootstrapTable('getSelections'), function(row) {
                    var onlineUserObject = {};
                    onlineUserObject['sessionId'] = row.sessionId;
                    onlineUserObject['loginId'] = row.loginId;
                    onlineUserList.push(onlineUserObject);
                });
                // 表单数据转换成JS对象
                var ajaxSubmitFormData = $("#platform9901FormAjaxSubmit").serialize() + '&'
                        + $.param(onlineUserList.serializeObject("onlineUserList"));

                window.top.layer.close(currentConfirmIndex);

                $.ajax({
                    type : 'post',
                    traditional : true,
                    data : ajaxSubmitFormData,
                    dataType : 'json',
                    url : getAdminContextPath('platform9901/doForceLogout'),
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
    tableOnlineUser.bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    tableOnlineUser.bootstrapTable({
        url : getAdminContextPath('platform9901/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'lastAccessDate',// 初始化的时候排序的字段
        sortOrder : "desc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platform9901FormSearch")[0]);
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
            field : 'sessionId'
        }, {
            field : 'loginId',
            sortable : true
        }, {
            field : 'lastAccessDate',
            sortable : true
        }, {
            field : 'ipAddress'
        }, {
            field : 'browser'
        }, {
            field : 'operatingSystem'
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

