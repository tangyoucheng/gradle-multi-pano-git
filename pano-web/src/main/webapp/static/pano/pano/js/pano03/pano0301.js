var pano0104krpano = document.getElementById("pano0104KrpanoSWFObject");
// ================================================
// HTML加载时的处理
// 【入力】
// 【返却】
// 【作成】
// 【更新】
// 【概要】
// ================================================
$(document).ready(
    function() {
        // 检查展览模式
        expositionVrFlag();

        // 隐藏编辑区域
        hideEdit();

        $("#materialTypeId").change(
            function() {
                // 隐藏编辑区域
                hideEdit();
                var krpano = document.getElementById("pano0301KrpanoSWFObject");

                var materialTypeId = $("#materialTypeId").val();
                // 不等于 文字浮动信息层 清空
                if (materialTypeId != PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
                    $('#textflowInfoTexbox').val('');
                    $('#textDiv').text('');
                }
                // 图文以外的场合
                if (materialTypeId != PanoConstants.VAL_MATERIAL_TYPE_IMAGE_TEXT) {
                    $('#textInfo').val('');
                }
                // 等于 视频 或 音频
                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND
                        || materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_VIDEO) {
                    $("#div_sounds").show();
                    $('#material_file_upload_div').show();
                    // 文字浮动信息层
                } else if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
                    $('#div_text_flow_info').show();
                    $("#textDiv").show();
                    $('#div_previewText').show();
                } else {
                    $('#material_file_upload_div').show();
                    $('#div_previewMateria').show();
                    if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_IMAGE_TEXT) {
                        $("#div_text_info").show(); // 图文文字信息
                    }
                }
            });

        // 登录处理
        $("#btn_entry").click(function() {

            // 统一移除popover提示消息层
            $("#pano0301FormAdd").validate().destroy();
            // validate初始化
            $("#pano0301FormAdd").validate();

            // 移除验证规则
            $("#filesInput").rules("remove", 'accept');
            $("#filesInput").rules('remove', 'required');

            if (!$("#pano0301FormAdd").valid()) {
                return;
            }

            var materialType = $("#materialTypeId").val();
            // 在所选种类为文字浮动信息时，上传图片的check不做
            if (materialType != PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
                if ($("#imageBox li").length != 2) {
                    var errorMessage = '请上传文件。';
                    window.top.showErrorMessage(errorMessage, false, 3);
                    return false;
                }
            }

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
                var ajaxFormData = new FormData($("#pano0301FormAdd")[0]);

                $.ajax({
                    url : getMemberContextPath('pano0301/doEntry'),
                    type : "post",
                    processData : false,
                    contentType : false,
                    dataType : "json",
                    data : ajaxFormData,
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

        // 选择图片事件
        $('#filesInput').change(
            function() {

                var materialTypeId = $("#materialTypeId").val();

                // 选中的文件
                var filelength = $('#filesInput').get(0).files.length;
                // 已上传的图片文件
                var imglength = $("#imageBox li").length - 1;
                // 总文件超过6个的场合
                if ((filelength + imglength) != 1) {
                    var errorMessage = '只能上传一个文件。';
                    window.top.showErrorMessage(errorMessage, false, 3);
                    return;
                }

                // 做成FormData对象
                var ajaxFormData = new FormData($("#pano0301FormAjaxSubmit")[0]);
                var checkErrors = [];
                // 从选中的文件中过滤满足条件的图片
                if (filelength > 0) {
                    // 文件类型正则表达式
                    var fileSoundTypeRegExp = new RegExp('(\\.)(mp3)$', "i");
                    var fileVideoTypeRegExp = new RegExp('(\\.)(mp4)$', "i");
                    var fileImageTypeRegExp = new RegExp('(\\.)(jpg|jpeg|png|gif|swf)$', "i");
                    jQuery.each($('#filesInput').get(0).files, function(i, file) {
                        // 文字浮动信息层的场合
                        if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
                            return;
                        } else if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND
                                && !fileSoundTypeRegExp.test(file.name)) {// 声音的场合
                            var errorMessage = '音频格式不支持,上传格式只支持(.mp3)';
                            checkErrors.push(errorMessage);
                            return;
                        } else if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_VIDEO
                                && !fileVideoTypeRegExp.test(file.name)) { // 视频的场合
                            var errorMessage = '视频格式不支持,上传格式只支持(.mp4)';
                            errorMessage = errorMessage + '<br>上传格式只支持(.jpg .png .swf .gif)';
                            checkErrors.push(errorMessage);
                            return;
                        } else {
                            if (!fileImageTypeRegExp.test(file.name)) {
                                var errorMessage = '照片[' + file.name + ']的格式不正确，请重新选择。';
                                errorMessage = errorMessage + '<br>上传格式只支持(.jpg .png .swf .gif)';
                                checkErrors.push(errorMessage);
                                return;
                            }
                        }

                        ajaxFormData.append('uploadFile', file);

                        // var fileSize = file.size / (1024 * 1024);
                        // // 单张图片文件不能超过2M
                        // if (fileSize > 5) {
                        // checkErrors.push('文件[' + file.name + ']超过5M，请重新选择。');
                        // } else {
                        // ajaxFormData.append('uploadFile', file);
                        // }
                    });
                }
                // 上传图片为空的场合
                if (ajaxFormData.get('uploadFile') == null) {
                    if (checkErrors.length > 0) {
                        window.top.showErrorMessage(checkErrors, false, 0);
                    }
                    return;
                }

                var targetUrl = 'pano0301/doUpload';

                $.ajax({
                    type : 'post',
                    processData : false,
                    contentType : false,
                    data : ajaxFormData,
                    dataType : 'json',
                    url : getMemberContextPath(targetUrl),
                    success : function(result) {
                        // 成功或部分失败的场合，统一做下面的处理(success ===
                        // true,保持前后台一致)
                        if (result.success === true) {
                            if (result.rows != null && result.rows.length > 0) {
                                // 照片循环展示
                                jQuery.each(result.rows, function(i, filePath) {
                                    appendImg(getContextPath(filePath.thumbFile), filePath.thumbFile);
                                });
                            }
                            // 获取后台验证信息
                            if (result.obj != null && result.obj.length > 0) {
                                jQuery.each(result.obj, function(i, checkInfo) {
                                    checkErrors.push(checkInfo);
                                });
                            }
                            // 显示信息
                            if (checkErrors.length > 0) {
                                window.top.showErrorMessage(checkErrors, false, 0);
                            } else {
                                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND) {
                                    // 素材为音乐的情况
                                    $('#div_sounds').show();
                                    var krpano = document.getElementById("pano0301KrpanoSWFObject");
                                    // 设置音乐播放按钮的click事件
                                    if (_expositionVrFlag == "0") {
                                        krpano.set('hotspot[start].onclick', 'playsound(s1,'
                                                + result.rows[0].uploadFile + '?temp=' + Math.random() + ', 0,0); ');
                                    } else {
                                        krpano.set('hotspot[start].onclick', 'playsound(s1,'
                                                + result.rows[0].uploadFile + '?temp=' + Math.random() + ', 0,0); ');
                                    }

                                } else if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_VIDEO) {
                                    // 素材为视频的情况
                                    $('#div_sounds').show();
                                    var krpano = document.getElementById("pano0301KrpanoSWFObject");
                                    var videoUrl = getMemberContextPath("" + result.rows[0].uploadFile);
                                    // 设定播放器皮肤尺寸,播放视频
                                    if (_expositionVrFlag == "0") {
                                        krpano.set('hotspot[start].onclick', 'suitableScreen();openvideo(' + videoUrl
                                                + ');');
                                    } else {
                                        krpano.set('hotspot[start].onclick', 'suitableScreen();openvideo(' + videoUrl
                                                + ');');
                                    }
                                    krpano.set('hotspot[stop].onclick', 'closevideo();');
                                } else {
                                    $("#tagPicture").attr('src',
                                        getContextPath(result.rows[0].uploadFile) + '?temp=' + Math.random());
                                    $("#tagPicture").css('display', 'block');
                                    $('#div_previewMateria').show();
                                    $('#pictureCheck').val('true');
                                    $("#div_sounds").hide();
                                }
                            }
                        }
                    }
                })
            });
    });

