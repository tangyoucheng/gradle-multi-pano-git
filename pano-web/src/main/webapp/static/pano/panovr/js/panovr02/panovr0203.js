var vr0203Interval = setInterval(function(){loadHotspotInfo();},500);

/**
 * 全景图热点编辑
 */
$(document).ready(function(){
     // 返回处理
    $('#back-button,#confirm-button').click(function() {
        setLookAtForEdit();
        vr0203DoBack();
    });
    
    //添加单点信息热点
    $('#normal-hotspot-button').click(function(){
        
        $("#popupPage").imuiPageDialog({
            url: getMemberContextPath('pano0208'),
            title: '选择单点热点',
            modal: true,
            width: 1000,
            height: 700,
            parameter: {
                expositionId: $("#expositionId").val(),
                materialTypeId: PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_IMAGE
            }
        });
        return false;
    });
    
     //多边形热点编辑处理
    $('#polygon-hotspot-button').click(function(){
        //得到当前视角的中心点
        setLookAtForEdit();
        $('#vr0108positionAthForEdit').val(positionAth);
        $('#vr0108positionAtvForEdit').val(positionAtv);
        $('#polygon-hotspot').submit(); 
    });
    
    //添加场景切换热点
    $('#guide-hotspot-button').click(function(){
        
        $("#popupPage").imuiPageDialog({
            url: getMemberContextPath('pano0208'),
            title: '选择场景切换热点',
            modal: true,
            width: 1000,
            height: 700,
            parameter: {
                expositionId: $("#expositionId").val(),
                materialTypeId: PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE
            }
        });
        return false;
            
    });
    
    //添加LOGO
    $('#logo-hotspot-button').click(function(){
        
        $("#popupPage").imuiPageDialog({
            url: getMemberContextPath('pano0208'),
            title: '选择Logo信息',
            modal: true,
            width: 1000,
            height: 700,
            parameter: {
                expositionId: $("#expositionId").val(),
                materialTypeId: PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_LOGO
            }
        });
        return false;
            
    });
    
    
     // 指令框中编辑音频热点图片
    $('#button-other-image').click(function(){
        eval("$('#vr0203HotspotCommandChoice2').imuiDialog('close')");
        callVr0209();
    });
    
    function callVr0209(){
    	
    	$('#flag').val("preview");
    	setLookAtForEdit();
        $("#popupPage").imuiPageDialog({
            url: getMemberContextPath('panoVr0209'),
            title: '预览音频热点效果',
            modal: true,
            width: 820,
            height: 750,
            parameter: {
            	seconfhotspotImageUrlVr0203: seconfhotspotImageUrl,
        	    vr0203MusicHotspot: 'true',
                positionAthForEdit: positionAth,
                positionAtvForEdit: positionAtv,
                vr0209hotspotId: vr0203HotspotImageId,
                vr0203HotspotName: vr0203HotspotName,
                vr0203HotspotAth: vr0203HotspotAth,
                vr0203HotspotAtv: vr0203HotspotAtv,
                hotspotScale: vr0203HotspotScale,
                expositionIdForVr0209: $('#expositionId').val(),
                panoramaIdForVr0209: $('#panoramaId').val(),
                firsthotspotImageIdVr0203: firsthotspotImageId,
                seconfhotspotImageIdVr0203: seconfhotspotImageId,
                firstSortKeyVr0203: firstSortKey,
                secondSortKeyVr0203: secondSortKey
            }
        });
        return false;
    }

// 指令框中编辑音频热点图片
    $('#button-other-icon').click(function(){
        eval("$('#vr0203HotspotCommandChoice2').imuiDialog('close')");
        $("#popupPage").imuiPageDialog({
            url: getMemberContextPath('pano0208'),
            title: '选择单点音频热点',
            modal: true,
            width: 1000,
            height: 700,
            parameter: {
            	reEdit:"true",
                expositionId: $("#expositionId").val(),
                selectedHotspotId: vr0203HotspotName.split('v_').join(''),
                materialTypeId: vr0203HotspotImageTypeId,
                hotspotTypeChoice: PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC,
                firsthotspotImageIdIc0203: firsthotspotImageId,
                seconfhotspotImageIdIc0203: seconfhotspotImageId,
                firstSortKeyIc0203: firstSortKey,
                secondSortKeyIc0203: secondSortKey
            }
        });
        return false;
    });
   
    // 指令框中编辑音频热点大小
    $('#button-other-size').click(function(){
    	$('#flag').val("size");
        eval("$('#vr0203HotspotCommandChoice2').imuiDialog('close')");
        setLookAtForEdit();
         $("#popupPage").imuiPageDialog({
             url: getMemberContextPath('panoVr0209'),
             title: '编辑音频热点大小',
             modal: true,
             width: 820,
             height: 750,
             parameter: {
            	 seconfhotspotImageUrlVr0203: seconfhotspotImageUrl,
            	 vr0203MusicHotspot: 'true',
                 positionAthForEdit: positionAth,
                 positionAtvForEdit: positionAtv,
                 vr0203HotspotName: vr0203HotspotName,
                 vr0203HotspotAth: vr0203HotspotAth,
                 vr0203HotspotAtv: vr0203HotspotAtv,
                 hotspotScale: vr0203HotspotScale,
                 vr0209hotspotId: vr0203HotspotImageId,
                 expositionIdForVr0209:$('#expositionId').val(),
                 panoramaIdForVr0209: $('#panoramaId').val()
             }
         });
         return false;
    });
    // 指令框中删除音频热点
    $('#button-delete-other').click(function(){
        vr0203RemoveHotspot2(vr0203HotspotName);
    });    

    // 指令框中编辑热点图片
    $('#button-edit-icon').click(function(){
        eval("$('#vr0203HotspotCommandChoice').imuiDialog('close')");
        $("#popupPage").imuiPageDialog({
            url: getMemberContextPath('pano0208'),
            title: '选择单点信息热点',
            modal: true,
            width: 1000,
            height: 700,
            parameter: {
        	    reEdit:"true",
                expositionId: $("#expositionId").val(),
                selectedHotspotId: vr0203HotspotName.split('v_').join(''),
                materialTypeId: vr0203HotspotImageTypeId,
                hotspotTypeChoice: PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_IMAGE
            }
        });
        return false;
    });
    
    // 指令框中编辑热点大小
    $('#button-edit-size').click(function(){
    	$('#flag').val("size");
        eval("$('#vr0203HotspotCommandChoice').imuiDialog('close')");
        setLookAtForEdit();
         $("#popupPage").imuiPageDialog({
             url: getMemberContextPath('panoVr0209'),
             title: '编辑热点大小',
             modal: true,
             width: 820,
             height: 750,
             parameter: {
            	 vr0203MusicHotspot: 'false',
                 positionAthForEdit: positionAth,
                 positionAtvForEdit: positionAtv,
                 vr0203HotspotName: vr0203HotspotName,
                 vr0203HotspotAth: vr0203HotspotAth,
                 vr0203HotspotAtv: vr0203HotspotAtv,
                 hotspotScale: vr0203HotspotScale,
                 vr0209hotspotId: vr0203HotspotImageId,
                 expositionIdForVr0209:$('#expositionId').val(),
                 panoramaIdForVr0209: $('#panoramaId').val()
             }
         });
         return false;
    });
    // 指令框中删除热点
    $('#button-delete-hotspot').click(function(){
        vr0203RemoveHotspot(vr0203HotspotName);
    });
    
    // 确定登录按钮按下，录入热点信息
    $('#save-button').click(function() {
        imuiConfirm('是否录入热点?', '确认', function() {
        	CommonUtilJs.loadMask();
            var hotspotInfoList = [];
            var krpano = document.getElementById("vr0203KrpanoSWFObject");
            
            for(var m=0; m<krpano.get('hotspot.count'); m++) {
                    var hotspotImageTypeId = krpano.get('hotspot['+m+'].hotspotImageTypeId');
                    //检查该热点类型
                    var hotspotType = "";
                    if(hotspotImageTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_CHANGE_SCENE){
                        //导航热点
                        hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE;
                    }
                    if(hotspotImageTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_IMAGE){
                        //普通热点
                        hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_IMAGE;
                    }
                    if(hotspotImageTypeId == "2"){
                    	//音频热点
                    	hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC;
                    }	
                    if(hotspotImageTypeId == PanoConstants.VAL_MATERIAL_TYPE_HOTSPOT_LOGO){
                        //LOGO热点
                        hotspotType = PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_LOGO;
                    }
                    var _positionAth = krpano.get('hotspot['+m+'].ath').toString();
                    var _positionAtv = krpano.get('hotspot['+m+'].atv').toString();
                    var positionAth = "";
                    var positionAtv = "";
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
                    
                        var recordData = new panoramaHotspot();
                        recordData.hotspotId = krpano.get('hotspot['+m+'].name').split('v_').join('');
                        recordData.hotspotAth = positionAth;
                        recordData.hotspotAtv = positionAtv;
                        recordData.hotspotScale = krpano.get('hotspot['+m+'].scale');
                        recordData.hotspotType = hotspotType;
                        
                        if(krpano.get('hotspot['+m+'].hasSecondImage') == 'yes'){
                        	recordData.hotspotImageId = krpano.get('hotspot['+m+'].firstImageId');
                        	recordData.secondHotspotImageId = krpano.get('hotspot['+m+'].secondImageId');
                        	recordData.firstSortkey = krpano.get('hotspot['+m+'].firstSortkey');
                        	recordData.secondSortkey = krpano.get('hotspot['+m+'].secondSortkey');
                        	
                        }else{
                            recordData.hotspotImageId = krpano.get('hotspot['+m+'].hotspotImageId');
                        }
                        
                        hotspotInfoList.push(recordData);
                        
            }
            
            
            $('#spotInfoListSubmitJson').val(JSON.stringify(hotspotInfoList));
            //提交后台
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('panoVr0203/doSave');
            var param = $("#vr0203Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
            	CommonUtilJs.removeMask();
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                    eval("$('#vr0203Finish').imuiDialog('open')");
                }
           });
            
       });
    });
});

  var positionAth = "";
  var positionAtv = "";
