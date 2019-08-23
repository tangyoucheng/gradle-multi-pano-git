$(document).ready(function() {

    // bootstrapTable初始化
    $("#table-panorama-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = $('#table-panorama-info').bootstrapTable('getSelections').length;
    $('#btn_choose').prop('disabled', selectionsLength != 1);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table ';
    jqueryEvent = jqueryEvent + 'check-all.bs.table uncheck-all.bs.table';
    $('#table-panorama-info').on(jqueryEvent, function() {
        var selectionsLength = $('#table-panorama-info').bootstrapTable('getSelections').length;
        $('#btn_choose').prop('disabled', selectionsLength != 1);
    });

    // 检索全景处理
    searchData();

    // 保存处理
    $('#btn_update').click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正登录当前页面的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);

            // 确认操作
            // var ajaxFormData = {};
            // ajaxFormData = $.extend({}, ajaxFormData,
            // form2js($("#pano0301FormAdd")[0]));
            var ajaxSubmitFormData = form2js($("#pano0308FormAjaxSubmit")[0]);

            // 选中的场景
            var thumbSelectedList = [];
            thumbSelectedList = $.map($("#table-panorama-info").bootstrapTable('getSelections'), function(row) {
                return row.panoramaId;
            });
            ajaxSubmitFormData['thumbSelectedList'] = thumbSelectedList;

            // 场景的备注
            var thumbInfoList = [];
            thumbInfoList = $.map($("#table-panorama-info").bootstrapTable('getData'), function(row) {
                var thumbInfo = {};
                thumbInfo['panoramaId'] = row.panoramaId;
                thumbInfo['thumbNote'] = row.thumbNote;
                return thumbInfo;
            });
            var thumbInfoListData = thumbInfoList.serializeObject("thumbInfoList")
            ajaxSubmitFormData = $.extend({}, ajaxSubmitFormData, thumbInfoListData);

            $.ajax({
                url : getMemberContextPath('pano0308/doSave'),
                type : "post",
                traditional : true,
                dataType : "json",
                data : ajaxSubmitFormData,
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
    });

});

// 检索场景信息
function searchData() {
    // 先销毁表格
    $('#table-panorama-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-panorama-info').bootstrapTable({
        url : getMemberContextPath('pano0308/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        pagination : false, // 是否显示分页（*）
        sortable : false, // 是否启用排序
        sortName : 'panoramaName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0308FormAjaxSubmit")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "panoramaId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true,
            formatter : function(value, tableRowInfo, index) {
                if (PlatformConstants.flagStatus_Enable == tableRowInfo.thumbnailShowFlag) {
                    var returnData = {
                        checked : true,// 设置选中
                    // disabled : true,// 设置是否可用
                    };
                    return returnData;
                } else {
                    var returnData = {
                        checked : false,// 设置选中
                    // disabled : true,// 设置是否可用
                    };
                    return returnData;

                }
            }
        }, {
            field : 'panoramaSortKey'
        }, {
            field : 'panoramaName'
        }, {
            field : 'panoramaPath'
        }, {
            field : 'thumbNote',
            editable : {
                mode : 'inline',
                validate : function(inputValue) {
                    if (inputValue != null && inputValue.length > 15) {
                        return '内容不能超过15个字。';
                    }
                }
            }
        }, {
            field : 'expositionId'
        }, {
            field : 'panoramaId'
        }, {
            field : 'thumbnailShowFlag'
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};