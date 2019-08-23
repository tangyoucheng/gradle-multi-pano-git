//自定义的全局变量和计时器
var _videoPath;
var _newPath;
var _panoId;
var radioboxSelected_1;
var ic0109OldVideoFile = setInterval(function(){lookOldVideoFile(_videoPath);},600);
var ic0109NewVideoFile = setInterval(function(){lookNewVideoFile(_newPath);},500);
//自定义是否上传了文件的flag
var _hadUploadFile;

//================================================
//展览编辑
//================================================
$(document).ready(function(){
    $("#editButtonsOrFlowInfo").hide();
    
	//删除成功后的按钮
    $('#button-delete').click(function(){
    	$("#pageSubmitResult").val('deleteSuccess');
        $("#success-form").submit();
    });
    
    // 隐藏删除按钮
    $('.imui-fileupload-delete ').hide();

    //画面初期隐藏预加载文件预览和上传，音乐设定按钮栏
    $('#preloadFileUploadTr').hide();
    $('#picturesTr').hide();
    $('#setExpoSound').hide();

    //如果存在预加载文件,保持预加载文件勾选框为选中状态
     radioboxSelected_1 = $("input[name='preloadFileType']:checked").val();
     if(radioboxSelected_1 != '' && radioboxSelected_1 != null && radioboxSelected_1 != 'undefined' ){
         $("#editButtonsOrFlowInfo").height(525);
         $("#preloadFlag").attr("checked",true);
         $('#preloadFileUploadTr').show();
         $('#picturesTr').show();
         $("input[name='preloadFileType']").attr("disabled", false);
         _hadUploadFile = true;
     } else {
         $("#editButtonsOrFlowInfo").height(280);
     }
    //如果存在音乐，保持音乐勾选框为选中状态
     if($('#expositionSoundName').val() != '' && $('#expositionSoundName').val() != null){
         $("#expoSoundFlag").attr("checked",true);
         $('#setExpoSound').show();
     }
    //如果展览有设置展览类型，则显示展览类型栏
    var radioboxSelected_2 = $("input[name='expositionType']:checked").val();
    if(radioboxSelected_2 != '' && radioboxSelected_2 != null){
        $('#expoTypeTr').show();
    }else{
        $('#expoTypeTr').hide();
    }
    //预加载文件勾选框事件
    $("#preloadFlag").click(function() {
        $("input[name='preloadFileType']").attr("disabled", !($("#preloadFlag").attr("checked")));
        if(!$("#preloadFlag").attr("checked")){
            $("#editButtonsOrFlowInfo").height(280);
            $("input[name='preloadFileType']").attr("checked",false);
            $('#preloadFileUploadTr').hide();
            $('#picturesTr').hide();
            $('.imui-fileupload-delete').click();
        }
    });
    
   //展览音乐勾选框事件
   $("#expoSoundFlag").click(function() {
        if($("#expoSoundFlag").attr("checked")){
            $("#setExpoSound").show();
        }else{
            $('#setExpoSound').hide();
            $('#soundName').text('');
        }
    });

    //操作成功后的相关提示信息
    if ($('#hidPageSubmitResult').val() === "deleteSuccess") {
        imuiShowSuccessMessage('展览删除完成。');
    }
    if ($('#hidPageSubmitResult').val() === "updateSuccess") {
        imuiShowSuccessMessage('展览编辑完成。');
    }
    
    _videoPath= $("#oldmaterialPath").val();
    
    //载入画面时隐藏新视频和新图片栏内容
    $('#newPicture').hide();
    $('#newPano').hide();
    $('#newPicture1').hide();
    $('#newPano1').hide();
    
    //展览预加载文件radioBox的选择处理
    $("input[name='preloadFileType']").click(function(){
        radioboxClick();
    });

    // 返回处理
    $("#back-button").click(function() {
        $('#back-form').submit();
    });
    
    // 确定按钮
    $("#button-finished-confirm").click(function() {
    	confirmOnclick();
    });
    
    // 展览登录处理
    $("#confirm-button").click(function() {
        if($("#preloadFlag").attr("checked")){
            var radioboxSelected = $("input[name='preloadFileType']:checked").val();
            if(radioboxSelected == null || radioboxSelected == ''){
                imuiAlert("请选择预览文件种类");
                return false;
            }
            
            if(!_hadUploadFile){
                imuiAlert("请上传文件");
                return false;
            }
        }
        imuiConfirm('是否更新展览信息?', '确认', function() {
            CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('pano0109/doUpdate');
            var param = $("#ic0109Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
            	CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#ic0109Finish').imuiDialog('open')");
                }
                
            });
        });
    });
    $("#editButtonsOrFlowInfo").show();
});

    
//确定按钮方法    
function confirmOnclick(){
   $("#pageSubmitResult").val('updateSuccess');
   $("#success-form").submit();
}

