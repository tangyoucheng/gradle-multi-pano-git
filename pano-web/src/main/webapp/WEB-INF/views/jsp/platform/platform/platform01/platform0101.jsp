<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!DOCTYPE html >
<html>
<head>
<platform:head></platform:head>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/header.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/index.css'/>" />
<!-- 信息显示组件 -->
<link href="static/framework/plugins/overhang/1.0.5/overhang.css" rel="stylesheet" />
<script src="static/framework/plugins/overhang/1.0.5/overhang.js"></script>
<script src="static/framework/plugins/overhang/messageUtil.js"></script>
<script src="static/sh/js/common/common.js"></script>
<script src="static/sh/js/sh01/sh0101.js"></script>
</head>
<body>
    <div class="head_bar">
        <div class="head_bar_con">
            <form:form action="/" modelAttribute="sh0101Form">
                <input type="hidden" id="loginId" name="loginId" value="${sh0101Form.loginId }">
                <platform:header loginId="${sh0101Form.loginId }" userDisplayName="${sh0101Form.userDisplayName }"></platform:header>
            </form:form>
        </div>
    </div>
    <div class="banner_con">
        <!-- The Gallery as inline carousel, can be positioned anywhere on the page -->
        <div id="blueimp-image-carousel" class="blueimp-gallery blueimp-gallery-carousel">
            <div class="slides"></div>
            <h3 class="title"></h3>
            <!--             <a class="prev">‹</a> -->
            <!--             <a class="next">›</a> -->
            <!--             <a class="play-pause"></a> -->
        </div>
        <div class="banner" style="height: 700px;">
            <ul class="banner_ul">
                <li class="banner_img">
                    <a href="javascript:void(0);">
                        <img src="static/img/1.jpg" />
                    </a>
                </li>
                <li class="banner_img">
                    <a href="javascript:void(0);">
                        <img src="static/img/2.jpg" />
                    </a>
                </li>
                <li class="banner_img">
                    <a href="javascript:void(0);">
                        <img src="static/img/3.jpg" />
                    </a>
                </li>
            </ul>
        </div>
        <div class="search_index rounded-left">
            <form action="<c:url value='/room'/>" method="post" onsubmit="return checkSearch(this)"></form>
            <div class="search_text input-group col-sm-4  pl-0  pr-0 rounded-left">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-white border-0 ">
                        <span class="icon-sh-position fs-2x" aria-hidden="true"></span>
                    </span>
                </div>
                <input id="searchCity" class="form-control  h-100 border-0" name="city" autocomplete="off"
                    placeholder="城市或目的地" type="text" />
            </div>
            <button onclick="checkSearch()" class="btn_pink_search textCt">
                <span>搜索房源</span>
            </button>
        </div>
        <span class="bg_up"></span>
        <span class="bg_down"></span>
    </div>
    <!--房间展示-->
    <div class="rooms_show">
        <div class="index_T">
            <h1>别住酒店，住我家</h1>
            <span>有朋自远方来，不亦乐乎</span>
        </div>
    </div>
</body>
</html>