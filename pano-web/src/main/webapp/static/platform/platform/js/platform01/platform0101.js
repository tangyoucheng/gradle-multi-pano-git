//首页

$(function() {

    // 取得top的URL
    var topHref = '';
    try {
        topHref = top.location.href;
    } catch (ex) {
        // 异常的场合
    }

    // 当前URL不是top的URL的场合
    if (topHref !== location.href) {
        top.location.href = location.href;
        return;
    }

    // 调用图片轮播
    changeImg();

});

function changeImg() {
    var $imgs = $(".banner_ul li");
    var isStop = false;
    var index = 0;
    var length = $imgs.length;
    $imgs.eq(index).show();
    // 设置循环播放
    setInterval(function() {
        if (isStop)
            return;
        if (index >= length)
            index = -1;
        index++;
        $imgs.eq(index).fadeIn();
        $imgs.eq(index).siblings().fadeOut();

    }, 5000);
}

function checkSearch(){
    var loginId = $('#loginId').val();
    var targetUrl = 'platforma0401/';
    targetUrl = targetUrl + '?houseKeyword=' + $("#searchCity").val();
    location.href = getTargetUrl(targetUrl, loginId);
}
