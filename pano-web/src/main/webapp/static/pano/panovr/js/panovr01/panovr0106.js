var vr0106Interval = setInterval(function(){showMiniMap()},1000);
/**
 * 全景图地图编辑操作
 */
$(document).ready(function(){
    
     // 返回处理
    $('#back-button,#confirm-button').click(function() {
        vr0106DoBack();
    });
    
    // 删除成功后处理
    $('#delete-confirm-button').click(function(){
    	$('#vr0106Form').submit();
    });
    
    //添加导航图
    $('#add-mini-map-button').click(function(){
        addMiniMap();
    });
    
    //为当前展览选择导航图
    $('#set-mini-map-button').click(function(){
        setMiniMap();
    });
    
    //删除导航图
    $('#delete-mini-map-button').click(function(){
        deleteMiniMap();
    });
    
});

//呼出新建导航图的页面
function addMiniMap(){
    $("#addMiniMap").imuiPageDialog({
        url: getMemberContextPath('pano0304'),
        title: '新建导航图',
        modal: true,
        width: 1000,
        height: 700,
        parameter: {
            lasthotspotIdFrom0106: $('#vr0106LasthotspotId').val(),
            currenthotspotIdFrom0106 : $('#vr0106CurrenthotspotId').val(),
            expositionId: $("#expositionId").val(),
            expositionName: $("#expositionName").val(),
            panoramaId:$("#panoramaId").val(),
            existedExpositionMapId: $("#expositionMapId").val(),
        }
    });
    return false
}

//呼出为展览选定地图的页面
function setMiniMap(){
    $('<div id="popupPage"></div>').imuiPageDialog({
        url: getMemberContextPath('pano0305'),
        title: '选择导航图',
        modal: true,
        width: 1000,
        height: 700,
        parameter: {
            lasthotspotIdFrom0106: $('#vr0106LasthotspotId').val(),
            currenthotspotIdFrom0106 : $('#vr0106CurrenthotspotId').val(),
            expositionId: $("#expositionId").val(),
            expositionName: $("#expositionName").val(),
            expositionMapName: $("#vr0106expositionMapName").val(),
            existedExpositionMapId: $("#expositionMapId").val(),
            panoramaId:$("#panoramaId").val()
        },
        close: function(event, ui){
            $('#popupPage').remove();
            $("#vr0106Form").submit();
        }
    });
    
    return false
}
//删除导航图操作
function deleteMiniMap(){
        imuiConfirm('是否删除导航图?', '确认', function() {
            $(".imui-validation-error").remove();
            var _ajaxUrl = getMemberContextPath('panoVr0106/doDelete');
            var param = $("#vr0106Form").serialize();
            jQuery.post(_ajaxUrl, param, function(data){
                if(CommonUtilJs.processAjaxSuccessAfter(data)){
                	eval("$('#vr0106DeleteFinish').imuiDialog('open')");
                }
            });
        });
}

//显示导航图
function showMiniMap(){
    
    var krpano = document.getElementById("vr0106KrpanoSWFObject");
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
//        krpano.set('layer['+newlayer+'].ondown','draglayer();');
    }
    
    clearInterval(vr0106Interval);
}

//返回方法
function vr0106DoBack() {
    $("#back-form").submit();
}



