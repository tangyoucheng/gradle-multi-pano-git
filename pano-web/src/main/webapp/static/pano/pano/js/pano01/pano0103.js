//================================================
//展览编辑和删除
//================================================
$(document).ready(function() {

    // 日期
    laydate.render({
        elem : '#txt_expositionStartDate',
        type : 'date',
        // range : '至',
        format : 'yyyy-MM-dd',
        trigger : 'click' // 采用click弹出
    });

    laydate.render({
        elem : '#txt_expositionEndDate',
        type : 'date',
        format : 'yyyy-MM-dd',
        trigger : 'click' // 采用click弹出
    });

    // 删除成功后的按钮
    $('#button-delete').click(function() {
        $("#pageSubmitResult").val('deleteSuccess');
        $("#success-form").submit();
    });

    // 如果展览有设置展览类型，则显示展览类型栏
    var radioboxSelected_2 = $("input[name='expositionType_show']:checked").val();
    if (radioboxSelected_2 != '' && radioboxSelected_2 != null) {
        $('#expoTypeTr').show();
    } else {
        $('#expoTypeTr').hide();
    }

    // 展览登录处理
    $("#btn_entry").click(function() {

        // 统一移除popover提示消息层
        $("#pano0103FormAdd").validate().destroy();
        // validate初始化
        $("#pano0103FormAdd").validate();
        // 展览时间的妥当性检查
        $("#txt_expositionEndDate").rules('add', {
            greaterEqual : $("#txt_expositionStartDate").val()
        });
        if (!$("#pano0103FormAdd").valid()) {
            return;
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正登录当前页面的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);

            // 确认操作
            var ajaxFormData = {};
            ajaxFormData = $.extend({}, ajaxFormData, form2js($("#pano0103FormAdd")[0]));

            $.ajax({
                url : getMemberContextPath('pano0103/doUpdate'),
                type : "post",
                dataType : "json",
                data : ajaxFormData,
                success : function(result) {
                    CommonUtilJs.processAjaxSuccessAfter(result);
                }
            // ,
            // error : function(result) {
            // window.top.layuiRemoveLoading();
            // window.top.layer.alert(result.status);
            // }
            });
        }, function() {
            // 取消操作
        });

    });

    // 删除展览
    $("#btn_delete").click(function() {

        // 统一移除popover提示消息层
        $("#pano0103FormAdd").validate().destroy();
        // validate初始化
        $("#pano0103FormAdd").validate();
        // 展览时间的妥当性检查
        $("#txt_expositionEndDate").rules('add', {
            greaterEqual : $("#txt_expositionStartDate").val()
        });
        if (!$("#pano0103FormAdd").valid()) {
            return;
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正登录当前页面的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);

            // 确认操作
            var ajaxFormData = {};
            ajaxFormData = $.extend({}, ajaxFormData, form2js($("#pano0103FormAdd")[0]));

            $.ajax({
                url : getMemberContextPath('pano0103/doDelete'),
                type : "post",
                dataType : "json",
                data : ajaxFormData,
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // 关闭自页面
                        var index = parent.layer.getFrameIndex(window.name);
                        window.top.layer.close(index);
                    }
                }
            // ,
            // error : function(result) {
            // window.top.layuiRemoveLoading();
            // window.top.layer.alert(result.status);
            // }
            });
        }, function() {
            // 取消操作
        });
    });
});
