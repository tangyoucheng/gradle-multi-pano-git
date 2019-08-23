var ic0104krpano = document.getElementById("ic0104KrpanoSWFObject");
//================================================
//HTML加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){
	//检查展览模式
	expositionVrFlag();
    if ($('#hidPageSubmitResult').val() === "success") {
        imuiShowSuccessMessage('素材登记完成。');
    }
    
    　// 隐藏删除按钮
    $('.imui-fileupload-delete ').hide();
    // 隐藏编辑区域
    hideEdit();
     
     $("input[name='materialTypeId']").click(function(){  
    	 // 隐藏编辑区域
         hideEdit();
         var krpano = document.getElementById("ic0301KrpanoSWFObject");
         
         var _selectedValue = $("input[name='materialTypeId']:checked").val();
         // 不等于 文字浮动信息层 清空
         if(_selectedValue != CdicConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT){
             $('#textflowInfoTexbox').val('');
             $('#textDiv').text('');
         }
         // 图文以外的场合
         if(_selectedValue != CdicConstants.VAL_MATERIAL_TYPE_IMAGE_TEXT){
             $('#textInfo').val('');
         }
         // 等于 视频 或 音频
         if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_SOUND || _selectedValue == CdicConstants.VAL_MATERIAL_TYPE_VIDEO){
             $("#sounds").show();
             $('#material_file_upload_tr').show();
            // 文字浮动信息层
         } else if (_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT) {
        	 $('#text_flow_info_tr').show();
        	 $("#textDiv").show();
        	 $('#previewText').show();
         } else {
        	 $('#material_file_upload_tr').show();
        	 $('#previewMateria').show();
        	 if (_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_IMAGE_TEXT) {
        		 $("#text_info_tr").show(); // 图文文字信息
        	 }
         }
         //点击radiobox删除文件
         $('.imui-fileupload-delete').click();
         callbackIc0301Remove('','');
     });

    // 返回按钮
    $("#ic0301-button-finish-confirm").click(function() {
        ic0301DoBack();
    });
    // 登录处理
    $("#confirm-button").click(function() {  
        
        var _selectedValue = $("input[name='materialTypeId']:checked").val();
        var _selectedFolderType = $("input[name='materialBelongType']:checked").val();
        var _materialName = $('#materialNameTexbox').val();
        var _materialId = $('#materialIdTexbox').val();
        //在所选种类为文字浮动信息时，上传图片的check不做
        if(_selectedValue != CdicConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT){
            if($('#pictureCheck').val() != 'true'){
                imuiAlert("请上传文件");
                return false;
            }
        }
        if(_materialId == '' || _materialId == null){
            imuiAlert("请输入素材ID");
            return false;
        }
        if(_materialName == '' || _materialName == null){
            imuiAlert("请输入素材名称");
            return false;
        }
        if(_selectedValue == '' || _selectedValue == null){
            imuiAlert("请选择一个素材种类");
            return false;
        }
        if($('#openFromIc0104').val() == 'yes'){
            if(_selectedFolderType == '' || _selectedFolderType == null){
                imuiAlert("请选择素材归属");
                return false;
            }
        }
        imuiConfirm('是否登录素材信息?', '确认', function() {
            CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();        
            var _ajaxUrl = getContextPath('/ic03/ic0301/doEntry');
            var materialId = $('#materialIdTexbox').val();
            $('#ic0301materialId').val(materialId)
            var param = $("#ic0301Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                 CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxValidateError(data)){
                    eval("$('#ic0301AddFinish').imuiDialog('open')");
                }    
            });
        });
    });
    
    // 返回处理
    $("#back-button").click(function() {
    	if($('#openFromIc0104').val() == 'yes'){
            $('#popupPage').imuiPageDialog('close');
            var ic0103krpano = document.getElementById("ic0301KrpanoSWFObject");
            if (ic0103krpano != null && undefined != ic0103krpano.get){
                ic0103krpano.call('stopallsounds();');
                ic0103krpano.call('closevideo();');
            }
        }else{
        	$("#back-form").submit();
        }
    });
});

//浮动信息(文字)预览操作
function doPreviewTextinfo(){
  	  $("#textDiv").text($('#textflowInfoTexbox').val());
}

//播放时，展览为非vr展时，先停止IC0104音乐播放
function stopIc0104Sounds(){
   var ic0104krpano = document.getElementById("ic0104KrpanoSWFObject");
   if (ic0104krpano != null && undefined != ic0104krpano.get){
       ic0104krpano.call('stopallsounds();');
   }
}
//播放时，展览为vr展时，先停止VR0104音乐播放
function stopVr0104Sounds(){
   var vr0104krpano = document.getElementById("vr0104KrpanoSWFObject");
   if (vr0104krpano != null && undefined != vr0104krpano.get){
       vr0104krpano.call('stopallsounds();');
   }
}

