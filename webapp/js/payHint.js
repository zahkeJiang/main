var ordernumber = "";
function ShowMessage() { 
    var thisURL = decodeURI(location.href);    
    var getval  = thisURL.split('?')[1];
    ordernumber = getval.split("=")[1];
    if (isWeiXin()) {
   	// alert("我是微信浏览器");
    }else{
        window.location.href="aliPay?ordernumber="+ordernumber;
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
	$(".pay_finish_text").click(function(){
		window.location.href="ds_pay.html";
	});
});