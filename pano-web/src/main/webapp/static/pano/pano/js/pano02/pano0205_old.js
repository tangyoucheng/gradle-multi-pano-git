
var ic0205Interval;
var videoInterval;
//================================================
//为热点添加素材图片加载时的处理
//【入力】
//【返却】
//【作成】
//【更新】
//【概要】
//================================================
$(document).ready(function(){
	//检查展览模式
	expositionVrFlag();
    doMakekrpano();
 
     //关闭音乐试听窗口操作
    $('.ui-dialog-titlebar-close ui-corner-all').click(function(){
        ic0205StopSound(); 
    });
    $('#ic0205Div li').hide();
    //画面初期化隐藏图片素材列表、音乐素材列表和外部链接输入栏
    radioBoxSelectFunc();
    // 该热点已有类型
    if($('input[name="urlType"]:checked').val() != '' && $('input[name="urlType"]:checked').val() != null
            && $('input[name="urlType"]:checked').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK){
//        radioBoxSelectFunc();
        doAjaxPage();
    }

    //热点类型radiobox的点击事件
    $('input[name="urlType"]').click(function(){
         radioBoxSelectFunc();
         var radioboxVal = $('input[name="materialBelongType"]:checked').val();
         $('#theCommandFromRadiobox').val('true');
         if(radioboxVal !='' && radioboxVal != null && $('input[name="urlType"]:checked').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK){
             doAjaxPage();
         }
    });
    
    //素材归属radiobox的点击事件
    $('input[name="materialBelongType"]').click(function(){
       var radioboxVal = $('input[name="materialBelongType"]:checked').val();
       $('#theCommandFromRadiobox').val('true');
       if ($('input[name="urlType"]:checked').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE) {
    	   doAjaxPage();
       } else {
    	   filterLeft();
       }
    });

    $('#ic0205_polygon_property').minicolors({
        control: $(this).attr('data-control') || 'hue',
        defaultValue: $(this).attr('data-defaultValue') || '',
        inline: $(this).attr('data-inline') === 'true',
        letterCase: $(this).attr('data-letterCase') || 'lowercase',
        opacity: true,
        position: $(this).attr('data-position') || 'bottom left',
        change: function(hex, opacity) {
        }
    });
    // 返回处理
    $("#button-ic0205Finish").click(function() {
        ic0205DoBack();
    });

    // 确定处理
    $("#ic0205-confirm-button").click(function() {
        var radioboxVal = $('input[name="urlType"]:checked').val();
        
        //外部链接
        if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK){
            if($('#LinkTexbox').val() == '' || $('#LinkTexbox').val() == null){
                imuiAlert("请输入外部链接地址！");
                return false;
            }
            
            var link =$('#LinkTexbox').val().toLowerCase();
            var pattern = /^((http|https|ftp):\/\/)/;
            var objExp=new RegExp(pattern);
            if(objExp.test(link)!=true){
                imuiAlert(" 外部链接的通讯协议只能是[http、https、ftp]");
                return false;
             }
        }
        
        //音乐
        if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND){
        	if (selectedMaterialId == null || selectedMaterialId == '') {
        		imuiAlert("请选择音频！");
                return false;
        	}
        }

        //信息图
        if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE){
            if ($("#ic0205SelectedListDataTable tbody tr").length != 0) {
                var _hotspotUrlInfoJson = [];
                var _sortKeys = [];
                var _sortKeyCheck = false;
                $("#ic0205SelectedListDataTable tbody tr").each(function(){
                    var _sortKey = $('#'+$(this)[0].id+'_sortKey').val();
                    var strValidate = '';
                    eval("strValidate = \/^[0-9]\\d{0,2}$\/");
                    
                    if (strValidate.test(_sortKey) && _sortKeys.indexOf(_sortKey) < 0) {
                        _sortKeys.push(_sortKey);
                        var recordData = new HotspotUrl();
                        recordData.hotspotUrlObjectId = $(this).children("td:eq(0)").text();
                        recordData.sortKey = _sortKey;
                        _hotspotUrlInfoJson.push(recordData);
                    }
                    //不以0开头
                    if(_sortKey.substring(0,1) == '0'){
                        _sortKeyCheck = true;
                    }
                })
                if(_sortKeyCheck){
                    imuiAlert('顺序请勿以0开头。');
                    return false;
                }
                if (_hotspotUrlInfoJson.length != $("#ic0205SelectedListDataTable tbody tr").length) {
                    imuiAlert('顺序必须填写半角数字，并且不能重复！');
                    return false;
                }
                $('#hotspotUrlInfoJson').val(JSON.stringify(_hotspotUrlInfoJson));
            } 
        }

         //视频
        if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO && (selectedMaterialId == '' || selectedMaterialId == null)){	
            imuiAlert('请选择一个视频素材！');
            return false;
        }
        
        //图文
        if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE){
            if ($("input[name='selectList']").length < 1) {
        		imuiAlert('请选择一个图文素材！');
                return false;
        	} else {
            	$(".imui-dragbox-selected").find("input").each(function(i,d){
              	   $(d).removeAttr("disabled");
                });
        	}
        }
        
        imuiConfirm('是否更新热点信息?', '确认', function() {
            $(".imui-validation-error").remove();
            if($('#isPolygon').val() == 'true'){
                //取得多边形颜色
                var polygon_color = $('#ic0205_polygon_property').attr('value').replace('#','0x');
                $('#polygonFillcolor').val(polygon_color);
                //取得多边形透明度
                var polygon_fillalpha = $('#ic0205_polygon_property').attr('data-opacity');
                $('#polygonFillalpha').val(polygon_fillalpha);
            }
            
            $('#selectedMaterialId').val(selectedMaterialId);
            var param = $("#ic0205Form").serialize();
            
            var _ajaxUrl = getMemberContextPath('pano0205/doEntry');
            jQuery.post(_ajaxUrl, param, function(data){
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#ic0205Finish').imuiDialog('open')");
                }
                
            });
        });
    });
    
   // 模糊查询
   $("#searchText").on("input propertychange", function(e){
       var val = $.trim($(this).val());
       $(".imui-dragbox-deselected").find("li").each(function (i, t) {
	       if($(t).find("label").html().toLowerCase().indexOf(val) >= 0){
	           $(t).show();
	       } else {
	           $(t).hide();
	       }
	   });   
   });
   // 左向右移动时,所有选项设置成显示
   $('#textImgList').bind('imuimovebuttonsetonselectall', function(a,b) {
       $(".imui-dragbox-selected").find("li").show();
   });
});