//radiobox的选择事件
function radioboxClick(){
    $('#preloadFileUploadTr').show();
    $('#picturesTr').show();
    var radioboxSelected = $("input[name='preloadFileType']:checked").val();
    //如果选中了视频
    if(radioboxSelected == '1'){
        $("#editButtonsOrFlowInfo").height(525);
        //隐藏图片预览
        $('#newPicture').attr('src','');
        $('#newPicture').hide();
        $('#newPicture1').attr('src','');
        $('#newPicture1').hide();
    //若果选中了图片    
    }else{
        $("#editButtonsOrFlowInfo").height(525);
        //隐藏视频预览
        var krpano;
    	if(_videoPath != '' && _videoPath != null){
            krpano = document.getElementById("newPanoId");
    	}else{
    		krpano = document.getElementById("newPano1Id");
    	}
        if (krpano != null && undefined != krpano.get){
            krpano.call('videoplayer_close();');
            $('#newPano').hide();
            $('#newPano1').hide();
        }
    }
    callbackIc0109Remove(null,null);
    $('.imui-fileupload-delete').click();
    
    // 在已有预加载文件的情况下，若没有更换预加载文件类型
    if(radioboxSelected_1 == $("input[name='preloadFileType']:checked").val()){
        _hadUploadFile = true;
    }else{
        //更换了预加载类型
        _hadUploadFile = false;
    }
}

//新视频加载 
function lookNewVideoFile(path){
    var timeFlag;
    if(timeFlag){
        return false;
    }
	var krpano;
	if(_videoPath != '' && _videoPath != null){
        krpano = document.getElementById("newPanoId");
	}else{
		krpano = document.getElementById("newPano1Id");
	}
    if (krpano == null || undefined == krpano.get){
        return false;
    }
    timeFlag = true;
    krpano.call('videoplayer_open(../'+path+ '?temp=' + Math.random()+');');
    clearInterval(ic0109NewVideoFile);
    timeFlag = false;
}

 //旧视频加载 
function lookOldVideoFile(path){
	var timeFlag;
    if(timeFlag){
        return false;
    }
    var krpano = document.getElementById("oldPanoId");
    if (krpano == null || undefined == krpano.get){
        return false;
    }
    timeFlag = true;
    krpano.call('videoplayer_open(../'+path+');');
    clearInterval(ic0109OldVideoFile);
    timeFlag = false;
}

 
var  _selectedValue;
//文件上传成功后的处理
function callbackIc0109Success(e,data){
    _selectedValue = $("input[name='preloadFileType']:checked").val();
    if(_selectedValue == '1'){
        $('#pictureCheck').val('true');
    }
    var _ajaxUrl=getMemberContextPath('pano0109/doGetTempFile');
    var param=$("#ic0109Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            if (data != null && data != '') {
                //如果是视频文件
                if(_selectedValue == '1') {
                	//如果没有原视频
                    if( _videoPath==''||_videoPath==null){
                        var krpano = document.getElementById("newPano1Id");
                    	if (krpano == null || undefined == krpano.get){
                            embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
                                ,xml:"framework/panorama/template/video/videopreview.xml" 
                                ,id: "newPano1Id"
                                ,target:"newPano1",wmode:"opaque"
                                ,flash: "only"
                                ,passQueryParameters:true
                        	});
                    	}
                        _newPath=data;
                        lookNewVideoFile(_newPath);
                        $('#newPano1').show();
                    }else{
                    	var krpano = document.getElementById("newPanoId");
                     	if (krpano == null || undefined == krpano.get){
                    	    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
                                ,xml:"framework/panorama/template/video/videopreview.xml" 
                                ,id: "newPanoId"
                                ,target:"newPano",wmode:"opaque"
                                ,flash: "only"
                                ,passQueryParameters:true
                            });
                	    }
                        _newPath=data;
                        lookNewVideoFile(_newPath);
                        $('#newPano').show();
                    }
                }else{
                	//如果没有原图片
                	if( _videoPath==''||_videoPath==null){
                        $("#newPicture1").attr('src',data + '?temp=' + Math.random());
                        $('#newPicture1').show();
                	}else{
                        $("#newPicture").attr('src',data + '?temp=' + Math.random());
                        $('#pictureCheck').val('true');
                	}
                    $('#newPicture').show();
                }
            }
        }
    });
    $('#preloadFileUploadSuccess').val('success');
    //只能上传一张图片或一个视频，上传成功后，隐藏上传文件的按钮
    $('.imui-fileupload-add ').hide();
    // 上传成功后，隐藏取消按钮
    $('.imui-fileupload-cancel ').hide();
    _hadUploadFile = true;
}

