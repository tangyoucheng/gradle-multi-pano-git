/**
 * 热点大小,浮动层大小，场景工具条编辑
 */
$(document).ready(function() {

    // bootstrapTable初始化
    $("#buttons-list").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });
    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table ';
    jqueryEvent = jqueryEvent + 'check-all.bs.table uncheck-all.bs.table';
    $('#buttons-list').on(jqueryEvent, function() {
        reloadButtonsBar_pano0209();
    });

    // 检索全景处理
    searchData();

});

// 检索场景信息
function searchData() {
    
    var tableDatas;
    if($('#buttonsInfoJson').val().length > 0){
        tableDatas = JSON.parse($('#buttonsInfoJson').val());
    } else {
        return;
    }
    
    // 先销毁表格
    $('#buttons-list').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#buttons-list').bootstrapTable({
        // url : getMemberContextPath('pano0104/doSearchPano'), // 请求后台的URL（*）
        // method : 'get', // 请求方式（*）
        data : tableDatas,
        toolbar : undefined, // 工具按钮用哪个容器
        pagination : false, // 是否显示分页（*）
        sortable : false, // 是否启用排序
        sortName : 'buttonName_CN',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        uniqueId : "buttonName", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'rowNumber',
            width : 50,
            formatter : function(value, tableRowInfo, index) {
                return index + 1;
            }
        }, {
            field : 'buttonName_CN'
        }, {
            field : 'buttonName'
        }, ],
        onLoadSuccess : function(data) {
        }
    });
};

