// 
$(function() {
    var tableServerTypeIdInfo = $('#table-serverTypeIdInfo');

    // bootstrapTable初始化
    tableServerTypeIdInfo.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableServerTypeIdInfo.bootstrapTable('getSelections').length;
    $('#platformz0301Btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableServerTypeIdInfo.on(jqueryEvent, function() {
        var selectionsLength = tableServerTypeIdInfo.bootstrapTable('getSelections').length;
        $('#platformz0301Btn_delete').prop('disabled', !selectionsLength);
    });
    // 新建
    $("#platformz0301Btn_new").click(function() {
        var targetUrl = 'platformz030101/';
        window.top.layer.open({
            title : '新建',
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
    $("#platformz0301Btn_delete").click(function() {
        // 选中表格数据ID
        var uniqueKeyArray = new Array();
        uniqueKeyArray = $.map($("#table-serverTypeIdInfo").bootstrapTable('getSelections'), function(row) {
            // 传入后台的唯一识别ID
            return row.serverTypeId;
        });
        if (uniqueKeyArray != null && uniqueKeyArray != "") {
            // 询问框
            var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
                icon : 3,
                title : '提示信息',
                btn : [ '确认', '取消' ]
            // 按钮
            }, function() { // 确认操作
                // 表单数据转换成JS对象
                var ajaxSubmitFormData = form2js($("#platformz0301FormAjaxSubmit")[0]);

                ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

                window.top.layer.close(currentConfirmIndex);

                $.ajax({
                    type : 'post',
                    traditional : true,
                    data : ajaxSubmitFormData,
                    dataType : 'json',
                    url : getMemberContextPath('platformz0301/doDelete'),
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
        } else {

        }

    });
    searchData();
    // 检索
    $("#platformz0301Btn_query").click(function() {
        // debugger; 检测
        searchData();
    });

    // 共通 高级查询
    $(".btn-search").click(function() {
        $('.full-search').slideToggle();
        $('.fa-angle-double-down').toggle();
        $('.fa-angle-double-up').toggle();
    });

    // 同时绑定多个 layui 日期控件
    lay('.test-item').each(function() {
        laydate.render({
            elem : this,
            trigger : 'click'
        });
    });
});

function searchData() {
    // 先销毁表格
    $('#table-serverTypeIdInfo').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-serverTypeIdInfo').bootstrapTable({
        url : getMemberContextPath('platformz0301/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'dispFlag',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式 正序 asc 倒序 desc
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platformz0301FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "serverTypeId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        },{
            field : 'serverTypeId',
        }, {
            field : 'dispFlag'
        }, {
            field : 'value'
        }, {
            field : 'remark'
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
                showData = showData + ' class="cis-text-primary font-14 p-2 row-edit row-edit"';
                showData = showData + '>';
                showData = showData + '编辑';
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
    var targetUrl = 'platformz030102/';
    targetUrl = targetUrl + '?serverTypeId=' + tableRowInfo.serverTypeId;
    window.top.layer.open({
        title : '编辑',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
}