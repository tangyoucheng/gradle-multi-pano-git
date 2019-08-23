// 自定义播放音乐用的计时器
var ic208Interval;
// ================================================
// 为热点添加素材图片加载时的处理
// 【入力】
// 【返却】
// 【作成】
// 【更新】
// 【概要】
// ================================================
$(document)
        .ready(
            function() {
                // $('#imageTable').hide();
                // $('#ic0208Div li').hide();
                // if ($('#selectedHotspotId').val() != null &&
                // $('#selectedHotspotId').val() != '') {
                // $('#choiceHotspotType').attr("disabled", "true");
                // }
                // // 判断单点热点种类，3为单点信息热点，6为单点音频热点
                // if ($('#ic0203hotspotTypeChoice').val() ==
                // PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                // $('#ic0208pageTable').attr("style", "width: 50%;");
                // $('#imageTable').show();
                // $('#ic0208DivTable').hide();
                // doSearch();
                // }
                //
                // // 判断是否存在有相应的素材
                // if ($("#ic0208materialInfo").val() == "" ||
                // $("#ic0208materialInfo").val() == null) {
                // $('#ic0208pageDiv').hide();
                // $('#ic0208pageTable').hide();
                // }

                $('input[name="hotspotTypeChoice"]').click(function() {
                    // 单点热点种类，3为单点信息热点，6为单点音频热点
                    var radioboxVal = $('input[name="hotspotTypeChoice"]:checked').val();
                    if (radioboxVal == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_IMAGE) {
                        $('#ic0208pageTable').attr("style", "width: 100%;");
                        $('#imageTable').hide();
                        $('#ic0208DivTable').show();
                        doSearch();
                    }

                    if (radioboxVal == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                        $('#ic0208pageTable').attr("style", "width: 50%;");
                        $('#imageTable').show();
                        $('#ic0208DivTable').hide();
                        doSearch();
                    }

                });

                // 关闭音乐试听窗口操作
                $('.ui-dialog-titlebar-close ui-corner-all').click(function() {
                    stopSound();
                });

                // 判断展览模式
                expositionVrFlag();

                // 确定处理
                $("#ic0208-confirm-button")
                        .click(
                            function() {

                                if ($('#selectedMaterialId').val().length == 0) {
                                    if ($('input[name="hotspotTypeChoice"]:checked').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC
                                            || $('#ic0203hotspotTypeChoice').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                                        // 如果新建热点为音频点
                                        makeSoundHotspot();
                                    } else {
                                        imuiAlert('请选择素材！');
                                        return false;
                                    }
                                } else {

                                    // 如果是声音素材，直接做录入操作
                                    if ($('#materialTypeId').val() == PanoConstants.VAL_MATERIAL_TYPE_SOUND) {
                                        imuiConfirm('是否设定音乐?', '确认', function() {
                                            // 请求来自新建场景画面
                                            if ($('#dataFromIc0201').val() == "yes") {
                                                ic0201GetDataFromIc0208($('#selectedMaterialId').val());
                                                stopSound();
                                                // 展览为非VR展
                                                if (_expositionVrFlag == "0") {
                                                    $("#ic0201SetSound").imuiPageDialog('close');
                                                } else {
                                                    $("#vr0201SetSound").imuiPageDialog('close');
                                                }

                                            }

                                            // 请求来自新建展览画面
                                            if ($('#dataFromIc0101').val() == "yes") {
                                                ic0101GetDataFromIc0208($('#selectedMaterialId').val());
                                                stopSound();
                                                $("#ic0101SetSound").imuiPageDialog('close');
                                            }

                                            // 请求来自场景编辑画面-为场景设置音乐的情况
                                            if ($('#dataFromIc0202').val() == "yes") {
                                                ic0202GetDataFromIc0208($('#selectedMaterialId').val());
                                                stopSound();
                                                $("#setSound").imuiPageDialog('close');
                                            }

                                            // 请求来自编辑展览编辑画面
                                            if ($('#dataFromIc0103').val() == "yes") {
                                                ic0103GetDataFromIc0208($('#selectedMaterialId').val());
                                                stopSound();
                                                lookOldVideoFile(_videoPath);
                                                lookNewVideoFile(_newPath);
                                                $("#ic0103SetSound").imuiPageDialog('close');
                                            }

                                            if ($('#dataFromIc0109').val() == "yes") {
                                                ic0109GetDataFromIc0208($('#selectedMaterialId').val());
                                                stopSound();
                                                lookOldVideoFile(_videoPath);
                                                lookNewVideoFile(_newPath);
                                                $("#ic0109SetSound").imuiPageDialog('close');
                                            }
                                        });
                                    } else if ($('#materialTypeId').val() == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE
                                            || $('#materialTypeId').val() == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
                                        // 如果是浮动信息文件，而且展览为非VR展
                                        if (_expositionVrFlag == "0") {
                                            ic0105ShowFlowInfoLayer($('#selectedMaterialId').val(), $(
                                                '#selectedMaterialPath').val(), $('#materialTypeId').val(),
                                                flowTextInfo);
                                        } else {
                                            vr0105ShowFlowInfoLayer($('#selectedMaterialId').val(), $(
                                                '#selectedMaterialPath').val(), $('#materialTypeId').val(),
                                                flowTextInfo);
                                        }

                                        // ic0105ShowFlowInfoLayer($('#selectedMaterialId').val(),$('#selectedMaterialPath').val(),
                                        // $('#materialTypeId').val(),flowTextInfo);
                                        $("#addFlowInfo").imuiPageDialog('close');

                                    } else {
                                        if (_expositionVrFlag == "0") {
                                            ic0203AddImageHotspot($('#selectedMaterialId').val(), $(
                                                '#selectedMaterialPath').val(), $('#materialTypeId').val(), width,
                                                height, frame, delay, hasPngImage, $('#selectedHotspotId').val(), $(
                                                    '#reEdit').val());
                                        } else {
                                            vr0203AddImageHotspot($('#selectedMaterialId').val(), $(
                                                '#selectedMaterialPath').val(), $('#materialTypeId').val(), width,
                                                height, frame, delay, hasPngImage, $('#selectedHotspotId').val(), $(
                                                    '#reEdit').val());
                                        }

                                        $("#popupPage").imuiPageDialog('close');

                                    }
                                }

                            });

            });

