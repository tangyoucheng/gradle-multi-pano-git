// 
$(function() {
    // 鼠标焦点事件
    window.focus();
    // 更新
    $("#btn_update").click(function() {
        // 统一移除popover提示消息层
        $("#platformz030302Form").validate().destroy();
        // validate初始化
        $("#platformz030302Form").validate();
        // $("#txt_bankNameCn").attr('required', '');
        // $("#txt_bankNameEn").attr('required', '');
        // $("#txt_bankCode").attr('required', '');
        // 有验证错误时返回
        if (!$("#platformz030302Form").valid()) {
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
            var targetUrl = 'platformz030302/doUpdate';
            var formData = form2js($("#platformz030302Form")[0]);
            $.ajax({
                type : 'post',
                traditional : true,
                data : formData,
                dataType : 'json',
                url : getMemberContextPath(targetUrl),
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
    // 关闭
    $("#btn_close").click(function() {
        var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
        parent.layer.close(index); // 再执行关闭
    });
});

//日期处理
function dispFlagFormat(inputObject) {
    var inputValue = accounting.formatNumber($(inputObject).val(), 0, '')
    if (inputValue == 0.00) {
        $(inputObject).val(null);
    } else {
        $(inputObject).val(inputValue);
    }
}