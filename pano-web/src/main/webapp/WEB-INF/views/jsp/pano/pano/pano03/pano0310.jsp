<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.cdic.form.ic03.Ic0310Form"%>
<%@ page import="cn.com.cdic.cnst.CommonConstants"%>
<%@ include file="/WEB-INF/view/cdic/common/common.jsp" %>

<imui:head>
    <title>音频热点预览</title>
</imui:head>

<div class="imui-form-container">
	 <form id="ic0310Form" action="ic03/ic0310"  method="post" >
		 <input type="hidden" id="ic0310panoramaPath" name="ic0310panoramaPath" value="${ic0310panoramaPath}" />
		 
		 <div id="ic0310SoundPreviewDiv" style="width: 500px; height: 500px;">
                   <script type="text/javascript">
                       var xmlPath = $('#ic0310panoramaPath').val();
                       embedpano({swf:FrameworkConstants.VAL_EMBEDPANO_SWF
                           , id:"panoMusicId"
                           , xml:xmlPath
                           , target:"ic0310SoundPreviewDiv",wmode:"opaque"
                           , flash: "only" 
                           , bgcolor:"#f5f5f5"
                           , passQueryParameters:true});
                   </script>
           </div>
     </form>
</div>