var firstMaterialId = "";
var seconfMaterialId = "";
var firstSortKey = "";
var secondSortKey = "";
// 选中了音乐radiobox
function makeSoundHotspot() {

    if ($("#ic0208SelectedListDataTable tbody tr").length != 0
            && $("#ic0208SelectedListDataTable tbody tr").length == 2) {

        $("#ic0208SelectedListDataTable tbody tr").each(function(index, item) {
            var _sortKey = $('#' + $(this)[0].id + '_sortKey').val();

            // 循环中取得选中的素材的Id与顺序
            if (index == 0) {
                firstSortKey = _sortKey;
                firstMaterialId = $(this).children("td:eq(0)").text();
            } else {
                secondSortKey = _sortKey;
                seconfMaterialId = $(this).children("td:eq(0)").text();
            }

        })

        var reg = new RegExp("^[1-9]*$");
        if (!reg.test(firstSortKey) || !reg.test(secondSortKey) || firstSortKey == secondSortKey || firstSortKey == ''
                || secondSortKey == '') {
            imuiAlert('顺序必须填写半角非零数字，并且不能重复！');
            return false;
        }
        // 排序
        if (secondSortKey < firstSortKey) {
            var changerMaterialId = seconfMaterialId;
            seconfMaterialId = firstMaterialId;
            firstMaterialId = changerMaterialId;
            var changerSort = firstSortKey;
            firstSortKey = secondSortKey;
            secondSortKey = changerSort;
        }

        // 通过选中的素材的ID在后台中取得对应素材的path
        var _ajaxUrl = getMemberContextPath('pano0208/doGetSelectedMaterialInfo');
        $('#selectedMaterialId').val(firstMaterialId);
        var param = $("#ic0208Form").serialize();
        if (_expositionVrFlag == "0") {
            // 展览为非VR展
            jQuery.post(_ajaxUrl, param, function(data) {
                if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                    var ic0208JsDto = JSON.parse(data);
                    ic0203AddImageHotspot(firstMaterialId + "/" + seconfMaterialId + "/" + firstSortKey + "/"
                            + secondSortKey, ic0208JsDto.materialPath, ic0208JsDto.materialTypeId,
                        ic0208JsDto.gifWidth, ic0208JsDto.gifHeight, ic0208JsDto.gifFrameCount,
                        ic0208JsDto.gifDelayTime, ic0208JsDto.hasPngImage, $('#selectedHotspotId').val(), $('#reEdit')
                                .val());

                    $("#popupPage").imuiPageDialog('close');

                }
            });
        } else {
            // 展览为VR展
            jQuery.post(_ajaxUrl, param, function(data) {
                if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                    var ic0208JsDto = JSON.parse(data);
                    vr0203AddImageHotspot(firstMaterialId + "/" + seconfMaterialId + "/" + firstSortKey + "/"
                            + secondSortKey, ic0208JsDto.materialPath, ic0208JsDto.materialTypeId,
                        ic0208JsDto.gifWidth, ic0208JsDto.gifHeight, ic0208JsDto.gifFrameCount,
                        ic0208JsDto.gifDelayTime, ic0208JsDto.hasPngImage, $('#selectedHotspotId').val(), $('#reEdit')
                                .val());

                    $("#popupPage").imuiPageDialog('close');

                }
            });
        }

    } else {
        imuiAlert('素材有且只能选择2个。');
        return false;
    }
}

