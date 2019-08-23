//================================================
//场景一览处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){

    // 检索处理
    $("#search-button").click(function() {
          var _ajaxUrl = getMemberContextPath('panoVr0206/doSearch');
          var param = $("#vr0206Form").serialize();
          jQuery.post(_ajaxUrl, param,function(data){
              if(CommonUtilJs.processAjaxSuccessAfter(data)){
                  createListDataTable(data);              
               }          
          });
    });
    
    //返回处理
    $('#vr0206-back-button').click(function(){
       $('#backToVr0104PanoramaId').val($('#vr0206PanoramaId').val());
       $("#back-to-vr0104").submit();
    });
    
    //确认处理
    $('#confirm-button').click(function(){
        imuiConfirm('是否保存当前场景序号状态?', '确认', function() {
        	CommonUtilJs.loadMask();
            var _panoramaInfoJson = [];
            var _sortKeys = [];
            $("#vr0206ListDataTable tbody tr").each(function(){
                var _sortKey = $('#'+$(this)[0].id+'_sortKey').html();
                if (_sortKey != "" && _sortKey != null ) {
                    _sortKeys.push(_sortKey);
                    var recordData = new Panorama();
                    recordData.panoramaId = $(this)[0].id;
                    recordData.panoramaSortKey = _sortKey;
                    _panoramaInfoJson.push(recordData);
                }
            })
            $('#panoramaInfoJson').val(JSON.stringify(_panoramaInfoJson));
            var _ajaxUrl = getMemberContextPath('panoVr0206/doSaveSortKey');
            var param = $('#vr0206Form').serialize();
            jQuery.post(_ajaxUrl, param, function(data){
            	 CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
//                    $('#backToVr0104PanoramaId').val($('#vr0206PanoramaId').val());
                    $("#vr0206-refresh-from-edit-sortkey").submit();
                }
            });
        });
    });
    
    //行上移
    $('#up-button').click(function(){
        moveUpListTr();
    });
    
    //行下移
    $('#down-button').click(function(){
        moveDownListTr();
    });

});

//自定场景对象
function Panorama(_panoramaId,_sortKey) {
  this.panoramaId=_panoramaId; 
  this.panoramaSortKey=_sortKey; 
}
//选中的全景图ID
var selectedPanoramaId;

// 设定被选行的颜色
function setTrColor(_selectedPanoramaId) {
    selectedPanoramaId = _selectedPanoramaId;
    $('#vr0206ListDataTable tbody td').each(function(){
        $(this).css("background-color","");
    })
    $("#" +selectedPanoramaId + ' td').css("background-color","#DACAA6");
}

//行的移动——上移
function moveUpListTr(){
    if(selectedPanoramaId != null && selectedPanoramaId != ''){
        var currentTr = $("#"+selectedPanoramaId);
        var prevTr = $("#"+selectedPanoramaId).prev();
        //上一行存在
        if(prevTr!=null && prevTr!='' && prevTr!=undefined){
            //上一个tr的ID
            var prevTrId = prevTr[0].id;
            prevTr.insertAfter(currentTr);
            //当前tr的sortKey
            var currentSortKey = $("#"+selectedPanoramaId+"_sortKey").html();
            //上个tr的sortkey
            var prevSortKey = $("#"+prevTrId+"_sortKey").html();
            //交换sortKey
            $("#"+selectedPanoramaId+"_sortKey").html(prevSortKey);
            $("#"+prevTrId+"_sortKey").html(currentSortKey);
        }
        
    }
}

//行的移动——下移
function moveDownListTr(){
    if(selectedPanoramaId != null && selectedPanoramaId != ''){
        var currentTr = $("#"+selectedPanoramaId);
        var nextTr = $("#"+selectedPanoramaId).next();
        //下一行存在
        if(nextTr!=null && nextTr!='' && nextTr!=undefined){
            //下一个tr的ID
            var nextTrId = nextTr[0].id;
            nextTr.insertBefore(currentTr);
            //当前tr的sortKey
            var currentSortKey = $("#"+selectedPanoramaId+"_sortKey").html();
            //下个tr的sortkey
            var nextSortKey = $("#"+nextTrId+"_sortKey").html();
            //交换sortKey
            $("#"+selectedPanoramaId+"_sortKey").html(nextSortKey);
            $("#"+nextTrId+"_sortKey").html(currentSortKey);
        }
        
    }
}