function doMakekrpano(){
    var ic0205Krpano = document.getElementById("ic0205videoPreviewKrpano");
    if (ic0205Krpano == null || undefined == ic0205Krpano.get){
        embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
            ,xml:"framework/panorama/template/sound/soundpreview.xml"  
            ,id: "ic0205videoPreviewKrpano"
            ,target:"videoPreview"
            ,wmode:"opaque"
            ,flash: "only"
            ,passQueryParameters:true
        });
    }
}

function radioBoxSelectFunc(){

    var radioboxVal = $('input[name="urlType"]:checked').val();
    $('#linkAddressTr').hide();
    $('#musicTable').hide();
    $('#vedioTable').hide();
    $('#imageTable').hide();
    $('#materialBelongTypeTr').hide();
    $('#ic0205pageTable').hide();
    $('#text_image').hide();
    $('#materialSearch').hide();
    if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE){
        //信息图
        $('#ic0205pageTable').show();
        $('#ic0205pageTable').attr("style","width: 50%;");
        $('#imageTable').show();
        $('#materialBelongTypeTr').show();
    }
    
    if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_LINK){
        //外部链接
        $('#linkAddressTr').show();
        $('#imageTable').hide();
        $('#materialBelongTypeTr').hide();
    }

    if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO){
        //视频
        $('#ic0205pageTable').show();
        $('#ic0205pageTable').attr("style","width: 100%;");
        $('#vedioTable').show();
        $('#materialBelongTypeTr').show();
    }
    
    if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND){
        //音乐 
        $('#ic0205pageTable').show();
        $('#ic0205pageTable').attr("style","width: 100%;");
        $('#musicTable').show();
        $('#materialBelongTypeTr').show();
    }
    // 图文
    if(radioboxVal == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE){
    	$('#materialBelongTypeTr').show();
        $('#text_image').show();
        $('#materialSearch').show();
    }

}