// 定义文字浮动信息的内容
var flowTextInfo;
// 定义gif图的属性
var width;
var height;
var frame;
var delay;
var hasPngImage;

// 设定被选行的颜色
function setSelectedTrColor(_trInfo, _selectedMaterialId, _selectedMaterialPath, _width, _height, _frame, _delay,
        _hasPngImage, _flowTextInfo) {

    if ($('input[name="hotspotTypeChoice"]:checked').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {

        $('#ic0208LeftListDataTable tbody td').each(function() {
            $(this).css("background-color", "");
        })
    } else {
        // 选中的设备信息设定
        $('#selectedMaterialId').val(_selectedMaterialId);
        $('#selectedMaterialPath').val(_selectedMaterialPath);
        width = _width;
        height = _height;
        frame = _frame;
        delay = _delay;
        hasPngImage = _hasPngImage;
        flowTextInfo = _flowTextInfo;

        $('#ic0208ListDataTable tbody td').each(function() {
            $(this).css("background-color", "");
        })
    }
    $("#" + _trInfo.id + ' td').css("background-color", "#DACAA6");
}

// 检索操作
function doSearch() {
    var _ajaxUrl = getMemberContextPath('pano0208/doSearch');
    var param = 'materialTypeId=' + $('#materialTypeId').val()
    param = param + '&materialId=' + $('#materialIdTextbox').val()
    param = param + '&materialName=' + $('#materialNameTextbox').val();
    param = param + '&materialBelongType=' + $("input[name='materialBelongType']:checked").val();
    param = param + '&expositionId=' + $('#expositionId').val();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            ic0208CreateListDataTable(data);
        }
    });
}

// 通过素材Id检索预览文件
function doPreview(_materialPath, _flowTextInfo) {
    $("#textDiv").html('');
    if (_materialPath != null && _materialPath != '') {
        if (_materialPath == 'dummy') {
            // 文字
            $("#textDiv").html(_flowTextInfo);
            eval("$('#ic0208textPreview').imuiDialog('open')");
            return;
        }
        // 图片
        $("#imagePreview").attr('src', _materialPath + '?temp=' + Math.random());
        $("#imagePreview").css('display', 'block');
        eval("$('#ic0208imagePreview').imuiDialog('open')");

    }

}

