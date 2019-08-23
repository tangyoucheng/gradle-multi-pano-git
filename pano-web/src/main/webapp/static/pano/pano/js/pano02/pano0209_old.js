//=================================
//热点大小,浮动层大小，场景工具条编辑 
//=================================
// 自定义pluginName和hotspotName
//var _pluginName;
var _spotName;
//定义大小变量
var hotspotScale;
//自定义计时器
var musicInterval;

var ic0209_loadHotspotFromIc0203_Interval;
var ic0209_loadHotspotFromIc0203_Interval_processFlag = false;

var load_ic0209_Interval;
var load_ic0209_Interval_processFlag = false;

var ic0209_loadLayer_Interval;
var ic0209_loadLayer_Interval_processFlag;

var ic0209ButtonsInterval;
var ic0209ButtonsInterval_processFlag = false;

var ic0209RecommendInterval;
var ic0209RecommendInterval_processFlag = false;

$(document).ready(function(){

	doMakeIc0209krpano();

    //勾选框点击动态预览工具条效果
    $("input[name='chkBox']").click(function(){
        reloadButtonsBar_ic0209();
    });
    if($('#commandTypeFromIc0105').val() == '' || $('#commandTypeFromIc0105').val() == null){
        $('#ExpoGoSceneInfoText').attr("disabled","true");
    }
    // 自定义工具条全选和全解除
    $("#selectAll").click(function() {
        var options=document.getElementsByName("chkBox"); 
        for (var i=0;i<options.length;i++){ 
                options[i].checked=true; 
            }
        reloadButtonsBar_ic0209();
    });
    $("#cancelAll").click(function() {
        var options=document.getElementsByName("chkBox"); 
        for (var i=0;i<options.length;i++){ 
                options[i].checked=false; 
            }
        reloadButtonsBar_ic0209();
    });
    
   // 保存处理
   $('#button-ic0209-confirm').click(function(){
       set0209LookAtForEdit(); 
       if($('#commandTypeFromIc0105').val() == 'buttons' ){
           //调用Ic0105保存工具条方法
           reloadButtonsBar_ic0209();
           doSaveButtonsBar(_selectedButtons);
       }else{
           imuiConfirm('是否保存修改?', '确认', function() {
               if($('#flag').val() == 'size'){
                   eval("$('#popupPage').imuiPageDialog('close')");
					
                   changeHotspotSize(hotspotScale,$('#ic0203HotspotName').val());
               }else{
//                   if($('#commandTypeFromIc0105').val() == 'commonInfo' 
//                       && $('#recommendInfoText').val() =='' || $('#recommendInfoText').val() == null){
//                       imuiAlert("请输入推荐路线信息！");
//                       return false;
//                   }
                   var _ajaxUrl = getContextPath('/ic02/ic0209/doSaveHotspotScale');
                   $('#hotspotScale').val(hotspotScale);
//                   $('#recommendInfo').val($('#recommendInfoText').val());
                   var param = $("#ic0209Form").serialize();
                   jQuery.post(_ajaxUrl, param, function(data){
                       if(CommonUtilJs.processAjaxValidateError(data)){
                           
                           if(data == 'recommend_info'){
                               $("#back-to-ic0105").submit();
                           }
    
                           if(data == 'true_hotspot'){
                               $("#back-to-ic0104").submit();
                           }
                           
                           if(data == 'true_guid_hotspot'){
                               $('#recommendHotspotIdBackTo0104').val($('#ic0209hotspotId').val());
                               $("#back-to-ic0104").submit();
                           }
                           
                           if(data == 'false_guid_hotspot'){
                               $('#recommendHotspotIdBackTo0104').val('');
                               $("#back-to-ic0104").submit();
                           }
                           
                           if(data == 'true_layer'){
                               $("#back-to-ic0105").submit();
                           }
                      }
                 });  
               }
          });  
       }
   });
   
   hotspotScale =$('#hotspotScale').val();
   //如果热点不是导航热点，隐藏推荐路径点设置的功能
   if(CdicConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE != $('#ic0209HotspotType').val()){
       $('#recommendTr').hide();
       $('#tooltipTr').hide();
       if($('#commandTypeFromIc0105').val() != 'commonInfo'){
           $('#editExpoGoSceneInfoTr').hide();
       }
   }else{
       $('#recommendInfoTh').hide();
       $('#recommendInfoTd').hide();
   }
   
   //如果热点已是推荐线路点，设置其在页面加载后为选中状态
   if($('#recommendInfo').val() != null && $('#recommendInfo').val() != ''){
       $('#recommendFlag').attr("checked","true");
//       _recommendInfo = $('#expoRecommendInfo').val();
       _recommendInfo = "推荐路线";
       load_ic0209();
   } else {
       _recommendInfo = '';
   }
   // 推荐路线勾选框点击事件
   $('#recommendFlag').click(function(){
       var _krpano = document.getElementById("ic0209PanoId");
       if($('#recommendFlag')[0].checked){
           $('#recommendInfoTh').show();
           $('#recommendInfoTd').show();
//           _recommendInfo = $('#expoRecommendInfo').val();
           _recommendInfo = "推荐路线";
           load_ic0209();
       }else{
           $('#recommendInfoTh').hide();
           $('#recommendInfoTd').hide();
           _recommendInfo = "";
           load_ic0209();
       }
   });

    //区分浮动层大小调整，工具条自定义和热点大小调整的画面
    editPage_ic0209();

});

