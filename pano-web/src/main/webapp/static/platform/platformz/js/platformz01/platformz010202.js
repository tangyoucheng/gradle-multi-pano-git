// 角色编辑

$(function() {
    // 更新
    $("#btn_update").click(function() {
        // 统一移除popover提示消息层
        $("#platformz010202Form").validate().destroy();
        // validate初始化
        $("#platformz010202Form").validate();
        // $("#txt_bankNameCn").attr('required', '');
        // $("#txt_bankNameEn").attr('required', '');
        // $("#txt_bankCode").attr('required', '');
        // 有验证错误时返回
        if (!$("#platformz010202Form").valid()) {
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
            var targetUrl = 'platformz010202/doUpdate';
            var formData = form2js($("#platformz010202Form")[0]);
            $.ajax({
                type : 'post',
                data : formData,
                dataType : 'json',
                url : getMemberContextPath(targetUrl),
                success : function(result) {
                    CommonUtilJs.processAjaxSuccessAfter(result);
                }
            })
        }, function() {// 取消操作

        });
    });

});