var ordernumb = "";
function ShowMessage() 
{ 
   var thisURL = document.URL;    
   var getval = thisURL.split('?')[1];  
   ordernumb = getval.split("=")[1];  
} 
window.onload=ShowMessage(); 

var orderschedule = '<div class="progress_pic"><div class="progress_pic_style"><div class="progress_bg"></div><img id="cartoon1" src="images/finish.png" height="30px" width="30px"><div class="submit_bg"></div></div><div class="progress_pic_style"><div class="audit_bg"></div><img id="cartoon2" src="images/waite.png" height="12px" width="12px"><div class="audit_bg"></div></div><div class="progress_pic_style"><div class="finish_bg"></div><img id="cartoon3" src="images/waite.png" height="12px" width="12px"><div class="finish_bg"></div></div><div class="progress_pic_style"><div class="return_bg"></div><img id="cartoon4" src="images/waite.png" height="12px" width="12px"><div class="progress_bg"></div></div></div><div class="orderprogress"><div class="submit_order"><div class="progress_style"><h2 >报名订单</h2><p class="submit_data">&nbsp;</p><p class="submit_time">&nbsp;</p></div></div><div class="material_audit"><div class="progress_style"><h2>已付款</h2><p class="audit_data">&nbsp;</p><p class="audit_time">&nbsp;</p></div></div><div class="finish_order"><div class="progress_style"><h2>已报名</h2><p class="finish_data">&nbsp;</p><p class="finish_time">&nbsp;</p></div></div><div class="material_return"><div class="progress_style"><h2>材料返还</h2><p class="return_data">&nbsp;</p><p class="return_time">&nbsp;</p></div></div></div>';
$(function(){
	$.post("schedule.action",{"ordernumber":ordernumb},function(obj){
		if (obj.status=="0") {
			var userorder = obj.data.dsOrder;
			$(".realname").html(userorder.realName);
			$(".tel").html(userorder.phoneNumber);
			$(".address").html(userorder.address);
			$(".ds_training_mode").html(userorder.note);
			$(".ds_name").html(userorder.dsName);
			$(".ds_type").html(userorder.dsType+"&nbsp;&frasl;&nbsp;"+userorder.models+"&nbsp;&frasl;&nbsp"+userorder.trainTime);
			$(".description").html(userorder.description);
			$(".order_number").html(userorder.orderNumber);
			$(".order_time").html(userorder.createTime);
			var coupons_sum = "";
			if (obj.data.price=="") {
				coupons_sum = 0;
			}else{
				coupons_sum = obj.data.price;
			}
			$(".ds_pay").html("¥&nbsp;"+userorder.orderPrice+".00");
			$(".ds_price").html("¥&nbsp;"+(userorder.orderPrice+coupons_sum)+".00");
			$(".ds_coupon").html("¥&nbsp;"+coupons_sum+".00");
			
			if (userorder.orderStatus=="0") {//用户提交订单未支付
				$(".shedule").html(orderschedule);
				$(".blank").css({"margin-bottom":"36px"});
				var footerBox = "<div class='footerBox'><span class='gopay'>去支付</span><span class='cancel'>取消订单</span><div>";
				$(".footer").html(footerBox);
				//未支付，去付款
				$(".gopay").click(function(){
					window.location.href="payHint.html?ordernumber="+userorder.orderNumber;
				});
				//未支付，取消订单
				$(".cancel").click(function(){
					var r = confirm("确定取消本订单吗？");
					if(r == true){
						$.post("cancelOrder.action",{"ordernumber":userorder.orderNumber},function(datas){
							if (status==0) {
								window.location.href="order_information.html?ordernumber="+userorder.orderNumber;
							}else{
								// alert("系统繁忙，请稍后再试");
							}
						},"json");

					}
				});
				// 显示提交订单时间
				var submitTime = userorder.createTime;
				var submit_data = submitTime.split(' ')[0];
				var submit_time = submitTime.split(" ")[1];
				$(".submit_data").html(submit_data);
				$(".submit_time").html(submit_time);
				$(".hint").html("您已提交订单,请尽快完成支付！");
			}else if (userorder.orderStatus=="1"||userorder.orderStatus=="2") {//用户已支付，或者材料正在接收中
				$(".shedule").html(orderschedule);
				$(".blank").css({"margin-bottom":"36px"});
				var footerBox = "<div class='footerBox'><span class='cancel'>取消订单</span><div>";
				$(".footer").html(footerBox);
				$(".cancel").click(function(){
					window.location.href="ds_refund.html?ordernumber="+userorder.orderNumber;
				});
				// 显示提交订单时间
				var submitTime = userorder.createTime;
				var submit_data = submitTime.split(' ')[0];
				var submit_time = submitTime.split(" ")[1];
				$(".submit_data").html(submit_data);
				$(".submit_time").html(submit_time);
				// 显示付款时间
				var audittime = userorder.payTime;
				var audit_data = audittime.split(' ')[0];
				var audit_time = audittime.split(" ")[1];
				$(".audit_data").html(audit_data);
				$(".audit_time").html(audit_time);
				$(".hint").html("您已付款成功。请准备好所需材料，24小时之内将会有工作人员与您联系上门提取，请保持电话畅通。");
				$(".material_audit p").css({"color":"#67c0ee"});
				$(".material_audit h2").css({"color":"#67c0ee"});
				$("#cartoon2").attr('src',"images/finish.png");
				$("#cartoon2").css({"height":"30px","width":"30px"});
				$(".audit_bg").css({"background-color":"#67c0ee"});
				
			}else if (userorder.orderStatus=="3") {//用户报名交接成功时
				$(".shedule").html(orderschedule);
				// 显示提交订单时间
				var submitTime = userorder.createTime;
				var submit_data = submitTime.split(' ')[0];
				var submit_time = submitTime.split(" ")[1];
				$(".submit_data").html(submit_data);
				$(".submit_time").html(submit_time);
				// 显示付款时间
				var audittime = userorder.payTime;
				var audit_data = audittime.split(' ')[0];
				var audit_time = audittime.split(" ")[1];
				$(".audit_data").html(audit_data);
				$(".audit_time").html(audit_time);
				//显示已完成报名时间
				var finishtime = userorder.signTime;
				var finish_data = finishtime.split(' ')[0];
				var finish_time = finishtime.split(" ")[1];
				$(".finish_data").html(finish_data);
				$(".finish_time").html(finish_time);

				$(".material_audit p").css({"color":"#67c0ee"});
				$(".material_audit h2").css({"color":"#67c0ee"});
				$("#cartoon2").attr('src',"images/finish.png");
				$("#cartoon2").css({"height":"30px","width":"30px"});
				$(".audit_bg").css({"background-color":"#67c0ee"});

				$(".finish_order p").css({"color":"#67c0ee"});
				$(".finish_order h2").css({"color":"#67c0ee"});
				$("#cartoon3").attr('src',"images/finish.png");
				$("#cartoon3").css({"height":"30px","width":"30px"});
				$(".finish_bg").css({"background-color":"#67c0ee"});
				$(".hint").html("您已完成报名，材料正在返还的路上，请注意查收。");

				$(".delivery").css({"display":"inline"});
				//材料已送达接口，确认，将状态值由3改为4
				$(".delivery").click(function(){
					$.post("changeStatus.action",{"ordernumber":userorder.orderNumber},function(obj){
						window.location.href="order_information.html?ordernumber="+userorder.orderNumber;//刷新当前页面
						// $(".chedule_content").empty();
						// $(".chedule_content").html("交易完成");
						// $(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#01b468"});
						// $(".hint").empty();
						// $(".hint").html("材料已送达，欢迎您再次使用！");
						// $(".delivery").html("已送达");
						// $(".delivery").css({"background-color":"#d2e9ff","display":"inline","text-align":"center","line-height":"28px"});
					},'json');
				});
				$('delivery').unbind("click"); //移除点击事件
				var result = "<div class='result'><p>您已报名成功；</p><p>欢迎您再次使用；</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").html(result);
			}else if (userorder.orderStatus=="4") {
				$(".chedule_content").empty();
				$(".chedule_content").html("交易完成");
				$(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#01b468"});
				$(".hint").empty();
				$(".hint").html("材料已送达，欢迎您再次使用！");
				$(".delivery").html("已送达");
				$(".delivery").css({"background-color":"#d2e9ff","display":"inline","text-align":"center","line-height":"28px"});
				var result = "<div class='result'><p>您已报名成功；</p><p>欢迎您再次使用；</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").html(result);
			}else if (userorder.orderStatus=="5") {
				$(".chedule_content").empty();
				$(".chedule_content").html("交易关闭");
				$(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#ccc"});
				$(".hint").empty();
				$(".hint").html("北京漂洋过海，欢迎您再次使用！");
				var result = "<div class='result'><p>您已报名成功；</p><p>欢迎您再次使用；</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").html(result);
			}else if (userorder.orderStatus=="6") {
				$(".chedule_content").empty();
				$(".chedule_content").html("交易关闭");
				$(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#ccc"});
				$(".hint").empty();
				$(".hint").html("北京漂洋过海，欢迎您再次使用！");
				var result = "<div class='result'><p>感谢您的使用；</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").empty();	
				$(".footer").html(result);
			}
		}
	},"json");
	
});
