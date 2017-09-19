$(function(){
	if (isWeiXin()) {
    }else{
        window.location.href="openWchat.html";//提示用客户端打开页面
    }
});

//判断是否是微信浏览器
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}