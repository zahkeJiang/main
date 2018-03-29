var footerHtml = "<div class='footerHtml'>没有更多了</div>"
$(function() {
    unused();
    //获取可用优惠券
    $(".header div").click(function() {
        $(this).css({
            "border-bottom": "2px solid orange",
            "color": "orange"
        });
        $(this).siblings("div").css({
            "border-bottom": "2px solid #f6f1f1",
            "color": "black"
        });
    })
    $(".header div.unused").click(function() {
        unused();
    });
    //获取已用优惠券
    $(".header div.used").click(function() {
        used();
    });
    //获取已过期优惠券
    $(".header div.overdue").click(function() {
        overdue();
    });


});

//获取可用优惠券
function unused() {
    $.post("getCoupons", {}, function(datas) {
        $(".container").html("");
        if (datas.status == 0) { //未过期，正常
            var couponList = datas.data.coupons;
            if (couponList && couponList.length > 0) {
                var coupon = "";
                $.each(couponList, function(commentIndex, comment) {
                    coupon += "<div class='couponBox' type='" + comment.type + "'><img src='" + comment.background + "'><div class='priceBox'><span class='price1'>¥</span><span class='price2'>" + comment.price + "</span></div><p class='date'>" + comment.date + "，即日生效</p><div class='layer'></div><div class='layerBox'><div class='detail layerBoxChildren'>详情</div><div class='toUse layerBoxChildren'>去使用</div></div></div>"
                        // coupon += "<div class='couponBox' type='" + comment.type + "'><img src='" + comment.background + "'><div class='priceBox'><span class='price1'>¥</span><span class='price2'>" + comment.price + "</span></div><p class='date'>" + comment.date + "，即日生效</p><div class='layer'></div><div class='layerBox'><div class='toUse layerBoxChildren'>去使用</div></div></div>"
                });

                $(".container").html("<div class='coupon'>" + coupon + "</div>" + footerHtml);
                $(".couponBox span").css({
                    "color": "#00569f"
                });
            } else {
                var coupon_hint = "<div class='coupons_hint_box'><div class='coupon_hint'><div class='nohint'><p class='nohint_no'>当前没有优惠券</p><p class='nohint_look'>去活动页面转转吧</p><p class='purl'><a href='lottery.html'>去看看</a></p></div></div></div>"
                $(".container").html(coupon_hint);
            }

            //点击屏幕，如果元素不是couponBox，则执行以下隐藏程序;
            $(document).click(function() {
                $(".layer").fadeOut();
                $(".detail").fadeOut();
                $(".toUse").fadeOut();
            });
            $(".couponBox").click(function(event) {
                event.stopPropagation();
                $(this).siblings(".couponBox").find(".layer").fadeOut();
                $(this).siblings(".couponBox").find(".detail").fadeOut();
                $(this).siblings(".couponBox").find(".toUse").fadeOut();
                $(this).find(".layer").fadeIn(100);
                $(this).find(".detail").fadeIn(200);
                $(this).find(".toUse").fadeIn(200);
            });
            $(window).scroll(function() {
                $(".layer").fadeOut(100);
                $(".detail").fadeOut(100);
                $(".toUse").fadeOut(100);
            });
            $(".detail").click(function() {
                window.location.href = "coupon_detail.html";
                // var type = $(this).attr("type"); // type(1为别墅 2为驾校 3为军旅)
                // if (type == "1") {
                //     window.location.href = "villa.html";
                // } else if (type == "2") {
                //     window.location.href = "index.html";
                // } else if (type == "3") {
                //     window.location.href = "army.html";
                // }
            })
            $(".toUse").click(function() {
                var type = $(this).parents(".couponBox").attr("type"); // type(1为别墅 2为驾校 3为军旅)
                if (type == "1") {
                    window.location.href = "villa.html";
                } else if (type == "2") {
                    window.location.href = "index.html";
                } else if (type == "3") {
                    window.location.href = "army.html";
                }
            })
        }

    }, 'json');

}

//获取已用优惠券
function used() {
    $.post("getCouponsUsed", {}, function(datas) {
        $(".container").html("");
        if (datas.status == 0) { //已使用优惠券
            var couponList = datas.data.coupons;
            if (couponList && couponList.length > 0) {
                var coupon = "";
                $.each(couponList, function(commentIndex, comment) {
                    coupon += "<div class='couponBox' type='" + comment.type + "'><img src='" + comment.background + "'><div class='priceBox'><span class='price1'>¥</span><span class='price2'>" + comment.price + "</span></div></div>"
                });
                $(".container").html("<div class='coupon'>" + coupon + "</div>" + footerHtml);

                $(".couponBox span").css({
                    "color": "#424242"
                });
            } else {
                var coupon_hint = "<div class='coupons_hint_box'><div class='coupon_hint'><div class='nohint'><p class='nohint_no'>还没有已用的券呢</p></div></div></div>"
                $(".container").html(coupon_hint);
            }
        }
    }, 'json');
}

function overdue() {
    $.post("getCouponsPassed", {}, function(datas) {
        $(".container").html("");
        if (datas.status == 0) { //过期
            var couponList = datas.data.coupons;
            if (couponList && couponList.length > 0) {
                var coupon = "";

                $.each(couponList, function(commentIndex, comment) {
                    coupon += "<div class='couponBox' type='" + comment.type + "'><img src='" + comment.background + "'><div class='priceBox'><span class='price1'>¥</span><span class='price2'>" + comment.price + "</span></div><p class='date'>" + comment.date + "，已过期</p></div>"
                });
                $(".container").html("<div class='coupon'>" + coupon + "</div>" + footerHtml);

                $(".couponBox span").css({
                    "color": "#424242"
                });
            } else {
                var coupon_hint = "<div class='coupons_hint_box'><div class='coupon_hint'><div class='nohint'><p class='nohint_no'>还没有过期的券呢</p></div></div></div>"
                $(".container").html(coupon_hint);
            }

            // $(".use_coupon").html("已过期，立即激活");
            // $(".use_coupon").click(function() {
            //     alert("暂不可激活");
            //     //          var confirmtext = confirm("即将扣除您的会员积分(15积分)，是否确认激活？"); 
            //     // if(confirmtext==true){
            //     //  $.post("activation.action",{},function(obj){
            //     //      if (obj.status == "0") {
            //     //          alert("激活成功");
            //     //          window.location.href="coupon.html";
            //     //      }else{
            //     //          alert("激活失败,可前往“你-会员”中查看您的会员积分。");
            //     //      }
            //     //  },"json");
            //     // }
            // });
        }
    }, "json");
}



/*
//付款
function recharge(){
    $.post("wxpay.action",{"total_fee":"15"},function(obj){
        if (obj.status=="1") {
             determine(obj);
        }else{
            alert("系统繁忙，请稍后再试。");
        }
       
    },"json");
}
//付款判断
function determine(obj){
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady(obj);
    }
}

 //微信支付
function onBridgeReady(obj){
    var payurl = obj.data;
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId":payurl.appid,     //公众号名称，由商户传入     
            "timeStamp":obj.timeStamp,         //时间戳，自1970年以来的秒数     
            "nonceStr":payurl.nonce_str, //随机串     
            "package":"prepay_id="+payurl.prepay_id,     
            "signType":"MD5",         //微信签名方式：     
            "paySign":obj.paySign //微信签名 
        },
        function(res){     
            if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                alert("充值成功");
            }// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。              
            else{
                alert("未支付");
            }
        }
         
    ); 
}

*/