//================================================
//展览编辑和删除
//================================================
$(document).ready(function(){
	//删除成功后的按钮
    $('#button-delete').click(function(){
    	$("#pageSubmitResult").val('deleteSuccess');
        $("#success-form").submit();
    });
    
    //如果展览有设置展览类型，则显示展览类型栏
    var radioboxSelected_2 = $("input[name='expositionType_show']:checked").val();
    if(radioboxSelected_2 != '' && radioboxSelected_2 != null){
        $('#expoTypeTr').show();
    }else{
        $('#expoTypeTr').hide();
    }

    //操作成功后的相关提示信息
    if ($('#hidPageSubmitResult').val() === "deleteSuccess") {
        imuiShowSuccessMessage('展览删除完成。');
    }
    if ($('#hidPageSubmitResult').val() === "updateSuccess") {
        imuiShowSuccessMessage('展览编辑完成。');
    }
    
    // 返回处理
    $("#back-button").click(function() {
        $('#back-form').submit();
    });
    
    // 展览登录处理
    $("#confirm-button").click(function() {
        imuiConfirm('是否登记展览?', '确认', function() {
            CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('pano0103/doUpdate');
            var param = $("#ic0103Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    $("#pageSubmitResult").val('updateSuccess');
                    $("#success-form").submit();
                }
                
            });
        });
    });
    
    //删除展览
    $("#delete-button").click(function() {
        imuiConfirm('是否删除展览?', '确认', function() {
            CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('pano0103/doDelete');
            var param = $("#ic0103Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                	eval("$('#ic0103Delete').imuiDialog('open')");
                }
            });
        });
    });
});
