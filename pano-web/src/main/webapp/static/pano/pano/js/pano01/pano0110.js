var i = 0;
// ================================================
// 展览一览画面检索
// ================================================
$(document).ready(function() {

    // 复制url
    $('#button-copy-confirm').click(function() {
        // var url=$('#ic0110SelectedShortCutUrl').text();
        // window.clipboardData.setData('text', url);
        // if(window.clipboardData.getData('text')==''){
        // imuiAlert("复制失败，请刷新页面后重新复制！");
        // }else{
        // $("<div>复制成功！</div>").imuiMessageDialog({
        // iconType: 'im-ui-icon-common-24-confirmation',
        // verticalAlign: 'middle'
        // });
        // }
        //        
        $('#ic0110SelectedShortCutUrl').select();
        document.execCommand('Copy');
        $("<div>复制成功！</div>").imuiMessageDialog({
            iconType : 'im-ui-icon-common-24-confirmation',
            verticalAlign : 'middle'
        });
    });

    // 检索处理
    $("#btn_query").click(function() {
        // 检索处理
        searchData();
    });

    // 发布成功后确定处理
    $('#button-releaseFinish-copy-confirm').click(function() {
        var url = $('#selectedShortCutUrl').text();
        window.clipboardData.setData('text', url);
        if (window.clipboardData.getData('text') == '') {
            imuiAlert("复制失败，请刷新页面后重新复制！");
        } else {
            $("<div>复制成功！</div>").imuiMessageDialog({
                iconType : 'im-ui-icon-common-24-confirmation',
                verticalAlign : 'middle'
            });
        }
    });

    // bootstrapTable初始化
    $("#table-exposition-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });

    // 检索处理
    searchData();

});

// 关闭提示框操作
function closeDialogAndRefresh() {
    $("#search-form").attr("action", 'pano0110/doRefreshByself');
    $("#search-form").submit();
}

// 下载展览
function doDownloadExposition(_expositionId) {
    $("#ic0101SelectedExpId").val(_expositionId);
    $("#downloadExpositionForm").submit();
}

// 下载VR展览
function doDownloadExposition_vr(_expositionId) {
    $("#ic0101SelectedExpId_vr").val(_expositionId);
    $("#downloadExpositionForm_vr").submit();
}

// 全景图编辑按钮
function editPanorama(_expositionId) {
    $("#thisFlagIsFromIc0110").val('yes');
    $("#hidpExpositionId").val(_expositionId);
    $("#hidepExpositionId").val(_expositionId);

    var _ajaxUrl = getMemberContextPath('pano0110/doVrFlag');
    var param = '';
    param = param + '&selectedExpositionId=' + _expositionId;
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            if (data == "0") {
                $("#goto-ic0104-form").submit();
            } else {
                $("#goto-vr0104-form").submit();
            }
        }

    });

}

// 展览发布
function doRealeaseExpro(_tenantId, _expositionId) {
    imuiConfirm('是否发布该展览?', '确认', function() {
        $(".imui-validation-error").remove();
        // 打开loading图
        eval("$('#ic0110Releasing').imuiDialog('open')");
        var _ajaxUrl = getMemberContextPath('pano0110/doRelease');
        var param = '';
        param = param + '&selectedExpositionId=' + _expositionId;
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                var _link = window.location.protocol;
                _link = _link + '//' + window.location.host;
                _link = _link + getMemberContextPath("statictour/" + _tenantId + '/' + _expositionId + "/index.html");
                $("#selectedShortCutUrl").text(_link);
                eval("$('#ic0110Releasing').imuiDialog('close')");
                eval("$('#ic0110ReleaseFinish').imuiDialog('open')");
            }

        });
    });
}

// VR展览发布
function doRealeaseExpro_vr(_tenantId, _expositionId) {
    imuiConfirm('是否发布该展览?', '确认', function() {
        $(".imui-validation-error").remove();
        // 打开loading图
        eval("$('#ic0110Releasing').imuiDialog('open')");
        var _ajaxUrl = getMemberContextPath('pano0110/doRelease_vr');
        var param = '';
        param = param + '&selectedExpositionId=' + _expositionId;
        jQuery.post(_ajaxUrl, param, function(data) {
            if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                var _link = window.location.protocol;
                _link = _link + '//' + window.location.host;
                _link = _link
                        + getMemberContextPath("statictour/" + _tenantId + '/' + _expositionId + "_vr/index.html");
                $("#selectedShortCutUrl").text(_link);
                eval("$('#ic0110Releasing').imuiDialog('close')");
                eval("$('#ic0110ReleaseFinish').imuiDialog('open')");
            }

        });
    });
}

// 全景图查看按钮
function doCreateShortCutUrl(_tenantId, _expositionId) {
    var _link = window.location.protocol;
    _link = _link + '//' + window.location.host;
    _link = _link + getMemberContextPath('statictour/' + _tenantId + '/' + _expositionId + '/index.html');
    $("#ic0110SelectedShortCutUrl").val(_link);
    eval("$('#ic0110ShowShortCutUrl').imuiDialog('open')");
}

// VR全景图查看按钮
function doCreateShortCutUrl_vr(_tenantId, _expositionId) {
    var _link = window.location.protocol;
    _link = _link + '//' + window.location.host;
    _link = _link + getMemberContextPath('statictour/' + _tenantId + '/' + _expositionId + '_vr/index.html');
    $("#ic0110SelectedShortCutUrl").val(_link);
    eval("$('#ic0110ShowShortCutUrl').imuiDialog('open')");
}

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
    var targetUrl = 'pano01003/';
    targetUrl = targetUrl + '?departmentId=' + tableRowInfo.departmentId;
    window.top.layer.open({
        title : '编辑社区',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
};

// 预览
function doPreview(tableRowInfo) {
    var targetUrl = 'pano01003/';
    targetUrl = targetUrl + '?departmentId=' + tableRowInfo.departmentId;
    window.top.layer.open({
        title : '编辑社区',
        type : 2,
        closeBtn : 1, // 显示关闭按钮
        area : [ '90%', '90%' ], // 宽高
        content : [ getMemberContextPath(targetUrl), 'yes' ],
        end : function() {
            searchData();
        }
    });
};
