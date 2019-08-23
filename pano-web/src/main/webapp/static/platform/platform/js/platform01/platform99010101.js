//功能一览
$(function() {
    $(function() {
        var Build = document.getElementById('build1');
        var speed = -2;

        Build.innerHTML += Build.innerHTML;

        function move() {
            if (Build.offsetLeft < -(Build.offsetWidth / 2)) {
                Build.style.left = 0;
            }

            if (Build.offsetLeft > 0) {
                Build.style.left = -(Build.offsetWidth / 2) + 'px';
            }

            Build.style.left = Build.offsetLeft + speed + 'px';
        }
        setInterval(move, 30);
    });

    $(function() {
        var Build = document.getElementById('build2');
        var speed = -1;

        Build.innerHTML += Build.innerHTML;

        function move() {
            if (Build.offsetLeft < -(Build.offsetWidth / 2)) {
                Build.style.left = 0;
            }

            if (Build.offsetLeft > 0) {
                Build.style.left = -(Build.offsetWidth / 2) + 'px';
            }

            Build.style.left = Build.offsetLeft + speed + 'px';
        }
        setInterval(move, 30);
    });

    $(function() {
        var Build = document.getElementById('build3');
        var speed = -3;

        Build.innerHTML += Build.innerHTML;

        function move() {
            if (Build.offsetLeft < -(Build.offsetWidth / 2)) {
                Build.style.left = 0;
            }

            if (Build.offsetLeft > 0) {
                Build.style.left = -(Build.offsetWidth / 2) + 'px';
            }

            Build.style.left = Build.offsetLeft + speed + 'px';
        }
        setInterval(move, 30);
    });

    // 初期化加载底部动画
    // footerCanvas();

    // resize加载底部动画
    // $(window).on('resize', function() {
    // footerCanvas();
    // });

});

// 门户页面迁移
function gotoPortalPage(dispFlag) {
    if (window.opener && !window.opener.closed) {
        gotoParentLoginAndClose(window);
        return;
    }
    document.createElement('form');
    var form = document.createElement('form');
    form.action = getMemberContextPath('platform99010101/doRredirect');

    // 创建一个隐藏项目
    var inputDispFlag = document.createElement("input");
    // 菜单显示顺
    inputDispFlag.type = "hidden";
    inputDispFlag.name = "dispFlag";
    inputDispFlag.value = dispFlag;
    // 将该输入框插入到 form 中
    form.appendChild(inputDispFlag);

    form.method = "POST";

    var body = document.getElementsByTagName("body").item(0);
    body.appendChild(form);
    form.submit();
}

// 底部动画
// function footerCanvas() {
// var c = document.getElementById('view-footer');
// var ctx = c.getContext('2d');
// var w = c.width = window.innerWidth;
// var h = c.height = window.innerHeight;
// var cx = w / 2;
// var cy = h / 2;
// var Box = function(x, y, vx, color) {
// this.color = color;
// this.vx = vx;
// this.x = x;
// this.y = y;
// this.w = 10 + Math.random() * 50;
// this.h = 5 + Math.random() * 300;
// };
// Box.prototype = {
// constructor : Box,
// update : function() {
// this.x += this.vx;
// if (this.x < -this.w / 2) {
// this.x = w + this.w / 2;
// }
// },
// render : function(ctx) {
// ctx.save();
// ctx.fillStyle = this.color;
// ctx.translate(this.x, this.y);
// ctx.fillRect(-this.w / 2, -this.h, this.w, this.h);
// ctx.restore();
// }
// };
//
// var ctr = 50;
// var boxes = [];
// var boxes2 = [];
// var boxes3 = [];
// var box;
// var speed = -1;
//
// for (var i = 0; i < ctr; i++) {
// box = new Box(Math.random() * w, h, speed * 0.5, '#e5e5e5');
// boxes.push(box);
// }
// for (var i = 0; i < ctr; i++) {
// box = new Box(Math.random() * w, h, speed * 0.8, '#cccccc');
// boxes2.push(box);
// }
// for (var i = 0; i < ctr; i++) {
// box = new Box(Math.random() * w, h, speed, '#adadad');
// boxes3.push(box);
// }
//
// requestAnimationFrame(function loop() {
// requestAnimationFrame(loop);
// ctx.clearRect(0, 0, w, h);
// ctx.globalAlpha = 0.9;
// for (var i = 0, len = boxes.length; i < len; i++) {
// box = boxes[i];
// box.updateByPrimaryKey();
// box.render(ctx);
// }
// for (var i = 0, len = boxes2.length; i < len; i++) {
// box = boxes2[i];
// box.updateByPrimaryKey();
// box.render(ctx);
// }
// for (var i = 0, len = boxes3.length; i < len; i++) {
// box = boxes3[i];
// box.updateByPrimaryKey();
// box.render(ctx);
// }
// });
// }
