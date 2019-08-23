
//================================================
//新建全景图首图处理
//================================================
$(document).ready(function(param) {
    // 返回按钮
    $("#vr0201-button-finish-confirm").click(function() {
        vr0201DoBack();
    });
    
    // 场景音乐勾选框
    $('#vr0201SelectMusicButton').hide();
    $('#vr0201PanoMusicSelect').click(function(){
        if($("#vr0201PanoMusicSelect").attr("checked")){
            $('#vr0201SelectMusicButton').show();
        }else{
            $('#vr0201SelectMusicButton').hide(); 
            $('#soundName').text('');
        }
    });
    
    // 隐藏删除按钮
    $('.imui-fileupload-delete ').hide();
    
    // 登录按钮
    $("#button-vr0201-confirm").click(function() {
        var vr0201panoramaName = $('#vr0201_panoramaName').val();
        if(vr0201panoramaName == null || vr0201panoramaName == '' ){
            imuiAlert('请输入场景名称！');
            return;
        }
        if($('#panoCheck').val()!='true'){
            imuiAlert('请上传左眼图片！');
            return;
        }
        if($("#vr0201PanoMusicSelect").attr("checked")){
            if($('#soundName').text() == '' || $('#soundName').text() == null){
                imuiAlert('请选择音乐文件！');
                return;
            }
        }
        
        imuiConfirm('是否登录场景信息?', '确认', function() {
        CommonUtilJs.loadMask();
        $(".imui-validation-error").remove();
        var _ajaxUrl = getMemberContextPath('panoVr0201/doEntry');
        var param = $("#vr0201Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data){
            CommonUtilJs.removeMask();
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                eval("$('#vr0201AddFinish').imuiDialog('open')");
                $('#panoCheck').val("");
            }
         });
        });
        
    });
});

//返回处理方法
function vr0201DoBack(){
    $('#panoramaId-from-vr0201').val($('#vr0201_panoramaId').val());
    $('#expositionId-from-vr0201').val($('#expositionId').val());
    $('#expositionName-from-vr0201').val($('#expositionName').val());
    $('#vr0104-refresh-from-vr0201').submit();
}

//文件上传前验证处理
function callbackVr0201BeforeSend(e, data) {
    //判断是否为6张图
    if (data.originalFiles.length != 6) {
        var jsArg_e= e
        var jsArg_Data= data
        var that = $(this).data('fileupload');
        $(".imui-fileupload-cancel").click();
        //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
        imuiAlert('请选择6张可用图片！');
        return false;
    }
    
    //判断6张图尾字母是否有重复
    for(var i=0;i<6;i++){
        for(var n = i+1;n<6;n++){
            var str1 = data.originalFiles[i].name;
            var str2 = data.originalFiles[n].name;
            if(str1.substring(str1.lastIndexOf(".")-1,str1.lastIndexOf(".")) 
                    == str2.substring(str2.lastIndexOf(".")-1,str2.lastIndexOf(".")) ){
                var jsArg_e= e
                var jsArg_Data= data
                var that = $(this).data('fileupload');
                $(".imui-fileupload-cancel").click();
                //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
                imuiAlert('只能同时上传文件名以半角[ -、_ ]，加半角英文字母<br>[r、d、f、l、b、u]结尾的6张图片，并且文件不能重复。<br>例：[pano-r.jpg、pano_r.jpg]',null,false);
                return false;
            }
        }
    }
    //6张图且尾字母不重复的情况下，判断尾字母是否符合标准
    var arr="rdflbu";
    for(var i=0;i<6;i++){
        var str = data.originalFiles[i].name;
        var result = str.substring(str.lastIndexOf(".")-1,str.lastIndexOf("."));
        if(arr.indexOf(result)==-1){
            var jsArg_e= e
            var jsArg_Data= data
            var that = $(this).data('fileupload');
            $(".imui-fileupload-cancel").click();
            //$(this).data('fileupload')._trigger('destroy', jsArg_e, jsArg_Data);
            imuiAlert('只能同时上传文件名以半角[ -、_ ]，加半角英文字母<br>[r、d、f、l、b、u]结尾的6张图片，并且文件不能重复。<br>例：[pano-r.jpg、pano_r.jpg]',null,false);
            return false;
        }
    }
    return true;
}

