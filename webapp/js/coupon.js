$(function(){
	renovation();
});

function renovation(){
	$.post("queryCoupon.action",{},function(obj){
		if (obj.status=="-40") {
			var coupon_hint = "<div class='coupons_hint_box'><div class='coupon_hint'><div class='nohint'><p class='nohint_no'>没有券</p><p class='nohint_look'>去优惠活动里进行看看吧</p><a href='lottery.html'>去看看</a></div></div></div>"
			$(".container").html(coupon_hint);
		}else{
			if (obj.price=="200") {
				var coupon_pic = "<img src='images/ds_coupon_02.png'>";
			}else if (obj.price=="300") {
				var coupon_pic = "<img src='images/ds_coupon_03.png'>";
			}else if (obj.price=="500") {
				var coupon_pic = "<img src='images/ds_coupon_05.png'>";
			}else if (obj.price=="600") {
				var coupon_pic = "<img src='images/ds_coupon_06.png'>";
			}else if (obj.price=="1000") {
				var coupon_pic = "<img src='images/ds_coupon_10.png'>";
			}
			var coupon = "<div class='coupon'>"+coupon_pic+"<div class='coupon_hint_text'><h2>优惠提示:</h2><p>30天有效期，从领取日开始计时</p><p>过期可使用会员积分激活继续使用</p></div><div class='use_coupon'></div></div>"
			$(".container").html(coupon);
			if (obj.status == "0") {
    			$(".use_coupon").html("立即使用");
    			$(".use_coupon").click(function(){
    				window.location.href="index.html";
    			});
    		}else if (obj.status == "-20") {
    			$(".use_coupon").html("已过期，立即激活");
    			$(".use_coupon").click(function(){
    				var confirmtext = confirm("即将扣除您的会员积分(15积分)，是否确认激活？"); 
					if(confirmtext==true){
						$.post("activation.action",{},function(obj){
							if (obj.status == "0") {
								alert("激活成功");
								renovation();
							}else{
								alert("激活失败,可前往“你-会员”中查看您的会员积分。");
							}
						},"json");
					}
    				
				});	
    		}else if (obj.status == "-30") {
    			$(".use_coupon").html("已使用");
    		}
		}
    },'json');	

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