//图片删除成功后处理方法
function callbackIc0109Remove(e,data){
    _selectedValue = $("input[name='preloadFileType']:checked").val();
   //如果删除的视频，则停止播放
    if(_selectedValue == '1'){
        var krpano;
        if( _videoPath==''||_videoPath==null){
            krpano = document.getElementById("newPano1Id");
	    }else{
            krpano = document.getElementById("newPanoId");
	    } 
        if (krpano != null && undefined != krpano.get){
            krpano.call('videoplayer_close();');
            $('#newPano').hide();  
            
            $('#newPano1').hide(); 
            $('#pictureCheck').val('true');
        }
    }else{
    	if( _videoPath==''||_videoPath==null){
            $("#newPicture1").attr('src','');
            $('#newPicture1').hide(); 
    	}else{
            $("#newPicture").attr('src','');
            $('#newPicture').hide(); 
    	}
    }
    //删除上传的临时文件
    var _ajaxUrl=getMemberContextPath('pano0109/doDeleteTempFile');
    var param = $("#ic0109Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            $('#pictureCheck').val('');
        }
    });
    
    $('#preloadFileUploadSuccess').val('');
    $('.imui-fileupload-add ').show();
    $('.imui-fileupload-cancel ').show();
    
    _hadUploadFile = false;
}

//文件上传前验证处理
function callbackIc0109BeforeSend(e, data) {
     var _selectedValue = $("input[name='preloadFileType']:checked").val(); 
     //上传前检查是否选择了文件类型
   var jsArg_e= e;
   var jsArg_Data= data;
     if(_selectedValue == null || _selectedValue == '' ){
         imuiAlert('请选择上传文件类型');
         $(".imui-fileupload-cancel").click();
         //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
        return false;
      }else{ 
           //检查上传文件是否符合格式
           var str=data.originalFiles[0].name;
           var result= str.substring(str.lastIndexOf("."));
           result=result.toLowerCase();
           if(_selectedValue=='1'){
               //视频
               if(result!=".mp4"&&result!=".mov"&&result!=".m4v"){
                      imuiAlert('视频格式不支持,上传格式只支持(.mp4, .m4v, .mov)');
                      $(".imui-fileupload-cancel").click();
                      //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
                      return false; 
               }
           }else{
               //图片
               if(result!=".jpg"&&result!=".png"&&result!=".swf"){
                     imuiAlert('图片格式不支持,上传格式只支持(.jpg .png .swf)');
                     $(".imui-fileupload-cancel").click();
                     //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
                     return false;  
               }
           }
     }
}

//打开选取声音素材的画面
function doSetPanoBackGroundSound(){
    $("#ic0109SetSound").imuiPageDialog({
        url: getMemberContextPath('pano0208'),
        title: '设置音乐',
        modal: true,
        width: 1000,
        height: 700,
        parameter: {
            expositionId: $('#expositionId').val(),
            dataFromIc0109: "yes",
            materialTypeId: PanoConstants.VAL_MATERIAL_TYPE_SOUND
        },
        close: function(event, ui) {
            //编辑画面关闭时，继续视频的播放
            lookOldVideoFile(_videoPath);
            lookNewVideoFile(_newPath);
        }
    });
    return false;
}

//从设定声音素材的画面获取返回的素材ID
function ic0109GetDataFromIc0208(_materialId){
    //关闭0208试听中的音乐
    stopSound();
    $('#materialIdFromIc0208').val(_materialId)
    //取得最新声音名
    var _ajaxUrl = getMemberContextPath('pano0109/doRefreshSoundName');
    var param = $("#ic0109Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            if(data != ''&& data != null){
                $('#soundName').text(data);
            }
        }
     });
}

 
  