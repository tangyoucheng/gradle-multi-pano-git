// 触发器设定 重复执行

$(function() {

    $("[name='isCount']").bootstrapSwitch();

    // 指定次数
    $("[name='isCount']").on('switchChange.bootstrapSwitch', function(event, state) {
        if ($("[name='isCount']").bootstrapSwitch('state')) {
            // 显示入力框
            $('#for_count').show();
        } else {
            // 隐藏入力框
            $('#for_count').hide();
            $('#txt_count').val('');
        }
    });

    // 选择日期
    laydate.render({
        elem : '#txt_startDate',
        type : 'datetime',
        trigger : 'click' // 采用click弹出
    });

    // 保存
    $("#btn_entry").click(function() {

        // 统一移除popover提示消息层
        $("#Platformz99060101Form").validate().destroy();
        // validate初始化
        $("#Platformz99060101Form").validate();
        $("#repeatInterval").attr('required', '');
        $("#txt_startDate").attr('required', '');

        // 有验证错误时返回
        if (!$("#Platformz99060101Form").valid()) {
            return;
        }

        var triggerJson = {};
        var repeatCount = $('#txt_repeatCount').val();
        var repeatInterval = $('#txt_repeatInterval').val();
        var startDate = $('#txt_startDate').val();

        var triggerRemark = "";
        triggerRemark = triggerRemark + startDate + "开始";
        var checkErrors = [];
        // 重复间隔
        if (!isInt(repeatInterval) || repeatInterval < 1) {
            checkErrors.push('请在重复间隔中输入正整数。');
        }
        triggerRemark = triggerRemark + "，每隔" + repeatInterval + "秒执行一次"
        // 重复次数
        if (repeatCount) {
            if (!isInt(repeatCount) || repeatCount < 1) {
                checkErrors.push('请在重复次数中输入正整数。');
            }
            triggerRemark = triggerRemark + "，共执行" + repeatCount + "次。"
        } else {
            triggerRemark = triggerRemark + "，不限次数。"
        }

        // 显示信息
        if (checkErrors.length > 0) {
            window.top.showErrorMessage(checkErrors, false, 3);
            return;
        }

        triggerJson['repeatCount'] = repeatCount;
        triggerJson['repeatInterval'] = repeatInterval;
        triggerJson['startDate'] = startDate;

        var returnObject = {};
        returnObject['triggerJson'] = triggerJson;
        returnObject['triggerRemark'] = triggerRemark;
        // 选中的值传给前页面
        window.parent.frames[$('#returnTargetIframe').val()].setShz99060101ReturnObject(returnObject);
        // 关闭自页面
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);
    });
});

// 判断整数
function isInt(str) {
    var r = new RegExp('^-?\\d+$');
    return r.test(str);
}