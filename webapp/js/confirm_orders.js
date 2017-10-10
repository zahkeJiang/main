
// var packageid = "";
// var select = "";
// var ordernumber = "";
// function ShowMessage() { 
//     var thisURL = decodeURI(location.href);    
//     var getval  = thisURL.split('?')[1];
//     packageid = getval.split("=")[1].split("&")[0];
//     select = getval.split("=")[2].split("&")[0];
//     ordernumber = getval.split("=")[3];
// }
// window.onload=ShowMessage(); 


var packageid = $.cookie("packageid");//班型编号id
var select = $.cookie("select");//是否有使用优惠券
var ordernumber = $.cookie("ordernumber");//订单号

$(function(){
	//获取订单信息
	$.post("schedule.action",{"ordernumber":ordernumber},function(datas){
		if (datas.status=="0") {
			var userorder = datas.data.dsOrder;
			$(".real_name").html(userorder.realName);
			$(".tel").html(userorder.phoneNumber);
			$(".address").html(userorder.address);
			$(".ds_training_mode").html(userorder.note);
			$(".ds_name").html(userorder.dsName);
			$(".ds_type").html(userorder.dsType+"&nbsp;&frasl;&nbsp;"+userorder.models+"&nbsp;&frasl;&nbsp"+userorder.trainTime);
			$(".order_number").html(userorder.orderNumber);
			var coupons_sum = "";
			if (datas.data.price=="") {
				coupons_sum = 0;
			}else{
				coupons_sum = datas.data.price;
			}
			$(".price").html("&nbsp;"+userorder.orderPrice+".00");
			$(".ds_price").html("¥&nbsp;"+(userorder.orderPrice+coupons_sum)+".00");
			
			$(".ds_coupon").html("¥&nbsp;"+coupons_sum+".00");
		}
	},"json");
	//支付宝支付
	$(".submit").click(function(){
		window.location.href="payHint.html?ordernumber="+ordernumber;	
	});
});