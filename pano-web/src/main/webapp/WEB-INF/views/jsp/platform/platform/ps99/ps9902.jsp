<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java"%>
<%@ include file="/WEB-INF/views/jsp/platform/common/common.jsp"%>
<!-- 会员门户页面 -->
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<!-- 移动设备 viewport -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--  360浏览器默认使用Webkit内核  -->
<meta name="renderer" content="webkit">
<!--  禁止搜索引擎抓取  -->
<meta name="robots" content="nofollow">
<!--  禁止百度SiteAPP转码  -->
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- Jquery组件引用 -->
<script src="<c:url value='/webjars/jquery/3.4.1/jquery.min.js'/>"></script>
<script src="<c:url value='/webjars/jquery-ui/1.12.1/jquery-ui.min.js'/>"></script>
<!-- popper.js组件引用 -->
<script src="<c:url value='/webjars/popper.js/1.15.0/umd/popper.min.js'/>"></script>
<!-- bootstrap组件引用 -->
<link href="<c:url value='/webjars/bootstrap/4.3.1/css/bootstrap.min.css'/>" rel="stylesheet">
<!-- bootstrap.js的AttachmentMap属性,3039行:this.addAttachmentClass(this._getAttachment(data.placement.replace('-',''))); -->
<script src="<c:url value='/static/framework/plugins/bootstrap/4.3.1/js/bootstrap.js'/>"></script>
<link href="<c:url value='/static/framework/plugins/bootstrap/4.3.1/css/glyphicons.css'/>" rel="stylesheet">
<link href="<c:url value='/static/framework/plugins/bootstrap/4.3.1/css/bootstrap_custom.css'/>" rel="stylesheet">
<!-- okayNav组件引用 -->
<link href="<c:url value='/static/framework/plugins/okayNav/css/okayNav-base.css'/>" rel="stylesheet">
<link href="<c:url value='/static/framework/plugins/okayNav/css/okayNav-theme.css'/>" rel="stylesheet">
<script src="<c:url value='/static/framework/plugins/okayNav/js/jquery.okayNav.js'/>"></script>
<!-- layui组件引用 -->
<link href="<c:url value='/webjars/layui/2.4.5/css/layui.css'/>" rel="stylesheet">
<%-- <script src="<c:url value='/webjars/layui/2.4.5/layui.all.js'/>"></script> --%>
<script src="<c:url value='/static/webjars/layui/2.4.5/layui.all.js'/>"></script>
<!-- blueimp-gallery组件引用 -->
<link href="<c:url value='/static/framework/plugins/gallery/2.33.0/css/blueimp-gallery.min.css'/>" rel="stylesheet">
<link href="<c:url value='/static/framework/plugins/gallery/2.33.0/css/blueimp-gallery-video.css'/>" rel="stylesheet">
<script src="<c:url value='/static/framework/plugins/gallery/2.33.0/js/blueimp-gallery.js'/>"></script>
<script src="<c:url value='/static/framework/plugins/gallery/2.33.0/js/blueimp-gallery-fullscreen.js'/>"></script>
<script src="<c:url value='/static/framework/plugins/gallery/2.33.0/js/blueimp-gallery-indicator.js'/>"></script>
<script src="<c:url value='/static/framework/plugins/gallery/2.33.0/js/jquery.blueimp-gallery.min.js'/>"></script>
<!-- 信息显示组件 -->
<link href="<c:url value='/static/framework/plugins/overhang/1.0.5/overhang.css'/>" rel="stylesheet" />
<script src="<c:url value='/static/framework/plugins/overhang/1.0.5/overhang.js'/>"></script>
<script src="<c:url value='/static/framework/plugins/overhang/messageUtil.js'/>"></script>
<!-- blueimp-gallery组件的引用 zIndex修改 99999⇒999999991 -->
<link href="<c:url value='/static/framework/plugins/gallery/2.33.0/css/blueimp-gallery.min.css'/>" rel="stylesheet">
<script src="<c:url value='/static/framework/plugins/gallery/2.33.0/js/blueimp-gallery.min.js'/>"></script>
<!-- sh-gallery组件引用 -->
<link href="<c:url value='/static/framework/plugins/sh-gallery/jquery-photo-gallery/photoGallery.css'/>"
    rel="stylesheet">