function doMakeIc0209krpano(){
    var ic0209Krpano = document.getElementById("ic0209videoPreviewKrpano");
    if (ic0209Krpano == null || undefined == ic0209Krpano.get){
        var xmlPath = $('#ic0209panoramaPath').val()
        embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                , id:"ic0209PanoId"
                , xml:xmlPath
                , target:"ic0209Pano",wmode:"opaque"
                , flash: "only" 
                , bgcolor:"#f5f5f5"
                , passQueryParameters:true
            });
    }
}

//自定义推荐信息
var _recommendInfo;

//区分浮动层大小调整，工具条自定义和热点大小调整的画面
function editPage_ic0209(){
    // 调整浮动层大小
    if($('#commandTypeFromIc0105').val() == 'flowInfo' ){
        ic0209_loadLayer_Interval = setInterval(function(){loadLayer();},500);
        $('#buttonslistDiv').hide();
        $('#buttonsTitleDiv').hide();
        $('#editRecommendTitleDiv').hide();
//        $('#editRecommendInfoTr').hide();
        return false;
    }

    // 自定义工具条
    if($('#commandTypeFromIc0105').val() == 'buttons' ){
        ic0209ButtonsInterval = setInterval(function(){ic0209ShowCurrentButtonsBar()},1000);
        $('#sizeTr').hide();
        $('#recommendTr').hide();
        $('#sizeTitleDiv').hide();
        $('#editRecommendTitleDiv').hide();
//        $('#editRecommendInfoTr').hide();
        $('#tooltipTr').hide();
        $('#ic0209Pano').css("height","100px");
        return false;
    }
    
    // 编辑推荐路线信息
    if($('#commandTypeFromIc0105').val() == 'commonInfo' ){
        ic0209ButtonsInterval = setInterval(function(){ic0209ShowCurrentButtonsBar()},1000);
        $('#sizeTr').hide();
        $('#buttonslistDiv').hide();
        $('#recommendTr').hide();
        $('#buttonsTitleDiv').hide();
        $('#sizeTitleDiv').hide();
        $('#tooltipTr').hide();
        $('#ic0209Pano').css("height","280px");
        ic0209RecommendInterval = setInterval(function(){loadRecommendInfo_ic0209();},500);
        return false;
    }

    //预览0203音频热点效果
    if($('#flag').val() == 'preview'){
        $('#buttonslistDiv').hide();
        $('#buttonsTitleDiv').hide();
//        $('#editRecommendInfoTr').hide();
        $('#editRecommendTitleDiv').hide();
        $('#sizeTitleDiv').hide();
        $('#sizeTr').hide();
        $("#button-ic0209-confirm").hide();
        musicInterval = setInterval(function(){loadMusicHotspotFromIc0203();},500);
        return false;
    }
    
    // 热点大小
    // 分0203和0104操作两种情况
    if($('#flag').val() == 'size'){
        ic0209_loadHotspotFromIc0203_Interval = setInterval(function(){loadHotspotFromIc0203();},500);
        $('#buttonslistDiv').hide();
        $('#buttonsTitleDiv').hide();
//        $('#editRecommendInfoTr').hide();
        $('#editRecommendTitleDiv').hide();
        $('#sizeTitleDiv').show();
        $('#sizeTr').show();
        $("#button-ic0209-confirm").show();
    }else {
        load_ic0209_Interval = setInterval(function(){load_ic0209();},1000);
        $('#buttonslistDiv').hide();
        $('#buttonsTitleDiv').hide();
//        $('#editRecommendInfoTr').hide();
        $('#editRecommendTitleDiv').hide();
        $('#sizeTitleDiv').show();
        $('#sizeTr').show();
        $("#button-ic0209-confirm").show();
    }
    
}

