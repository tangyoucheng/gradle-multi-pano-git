var i = 0;
//================================================
//展览一览画面检索
//================================================
$(document).ready(function(){
    
    // 返回处理
    $("#back-button").click(function() {
        $("#back-form").submit();
    });
    
    // 复制url
    $('#button-copy-confirm').click(function(){
        var url=$('#ic0110SelectedShortCutUrl').text(); 
        window.clipboardData.setData('text', url); 
        if(window.clipboardData.getData('text')==''){ 
            imuiAlert("复制失败，请刷新页面后重新复制！"); 
        }else{
            $("<div>复制成功！</div>").imuiMessageDialog({
                iconType: 'im-ui-icon-common-24-confirmation',
                verticalAlign: 'middle'
            });
        }
    });
    
    // 检索处理
    $("#search-button").click(function() {
        $("#search-form").attr("action",  'pano0110/doSearch');
        $("#search-form").submit();
    });
    
    // 发布成功后确定处理
    $('#button-releaseFinish-copy-confirm').click(function() {
        var url=$('#selectedShortCutUrl').text(); 
        window.clipboardData.setData('text', url); 
        if(window.clipboardData.getData('text')==''){
            imuiAlert("复制失败，请刷新页面后重新复制！"); 
        }else{
            $("<div>复制成功！</div>").imuiMessageDialog({
                iconType: 'im-ui-icon-common-24-confirmation',
                verticalAlign: 'middle'
            });
        }
    });
    
});

// 关闭提示框操作
function closeDialogAndRefresh(){
    $("#search-form").attr("action",  'pano0110/doRefreshByself');
    $("#search-form").submit();
}

//下载展览
function doDownloadExposition(_expositionId){
    $("#ic0101SelectedExpId").val(_expositionId);
    $("#downloadExpositionForm").submit();
}

//vr下载展览
function doDownloadExposition_vr(_expositionId){
    $("#ic0101SelectedExpId_vr").val(_expositionId);
    $("#downloadVrExpositionForm").submit();
}

// 全景图编辑按钮
function editPanorama(_expositionId){
	$("#thisFlagIsFromIc0110").val('yes');
    $("#hidpExpositionId").val(_expositionId);
    $("#goto-ic0104-form").submit();
}

// 展览发布
function doRealeaseExpro(_tenantId,_expositionId){
    imuiConfirm('是否发布该展览?', '确认', function() {
        $(".imui-validation-error").remove();
        //打开loading图
        eval("$('#ic0110Releasing').imuiDialog('open')");
        var _ajaxUrl = getMemberContextPath('pano0110/doRelease');
        var param = '';
        param = param + '&selectedExpositionId='+_expositionId;
        jQuery.post(_ajaxUrl, param, function(data){
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                var _link = window.location.protocol;
                _link =_link + '//' + window.location.host;
                _link =_link + getMemberContextPath("statictour/"+_expositionId+"/index.html");
                $("#selectedShortCutUrl").text(_link);
                eval("$('#ic0110Releasing').imuiDialog('close')");
                eval("$('#ic0110ReleaseFinish').imuiDialog('open')");
            }
            
        });
    });
}

// vr展览发布
function doRealeaseExpro_vr(_tenantId,_expositionId){
    imuiConfirm('是否发布该vr展览?', '确认', function() {
        $(".imui-validation-error").remove();
        //打开loading图
        eval("$('#ic0110Releasing').imuiDialog('open')");
        var _ajaxUrl = getMemberContextPath('pano0110/doRelease_vr');
        var param = '';
        param = param + '&selectedExpositionId='+_expositionId;
        jQuery.post(_ajaxUrl, param, function(data){
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                var _link = window.location.protocol;
                _link =_link + '//' + window.location.host;
                _link =_link + getMemberContextPath("statictour/"+_expositionId+"_vr/index.html");
                $("#selectedShortCutUrl").text(_link);
                eval("$('#ic0110Releasing').imuiDialog('close')");
                eval("$('#ic0110ReleaseFinish').imuiDialog('open')");
            }
            
        });
    });
}

//全景图查看按钮
function doCreateShortCutUrl(_expositionId){
    var _link = window.location.protocol;
    _link =_link + '//' + window.location.host;
    _link =_link +  getMemberContextPath('statictour/' + _expositionId + '/index.html');
    $("#ic0110SelectedShortCutUrl").text(_link);
    eval("$('#ic0110ShowShortCutUrl').imuiDialog('open')");
}

//全景图查看按钮
function doCreateShortCutUrl_vr(_expositionId){
    var _link = window.location.protocol;
    _link =_link + '//' + window.location.host;
    _link =_link +  getMemberContextPath('statictour/' + _expositionId + '_vr/index.html');
    $("#ic0110SelectedShortCutUrl").text(_link);
    eval("$('#ic0110ShowShortCutUrl').imuiDialog('open')");
}
