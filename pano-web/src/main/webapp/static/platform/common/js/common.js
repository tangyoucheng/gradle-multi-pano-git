//首页

// 遮盖层的ID
var currentMaskIndex = null;
// 菜单标签页遮盖层
var menuTabMaskIndex = null;
// krpano加载遮盖层
var krpanoMaskIndex = null;
// layui中layer的别名
var layer;

layui.use([ 'layer', 'laydate' ], function() { // 独立版的layer无需执行这一句
    layer = layui.layer; // 独立版的layer无需执行这一句
    laydate = layui.laydate;
    // ajax全局设定
    $.ajaxSetup({
        beforeSend : function(jqXHR) {
            layuiLoading();
        },
        complete : function(jqXHR, textStatus) {
            layuiRemoveLoading();
        },
        error : function(jqXHR, textStatus, errorThrown) {
            ajaxCommonError(jqXHR.status);
        }
    });

    // 共通 高级查询
    $(".btn-search").click(function() {
        $('.full-search').slideToggle();
        $('.fa-angle-double-up').slideToggle();
        $('.fa-angle-double-down').slideToggle();
    });

    $(".checkbox-button .btn").click(function() {
        $(this).toggleClass("active");
    });
    $(".radio-button .btn").click(function() {
        $(this).addClass("active").siblings().removeClass("active");
    });
    $(".radio-button-clean .btn").click(function() {
        $(this).toggleClass("active");
        $(this).siblings().removeClass("active");
    });
//    $(".radio-button.radio-button-clean .btn.active").click(function() {
//        $(this).removeClass("active");
//    });
    // 同时绑定多个 layui 日期控件
    lay('.date-item').each(function() {
        laydate.render({
            elem : this,
            trigger : 'click'
        });
    });
    // 日期控件
    lay('.laydate-item').each(function() {
        laydate.render({
            elem : this,
            trigger : 'click'
        });
    });

    // 日期时间选择器
    laydate.render({
        elem : '.laydate-datetime',
        type : 'datetime'
    });

    // 日期只读
    laydate.render({
        elem : '.laydate-readonly',
        trigger : 'click'
    });
});

//记载krpano遮盖
function krpanoMaskLoading() {

 // 已经存在打开的遮盖层的场合，不再新开遮盖层
 if (krpanoMaskIndex != null) {
     return;
     // layer.close(currentMaskIndex);
     // currentMaskIndex = null;
 }
 krpanoMaskIndex = commonMaskLoading('menuTabMask');
}

//移除krpano遮盖
function removekrpanoMask() {
 if (layer != undefined && krpanoMaskIndex != null) {
     layer.close(krpanoMaskIndex);
     krpanoMaskIndex = null;
 }
 // 调用close方法,关闭全局变量index对应的加载效果
}

// 记载标签页遮盖
function menuTabMaskLoading() {

    // 已经存在打开的遮盖层的场合，不再新开遮盖层
    if (menuTabMaskIndex != null) {
        return;
        // layer.close(currentMaskIndex);
        // currentMaskIndex = null;
    }
    menuTabMaskIndex = commonMaskLoading('menuTabMask');
}

// 移除标签页遮盖
function removeMenuTabMask() {
    if (layer != undefined && menuTabMaskIndex != null) {
        layer.close(menuTabMaskIndex);
        menuTabMaskIndex = null;
    }
    // 调用close方法,关闭全局变量index对应的加载效果
}

// 记载业务遮盖
function layuiLoading() {

    // 已经存在打开的遮盖层的场合，不再新开遮盖层
    if (currentMaskIndex != null) {
        return;
        // layer.close(currentMaskIndex);
        // currentMaskIndex = null;
    }
    currentMaskIndex = commonMaskLoading('');
}

// 移除业务遮盖
function layuiRemoveLoading() {
    if (layer != undefined && currentMaskIndex != null) {
        layer.close(currentMaskIndex);
        currentMaskIndex = null;
    }
    // 调用close方法,关闭全局变量index对应的加载效果
}

// 共通遮盖层
function commonMaskLoading(maskId) {

    var loadingContent = '';
    loadingContent = loadingContent
            + '<div style="padding: 30px; line-height: 24px; background-color: #f0f0f0; color: #000; font-weight: 300;display: flex;align-items: center;">';
    loadingContent = loadingContent + '<div><img src="'
            + getContextPath('webjars/layui/2.4.5/css/modules/layer/default/loading-0.gif') + '"></div>';
    loadingContent = loadingContent + '<div>服务器端处理中，<br>禁止刷新本页面！</div>';
    loadingContent = loadingContent + '</div>';

    // 打开新的遮盖层
    var returnMaskIndex = layer.open({
        type : 1,
        title : false,// 不显示标题栏
        closeBtn : false,
        area : '300px;',
        shade : 0.1,// 遮罩透明度
        id : 'LAY_layuipro' + maskId, // 设定一个id，防止重复弹出
        moveType : 1, // 拖拽模式，0或者1
        content : loadingContent
    });
    return returnMaskIndex;
}

// ajax异常共通处理
function ajaxCommonError(status) {
    window.top.layuiRemoveLoading();

    switch (status) {
    case (500):
        window.top.layer.alert("服务器系统内部错误。", {
            icon : 6
        });
        break;
    case (200):
        // ajax请求，框架返回登录页面的场合
        window.top.layer.alert("您的账号已在其它地方登录，<br>若不是本人操作，请重新登录后修改密码。", {
            icon : 6
        }, function(index) {
            // 转到登录页面
            gotoLoginPage();
        });
        break;
    case (401):
        window.top.layer.alert("会话已结束,请重新登录。", {
            icon : 6
        }, function(index) {
            // 转到登录页面
            gotoLoginPage();
        });
        break;
    case (403):
        window.top.layer.alert("无权限执行此操作。", {
            icon : 6
        });
        break;
    case (408):
        window.top.layer.alert("请求超时。", {
            icon : 6
        });
        break;
    default:
        window.top.layer.alert("未知错误，错误代码：" + status + "。", {
            icon : 6
        });
    }
}

// 登陆处理
function gotoLoginPage() {
    if (window.opener && !window.opener.closed) {
        gotoParentLoginAndClose(window);
        return;
    }
    document.createElement('form');
    var form = document.createElement('form');
    var topHref = top.location.href;
    form.action = getContextPath("");
    if (topHref.indexOf(PlatformConstants.URI_BASE_ADMIN) != -1) {
        form.action = getAdminContextPath("/");
    }
    form.method = "GET";

    var body = document.getElementsByTagName("body").item(0);
    body.appendChild(form);
    form.submit();
}
function gotoParentLoginAndClose(w, wins) {
    var ws = wins || [];
    var opener;
    try {
        opener = w.opener;
    } catch (e) {
    }
    if (opener && !opener.closed) {
        ws.push(w);
        gotoParentLoginAndClose(opener, ws);
    } else {
        w.location.href = getContextPath("/login")
        w._im_pop_close = function(wins) {
            this._im_pop_wins = wins;
            var n = this._im_pop_wins.length
            for (var i = 1; i < n; i++) {
                this._im_pop_wins[i].close();
            }
            if (n > 0) {
                this._im_pop_wins[0].close();
            }
            this._im_pop_wins = null;
            this._im_pop_close = null;
        }
        w._im_pop_close.call(w, ws);
    }
}