// 试听歌曲
function doPlaySound(_materialPath) {
    // 检查是否有0104的音乐在播放，如果有，则先停止0104的音乐播放,此方法针对于从0104点进场景音乐编辑后的情况
    // 判断展览模式
    if (_expositionVrFlag == "0") {
        var ic0104Krpano = document.getElementById("ic0104KrpanoSWFObject");
        if (ic0104Krpano != null && undefined != ic0104Krpano.get) {
            ic0104Krpano.call('stopallsounds();');
        }
    } else if (_expositionVrFlag == "1") {
        var vr0104Krpano = document.getElementById("vr0104KrpanoSWFObject");
        if (vr0104Krpano != null && undefined != vr0104Krpano.get) {
            vr0104Krpano.call('stopallsounds();');
        }
    }

    // 检查是否有0103的视频在播放，如果有，则先停止0103的视频播放,此方法针对于从0103点进场景音乐编辑后的情况
    var ic0103OldKrpano = document.getElementById("oldPanoId");
    var ic0103NewKrpano = document.getElementById("newPanoId");
    if (ic0103OldKrpano != null && undefined != ic0103OldKrpano.get) {
        ic0103OldKrpano.call('closevideo();');
    }
    if (ic0103NewKrpano != null && undefined != ic0103NewKrpano.get) {
        ic0103NewKrpano.call('closevideo();');
    }

    // 查看是否已有音乐用的karpano，如果没有再新建
    var newSoundPano = document.getElementById("ic0208SoundPano");
    if (newSoundPano == null || undefined == newSoundPano.get) {
        embedpano({
            swf : PanoConstants.VAL_EMBEDPANO_SWF,
            xml : "framework/panorama/template/sound/soundpreview.xml",
            id : "ic0208SoundPano",
            target : "ic0208SoundPreviewDiv",
            wmode : "opaque",
            flash : "only",
            passQueryParameters : true
        });
    }
    $("#ic0208SoundPreviewDiv").css('display', 'block');
    eval("$('#ic0208soundPreview').imuiDialog('open')");
    ic208Interval = setInterval(function() {
        startSound(_materialPath)
    }, 500);
}

// 设置音乐的播放事件
function startSound(_materialPath) {
    var krpano = document.getElementById("ic0208SoundPano");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    krpano.set('hotspot[start].onclick', 'playsound(s1,' + _materialPath + '?temp=' + Math.random() + ', 0,0); ');
    clearInterval(ic208Interval);
}

// 设置关闭试听画面，登录成功和关闭检索画面时停止音乐的操作
function stopSound() {
    var krpano = document.getElementById("ic0208SoundPano");
    if (krpano == null || undefined == krpano.get) {
        return false;
    }
    krpano.call('stopallsounds();');
}

// Ajax分页处理
function doAjaxPage() {
    var _ajaxUrl = getMemberContextPath('pano0208/doPage');
    var param = $("#ic0208Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            ic0208CreateListDataTable(data);
        }
    });
}

