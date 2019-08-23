// 公司用戶编辑

$(function() {
    // 公司社区添加处理
    $('#platformz020102Btn_addDepartment').click(function() {
        window.top.layer.open({
            title : '公司部门检索',
            type : 2,
            area : [ '80%', '90%' ], // 宽高
            content : [ getMemberContextPath('platformz019903/' + window.name), 'yes' ]
        });
    });

    // 公司社区删除处理
    $('#platformz020102Btn_deleteDepartment').click(function() {
        $("#select_department option:selected").each(function() {
            $(this).remove();
        })
    });
    // 公司角色添加处理
    $('#platformz020102Btn_addRole').click(function() {
        window.top.layer.open({
            title : '角色检索',
            type : 2,
            area : [ '80%', '90%' ], // 宽高
            content : [ getMemberContextPath('platformz020203/' + window.name), 'yes' ]
        });
    });

    // 公司角色删除处理
    $('#platformz020102Btn_deleteRole').click(function() {
        $("#select_role option:selected").each(function() {
            $(this).remove();
        })
    });

    // 更新
    $("#btn_update").click(function() {

        // 选中所有的角色，才能提交到后台
        $("#select_role option").each(function() {
            $(this).attr("selected", "selected");
        })
        // 选中所有的社区，才能提交到后台
        $("#select_department option").each(function() {
            $(this).attr("selected", "selected");
        })

        // 统一移除popover提示消息层
        $("#platformz020102Form").validate().destroy();
        // validate初始化
        $("#platformz020102Form").validate();
        // $("#txt_bankNameCn").attr('required', '');
        // $("#txt_bankNameEn").attr('required', '');
        // $("#txt_bankCode").attr('required', '');
        // 有验证错误时返回
        if (!$("#platformz020102Form").valid()) {
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
            var targetUrl = 'platformz020102/doUpdate';
            var formData = form2js($("#platformz020102Form")[0]);
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

});

// 公司角色检索返回方法
function setCisz020203ReturnObject(returnObject) {
    jQuery.each(returnObject, function(i, item) {
        if (!CommonUtilJs.isExistOption('select_role', item.roleId)) {
            var $option = $('<option value="' + item.roleId + '">' + item.roleName + '</option>');
            $("#select_role").append($option);
        }
    });
}
// 公司社区检索返回方法
function setCisz019903ReturnObject(returnObject) {
    jQuery.each(returnObject, function(i, item) {
        if (!CommonUtilJs.isExistOption('select_department', item.departmentId)) {
            var $option = $('<option value="' + item.departmentId + '">' + item.departmentName + '</option>');
            $("#select_department").append($option);
        }
    });
}