//跳转画面前取得当前视角点
function setLookAtForEdit(){
    $("#0203BackAth").val('');
    $("#0203BackAtv").val('');

  //得到当前场景的中心点
  var krpano = document.getElementById("vr0203KrpanoSWFObject");
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
   $("#0203BackAth").val(positionAth);
   $("#0203BackAtv").val(positionAtv);
}


//自定义热点对象
function panoramaHotspot(_mapHotspotId,_vtourHotspotAth,_vtourHotspotAtv,_hotspotScale,_hotspotType,
                         _hotspotTooltip,_hotspotImageId,_secondHotspotImageId,_firstSortkey,_secondSortkey) {
    this.hotspotId=_mapHotspotId; 
    this.hotspotAth=_vtourHotspotAth; 
    this.hotspotAtv=_vtourHotspotAtv; 
    this.hotspotScale = _hotspotScale;
    this.hotspotType=_hotspotType;
    this.hotspotTooltip=_hotspotTooltip;
    this.hotspotImageId=_hotspotImageId;
    this.secondHotspotImageId = _secondHotspotImageId;
    this.firstSortkey = _firstSortkey;
    this.secondSortkey = _secondSortkey;
}



// 自定义热点属性
var vr0203HotspotName;
var vr0203HotspotImageTypeId;
var vr0203HotspotAth;
var vr0203HotspotAtv;
var vr0203HotspotScale;
var vr0203HotspotImageId;
var seconfhotspotImageUrl;
var seconfhotspotImageId;
var firsthotspotImageId;
var firstSortKey;
var secondSortKey;
 

