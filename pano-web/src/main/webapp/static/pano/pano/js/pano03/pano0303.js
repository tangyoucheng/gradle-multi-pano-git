//================================================
//HTML加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function() {

    // 登录处理
    $("#btn_entry").click(function() {

        // 统一移除popover提示消息层
        $("#pano0303FormAdd").validate().destroy();
        // validate初始化
        $("#pano0303FormAdd").validate();

        // 移除验证规则
        $("#filesInput").rules("remove", 'accept');
        $("#filesInput").rules('remove', 'required');

        // 延迟入力值制御
        $("#txt_oldGifDelayTime").attr('required', false);
        $("#txt_oldGifDelayTime").rules('remove', 'required');
        if ($('#txt_oldGifFrameCount').val() != null && $('#txt_oldGifFrameCount').val().length > 0) {
            // 红色边框样式所需的设定
            $("#txt_oldGifDelayTime").attr('required', '');
            $("#txt_oldGifDelayTime").rules('add', {
                required : true
            });
        }

        // 延迟入力值制御
        $("#txt_gifDelayTime").attr('required', false);
        $("#txt_gifDelayTime").rules('remove', 'required');
        if ($('#txt_gifFrameCount').val() != null && $('#txt_gifFrameCount').val().length > 0) {
            // 红色边框样式所需的设定
            $("#txt_gifDelayTime").attr('required', '');
            $("#txt_gifDelayTime").rules('add', {
                required : true
            });
        }

        if (!$("#pano0303FormAdd").valid()) {
            return;
        }

        var materialType = $("#materialTypeId").val();
        
        // 素材变更页面，图片不变更的场合不错验证
        // 在所选种类为文字浮动信息时，上传图片的check不做
        // if (materialType != PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
        // if ($("#imageBox li").length != 2) {
        // var errorMessage = '请上传文件。';
        // window.top.showErrorMessage(errorMessage, false, 3);
        // return false;
        // }
        // }

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
            // form2js($("#pano0303FormAdd")[0]));
            var ajaxFormData = new FormData($("#pano0303FormAdd")[0]);

            $.ajax({
                url : getMemberContextPath('pano0303/doUpdate'),
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

    var targetUrl = 'pano0303/doDeleteFile';
    // 做成FormData对象
    var ajaxFormData = new FormData($("#pano0303FormAjaxSubmit")[0]);
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
                    var pano0103krpano = document.getElementById("pano0303KrpanoSWFObject");
                    pano0103krpano.call('stopalldiv_sounds();');
                }

                if (materialTypeId == PanoConstants.VAL_MATERIAL_TYPE_VIDEO) {
                    var pano0103krpano = document.getElementById("pano0303KrpanoSWFObject");
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