// 显示缩略图
function appendImg(src, deleteSrc) {
    var imgHtml = '<li class="success_show">';
    imgHtml = imgHtml + '<div class="img_btm_bg clearfix close" id="' + deleteSrc;
    imgHtml = imgHtml + '" onclick="removeImg(this)">';
    imgHtml = imgHtml + '<span class="delete_text" >×</span>';
    imgHtml = imgHtml + '</div>';
    imgHtml = imgHtml + '<div class="room_img_show">';
    imgHtml = imgHtml + '<img src="' + src + '" />';
    imgHtml = imgHtml + '</div>';
    imgHtml = imgHtml + '</li>';
    var target = $("#imageBox");
    target.prepend(imgHtml);
}

// 图片删除
function removeImg(imgFile) {

    var targetUrl = 'pano0301/doDeleteFile';
    // 做成FormData对象
    var ajaxFormData = new FormData($("#pano0301FormAjaxSubmit")[0]);
    ajaxFormData.append("deleteFilePath", imgFile.id);

    $.ajax({
        type : 'post',
        processData : false,
        contentType : false,
        data : ajaxFormData,
        dataType : 'json',
        url : getMemberContextPath(targetUrl),
        success : function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                // 画面移除图片
                $(imgFile).parent(".success_show").remove();

                var materialTypeId = $("#materialTypeId").val();
                // 如果是音乐，点击删除后停止播放
                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND) {
                    var pano0103krpano = document.getElementById("pano0301KrpanoSWFObject");
                    pano0103krpano.call('stopalldiv_sounds();');
                }

                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_VIDEO) {
                    var pano0103krpano = document.getElementById("pano0301KrpanoSWFObject");
                    pano0103krpano.call('closevideo();');
                }

                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND) {
                    $('#pictureCheck').val('');
                }
                $("#tagPicture").attr('src', '');
                $("#tagPicture").css('display', 'none');
            }
        }
    })

}

// 浮动信息(文字)预览操作
function doPreviewTextinfo() {
    $("#textDiv").text($('#textflowInfoTexbox').val());
}

// 播放时，展览为非vr展时，先停止IC0104音乐播放
function stopPano0104Sounds() {
    var pano0104krpano = document.getElementById("pano0104KrpanoSWFObject");
    if (pano0104krpano != null && undefined != pano0104krpano.get) {
        pano0104krpano.call('stopalldiv_sounds();');
    }
}
// 播放时，展览为vr展时，先停止VR0104音乐播放
function stopVr0104Sounds() {
    var vr0104krpano = document.getElementById("vr0104KrpanoSWFObject");
    if (vr0104krpano != null && undefined != vr0104krpano.get) {
        vr0104krpano.call('stopalldiv_sounds();');
    }
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
// 隐藏编辑区域
function hideEdit() {
    $('#material_file_upload_div').hide(); // 文件上传
    $('#div_text_flow_info').hide(); // 浮动信息(文字)内容
    $('#div_previewMateria').hide(); // 预览
    $('#div_previewText').hide(); // 预览文字
    $("#div_sounds").hide(); // 音频预览
    $("#div_text_info").hide(); // 图文文字信息
}