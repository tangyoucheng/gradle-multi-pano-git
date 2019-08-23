//================================================
//地图一览时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function() {

    // bootstrapTable初始化
    $("#table-expositionMap-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });
    // toolbar的制御
    var selectionsLength = $('#table-expositionMap-info').bootstrapTable('getSelections').length;
    $('#btn_choose').prop('disabled', selectionsLength != 1);
    $('#btn_delete').prop('disabled', !selectionsLength);

    var jqueryEvent = 'check.bs.table uncheck.bs.table load-success.bs.table ';
    jqueryEvent = jqueryEvent + 'check-all.bs.table uncheck-all.bs.table';
    $('#table-expositionMap-info').on(jqueryEvent, function() {
        var selectionsLength = $('#table-expositionMap-info').bootstrapTable('getSelections').length;
        $('#btn_choose').prop('disabled', selectionsLength != 1);
        $('#btn_delete').prop('disabled', !selectionsLength);
    });

    // 检索处理
    searchData();

    // 新增处理
    $("#btn_add").click(function() {

        var targetUrl = 'pano0304/';
        var urlParam = form2js($("#pano0305FormAjaxSubmit")[0]);
        urlParam['returnTargetIframe'] = window.name;

        targetUrl = targetUrl + '?' + $.param(urlParam);
        window.top.layer.open({
            title : '新增导航图',
            type : 2,
            closeBtn : 1, // 0不显示关闭按钮
            area : [ '90%', '100%' ], // 宽高
            content : [ getMemberContextPath(targetUrl), 'yes' ],
            success : function(layero, index) {
                layer.close(layer.index); // 关闭操作菜单
            },
            end : function() {
                searchData();
            }
        });

    });

    // 删除
    $("#btn_delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('本操作会删除选中的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0305FormAjaxSubmit")[0]);
            var uniqueKeyArray = new Array();
            uniqueKeyArray = $.map($('#table-expositionMap-info').bootstrapTable('getSelections'), function(row) {
                // departmentyId前台传入后台的数据
                return row.expositionMapId;
            });
            ajaxSubmitFormData['uniqueKeyList'] = uniqueKeyArray;

            window.top.layer.close(currentConfirmIndex);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0305/doDelete'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // 重新检索画面
                        searchData();
                    }
                }
            // ,
            // error : function() {
            // }
            });
        }, function() {
            // 取消操作
        });

    });

    // 确定处理
    $("#btn_choose").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存当前数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);

            var tableRowsInfo = $('#table-expositionMap-info').bootstrapTable('getSelections');
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0305FormAjaxSubmit")[0]);
            ajaxSubmitFormData['selectedExpositionMapId'] = tableRowsInfo[0].expositionMapId;

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0305/doEntry'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // var showMessage = '只能同时上传文件名以半角[ -、_ ]，';
                        // window.top.showErrorMessage(showMessage, false, 3);
                    }
                }
            })
        }, function() {// 取消操作
        });
    });

    // 确定处理
    // $("#ic0305-confirm-button").click(function() {
    // if (selectedExpositionMapId == '') {
    // imuiAlert('请选择一张地图！');
    // return false;
    // } else {
    // imuiConfirm('是否更新场景地图信息?', '确认', function() {
    // $(".imui-validation-error").remove();
    // $('#selectedExpositionMapId').val(selectedExpositionMapId);
    // var _ajaxUrl = getMemberContextPath('pano0305/doEntry');
    // var param = $("#ic0305Form").serialize();
    // jQuery.post(_ajaxUrl, param, function(data) {
    // if (CommonUtilJs.processAjaxSuccessAfter(data)) {
    // eval("$('#ic0305Finish').imuiDialog('open')");
    // }
    //
    // });
    // });
    // }
    // });

});

// 检索场景信息
function searchData() {
    // 先销毁表格
    $('#table-expositionMap-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-expositionMap-info').bootstrapTable({
        url : getMemberContextPath('pano0305/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        pagination : false, // 是否显示分页（*）
        sortable : true, // 是否启用排序
        sortName : 'expositionMapName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0305FormAjaxSubmit")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "expositionMapId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'expositionMapName',
            sortable : true
        }, {
            field : 'notes'
        }, {
            field : 'expositionMapId'
        }, {
            field : 'expositionMapPath'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-preview' : function(e, value, tableRowInfo, index) {
                    doPreview(tableRowInfo);
                },
                'click .row-edit' : function(e, value, tableRowInfo, index) {
                    doEdit(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 预览按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-preview"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;预览';
                showData = showData + '</a>';
                // 编辑按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-edit"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;编辑';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 预览图
function doPreview(tableRowInfo) {
    layer.open({
        id : 'LAY_pano0305OpenLayer', // 设定一个id，防止重复弹出
        title : '场景预览', // false不显示标题栏
        type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        area : [ '360px', '360px' ], // 宽高
        content : $('#pano0305OpenLayer'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        success : function(layero, index) {

            // 文件路径
            var fileSessionPath = window.top.getSessionFileEditPath(tableRowInfo.expositionMapPath);

            $("#imagePreview").attr('src', fileSessionPath + '?temp=' + Math.random());
            $("#imagePreview").css('display', 'block');

        },
        end : function() {
            // location.reload(true);
            // searchData();
            $('#pano0305OpenLayer').hide();
        }
    });
}

// 通过素材Id检索预览图
// function doPreview(_expositionMapId) {
//
// var _ajaxUrl = getMemberContextPath('pano0305/doGetPreviewImage');
// $('#selectedExpositionMapId').val(_expositionMapId);
// var param = $("#ic0305Form").serialize();
// jQuery.post(_ajaxUrl, param, function(data) {
// if (CommonUtilJs.processAjaxSuccessAfter(data)) {
// if (data != null && data != '') {
// $("#imagePreview").attr('src', data + '?temp=' + Math.random());
// $("#imagePreview").css('display', 'block');
// eval("$('#ic0305imagePreview').imuiDialog('open')");
// }
// }
// });
// }

// 导航图编辑
function doEdit(tableRowInfo) {

    var targetUrl = 'pano0306/';
    // var urlParam = form2js($("#pano0305FormAjaxSubmit")[0]);
    var urlParam = {};
    urlParam['returnTargetIframe'] = window.name;
    urlParam['pano0306ExpositionMapId'] = tableRowInfo.expositionMapId;

    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '编辑导航图',
        type : 2,
        closeBtn : 1, // 0不显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        success : function(layero, index) {
            layer.close(layer.index); // 关闭操作菜单
        },
        end : function() {
            searchData();
        }
    });
    return false;
}
