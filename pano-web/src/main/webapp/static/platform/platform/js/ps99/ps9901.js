///
// 管理员门户页面
//

// 遮盖曾的ID
var currentMaskIndex = null;

// layui中layer的别名
var layer;
var laydate;

layui.use([ 'layer', 'laydate' ], function() { // 独立版的layer无需执行这一句
    layer = layui.layer; // 独立版的layer无需执行这一句
    laydate = layui.laydate;
    
 // okayNav实例化
//    var navigation = $('#nav-main').okayNav({
//        parent : '', // will call nav's parent() by default
//        toggle_icon_class : 'okayNav__menu-toggle',
//        toggle_icon_content : '<span /><span /><span />',
//        align_right : true, // If false, the menu and the kebab icon will be on
//        // the left
//        swipe_enabled : true, // If true, you'll be able to swipe left/right
//        // to open the navigation
//        threshold : 20, // Nav will auto open/close if swiped >= this many
//        // percent
//        beforeOpen : function() {
//        }, // Will trigger before the nav gets opened
//        afterOpen : function() {
//        }, // Will trigger after the nav gets opened
//        beforeClose : function() {
//        }, // Will trigger before the nav gets closed
//        afterClose : function() {
//        }, // Will trigger after the nav gets closed
//        itemHidden : function() {
//        }, // Will trigger after an item moves to the hidden navigation
//        itemDisplayed : function() {
//        } // Will trigger after an item moves to the visible navigation
//    });
//
//    $('.okayNav ul > li > a').click(function() {
//        $('.okayNav ul > li > a').removeClass('selected');
//        $(this).addClass('selected');
//    });
//
//    $("#nav-main li:eq(0)").find("a").click();

    // 显示纵向菜单处理
    showMenuMiddle();

});

// 查看会话范围的图片
function doWebappGallery(attachFilePath) {
    var attachFilePaths = JSON.parse(decodeURIComponent(attachFilePath))
    jQuery.each(attachFilePaths, function(i, item) {
        attachFilePaths[i] = getContextPath(item);
    });
    $(this).EZView(attachFilePaths);
//    var options = {
//        // Close the gallery when clicking on an empty slide area:
//        closeOnSlideClick : false
//    }
//    var gallery = blueimp.Gallery(attachFilePaths, options);
    // var gallery = blueimp.Gallery([
    // 'https://www.jssor.com/demos/img/gallery/980x380/011.jpg',
    // 'https://www.jssor.com/demos/img/gallery/980x380/012.jpg',
    // 'https://www.jssor.com/demos/img/gallery/980x380/013.jpg' ]);
}

// 登陆处理
function gotoLoginPage() {
    if (window.opener && !window.opener.closed) {
        gotoParentLoginAndClose(window);
        return;
    }
    document.createElement('form');
    var form = document.createElement('form');
    form.action = getAdminContextPath("");
    form.method = "POST";

    var body = document.getElementsByTagName("body").item(0);
    body.appendChild(form);
    form.submit();
}

