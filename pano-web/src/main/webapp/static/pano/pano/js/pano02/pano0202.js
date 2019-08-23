//================================================
//场景编辑画面加载时的处理
//================================================
$(document).ready(function() {

    // 登录处理
    $("#btn_entry").click(function() {

        // 统一移除popover提示消息层
        $("#pano0202FormAdd").validate().destroy();
        // validate初始化
        $("#pano0202FormAdd").validate();
        // 移除验证规则
        $("#filesInput").rules("remove", 'accept');
        $("#filesInput").rules('remove', 'required');

        if (!$("#pano0202FormAdd").valid()) {
            return;
        }
        // 是否存在图片
        if ($("#imageBox li").length > 1 && $("#imageBox li").length != 7) {
            window.top.showErrorMessage('请上传6张场景照片。', false, 3);
            return;
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
            // form2js($("#pano0202FormAdd")[0]));
            var ajaxFormData = new FormData($("#pano0202FormAdd")[0]);

            $.ajax({
                url : getMemberContextPath('pano0202/doUpdate'),
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

    // 删除当前可视化信息
    $("#btn_delete").click(function() {

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
            // form2js($("#pano0202FormAdd")[0]));
            var ajaxFormData = new FormData($("#pano0202FormAdd")[0]);

            $.ajax({
                url : getMemberContextPath('pano0104/doDeletePano'),
                type : "post",
                processData : false,
                contentType : false,
                dataType : "json",
                data : ajaxFormData,
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        removepano("pano0202KrpanoOldObject");
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
    $('#filesInput').change(function() {

        // 选中的文件
        var filelength = $('#filesInput').get(0).files.length;
        // 已上传的图片文件
        var imglength = $("#imageBox li").length - 1;
        // 总文件超过6个的场合
        if ((filelength + imglength) != 6) {
            var errorMessage = '只能同时上传文件名以半角[ -、_ ]，';
            errorMessage = errorMessage + '加半角英文字母[r、d、f、l、b、u]结尾的6张图片，';
            errorMessage = errorMessage + '并且文件不能重复。';
            errorMessage = errorMessage + '<br>例：[pano-r.jpg、pano_r.jpg]';
            window.top.showErrorMessage(errorMessage, false, 3);
            return;
        }

        // 判断6张图尾字母是否有重复
        jQuery.each($('#filesInput').get(0).files, function(i, firstFile) {
            var firstFileName = firstFile.name;
            var firstPosition = firstFileName.substring(firstFileName.lastIndexOf(".") - 1);
            jQuery.each($('#filesInput').get(0).files, function(i, secondFile) {
                var secondFileName = secondFile.name;
                var secondPosition = secondFileName.substring(secondFileName.lastIndexOf(".") - 1);
                if (firstFileName != secondFileName && firstPosition == secondPosition) {
                    var errorMessage = '只能同时上传文件名以半角[ -、_ ]，';
                    errorMessage = errorMessage + '加半角英文字母[r、d、f、l、b、u]结尾的6张图片，';
                    errorMessage = errorMessage + '并且文件不能重复。';
                    errorMessage = errorMessage + '<br>例：[pano-r.jpg、pano_r.jpg]';
                    window.top.showErrorMessage(errorMessage, false, 3);
                    return false;
                }
            });
        });
        // 6张图且尾字母不重复的情况下，判断尾字母是否符合标准
        var arr = "fblrud";
        jQuery.each($('#filesInput').get(0).files, function(i, firstFile) {
            var firstFileName = firstFile.name;
            var result = firstFileName.substring(firstFileName.lastIndexOf(".") - 1, firstFileName.lastIndexOf("."));
            if (arr.indexOf(result) == -1) {

                var errorMessage = '只能同时上传文件名以半角[ -、_ ]，';
                errorMessage = errorMessage + '加半角英文字母[r、d、f、l、b、u]结尾的6张图片，';
                errorMessage = errorMessage + '并且文件不能重复。';
                errorMessage = errorMessage + '<br>例：[pano-r.jpg、pano_r.jpg]';
                window.top.showErrorMessage(errorMessage, false, 3);
                return false;
            }

        });

        // 做成FormData对象
        var ajaxFormData = new FormData($("#pano0202FormUpdate")[0]);
        var checkErrors = [];
        // 从选中的文件中过滤满足条件的图片
        if (filelength > 0) {
            // 图片类型正则表达式
            var fileTypeRegExp = new RegExp('(\\.)(jpg)$', "i");
            jQuery.each($('#filesInput').get(0).files, function(i, file) {
                if (!fileTypeRegExp.test(file.name)) {
                    checkErrors.push('照片[' + file.name + ']的格式不正确，请重新选择。');
                } else {
                    var fileSize = file.size / (1024 * 1024);
                    // 单张图片文件不能超过2M
                    if (fileSize > 5) {
                        checkErrors.push('照片[' + file.name + ']超过5M，请重新选择。');
                    } else {
                        ajaxFormData.append('panoImg', file);
                    }
                }
            });
        }
        // 上传图片为空的场合
        if (ajaxFormData.get('panoImg') == null) {
            if (checkErrors.length > 0) {
                window.top.showErrorMessage(checkErrors, false, 0);
            }
            return;
        }

        var targetUrl = 'pano0202/doUpload';

        $.ajax({
            type : 'post',
            processData : false,
            contentType : false,
            data : ajaxFormData,
            dataType : 'json',
            url : getMemberContextPath(targetUrl),
            success : function(result) {
                // 成功或部分失败的场合，统一做下面的处理(success === true,保持前后台一致)
                if (result.success === true) {
                    if (result.rows != null && result.rows.length > 0) {
                        // 照片循环展示
                        jQuery.each(result.rows, function(i, filePath) {
                            appendImg(getContextPath(filePath), filePath);
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
                        // 变更后的场景
                        removepano("pano0202KrpanoNewObject");
                        var xmlfilePath = "compare_file/pano0202/";
                        xmlfilePath = xmlfilePath + ajaxFormData.get('expositionId');
                        xmlfilePath = xmlfilePath + "/panoramas/";
                        xmlfilePath = xmlfilePath + ajaxFormData.get('panoramaId');
                        xmlfilePath = xmlfilePath + "/show_l.xml";
                        var xmlSessionFilePath = window.top.getSessionFileEditPath(xmlfilePath);
                        embedpano({
                            swf : PanoConstants.VAL_EMBEDPANO_SWF,
                            id : 'pano0202KrpanoNewObject',
                            xml : xmlSessionFilePath,
                            target : "pano0202NewPano",
                            wmode : "transparent",
                            html5 : "prefer",
                            passQueryParameters : true
                        });
                    }
                }
            }
        })
    });

    // 变更前场景预览
    var ajaxFormUpdateData = new FormData($("#pano0202FormUpdate")[0]);
    // removepano("pano0202KrpanoOldObject");
    var xmlfilePath = "pano0202/";
    xmlfilePath = xmlfilePath + ajaxFormUpdateData.get('expositionId');
    xmlfilePath = xmlfilePath + "/panoramas/";
    xmlfilePath = xmlfilePath + ajaxFormUpdateData.get('panoramaId');
    xmlfilePath = xmlfilePath + "/show_l.xml";
    var xmlSessionFilePath = window.top.getSessionFileEditPath(xmlfilePath);
    embedpano({
        swf : PanoConstants.VAL_EMBEDPANO_SWF,
        id : 'pano0202KrpanoOldObject',
        xml : xmlSessionFilePath,
        target : "pano0202OldPano",
        wmode : "transparent",
        html5 : "prefer",
        passQueryParameters : true
    });

    // // 如果已是首个场景，让勾选框选中
    // if($('#isStartScene').val() == '1'){
    // $("input[name='startSceneFlag']").attr("checked","true");
    // }
    //    
    // // 如果该场景有音乐，让音乐勾选框选中
    // if($('#soundName').text() != null && $('#soundName').text() != ''){
    // $("input[id='ic0202PanoMusicSelect']").attr("checked","true");
    // $('#edittMusicButton').show();
    // }else{
    // $('#edittMusicButton').hide();
    // }

    // 场景音乐勾选框
    $('#ic0202PanoMusicSelect').click(function() {
        if ($("#ic0202PanoMusicSelect").attr("checked")) {
            $('#edittMusicButton').show();
        } else {
            $('#edittMusicButton').hide();
            $('#soundName').text('');
        }
    })

    // 登录按钮
    $("#button-ic0202-confirm").click(function() {
        var ic0202PanoramaName = $('#ic0202panoramaName').val();
        if (ic0202PanoramaName == null || ic0202PanoramaName == '') {
            imuiAlert('请输入场景名称！');
            return;
        }

        if ($("#ic0202PanoMusicSelect").attr("checked")) {
            if ($('#soundName').text() == '' || $('#soundName').text() == null) {
                imuiAlert('请选择音乐文件！');
                return;
            }
        }

        imuiConfirm('是否登录场景信息?', '确认', function() {
            CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('pano0202/doUpdate');
            var param = $("#ic0202Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data) {
                CommonUtilJs.removeMask();
                if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                    eval("$('#ic0202UpdFinish').imuiDialog('open')");
                }
            });
        });

    });

    // 删除当前可视化信息
    $('#button-delete-map').click(function() {

        if ($('#flagFromParentPage').val() == 'ic0104') {
            imuiConfirm('是否删除当前可视化信息?', '确认', function() {
                CommonUtilJs.loadMask();
                var panoramaIdForDelete = $('#panoramaIdForDelete').val();
                if (panoramaIdForDelete != '' && panoramaIdForDelete != null) {
                    $('#deletePanorama').val(panoramaIdForDelete);
                }
                var _ajaxUrl = getMemberContextPath('pano0104/doDeletePano');
                var param = $("#ic0104-delete-map").serialize();
                jQuery.post(_ajaxUrl, param, function(data) {
                    CommonUtilJs.removeMask();
                    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                        eval("$('#ic0201DeleteFinishGotoIc0104').imuiDialog('open')");
                        $('#ic0104DeleteFlgFromIc0202').val('yes');
                    }
                });
            });
        }

        if ($('#flagFromParentPage').val() == 'ic0206') {
            imuiConfirm('是否删除当前可视化信息?', '确认', function() {
                CommonUtilJs.loadMask();
                var panoramaIdForDelete = $('#panoramaIdForDelete').val();
                if (panoramaIdForDelete != '' && panoramaIdForDelete != null) {
                    $('#deletePanorama').val(panoramaIdForDelete);
                }
                var _ajaxUrl = getMemberContextPath('pano0206/doDeleteScene');
                var param = $("#ic0206-delete-map").serialize();
                jQuery.post(_ajaxUrl, param, function(data) {
                    CommonUtilJs.removeMask();
                    if (CommonUtilJs.processAjaxSuccessAfter(data)) {
                        eval("$('#ic0201DeleteFinishGotoIc0206').imuiDialog('open')");
                    }
                });
            });
        }

    });

});

// 打开选取声音素材的画面
function doSetPanoBackGroundSound() {
    $("#setSound").imuiPageDialog({
        url : getMemberContextPath('pano0208'),
        title : '设置音乐',
        modal : true,
        width : 1000,
        height : 700,
        parameter : {
            dataFromIc0202 : "yes",
            expositionId : $('#ic0202expositionId').val(),
            panoramaId : $('#ic0202panoramaId').val(),
            materialTypeId : PanoConstants.VAL_MATERIAL_TYPE_SOUND
        }
    });
    return false;
}
// 从设定声音素材的画面获取返回的素材ID
function ic0202GetDataFromIc0208(_materialId) {
    // 关闭0208试听中的音乐
    stopSound();
    $('#materialIdFromIc0208').val(_materialId)
    // 取得最新声音名
    var _ajaxUrl = getMemberContextPath('pano0202/doRefreshSoundName');
    var param = $("#ic0202Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data) {
        if (CommonUtilJs.processAjaxSuccessAfter(data)) {
            if (data != '' && data != null) {
                $('#soundName').text(data);
            }
        }
    });
}

// 现实缩略图
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

    var targetUrl = 'pano0202/doDeleteImage';
    // 做成FormData对象
    var ajaxFormData = new FormData($("#pano0202FormUpdate")[0]);
    ajaxFormData.append("deleteImgPath", imgFile.id);

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
            }
        }
    })

}
