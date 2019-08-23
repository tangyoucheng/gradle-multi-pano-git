//================================================
//为热点添加素材图片加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================

//返回处理
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

            var tableRowsInfo = $('#table-panorama-info').bootstrapTable('getSelections');
            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0204FormAjaxSubmit")[0]);
            ajaxSubmitFormData['selectedPanoramaId'] = tableRowsInfo[0].panoramaId;

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0204/doEntry'),
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
});

// 检索场景信息
function searchData() {
    // 先销毁表格
    $('#table-panorama-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-panorama-info').bootstrapTable({
        url : getMemberContextPath('pano0204/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        pagination : false, // 是否显示分页（*）
        sortable : true, // 是否启用排序
        sortName : 'panoramaName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0204FormAjaxSubmit")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "panoramaId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            checkbox : true
        }, {
            field : 'panoramaSortKey'
        }, {
            field : 'panoramaName',
            sortable : true
        }, {
            field : 'expositionId'
        }, {
            field : 'panoramaId'
        }, {
            field : 'panoramaSoundId'
        }, {
            field : 'panoramaHlookat'
        }, {
            field : 'panoramaVlookat'
        }, {
            field : 'startSceneFlag'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-edit' : function(e, value, tableRowInfo, index) {
                    doPreview(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 编辑按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-edit"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;预览';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 读取场景预览图
function doPreview(tableRowInfo) {
    layer.open({
        id : 'LAY_pano0204OpenLayer', // 设定一个id，防止重复弹出
        title : '场景预览', // false不显示标题栏
        type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        area : [ '360px', '360px' ], // 宽高
        content : $('#pano0204OpenLayer'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        success : function(layero, index) {

            removepano("pano0204KrpanoNewObject");

            // 引擎配置文件路径
            var xmlfilePath = "pano0204/";
            xmlfilePath = xmlfilePath + tableRowInfo.expositionId;
            xmlfilePath = xmlfilePath + "/panoramas/";
            xmlfilePath = xmlfilePath + tableRowInfo.panoramaId;
            xmlfilePath = xmlfilePath + "/show_l.xml?temp=" + Math.random();
            var xmlSessionFilePath = window.top.getSessionFileEditPath(xmlfilePath);
            
            embedpano({
                swf : PanoConstants.VAL_EMBEDPANO_SWF,
                id : 'pano0204KrpanoNewObject',
                xml : xmlSessionFilePath,
                target : "pano0204Pano",
                wmode : "transparent",
                html5 : "prefer",
                passQueryParameters : true
            });

        },
        end : function() {
            // location.reload(true);
            // searchData();
            $('#pano0204OpenLayer').hide();
        }
    });
}