<script src="<c:url value='/static/framework/plugins/sh-gallery/EZView.js'/>"></script>
<!-- 自定义共通组件的引用 -->
<script src="<c:url value='/static/framework/js/common/common_utils.js'/>"></script>
<!-- fontawesome组件的引用 -->
<link href="<c:url value='/webjars/font-awesome/5.8.1/css/all.min.css'/>" rel="stylesheet">
<!-- font-platformweb组件的引用 -->
<link href="<c:url value='/static/framework/plugins/font-platformweb/font-platformweb.css'/>" rel="stylesheet">
<!-- font-shweb组件的引用 -->
<link href="<c:url value='/static/framework/plugins/font-shweb/font-shweb.css'/>" rel="stylesheet">
<link href="<c:url value='/static/framework/plugins/font-shweb/font-shweb-custom.css'/>" rel="stylesheet">
<!-- mCustomScrollbar组件的引用 -->
<link href="<c:url value='/static/framework/plugins/mCustomScrollbar/jquery.mCustomScrollbar.css'/>" rel="stylesheet">
<!-- contabs组件的引用 -->
<%-- <link href="<c:url value='/framework/plugins/contabs/portlet.css'/>" rel="stylesheet"> --%>
<link href="<c:url value='/static/framework/plugins/contabs/portlet-member.css'/>" rel="stylesheet">
<link href="<c:url value='/static/framework/plugins/contabs/contabs.css'/>" rel="stylesheet">
<link href="<c:url value='/static/framework/plugins/contabs/contabs-member.css'/>" rel="stylesheet">
<!-- 自定义组件的引用 -->
<link href="<c:url value='/static/platform/platform/css/ps99/ps9902.css'/>" rel="stylesheet">
<link id="ps99theme-type" href="" rel="stylesheet">
<script type="text/javascript">
    // krpano用
    function getSessionFileEditPath(path) {
        var sessionImagepath = PlatformConstants.APP_SERVER_TEMP_SESSION_FOLDER + "/";
        sessionImagepath = sessionImagepath + $('#hiddenSessionId').val() + "/file_w_app/";
        sessionImagepath = sessionImagepath + (path ? path : "");
        return sessionImagepath;
        // return '<c:url value="/' + sessionImagepath + '"/>';
    }
