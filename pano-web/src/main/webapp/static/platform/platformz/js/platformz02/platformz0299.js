// 管理

$(function() {
    var tableMemberInfo = $('#table-companyInfo');

    // bootstrapTable初始化
    tableMemberInfo.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableMemberInfo.bootstrapTable('getSelections').length;
    $('#platformz0299Btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableMemberInfo.on(jqueryEvent, function() {
        var selectionsLength = tableMemberInfo.bootstrapTable('getSelections').length;
        $('#platformz0299Btn_delete').prop('disabled', !selectionsLength);
    });
    // 新建
    $("#platformz0299Btn_new").click(function() {
        var targetUrl = 'platformz029901/';
        window.top.layer.open({
            title : '新建公司',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '90%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 删除
    $("#platformz0299Btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platformz0299FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-companyInfo").bootstrapTable('getSelections'), function(row) {
                // companyId前台传入后台的数据
                return row.companyId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('platformz0299/doDelete'),
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
    // 检索
    $("#platformz0299Btn_query").click(function() {
        // debugger; 检测
        searchData();
    });
});

function searchData() {
    // 先销毁表格
    $('#table-companyInfo').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-companyInfo').bootstrapTable({
        url : getMemberContextPath('platformz0299/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'companyId',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platformz0299FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "companyId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        },  {
            field : 'companyName',
            sortable : true
        }, {
            field : 'createUserId',
            sortable : true
        }, {
            field : 'createDate',
            sortable : true
        }, {
            field : 'lastUpdateUserId',
            sortable : true
        }, {
            field : 'lastUpdateDate',
            sortable : true
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
    var targetUrl = 'platformz029902/';
    targetUrl = targetUrl + '?companyId=' + tableRowInfo.companyId;
    window.top.layer.open({
        title : '编辑公司',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
}