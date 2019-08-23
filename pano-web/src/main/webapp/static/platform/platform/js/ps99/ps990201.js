//
// 会员密码变更页面
//

$(function() {
    $('#btn_update').click(function() {
        // 统一移除popover提示消息层
        $("#ps990201Form").validate().destroy();
        // validate初始化
        $("#ps990201Form").validate();
        // $("#txt_bankNameCn").attr('required', '');
        // $("#txt_bankNameEn").attr('required', '');
        // $("#txt_bankCode").attr('required', '');
        // 有验证错误时返回
        if (!$("#ps990201Form").valid()) {
            return;
        }
        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在修改密码，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);
            var formData = form2js($("#ps990201Form")[0]);
            $.ajax({
                url : getMemberContextPath('ps99/ps990201/doUpdate'),
                type : "post",
                dataType : "json",
                data : formData,
                success : function(result) {
                    CommonUtilJs.processAjaxSuccessAfter(result)
                }
            // ,
            // error : function(result) {
            // window.top.layuiRemoveLoading();
            // window.top.layer.alert(result.status);
            // }
            });
        }, function() {// 取消操作

        });
    });
});
