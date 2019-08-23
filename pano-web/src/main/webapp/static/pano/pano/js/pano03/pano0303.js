//如果是音频素材或视频素材，载入计时器
if($("input[name='materialTypeId']:checked").val() == PanoConstants.VAL_MATERIAL_TYPE_SOUND || $("input[name='materialTypeId']:checked").val() == PanoConstants.VAL_MATERIAL_TYPE_VIDEO){
    var ic0303Interval_1 = setInterval(function(){showOldSoundsFile()},500);
}
var ic0303Interval_2;
//================================================
//HTML加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){
    $("#oldtextDiv").text($('#textflowInfoTexbox').val());
    //页面初期化不显示新素材和新音乐预览
    $("#newSoundTr").hide();
    $("#newImageTr").hide();
    $("#newTextTr").hide();
    // 返回按钮
    $("#ic0303-button-finish-confirm").click(function() {
        stopSoundOnClosePage();
        if($('#openFromIc0104').val() == 'yes'){
            $('#ic0104FlgFromIc0302').val('yes');
        }
        eval("$('#ic0303AddFinish').imuiDialog('close')");
        eval("$('#editMaterials').imuiPageDialog('close')");
        doAjaxPage();
    });

    // 隐藏删除按钮
    $('.imui-fileupload-delete ').hide();
    
    //修改操作
    $("#confirm-button").click(function() {  
        var _materialName = $('#materialNameTexbox').val();
        if(_materialName == '' || _materialName == null){
            imuiAlert("请输入素材名称");
            return false;
        }
        if($('#ic0303materialTypeId').val() == PanoConstants.VAL_MATERIAL_TYPE_FLOW_INFO_TEXT){
        	if($('#textflowInfoTexbox').val() == '' || $('#textflowInfoTexbox').val() == null){
                imuiAlert("请输文字信息");
                return false;
        	}
        }
        
        imuiConfirm('是否保存修改的素材信息?', '确认', function() {
            CommonUtilJs.loadMask();
            stopSoundOnClosePage();
            $(".imui-validation-error").remove();        
            var _ajaxUrl = getMemberContextPath('pano0303/doUpdate');
            
            var param = $("#ic0303Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                 CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#ic0303AddFinish').imuiDialog('open')");
                }    
            });
        });
    });
    
    //删除操作
  $("#delete-button").click(function() { 
      imuiConfirm('是否删除素材信息?', '确认', function() {
            CommonUtilJs.loadMask();
            stopSoundOnClosePage();
            $(".imui-validation-error").remove();        
            var _ajaxUrl = getMemberContextPath('pano0303/doDelete');
            var param = $("#ic0303Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#ic0303AddFinish').imuiDialog('open')");
                }    
            });
        });
   });
});

//新文字浮动信息预览操作
function doPreviewTextinfo(){
	  $("#newtextDiv").text('');
  	  $("#newtextDiv").text($('#textflowInfoTexbox').val());
      $('#newTextTr').show();
}


//图片上传成功后的处理
function callbackIc0303Success(e,data){
   var _ajaxUrl=getMemberContextPath('pano0303/doGetTempFile');
   var param=$("#ic0303Form").serialize();
   var selectedValue = $("input[name='materialTypeId']:checked").val();
   jQuery.post(_ajaxUrl, param, function(data){
       if(CommonUtilJs.processAjaxSuccessAfter(data)){
       if (data != null && data != '') {
           if(selectedValue == PanoConstants.VAL_MATERIAL_TYPE_SOUND){
                   //上传新的音乐文件后，先停止正在试听的旧音乐
                   var krpano = document.getElementById("oldSoundPanorama");
                   krpano.call('stopallsounds();');
                   $('#newSoundTr').show();
                   
                   //设置新音乐的播放事件
                   ic0303Interval_2 = setInterval(function(){showNewSoundFile(data)},500);
               
              }else if(selectedValue == PanoConstants.VAL_MATERIAL_TYPE_VIDEO){
                    $('#newSoundTr').show();
                    
                    ic0303Interval_2 = setInterval(function(){showNewVideoFile(data)},500);
              }else{
                   $("#newImage").attr('src',data + '?temp=' + Math.random());
                   $("#newImage").css('display','block');
                   $("#newImageTr").show();
              }
           }
        }
   });
   
    //只能上传一张图片或一个视频，上传成功后，隐藏上传文件的按钮
    $('.imui-fileupload-add ').hide();
    //隐藏取消按钮
    $('.imui-fileupload-cancel ').hide();
}


