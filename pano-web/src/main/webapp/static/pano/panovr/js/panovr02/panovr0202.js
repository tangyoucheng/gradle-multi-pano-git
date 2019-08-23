
//================================================
//场景编辑画面加载时的处理
//================================================
$(document).ready(function(){
    // 返回按钮
    $("#vr0202-button-finish-confirm").click(function() {
        vr0202DoBack();
    });
    
    // 隐藏删除按钮
    $('.imui-fileupload-delete ').hide();

    //如果已是首个场景，让勾选框选中
    if($('#isStartScene').val() == '1'){
        $("input[name='startSceneFlag']").attr("checked","true");
    }
    
    //如果该场景有音乐，让音乐勾选框选中
    if($('#soundName').text() != null && $('#soundName').text() != ''){
        $("input[id='vr0202PanoMusicSelect']").attr("checked","true");
        $('#edittMusicButton').show();
    }else{
        $('#edittMusicButton').hide(); 
    }
    
    //删除成功后的按钮
    $('#button-delete-goto-vr0104').click(function(){
        $("#vr0104Form").submit();
    });
    $('#button-delete-goto-vr0206').click(function(){
        $("#vr0206Form").submit();
    });

    // 场景音乐勾选框
    $('#vr0202PanoMusicSelect').click(function(){
        if($("#vr0202PanoMusicSelect").attr("checked")){
            $('#edittMusicButton').show();
        }else{
            $('#edittMusicButton').hide(); 
            $('#soundName').text('');
        }
    })

    // 登录按钮
    $("#button-vr0202-confirm").click(function() {
        var vr0202panoramaName = $('#vr0202panoramaName').val();
        if(vr0202panoramaName == null || vr0202panoramaName == '' ){
            imuiAlert('请输入场景名称！');
            return;
        }
        
        if($("#vr0202PanoMusicSelect").attr("checked")){
            if($('#soundName').text() == '' || $('#soundName').text() == null){
                imuiAlert('请选择音乐文件！');
                return;
            }
        }
        
        imuiConfirm('是否登录场景信息?', '确认', function() {
        	CommonUtilJs.loadMask();
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('panoVr0202/doUpdate');
            var param = $("#vr0202Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
            	CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#vr0202UpdFinish').imuiDialog('open')");
                }
             });
        });
        
    });
    
    
    // 删除当前可视化信息
    $('#button-delete-map').click(function() {
    
        if($('#flagFromParentPage').val() == 'vr0104'){
            imuiConfirm('是否删除当前可视化信息?', '确认', function() {
            	CommonUtilJs.loadMask();
                var panoramaIdForDelete = $('#panoramaIdForDelete').val();
                if(panoramaIdForDelete != '' && panoramaIdForDelete != null){
                    $('#deletePanorama').val(panoramaIdForDelete);
                }
                var _ajaxUrl = getMemberContextPath('panoVr0104/doDeleteMap');
                var param = $("#vr0104-delete-map").serialize();
                jQuery.post(_ajaxUrl, param, function(data){
                	CommonUtilJs.removeMask();
                    if(CommonUtilJs.processAjaxSuccessAfter(data)){
                        eval("$('#vr0201DeleteFinishGotoVr0104').imuiDialog('open')");
                        $('#vr0104DeleteFlgFromVr0202').val('yes');
                    }
                 });
            });
        }
        
        if($('#flagFromParentPage').val() == 'vr0206'){
            imuiConfirm('是否删除当前可视化信息?', '确认', function() {
            	CommonUtilJs.loadMask();
                var panoramaIdForDelete = $('#panoramaIdForDelete').val();
                if(panoramaIdForDelete != '' && panoramaIdForDelete != null){
                    $('#deletePanorama').val(panoramaIdForDelete);
                }
                var _ajaxUrl = getMemberContextPath('panoVr0206/doDeleteScene');
                var param = $("#vr0206-delete-map").serialize();
                jQuery.post(_ajaxUrl, param, function(data){
                	CommonUtilJs.removeMask();
                    if(CommonUtilJs.processAjaxSuccessAfter(data)){
                        eval("$('#vr0201DeleteFinishGotoVr0206').imuiDialog('open')");
                    }
                 });
            });
        }
    
    });
 
});

