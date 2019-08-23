// 操作历史共通-查看
$(function() {
    // 关闭
    $("#operateLogView_close").click(function() {
        var index = parent.layer.getFrameIndex(window.name);
        window.top.layer.close(index);
    });
});