//文件上传前验证处理
function callbackIc0303BeforeSend(e, data) {
    var _selectedType = $("input[name='materialTypeId']:checked").val();
    if(_selectedType ==null&&_selectedType == '' ){
        imuiAlert('请选择上传文件类型');
       return false;
     }else{ 
          var str=data.originalFiles[0].name
          var results= str.substring(str.lastIndexOf("."))
          var str1 = JSON.stringify(results)  
          var str2 = JSON.parse(str1)
          var result=str2.toUpperCase()
          var jsArg_e= e;
          var jsArg_Data= data;
        if(_selectedType==PanoConstants.VAL_MATERIAL_TYPE_SOUND){
          if(result!=".MP3"){
             imuiAlert('音频格式不支持,上传格式只支持(.mp3)');
             $(".imui-fileupload-cancel").click();
             //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
             return false;   
            }
        }else if(_selectedType==PanoConstants.VAL_MATERIAL_TYPE_VIDEO){
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


//文件删除成功后处理方法
function callbackIc0303Remove(e,data){
    //如果是音频素材
    if($("input[name='materialTypeId']:checked").val() == PanoConstants.VAL_MATERIAL_TYPE_SOUND){
        //关闭声音
        var krpano = document.getElementById("newSoundPanorama");
        krpano.call('stopallsounds();');
        $('#newSoundTr').hide();
    }else if($("input[name='materialTypeId']:checked").val() == PanoConstants.VAL_MATERIAL_TYPE_VIDEO){
        //视频素材
        var krpano = document.getElementById("newSoundPanorama");
        krpano.call('closevideo();');
        $('#newSoundTr').hide();
    }else{
        //如果是其他素材    
        $("#newImageTr").hide();
        $("#newImage").attr('src','');
        $("#newImage").css('display','none');
    }
    var _ajaxUrl=getMemberContextPath('pano0303/doDeleteTempFile');
    var param = $("#ic0303Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            
        }
    });
    
    $('.imui-fileupload-add ').show();
    $('.imui-fileupload-cancel ').show();
}

//关闭所有音乐和视频
function stopSoundOnClosePage(){
    var ic0303krpano_1 = document.getElementById("oldSoundPanorama");
    if (ic0303krpano_1 != null && undefined != ic0303krpano_1.get){
        ic0303krpano_1.call('stopallsounds();');
        ic0303krpano_1.call('closevideo();');
    }
    var ic0303krpano_2 = document.getElementById("newSoundPanorama");
    if (ic0303krpano_2 != null && undefined != ic0303krpano_2.get){
        ic0303krpano_2.call('stopallsounds();');
        ic0303krpano_2.call('closevideo();');
    }
}


//试听已有的声音或视频文件
function showOldSoundsFile(){
    var krpano = document.getElementById("oldSoundPanorama");
    if (krpano == null || undefined == krpano.get){
        return false;
    }
     clearInterval(ic0303Interval_1);
    
    // 声音
    if($("input[name='materialTypeId']:checked").val() == PanoConstants.VAL_MATERIAL_TYPE_SOUND){
        krpano.set('hotspot[start].onclick','playsound(s1,'+$("#oldmaterialPath").val()+', 0,0); ');
    }
    
    // 视频
    if($("input[name='materialTypeId']:checked").val() == PanoConstants.VAL_MATERIAL_TYPE_VIDEO){
        
        krpano.set('hotspot[start].onclick','suitableScreen();openvideo(../'+$("#oldmaterialPath").val()+');');
        krpano.set('hotspot[stop].onclick','closevideo();');
    }
}

// 试听新上传的音乐文件
function showNewSoundFile(data){
    var krpano = document.getElementById("newSoundPanorama");
    if (krpano == null || undefined == krpano.get){
        return false;
    }  
    krpano.set('hotspot[start].onclick','playsound(s1,'+data+'?temp='+Math.random()+', 0,0); ');
    clearInterval(ic0303Interval_2);
}

// 播放新上传的视频文件
function showNewVideoFile(data){
    var krpano = document.getElementById("newSoundPanorama");
    if (krpano == null || undefined == krpano.get){
        return false;
    }  
    clearInterval(ic0303Interval_2);
    
    krpano.set('hotspot[start].onclick','suitableScreen();openvideo(../'+data+');');
    krpano.set('hotspot[stop].onclick','closevideo();');
}

//返回方法
function ic0303DoBack(){
    $('#ic0303AddFinish').imuiDialog('close');
    $("#editMaterials").imuiPageDialog('close');
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