//选中的设备信息
var selectedMaterialId;

//返回处理方法
function ic0205DoBack(){
	if(_expositionVrFlag=="0"){
		//展览为非VR展
	    $('#ic0104-refresh-from-ic0205').submit();
	}else{
		$('#vr0104-refresh-from-ic0205').submit();
	}
}
    
//Ajax分页处理
function doAjaxPage(){
	if ($('input[name="urlType"]:checked').val() != PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE){
	    var _ajaxUrl = getMemberContextPath('pano0205/doPage');
	    var param = $("#ic0205Form").serialize();
	    jQuery.post(_ajaxUrl, param, function(data){
	        if(CommonUtilJs.processAjaxSuccessAfter(data)){
	            createListDataTable(data);}
	    });
	} else {
	    var param = $("#ic0205Form").serialize();
	    jQuery.post("pano0205/searchTextImgList", param, function(rsp) {
	        if (rsp.result != "OK") {
	        	imuiAlert('指定条件的数据不存在。请改变索条件后再检索。');
	        } else {
	        	$('#textImg').imuiMultiDragbox('removeAll');
	        	$('#textImg').imuiMultiDragbox('items', rsp.data);
	        	$(rsp.data).each(function(i,d){
	        		text_img_map[d.materialId] = {imgPath:d.materialPath,expositionId:d.expositionId};
	        	});
	        	initTextImgList();
	        }
	    });
	}
}

// 刷新一览数据
function createListDataTable(data) {
    if(CommonUtilJs.processAjaxSuccessAfter(data)){
        var isjson = typeof(data) == "object" && Object.prototype.toString.call(data).toLowerCase() == "[object object]" && !data.length;
        if(data.indexOf("指定条件的数据不存在。请改变索条件后再检索。") != -1){
            imuiAlert('指定条件的数据不存在。请改变索条件后再检索。');
            $("#ic0205ListDataTable tbody").html('');
            $("#ic0205VedioListDataTable tbody").html('');
            $('#ic0205ListMusicTable tbody').html('');
            $('#pageShowInfo').hide();
            $('#ic0205Div li').hide();
            $('#ic0205pageTable').hide();
            return false;
        }
        $('#theCommandFromRadiobox').val('false');
        var ic0205JsForm = JSON.parse(data);
        //分页信息设定
        $('#ic0205Form_pageShowInfo').show();
        $('#ic0205Form_pageShowInfo').each(function(id, item) {
            $(this).prop('outerHTML', ic0205JsForm.pageShowInfos[0])
        
        });
        $('#ic0205Form_pageHiddenInfo').each(function(id, item) {
            $(this).prop('outerHTML', ic0205JsForm.pageShowInfos[1])
        
        });
        // 刷新一览数据
        var tbodyHtml = "";
        var existVideoFlg = false;
        var videoId = "";
        var existVideoTrId = "";
        
        if(ic0205JsForm.urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO){
            $(ic0205JsForm.videoMaterialInfo).each(function(index, item) {
                tbodyHtml += '<tr id="ic0205Tr_' +index +'" style="height:10px;cursor: pointer;" onclick="setSelectedTrColor(this,' +"'" +item.materialId +"'" +');">';
                tbodyHtml += '<td title=' +item.materialId +' class="align-L valign-M selected" style="width:20%;">' +item.materialId +'</td>';
                tbodyHtml += '<td title=' +item.materialName +' class="align-L valign-M selected" style="width:20%;">' +item.materialName +'</td>';
                tbodyHtml += '<td title=' + '"' +item.notes + '"' +' class="align-L valign-M selected" style="width:20%;">' +item.notes +'</td>';
                tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('+"'" +item.materialPath +"'" +')"/>' +'</td>';
                tbodyHtml += "</tr>";
                
                if(item.materialId == $('#existedVideoId').val()){
                    existVideoFlg = true;
                    videoId = item.materialId;
                    existVideoTrId = "ic0205Tr_" +index;
                }
            });
            $("#ic0205VedioListDataTable tbody").html(tbodyHtml);
            
           if(existVideoFlg){
                //让已有视频保持选中
                selectedMaterialId=videoId;
                $("#" + existVideoTrId +" td").css("background-color","#DACAA6");
            }
           $('#ic0205pageTable').show();
        }
        
        var existSoundFlg=false;
        var soundId='';
        var existSoundTrId='';
        if(ic0205JsForm.urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE || ic0205JsForm.urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND){
            $(ic0205JsForm.materialInfo).each(function(index, item) {
                tbodyHtml += '<tr id="ic0205Tr_' +index +'" style="height:10px;cursor: pointer;" onclick="setSelectedTrColor(this,' +"'" +item.materialId +"'" +');">';
                tbodyHtml += '<td title=' +item.materialId +' class="align-L valign-M selected" style="width:20%;">' +item.materialId +'</td>';
                tbodyHtml += '<td title=' +item.materialName +' class="align-L valign-M selected" style="width:20%;">' +item.materialName +'</td>';
                tbodyHtml += '<td title=' + '"' +item.notes + '"' +' class="align-L valign-M selected" style="width:20%;">' +item.notes +'</td>';
                if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE||$('#urlType').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE){
                    tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('+"'" +item.materialPath +"'" +')"/>' +'</td>';
                    tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="选择" class="imui-button imui-running-button" onclick="doSelect(this,' +"'" +item.materialPath +"'" +')"/>' +'</td>';
                }else if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND||$('#urlType').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND){
                	tbodyHtml += '<td class="align-C valign-M" style="width:15px;">' +'<input type="button" value="试听" class="imui-button imui-running-button" onclick="doPlaySound_Ic0205('+"'" +item.materialPath +"'" +')"/>' +'</td>';
                    if(item.materialId == $('#existedSoundId').val()){
                    	    existSoundFlg = true;
                            soundId = item.materialId;
                            existSoundTrId = "ic0205Tr_" +index;
                        }
                }
                
                tbodyHtml += "</tr>";
            });
            if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND
            		||$('#urlType').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND){
                $('#ic0205ListMusicTable tbody').html(tbodyHtml);
                if(existSoundFlg){
                        //让已有音频保持选中
                        selectedMaterialId = soundId;
                        $("#" + existSoundTrId +" td").css("background-color","#DACAA6");
                }
                
            }
            if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE
            		||$('#urlType').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE){
                $("#ic0205ListDataTable tbody").html(tbodyHtml);
            }
            
            $('#ic0205pageTable').show();
        }
        
    }
}

