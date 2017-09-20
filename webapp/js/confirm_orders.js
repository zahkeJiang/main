var userid = "";
var packageid = "";
var select = "";
var ordernumber = "";
function ShowMessage() { 
    var thisURL = decodeURI(location.href);    
    var getval  = thisURL.split('?')[1];
    userid = getval.split("=")[1].split("&")[0];
    packageid = getval.split("=")[2].split("&")[0];
    select = getval.split("=")[3].split("&")[0];
    select = getval.split("=")[4];
}
window.onload=ShowMessage(); 

$(function(){
	//获取订单信息
	$.post("schedule.action",{"ordernumber":ordernumb},function(datas){
		if (datas.status=="0") {
			var userorder = datas.data.dsOrder;
			$(".realname").html(userorder.realName);
			$(".tel").html(userorder.phoneNumber);
			$(".address").html(userorder.address);
			$(".ds_training_mode").html(userorder.note);
			$(".ds_name").html(userorder.dsName);
			$(".ds_type").html(userorder.dstype+"&nbsp;&frasl;&nbsp;"+userorder.models+"&nbsp;&frasl;&nbsp"+userorder.trainTime);
			$(".order_number").html(userorder.orderNumber);
			$(".price").html("¥&nbsp;"+userorder.orderPrice+".00");
			$(".ds_price").html("¥&nbsp;"+(userorder.orderPrice+datas.data.price)+".00");
			$(".ds_coupon").html("¥&nbsp;"+datas.data.price+".00");
		}
	}
	//支付宝支付
	$(".submit").click(function(){
		window.location.href="determin_browser.html?ordernumber"+ordernumber;	
	});
});