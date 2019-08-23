<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="loginId" type="java.lang.String" required="false"%>
<%@ attribute name="userDisplayName" type="java.lang.String" required="false"%>
<%@ attribute name="targetUrl" type="java.lang.String" required="false"%>

<script type="text/javascript">
    // 定数设定
    var PanoHeaderConstants = {};
    // 访问地址
    var jsTargetUrl = '<c:out value="${targetUrl}"></c:out>';
    if (jsTargetUrl == null || jsTargetUrl.length == 0) {
        // 登陆链接地址
        PanoHeaderConstants.loginTargetUrl = '<c:out value="platform01010202/"></c:out>';
        // 注册链接地址
        PanoHeaderConstants.registerTargetUrl = '<c:out value="platform01010201/"></c:out>';
    } else {
        // 登陆链接地址
        PanoHeaderConstants.loginTargetUrl = '<c:out value="platform01010202/"></c:out>' + "?targetUrl=" + jsTargetUrl;
        // 注册链接地址
        PanoHeaderConstants.registerTargetUrl = '<c:out value="platform01010201/"></c:out>' + "?targetUrl=" + jsTargetUrl;
    }
</script>
<style type="text/css">
.sh-btn-free {
    color: #fff;
    background-color: #1abc9c;
    border-color: #1abc9c;
}

.sh-btn-free:hover {
    color: #fff;
    background-color: #1aaa88;
    border-color: #1aaa88;
}

.sh-btn-free:focus, .sh-btn-free.focus {
    box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.5);
}

.sh-btn-free.disabled, .sh-btn-free:disabled {
    color: #fff;
    background-color: #1abc9c;
    border-color: #1abc9c;
}

.sh-btn-free:not (:disabled ):not (.disabled ):active, .sh-btn-free:not
    (:disabled ):not (.disabled ).active, .show>.sh-btn-free.dropdown-toggle
    {
    color: #fff;
    background-color: #1aaa88;
    border-color: #1aaa88;
}

.sh-btn-free:not (:disabled ):not (.disabled ):active:focus,
    .sh-btn-free:not (:disabled ):not (.disabled ).active:focus, .show>.sh-btn-free.dropdown-toggle:focus
    {
    box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.5);
}

.btn {
    line-height: 1 !important;
    display: inline-block;
    font-weight: 400;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    border: 1px solid transparent;
    border-top-color: transparent;
    border-right-color: transparent;
    border-bottom-color: transparent;
    border-left-color: transparent;
    padding: .375rem .75rem;
    font-size: 1rem;
    line-height: 1.5;
    border-radius: .25rem;
    transition: color .15s ease-in-out, background-color .15s ease-in-out,
        border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}
</style>
<script type="text/javascript">
    $(function() {
        //登录框事件
        $(".login-dialog").click(function() {
            var targetUrl = getMemberContextPath(PanoHeaderConstants.loginTargetUrl);
            window.top.layer.open({
                title : false, // 不显示标题栏
                type : 2,
                closeBtn : 0, // 不显示关闭按钮
                area : [ '638px', '293px' ], // 宽高
                content : [ targetUrl, 'yes' ],
                end : function() {
                }
            });
        });
        //注册框事件
        $(".show-register-box").click(function() {
            var targetUrl = getMemberContextPath(PanoHeaderConstants.registerTargetUrl);
            window.top.layer.open({
                title : false, // 不显示标题栏
                type : 2,
                closeBtn : 0, // 不显示关闭按钮
                area : [ '638px', '468px' ], // 宽高
                content : [ targetUrl, 'yes' ],
                end : function() {
                }
            });
        });

        //发布房源
        $("#sh0101Btn_public").click(function() {

            // 未登录时弹出登录框
            if ($('#hiddenHeaderLoginId').val() == null || $('#hiddenHeaderLoginId').val() == '') {
                $(".login-dialog").click();
                return false;
            }

            var targetUrl = 'sha01010101/';
            window.top.layer.open({
                title : '新建房源',
                type : 2,
                closeBtn : 1, // 显示关闭按钮
                area : [ '90%', '100%' ], // 宽高
                content : [ getMemberContextPath(targetUrl), 'yes' ],
                end : function() {
                }
            });
            return false;
        });
    });

    function getTargetUrl(targetUrl, loginId) {
        if (loginId) {
            return getMemberContextPath(targetUrl);
        } else {
            return getContextPath(targetUrl);
        }
    }
</script>


<ul class="nav nav_R nav_commen">
    <input type="hidden" id="hiddenHeaderLoginId" value="${loginId }">
    <c:if test="${empty loginId}">
        <li>
            <a class="show-register-box" href="javascript:void(0);">注册</a>
        </li>
        <li>&nbsp;</li>
        <li>
            <a class="login-dialog" href="javascript:void(0);">登录</a>
        </li>
    </c:if>
    <c:if test="${not empty loginId}">
        <li class="current">
            <c:if test="${not empty userDisplayName}">
                <a class="openTree" href="javascript:void(0);" rel="nofollow">${userDisplayName}</a>
            </c:if>
            <c:if test="${empty userDisplayName}">
                <a class="openTree" href="javascript:void(0);" rel="nofollow">${loginId}</a>
            </c:if>
            <div class="head_pop top40">
                <div class="pop_col">
                    <a href="<c:url value='/member/ps99/ps9902'/>" class="pop_T">个人中心</a>
                    <span> </span>
                </div>
                <div class="pop_col pop_bor">
                    <span> </span>
                    <span class="textCt">
                        <a href="<c:url value='/member/logout'/>">退出</a>
                    </span>
                </div>
            </div>
        </li>
    </c:if>
    <li>
        <button id="sh0101Btn_public" class="btn sh-btn-free">免费发布房源</button>
    </li>
</ul>