// 全景图上传计数
//var vr0201FileCount = 0;
var vr0201FileCount_l = 0;
var vr0201FileCount_r = 0;

//文件上传成功后处理方法
function callbackVr0201Success(e, data){
	if(data.paramName=="local_file"){
		vr0201FileCount_l = vr0201FileCount_l + 1;
	}
	if(data.paramName=="local_file2"){
		vr0201FileCount_r = vr0201FileCount_r + 1;
	}
	if(vr0201FileCount_l==6){
		$('#subFolderName').val("panos_l");
        var _ajaxUrl = getMemberContextPath('panoVr0201/doGetTempFile');
        var param = $('#vr0201Form').serialize();
        jQuery.post(_ajaxUrl, param, function(data){
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                if (data != null && data != '') {
                    var xmlPath = data;
                    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
                        , id:'vr0201KrpanoNewObject_L'
                        , xml:xmlPath
                        , target:"vr0201NewPano_L",wmode:"opaque"
                        , flash: "only" 
                        , passQueryParameters:true});
                    
                    $("#vr0201NewPano_L").css('display','block');
                    $("#panoCheck").val('true');
                }
            }
        });
        $('#vr0201FileUpload_L_td').find('.imui-fileupload-add').each(function(){
	        $(this).hide();
	    })
	    $('#vr0201FileUpload_L_td').find('.imui-fileupload-cancel').each(function(){
	        $(this).hide();
	    })
	    vr0201FileCount_l = 0;
	}
	if(vr0201FileCount_r==6){
		$('#subFolderName').val("panos_r");
	      var _ajaxUrl = getMemberContextPath('panoVr0201/doGetTempFile');
	      var param = $('#vr0201Form').serialize();
	      jQuery.post(_ajaxUrl, param, function(data){
	          if(CommonUtilJs.processAjaxSuccessAfter(data)){
	              if (data != null && data != '') {
	                  var xmlPath = data;
	                  embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
	                      , id:'vr0201KrpanoNewObject_R'
	                      , xml:xmlPath
	                      , target:"vr0201NewPano_R",wmode:"opaque"
	                      , flash: "only" 
	                      , passQueryParameters:true});
	                  
	                  $("#vr0201NewPano_R").css('display','block');
	//                      $("#panoCheck").val('true');
	              }
	          }
	      });
	      $('#vr0201FileUpload_R_td').find('.imui-fileupload-add').each(function(){
		        $(this).hide();
		  })
		  $('#vr0201FileUpload_R_td').find('.imui-fileupload-cancel').each(function(){
		        $(this).hide();
		  })
		  vr0201FileCount_r = 0;
	}

//    vr0201FileCount = vr0201FileCount + 1;
//    if(vr0201FileCount==6){
//    	if(data.paramName=="local_file"){
//	    	$('#subFolderName').val("panos_l");
//	        var _ajaxUrl = getMemberContextPath('panoVr0201/doGetTempFile');
//	        var param = $('#vr0201Form').serialize();
//	        jQuery.post(_ajaxUrl, param, function(data){
//	            if(CommonUtilJs.processAjaxSuccessAfter(data)){
//	                if (data != null && data != '') {
//	                    var xmlPath = data;
//	                    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
//	                        , id:'vr0201KrpanoNewObject_L'
//	                        , xml:xmlPath
//	                        , target:"vr0201NewPano_L",wmode:"opaque"
//	                        , flash: "only" 
//	                        , passQueryParameters:true});
//	                    
//	                    $("#vr0201NewPano_L").css('display','block');
//	                    $("#panoCheck").val('true');
//	                }
//	            }
//	        });
//	        $('#vr0201FileUpload_L_td').find('.imui-fileupload-add').each(function(){
//		        $(this).hide();
//		    })
//		    $('#vr0201FileUpload_L_td').find('.imui-fileupload-cancel').each(function(){
//		        $(this).hide();
//		    })
//        }
//        if(data.paramName=="local_file2"){
//        	  $('#subFolderName').val("panos_r");
//		      var _ajaxUrl = getMemberContextPath('panoVr0201/doGetTempFile');
//		      var param = $('#vr0201Form').serialize();
//		      jQuery.post(_ajaxUrl, param, function(data){
//		          if(CommonUtilJs.processAjaxSuccessAfter(data)){
//		              if (data != null && data != '') {
//		                  var xmlPath = data;
//		                  embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
//		                      , id:'vr0201KrpanoNewObject_R'
//		                      , xml:xmlPath
//		                      , target:"vr0201NewPano_R",wmode:"opaque"
//		                      , flash: "only" 
//		                      , passQueryParameters:true});
//		                  
//		                  $("#vr0201NewPano_R").css('display','block');
//		//                      $("#panoCheck").val('true');
//		              }
//		          }
//		      });
//		      $('#vr0201FileUpload_R_td').find('.imui-fileupload-add').each(function(){
//			        $(this).hide();
//			  })
//			  $('#vr0201FileUpload_R_td').find('.imui-fileupload-cancel').each(function(){
//			        $(this).hide();
//			  })
//        }
//        
//        vr0201FileCount = 0;
//    }
    
}

