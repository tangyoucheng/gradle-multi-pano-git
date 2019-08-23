var ic0108Interval = setInterval(function(){loadHotspotInfo();},1000);

/**
 * 图片热点编辑
 */
$(document).ready(function(){
	
    // 多边形热点编辑手册按钮
    $('#edit-button').click(function(){
        // 呼出多边形热点编辑手册画面
         $('<div id="popupPage"></div>').appendTo(document.body).imuiPageDialog({
             url: getMemberContextPath('pano0309'),
             title: '编辑手册',
             modal: true,
             width: 450,
             height: 350,
            
             close: function(event, ui) {
                 $('#popupPage').remove();
             }
         });
         return false;
    });

     // 返回处理
    $('#back-button,#confirm-button').click(function() {
        setLookAtForEdit();
        ic0108DoBack();
    });
    
    // 确定登录按钮按下，录入热点信息
    $('#save-button').click(function() {
        imuiConfirm('是否录入热点?', '确认', function() {
         
            var hotspotInfoList = [];
            var krpano = document.getElementById("ic0108KrpanoSWFObject");
            
            for(var m=0; m<krpano.get('hotspot.count'); m++) {
                var polygonPointInfoList = [];
                
                var polygonPointCount = krpano.get('hotspot['+m+'].point').count
                if (polygonPointCount >2) {
                    //多边形组成点信息  
                    for(var n=0;n<krpano.get('hotspot['+m+'].point').count;n++){
                        
                        var _polygonPointAtv= krpano.get('hotspot['+m+'].point['+n+'].atv').toString();
                        var _polygonPointAth= krpano.get('hotspot['+m+'].point['+n+'].ath').toString();
                        var polygonPointAtv="";
                        var polygonPointAth="";
                        if (Number(_polygonPointAth) >= 0) {
                            // 正数的场合
                            polygonPointAth = Math.round(parseFloat(_polygonPointAth));
                        } else {
                            // 负数的场合
                            polygonPointAth = _polygonPointAth.substring(0,1) + Math.round(parseFloat(_polygonPointAth.substring(1)));
                        }
                        if (Number(_polygonPointAtv) >= 0) {
                            // 正数的场合
                            polygonPointAtv = Math.round(parseFloat(_polygonPointAtv));
                        } else {
                            // 负数的场合
                            polygonPointAtv = _polygonPointAtv.substring(0,1) + Math.round(parseFloat(_polygonPointAtv.substring(1)));
                        }
                        var recordData = new HotspotPoint(); 
                          recordData.polygonPointId=krpano.get('hotspot['+m+'].point['+n+'].name').split('v_').join('');
                          recordData.polygonPointAth=polygonPointAth;
                          recordData.polygonPointAtv=polygonPointAtv;
                          polygonPointInfoList.push(recordData);
                    }
                }
                
                if (polygonPointInfoList.length != 0) {
                    var recordData = new MapHotspot();
                    recordData.hotspotId = krpano.get('hotspot['+m+'].name').split('v_').join('');
                    recordData.hotspotPointListJson=JSON.stringify(polygonPointInfoList);
                    hotspotInfoList.push(recordData);
                }
            }
            
            //提交后台
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('pano0108/doSave');
            $('#spotInfoListJson').val(JSON.stringify(hotspotInfoList));
            var param = $("#ic0108Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#ic0108Finish').imuiDialog('open')");
                  }
            });
                
       });
    });
    
});

//跳转画面前取得当前视角点
function setLookAtForEdit(){
    $("#0108BackAth").val('');
    $("#0108BackAtv").val('');
  var positionAth = "";
  var positionAtv = "";
  //得到当前场景的中心点
  var krpano = document.getElementById("ic0108KrpanoSWFObject");
  var _positionAth = krpano.get('view.hlookat').toString();
  var _positionAtv = krpano.get('view.vlookat').toString();
  // 全景图的场合
  if (Number(_positionAth) >= 0) {
      // 正数的场合
      positionAth = Math.round(parseFloat(_positionAth));
  } else {
      // 负数的场合
      positionAth = _positionAth.substring(0,1) + Math.round(parseFloat(_positionAth.substring(1)));
  }
  if (Number(_positionAtv) >= 0) {
      // 正数的场合
      positionAtv = Math.round(parseFloat(_positionAtv));
  } else {
      // 负数的场合
      positionAtv = _positionAtv.substring(0,1) + Math.round(parseFloat(_positionAtv.substring(1)));
  }
   $("#0108BackAth").val(positionAth);
   $("#0108BackAtv").val(positionAtv);
}


//自定义热点对象
function MapHotspot(_mapHotspotId,_hotspotPointListJson) {
    this.hotspotId=_mapHotspotId; 
    this.hotspotPointListJson=_hotspotPointListJson;
}

//自定义多边形点对象
 function HotspotPoint(_polygonPointId,_polygonPointAth,_polygonPointAtv){
     this.polygonPointId=_polygonPointId;
     this.polygonPointAth=_polygonPointAth;
     this.polygonPointAtv=_polygonPointAtv;
 }

// 返回处理
function ic0108DoBack() {
    $("#back-form").submit();
}

//图上加载热点处理
function loadHotspotInfo() {
    if($('#spotInfoListJson').val() == undefined 
            || $('#spotInfoListJson').val().length == 0){
        return false;
    }
    var spotInfoList = JSON.parse($('#spotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }
    var krpano = document.getElementById("ic0108KrpanoSWFObject");
    if (undefined == krpano.get){
        return false;
    }
    
    clearInterval(ic0108Interval);
    //读取已有的热点信息
    $(spotInfoList).each(function(index, hotspotData){
        var newspot = 'v_' + hotspotData.hotspotId;
        krpano.call('addhotspot('+newspot+')');
        // 多边形点的设定
        $(hotspotData.pointList).each(function(pointIndex,pointData){
            krpano.set('hotspot['+newspot+'].point['+pointIndex+'].ath',pointData.polygonPointAth);
            krpano.set('hotspot['+newspot+'].point['+pointIndex+'].atv',pointData.polygonPointAtv);
        });    
        // 初期显示时保持热点图片的原始尺寸的设定
        krpano.set('hotspot['+newspot+'].zoom',true);
        krpano.set('hotspot['+newspot+'].onclick','js(ic0108RemoveHotspot(get(name));)');
        krpano.set('hotspot['+newspot+'].bordercolor','0x489620');
        krpano.set('hotspot['+newspot+'].fillalpha','0.4');
        krpano.set('hotspot['+newspot+'].fillcolor','0x489620');
//       return false;
    });
}

//删除热点
function ic0108RemoveHotspot(_hotspotName) {
    imuiConfirm('是否删除当前多边形?', '确认', function() {
        var krpano = document.getElementById("ic0108KrpanoSWFObject");
        krpano.call('removehotspot('+_hotspotName+')')
    });
}

