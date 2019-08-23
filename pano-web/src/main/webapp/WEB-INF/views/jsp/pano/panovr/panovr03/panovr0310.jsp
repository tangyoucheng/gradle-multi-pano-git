<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.product_vr.form.vr03.Vr0310Form"%>
<%@ page import="cn.com.cdic.cnst.CommonConstants"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>音频热点预览</title>
</imui:head>

<div class="imui-form-container">
	 <form id="vr0310Form" action="vr03/vr0310"  method="post" >
		 <input type="hidden" id="vr0310panoramaPath" name="vr0310panoramaPath" value="${vr0310panoramaPath}" />
		 
		 <div id="vr0310SoundPreviewDiv" style="width: 500px; height: 500px;">
                   <script type="text/javascript">
                       var xmlPath = $('#vr0310panoramaPath').val();
                       embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                           , id:"panoMusicId"
                           , xml:xmlPath
                           , target:"vr0310SoundPreviewDiv",wmode:"opaque"
                           , flash: "only" 
                           , bgcolor:"#f5f5f5"
                           , passQueryParameters:true});
                   </script>
           </div>
     </form>
</div>