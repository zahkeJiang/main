//显示中奖结果 这个函数需要自定义，默认没有
function lotteryStop(index) {
    //index就是lottery.num
    switch (index) {
        case 0:
            $(".coupon_priceBox_span2").html(150);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 1:
            $(".coupon_priceBox_span2").html(200);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 2:
            $(".coupon_priceBox_span2").html(150);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 3:
            $(".coupon_priceBox_span2").html(300);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 4:
            $(".coupon_priceBox_span2").html(150);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 5:
            $(".coupon_priceBox_span2").html(1000);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 6:
            $(".coupon_priceBox_span2").html(150);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        case 7:
            $(".coupon_priceBox_span2").html(400);
            change_lottery_begin(); //抽奖按钮改变样式，不能再点击
            $(".layer").fadeIn("slow");
            $(".coupon").show();
            break;
        default:
            alert("当前抽奖人数过多，请稍后尝试！");
    }
}
var lottery = {
    index: 7, //当前转动到哪个位置，起点位置
    count: 8, //总共有多少个位置
    timer: 0, //setTimeout的ID，用clearTimeout清除
    speed: 20, //初始转动速度
    times: 0, //转动次数
    cycle: 24, //转动基本次数：即至少需要转动多少次再进入抽奖环节
    prize: 0, //中奖位置
    num: 0, //最后停下的位置
    // 当一个函数被保存为对象的一个属性时，我们称之它为该对象的一个方法，那么this被绑定到该对象上
    roll: function() {
        var index = this.index;
        var count = this.count;
        $("#lottery").find(".lottery-unit-" + index).removeClass("active");
        index += 1;
        if (index > count - 1) {
            index = 0;
        };
        $("#lottery").find(".lottery-unit-" + index).addClass("active");
        this.index = index;
    }
};
//开始抽奖动画
function roll(num) {
    lottery.times += 1;
    lottery.roll();
    if (lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
        clearTimeout(lottery.timer);
        try {
            if (typeof(eval(lotteryStop)) == "function") {
                setTimeout("lotteryStop(lottery.index)", 100);
            }
        } catch (e) {}
        lottery.prize = 0;
        lottery.times = 0;
        lottery.speed = 20;

    } else {
        if (lottery.times == lottery.cycle) {
            lottery.speed -= 10;
        } else if (lottery.times < lottery.cycle) {
            var index = lottery.num;
            lottery.prize = index;
        } else {
            if (lottery.times > lottery.cycle + 10) {
                lottery.speed += 100;
            } else {
                lottery.speed += 20;
            }
        }
        if (lottery.speed < 40) {
            lottery.speed = 40;
        };
        lottery.timer = setTimeout(roll, lottery.speed);
    }
}
$(function() {
    $.post("queryCoupon.action", {}, function(obj) {
        if (obj.status == "-10") { //-10未关注公众号
            window.location.href = "openWchat.html";
        } else if (obj.status == "-40") { //没有优惠券
            //点击抽奖按钮，开始抽奖
            $(".lottery_begin").click(function() {
                $.post("coupon.action", {}, function(obj) {
                    if (obj.status == "-30") { //需要激活码
                        $(".layer").fadeIn("slow");
                        $(".coupon_code").show();
                    } else if (obj.status == "0") { //正常抽取
                        if (obj.price == 0) {
                            lottery.num = 0;
                        } else if (obj.price == 1) {
                            lottery.num = 1;
                        } else if (obj.price == 2) {
                            lottery.num = 2;
                        } else if (obj.price == 3) {
                            lottery.num = 3;
                        } else if (obj.price == 4) {
                            lottery.num = 4;
                        } else if (obj.price == 5) {
                            lottery.num = 5;
                        } else if (obj.price == 6) {
                            lottery.num = 6;
                        } else if (obj.price == 7) {
                            lottery.num = 7;
                        }
                        $(this).prop("disabled", true); //抽奖按钮设为不可点击状态
                        roll(); //开始执行动画
                    } else {
                        $(this).prop("disabled", true); //抽奖按钮设为不可用状态
                        // $(this).unbind("click");
                    }
                }, 'json');
            });
        } else {
            change_lottery_begin(); //改变抽奖按钮，并显示优惠信息
            // $(".footer").html("<p>您已抽过奖了，<a href='index.html'>立即使用</a></p>");
        }

    }, 'json');

    //关闭弹窗
    $("#closeBtn").click(function() {
        $(".coupon").fadeOut("fast");
        $(".layer").fadeOut("fast");
        // $(".footer").html("<p>您已抽过奖了，<a href='index.html'>立即使用</a></p>");
    });
    //关闭弹窗
    $("#closeBtn_code").click(function() {
        $(".coupon_code").fadeOut("fast");
        $(".layer").fadeOut("fast");

    });
    //抽奖码输入后，点击验证
    $(".btn_submit").click(function() {
        var ds_coupon_code = $("input[name='ds_coupon_code']").val();
        $.post("actcode.action", {
            "code": ds_coupon_code
        }, function(obj) {
            if (obj.status == "1") {
                alert("领取成功");
                $(".coupon_code").fadeOut("fast");
                $(".layer").fadeOut("fast");

            } else {
                alert("抽奖码错误，请重新输入");
            }
        }, "json");

    });


});

//开始抽奖按钮改变样式
function change_lottery_begin() {
    $(".lottery_begin p").empty();
    $(".lottery_begin p").html("已&nbsp;抽&nbsp;奖");
    $(".lottery_begin p").css({
        "color": "white"
    });
    $(".lottery_begin").css({
        "font-size": "18px",
        "background-color": "#999"
    });
    $(".lottery_begin").prop("disabled", true);
}