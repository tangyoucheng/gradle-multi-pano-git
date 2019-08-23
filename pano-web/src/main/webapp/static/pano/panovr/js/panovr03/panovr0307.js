
//================================================
//全景一览时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){

	// 返回处理
	$("#button-vr0307Finish").click(function() {
	    vr0307DoBack();
	});

    // 确定处理
	$("#vr0307-confirm-button").click(function() {
	    if ($('#selectedPanoramaId').val().length == 0) {
	        imuiAlert('请选择全景图！');
	        return false;
	    } else {
            imuiConfirm('是否更新热点上场景信息?', '确认', function() {
            	$(".imui-validation-error").remove();
            	var _ajaxUrl = getMemberContextPath('panoVr0307/doEntry');
            	var param = $("#vr0307Form").serialize();
            	jQuery.post(_ajaxUrl, param, function(data){
            	    if(CommonUtilJs.processAjaxSuccessAfter(data)){
            	    	eval("$('#vr0307Finish').imuiDialog('open')");
            	    }
            	    
            	});
            });
	    }
	});
	
});


//返回处理方法
function vr0307DoBack(){
    $('#panoramaIdFormVr0307').val($('#selectedPanoramaId').val());
    $('#vr0105-refresh-from-vr0307').submit();
}
    
//Ajax分页处理
function doAjaxPage(){
    var _ajaxUrl = getMemberContextPath('panoVr0307/doPage');
    var param = $("#vr0307Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            createListDataTable(data);}
   });
}

// 刷新一览数据
function createListDataTable(data) {
    if(CommonUtilJs.processAjaxSuccessAfter(data)){
        var isjson = typeof(data) == "object" && Object.prototype.toString.call(data).toLowerCase() == "[object object]" && !data.length;
        if(data.indexOf("指定条件的数据不存在。请改变索条件后再检索。") != -1){
            imuiAlert('暂无数据。');
            $("#vr0307ListDataTable tbody").html('');
            $('#pageShowInfo').hide();
        	return false;
        }
        
        var vr0307JsForm = JSON.parse(data);
        //分页信息设定
        $('#vr0307Form_pageShowInfo').show();
        $('#vr0307Form_pageShowInfo').each(function(id, item) {
            $(this).prop('outerHTML', vr0307JsForm.pageShowInfos[0])
        
        });
        $('#vr0307Form_pageHiddenInfo').each(function(id, item) {
            $(this).prop('outerHTML', vr0307JsForm.pageShowInfos[1])
        
        });
        // 刷新一览数据
        var tbodyHtml = "";
        $(vr0307JsForm.panoramaInfo).each(function(index, item) {
            tbodyHtml += '<tr id="vr0307Tr_' +index +'" style="height:10px;cursor: pointer;" onclick="setSelectedTrColor(this,' +"'" +item.panoramaId +"'" +');">';
            tbodyHtml += '<td title=' +item.panoramaName +' class="align-L valign-M selected" style="width:20%;">' +item.panoramaName +'</td>';
            tbodyHtml += '<td title=' +item.notes +' class="align-L valign-M selected" style="width:20%;">' +item.notes +'</td>';
            tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('+"'" +item.panoramaId +"'" +')"/>' +'</td>';
            tbodyHtml += "</tr>";
        });
        $("#vr0307ListDataTable tbody").html(tbodyHtml);
    }
}


// 设定被选行的颜色
function setSelectedTrColor(_trInfo,_selectedPanoramaId) {
    //选中的场景信息设定
    $('#selectedPanoramaId').val(_selectedPanoramaId);
    $('#vr0307ListDataTable tbody td').each(function(){
        $(this).css("background-color","");
    })
    $("#" + _trInfo.id + ' td').css("background-color","#DACAA6");
}


//读取场景预览图
function doPreview(_panoramaId){
    
    var _ajaxUrl = getMemberContextPath('panoVr0307/doGetPreviewImage');
    $('#selectedPanoramaId').val(_panoramaId);
    var param = $("#vr0307Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
            if (data != null && data != '') {
                $("#previewPanoramaPath").val(data);
                if($('#previewPanoramaPath').val().length > 0){
                    var xmlPath = $('#previewPanoramaPath').val()
                    embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
                        , id:'vr0107KrpanoSWFObject'
                        , xml:xmlPath
                        , target:"vr0107Pano",wmode:"opaque"
                        , flash: "only" 
                        , passQueryParameters:true
                        , bgcolor:"#f5f5f5"});
                }
                $("#vr0107Pano").css('display','block');
                eval("$('#vr0307imagePreview').imuiDialog('open')");
            }
	    }
	});
}
 