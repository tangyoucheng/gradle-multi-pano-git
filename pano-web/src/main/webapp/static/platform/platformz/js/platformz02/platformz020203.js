// 角色 一览，弹出页面

$(function() {
    var tableRoleInfo = $('#table-rolesInfo');

    // bootstrapTable初始化
    tableRoleInfo.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableRoleInfo.bootstrapTable('getSelections').length;
    $('#platformz020203Btn_selected').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableRoleInfo.on(jqueryEvent, function() {
        var selectionsLength = tableRoleInfo.bootstrapTable('getSelections').length;
        $('#platformz020203Btn_selected').prop('disabled', !selectionsLength);
    });

    // 选择处理
    $('#platformz020203Btn_selected').click(function() {
        var returnObject = [];
        var recoedObject = {};
        var arrselections = tableRoleInfo.bootstrapTable('getSelections');

        jQuery.each(arrselections, function(i, item) {
            recoedObject = {};
            recoedObject['roleId'] = item.roleId;
            recoedObject['roleName'] = item.roleName;
            returnObject.push(recoedObject);
        });
        // 选中的值传给前页面
        window.parent.frames[$('#returnTargetIframe').val()].setCisz020203ReturnObject(returnObject);
        // 自页面关闭
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });
    searchData();
});

function searchData() {
    // 先销毁表格
    $('#table-rolesInfo').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-rolesInfo').bootstrapTable({
        url : getMemberContextPath('platformz020203/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'roleName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platformz020203FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "roleId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'roleName',
            sortable : true
        }, {
            field : 'roleId'
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};