// 刷新一览数据
function ic0208CreateListDataTable(data) {
    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
        var isjson = typeof (data) == "object"
                && Object.prototype.toString.call(data).toLowerCase() == "[object object]" && !data.length;
        if (data.indexOf("指定条件的数据不存在。请改变索条件后再检索。") != -1) {
            imuiAlert('指定条件的数据不存在。请改变索条件后再检索。');
            // 判断是否为音频热点
            if ($('input[name="hotspotTypeChoice"]:checked').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC
                    || $('#ic0203hotspotTypeChoice').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                $('#ic0208LeftListDataTable tbody').html('');
            } else {
                $("#ic0208ListDataTable tbody").html('');
            }
            $('#pageShowInfo').hide();
            $('#ic0208pageDiv').hide();
            $('#ic0208Div li').hide();
            $('#ic0208pageTable').hide();
            return false;
        }

        var ic0208JsForm = JSON.parse(data);
        // 分页信息设定
        $('#ic0208Form_pageShowInfo').show();
        $('#ic0208Form_pageShowInfo').each(function(id, item) {
            $(this).prop('outerHTML', ic0208JsForm.pageShowInfos[0])
        });
        $('#ic0208Form_pageHiddenInfo').each(function(id, item) {
            $(this).prop('outerHTML', ic0208JsForm.pageShowInfos[1])

        });

        // 刷新一览数据
        var tbodyHtml = "";
        $(ic0208JsForm.materialInfo)
                .each(
                    function(index, item) {
                        var onclickStr = "setSelectedTrColor(this,'" + item.materialId;
                        onclickStr = onclickStr + "','" + item.materialPath;
                        onclickStr = onclickStr + "','" + item.gifWidth;
                        onclickStr = onclickStr + "','" + item.gifHeight;
                        onclickStr = onclickStr + "','" + item.gifFrameCount;
                        onclickStr = onclickStr + "','" + item.gifDelayTime;
                        onclickStr = onclickStr + "','" + item.hasPngImage;
                        onclickStr = onclickStr + "','" + item.flowTextInfo + "');"
                        tbodyHtml += '<tr id="ic0208Tr_' + index + '" style="height:10px;cursor: pointer;" onclick="'
                                + onclickStr + '">';
                        tbodyHtml += '<td title=' + item.materialId
                                + ' class="align-L valign-M selected" style="width:20%;">' + item.materialId + '</td>';
                        tbodyHtml += '<td title=' + item.materialName
                                + ' class="align-L valign-M selected" style="width:20%;">' + item.materialName
                                + '</td>';
                        tbodyHtml += '<td title=' + item.notes
                                + ' class="align-L valign-M selected" style="width:20%;">' + item.notes + '</td>';
                        // 如果是音频，则显示试听按钮，其他显示预览
                        if ($('#materialTypeId').val() == PanoConstants.VAL_MATERIAL_TYPE_SOUND) {
                            tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                    + '<input type="button" value="试听" class="imui-button imui-running-button" onclick="doPlaySound('
                                    + "'" + item.materialPath + "'" + ')"/>' + '</td>';
                        } else {
                            tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                    + '<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('
                                    + "'" + item.materialPath + "','" + item.flowTextInfo + "'" + ')"/>' + '</td>';
                        }
                        if ($('input[name="hotspotTypeChoice"]:checked').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC
                                || $('#ic0203hotspotTypeChoice').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
                            tbodyHtml += '<td class="align-C valign-M" style="width:15px;">'
                                    + '<input type="button" value="选择" class="imui-button imui-running-button" onclick="doSelect('
                                    + "'" + item.materialId + "','" + item.materialName + "','" + item.materialPath
                                    + "','" + item.notes + "'" + ')"/>' + '</td>';
                        }
                        tbodyHtml += "</tr>";
                    });
        if ($('input[name="hotspotTypeChoice"]:checked').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC
                || $('#ic0203hotspotTypeChoice').val() == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC) {
            $('#ic0208LeftListDataTable tbody').html(tbodyHtml);
        } else {
            $("#ic0208ListDataTable tbody").html(tbodyHtml);
        }
        $('#ic0208pageTable').show();
        $('#ic0208pageDiv').show();
    }
}

// 选择操作
function doSelect(_materialId, _materialName, _materialPath, _notes) {
    var _trId = 'ic0208TrRight_' + _materialId;
    if ($('#' + _trId).length != 0) {
        imuiAlert('该素材已经选择。');
        return false;
    }
    if ($("#ic0208SelectedListDataTable tbody tr").length == 2) {
        imuiAlert('素材有且只能选择2个。');
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
            + '_sortKey" type="text" value="" style="width:30px;text-align:right;" maxlength="1"/>' + '</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">'
            + '<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview(' + "'"
            + _materialPath + "'" + ')"/>' + '</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">'
            + '<input type="button" value="取消" class="imui-button imui-running-button" onclick="doCancel('
            + "'ic0208TrRight_" + _materialId + "'" + ')"/>' + '</td>';
    tbodyHtml += "</tr>";
    $("#ic0208SelectedListDataTable tbody").append(tbodyHtml);
}

// 取消操作
function doCancel(_tr) {
    $("#ic0208SelectedListDataTable tbody")[0].deleteRow($('#' + _tr)[0].rowIndex - 1);
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
