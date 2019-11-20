// ================================================
// 展览一览画面检索
// ================================================
$(document).ready(function() {

    // 检索处理
    $("#btn_query").click(function() {
        // 检索处理
        searchData();
    });

    // bootstrapTable初始化
    $("#table-exposition-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });

    // 检索处理
    searchData();

});

function searchData() {
    // 先销毁表格
    $('#table-exposition-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-exposition-info').bootstrapTable({
        height : 300,
        url : getMemberContextPath('pano0110/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : '#toolbar', // 工具按钮用哪个容器
        sortable : true, // 是否启用排序
        sortName : 'expositionId',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var formData = form2js($("#pano0110FormSearch")[0]);
            formData["pageNumber"] = params.pageNumber;
            formData["pageSize"] = params.pageSize;
            formData["sortName"] = params.sortName;
            formData["sortOrder"] = params.sortOrder;
            return formData;
        },
        uniqueId : "expositionId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            field : 'rowNumber',
            width : 50,
            formatter : function(value, tableRowInfo, index) {
                var options = $('#table-exposition-info').bootstrapTable("getOptions");
                return (options.pageNumber - 1) * (options.pageSize) + index + 1;
            }
        }, {
            field : 'expositionName',
            sortable : true
        }, {
            field : 'expositionId',
            sortable : true
        }, {
            field : 'status',
            sortable : true
        }, {
            field : 'expositionStatusName',
            sortable : true
        }, {
            field : 'expositionType',
            sortable : true
        }, {
            field : 'expositionTypeName',
            sortable : true
        }, {
            field : 'expositionStartDate',
            sortable : true
        }, {
            field : 'expositionEndDate'
        }, {
            field : 'releaseDate'
        }, {
            field : 'vrFlag'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-edit' : function(e, value, tableRowInfo, index) {
                    doEdit(tableRowInfo);
                },
                'click .row-release' : function(e, value, tableRowInfo, index) {
                    doRelease(tableRowInfo);
                },
                'click .row-download' : function(e, value, tableRowInfo, index) {
                    doDownload(tableRowInfo);
                },
                'click .row-copyUrl' : function(e, value, tableRowInfo, index) {
                    doCopyUrl(tableRowInfo);
                },
                'click .row-preview' : function(e, value, tableRowInfo, index) {
                    doPreview(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 编辑
                showData = showData + '<button class="dropdown-item row-edit" ';
                showData = showData + '  type="button"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;编辑';
                showData = showData + '</button>';
                // 发布
                showData = showData + '<button class="dropdown-item row-release" ';
                showData = showData + '  type="button"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;发布';
                showData = showData + '</button>';
                // 下载
                showData = showData + '<button class="dropdown-item row-download" ';
                showData = showData + '  type="button"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;下载';
                showData = showData + '</button>';
                // 拷贝URL
                showData = showData + '<button class="dropdown-item row-copyUrl" ';
                showData = showData + '  type="button"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;拷贝URL';
                showData = showData + '</button>';
                // 预览
                showData = showData + '<button class="dropdown-item row-preview" ';
                showData = showData + '  type="button"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-print"></span>&nbsp;预览';
                showData = showData + '</button>';
                showData = CommonUtilJs.createDropMenu(showData);
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        }
    });
};

// 编辑
function doEdit(tableRowInfo) {
    // 引继变量
    var urlParam = {};
    urlParam['expositionId'] = tableRowInfo.expositionId
    urlParam['expositionName'] = tableRowInfo.expositionName
    // 请求的URL
    var targetUrl = 'pano0104/';
    if (PlatformConstants.flagStatus_Enable === tableRowInfo.vrFlag) {
        targetUrl = 'panoVr0104/';
    }
    targetUrl = targetUrl + '?' + $.param(urlParam);
    window.top.layer.open({
        title : '编辑场景',
        type : 2,
        closeBtn : 0, // 不显示关闭按钮
        title : false, // 不显示标题
        area : [ '100%', '100%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
};

// 发布
function doRelease(tableRowInfo) {
    // 提交表单的数据
    var ajaxSubmitFormData = form2js($("#pano0110FormAjaxSubmit")[0]);
    ajaxSubmitFormData['selectedExpositionId'] = tableRowInfo.expositionId
    // 请求的URL
    var targetUrl = 'pano0110/doRelease_vr';
    // if (PlatformConstants.flagStatus_Enable === tableRowInfo.vrFlag) {
    // targetUrl = 'pano0110/doRelease_vr';
    // }
    $.ajax({
        type : 'post',
        traditional : true,
        data : ajaxSubmitFormData,
        dataType : 'json',
        url : getMemberContextPath(targetUrl),
        success : function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                searchData();
            }
        }
    })
};

// 下载
function doDownload(tableRowInfo) {
    $('#pano0110FormAjax_selectedExpositionId').val(tableRowInfo.expositionId);
    // 请求的URL
    var targetUrl = 'pano0110/doDownload';
    if (PlatformConstants.flagStatus_Enable === tableRowInfo.vrFlag) {
        targetUrl = 'pano0110/doDownload_vr';
    }
    // targetUrl = targetUrl + '?' + $.param(urlParam);

    var msg = '本操作会导出当前展览，是否继续？';
    var formId = "pano0110FormAjaxSubmit";
    var createFileurl = getMemberContextPath(targetUrl);
    var downloadUrl = getContextPath('/ajaxDownload');
    var downloadFileName = tableRowInfo.expositionId + window.top.CommonUtilJs.getCurrentTime() + '.zip';
    CommonUtilJs.doOutputExcelConfirm(msg, formId, createFileurl, downloadUrl, downloadFileName);
};

// 拷贝URL
function doCopyUrl(tableRowInfo) {
    var _link = window.location.protocol;
    _link = _link + '//' + window.location.host;
    _link = _link + getContextPath("statictour_app/" + tableRowInfo.expositionId + "_vr/index.html");

    // 做成一个临时按钮
    var tempBtnShare = document.createElement("button");
    tempBtnShare.setAttribute('data-clipboard-text', _link);
    // 实例化ClipboardJS对象
    var clipboard = new ClipboardJS(tempBtnShare);

    clipboard.on('success', function(e) {
        window.top.showSuccessMessage("链接复制成功", false, 3);
    });

    clipboard.on('error', function(e) {
        var errorMsgList = [];
        errorMsgList.push("链接复制失败，请刷新页面后重新复制！");
        window.top.showErrorMessage(errorMsgList, false, 3);
    });

    // 触发ClipboardJS处理
    tempBtnShare.click();

};

// 预览
function doPreview(tableRowInfo) {
    var targetUrl = 'statictour_app/';
    targetUrl = targetUrl + tableRowInfo.expositionId+ '_vr/index.html' ;
    window.top.layer.open({
        title : tableRowInfo.expositionName + '预览',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '100%' ], // 宽高
        content : [ getContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
};