//返回处理方法
function vr0202DoBack(){
   if($('#flagFromParentPage').val() == 'vr0104'){
        $('#panoramaId-from-vr0202').val($('#vr0202panoramaId').val());
        $('#expositionId-from-vr0202').val($('#expositionId').val());
        $('#expositionName-from-vr0202').val($('#expositionName').val());
        $('#hotspotIdFromIc0102').val($('#lastHotspotIdFromVr0104').val());
        $('#currentHotspotIdFromIc0102').val($('#currentHotspotIdFromVr0104').val());
        $('#vr0104-refresh-from-vr0202').submit();
    }
    
    if($('#flagFromParentPage').val() == 'vr0206'){
        $('#expositionId-from-vr0202').val($('#expositionId').val());
        $('#expositionName-from-vr0202').val($('#expositionName').val());
        $('#last-hotspotId-from-0202').val($('#lastHotspotIdFromVr0104').val());
        $('#current-hotspotId-from-0202').val($('#currentHotspotIdFromVr0104').val());
        $('#vr0206-refresh-from-vr0202').submit();
    }

}

//打开选取声音素材的画面
function doSetPanoBackGroundSound(){
    $("#setSound").imuiPageDialog({
        url: getMemberContextPath('pano0208'),
        title: '设置音乐',
        modal: true,
        width: 1000,
        height: 700,
        parameter: {
            dataFromIc0202: "yes",
            expositionId: $('#vr0202expositionId').val(),
            panoramaId:$('#vr0202panoramaId').val(),
            materialTypeId: PanoConstants.VAL_MATERIAL_TYPE_SOUND
        }
    });
    return false;
}
//从设定声音素材的画面获取返回的素材ID
function ic0202GetDataFromIc0208(_materialId){
    //关闭0208试听中的音乐
    stopSound();
    $('#materialIdFromIc0208').val(_materialId)
    //取得最新声音名
    var _ajaxUrl = getMemberContextPath('panoVr0202/doRefreshSoundName');
    var param = $("#vr0202Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            if(data != ''&& data != null){
                $('#soundName').text(data);
            }
        }
     });
}

//文件上传前验证处理
function callbackVr0202BeforeSend(e, data) {
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
//var vr0202FileCount = 0;
var vr0202FileCount_l = 0;
var vr0202FileCount_r = 0;
//文件上传成功后处理方法
function callbackVr0202Success(e, data){
	if(data.paramName=="local_file"){
		vr0202FileCount_l = vr0202FileCount_l + 1;
	}
	if(data.paramName=="local_file2"){
		vr0202FileCount_r = vr0202FileCount_r + 1;
	}
	// 左眼图片
	if(vr0202FileCount_l == 6){
		$('#vr0202subFolderName').val("panos_l");
        var _ajaxUrl = getMemberContextPath('panoVr0202/doGetTempFile');
        var param = $('#vr0202Form').serialize();
        jQuery.post(_ajaxUrl, param, function(data){
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                if (data != null && data != '') {
                    $('#newPanoramaPath').val(data);
                    var xmlPath = $('#newPanoramaPath').val();
                    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
                        , id:'vr0202KrpanoNewObject_L'
                        , xml:xmlPath
                        , target:"vr0202NewPano_L",wmode:"opaque"
                        , flash: "only" 
                        , passQueryParameters:true});
                    
                    $("#vr0202NewPano_L").css('display','block');
                }
            }
        }); 
        //上传成功后隐藏上传按钮和取消按钮
        $('#vr0202FileUpload_L_td').find('.imui-fileupload-add').each(function(){
	        $(this).hide();
	    })
	    $('#vr0202FileUpload_L_td').find('.imui-fileupload-cancel').each(function(){
	        $(this).hide();
	    })
	    vr0202FileCount_l = 0;
	}
	// 右眼图片
	if (vr0202FileCount_r == 6){
		$('#vr0202subFolderName').val("panos_r");
		var _ajaxUrl = getMemberContextPath('panoVr0202/doGetTempFile');
        var param = $('#vr0202Form').serialize();
        jQuery.post(_ajaxUrl, param, function(data){
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                if (data != null && data != '') {
                    $('#newPanoramaPath').val(data);
                    var xmlPath = $('#newPanoramaPath').val();
                    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
                        , id:'vr0202KrpanoNewObject_R'
                        , xml:xmlPath
                        , target:"vr0202NewPano_R",wmode:"opaque"
                        , flash: "only" 
                        , passQueryParameters:true});
                    
                    $("#vr0202NewPano_R").css('display','block');
                }
            }
        }); 
        //上传成功后隐藏上传按钮和取消按钮
        $('#vr0202FileUpload_R_td').find('.imui-fileupload-add').each(function(){
	        $(this).hide();
	    })
	    $('#vr0202FileUpload_R_td').find('.imui-fileupload-cancel').each(function(){
	        $(this).hide();
	    })
	    vr0202FileCount_r = 0;
	}
	
