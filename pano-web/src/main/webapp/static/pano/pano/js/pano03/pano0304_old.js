
//================================================
//新建展览地图的html操作
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){
    
    // 返回处理
    $("#ic0304-button-finish-confirm").click(function() {
        ic0304DoBack();
    });
    
    // 隐藏删除按钮
    $('.imui-fileupload-delete ').hide();    

    // 登录处理
    $("#ic0304-confirm-button").click(function() {
        if($('#mapCheck').val()=='false' || $('#mapCheck').val() == ''){
            imuiAlert('请上传图片！');
            return false;
        }
        
        imuiConfirm('是否登录新地图?', '确认', function() {
            CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();
            
            var _ajaxUrl = getMemberContextPath('pano0304/doEntry');
            var param = $("#ic0304Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
             	CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#ic0304AddFinish').imuiDialog('open')");
                }
                
            });
        });
    });
    
    $(".imui-fileupload-add-text").find("span").each(function(){
        $(this).text("添加图片");
    })
 
});

//图片上传成功后的处理
function callbackIc0304Success(e,data){
   var _ajaxUrl=getMemberContextPath('pano0304/doGetTempFile');
   var param = $('#ic0304Form').serialize();
   jQuery.post(_ajaxUrl, param, function(data){
       if(CommonUtilJs.processAjaxSuccessAfter(data)){
           if (data != null && data != '') {
               $("#miniMap").attr('src',data + '?temp=' + Math.random());
               $("#miniMap").css('display','block');
               $('#mapCheck').val('true');
           }
        }
   });
   //只能上传一张图片，上传成功后，隐藏上传文件的按钮
   $('.imui-fileupload-add ').hide();
   // 上传成功后，隐藏取消按钮
   $('.imui-fileupload-cancel ').hide();
   
}


//图片删除成功后处理方法
function callbackIc0304Remove(e,data){
    $("#miniMap").attr('src','');
    $("#miniMap").css('display','none');
    var _ajaxUrl=getMemberContextPath('pano0304/doDeleteTempFile');
    var param = $('#ic0304Form').serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            $('#mapCheck').val('false');
        }
    });
    $('.imui-fileupload-add ').show();
    $('.imui-fileupload-cancel ').show();
}

//返回方法
function ic0304DoBack(){
	var _ajaxUrl = getMemberContextPath('pano0110/doVrFlag');
    var param = '';
    param = param + '&selectedExpositionId='+$('#expositionId').val();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
        	if(data=="0"){
	        	$('#expositionMapIdForIc0106').val($('#expositionMapId').text());
			    $("#ic0106-refresh-from-ic0304").submit();
        	}else{
        		$('#expositionMapIdForVr0106').val($('#expositionMapId').text());
        		$("#vr0106-refresh-from-ic0304").submit();
        	}
        }
        
    });
}

//文件上传前验证处理
function callbackIc0304BeforeSend(e, data) {
    var jsArg_e= e;
    var jsArg_Data= data;
    //检查上传文件是否符合格式
    var str=data.originalFiles[0].name;
    var result = str.substring(str.lastIndexOf("."));    
    result=result.toLowerCase();
    //图片
    if(result!=".jpg"&&result!=".png"&&result!=".swf"){
          imuiAlert('图片格式不支持,上传格式只支持(.jpg .png .swf)');
          $(".imui-fileupload-cancel").click();
          //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
          return false;  
    }
}