//显示现有状态下的工具条样式
function ic0209ShowCurrentButtonsBar(){
    
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get || ic0209ButtonsInterval_processFlag){
        return false;
    }
    ic0209ButtonsInterval_processFlag = true;
    
    //2.重构并顺序排列选中的工具条按钮的x坐标
    var strs = [];
    strs = $('#selectedButtons').val().split(',');
    //坐标变量
    for(var i=0;i<strs.length;i++){
        _krpano.set('layer['+strs[i]+'].x',i *40);
        _krpano.set('layer['+strs[i]+'].visible',true);
        //让对应单选框保持选中
        $('#'+strs[i]).attr("checked",true);
    }
    
    _krpano.set('layer[defaultskin_buttons].width',strs.length*40);
    clearInterval(ic0209ButtonsInterval);
    ic0209ButtonsInterval_processFlag = false;
}

//展览工具条选中的按钮名称组合
var _selectedButtons = '';
//设定工具条的显示
function reloadButtonsBar_ic0209(){
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get){
        return false;
    }
     
    _selectedButtons = '';
    //坐标变量
    var _positionX = 0;
    $('#ic0209ListDataTable').find('input[type="checkbox"]').each(function(){
        var _chkBoxId = $(this)[0].id;
        if($('#'+_chkBoxId)[0].checked){
            _krpano.set('layer['+_chkBoxId+'].x',_positionX);
            _krpano.set('layer['+_chkBoxId+'].visible',true);
            _positionX += 40;
            //拼接字符串
            _selectedButtons = _selectedButtons + _chkBoxId +",";
        } else {
            _krpano.set('layer['+_chkBoxId+'].visible',false);
        }
    });
    _krpano.set('layer[defaultskin_buttons].width',_positionX);

}