// 场景编辑按钮按下时的处理
function editPanorama(_panoramaId) {
    //全景图编辑
    $("#updatePanoramaPage").imuiPageDialog({
        url: getMemberContextPath('panoVr0202'),
        title: '场景信息编辑',
        modal: true,
        width: 1000,
        height: 700,
        parameter: {
            vr0202TheLastedSceneHotspotId: $('#vr0104LastedSceneHotspotId').val(),
            vr0202CurrentRecommendHotspotId: $('#vr0104CurrentHotspotId').val(), 
            flagFromParentPage: 'vr0206',
            panoramaIdForDelete:_panoramaId,
            panoramaId: _panoramaId,
            expositionId:$("#expositionId").val(),
            expositionName:$("#expositionName").val()
        },
        close: function(event, ui) {
            //编辑画面关闭时，删除临时文件
            var _ajaxUrl = getMemberContextPath('panoVr0202/doDeleteTempFile');
            var param = 'panoramaId='+$("#vr0202panoramaId").val();
            jQuery.post(_ajaxUrl, param, function(data){
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                }
            });
            
            $("#vr0206Form").submit();
        }
     });
    return false;
}

//检索预览图
function doPreview(_panoramaPath){
    if (_panoramaPath != null && _panoramaPath != '') {
        embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
            , id:'vr0206KrpanoNewObject'
            , xml:_panoramaPath
            , target:"vr0206PreviewPano",wmode:"opaque"
            , flash: "only" 
            , passQueryParameters:true});
            
        $("#vr0206PreviewPano").css('display','block');
        eval("$('#vr0206imagePreview').imuiDialog('open')");
    }
}


// 刷新一览数据
function createListDataTable(data) {
    if(CommonUtilJs.processAjaxSuccessAfter(data)){
        var isjson = typeof(data) == "object" && Object.prototype.toString.call(data).toLowerCase() == "[object object]" && !data.length;
        if(data.indexOf("指定条件的数据不存在。请改变索条件后再检索。") != -1){
            imuiAlert('指定条件的数据不存在。请改变索条件后再检索。');
            $("#vr0206ListDataTable tbody").html('');
            $('#confirmDiv').hide();
            return false;
        }
        $('#confirmDiv').show();
        var vr0206JsForm = JSON.parse(data);
        // 刷新一览数据
        var tbodyHtml = "";
        $(vr0206JsForm.panoramaInfo).each(function(index, item) {
            tbodyHtml += '<tr id='+item.panoramaId+' style="height:10px;cursor: pointer;" onclick="setTrColor(' +"'" +item.panoramaId +"'" +');">';
            tbodyHtml += '<td class="align-C valign-M" style="width:40px; cursor: pointer;" onclick="editPanorama('+"'" +item.panoramaId +"'" +')"><span class="im-ui-icon-common-16-settings"/></td>';
            tbodyHtml += '<td title=' +item.panoramaId +' class="align-L valign-M selected" style="width:20%;">' +item.panoramaId +'</td>';
            tbodyHtml += '<td title=' +item.panoramaName +' class="align-L valign-M selected" style="width:20%;">' +item.panoramaName +'</td>';
            tbodyHtml += '<td id='+item.panoramaId+"_sortKey"+' title=' +item.panoramaSortKey +' class="align-L valign-M selected" style="width:20%;">' +item.panoramaSortKey +'</td>';
            tbodyHtml += '<td title=' +item.notes +' class="align-L valign-M selected" style="width:20%;">' +item.notes +'</td>';    
            tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('+"'" +item.panoramaPath +"'" +')"/>' +'</td>';
            tbodyHtml += "</tr>";
        });
        $("#vr0206ListDataTable tbody").html(tbodyHtml);
    }
}


