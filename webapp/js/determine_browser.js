var userid = "";
var packageid = "";
var select = "";
function ShowMessage() { 
    var thisURL = decodeURI(location.href);    
    var getval  = thisURL.split('?')[1];
    userid = getval.split("=")[1].split("&")[0];
    packageid = getval.split("=")[2].split("&")[0];
    select = getval.split("=")[3].split("&")[0];
    if (isWeiXin()) {
//    	alert("我是微信浏览器");
    }else{
        window.location.href="dspay.action?userid="+userid+"&packageid="+packageid+"&select="+select;
    }
} 
window.onload=ShowMessage(); 

function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

$(function(){
    var pay_finish = '<div class="pay_finish"><p>是否完成支付?</p><span>请确保支付成功后，点击<span class="pay_finish_text">完成付款</span></span></div>';
    var phone_width = $(window).width();//获取屏幕宽度
    var phone_height = $(window).height();//获取屏幕高度
    var ratio = phone_height/phone_width;//得到高与宽的比值
    $('.container').height(($(window).height())*(3/4));//设置container元素高度为屏幕高度的3/4
    $(".pay_finish_box").height($(window).height()*(1/4));
    $(".pay_finish_box").html(pay_finish);
	$(".pay_finish_text").click(function(){
		window.location.href="ds_pay.html";
	});
});