// 打开热点操作指令框
function vr0203OpenHotspotCommandChoiceDiv(_hotspotName,_materialTypeId,_ath,_atv,_hotspotScale,_hotspotImageId){
    vr0203HotspotName = _hotspotName;
    vr0203HotspotImageTypeId = _materialTypeId;
    vr0203HotspotAth = _ath;
    vr0203HotspotAtv = _atv;
    vr0203HotspotScale = _hotspotScale;
    vr0203HotspotImageId = _hotspotImageId;
    eval("$('#vr0203HotspotCommandChoice').imuiDialog('open')");
}

// 打开单点音频热点操作指令框
function vr0203OpenHotspotCommandChoice2Div(_hotspotName,_materialTypeId,_ath,_atv,_hotspotScale,_seconfhotspotImageId,_firsthotspotImageId,_firstSortKey,_secondSortKey){
    vr0203HotspotName = _hotspotName;
    vr0203HotspotImageTypeId = _materialTypeId;
    vr0203HotspotAth = _ath;
    vr0203HotspotAtv = _atv;
    vr0203HotspotScale = _hotspotScale;
    seconfhotspotImageId = _seconfhotspotImageId;
    firsthotspotImageId = _firsthotspotImageId;
    vr0203HotspotImageId = _firsthotspotImageId;
    firstSortKey = _firstSortKey;
    secondSortKey = _secondSortKey;
    eval("$('#vr0203HotspotCommandChoice2').imuiDialog('open')");
}

