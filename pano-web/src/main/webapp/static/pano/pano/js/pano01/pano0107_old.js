var ic0107Interval = setInterval(function(){showMiniMap()},1000);
var ic0107HotspotInterval = setInterval(function(){loadMapHotspot()},1000);
/**
 * 场景地图上热点的操作
 */
$(document).ready(function(){
    
     // 返回处理
    $('#back-button,#confirm-button').click(function() {
        ic0107DoBack();
    });
    
    //添加导航图上的热点
    $('#add-mini-map-hotspot-button').click(function(){
        addMapHotspot();
    });
    
    //保存热点操作
    $('#save-button').click(function(){
        saveMapHotspot();
    });
    
});

//显示导航图
function showMiniMap(){
    
    var krpano = document.getElementById("ic0107KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get){
        return false;
    }
    //查看当前展览有无导航图，有即显示
    var check = $('#miniMapCheck').val();
    if(check == 'true'){
        var newlayer ='layer_nimi_map_container';
        krpano.call('addlayer('+newlayer+')');
        krpano.set('layer['+newlayer+'].url',$('#expositionMapPath').val());
        krpano.set('layer['+newlayer+'].keep',true);
        krpano.set('layer['+newlayer+'].align','rightbottom');
        krpano.set('layer['+newlayer+'].x',10);
        krpano.set('layer['+newlayer+'].y',10);
        krpano.set('layer['+newlayer+'].bgcolor','0xCCCCCC');
        krpano.set('layer['+newlayer+'].bgalpha','0.5');
        krpano.set('layer['+newlayer+'].scalechildren',true);
        krpano.set('layer['+newlayer+'].maskchildren',true);
        krpano.set('layer['+newlayer+'].handcursor',false);
    }
    
    clearInterval(ic0107Interval);
}
//添加小地图上的热点
function addMapHotspot(){
    var krpano = document.getElementById("ic0107KrpanoSWFObject");
    var newlayer ='newlayer' + new Date().getTime() + krpano.get('layer.count');
    krpano.call('addlayer('+newlayer+')');
    // TODO 测试图片
    krpano.set('layer['+newlayer+'].parent','layer_nimi_map_container');
    krpano.set('layer['+newlayer+'].url',$('#styleSelect').val());
    krpano.set('layer['+newlayer+'].align','rightbottom');
    krpano.set('layer['+newlayer+'].bgalpha','1.0');
    krpano.set('layer['+newlayer+'].x', 10);
    krpano.set('layer['+newlayer+'].y', 10);
    krpano.set('layer['+newlayer+'].handcursor',true);
    krpano.set('layer['+newlayer+'].ondown','draglayer();');
    krpano.set('layer['+newlayer+'].onup','js(doChidLayerOnUp(layer_nimi_map_container,get(name)));');
    krpano.set('layer['+newlayer+'].onclick','js(ic0107RemoveLayer(get(name)))');
}
//返回方法
function ic0107DoBack() {
    $("#back-form").submit();
}
//删除层
function ic0107RemoveLayer(_hotspotName) {
    imuiConfirm('是否删除热点?', '确认', function() {
      var krpano = document.getElementById("ic0107KrpanoSWFObject");
      krpano.call('removelayer('+_hotspotName+')')
    });
}

// 子层不能拖拽到父层之外
function doChidLayerOnUp(parentLayer,childerLayer) {

  var krpano = document.getElementById("ic0107KrpanoSWFObject");
  var childerLayerMaxX =  parseFloat(krpano.get('layer['+parentLayer+'].imagewidth')) -15;
  var childerLayerX = parseFloat(krpano.get('layer['+childerLayer+'].x'));
  var childerLayerMaxY =  parseFloat(krpano.get('layer['+parentLayer+'].imageheight')) - 15;
  var childerLayerY = parseFloat(krpano.get('layer['+childerLayer+'].y'));

  if (childerLayerX < 0) {
      krpano.set('layer['+childerLayer+'].x',5);
  }
  if (childerLayerY < 0) {
      krpano.set('layer['+childerLayer+'].y',5);
  }
  if (childerLayerMaxX < childerLayerX) {
      krpano.set('layer['+childerLayer+'].x',childerLayerMaxX);
  } 
  if (childerLayerMaxY < childerLayerY) {
      krpano.set('layer['+childerLayer+'].y',childerLayerMaxY);
  } 
}



