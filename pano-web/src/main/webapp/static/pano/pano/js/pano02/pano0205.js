var pano0205Interval;
var videoInterval;
// ================================================
// 为热点添加素材图片加载时的处理
// 【入力】
// 【返却】
// 【作成】
// 【更新】
// 【概要】
// ================================================
$(document).ready(function() {

    // bootstrapTable初始化
    $("#table-material-info").bootstrapTable({
    // locale : window.top.getCurrentLocal()
    // height : 500
    });

    var existedMaterialInfo = '';
    if ($('#existedMaterialInfoJson').val() != null && $('#existedMaterialInfoJson').val().length > 0) {
        existedMaterialInfo = JSON.parse($('#existedMaterialInfoJson').val());
    }
    newOrderList = $.extend(newOrderList, existedMaterialInfo);
    $("#table-selected-material-info").bootstrapTable({
        pagination : false, // 是否显示分页（*）
        data : existedMaterialInfo, // 初始数据
        uniqueId : "materialId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            field : 'materialId'
        }, {
            field : 'materialName',
            sortable : true
        }, {
            field : 'notes'
        }, {
            field : 'materialPath'
        }, {
            field : 'expositionId'
        }, {
            field : 'materialTypeId'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-preview' : function(e, value, tableRowInfo, index) {
                    doPreview(newOrderList[index].materialPath);
                },
                'click .row-delete' : function(e, value, tableRowInfo, index) {
                    // 已经排序的场合，从排序的数据里取原始数据删除
                    doEditRight(newOrderList[index]);
                    // 更新排序后的数据
                    newOrderList.splice(index, 1);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 查看按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-preview"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;查看';
                showData = showData + '</a>';
                // 选择按钮
                showData = showData + '&nbsp;';
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-delete"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-minus"></span>&nbsp;删除';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        },
        // 当选中行，拖拽时的哪行数据，并且可以获取这行数据的上一行数据和下一行数据
        onReorderRowsDrag : function(table, row) {
            return false;
        },
        // 拖拽完成后的这条数据，并且可以获取这行数据的上一行数据和下一行数据
        onReorderRowsDrop : function(table, row) {
            return false;
        },
        // 当拖拽结束后，整个表格的数据
        onReorderRow : function(newData) {
            newOrderList = newData;
            $('#btn_update_panoOrder').prop('disabled', !newOrderList);
            return false;
        },
    });

    // 检查展览模式
    expositionVrFlag();
    doMakekrpano();

    // 关闭音乐试听窗口操作
    $('.ui-dialog-titlebar-close ui-corner-all').click(function() {
        pano0205StopSound();
    });
    $('#pano0205Div li').hide();
    // 画面初期化隐藏图片素材列表、音乐素材列表和外部链接输入栏
    radioBoxSelectFunc();
    // 该热点已有类型
    if ($('select[name="urlType"]').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK) {
        // radioBoxSelectFunc();
        doSearchMaterial();
    }

    // 热点类型选择下拉框
    $('select[name="urlType"]').change(function() {
        // 表示制御
        radioBoxSelectFunc();

        var radioboxVal = $('select[name="materialBelongType"]').val();
        $('#theCommandFromRadiobox').val('true');
        if ($('select[name="urlType"]').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK) {
            doSearchMaterial();
        }
    });

    // 素材归属选择下拉框
    $('select[name="materialBelongType"]').change(function() {
        var radioboxVal = $('select[name="materialBelongType"]').val();
        $('#theCommandFromRadiobox').val('true');
        if ($('select[name="urlType"]').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
            doSearchMaterial();
            // } else {
            // filterLeft();
        }
    });

    $('#pano0205_polygon_property').minicolors({
        control : $(this).attr('data-control') || 'hue',
        defaultValue : $(this).attr('data-defaultValue') || '',
        inline : $(this).attr('data-inline') === 'true',
        letterCase : $(this).attr('data-letterCase') || 'lowercase',
        opacity : true,
        position : $(this).attr('data-position') || 'bottom left',
        change : function(hex, opacity) {
        }
    });

    // 确定处理
    $("#pano0205-confirm-button").click(function() {
        var radioboxVal = $('select[name="urlType"]').val();

        var uniqueKeyArray = [];
        uniqueKeyArray = $.map($('#table-selected-material-info').bootstrapTable('getData'), function(row) {
            // departmentyId前台传入后台的数据
            return row.materialId;
        });

        // 外部链接
        if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK) {
            if ($('#LinkTexbox').val() == '' || $('#LinkTexbox').val() == null) {
                var showMessage = '请输入外部链接地址！';
                window.top.showErrorMessage(showMessage, false, 3);
                return false;
            }

            var link = $('#LinkTexbox').val().toLowerCase();
            var pattern = /^((http|https|ftp):\/\/)/;
            var objExp = new RegExp(pattern);
            if (objExp.test(link) != true) {
                var showMessage = '外部链接的通讯协议只能是[http、https、ftp]';
                window.top.showErrorMessage(showMessage, false, 3);
                return false;
            }
        }

        // 音乐
        if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {
            if (uniqueKeyArray.length > 1) {
                var showMessage = '请选择一个音频素材！';
                window.top.showErrorMessage(showMessage, false, 3);
                return false;
            }
        }

        // 信息图
        if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE) {
            // if (uniqueKeyArray.length != 0) {
            // }
        }

        // 视频
        if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO && uniqueKeyArray.length > 1) {
            var showMessage = '请选择一个视频素材！';
            window.top.showErrorMessage(showMessage, false, 3);
            return false;
        }

        // 图文
        if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
            // if (uniqueKeyArray.length < 1) {
            // var showMessage = '请选择一个图文素材！';
            // window.top.showErrorMessage(showMessage, false, 3);
            // return false;
            // }
        }

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正在保存热点信息，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() { // 确认操作
            window.top.layer.close(currentConfirmIndex);

            if ($('#isPolygon').val() == 'true') {
                // 取得多边形颜色
                var polygon_color = $('#pano0205_polygon_property').val().replace('#', '0x');
                $('#polygonFillcolor').val(polygon_color);
                // 取得多边形透明度
                var polygon_fillalpha = $('#pano0205_polygon_property').attr('data-opacity');
                $('#polygonFillalpha').val(polygon_fillalpha);
            }
            $('#selectedMaterialId').val(selectedMaterialId);

            // 表单数据转换成JS对象
            var ajaxSubmitFormData = form2js($("#pano0205Form")[0]);
            var selectedMaterialList = new Array();
            selectedMaterialList = $.map(newOrderList, function(row, index) {
                var selectedMaterial = {};
                selectedMaterial['hotspotUrlObjectId'] = row.materialId;
                selectedMaterial['sortKey'] = index + 1;
                return selectedMaterial;
            });

            var selectedMaterial = selectedMaterialList.serializeObject("hotspotUrlInfoList")
            ajaxSubmitFormData = $.extend({}, selectedMaterial, ajaxSubmitFormData);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0205/doEntry'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // searchData();
                        location.reload(true);
                    }
                }
            })
        }, function() {// 取消操作
        });

    });

    // 模糊查询
    $("#searchText").on("input propertychange", function(e) {
        var val = $.trim($(this).val());
        $(".imui-dragbox-deselected").find("li").each(function(i, t) {
            if ($(t).find("label").html().toLowerCase().indexOf(val) >= 0) {
                $(t).show();
            } else {
                $(t).hide();
            }
        });
    });
    // 左向右移动时,所有选项设置成显示
    $('#textImgList').bind('imuimovebuttonsetonselectall', function(a, b) {
        $(".imui-dragbox-selected").find("li").show();
    });
});