// 设定被选行的颜色
function setSelectedTrColor(_trInfo,_selectedMaterialId) {

    //选中的设备信息设定
    selectedMaterialId = _selectedMaterialId;
	if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE){
        $('#ic0205ListDataTable tbody td').each(function(){
            $(this).css("background-color","");
        })
	}else if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_SOUND){
	    $('#ic0205ListMusicTable tbody td').each(function(){
	        $(this).css("background-color","");
	    })
	}else if($('input[name="urlType"]:checked').val()==PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO){
        //如果是视频
		$('#ic0205VedioListDataTable tbody td').each(function(){
	        $(this).css("background-color","");
	    })
	}
    $("#" + _trInfo.id + ' td ').css("background-color","#DACAA6");
}


//通过素材Id检索预览图
function doPreview(_materialPath){
    if (_materialPath != null && _materialPath != '') {
    	var _urlType = $('input[name="urlType"]:checked').val();
        // 图片预览
        if(_urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_IMAGE || _urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_TEXT_IMAGE){
            $("#imagePreview").attr('src',_materialPath + '?temp=' + Math.random());
            $("#imagePreview").css('display','block');
            eval("$('#ic0205imagePreview').imuiDialog('open')");
        }
        //视频预览
        if(_urlType == PanoConstants.VAL_HOTSPOT_URL_TYPE_VIDEO){
            eval("$('#ic0205videoPreview').imuiDialog('open')");
            videoInterval = setInterval(function(){showVideoPreview(_materialPath)},500);
            
        }
    }
}

