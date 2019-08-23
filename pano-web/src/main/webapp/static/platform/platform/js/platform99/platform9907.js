// 任务日志
var tableJobLogList;
$(function() {
    tableJobLogList = $('#table-jobLogList');

    // bootstrapTable初始化
    tableJobLogList.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableJobLogList.bootstrapTable('getSelections').length;
    $('#platform9907Btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableJobLogList.on(jqueryEvent, function() {
        var selectionsLength = tableJobLogList.bootstrapTable('getSelections').length;
        $('#platform9907Btn_delete').prop('disabled', !selectionsLength);
    });

    // 检索
    $("#platform9907Btn_query").click(function() {
        searchData();
    });

    if (window.location.protocol.startWith('http')) {
        searchData();
    }
    
    // 删除
    $("#platform9907Btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的日志，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platform9907FormAjaxSubmit")[0]);

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getAdminContextPath('platform9907/doClean'),
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
    $("#platform9907Btn_clean").click(function() {
        // 询问框
           var currentConfirmIndex = window.top.layer.confirm('本操作会清空所有日志，是否继续？', {
               icon : 3,
               title : '提示信息',
               btn : [ '确认', '取消' ]
           // 按钮
           }, function() { // 确认操作
               // 表单数据转换成JS对象
               var ajaxSubmitFormData = form2js($("#platform9907FormAjaxSubmit")[0]);
               var uniqueKeyArray = new Array();
               uniqueKeyArray = $.map($("#table-jobLogList").bootstrapTable('getSelections'), function(row) {
                   return row.jobId;
               });
               ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

               window.top.layer.close(currentConfirmIndex);

               $.ajax({
                   type : 'post',
                   traditional : true,
                   data : ajaxSubmitFormData,
                   dataType : 'json',
                   url : getAdminContextPath('platform9907/doDelete'),
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


});

function searchData() {
    // 先销毁表格
    tableJobLogList.bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    tableJobLogList.bootstrapTable({
        url : getAdminContextPath('platform9907/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'jobName',// 初始化的时候排序的字段
        sortOrder : "desc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platform9907FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "jobLogId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'jobLogId'
        }, {
            field : 'jobName',
            sortable : true
        }, {
            field : 'jobGroup'
        }, {
            field : 'methodName'
        }, {
            field : 'methodParams'
        }, {
            field : 'jobMessage'
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
            field : 'createTime'
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 日志详情
function doEdit(tableRowInfo) {
     var targetUrl = 'platform990701/';
     targetUrl = targetUrl + '?jobId=' + tableRowInfo.jobId;
     window.top.layer.open({
         title : '日志详情',
         type : 2,
         closeBtn : 1, // 显示关闭按钮
         area : [ '90%', '100%' ], // 宽高
         content : [ getAdminContextPath(targetUrl), 'yes' ],
         end : function() {
             searchData();
         }
     });
}