function doMakekrpano() {
    var pano0205Krpano = document.getElementById("pano0205videoPreviewKrpano");
    if (pano0205Krpano == null || undefined == pano0205Krpano.get) {
        embedpano({
            swf : PanoConstants.VAL_EMBEDPANO_SWF,
            xml : "static/pano/pano/common/template/sound/soundpreview.xml",
            id : "pano0205videoPreviewKrpano",
            target : "videoPreview",
            wmode : "opaque",
            flash : "only",
            passQueryParameters : true
        });
    }
}

function radioBoxSelectFunc() {

    var radioboxVal = $('select[name="urlType"]').val();
    $('#linkAddressTr').hide();
    // $('#musicTable').hide();
    // $('#vedioTable').hide();
    // $('#imageTable').hide();
    $('#materialTable').hide();
    $('#materialBelongTypeTr').hide();
    $('#pano0205pageTable').hide();
    // $('#text_image').hide();
    $('#materialSearch').hide();
    if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE) {
        // 信息图
        $('#pano0205pageTable').show();
        $('#pano0205pageTable').attr("style", "width: 50%;");
        // $('#imageTable').show();
        $('#materialTable').show();
        $('#materialBelongTypeTr').show();
    }

    if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK) {
        // 外部链接
        $('#linkAddressTr').show();
        // $('#imageTable').hide();
        $('#materialTable').hide();
        $('#materialBelongTypeTr').hide();
    }

    if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO) {
        // 视频
        $('#pano0205pageTable').show();
        $('#pano0205pageTable').attr("style", "width: 100%;");
        // $('#vedioTable').show();
        $('#materialTable').show();
        $('#materialBelongTypeTr').show();
    }

    if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {
        // 音乐
        $('#pano0205pageTable').show();
        $('#pano0205pageTable').attr("style", "width: 100%;");
        // $('#musicTable').show();
        $('#materialTable').show();
        $('#materialBelongTypeTr').show();
    }
    // 图文
    if (radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
        $('#materialBelongTypeTr').show();
        // $('#text_image').show();
        $('#materialTable').show();
        $('#materialSearch').show();
    }

}