//显示导航图上的热点
function loadMapHotspot(){
    if($('#miniMapSpotInfoListJson').val() == undefined 
            || $('#miniMapSpotInfoListJson').val().length == 0){
        return false;
    }
    var spotInfoList = JSON.parse($('#miniMapSpotInfoListJson').val());
    if (spotInfoList == null || spotInfoList.length <= 0) {
        return false;
    }
    var krpano = document.getElementById("ic0107KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get){
        return false;
    }
    //读取已有的热点信息
    $(spotInfoList).each(function(index, recordData){
        
        var newlayer ='v_' + recordData.expositionMapHotspotId;
        
        krpano.call('addlayer('+newlayer+')');
        krpano.set('layer['+newlayer+'].parent','layer_nimi_map_container');
        if(recordData.expositionMapHotspotUrl != '' && recordData.expositionMapHotspotUrl != null){
            krpano.set('layer['+newlayer+'].url',recordData.expositionMapHotspotUrl);
        }else{
            krpano.set('layer['+newlayer+'].url',FrameworkConstants.VAL_IMAGE_MAPPOINT);
        }
        krpano.set('layer['+newlayer+'].align','rightbottom');
        krpano.set('layer['+newlayer+'].bgalpha','1.0');
        krpano.set('layer['+newlayer+'].x', recordData.expositionMapHotspotX);
        krpano.set('layer['+newlayer+'].y', recordData.expositionMapHotspotY);
        krpano.set('layer['+newlayer+'].handcursor',true);
        krpano.set('layer['+newlayer+'].ondown','draglayer();');
        krpano.set('layer['+newlayer+'].onup','js(doChidLayerOnUp(layer_nimi_map_container,get(name)));');
        krpano.set('layer['+newlayer+'].onclick','js(ic0107RemoveLayer(get(name)))');
    });
    clearInterval(ic0107HotspotInterval);
}

//自定义热点对象
function mapHotspot(_expositionMapHotspotId,_expositionMapHotspotX,_expositionMapHotspotY,_expositionMapHotspotUrl) {
    this.expositionMapHotspotId=_expositionMapHotspotId; 
    this.expositionMapHotspotX=_expositionMapHotspotX; 
    this.expositionMapHotspotY=_expositionMapHotspotY; 
    this.expositionMapHotspotUrl=_expositionMapHotspotUrl; 
}

//保存导航图上的热点
function saveMapHotspot(){
    imuiConfirm('是否录入热点?', '确认', function() {
    var hotspotInfoList = [];
    
    var krpano = document.getElementById("ic0107KrpanoSWFObject");
    for(var m=0; m<krpano.get('layer.count'); m++) {
            var _parentLayer = '';
            if (krpano.get('layer['+m+'].parent') != null) {
               _parentLayer = krpano.get('layer['+m+'].parent').toString();
            }
            //判断是否有父层，有父层即为导航图热点
            if(_parentLayer!=''
                && _parentLayer.indexOf('layer_nimi_map_container') != -1) {
                var _positionX = krpano.get('layer['+m+'].x').toString();
                var _positionY = krpano.get('layer['+m+'].y').toString();
                var _url = krpano.get('layer['+m+'].url');
                var positionX = "";
                var positionY = "";
                // 平面图的场合
                if (Number(_positionX) >= 0) {
                    // 正数的场合
                    positionX = Math.round(parseFloat(_positionX)*1000)/1000;
                } else {
                    // 负数的场合
                    positionX = _positionX.substring(0,1) + Math.round(parseFloat(_positionX.substring(1))*1000)/1000;
                }
                if (Number(_positionY) >= 0) {
                    // 正数的场合
                    positionY = Math.round(parseFloat(_positionY)*1000)/1000;
                } else {
                    // 负数的场合
                    positionY = _positionY.substring(0,1) + Math.round(parseFloat(_positionY.substring(1))*1000)/1000;
                }
                var recordData = new mapHotspot();
                recordData.expositionMapHotspotId = krpano.get('layer['+m+'].name').split('v_').join('');
                recordData.expositionMapHotspotX = positionX;
                recordData.expositionMapHotspotY = positionY;
                recordData.expositionMapHotspotUrl=_url; 
                hotspotInfoList.push(recordData);
            }

    }

    // 做成FormData对象
    var ajaxSubmitFormData = form2js($("#pano0203FormAjaxSubmit")[0]);
    // var hotspotInfoData =
    // $.param(hotspotInfoList.serializeObject("hotspotInfoList"))
    var hotspotInfoData = hotspotInfoList.serializeObject("hotspotInfoList")
    ajaxSubmitFormData = $.extend({}, hotspotInfoData, ajaxSubmitFormData);
    
    $('#spotInfoListSubmitJson').val(JSON.stringify(hotspotInfoList));
    //提交后台
    $(".imui-validation-error").remove();
    var _ajaxUrl = getMemberContextPath('pano0107/doSave');
    var param = $("#ic0107Form").serialize();
    jQuery.post(_ajaxUrl, param, function(data){
        if(CommonUtilJs.processAjaxSuccessAfter(data)){
        	eval("$('#ic0107Finish').imuiDialog('open')");
        }
    });

});
}