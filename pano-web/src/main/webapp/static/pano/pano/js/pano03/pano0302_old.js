//================================================
//为热点添加素材图片加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){
    $('#move-material-button').hide();
    $('#ic0302Div li').hide();

   var _materialTypeId=$("#materialTypeId").val();
    if(_materialTypeId!=PanoConstants.VAL_MATERIAL_TYPE_SOUND){
        $("sounds").hide();
    }else{
        $("notSounds").hide();
    }
    // 检索处理
    $("#search-button").click(function() {
          $('#move-material-button').hide();
          var _ajaxUrl = getMemberContextPath('pano0302/doSearch');
          var param = $("#ic0302Form").serialize();
          jQuery.post(_ajaxUrl, param,function(data){
              if(CommonUtilJs.processAjaxSuccessAfter(data)){
                  createListDataTable(data);              
               }          
          });
    });
 
    
    // 确定处理
    $("#ic0302-confirm-button").click(function() { 
      $("#editMaterial").imuiPageDialog('close');
    });
    
    //返回处理
    $('#ic0302-back-button').click(function(){
        if($('#openFromIc0104').val() == 'yes'){
           
           var _ajaxUrl = getMemberContextPath('pano0110/doVrFlag');
		   var param = '';
		   param = param + '&selectedExpositionId='+$('#expositionId').val();
		   jQuery.post(_ajaxUrl, param, function(data){
		       if(CommonUtilJs.processAjaxSuccessAfter(data)){
		           if(data=="0"){
			           $('#backToIc0104PanoramaId').val($('#ic0302PanoramaId').val());
		        	   $("#back-to-ic0104").submit();
		           }else{
			           $('#backToVr0104PanoramaId').val($('#ic0302PanoramaId').val());
		               $("#back-to-vr0104").submit();
		           }
		       }
		   });
           
        }else{
           $("#back-form").submit();
        }
    });
    
    //素材转移操作
    $('#move-material-button').click(function(){
        moveMaterials();
    });
    
    //素材转移成功
    $('#button-move-finished-confirm').click(function(){
        doAjaxPage();
        eval("$('#ic0302MoveMaterialFinish').imuiDialog('close')");
    });
    
});

// 全选，全解除操作
function selectAllChkbox(){
    $('#ic0302ListDataTable').find('input[type="checkbox"]').each(function(){
        $(this)[0].checked=true;
        $('#'+$(this)[0].id).val("true");
    });
    $('#move-material-button').show();
}

function cancelAllChkbox(){
    $('#ic0302ListDataTable').find('input[type="checkbox"]').each(function(){
        $(this)[0].checked=false;
        $('#'+$(this)[0].id).val("false");
    });
    $('#move-material-button').hide();
}

//素材栏勾选框选中事件
function doSelect(_chkboxId){
    $('#move-material-button').hide();
    if($('#'+_chkboxId).attr("checked")){
        $('#'+_chkboxId).val("true");
    }else{
        $('#'+_chkboxId).val("false");
    }
    $('#ic0302ListDataTable').find('input[type="checkbox"]').each(function(){
        if($(this)[0].checked){
            $('#move-material-button').show();
            return;
        }
    });
}

//素材转移操作
function moveMaterials(){
    //定义转移素材时的提示信息
    var confirmMsg;
    if($("input[name='materialBelongType']:checked").val() == PanoConstants.VAL_MATERIAL_BELONGTYPE_EXPOSITION){
        confirmMsg = "是否将选中素材转移为公共素材?";
    }else{
        confirmMsg = "是否将选中素材转移为所在展览素材?";
    }
    imuiConfirm(confirmMsg, '确认', function() {
        $(".imui-validation-error").remove();
        $('#move-material-button').hide();
        var _ajaxUrl = getMemberContextPath('pano0302/doMoveMaterials');
        var param = $("#ic0302Form").serialize();
        jQuery.post(_ajaxUrl, param, function(data){     
            if(CommonUtilJs.processAjaxSuccessAfter(data)){
                eval("$('#ic0302MoveMaterialFinish').imuiDialog('open')");
            }
        });
    });
}


