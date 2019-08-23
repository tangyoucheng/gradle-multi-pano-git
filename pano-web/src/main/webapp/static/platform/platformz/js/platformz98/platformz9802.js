$(function() {
    // 提交
    $("#Platformz9802Btn_submit").click(function() {
        var targetUrl = 'Platformz9804/';
        window.top.layer.open({
            title : '新建',
            type : 2,
            closeBtn : 1, // 显示关闭按钮
            area : [ '600px', '200px' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            end : function() {
            }
        });
    });
});