//文件删除成功后处理方法
function callbackVr0201Remove(e, data){
	
	if($(data.fileInput)[0].name=="local_file"){
	    $("#vr0201NewPano_L").css('display','none');
	    $('#vr0201FileUpload_L_td').find('.delete button').each(function(){
	        $(this).click();
	    })
	    $('#subFolderName').val("panos_l");
	    var _ajaxUrl = getMemberContextPath('panoVr0201/doDeleteTempFile');
	    var param = $('#vr0201Form').serialize();
	    jQuery.post(_ajaxUrl, param, function(data){
	        if(CommonUtilJs.processAjaxSuccessAfter(data)){
	            $("#panoCheck").val("");
	        }
	    });
	    $('#vr0201FileUpload_L_td').find('.imui-fileupload-add').each(function(){
	        $(this).show();
	    })
	    $('#vr0201FileUpload_L_td').find('.imui-fileupload-cancel').each(function(){
	        $(this).show();
	    })
    }
	
	if($(data.fileInput)[0].name=="local_file2"){
		$("#vr0201NewPano_R").css('display','none');
		$('#vr0201FileUpload_R_td').find('.delete button').each(function(){
		  $(this).click();
		})
		$('#subFolderName').val("panos_r");
		var _ajaxUrl = getMemberContextPath('panoVr0201/doDeleteTempFile');
		var param = $('#vr0201Form').serialize();
		jQuery.post(_ajaxUrl, param, function(data){
		  if(CommonUtilJs.processAjaxSuccessAfter(data)){
		//      $("#panoCheck").val("");
		  }
		});
		$('#vr0201FileUpload_R_td').find('.imui-fileupload-add').each(function(){
	        $(this).show();
	    })
	    $('#vr0201FileUpload_R_td').find('.imui-fileupload-cancel').each(function(){
	        $(this).show();
	    })
	}
    
}

//打开选取声音素材的画面
function doSetPanoBackGroundSound(){
  $("#vr0201SetSound").imuiPageDialog({
      url: getMemberContextPath('pano0208'),
      title: '设置音乐',
      modal: true,
      width: 1000,
      height: 700,
      parameter: {
          dataFromIc0201:"yes",
          expositionId: $('#expositionId').val(),
          materialTypeId: PanoConstants.VAL_MATERIAL_TYPE_SOUND
      }
  });
  return false;
}

//从设定声音素材的画面获取返回的素材ID
function ic0201GetDataFromIc0208(_materialId){
    //关闭0208试听中的音乐
    stopSound();
    $('#materialIdFromIc0208').val(_materialId)
    //取得最新声音名
    var _ajaxUrl = getMemberContextPath('panoVr0201/doRefreshSoundName');
    var param = $("#vr0201Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            if(data != ''&& data != null){
                $('#soundName').text(data);
            }
        }
     });
}