// 加载热点
function load_ic0209(){
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get
            || load_ic0209_Interval_processFlag
            || $('#hotspotImageInfoJson').val() == ''){
        return false;
    }
    load_ic0209_Interval_processFlag = true;
    var newspot = 'v_' + $('#ic0209hotspotId').val();
    _krpano.call("stopdelayedcall('"+newspot+"');");
    _krpano.call('removehotspot('+newspot+')')
    _krpano.call('addhotspot('+newspot+')'); 
    // 初期显示时保持热点图片的原始尺寸的设定
    _krpano.set('hotspot['+newspot+'].zoom',true);
    _krpano.set('hotspot['+newspot+'].handcursor',false);
    var ath = $('#hotspotAth').val();
    var atv = $('#hotspotAtv').val();
    _krpano.set('hotspot['+newspot+'].ath',ath);
    _krpano.set('hotspot['+newspot+'].atv',atv);
    _krpano.set('hotspot['+newspot+'].zorder',"2");
    if(hotspotScale != "" && hotspotScale != null){
        _krpano.set('hotspot['+newspot+'].scale',hotspotScale);
    }else{
       _krpano.set('hotspot['+newspot+'].scale',"1.0");     
    }

    var hotspotImageInfoObject = JSON.parse($('#hotspotImageInfoJson').val());
    if(hotspotImageInfoObject == null){
        clearInterval(ic0209_loadHotspotFromIc0203_Interval);
        ic0209_loadHotspotFromIc0203_Interval_processFlag = false;
        return false;
    }
    
    var hotspotStyle = null;
    var hotspotStyleOnloaded = null;
    var _url = getContextPath('/'+hotspotImageInfoObject.hotspotImagePath);
    if(hotspotImageInfoObject.hasPngImage=='true'){
        var gifstyle = 'gifstyle' + newspot.split('v_').join('');
        _krpano.call('addstyle('+gifstyle+')');
        _krpano.set('style['+gifstyle+'].name',gifstyle);
    
        var _hotspotImageUrlForGif = _url.substring(0, _url.lastIndexOf(".")) + ".png";        
        _krpano.set('style['+gifstyle+'].url',_hotspotImageUrlForGif);
        _krpano.set('style['+gifstyle+'].crop','0|0|'+hotspotImageInfoObject.gifWidth+'|'+hotspotImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle+'].framewidth',hotspotImageInfoObject.gifWidth);
        _krpano.set('style['+gifstyle+'].frameheight',hotspotImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle+'].frame','0');
        _krpano.set('style['+gifstyle+'].lastframe',hotspotImageInfoObject.gifFrame);
        
        hotspotStyleOnloaded = 'hotspot_animate('+hotspotImageInfoObject.gifDelayTime+');'
        hotspotStyle = gifstyle;
        
    } else {
        _krpano.set('hotspot['+newspot+'].url',_url); 
    }

    //判断该热点是否是已被设为推荐路线的导航点
   if(CdicConstants.VAL_HOTSPOT_TYPE_HOTSPOT_CHANGE_SCENE == $('#ic0209HotspotType').val()){
       //为该热点增加plugin
       if(_recommendInfo != null && _recommendInfo != ''){
    	 //推荐路线信息为图片时
           _krpano.set('hotspot[recommedInfoHotspot].visible',true);
           _krpano.set('hotspot[recommedInfoHotspot].ath',ath);
           _krpano.set('hotspot[recommedInfoHotspot].atv',atv);
    
			//推荐路线信息为文字时
//           if(hotspotStyleOnloaded == null){
//               hotspotStyleOnloaded = "showrecommend(3,'recommedInfoPlugin','"+newspot+"');";
//           } else {
//               hotspotStyleOnloaded = hotspotStyleOnloaded + "showrecommend(3,'recommedInfoPlugin','"+newspot+"');";
//           }
//           _krpano.set('style[recommedInfoPluginStyle].onloaded',hotspotStyleOnloaded);
//           
//           if(hotspotStyle == null){
//               hotspotStyle = 'recommedInfoPluginStyle';
//           } else {
//               hotspotStyle = hotspotStyle + '|'+'recommedInfoPluginStyle';
//           }
           

       }else{
           _krpano.set('hotspot[recommedInfoHotspot].visible',"false");
//           _krpano.call("stopdelayedcall('"+newspot+"');");
       }
       
   }
   
   if(hotspotStyle != null){
       var hotspotStyleOnloadeds = hotspotStyleOnloaded.split(';');
       if (hotspotStyleOnloadeds.length < 3) {
           _krpano.set('style['+hotspotStyle+'].onloaded',hotspotStyleOnloaded);
       } 
       _krpano.call('hotspot['+newspot+'].loadstyle('+hotspotStyle+');');

   }
   
   clearInterval(load_ic0209_Interval);
   load_ic0209_Interval_processFlag = false;
}

