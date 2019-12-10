//================================================
//HTML加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function() {

    // 隐藏编辑区域
    hidePano0303ImageArea();
});

// 隐藏编辑区域
function hidePano0303ImageArea() {

    var materialTypeId = $("#materialTypeId").val();

    // 普通热点，场景切换热点，logo热点的场合，可以上传帧动画图片。
    if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE
            || materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_IMAGE
            || materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_LOGO) {
        $("#div_old_sprite_info").show(); // 竖直排列帧动画信息
        $("#div_sprite_info").show(); // 竖直排列帧动画信息
    }

    if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_IMAGE_TEXT) {
        $("#div_text_info").show(); // 图文文字信息
    }

    // 选择图片事件
    $('#imageFilesInput').change(
        function() {

            var materialTypeId = $("#materialTypeId").val();

            // 选中的文件
            var filelength = $('#imageFilesInput').get(0).files.length;
            // 已上传的图片文件
            var imglength = $("#imageBox li").length - 1;
            // 总文件超过6个的场合
            if ((filelength + imglength) != 1) {
                var errorMessage = '只能上传一个文件。';
                window.top.showErrorMessage(errorMessage, false, 3);
                return;
            }

            // 做成FormData对象
            var ajaxFormData = new FormData($("#pano0303FormAjaxSubmit")[0]);
            // 素材类型ID
            ajaxFormData.append('materialTypeId', materialTypeId);
            var checkErrors = [];
            // 从选中的文件中过滤满足条件的图片
            if (filelength > 0) {
                // 文件类型正则表达式
                var fileImageTypeRegExp = new RegExp('(\\.)(jpg|jpeg|png|gif|swf)$', "i");
                jQuery.each($('#imageFilesInput').get(0).files, function(i, file) {
                    if (!fileImageTypeRegExp.test(file.name)) {
                        var errorMessage = '照片[' + file.name + ']的格式不正确，请重新选择。';
                        errorMessage = errorMessage + '<br>上传格式只支持(.jpg .png .swf .gif)';
                        checkErrors.push(errorMessage);
                        return;
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

            var targetUrl = 'pano0303/doUpload';

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
                            var imageSrc = getContextPath(result.rows[0].uploadFile);
                            imageSrc = imageSrc + '?temp=';
                            imageSrc = imageSrc + Math.random();
                            $("#tagPicture").attr('src', imageSrc);
                            $("#tagPicture").css('display', 'block');
                            $('#div_previewMateria').show();
                            $('#pictureCheck').val('true');
                            $("#div_sounds").hide();

                            // 普通热点，场景切换热点，logo热点的场合，可以上传帧动画图片。
                            if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE
                                    || materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_IMAGE
                                    || materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_LOGO) {
                                $('#txt_gifWidth').val(result.rows[0].gifWidth);
                                $('#txt_gifHeight').val(result.rows[0].gifHeight);
                                $('#txt_gifFrameCount').val(result.rows[0].gifFrameCount);
                            }
                        }
                    }
                }
            })
        });
}