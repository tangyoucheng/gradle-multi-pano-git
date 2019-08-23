// 公司新增

$(function() {
    // 新建
    $("#btn_entry").click(function() {
        // 统一移除popover提示消息层
        $("#platform019901Form").validate().destroy();
        // validate初始化
        $("#platform019901Form").validate();
        // $("#txt_bankNameCn").attr('required', '');
        // $("#txt_bankNameEn").attr('required', '');
        // $("#txt_bankCode").attr('required', '');
        // 有验证错误时返回
        if (!$("#platform019901Form").valid()) {
            return;
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在提交当前数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);

            var targetUrl = 'platform019901/doEntry';
            var formData = form2js($("#platform019901Form")[0]);
            $.ajax({
                type : 'post',
                data : formData,
                dataType : 'json',
                url : getAdminContextPath(targetUrl),
                success : function(result) {
                    CommonUtilJs.processAjaxSuccessAfter(result);
                    // 关闭自页面
                    var index = parent.layer.getFrameIndex(window.name);
                    window.top.layer.close(index);
                }
            })
        }, function() {// 取消操作

        });
    });

});