//播放视频
function showVideoPreview(_path){
      var krpano = document.getElementById("ic0205videoPreviewKrpano");
      if (krpano == null || undefined == krpano.get){
          return false;
      } 
      clearInterval(videoInterval);
      // 检查是否有0104的音乐在播放，如果有，则先停止0104的音乐播放,此方法针对于从0104点进场景音乐编辑后的情况
      if(_expositionVrFlag=="0"){
    	  var ic0104Krpano = document.getElementById("ic0104KrpanoSWFObject");
          if (ic0104Krpano != null && undefined != ic0104Krpano.get){
              ic0104Krpano.call('stopallsounds();');
          }
      }else{
    	  var vr0104Krpano = document.getElementById("vr0104KrpanoSWFObject");
          if (vr0104Krpano != null && undefined != vr0104Krpano.get){
              vr0104Krpano.call('stopallsounds();');
          }
      }
      var videoUrl = getMemberContextPath(""+_path);
      //设定播放器皮肤尺寸,播放视频
      krpano.set('hotspot[start].onclick','suitableScreen();openvideo('+videoUrl+');');
      krpano.set('hotspot[stop].onclick','closevideo();');
}
//关闭视频
function stopVideo(){
      var krpano = document.getElementById("ic0205videoPreviewKrpano");
      if (krpano == null || undefined == krpano.get){
          return false;
      } 
      krpano.call('closevideo();');
      //检索当前场景是否有背景音乐，有救播放背景音乐
      if(_expositionVrFlag=="0"){
    	  var ic0104krpano = document.getElementById("ic0104KrpanoSWFObject");
          if (ic0104krpano != null && undefined != ic0104krpano.get){
              ic0104krpano.call('playsound(bgsnd,'+$('#currentSoundPath').val()+', 0,0); ');
          }
      }else{
    	  var vr0104krpano = document.getElementById("vr0104KrpanoSWFObject");
          if (vr0104krpano != null && undefined != vr0104krpano.get){
              vr0104krpano.call('playsound(bgsnd,'+$('#currentSoundPath').val()+', 0,0); ');
          }
      }
}
 
// 试听歌曲
function doPlaySound_Ic0205(_materialPath){
    // 检查是否有0104的音乐在播放，如果有，则先停止0104的音乐播放,此方法针对于从0104点进场景音乐编辑后的情况
    if(_expositionVrFlag=="0"){
    	var ic0104Krpano = document.getElementById("ic0104KrpanoSWFObject");
        if (ic0104Krpano != null && undefined != ic0104Krpano.get){
            ic0104Krpano.call('stopallsounds();');
        }
    }else{
    	var vr0104Krpano = document.getElementById("vr0104KrpanoSWFObject");
        if (vr0104Krpano != null && undefined != vr0104Krpano.get){
            vr0104Krpano.call('stopallsounds();');
        }
    }
    // 查看是否已有音乐用的karpano，如果没有再新建
    var newSoundPano = document.getElementById("ic0205SoundPano");
    if (newSoundPano == null || undefined == newSoundPano.get){
        embedpano({swf:PanoConstants.VAL_EMBEDPANO_SWF
            ,xml:"framework/panorama/template/sound/soundpreview.xml"  
            ,id:"ic0205SoundPano"
            ,target:"ic0205SoundPreviewDiv"
            ,wmode:"opaque"
            ,flash: "only"
            ,passQueryParameters:true
        });
    }
    $("#ic0205SoundPreviewDiv").css('display','block');
    eval("$('#ic0205soundPreview').imuiDialog('open')");
    ic0205Interval = setInterval(function(){startSound(_materialPath)},500);
}
 // 设置音乐的播放事件
 function startSound(_materialPath){
     var krpano = document.getElementById("ic0205SoundPano");
     if (krpano == null || undefined == krpano.get){
         return false;
     }  
     krpano.set('hotspot[start].onclick','playsound(s1,'+_materialPath+'?temp='+Math.random()+', 0,0); ');
     clearInterval(ic0205Interval);
 }

 // 设置关闭试听画面，登录成功和关闭检索画面时停止音乐的操作
 function ic0205StopSound(){
     var krpano = document.getElementById("ic0205SoundPano");
     if (krpano == null || undefined == krpano.get){
         return false;
     } 
     krpano.call('stopallsounds();');
     
     //检索当前场景是否有背景音乐，有救播放背景音乐
	if(_expositionVrFlag=="0"){
		var ic0104krpano = document.getElementById("ic0104KrpanoSWFObject");
	     if (ic0104krpano != null && undefined != ic0104krpano.get){
	         ic0104krpano.call('playsound(bgsnd,'+$('#currentSoundPath').val()+', 0,0); ');
	     }
	}else{
		var vr0104krpano = document.getElementById("vr0104KrpanoSWFObject");
	     if (vr0104krpano != null && undefined != vr0104krpano.get){
	         vr0104krpano.call('playsound(bgsnd,'+$('#currentSoundPath').val()+', 0,0); ');
	     }
	}
 }