// 显示纵向菜单处理
function showMenuMiddle(menuTopId, menuTopName) {

    var params = {
        selectTopMenuId : menuTopId,
        selectTopMenuName : menuTopName
    };
    // 确认操作
    $.ajax({
        url : getAdminContextPath('ps99/ps9901/doSearch'),
        type : "post",
        dataType : "json",
        data : params,
        success : function(result) {
            if (CommonUtilJs.processAjaxSuccessAfter(result)) {

                $('div .first-level-menu').html(
                    '<a id="' + result.obj.selectTopMenuId + '" href="#">' + decodeURIComponent(result.obj.selectTopMenuName)
                        + '</a>');
                $("#accordion-menu li:eq(0),#accordion-menu li:gt(0)").remove();
                for ( var m = 0; m < result.obj.menuMiddleList.length; m++) {
                    var ps990102Dto = result.obj.menuMiddleList[m]
                    if (ps990102Dto.url == null || ps990102Dto.url.length == 0) {
                        var menuLi = '<li class="dropdown">';
                        menuLi = menuLi + '<a id="' + ps990102Dto.menuId
                            + '" href="javascript:;" class="dropdown-toggle">';
                        if (ps990102Dto.webFont == null || ps990102Dto.webFont.length == 0) {
                            menuLi = menuLi + '<span class="mtext">' + ps990102Dto.menuName
                                + '</span>';
                        } else {
                            menuLi = menuLi + ps990102Dto.webFont + '<span class="mtext">'
                                + ps990102Dto.menuName + '</span>';
                        }
                        menuLi = menuLi + '</a>';
                        menuLi = menuLi + '<ul class="submenu">';

                        for ( var n = 0; n < ps990102Dto.menuLinkList.length; n++) {
                            var menuLink = ps990102Dto.menuLinkList[n]
                            if (menuLink.url == null || menuLink.url.length == 0) {
                                    menuLi = menuLi + '<li class="dropdown">';
                                    menuLi = menuLi + '<a id="' + menuLink.menuId
                                        + '" href="javascript:;" class="dropdown-toggle">';
                                    if (menuLink.webFont == null
                                        || menuLink.webFont.length == 0) {
                                        menuLi = menuLi + '<span class="mtext">'
                                            + menuLink.menuName + '</span>';
                                    } else {
                                        menuLi = menuLi + menuLink.webFont
                                            + '<span class="mtext">' + menuLink.menuName
                                            + '</span>';
                                    }
                                    menuLi = menuLi + '</a>';
                                    menuLi = menuLi + '<ul class="submenu child">';

                                    for ( var i = 0; i < menuLink.menuLinkList.length; i++) {
                                        var menuLinkSecond = menuLink.menuLinkList[i];
                                        menuLi = menuLi + '<li>';
                                        menuLi = menuLi + '<a id="' + menuLinkSecond.menuId
                                            + '" class="J_menuItem" href="';
                                        menuLi = menuLi + getContextPath(menuLinkSecond.url);
                                        menuLi = menuLi + '" data-index="' + (m + 1) + n + i + '">';
                                        menuLi = menuLi + menuLinkSecond.menuName;
                                        menuLi = menuLi + '</a>';
                                        menuLi = menuLi + '</li>';
                                    }
                                    menuLi = menuLi + '</ul>';
                                } else {
                                    menuLi = menuLi + '<li>';
                                    menuLi = menuLi + '<a id="' + menuLink.menuId
                                        + '" class="J_menuItem" href="';
                                    menuLi = menuLi + getContextPath(menuLink.url);
                                    menuLi = menuLi + '" data-index="' + (m + 1) + n + '">';
                                    menuLi = menuLi + menuLink.menuName;
                                    menuLi = menuLi + '</a>';
                                    menuLi = menuLi + '</li>';
                                }
                        }

                        menuLi = menuLi + '</ul>';

                        $('#accordion-menu').append(menuLi);
                    } else {
                        var menuLi = '<li>';
                        menuLi = menuLi + '<a class="J_menuItem dropdown-toggle no-arrow" href="';
                        menuLi = menuLi + getContextPath(ps990102Dto.url);
                        menuLi = menuLi + '" data-index="' + (m + 1) + '">';
                        if (ps990102Dto.webFont == null || ps990102Dto.webFont.length == 0) {
                            menuLi = menuLi + '<span class="mtext">' + ps990102Dto.menuName
                                + '</span>';
                        } else {
                            menuLi = menuLi + ps990102Dto.webFont + '<span class="mtext">'
                                + ps990102Dto.menuName + '</span>';
                        }
                        menuLi = menuLi + '</a>';
                        menuLi = menuLi + '</li>';

                        $('#accordion-menu').append(menuLi);
                    }
                }

                $('.J_menuItem').on('click', menuItem);

                $("#accordion-menu").vmenuModule({
                    Speed : 400,
                    autostart : false,
                    autohide : true
                });

            }
        }
    // ,
    // error : function(result) {
    // window.top.layuiRemoveLoading();
    // window.top.layer.alert(result.obj.status);
    // }
    });
}