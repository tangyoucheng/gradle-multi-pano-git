// 菜单管理

$(function() {

    $('#Btn_save').click(function() {
        var zTree = $.fn.zTree.getZTreeObj("zTree");
        var keyArray = [];
        keyArray.push('menuId');
        $('#menuListJson').val(JSON.stringify(zTree.getCheckedNodes(), keyArray));

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正登录当前页面的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);
            // 确认操作

            $.ajax({
                url : getMemberContextPath('/platformz0103/doEntry'),
                type : "post",
                dataType : "json",
                data : form2js($("#platformz0103Form")[0]),
                success : function(result) {
                    if (result.success) {
                        window.top.layer.alert(result.msg, function(index) {
                            // 关闭当前层
                            window.top.layer.close(index);
                        });
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

    if ($("#roleList tr:eq(1)").length > 0) {
        $("#roleList tr:eq(1)").click();
    }

});

var setting = {
    check : {
        enable : true
    },
    data : {
        simpleData : {
            enable : true
        }
    }
};

// zTree
function initZtree(zNodes) {
    $.fn.zTree.init($("#zTree"), setting, zNodes);
    setCheck();
    $("#py").bind("change", setCheck);
    $("#sy").bind("change", setCheck);
    $("#pn").bind("change", setCheck);
    $("#sn").bind("change", setCheck);
}

// setCheck
function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("zTree");
    var type = {
        "Y" : "ps",
        "N" : "ps"
    };
    zTree.setting.check.chkboxType = type;
}

// 设定被选行的颜色
function setTrColor(_trId, _selectedRoleCode) {
    $('#roleCode').val(_selectedRoleCode);
    $('#roleList tbody td').each(function() {
        $(this).css("background-color", "");
        $(this).css("color", "#000000");
    })
    $("#" + _trId + ' td').css("background-color", "#28a745");
    $("#" + _trId + ' td').css("color", "#FFFFFF");
    searchRoleMenu(_selectedRoleCode);
}

// 菜单权限检索
function searchRoleMenu(_selectedRoleCode) {
    $.ajax({
        url : getMemberContextPath('/platformz0103/doSearch'),
        type : "post",
        dataType : "json",
        data : {
            "roleCode" : _selectedRoleCode
        },
        success : function(result) {
            if (result.success) {
                initZtree(result.obj);
            }
        }
    // ,
    // error : function(result) {
    // window.top.layuiRemoveLoading();
    // window.top.layer.alert(result.status);
    // }
    });
}
