function scoreFun(object, opts) {
    var defaults = {
        fen_d: 16,
        ScoreGrade: 10,
        types: ["很不满意", "差得太离谱，与房东描述的严重不符，非常不满", "不满意", "与房东描述的不符，不满意", "一般", "房源一般", "没有房东描述的那么好", "满意", "体验不错，与房东描述的基本一致，还是挺满意的", "非常满意", "很差", "差", "一般", "好", "很好"],
        nameScore: "fenshu",
        parent: "star_score",
        attitude: "attitude"
    };
    options = $.extend({},
    defaults, opts);
    var countScore = object.find("." + options.nameScore);
    var startParent = object.find("." + options.parent);
    var atti = object.find("." + options.attitude);
    var now_cli;
    var fen_cli;
    var atu;
    var fen_d = options.fen_d;
    var len = options.ScoreGrade;
    startParent.width(fen_d * len);
    var preA = (5 / len);
    for (var i = 0; i < len; i++) {
        var newSpan = $("<a href='javascript:void(0)'></a>");
        newSpan.css({
            "left": 0,
            "width": fen_d * (i + 1),
            "z-index": len - i
        });
        newSpan.appendTo(startParent)
    }
    startParent.find("a").each(function(index, element) {
        $(this).click(function() {
            now_cli = index;
            show(index, $(this))
        });
        $(this).mouseenter(function() {
            show(index, $(this))
        });
        $(this).mouseleave(function() {
            if (now_cli >= 0) {
                var scor = preA * (parseInt(now_cli) + 1);
                startParent.find("a").removeClass("clibg");
                startParent.find("a").eq(now_cli).addClass("clibg");
                var ww = fen_d * (parseInt(now_cli) + 1);
                startParent.find("a").eq(now_cli).css({
                    "width": ww,
                    "left": "0"
                });
                if (countScore) {
                    countScore.text(scor)
                }
            } else {
                startParent.find("a").removeClass("clibg");
                if (countScore) {
                    countScore.text("")
                }
            }
        })
    });
    function show(num, obj) {
        var n = parseInt(num) + 1;
        var lefta = num * fen_d;
        var ww = fen_d * n;
        var scor = preA * n; (len > 5) ? atu = options.types[parseInt(num)] : atu = options.types[parseInt(num) + 10];
        object.find("a").removeClass("clibg");
        obj.addClass("clibg");
        obj.css({
            "width": ww,
            "left": "0"
        });
        countScore.text(scor);
        atti.text(atu);
    };

    // 评分编辑初期化加载
    startParent.find("a:eq("+options.currentIndex+")").click();
};