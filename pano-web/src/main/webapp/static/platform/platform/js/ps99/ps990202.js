//
// 会员门户首页
//

$(function() {
    // 时间信息
    setInterval(function() {
        var date = new Date();
        var now = "";
        now = date.getFullYear() + "-";
        var month = date.getMonth() + 1 + "";
        if (month.length == 1) {
            month = "0" + month;
        }
        now = now + month + "-";
        var days = date.getDate() + "";
        if (days.length == 1) {
            days = "0" + days + "";
        }
        now = now + days;
        var hours = date.getHours() + "";
        if (hours.length == 1) {
            hours = "0" + hours;
        }
        now = now + "&nbsp;" + hours + ":";
        var minutes = date.getMinutes() + "";
        if (minutes.length == 1) {
            minutes = "0" + minutes;
        }
        now = now + minutes + ":";
        var seconds = date.getSeconds() + "";
        if (seconds.length == 1) {
            seconds = "0" + seconds;
        }
        now = now + seconds + "";
        $("#timeNow").html(now);
    }, 1000);
});
