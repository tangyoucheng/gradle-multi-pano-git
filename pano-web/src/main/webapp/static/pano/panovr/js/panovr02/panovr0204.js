
//================================================
//为热点添加素材图片加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================

//返回处理
$(document).ready(function(){
  $("#button-vr0204Finish").click(function(){
      vr0204DoBack();
  });  
  
//确定处理
 $("#vr0204-confirm-button").click(function(){
     
 if (selectedPanoramaId == '' || selectedPanoramaId == null) {
        imuiAlert('请选择场景！');
        return false;
    }else{
        imuiConfirm('是否更新场景信息?', '确认', function() {
            $(".imui-validation-error").remove();
            $('#selectedPanoramaId').val(selectedPanoramaId);
            var _ajaxUrl = getMemberContextPath('panoVr0204/doEntry');
            var param = $("#vr0204Form").serialize();
            jQuery.post(_ajaxUrl,param,function(data){
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#vr0204Finish').imuiDialog('open')");
                } 
            });
        });
     }
   });
});

//选中设备信息
var selectedPanoramaId;

//返回处理
function  vr0204DoBack(){
    $('#vr0104-refresh-from-vr0204').submit();
}


  //Ajax分页处理
  function doAjaxPage(){
      var _ajaxUrl = getMemberContextPath('panoVr0204/doPage');
      var param = $("#vr0204Form").serialize();
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
              $("#vr0204ListDataTable tbody").html('');
              $('#pageShowInfo').hide();
              return false;
          }
          
          var vr0204JsForm = JSON.parse(data);
          //分页信息设定
          $('#vr0204Form_pageShowInfo').show();
          $('#vr0204Form_pageShowInfo').each(function(id, item) {
              $(this).prop('outerHTML', vr0204JsForm.pageShowInfos[0])
          
          });
          $('#vr0204Form_pageHiddenInfo').each(function(id, item) {
              $(this).prop('outerHTML', vr0204JsForm.pageShowInfos[1])
          
          });
          // 刷新一览数据
          var tbodyHtml = "";
          $(vr0204JsForm.panoramaList).each(function(index, item) {
              tbodyHtml += '<tr id="vr0204Tr_' +index +'" style="height:10px;cursor: pointer;" onclick="setSelectedTrColor(this,' +"'" +item.panoramaId +"','" +item.panoramaName +"'" +');">';
              tbodyHtml += '<td title=' +item.panoramaId +' class="align-L valign-M selected" style="width:20%;">' +item.panoramaId +'</td>'
              tbodyHtml += '<td title=' +item.panoramaName +' class="align-L valign-M selected" style="width:20%;">' +item.panoramaName +'</td>';
              tbodyHtml += '<td title=' +item.notes +' class="align-L valign-M selected" style="width:20%;">' +item.notes +'</td>';
              tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('+"'" +item.panoramaPath +"'" +')"/>' +'</td>';
              tbodyHtml += "</tr>";
          });
          $("#vr0204ListDataTable tbody").html(tbodyHtml);
      }
  }

    // 设定被选行的颜色
    function setSelectedTrColor(_trInfo,_selectedPanoramaId,_selectedPanoramaName) {
        //选中的设备信息设定
        selectedPanoramaId = _selectedPanoramaId;
        $('#vr0204ListDataTable tbody td').each(function(){
            $(this).css("background-color","");
        })
        $("#" + _trInfo.id + ' td').css("background-color","#DACAA6");
        if($('#vr0204HotspotTooltip').val() == '' || $('#vr0204HotspotTooltip').val() == null){
            $('#vr0204HotspotTooltip').val(_selectedPanoramaName);
        }
    }


  //读取场景预览图
function doPreview(_panoramaPath){

    if (_panoramaPath != null && _panoramaPath != '') {
        embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
            , id:'vr0204KrpanoSWFObject'
            , xml:_panoramaPath
            , target:"vr0204Pano",wmode:"opaque"
            , flash: "only" 
            , passQueryParameters:true
            , bgcolor:"#f5f5f5"});
        $("#vr0204Pano").css('display','block');
        eval("$('#vr0204imagePreview').imuiDialog('open')");
    }
        
}