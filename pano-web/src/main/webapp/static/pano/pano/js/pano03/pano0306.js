//【概要】
//================================================

var currentExpositionMapPath = "";

$(document).ready(function() {

    // 登录处理
    $("#button-pano0306-update").click(function() {

        // 统一移除popover提示消息层
        $("#pano0306FormAdd").validate().destroy();
        // validate初始化
        $("#pano0306FormAdd").validate();
        // 移除验证规则
        $("#filesInput").rules("remove", 'accept');
        $("#filesInput").rules('remove', 'required');

        if (!$("#pano0306FormAdd").valid()) {
            return;
        }
        // 是否存在图片
        if ($("#imageBox li").length > 1 && $("#imageBox li").length != 2) {
            window.top.showErrorMessage('请上传1张场景照片。', false, 3);
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
            // form2js($("#pano0306FormAdd")[0]));
            var ajaxFormData = new FormData($("#pano0306FormAdd")[0]);
            ajaxFormData.append("pano0306ExpositionMapPath", currentExpositionMapPath);

            $.ajax({
                url : getMemberContextPath('pano0306/doUpdate'),
                type : "post",
                processData : false,
                contentType : false,
                dataType : "json",
                data : ajaxFormData,
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // 关闭确认对话框
                        window.top.layer.close(currentConfirmIndex);
                        // 自页面关闭
                        var index = parent.layer.getFrameIndex(window.name);
                        window.top.layer.close(index);
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

    // 删除操作
    $("#button-pano0306-delete").click(function() {

        // 询问框
        var currentConfirmIndex = window.top.layer.confirm('正登录当前页面的数据，是否继续？', {
            icon : 3,
            title : '提示信息',
            btn : [ '确认', '取消' ]
        // 按钮
        }, function() {
            window.top.layer.close(currentConfirmIndex);

            var ajaxSubmitFormData = form2js($("#pano0306FormUpdate")[0]);

            $.ajax({
                type : 'post',
                traditional : true,
                data : ajaxSubmitFormData,
                dataType : 'json',
                url : getMemberContextPath('pano0306/doDelete'),
                success : function(result) {
                    if (CommonUtilJs.processAjaxSuccessAfter(result)) {
                        // 关闭确认对话框
                        window.top.layer.close(currentConfirmIndex);
                        // 自页面关闭
                        var index = parent.layer.getFrameIndex(window.name);
                        window.top.layer.close(index);
                    }
                }
            // ,
            // error : function() {
            // }
            })

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
        // 总文件超过1个的场合
        if ((filelength + imglength) != 1) {
            var errorMessage = '只能上传一个文件。';
            window.top.showErrorMessage(errorMessage, false, 3);
            return;
        }

        // 做成FormData对象
        var ajaxFormData = new FormData($("#pano0306FormUpdate")[0]);
        ajaxFormData.append('expositionMapId', $("#txt_expositionMapId").val());
        var checkErrors = [];
        // 从选中的文件中过滤满足条件的图片
        if (filelength > 0) {
            // 图片类型正则表达式
            var fileTypeRegExp = new RegExp('(\\.)(jpg|jpeg|png)$', "i");
            jQuery.each($('#filesInput').get(0).files, function(i, file) {
                if (!fileTypeRegExp.test(file.name)) {
                    checkErrors.push('照片[' + file.name + ']的格式不正确，请重新选择。');
                } else {
                    var fileSize = file.size / (1024 * 1024);
                    // 单张图片文件不能超过2M
                    if (fileSize > 5) {
                        checkErrors.push('照片[' + file.name + ']超过5M，请重新选择。');
                    } else {
                        ajaxFormData.append('mapImg', file);
                    }
                }
            });
        }
        // 上传图片为空的场合
        if (ajaxFormData.get('mapImg') == null) {
            if (checkErrors.length > 0) {
                window.top.showErrorMessage(checkErrors, false, 0);
            }
            return;
        }

        var targetUrl = 'pano0306/doUpload';

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
                    }
                }
            }
        })
    });
});

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
    // 保存当前文件的相对路径
    currentExpositionMapPath = src.split('pano0306/')[1];
}

// 图片删除
function removeImg(imgFile) {

    var targetUrl = 'pano0306/doDeleteImage';
    // 做成FormData对象
    var ajaxFormData = new FormData($("#pano0306FormUpdate")[0]);
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
                currentExpositionMapPath = "";
            }
        }
    })

}
