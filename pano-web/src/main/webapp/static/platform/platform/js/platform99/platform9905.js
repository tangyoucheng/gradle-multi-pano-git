// 定时任务
var tableJobList;
$(function() {
    tableJobList = $('#table-jobList');

    // bootstrapTable初始化
    tableJobList.bootstrapTable({
    // locale : 'zh-CN'
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = tableJobList.bootstrapTable('getSelections').length;
    $('#platform9905Btn_delete').prop('disabled', !selectionsLength);
    $('#platform9905Btn_resume').prop('disabled', !selectionsLength);
    $('#platform9905Btn_pause').prop('disabled', !selectionsLength);
    $('#platform9905Btn_run').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table';
    tableJobList.on(jqueryEvent, function() {
        var selectionsLength = tableJobList.bootstrapTable('getSelections').length;
        $('#platform9905Btn_delete').prop('disabled', !selectionsLength);
        $('#platform9905Btn_resume').prop('disabled', !selectionsLength);
        $('#platform9905Btn_pause').prop('disabled', !selectionsLength);
        $('#platform9905Btn_run').prop('disabled', !selectionsLength);
    });

    // 检索
    $("#platform9905Btn_query").click(function() {
        searchData();
    });

    if (window.location.protocol.startWith('http')) {
        searchData();
    }
    

    // 新建
    $("#platform9905Btn_new").click(function() {
        var targetUrl = 'platform990501/';
        window.top.layer.open({
            title : '定时任务新建',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getAdminContextPath(targetUrl), 'yes' ],
            end : function() {
                searchData();
            }
        });
    });

    // 删除
    $("#platform9905Btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的任务，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platform9905FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-jobList").bootstrapTable('getSelections'), function(row) {
                return row.jobId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getAdminContextPath('platform9905/doDelete'),
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
    
    // 恢复
    $("#platform9905Btn_resume").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会恢复选中的任务，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platform9905FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-jobList").bootstrapTable('getSelections'), function(row) {
                return row.jobId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getAdminContextPath('platform9905/doResume'),
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
    
    // 暂停
    $("#platform9905Btn_pause").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会暂停选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platform9905FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-jobList").bootstrapTable('getSelections'), function(row) {
                return row.jobId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getAdminContextPath('platform9905/doPause'),
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
    
    // 执行一次
    $("#platform9905Btn_run").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会立即执行一次选中的任务，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#platform9905FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($("#table-jobList").bootstrapTable('getSelections'), function(row) {
                return row.jobId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getAdminContextPath('platform9905/doRun'),
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
    tableJobList.bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    tableJobList.bootstrapTable({
        url : getAdminContextPath('platform9905/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'jobName',// 初始化的时候排序的字段
        sortOrder : "desc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#platform9905FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "jobId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'jobId'
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
            field : 'cronExpression'
        }, {
            field : 'triggerRemark'
        }, {
            field : 'priority'
        }, {
            field : 'jobStatus'
        }, {
            field : 'jobStatusName'
        }, {
            field : 'createDate'
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

// 定时任务编辑
function doEdit(tableRowInfo) {
     var targetUrl = 'platform990501/';
     targetUrl = targetUrl + '?jobId=' + tableRowInfo.jobId;
     window.top.layer.open({
         title : '定时任务编辑',
         type : 2,
         closeBtn : 1, // 显示关闭按钮
         area : [ '90%', '100%' ], // 宽高
         content : [ getAdminContextPath(targetUrl), 'yes' ],
         end : function() {
             searchData();
         }
     });
}