//删除热点
function vr0203RemoveHotspot(_hotspotName) {
    imuiConfirm('是否删除当前热点?', '确认', function() {
        eval("$('#vr0203HotspotCommandChoice').imuiDialog('close')");
        var krpano = document.getElementById("vr0203KrpanoSWFObject");
        krpano.call('removehotspot('+_hotspotName+')')
    });
}

//删除音频热点
function vr0203RemoveHotspot2(_hotspotName) {
    imuiConfirm('是否删除当前热点?', '确认', function() {
        eval("$('#vr0203HotspotCommandChoice2').imuiDialog('close')");
        var krpano = document.getElementById("vr0203KrpanoSWFObject");
        krpano.call('removehotspot('+_hotspotName+')')
    });
}

function vr0203DoBack() {
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
    
    var krpano = document.getElementById("vr0203KrpanoSWFObject");
    if (krpano == null || undefined == krpano.get){
        return false;
    }
    
    //读取已有的热点信息
    $(spotInfoList).each(function(index, recordData){
        var _hotspotType = recordData.hotspotType;
        var newspot = 'v_' + recordData.hotspotId;
        krpano.call('addhotspot('+newspot+')');
        // 初期显示时保持热点图片的原始尺寸的设定
        krpano.set('hotspot['+newspot+'].zoom',true);
        //多边形热点
        if(_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_POLYGON){
            krpano.call('addhotspot('+newspot+')');
            krpano.set('hotspot['+newspot+'].zorder',"1");
            // 多边形点的设定
            $(recordData.pointList).each(function(pointIndex,pointData){
                krpano.set('hotspot['+newspot+'].point['+pointIndex+'].ath',pointData.polygonPointAth);
                krpano.set('hotspot['+newspot+'].point['+pointIndex+'].atv',pointData.polygonPointAtv);
            });    
            krpano.set('hotspot['+newspot+'].onclick','js(vr0203RemoveHotspot(get(name));)');
            krpano.set('hotspot['+newspot+'].bordercolor','0x489620');
            krpano.set('hotspot['+newspot+'].fillalpha','0.4');
            krpano.set('hotspot['+newspot+'].fillcolor','0x489620');
        }else{
            //普通热点
            krpano.set('hotspot['+newspot+'].zorder',"2");
            //判断当前热点显示图片是否是gif并有对应的png图片
            if(recordData.hasPngImage == 'true'){
                var gifstyle = 'gifstyle' + recordData.hotspotId;
                krpano.call('addstyle('+gifstyle+')');
                krpano.set('style['+gifstyle+'].name',gifstyle);
                var hotspotImagePathForGif = recordData.hotspotImagePath.substring(0, recordData.hotspotImagePath.lastIndexOf(".")) + ".png";        
                krpano.set('style['+gifstyle+'].url',hotspotImagePathForGif );
                krpano.set('style['+gifstyle+'].crop','0|0|'+recordData.gifWidth+'|'+recordData.gifHeight);
                krpano.set('style['+gifstyle+'].framewidth',recordData.gifWidth);
                krpano.set('style['+gifstyle+'].frameheight',recordData.gifHeight);
                krpano.set('style['+gifstyle+'].frame','0');
                krpano.set('style['+gifstyle+'].lastframe',recordData.gifFrame);
                krpano.set('style['+gifstyle+'].onloaded','hotspot_animate('+recordData.gifDelayTime+');');
                krpano.call('hotspot['+newspot+'].loadstyle('+gifstyle+');');
            }else{
                // 显示数据库中的热点图片
                if(recordData.hotspotImagePath != null 
                        && recordData.hotspotImagePath != '' ){
                    krpano.set('hotspot['+newspot+'].url',recordData.hotspotImagePath);
                }
            }
            krpano.set('hotspot['+newspot+'].ath',recordData.hotspotAth);
            krpano.set('hotspot['+newspot+'].atv',recordData.hotspotAtv);

            //判断是否是单点音频热点
                 
            if(_hotspotType == PanoConstants.VAL_HOTSPOT_TYPE_HOTSPOT_MUSIC ){
        	    krpano.set('hotspot['+newspot+'].hasSecondImage','yes');
                krpano.set('hotspot['+newspot+'].firstImageId',recordData.firstHotspotImageId);
                krpano.set('hotspot['+newspot+'].secondImageId',recordData.secondHotspotImageId);
                krpano.set('hotspot['+newspot+'].firstSortkey',recordData.firstSortkey);
                krpano.set('hotspot['+newspot+'].secondSortkey',recordData.secondSortkey);
            
                krpano.set('hotspot['+newspot+'].onclick','js(vr0203OpenHotspotCommandChoice2Div(get(name),'+recordData.materialTypeId+',get(ath),get(atv),get(scale),'+recordData.secondHotspotImageId+','+recordData.hotspotImageId+','+recordData.firstSortkey+','+recordData.secondSortkey+'))');
                krpano.set('hotspot['+newspot+'].hotspotImageTypeId','2');  
            }else{
                krpano.set('hotspot['+newspot+'].onclick','js(vr0203OpenHotspotCommandChoiceDiv(get(name),'+recordData.materialTypeId+',get(ath),get(atv),get(scale),'+recordData.hotspotImageId+'))');
                krpano.set('hotspot['+newspot+'].hotspotImageTypeId',recordData.materialTypeId);  
            }
            
            
            krpano.set('hotspot['+newspot+'].ondown','draghotspot();');
            krpano.set('hotspot['+newspot+'].hotspotImageId',recordData.hotspotImageId);  
            
            // 初期显示时保持热点图片的原始尺寸的设定
           // krpano.set('hotspot['+newspot+'].zoom',true);
            var hotspotScale =recordData.hotspotScale
            if(hotspotScale != "" && hotspotScale != null){
               krpano.set('hotspot['+newspot+'].scale',hotspotScale);
            }else{
                krpano.set('hotspot['+newspot+'].scale',"1.0"); 
            }
        }
    });
    clearInterval(vr0203Interval);  
}