</script>
</head>
<body style="overflow: hidden">
    <input id="hiddenSessionId" type="hidden" value="<%=request.getSession().getId()%>">
    <input id="hiddenUserCurrrentLocal" type="hidden" value="${currrentLocal }">
    <input id="hiddenPs9902dispFlag" type="hidden" value="${dispFlag }">
    <!-- 预加载内容 -->
    <div class="pre-loader"></div>
    <!-- 主题切换 -->
    <div class="ps99theme-dropdown">
        <a class="ps99theme-dropdown-toggle" href="#" role="button">
            <i class="fa fa-columns ps99theme-open" aria-hidden="true"></i>
            <i class="fa fa-columns ps99theme-closed" aria-hidden="true"></i>
        </a>
        <ul class="ps99theme-dropdown-menu">
            <li class="ps99theme-black ps99theme-dropdown-menu-item"></li>
            <li class="ps99theme-red ps99theme-dropdown-menu-item"></li>
            <li class="ps99theme-blue ps99theme-dropdown-menu-item"></li>
            <li class="ps99theme-green ps99theme-dropdown-menu-item"></li>
        </ul>
    </div>
    <div class="header clearfix">
        <div class="header-right">
            <!-- 左上角图标 -->
            <div class="brand-logo platform-brand-logo">
                <a href="javascript:void(0);" class="position-relative pl-3">
                    <!--<img src="vendors/images/logo.png" alt="" class="mobile-logo"> -->
                    <%--<img src="<c:url value='/static/platform/platform/images/ps99/main_logo.png'/>" alt=""> --%>
                    <c:out value="全景展览管理系统"></c:out>
                </a>
            </div>
            <!-- 顶部菜单 -->
            <div class="navbar-header">
                <form id="ps9902Form" method="post" action="ps9902/doInit">
                    <header id="header" class=" okayNav-header">
                        <nav role="navigation" id="nav-main" class="okayNav platform-okayNav">
                            <ul>
                                <c:forEach var="menuTop" items="${ps9902Form.menuTopList}">
                                    <li>
                                        <a id="${menuTop.menuId}" href="javascript:void(0)"
                                            onclick="showMenuMiddle('${menuTop.menuId}',encodeURIComponent('<c:out value="${menuTop.webFont}" escapeXml="true" />' + '&nbsp;' + '${menuTop.menuName}'))">
                                            <c:out value="${menuTop.webFont}" escapeXml="false" />
                                            <c:out value="${menuTop.menuName}" />
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </header>
                    <!-- /header -->
                </form>
            </div>
            <!-- 右上角用户信息 -->
            <div class="user-info-dropdown">
                <div class="dropdown">
                    <a class="dropdown-toggle d-flex align-items-center" href="#" role="button" data-toggle="dropdown">
                        <!-- <span class="user-icon">
                            <i class="fa fa-user"></i>
                        </span> -->
                        <span class="user-name">
                            <security:authentication property='principal.userDisplayName' />
                        </span>
                        <i class="icon-platform-more ml-2" aria-hidden="true"></i>
                    </a>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right pt-2 pb-2 mt-1">
                        <!-- 密码 -->
                        <li class="dropdown-item user-info-password " onclick="$(this).find('a').click();">
                            <a class="J_menuItem" onclick="$(this).parent().click();"
                                href="<c:url value='/${CommonConstantsIF.URI_BASE_MEMBER}/ps99/ps990201'/>">
                                <c:out value="密码修改"></c:out>
                            </a>
                        </li>
                        <li class="dropdown-item" onclick="$(this).find('a').click();">
                            <a class="J_menuItem" onclick="$(this).parent().click();"
                                href="<c:url value='/${CommonConstantsIF.URI_BASE_MEMBER}/logout'/>">
                                <c:out value="退出"></c:out>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- 左侧菜单 -->
    <div class="left-side-bar platform-left-side-bar">
        <div class="first-level-menu">
            <a href="javascript:void(0);">菜单</a>
        </div>
        <div class="menu-block customscroll">
            <div class="sidebar-menu">
                <ul id="accordion-menu">
                </ul>
            </div>
        </div>
    </div>
    <!-- 定位导航栏 start -->
    <div class="main-container platform-main-container">
        <div class="  height-100-footer ">
            <div class="clearfix content-tabs">
                <button class="roll-nav roll-left J_tabLeft">
                    <i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:void(0);" class="active J_menuTab" data-id="index_v1.html"> 主页 </a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight">
                    <i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right dropdown">
                    <button class="dropdown-toggle J_tabClose" data-toggle="dropdown">选项卡操作</button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right  pt-2 pb-2 mt-1">
                        <li class="dropdown-item J_tabShowActive">
                            <a>定位当前选项卡</a>
                        </li>
                        <li class="dropdown-divider"></li>
                        <li class="dropdown-item J_tabCloseAll">
                            <a>关闭全部选项卡</a>
                        </li>
                        <li class="dropdown-item J_tabCloseOther">
                            <a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class=" clearfix J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%"
                    src="<c:url value='/${CommonConstantsIF.URI_BASE_MEMBER}/ps99/ps990202'/>" frameborder="0"
                    data-id="index_v1.html" seamless></iframe>
            </div>
        </div>
        <div class="footer">
            <div class="pull-right">
                &copy; 2018
                <a href="javascript:void(0);" target="_blank">天涯科技</a>
            </div>
        </div>
    </div>
    <!-- 定位导航栏 end -->
    <!-- The Gallery as lightbox dialog, should be a child element of the document body -->
    <div id="blueimp-gallery" class="blueimp-gallery">
        <div class="slides"></div>
        <!--         <h3 class="title"></h3> -->
        <a class="prev" style="color: #fff !important;">‹</a>
        <a class="next" style="color: #fff !important;">›</a>
        <a class="close" style="color: #fff !important;">×</a>
        <!--         <a class="play-pause"></a> -->
        <ol class="indicator"></ol>
    </div>
    <script src="<c:url value='/static/framework/plugins/mCustomScrollbar/jquery.mousewheel.min.js'/>"></script>
    <script src="<c:url value='/static/framework/plugins/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js'/>"></script>
    <script src="<c:url value='/static/framework/plugins/mCustomScrollbar/jquery.mCustomScrollbar.js'/>"></script>
    <script src="<c:url value='/static/framework/plugins/contabs/contabs.js'/>"></script>
    <script src="<c:url value='/static/framework/plugins/contabs/portlet.js'/>"></script>
    <script src="<c:url value='/static/platform/common/js/common.js'/>"></script>
    <script src="<c:url value='/static/platform/platform/js/ps99/ps9902.js'/>"></script>
</body>
</html>