// 由热点编辑画面呼出的热点大小调整，读取热点
function loadHotspotFromIc0203(){
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get 
            || ic0209_loadHotspotFromIc0203_Interval_processFlag
            || $('#hotspotImageInfoJson').val() == ''){
        return false;
    }
    ic0209_loadHotspotFromIc0203_Interval_processFlag = true;
    
    var newspot = $('#ic0203HotspotName').val();
    _krpano.call('addhotspot('+newspot+')'); 
    // 初期显示时保持热点图片的原始尺寸的设定
    _krpano.set('hotspot['+newspot+'].zoom',true);
    var ath = $('#ic0203HotspotAth').val();
    var atv = $('#ic0203HotspotAtv').val();
    _krpano.set('hotspot['+newspot+'].ath',ath);
    _krpano.set('hotspot['+newspot+'].atv',atv);
    _krpano.set('hotspot['+newspot+'].zorder',"2");
    if(hotspotScale != "" && hotspotScale != null){
        _krpano.set('hotspot['+newspot+'].scale',hotspotScale);
    }else{
       _krpano.set('hotspot['+newspot+'].scale',"1.0");     
    }
    
    var hotspotImageInfoObject = JSON.parse($('#hotspotImageInfoJson').val());
    if(hotspotImageInfoObject == null){
        clearInterval(ic0209_loadHotspotFromIc0203_Interval);
        ic0209_loadHotspotFromIc0203_Interval_processFlag = false;
        return false;
    }

    var _url = getContextPath('/'+hotspotImageInfoObject.hotspotImagePath);
    if(hotspotImageInfoObject.hasPngImage=='true'){
        var gifstyle = 'gifstyle' + newspot.split('v_').join('');
        _krpano.call('addstyle('+gifstyle+')');
        _krpano.set('style['+gifstyle+'].name',gifstyle);
        var _hotspotImageUrlForGif = _url.substring(0, _url.lastIndexOf(".")) + ".png";        
        _krpano.set('style['+gifstyle+'].url',_hotspotImageUrlForGif);
        _krpano.set('style['+gifstyle+'].crop','0|0|'+hotspotImageInfoObject.gifWidth+'|'+hotspotImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle+'].framewidth',hotspotImageInfoObject.gifWidth);
        _krpano.set('style['+gifstyle+'].frameheight',hotspotImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle+'].frame','0');
        _krpano.set('style['+gifstyle+'].lastframe',hotspotImageInfoObject.gifFrame);
        _krpano.set('style['+gifstyle+'].onloaded','hotspot_animate('+hotspotImageInfoObject.gifDelayTime+');');
        _krpano.call('hotspot['+newspot+'].loadstyle('+gifstyle+');');
        
    } else {
        _krpano.set('hotspot['+newspot+'].url',_url); 
    }

    clearInterval(ic0209_loadHotspotFromIc0203_Interval);
    ic0209_loadHotspotFromIc0203_Interval_processFlag = false;
    
}

