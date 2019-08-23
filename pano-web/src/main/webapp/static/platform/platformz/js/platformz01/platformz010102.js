// 管理编辑

$(function() {

    // 角色添加处理
    $('#platformz010102Btn_addRole').click(function() {
        window.top.layer.open({
            title : '角色检索',
            type : 2,
            area : [ '80%', '90%' ], // 宽高
            content : [ getMemberContextPath('platformz010203/' + window.name), 'yes' ]
        });
    });

    // 角色删除处理
    $('#platformz010102Btn_deleteRole').click(function() {
        $("#select_member_role option:selected").each(function() {
            $(this).remove();
        })
    });

    // 更新
    $("#btn_update").click(function() {

        // 选中所有的角色，才能提交到后台
        $("#select_member_role option").each(function() {
            $(this).attr("selected", "selected");
        })

        // 统一移除popover提示消息层
        $("#platformz010102Form").validate().destroy();
        // validate初始化
        $("#platformz010102Form").validate();
        // $("#txt_bankNameCn").attr('required', '');
        // $("#txt_bankNameEn").attr('required', '');
        // $("#txt_bankCode").attr('required', '');
        // 有验证错误时返回
        if (!$("#platformz010102Form").valid()) {
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
            var targetUrl = 'platformz010102/doUpdate';
            var formData = form2js($("#platformz010102Form")[0]);
            $.ajax({
                type : 'post',
                traditional : true,
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

// 角色检索返回方法
function setCisz010203ReturnObject(returnObject) {
    jQuery.each(returnObject, function(i, item) {
        if (!CommonUtilJs.isExistOption('select_member_role', item.roleId)) {
            var $option = $('<option value="' + item.roleId + '">' + item.roleName + '</option>');
            $("#select_member_role").append($option);
        }
    });
}