//    vr0202FileCount = vr0202FileCount + 1;
//    if(vr0202FileCount == 6){
//    	if(data.paramName=="local_file"){
//    		$('#vr0202subFolderName').val("panos_l");
//	        var _ajaxUrl = getMemberContextPath('panoVr0202/doGetTempFile');
//	        var param = $('#vr0202Form').serialize();
//	        jQuery.post(_ajaxUrl, param, function(data){
//	            if(CommonUtilJs.processAjaxSuccessAfter(data)){
//	                if (data != null && data != '') {
//	                    $('#newPanoramaPath').val(data);
//	                    var xmlPath = $('#newPanoramaPath').val();
//	                    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
//	                        , id:'vr0202KrpanoNewObject_L'
//	                        , xml:xmlPath
//	                        , target:"vr0202NewPano_L",wmode:"opaque"
//	                        , flash: "only" 
//	                        , passQueryParameters:true});
//	                    
//	                    $("#vr0202NewPano_L").css('display','block');
//	                }
//	            }
//	        }); 
//	        //上传成功后隐藏上传按钮和取消按钮
//	        $('#vr0202FileUpload_L_td').find('.imui-fileupload-add').each(function(){
//		        $(this).hide();
//		    })
//		    $('#vr0202FileUpload_L_td').find('.imui-fileupload-cancel').each(function(){
//		        $(this).hide();
//		    })
//        }
//    	if(data.paramName=="local_file2"){
//    		$('#vr0202subFolderName').val("panos_r");
//    		var _ajaxUrl = getMemberContextPath('panoVr0202/doGetTempFile');
//            var param = $('#vr0202Form').serialize();
//            jQuery.post(_ajaxUrl, param, function(data){
//                if(CommonUtilJs.processAjaxSuccessAfter(data)){
//                    if (data != null && data != '') {
//                        $('#newPanoramaPath').val(data);
//                        var xmlPath = $('#newPanoramaPath').val();
//                        embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
//                            , id:'vr0202KrpanoNewObject_R'
//                            , xml:xmlPath
//                            , target:"vr0202NewPano_R",wmode:"opaque"
//                            , flash: "only" 
//                            , passQueryParameters:true});
//                        
//                        $("#vr0202NewPano_R").css('display','block');
//                    }
//                }
//            }); 
//            //上传成功后隐藏上传按钮和取消按钮
//            $('#vr0202FileUpload_R_td').find('.imui-fileupload-add').each(function(){
//		        $(this).hide();
//		    })
//		    $('#vr0202FileUpload_R_td').find('.imui-fileupload-cancel').each(function(){
//		        $(this).hide();
//		    })
//    	}
//    	
//        vr0202FileCount = 0;
//    }
}

//文件删除成功后处理方法
function callbackVr0202Remove(e, data){
	if($(data.fileInput)[0].name=="local_file"){
	    $("#vr0202NewPano_L").css('display','none');
	    $("#vr0202FileUpload_L_td").find('.delete button').each(function(){
	        $(this).click();
	    })
	    var _ajaxUrl = getMemberContextPath('panoVr0202/doDeleteTempFile');
	    var param = 'panoramaId='+$('#vr0202panoramaId').val();
	    jQuery.post(_ajaxUrl, param, function(data){
	        if(CommonUtilJs.processAjaxSuccessAfter(data)){
	        }
	    });
	    $('#vr0202FileUpload_L_td').find('.imui-fileupload-add').each(function(){
	        $(this).show();
	    })
	    $('#vr0202FileUpload_L_td').find('.imui-fileupload-cancel').each(function(){
	        $(this).show();
	    })
    }
	
	if($(data.fileInput)[0].name=="local_file2"){
		$("#vr0202NewPano_R").css('display','none');
	      $("#vr0202FileUpload_R_td").find('.delete button').each(function(){
	          $(this).click();
	      })
	      var _ajaxUrl = getMemberContextPath('panoVr0202/doDeleteTempFile');
	      var param = 'panoramaId='+$('#vr0202panoramaId').val();
	      jQuery.post(_ajaxUrl, param, function(data){
	          if(CommonUtilJs.processAjaxSuccessAfter(data)){
	          }
	      });
	      $('#vr0202FileUpload_R_td').find('.imui-fileupload-add').each(function(){
		        $(this).show();
		  })
		  $('#vr0202FileUpload_R_td').find('.imui-fileupload-cancel').each(function(){
		        $(this).show();
		  })
	}
	
}