// 由热点编辑画面呼出的音频热点效果，读取热点
function loadMusicHotspotFromIc0203(){
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get
            || $('#firstImageInfoJson').val() == ''
            || $('#secondImageInfoJson').val() == ''){
        return false;
    }
    
    var newspot_1 = $('#ic0203HotspotName').val()+"_1";
    var newspot_2 = $('#ic0203HotspotName').val()+"_2";
    _krpano.call('addhotspot('+newspot_1+')');
    _krpano.call('addhotspot('+newspot_2+')'); 
    // 初期显示时保持热点图片的原始尺寸的设定
    var ath = $('#ic0203HotspotAth').val();
    var atv = $('#ic0203HotspotAtv').val();
    _krpano.set('hotspot['+newspot_1+'].zoom',true);
    _krpano.set('hotspot['+newspot_1+'].ath',ath);
    _krpano.set('hotspot['+newspot_1+'].atv',atv);
    _krpano.set('hotspot['+newspot_1+'].zorder',"2");
    _krpano.set('hotspot['+newspot_2+'].zoom',true);
    _krpano.set('hotspot['+newspot_2+'].ath',ath);
    _krpano.set('hotspot['+newspot_2+'].atv',atv);
    _krpano.set('hotspot['+newspot_2+'].zorder',"2");
    _krpano.set('hotspot['+newspot_2+'].visible',"false");
    
    if(hotspotScale != "" && hotspotScale != null){
        _krpano.set('hotspot['+newspot_1+'].scale',hotspotScale);
        _krpano.set('hotspot['+newspot_2+'].scale',hotspotScale);
    }else{
       _krpano.set('hotspot['+newspot_1+'].scale',"1.0");
       _krpano.set('hotspot['+newspot_2+'].scale',"1.0"); 
    }
    
    var firstImageInfoObject = JSON.parse($('#firstImageInfoJson').val());
    if(firstImageInfoObject != null 
            && firstImageInfoObject.hasPngImage == 'true'){
        var gifstyle = 'gifstyle' + $('#ic0209hotspotId').val().split('v_').join('')+"_1";
        _krpano.call('addstyle('+gifstyle+')');
        _krpano.set('style['+gifstyle+'].name',gifstyle);
        var firstImageUrlPath = firstImageInfoObject.hotspotImagePath;
        var _hotspotImageUrlForGif = firstImageUrlPath.substring(0, firstImageUrlPath.lastIndexOf(".")) + ".png";
        _krpano.set('style['+gifstyle+'].url',_hotspotImageUrlForGif);
        _krpano.set('style['+gifstyle+'].crop','0|0|'+firstImageInfoObject.gifWidth+'|'+firstImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle+'].framewidth',firstImageInfoObject.gifWidth);
        _krpano.set('style['+gifstyle+'].frameheight',firstImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle+'].frame','0');
        _krpano.set('style['+gifstyle+'].lastframe',firstImageInfoObject.gifFrame);
        _krpano.set('style['+gifstyle+'].onloaded','hotspot_animate('+firstImageInfoObject.gifDelayTime+');');
        _krpano.call('hotspot['+newspot_1+'].loadstyle('+gifstyle+');');
        
    }else{
        _krpano.set('hotspot['+newspot_1+'].url',firstImageInfoObject.hotspotImagePath);
    }
    
    var secondImageInfoObject = JSON.parse($('#secondImageInfoJson').val());
    if(secondImageInfoObject != null 
            && secondImageInfoObject.hasPngImage == 'true'){
        var gifstyle_2 = 'gifstyle' + $('#ic0209hotspotId').val().split('v_').join('')+ "_2";
        _krpano.call('addstyle('+gifstyle_2+')');
        _krpano.set('style['+gifstyle_2+'].name',gifstyle_2);
        var secondImageUrlPath = secondImageInfoObject.hotspotImagePath;
        var _hotspotImageUrlForGif = secondImageUrlPath.substring(0, secondImageUrlPath.lastIndexOf(".")) + ".png";
        _krpano.set('style['+gifstyle_2+'].url',_hotspotImageUrlForGif);
        _krpano.set('style['+gifstyle_2+'].crop','0|0|'+secondImageInfoObject.gifWidth+'|'+secondImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle_2+'].framewidth',secondImageInfoObject.gifWidth);
        _krpano.set('style['+gifstyle_2+'].frameheight',secondImageInfoObject.gifHeight);
        _krpano.set('style['+gifstyle_2+'].frame','0');
        _krpano.set('style['+gifstyle_2+'].lastframe',secondImageInfoObject.gifFrame);
        _krpano.set('style['+gifstyle_2+'].onloaded','hotspot_animate('+secondImageInfoObject.gifDelayTime+');');
        _krpano.call('hotspot['+newspot_2+'].loadstyle('+gifstyle_2+');');
    }else{
        _krpano.set('hotspot['+newspot_2+'].url',secondImageInfoObject.hotspotImagePath);
    }
         
    //音频热点的url切换
    switchFlg = true;
    _krpano.set('hotspot['+newspot_1+'].onclick','js(changeUrl_ic0209('+newspot_1+','+newspot_2+'))'); 
    _krpano.set('hotspot['+newspot_2+'].onclick','js(changeUrl_ic0209('+newspot_1+','+newspot_2+'))');     
    
    clearInterval(musicInterval);
}


var switchFlg;
//更换音频热点的图片
function changeUrl_ic0209(newspot_1,newspot_2){
    var _krpano = document.getElementById("ic0209PanoId");
    if(switchFlg){
        _krpano.set('hotspot['+newspot_1+'].visible','false');
        _krpano.set('hotspot['+newspot_2+'].visible','true');
        switchFlg = false;
    }else{
        _krpano.set('hotspot['+newspot_1+'].visible','true');
        _krpano.set('hotspot['+newspot_2+'].visible','false');
        switchFlg = true;
    }
}