// 素材编辑按钮按下时的处理
function editMaterial(_materialId,_materialName) {
    var _materiaIdForSearch = $("#materialIdtextbox").val();
    var _materialNameForSearch = $("#materialNametextbox").val();
    var _materialNameTypeForSearch = $("#materialTypeId").val();
    
    $("#editMaterials").imuiPageDialog({
        url: getMemberContextPath('pano0303'),
        title: '编辑素材',
        modal: true,
        width: 880,
        height: 850,
        stack:false,
        parameter: {
            hiddenExpositionName:$('#expositionName').val(),
            hiddenExpositionId:$('#expositionId').val(),
            hiddenPanoramaId:$('#ic0302PanoramaId').val(),
            materiaIdForSearch: _materiaIdForSearch,
            materialNameForSearch: _materialNameForSearch,  
            materialNameTypeForSearch: _materialNameTypeForSearch,
            expositionId: $("#expositionId").val(),
            expositionName: $("#expositionName").val(),
            materialId: _materialId,
            materialName:_materialName
        },
            close: function(event, ui) {
            //关闭画面时，停止0303音乐和视频
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
    }); 
    return false;
}


//通过素材Id检索预览图
function doPreview(_materialPath){
 
if (_materialPath != null && _materialPath != '') {
         $("#imagePreview").attr('src',_materialPath + '?temp=' + Math.random());
         $("#imagePreview").css('display','block');
         eval("$('#ic0302imagePreview').imuiDialog('open')");
     }
}


//Ajax分页处理
function doAjaxPage(){
    var _ajaxUrl = getMemberContextPath('pano0302/doPage');
    var param = $("#ic0302Form").serialize();
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
            imuiAlert('指定条件的数据不存在。请改变索条件后再检索。');
            $("#ic0302ListDataTable tbody").html('');
            $('#pageShowInfo').hide();
            $('#ic0302pageDiv').hide();
            $('#ic0302Div li').hide();
            return false;
        }
        
        var ic0302JsForm = JSON.parse(data);
        //分页信息设定
        $('#ic0302Form_pageShowInfo').show();
        $('#ic0302Form_pageShowInfo').each(function(id, item) {
            $(this).prop('outerHTML', ic0302JsForm.pageShowInfos[0])
        });
        $('#ic0302Form_pageHiddenInfo').each(function(id, item) {
            $(this).prop('outerHTML', ic0302JsForm.pageShowInfos[1])
        
        });
     
        // 刷新一览数据
        var tbodyHtml = "";
        $(ic0302JsForm.materialInfo).each(function(index, item) {
            tbodyHtml += '<tr id="ic0302Tr_' +index +'" style="height:10px;cursor: pointer;" >';   
            tbodyHtml += '<td class="align-C valign-M" style="width:40px; cursor: pointer;" onclick="editMaterial('+"'" +item.materialId +"','" +item.materialName +"'" +')"><span class="im-ui-icon-common-16-settings"/></td>';
            tbodyHtml += '<td title=' +item.materialId +' class="align-L valign-M selected" style="width:20%;">' +item.materialId +'</td>';
            tbodyHtml += '<td title=' +item.materialName +' class="align-L valign-M selected" style="width:20%;">' +item.materialName +'</td>';
            tbodyHtml += '<td title=' +item.materialTypeId +' class="align-L valign-M selected" style="width:20%;">' +item.materialTypeId+'</td>';
            tbodyHtml += '<td title=' +item.notes +' class="align-L valign-M selected" style="width:20%;">' +item.notes +'</td>';  
            if($('#openFromIc0104').val() != null && $('#openFromIc0104').val() != ''){
                tbodyHtml += '<td class="align-C valign-M">';
                tbodyHtml += '<input id="'+item.materialId+'_chkbox" type="checkbox" name="materialInfo['+index+'].isSelected" value="' +item.isSelected +'" onclick="doSelect('+"'"+item.materialId+"_chkbox'"+');">';
                tbodyHtml += '<input type="hidden" name="materialInfo['+index+'].materialId" value="'+item.materialId +'">';
                tbodyHtml += '<input type="hidden" name="materialInfo['+index+'].materialTypeId" value="'+item.materialTypeId +'">';
                tbodyHtml += '</td>';
            }
            tbodyHtml += "</tr>";
        });
        $("#ic0302ListDataTable tbody").html(tbodyHtml);
        $('#ic0302pageDiv').show();
    }
}