// 增加场景切换热点、增加普通热点
function vr0203AddImageHotspot(_hotspotImageId, _hotspotImageUrl, 
                               _materialTypeId,_width,_height,_frame,
                               _delay,_hasPngImage,_selectedHotspotId,_reEdit){
    //判断是否为单点音频热点	
    var hotspotImageId = _hotspotImageId.split("/");
	firsthotspotImageId = hotspotImageId[0];
	seconfhotspotImageId = hotspotImageId[1];
	firstSortKey=hotspotImageId[2];
	secondSortKey=hotspotImageId[3];
	
    var krpano = document.getElementById("vr0203KrpanoSWFObject");
    var newspot ='newspot' + krpano.get('hotspot.count');
    
    // 现有热点编辑
    if(_selectedHotspotId != undefined 
            && _selectedHotspotId != null 
            && _selectedHotspotId.length >0 ){
        
        _selectedHotspotId = _selectedHotspotId.split('v_').join('');
        newspot ='v_'+ _selectedHotspotId;
    }
    
    if(_reEdit != 'true'){
    	newspot = 'v_'+ newspot;
        krpano.call('addhotspot('+newspot+')');
    }
    
	var gifstyle = 'gifstyle' + Math.random();
    krpano.call('addstyle('+gifstyle+')');
    krpano.set('style['+gifstyle+'].name',gifstyle);
    var rebornAth = krpano.get('hotspot['+newspot+'].ath');
	var rebornAtv = krpano.get('hotspot['+newspot+'].atv');
	var rebornScale = krpano.get('hotspot['+newspot+'].scale');
	krpano.call('removehotspot('+newspot+')');
	krpano.call('addhotspot('+newspot+')');
	krpano.set('hotspot['+newspot+'].ath',rebornAth);
    krpano.set('hotspot['+newspot+'].atv',rebornAtv);
    krpano.set('hotspot['+newspot+'].scale',rebornScale);
    krpano.set('hotspot['+newspot+'].ondown','draghotspot();');
    krpano.set('hotspot['+newspot+'].zorder',"3");
    krpano.set('hotspot['+newspot+'].zoom',true);
    //判断是否为单点音频热点
    if( hotspotImageId[1] != "" && hotspotImageId[1] != null ){
        //判断当前热点显示图片是否是gif并有对应的png图片
        if(_hasPngImage == 'true'){
                
            var _hotspotImageUrlForGif = _hotspotImageUrl.substring(0, _hotspotImageUrl.lastIndexOf(".")) + ".png";        
            krpano.set('style['+gifstyle+'].url',_hotspotImageUrlForGif);    

            krpano.set('style['+gifstyle+'].crop','0|0|'+_width+'|'+_height);
            krpano.set('style['+gifstyle+'].framewidth',_width);
            krpano.set('style['+gifstyle+'].frameheight',_height);
            krpano.set('style['+gifstyle+'].frame','0');
            krpano.set('style['+gifstyle+'].lastframe',_frame);
            krpano.set('style['+gifstyle+'].onloaded','hotspot_animate('+_delay+');');
            krpano.call('hotspot['+newspot+'].loadstyle('+gifstyle+');');
        }else{
            krpano.set('hotspot['+newspot+'].url',_hotspotImageUrl);
        }
        //判断当前热点是否是再编辑
        if(_reEdit != 'true'){
        	krpano.set('hotspot['+newspot+'].ath',krpano.get('view.hlookat'));
            krpano.set('hotspot['+newspot+'].atv',krpano.get('view.vlookat'));
            krpano.set('hotspot['+newspot+'].scale',"1.0"); 
        }
        
        krpano.set('hotspot['+newspot+'].onclick','js(vr0203OpenHotspotCommandChoice2Div(get(name),'+_materialTypeId+',get(ath),get(atv),get(scale),'+seconfhotspotImageId+','+firsthotspotImageId+','+firstSortKey+','+secondSortKey+'))');
        krpano.set('hotspot['+newspot+'].hotspotImageId',firsthotspotImageId);  
        krpano.set('hotspot['+newspot+'].hotspotImageTypeId',"2");
        
        krpano.set('hotspot['+newspot+'].hasSecondImage','yes');
        krpano.set('hotspot['+newspot+'].firstImageId',firsthotspotImageId);
        krpano.set('hotspot['+newspot+'].secondImageId',seconfhotspotImageId);
        krpano.set('hotspot['+newspot+'].firstSortkey',hotspotImageId[2]);
        krpano.set('hotspot['+newspot+'].secondSortkey',hotspotImageId[3]);
        
    }else{
        
        //判断当前热点显示图片是否是gif并有对应的png图片
        if(_hasPngImage == 'true'){
            var _hotspotImageUrlForGif = _hotspotImageUrl.substring(0, _hotspotImageUrl.lastIndexOf(".")) + ".png";        
            krpano.set('style['+gifstyle+'].url',_hotspotImageUrlForGif);
        
            krpano.set('style['+gifstyle+'].crop','0|0|'+_width+'|'+_height);
            krpano.set('style['+gifstyle+'].framewidth',_width);
            krpano.set('style['+gifstyle+'].frameheight',_height);
            krpano.set('style['+gifstyle+'].frame','0');
            krpano.set('style['+gifstyle+'].lastframe',_frame);
            krpano.set('style['+gifstyle+'].onloaded','hotspot_animate('+_delay+');');
            krpano.call('hotspot['+newspot+'].loadstyle('+gifstyle+');');
        }else{
            krpano.set('hotspot['+newspot+'].url',_hotspotImageUrl);
        }
        
        if(_reEdit != 'true'){
        	krpano.set('hotspot['+newspot+'].ath',krpano.get('view.hlookat'));
            krpano.set('hotspot['+newspot+'].atv',krpano.get('view.vlookat'));
            krpano.set('hotspot['+newspot+'].scale',"1.0");
        }
        
        krpano.set('hotspot['+newspot+'].onclick','js(vr0203OpenHotspotCommandChoiceDiv(get(name),'+_materialTypeId+',get(ath),get(atv),get(scale),'+_hotspotImageId+'))');
        krpano.set('hotspot['+newspot+'].hotspotImageId',_hotspotImageId);  
        krpano.set('hotspot['+newspot+'].hotspotImageTypeId',_materialTypeId);
        
    }
    
}

// 在呼出的热点大小调整画面调整了热点大小后，显示在当前画面上
function changeHotspotSize(_hotspotScale,_hotspotName){
    var krpano = document.getElementById("vr0203KrpanoSWFObject");
    krpano.set('hotspot['+_hotspotName+'].scale',_hotspotScale);
}