// 选中的设备信息
var selectedMaterialId;

// Ajax分页处理
function doSearchMaterial() {
    // 先销毁表格
    $('#table-material-info').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $('#table-material-info').bootstrapTable({
        url : getMemberContextPath('pano0205/doSearch'), // 请求后台的URL（*）
        method : 'get', // 请求方式（*）
        toolbar : undefined, // 工具按钮用哪个容器
        pagination : true, // 是否显示分页（*）
        sortable : true, // 是否启用排序
        sortName : 'materialName',// 初始化的时候排序的字段
        sortOrder : "asc", // 排序方式
        queryParamsType : "undefined",
        queryParams : function queryParams(params) { // 设置查询参数
            // 表单数据转换成JS对象
            var ajaxFormData = form2js($("#pano0205Form")[0]);
            ajaxFormData["pageNumber"] = params.pageNumber;
            ajaxFormData["pageSize"] = params.pageSize;
            ajaxFormData["sortName"] = params.sortName;
            ajaxFormData["sortOrder"] = params.sortOrder;
            return ajaxFormData;
        },
        uniqueId : "materialId", // 每一行的唯一标识，一般为主键列
        columns : [ {
            field : 'materialId'
        }, {
            field : 'materialName',
            sortable : true
        }, {
            field : 'notes'
        }, {
            field : 'materialPath'
        }, {
            field : 'expositionId'
        }, {
            field : 'materialTypeId'
        }, {
            field : 'rowOperate',
            events : {
                'click .row-preview' : function(e, value, tableRowInfo, index) {
                    doPreview(tableRowInfo.materialPath);
                },
                'click .row-select' : function(e, value, tableRowInfo, index) {
                    doEditLeft(tableRowInfo);
                },
            },
            formatter : function(value, tableRowInfo, index) {
                var showData = '';
                // 查看按钮
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-preview"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-edit"></span>&nbsp;查看';
                showData = showData + '</a>';
                // 选择按钮
                showData = showData + '&nbsp;';
                showData = showData + '<a href="javascript:void(0);" ';
                showData = showData + ' class="btn pano-btn-danger font-12 p-1 row-select"';
                showData = showData + '>';
                showData = showData + '<span class="glyphicon glyphicon-ok"></span>&nbsp;选择';
                showData = showData + '</a>';
                return showData;
            }
        }, ],
        onLoadSuccess : function(result) {
        },
    });
}

