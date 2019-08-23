//================================================
//展览登记画面加载时的处理
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

    // 展览类型勾选框
    $("#expoTypeFlag").click(function() {
        $("input[name='expositionType']").attr("disabled", !($("#expoTypeFlag").attr("checked")));
        if ($("#expoTypeFlag").attr("checked")) {
            $('#companyName').show();
        } else {
            $("input[name='expositionType']").attr("checked", false);
            $('#companyName').hide();
        }
    });

    // 展览登录处理
    $("#btn_entry").click(function() {

        // 统一移除popover提示消息层
        $("#pano0101FormAdd").validate().destroy();
        // validate初始化
        $("#pano0101FormAdd").validate();
        // 展览时间的妥当性检查
        $("#txt_expositionEndDate").rules('add', {
            greaterEqual : $("#txt_expositionStartDate").val()
        });
        if (!$("#pano0101FormAdd").valid()) {
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
            ajaxFormData = $.extend({}, ajaxFormData, form2js($("#pano0101FormAdd")[0]));

            $.ajax({
                url : getMemberContextPath('pano0101/doEntry'),
                type : "post",
                dataType : "json",
                data : ajaxFormData,
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        location.reload(true);
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

//        var _expositionType = $("input[name='expositionType']:checked").val();
//        if ($("#expoTypeFlag").attr("checked")) {
//            if (_expositionType == '' || _expositionType == null) {
//                imuiAlert("请选择展览类型");
//                return false;
//            }
//        }

    });
});
