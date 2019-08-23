// 新建定时任务

$(function() {
    // 新建
    $("#btn_entry").click(function() {

        // 统一移除popover提示消息层
        $("#platform990501Form").validate().destroy();
        // validate初始化
        $("#platform990501Form").validate();
        $("#txt_jobName").attr('required', '');
        $("#txt_methodName").attr('required', '');
        $("#txt_triggerRemark").attr('required', '');
        $("#txt_priority").attr('required', '');

        // 有验证错误时返回
        if (!$("#platform990501Form").valid()) {
            return;
        }
        
        var priority = $("#txt_priority").val();
        if (!isInt(priority) || priority < 1 || priority > 10) {
            window.top.showErrorMessage('请在优先级中输入1-10的范围之间的整数。', false, 3)
            return false;
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存定时任务，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);

            var targetUrl = 'platform990501/doEntry';
            var formData = form2js($("#platform990501Form")[0]);
            $.ajax({
                type : 'post',
                traditional : true,
                data : formData,
                dataType : 'json',
                url : getAdminContextPath(targetUrl),
                success : function(result) {
                    CommonUtilJs.processAjaxSuccessAfter(result);
                    if (result.success) {
                        // 关闭自页面
                        var index = parent.layer.getFrameIndex(window.name);
                        window.top.layer.close(index);
                    }
                }
            })

        }, function() {// 取消操作

        });
    });

    // 触发器设定 日期设定
    $("#platform990501_cron").click(function() {
        var targetUrl = 'platform99050101/';
        targetUrl = targetUrl + '?returnTargetIframe=' + window.name + '&jobId=' + $('#txt_jobId').val();
        window.top.layer.open({
            title : '触发器设定',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getAdminContextPath(targetUrl), 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });
    
    // 触发器设定 重复设定
    $("#platform990501_repeat").click(function() {
        var targetUrl = 'platform99050102/';
        targetUrl = targetUrl + '?returnTargetIframe=' + window.name + '&jobId=' + $('#txt_jobId').val();
        window.top.layer.open({
            title : '触发器设定',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getAdminContextPath(targetUrl), 'yes' ],
            end : function() {
                // searchData();
            }
        });
    });
});

// 定时任务触发器设定返回方法
function setShz99050101ReturnObject(returnObject) {
    $('#txt_triggerJson').val(JSON.stringify(returnObject.triggerJson));
    $('#txt_triggerRemark').val(returnObject.triggerRemark);
}

// 重复任务触发器设定返回方法
function setShz99050102ReturnObject(returnObject) {
    $('#txt_triggerJson').val(JSON.stringify(returnObject.triggerJson));
    $('#txt_triggerRemark').val(returnObject.triggerRemark);
}

//判断整数
function isInt(str) {
    var r = new RegExp('^-?\\d+$');
    return r.test(str);
}