// 左侧表格选中的场合
function doEditLeft(tableRowInfo) {

    var uniqueKeyArray = [];
    uniqueKeyArray = $.map($('#table-selected-material-info').bootstrapTable('getData'), function(row) {
        // departmentyId前台传入后台的数据
        return row.materialId;
    });
    if (uniqueKeyArray.length == 5) {
        var showMessage = '素材最多只能选择5个！';
        window.top.showErrorMessage(showMessage, false, 3);
        return;
    }
    if (uniqueKeyArray.indexOf(tableRowInfo.materialId) != -1) {
        var showMessage = '该素材已被选择！';
        window.top.showErrorMessage(showMessage, false, 3);
    } else {
        $('#table-selected-material-info').bootstrapTable("append", tableRowInfo);
        newOrderList = $.extend(newOrderList, $('#table-selected-material-info').bootstrapTable('getData'));
    }

}

// 右侧表格选中的场合
function doEditRight(tableRowInfo) {
    $('#table-selected-material-info').bootstrapTable('removeByUniqueId', tableRowInfo.materialId);
}

// 刷新一览数据
function createListDataTable(data) {
    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
        var isjson = typeof (data) == "object"
                && Object.prototype.toString.call(data).toLowerCase() == "[object object]" && !data.length;
        if (data.indexOf("指定条件的数据不存在。请改变索条件后再检索。") != -1) {
            imuiAlert('指定条件的数据不存在。请改变索条件后再检索。');
            $("#pano0205ListDataTable tbody").html('');
            $("#pano0205VedioListDataTable tbody").html('');
            $('#pano0205ListMusicTable tbody').html('');
            $('#pageShowInfo').hide();
            $('#pano0205Div li').hide();
            $('#pano0205pageTable').hide();
            return false;
        }
        $('#theCommandFromRadiobox').val('false');
        var pano0205JsForm = JSON.parse(data);
        // 分页信息设定
        $('#pano0205Form_pageShowInfo').show();
        $('#pano0205Form_pageShowInfo').each(function(id, item) {
            $(this).prop('outerHTML', pano0205JsForm.pageShowInfos[0])

        });
        $('#pano0205Form_pageHiddenInfo').each(function(id, item) {
            $(this).prop('outerHTML', pano0205JsForm.pageShowInfos[1])

        });
        // 刷新一览数据
        var tbodyHtml = "";
        var existVideoFlg = false;
        var videoId = "";
        var existVideoTrId = "";

        if (pano0205JsForm.urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO) {
            $(pano0205JsForm.videoMaterialInfo)
                    .each(
                        function(index, item) {
                            tbodyHtml += '<tr id="pano0205Tr_' + index
                                    + '" style="height:10px;cursor: pointer;" onclick="setSelectedTrColor(this,' + "'"
                                    + item.materialId + "'" + ');">';
                            tbodyHtml += '<td title=' + item.materialId
                                    + ' class="align-L valign-M selected" style="width:20%;">' + item.materialId
                                    + '</td>';
                            tbodyHtml += '<td title=' + item.materialName
                                    + ' class="align-L valign-M selected" style="width:20%;">' + item.materialName
                                    + '</td>';
                            tbodyHtml += '<td title=' + '"' + item.notes + '"'
                                    + ' class="align-L valign-M selected" style="width:20%;">' + item.notes + '</td>';
                            tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                    + '<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('
                                    + "'" + item.materialPath + "'" + ')"/>' + '</td>';
                            tbodyHtml += "</tr>";

                            if (item.materialId == $('#existedVideoId').val()) {
                                existVideoFlg = true;
                                videoId = item.materialId;
                                existVideoTrId = "pano0205Tr_" + index;
                            }
                        });
            $("#pano0205VedioListDataTable tbody").html(tbodyHtml);

            if (existVideoFlg) {
                // 让已有视频保持选中
                selectedMaterialId = videoId;
                $("#" + existVideoTrId + " td").css("background-color", "#DACAA6");
            }
            $('#pano0205pageTable').show();
        }

        var existSoundFlg = false;
        var soundId = '';
        var existSoundTrId = '';
        if (pano0205JsForm.urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE
                || pano0205JsForm.urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {
            $(pano0205JsForm.materialInfo)
                    .each(
                        function(index, item) {
                            tbodyHtml += '<tr id="pano0205Tr_' + index
                                    + '" style="height:10px;cursor: pointer;" onclick="setSelectedTrColor(this,' + "'"
                                    + item.materialId + "'" + ');">';
                            tbodyHtml += '<td title=' + item.materialId
                                    + ' class="align-L valign-M selected" style="width:20%;">' + item.materialId
                                    + '</td>';
                            tbodyHtml += '<td title=' + item.materialName
                                    + ' class="align-L valign-M selected" style="width:20%;">' + item.materialName
                                    + '</td>';
                            tbodyHtml += '<td title=' + '"' + item.notes + '"'
                                    + ' class="align-L valign-M selected" style="width:20%;">' + item.notes + '</td>';
                            if ($('select[name="urlType"]').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE
                                    || $('#urlType').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE) {
                                tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                        + '<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('
                                        + "'" + item.materialPath + "'" + ')"/>' + '</td>';
                                tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                        + '<input type="button" value="选择" class="imui-button imui-running-button" onclick="doSelect(this,'
                                        + "'" + item.materialPath + "'" + ')"/>' + '</td>';
                            } else if ($('select[name="urlType"]').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND
                                    || $('#urlType').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {
                                tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                        + '<input type="button" value="试听" class="imui-button imui-running-button" onclick="doPlaySound_Ic0205('
                                        + "'" + item.materialPath + "'" + ')"/>' + '</td>';
                                if (item.materialId == $('#existedSoundId').val()) {
                                    existSoundFlg = true;
                                    soundId = item.materialId;
                                    existSoundTrId = "pano0205Tr_" + index;
                                }
                            }

                            tbodyHtml += "</tr>";
                        });
            if ($('select[name="urlType"]').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND
                    || $('#urlType').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {
                $('#pano0205ListMusicTable tbody').html(tbodyHtml);
                if (existSoundFlg) {
                    // 让已有音频保持选中
                    selectedMaterialId = soundId;
                    $("#" + existSoundTrId + " td").css("background-color", "#DACAA6");
                }

            }
            if ($('select[name="urlType"]').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE
                    || $('#urlType').val() == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE) {
                $("#pano0205ListDataTable tbody").html(tbodyHtml);
            }

            $('#pano0205pageTable').show();
        }

    }
}

// 通过素材Id检索预览图
function doPreview(materialPath) {
    if (materialPath != null && materialPath != '') {

        var materialSessionPath = window.top.getSessionFileEditPath(materialPath + '?temp=' + Math.random());

        var _urlType = $('select[name="urlType"]').val();
        // 图片预览
        if (_urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE
                || _urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {

            layer.open({
                id : 'LAY_pano0205OpenLayer', // 设定一个id，防止重复弹出
                title : '图片预览', // false不显示标题栏
                type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                area : [ '360px', '360px' ], // 宽高
                content : $('#pano0205imagePreview'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                success : function(layero, index) {
                    $("#imagePreview").attr('src', materialSessionPath);
                    $("#imagePreview").css('display', 'block');

                },
                end : function() {
                    // location.reload(true);
                    // searchData();
                    $('#pano0205imagePreview').hide();
                }
            });
        }
        // 视频预览
        if (_urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO) {

            layer.open({
                id : 'LAY_pano0205OpenLayer', // 设定一个id，防止重复弹出
                title : '视频预览', // false不显示标题栏
                type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                area : [ '360px', '360px' ], // 宽高
                content : $('#pano0205videoPreview'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                success : function(layero, index) {
                    videoInterval = setInterval(function() {
                        showVideoPreview(materialSessionPath)
                    }, 500);
                },
                end : function() {
                    // location.reload(true);
                    // searchData();
                    $('#pano0205videoPreview').hide();
                }
            });

        }
        // 音频预览
        if (_urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND) {

            layer.open({
                id : 'LAY_pano0205OpenLayer', // 设定一个id，防止重复弹出
                title : '音频预览', // false不显示标题栏
                type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                area : [ '360px', '360px' ], // 宽高
                content : $('#pano0205soundPreview'), // 这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                success : function(layero, index) {
                    doPlaySound_Ic0205(materialSessionPath)
                },
                end : function() {
                    // location.reload(true);
                    // searchData();
                    $('#pano0205soundPreview').hide();
                }
            });

        }
    }
}

// 播放视频
function showVideoPreview(_path) {
    var krpano = document.getElementById("pano0205videoPreviewKrpano");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    clearInterval(videoInterval);
    // 检查是否有0104的音乐在播放，如果有，则先停止0104的音乐播放,此方法针对于从0104点进场景音乐编辑后的情况
    if (_expositionVrFlag == "0") {
        var ic0104Krpano = document.getElementById("ic0104KrpanoSWFObject");
        if (ic0104Krpano != null && undefined != ic0104Krpano.get) {
            ic0104Krpano.call('stopallsounds();');
        }
    } else {
        var vr0104Krpano = document.getElementById("vr0104KrpanoSWFObject");
        if (vr0104Krpano != null && undefined != vr0104Krpano.get) {
            vr0104Krpano.call('stopallsounds();');
        }
    }
    var videoUrl = getMemberContextPath("" + _path);
    // 设定播放器皮肤尺寸,播放视频
    krpano.set('hotspot[start].onclick', 'suitableScreen();openvideo(' + videoUrl + ');');
    krpano.set('hotspot[stop].onclick', 'closevideo();');
}
// 关闭视频
function stopVideo() {
    var krpano = document.getElementById("pano0205videoPreviewKrpano");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    krpano.call('closevideo();');
    // 检索当前场景是否有背景音乐，有救播放背景音乐
    if (_expositionVrFlag == "0") {
        var ic0104krpano = document.getElementById("ic0104KrpanoSWFObject");
        if (ic0104krpano != null && undefined != ic0104krpano.get) {
            ic0104krpano.call('playsound(bgsnd,' + $('#currentSoundPath').val() + ', 0,0); ');
        }
    } else {
        var vr0104krpano = document.getElementById("vr0104KrpanoSWFObject");
        if (vr0104krpano != null && undefined != vr0104krpano.get) {
            vr0104krpano.call('playsound(bgsnd,' + $('#currentSoundPath').val() + ', 0,0); ');
        }
    }
}

// 试听歌曲
function doPlaySound_Ic0205(_materialPath) {
    // 检查是否有0104的音乐在播放，如果有，则先停止0104的音乐播放,此方法针对于从0104点进场景音乐编辑后的情况
    // if (_expositionVrFlag == "0") {
    // var ic0104Krpano = document.getElementById("ic0104KrpanoSWFObject");
    // if (ic0104Krpano != null && undefined != ic0104Krpano.get) {
    // ic0104Krpano.call('stopallsounds();');
    // }
    // } else {
    // var vr0104Krpano = document.getElementById("vr0104KrpanoSWFObject");
    // if (vr0104Krpano != null && undefined != vr0104Krpano.get) {
    // vr0104Krpano.call('stopallsounds();');
    // }
    // }
    // 查看是否已有音乐用的karpano，如果没有再新建
    var newSoundPano = document.getElementById("pano0205SoundPano");
    if (newSoundPano == null || undefined == newSoundPano.get) {
        embedpano({
            swf : PanoConstants.VAL_EMBEDPANO_SWF,
            xml : "framework/panorama/template/sound/soundpreview.xml",
            id : "pano0205SoundPano",
            target : "pano0205SoundPreviewDiv",
            wmode : "opaque",
            flash : "only",
            passQueryParameters : true
        });
    }
    $("#pano0205SoundPreviewDiv").css('display', 'block');
    eval("$('#pano0205soundPreview').imuiDialog('open')");
    pano0205Interval = setInterval(function() {
        startSound(_materialPath)
    }, 500);
}
// 设置音乐的播放事件
function startSound(_materialPath) {
    var krpano = document.getElementById("pano0205SoundPano");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    krpano.set('hotspot[start].onclick', 'playsound(s1,' + _materialPath + '?temp=' + Math.random() + ', 0,0); ');
    clearInterval(pano0205Interval);
}

// 设置关闭试听画面，登录成功和关闭检索画面时停止音乐的操作
function pano0205StopSound() {
    var krpano = document.getElementById("pano0205SoundPano");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    krpano.call('stopallsounds();');

    // 检索当前场景是否有背景音乐，有救播放背景音乐
    if (_expositionVrFlag == "0") {
        var ic0104krpano = document.getElementById("ic0104KrpanoSWFObject");
        if (ic0104krpano != null && undefined != ic0104krpano.get) {
            ic0104krpano.call('playsound(bgsnd,' + $('#currentSoundPath').val() + ', 0,0); ');
        }
    } else {
        var vr0104krpano = document.getElementById("vr0104KrpanoSWFObject");
        if (vr0104krpano != null && undefined != vr0104krpano.get) {
            vr0104krpano.call('playsound(bgsnd,' + $('#currentSoundPath').val() + ', 0,0); ');
        }
    }
}

// 选择操作
function doSelect(node, _materialPath) {

    var table = node.parentNode.parentNode;
    var _materialId = table.getElementsByTagName("td")[0].innerHTML;
    var _materialName = table.getElementsByTagName("td")[1].innerHTML;
    var _notes = table.getElementsByTagName("td")[2].innerHTML;

    var _trId = 'pano0205TrRight_' + _materialId;
    if ($('#' + _trId).length != 0) {
        imuiAlert('该素材已经选择。');
        return false;
    }
    if ($("#pano0205SelectedListDataTable tbody tr").length == 10) {
        imuiAlert('素材最大只能选择10个。');
        return false;
    }
    // 刷新右侧一览数据
    var tbodyHtml = "";
    tbodyHtml += '<tr id="' + _trId + '" style="height:10px;" >';
    tbodyHtml += '<td title=' + _materialId + ' class="align-L valign-M" style="width:20%;">' + _materialId + '</td>';
    tbodyHtml += '<td title=' + _materialName + ' class="align-L valign-M" style="width:20%;">' + _materialName
            + '</td>';
    tbodyHtml += '<td title=' + _notes + ' class="align-L valign-M" style="width:auto;">' + _notes + '</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">' + '<input id="' + _trId
            + '_sortKey" type="text" value="" style="width:30px;text-align:right;" maxlength="2"/>' + '</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">'
            + '<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview(' + "'"
            + _materialPath + "'" + ')"/>' + '</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">'
            + '<input type="button" value="取消" class="imui-button imui-running-button" onclick="doCancel('
            + "'pano0205TrRight_" + _materialId + "'" + ')"/>' + '</td>';
    tbodyHtml += "</tr>";
    $("#pano0205SelectedListDataTable tbody").append(tbodyHtml);

}

// 取消操作
function doCancel(_tr) {
    $("#pano0205SelectedListDataTable tbody")[0].deleteRow($('#' + _tr)[0].rowIndex - 1);
}

// 自定义热点Url对象
function HotspotUrl(_hotspotUrlObjectId, _sortKey) {
    this.hotspotUrlObjectId = _hotspotUrlObjectId;
    this.sortKey = _sortKey;
}

// 判断展览是否为VR展
var _expositionVrFlag = "";
function expositionVrFlag() {
    var _ajaxUrl = getMemberContextPath('pano0110/doVrFlag');
    var param = '';
    param = param + '&selectedExpositionId=' + $('#expositionId').val();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            _expositionVrFlag = data;
        }

    });
}
// 图文前按钮初期化处理
function initTextImgList() {
    var html = "<a herf='javascript:void(0);' onclick='return imgPreview(this);' title='预览'><span class='im-ui-icon-common-16-preview'></span></a>&nbsp;&nbsp";
    $("#textImg").find("li").find("label").before(html);
    $("#textImg").find("li").show();
    filterLeft();
}
// 图片预览
function imgPreview(obj) {
    var id = $(obj).nextAll("input").val();
    doPreview(text_img_map[id].imgPath);
    return false;
}
// 根据素材归属 筛选左边可选素材
function filterLeft() {
    $(".imui-dragbox-deselected").find("li").each(function(i, t) {
        var id = $(t).find("input").val();
        if ($('input[name="materialBelongType"]:checked').val() == '2') {
            if (text_img_map[id].expositionId == 'common_material') {
                $(t).show();
            } else {
                $(t).hide();
            }
        } else {
            if (text_img_map[id].expositionId != 'common_material') {
                $(t).show();
            } else {
                $(t).hide();
            }
        }
    });
    return false;
}