//在编辑推荐路线点信息时预览效果
function loadRecommendInfo_ic0209(){
	debugger
	set0209LookAtForEdit();
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get || ic0209RecommendInterval_processFlag){
        return false;
    }
    ic0209RecommendInterval_processFlag == true;
    _krpano.set('layer[defaultskin_buttons].visible',"false");
    //推荐路线信息为文字时
//    _krpano.set('plugin[recommedInfoPlugin].html',$('#recommendInfoText').val());
//    _krpano.set('plugin[recommedInfoPlugin].visible',"true");
    //推荐路线信息为图片时
   _krpano.set('hotspot[recommedInfoHotspot].visible',true);
   _krpano.set('hotspot[recommedInfoHotspot].ath',$("#0209BackAth").val());
   _krpano.set('hotspot[recommedInfoHotspot].atv',$("#0209BackAtv").val());

    clearInterval(ic0209RecommendInterval);
    ic0209RecommendInterval_processFlag = false;
}


// 加载浮动信息层
function loadLayer(){
    var _krpano = document.getElementById("ic0209PanoId");
    if (_krpano == null || undefined == _krpano.get || ic0209_loadLayer_Interval_processFlag){
        return false;
    }
    ic0209_loadLayer_Interval_processFlag = true;
    
    var newlayer = 'flowInfoLayer';
    _krpano.call('addlayer('+newlayer+')');
    _krpano.set('layer['+newlayer+'].border',false);
    _krpano.set('layer['+newlayer+'].align','righttop');
    _krpano.set('layer['+newlayer+'].x', $('#ic0209layerX').val());
    _krpano.set('layer['+newlayer+'].y', $('#ic0209layerY').val());
    _krpano.set('layer['+newlayer+'].scale',hotspotScale);
    //如果是图片浮动信息
    if($('#ic0209flowFileType').val() == CdicConstants.VAL_MATERIAL_TYPE_FLOW_INFO_IMAGE){
        _krpano.set('layer['+newlayer+'].url',$('#ic0209flowFilePath').val());
    }else{
    //如果是文字浮动信息    
        _krpano.set('layer['+newlayer+'].url','%SWFPATH%/plugins/textfield.swf');
        _krpano.set('layer['+newlayer+'].html',$('#ic0209flowFileInfo').val());
        _krpano.set('layer['+newlayer+'].css','text-align:center; font-family:Arial; font-weight:bold; font-size:'+parseFloat(hotspotScale)*36+'px;');
    }
    _krpano.set('layer['+newlayer+'].backgroundalpha',"0");
    _krpano.set('layer['+newlayer+'].height',"auto");
    _krpano.set('layer['+newlayer+'].width',"auto");
    
    clearInterval(ic0209_loadLayer_Interval);
    ic0209_loadLayer_Interval_processFlag = false;
}

// 刷新热点大小
function selectRefresh(){
    //如果是浮动信息层
    hotspotScale = $('#hotspotSizeInfo').val();  
    if($('#commandTypeFromIc0105').val() == 'flowInfo' ){
        loadLayer();
    }else if($('#flag').val() == 'size'){
    //普通热点与音频热点    
        loadHotspotFromIc0203();
    }else{
        load_ic0209();
    }
}

// 预览提示信息
//function recommendPreview(){
//    var _krpano = document.getElementById("ic0209PanoId");
//    _krpano.set('plugin['+_pluginName+'].html',$('#recommendInfotext').val());
//    _krpano.call('hotspot['+_spotName+'].onloaded');
//}

//跳转画面前取得当前视角点
function set0209LookAtForEdit(){
    $("#0209BackAth").val('');
    $("#0209BackAtv").val('');
  var positionAth = "";
  var positionAtv = "";
  //得到当前场景的中心点
  var _krpano = document.getElementById("ic0209PanoId");
  var _positionAth = _krpano.get('view.hlookat').toString();
  var _positionAtv = _krpano.get('view.vlookat').toString();
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
   $("#0209BackAth").val(positionAth);
   $("#0209BackAtv").val(positionAtv);
}


