//================================================
//HTML加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function() {

    // 选择图片事件
    $('#soundFilesInput').change(function() {

        var materialTypeId = $("#materialTypeId").val();

        // 选中的文件
        var filelength = $('#soundFilesInput').get(0).files.length;
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
            var fileSoundTypeRegExp = new RegExp('(\\.)(mp3)$', "i");
            jQuery.each($('#soundFilesInput').get(0).files, function(i, file) {
                // 文字浮动信息层的场合
                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND && !fileSoundTypeRegExp.test(file.name)) {// 声音的场合
                    var errorMessage = '音频格式不支持,上传格式只支持(.mp3)';
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
                        if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_SOUND) {
                            // 素材为音乐的情况
                            $('#div_sounds').show();
                            var krpano = document.getElementById("pano0303KrpanoSWFObject");
                            // 设置音乐播放按钮的click事件
                            var startClick = 'playsound(s1,';
                            startClick = startClick + result.rows[0].uploadFile;
                            startClick = startClick + '?temp=';
                            startClick = startClick + Math.random() + ', 0,0); ';
                            krpano.set('hotspot[start].onclick', startClick);

                        }
                    }
                }
            }
        })
    });
});
