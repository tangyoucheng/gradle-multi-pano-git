// 触发器设定 指定时间

$(function() {
    // checkbox初始化
    $("[name='isYear']").bootstrapSwitch();
    $("[name='isMonth']").bootstrapSwitch();
    $("[name='isDay']").bootstrapSwitch();
    $("[name='dayOrWeek']").bootstrapSwitch();
    $("[name='isHour']").bootstrapSwitch();
    $("[name='isMinute']").bootstrapSwitch();

    // 指定年
    $("[name='isYear']").on(
        'switchChange.bootstrapSwitch',
        function(event, state) {
            if ($("[name='isYear']").bootstrapSwitch('state')) {
                // 显示入力框
                $('#for_years').show();
            } else {
                // 隐藏入力框
                $('#for_years').hide();
                $('#txt_years').val('');
            }
        });
    // 指定月
    $("[name='isMonth']").on(
        'switchChange.bootstrapSwitch',
        function(event, state) {
            if ($("[name='isMonth']").bootstrapSwitch('state')) {
                // 显示入力框
                $('#for_months').show();
            } else {
                // 隐藏入力框
                $('#for_months').hide();
                $('#txt_months').val('');
            }
        });
    // 指定日
    $("[name='isDay']").on(
        'switchChange.bootstrapSwitch',
        function(event, state) {
            if ($("[name='isDay']").bootstrapSwitch('state')) {
                // 显示入力框
                $('#for_dayOrWeek').show();
            } else {
                // 隐藏入力框
                $('#for_dayOrWeek').hide();
                $("#for_daysOfWeek input:checkbox:checked").each(function(id, item) {
                    $(this)[0].checked = false;
                });
                $('#txt_daysOfMonth').val('');
            }
        });
    // 日期或星期
    $("[name='dayOrWeek']").on(
        'switchChange.bootstrapSwitch',
        function(event, state) {
            if ($("[name='dayOrWeek']").bootstrapSwitch('state')) {
                // 显示入力框
                $('#for_daysOfMonth').show();
                // 隐藏入力框
                $('#for_daysOfWeek').hide();
                $("#for_daysOfWeek input:checkbox:checked").each(function(id, item) {
                    $(this)[0].checked = false;
                });
            } else {
                // 显示入力框
                $('#for_daysOfWeek').show();
                // 隐藏入力框
                $('#for_daysOfMonth').hide();
                $('#txt_daysOfMonth').val('');
            }
        });
    // 指定时
    $("[name='isHour']").on(
        'switchChange.bootstrapSwitch',
        function(event, state) {
            if ($("[name='isHour']").bootstrapSwitch('state')) {
                // 显示入力框
                $('#for_hours').show();
            } else {
                // 隐藏入力框
                $('#for_hours').hide();
                $('#txt_hours').val('');
            }
        });
    // 指定分
    $("[name='isMinute']").on(
        'switchChange.bootstrapSwitch',
        function(event, state) {
            if ($("[name='isMinute']").bootstrapSwitch('state')) {
                // 显示入力框
                $('#for_minutes').show();
            } else {
                // 隐藏入力框
                $('#for_minutes').hide();
                $('#txt_minutes').val('');
            }
        });

    // 保存
    $("#btn_entry").click(function() {
        
        var triggerJson = {};
        var triggerRemark = '';
        var years = $('#txt_years').val();// 年
        var months = $('#txt_months').val();// 月
        var daysOfMonth = $('#txt_daysOfMonth').val();// 日期
        var daysOfWeek = [];// 星期
        var daysOfWeekText = [];
        $("#for_daysOfWeek input:checkbox:checked").each(function(id, item) {
            daysOfWeek.push($(this).val());
            daysOfWeekText.push($($(this)[0].nextElementSibling)[0].innerText);
        });
        var hours = $('#txt_hours').val();// 时
        var minutes = $('#txt_minutes').val();// 分
        
        var checkErrors = [];
        if (years) {
            triggerJson['years'] = years.split(",");
            triggerRemark = triggerRemark + '[' + years + ']年';
            jQuery.each(triggerJson['years'], function(i, item) {
                if (!isInt(item)) {
                    checkErrors.push('请在年中输入整数。');
                    return false;
                }
            });
        } else {
            triggerJson['years'] = [];
            triggerRemark = triggerRemark + '每年';
        }
        if (months) {
            triggerJson['months'] = months.split(",");
            triggerRemark = triggerRemark + '[' + months + ']月';
            jQuery.each(triggerJson['months'], function(i, item) {
                if (!isInt(item) || item < 1 || item > 12) {
                    checkErrors.push('请在月中输入1-12的范围之间的整数。');
                    return false;
                }
            });
        } else {
            triggerJson['months'] = [];
            triggerRemark = triggerRemark + '每月';
        }
        if (daysOfMonth) {
            triggerJson['daysOfMonth'] = daysOfMonth.split(",");
            triggerJson['daysOfWeek'] = [];
            triggerRemark = triggerRemark + '[' + daysOfMonth + ']日';
            jQuery.each(triggerJson['daysOfMonth'], function(i, item) {
                if (!isInt(item) || item < 1 || item > 31) {
                    checkErrors.push('请在日中输入1-31的范围之间的整数。');
                    return false;
                }
            });
        } else if(daysOfWeek != null && daysOfWeek.length > 0) {
            triggerJson['daysOfMonth'] = [];
            triggerJson['daysOfWeek'] = daysOfWeek;
            triggerRemark = triggerRemark + '[' + daysOfWeekText + ']';
        } else {
            triggerJson['daysOfMonth'] = [];
            triggerJson['daysOfWeek'] = [];
            triggerRemark = triggerRemark + '每天';
        }
        if (hours) {
            triggerJson['hours'] = hours.split(",");
            triggerRemark = triggerRemark + '[' + hours + ']时';
            jQuery.each(triggerJson['hours'], function(i, item) {
                if (!isInt(item) || item < 0 || item > 23) {
                    checkErrors.push('请在时中输入0-23的范围之间的整数。');
                    return false;
                }
            });
        } else {
            triggerJson['hours'] = [];
            triggerRemark = triggerRemark + '每时';
        }
        if (minutes) {
            triggerJson['minutes'] = minutes.split(",");
            triggerRemark = triggerRemark + '[' + minutes + ']分';
            jQuery.each(triggerJson['minutes'], function(i, item) {
                if (!isInt(item) || item < 0 || item > 59) {
                    checkErrors.push('请在分中输入0-59的范围之间的整数。');
                    return false;
                }
            });
        } else {
            triggerJson['minutes'] = [];
            triggerRemark = triggerRemark + '每分';
        }
        triggerRemark = triggerRemark + ' 执行一次';
        
        // 显示信息
        if (checkErrors.length > 0) {
            window.top.showErrorMessage(checkErrors, false, 3);
            return;
        }
        
        var returnObject = {};
        returnObject['triggerJson'] = triggerJson;
        returnObject['triggerRemark'] = triggerRemark;
        // 选中的值传给前页面
        window.parent.frames[$('#returnTargetIframe').val()].setShz99050101ReturnObject(returnObject);
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