var _selectedValue;
//图片上传成功后的处理
function callbackIc0301Success(e,data){
    _selectedValue = $("input[name='materialTypeId']:checked").val();
    if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_SOUND || _selectedValue == CdicConstants.VAL_MATERIAL_TYPE_VIDEO){
        $('#pictureCheck').val('true');
        //return;
    }  
       var _ajaxUrl=getContextPath('/ic03/ic0301/doGetTempFile');
       var _materialId=$("#materialIdTexbox").val();
       $("#ic0301materialId").val(_materialId)
       var param=$("#ic0301Form").serialize();

       jQuery.post(_ajaxUrl, param, function(data){
           if(CommonUtilJs.processAjaxValidateError(data)){
               if (data != null && data != '') {
                   if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_SOUND){
                       //素材为音乐的情况
                       $('#sounds').show();
                       var krpano = document.getElementById("ic0301KrpanoSWFObject");
                       //设置音乐播放按钮的click事件
                       if(_expositionVrFlag=="0"){
	                       krpano.set('hotspot[start].onclick','js(stopIc0104Sounds(););playsound(s1,'+data+'?temp='+Math.random()+', 0,0); ');
                       }else{
	                       krpano.set('hotspot[start].onclick','js(stopVr0104Sounds(););playsound(s1,'+data+'?temp='+Math.random()+', 0,0); ');
                       }
                       
                    }else if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_VIDEO){
                       //素材为视频的情况
                       $('#sounds').show();
                       var krpano = document.getElementById("ic0301KrpanoSWFObject");
                       var videoUrl = getContextPath("/"+data);
                       //设定播放器皮肤尺寸,播放视频
                       if(_expositionVrFlag=="0"){
	                       krpano.set('hotspot[start].onclick','js(stopIc0104Sounds(););suitableScreen();openvideo('+videoUrl+');');
                       }else{
                    	   krpano.set('hotspot[start].onclick','js(stopVr0104Sounds(););suitableScreen();openvideo('+videoUrl+');');
                       }
                        krpano.set('hotspot[stop].onclick','closevideo();');
                    }else{
                       $("#tagPicture").attr('src',data + '?temp=' + Math.random());
                       $("#tagPicture").css('display','block');
                       $('#pictureCheck').val('true'); 
                       $("#sounds").hide();
                  }
               }
            }
       });
       //只能上传一张图片或一个视频，上传成功后，隐藏上传文件的按钮
       $('.imui-fileupload-add ').hide();
       $('.imui-fileupload-cancel ').hide();
   
}

//图片删除成功后处理方法
function callbackIc0301Remove(e,data){
    //如果是音乐，点击删除后停止播放
    if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_SOUND){
        var ic0103krpano = document.getElementById("ic0301KrpanoSWFObject");
        ic0103krpano.call('stopallsounds();');
    }
    
    if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_VIDEO ){
        var ic0103krpano = document.getElementById("ic0301KrpanoSWFObject");
        ic0103krpano.call('closevideo();');
    }
    
    _selectedValue = $("input[name='materialTypeId']:checked").val();
    if(_selectedValue == CdicConstants.VAL_MATERIAL_TYPE_SOUND){
        $('#pictureCheck').val('');
    }
    $("#tagPicture").attr('src','');
    $("#tagPicture").css('display','none');
    var _ajaxUrl=getContextPath('/ic03/ic0301/doDeleteTempFile');
    var _materialId=$("#materialIdTexbox").val();
    $("#ic0301materialId").val(_materialId)
    var param = $("#ic0301Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxValidateError(data)){
            $('#pictureCheck').val('');
        }
    });
    
    $('.imui-fileupload-add ').show();
    $('.imui-fileupload-cancel ').show();
}

//返回方法
function ic0301DoBack(){
    $('#ic0301AddFinish').imuiDialog('close');
    //返回时关闭当前音乐和视频
    var ic0103krpano = document.getElementById("ic0301KrpanoSWFObject");
    if (ic0103krpano != null && undefined != ic0103krpano.get){
        ic0103krpano.call('stopallsounds();');
        ic0103krpano.call('closevideo();');
    }
    if($('#openFromIc0104').val() == 'yes'){
        $('#popupPage').imuiPageDialog('close');
    }else{
      $("#pageSubmitResult").val('success');
      $("#success-form").submit();
    }
}

//文件上传前验证处理
function callbackIc0301BeforeSend(e, data) {
    var _selectedType = $("input[name='materialTypeId']:checked").val();
    var jsArg_e= e;
    var jsArg_Data= data;
    if(_selectedType ==null&&_selectedType == '' ){
        imuiAlert('请选择上传文件类型');
        $(".imui-fileupload-cancel").click();
        //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
       return false;
     }else{ 
          var str=data.originalFiles[0].name
          var results= str.substring(str.lastIndexOf("."));
          var str1 = JSON.stringify(results)  
          var str2 = JSON.parse(str1)
          var result=str2.toUpperCase()
        if(_selectedType == CdicConstants.VAL_MATERIAL_TYPE_SOUND){
          if(result!=".MP3"){
             imuiAlert('音频格式不支持,上传格式只支持(.mp3)');
             $(".imui-fileupload-cancel").click();
             //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
             return false;   
            }
        }else if(_selectedType==CdicConstants.VAL_MATERIAL_TYPE_VIDEO){
            if(result!=".MP4"){
                imuiAlert('视频格式不支持,上传格式只支持(.mp4)');
                $(".imui-fileupload-cancel").click();
                //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
                return false;   
            }
        }else{
            if(result!=".JPG"&&result!=".PNG"&&result!=".SWF"&&result!=".GIF"){
                imuiAlert('图片格式不支持,上传格式只支持(.jpg .png .swf .gif)');
                $(".imui-fileupload-cancel").click();
                //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
                return false;  
               }
        }
    }
}

//判断展览是否为VR展
var _expositionVrFlag="";
function expositionVrFlag(){
	var _ajaxUrl = getContextPath('/ic01/ic0110/doVrFlag');
    var param = '';
    param = param + '&selectedExpositionId='+$('#expositionId').val();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxValidateError(data)){
        	_expositionVrFlag=data;
        }
        
    });
}
// 隐藏编辑区域
function hideEdit(){
    $('#material_file_upload_tr').hide(); // 文件上传
    $('#text_flow_info_tr').hide(); // 浮动信息(文字)内容
    $('#previewMateria').hide(); // 预览
    $('#previewText').hide(); //  预览文字
    $("#sounds").hide();  // 音频预览
    $("#text_info_tr").hide(); // 图文文字信息
}