//选择操作
function doSelect(node,_materialPath){

	var table = node.parentNode.parentNode; 
	var _materialId = table.getElementsByTagName("td")[0].innerHTML;
	var _materialName = table.getElementsByTagName("td")[1].innerHTML;
	var _notes = table.getElementsByTagName("td")[2].innerHTML;
	
    var _trId = 'ic0205TrRight_' +_materialId;
    if ($('#'+ _trId).length != 0) {
        imuiAlert('该素材已经选择。');
        return false;
    } 
    if ($("#ic0205SelectedListDataTable tbody tr").length == 10) {
        imuiAlert('素材最大只能选择10个。');
        return false;
    } 
    // 刷新右侧一览数据
    var tbodyHtml = "";
    tbodyHtml += '<tr id="' +_trId +'" style="height:10px;" >';
    tbodyHtml += '<td title=' +_materialId +' class="align-L valign-M" style="width:20%;">' +_materialId +'</td>';
    tbodyHtml += '<td title=' +_materialName +' class="align-L valign-M" style="width:20%;">' +_materialName +'</td>';
    tbodyHtml += '<td title=' +_notes +' class="align-L valign-M" style="width:auto;">' +_notes +'</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">' +'<input id="' +_trId +'_sortKey" type="text" value="" style="width:30px;text-align:right;" maxlength="2"/>' +'</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">' +'<input type="button" value="预览" class="imui-button imui-running-button" onclick="doPreview('+"'" +_materialPath +"'" +')"/>' +'</td>';
    tbodyHtml += '<td class="align-C valign-M" style="width:50px;">' +'<input type="button" value="取消" class="imui-button imui-running-button" onclick="doCancel('+"'ic0205TrRight_" +_materialId +"'" +')"/>' +'</td>';
    tbodyHtml += "</tr>";
    $("#ic0205SelectedListDataTable tbody").append(tbodyHtml);
    
}

//取消操作
function doCancel(_tr){
    $("#ic0205SelectedListDataTable tbody")[0].deleteRow($('#' + _tr)[0].rowIndex-1);
}

//自定义热点Url对象
function HotspotUrl(_hotspotUrlObjectId,_sortKey) {
  this.hotspotUrlObjectId=_hotspotUrlObjectId; 
  this.sortKey=_sortKey; 
}

//判断展览是否为VR展
var _expositionVrFlag="";
function expositionVrFlag(){
	var _ajaxUrl = getMemberContextPath('pano0110/doVrFlag');
    var param = '';
    param = param + '&selectedExpositionId='+$('#expositionId').val();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
        	_expositionVrFlag=data;
        }
        
    });
}
// 图文前按钮初期化处理
function initTextImgList() {
    var html = "<a herf='javascript:void(0);' onclick='return imgPreview(this);' title='预览'><span class='im-ui-icon-common-16-preview'></span></a>&nbsp;&nbsp";
    $("#textImg").find("li").find("label").before(html);
    $("#textImg").find("li").show();
    filterLeft();
}
// 图片预览
function imgPreview(obj){
   var id = $(obj).nextAll("input").val();
   doPreview(text_img_map[id].imgPath);
   return false;
}
// 根据素材归属 筛选左边可选素材
function filterLeft(){
	$(".imui-dragbox-deselected").find("li").each(function (i, t) {
		var id = $(t).find("input").val();
		if ($('input[name="materialBelongType"]:checked').val() == '2') {
			if(text_img_map[id].expositionId == 'common_material'){
				$(t).show();
			} else {
				$(t).hide();
			}
		} else {
			if(text_img_map[id].expositionId != 'common_material'){
				$(t).show();
			} else {
				$(t).hide();
			}
		}
	});
    return false;
}
 