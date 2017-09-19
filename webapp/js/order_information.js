var ordernumb = "";
function ShowMessage() 
{ 
   var thisURL = document.URL;    
   var getval = thisURL.split('?')[1];  
   ordernumb = getval.split("=")[1];  
} 
window.onload=ShowMessage(); 

var orderschedule = '<div class="progress_pic"><div class="progress_pic_style"><div class="progress_bg"></div><img id="cartoon1" src="images/finish.png" height="30px" width="30px"><div class="submit_bg"></div></div><div class="progress_pic_style"><div class="audit_bg"></div><img id="cartoon2" src="images/waite.png" height="12px" width="12px"><div class="audit_bg"></div></div><div class="progress_pic_style"><div class="finish_bg"></div><img id="cartoon3" src="images/waite.png" height="12px" width="12px"><div class="finish_bg"></div></div><div class="progress_pic_style"><div class="return_bg"></div><img id="cartoon4" src="images/waite.png" height="12px" width="12px"><div class="progress_bg"></div></div></div><div class="orderprogress"><div class="submit_order"><div class="progress_style"><h2 >报名订单</h2><p class="submit_data">&nbsp;</p><p class="submit_time">&nbsp;</p></div></div><div class="material_audit"><div class="progress_style"><h2>材料审核</h2><p class="audit_data">&nbsp;</p><p class="audit_time">&nbsp;</p></div></div><div class="finish_order"><div class="progress_style"><h2>完成报名</h2><p class="finish_data">&nbsp;</p><p class="finish_time">&nbsp;</p></div></div><div class="material_return"><div class="progress_style"><h2>材料返还</h2><p class="return_data">&nbsp;</p><p class="return_time">&nbsp;</p></div></div></div>';
$(function(){
	$.post("schedule.action",{"ordernumber":ordernumb},function(obj){
		if (obj.status=="0") {
			var userorder = obj.data.dsOrder;
			$(".realname").html(userorder.realName);
			$(".tel").html(userorder.phoneNumber);
			$(".address").html(userorder.address);
			$(".ds_training_mode").html(userorder.note);
			$(".ds_name").html(userorder.dsName);
			$(".ds_type").html(userorder.dstype+"&nbsp;&frasl;&nbsp;"+userorder.models+"&nbsp;&frasl;&nbsp"+userorder.trainTime);
			$(".description").html(userorder.description);
			$(".order_number").html(userorder.orderNumber);
			$(".order_time").html(userorder.payTime);
			$(".ds_pay").html("¥&nbsp;"+userorder.orderPrice+".00");
			$(".ds_price").html("¥&nbsp;"+(userorder.orderPrice+obj.data.price)+".00");
			$(".ds_coupon").html("¥&nbsp;"+obj.data.price+".00");
			
			if (userorder.orderStatus=="1"||userorder.orderStatus=="2") {
				$(".shedule").html(orderschedule);
				var refund = "<div class='refund'>取消订单</div>";
				$(".footer").html(refund);
				$(".refund").click(function(){
					window.location.href="ds_refund.html?ordernumber="+userorder.orderNumber;
				});
				if (userorder.orderStatus=="2") {
					var submitTime = userorder.payTime;
					var submit_data = submitTime.split(' ')[0];
					var submit_time = submitTime.split(" ")[1];
					$(".submit_data").html(submit_data);
					$(".submit_time").html(submit_time);

					var audittime = userorder.submiTtime;
					var audit_data = audittime.split(' ')[0];
					var audit_time = audittime.split(" ")[1];
					$(".audit_data").html(audit_data);
					$(".audit_time").html(audit_time);

					$(".material_audit p").css({"color":"#67c0ee"});
					$(".material_audit h2").css({"color":"#67c0ee"});
					$("#cartoon2").attr('src',"images/finish.png");
					$("#cartoon2").css({"height":"30px","width":"30px"});
					$(".audit_bg").css({"background-color":"#67c0ee"});
					$(".hint").html("您的材料正在接受审核，24小时之内审核完成。");
				}else{
					var submitTime = userorder.payTime;
					var submit_data = submitTime.split(' ')[0];
					var submit_time = submitTime.split(" ")[1];
					$(".submit_data").html(submit_data);
					$(".submit_time").html(submit_time);

					$(".hint").html("您已提交报名。请准备好所需材料，24小时之内将会有工作人员与您联系上门提取，请保持电话畅通。");
				}
			}else if (userorder.orderstatus=="3") {
				$(".shedule").html(orderschedule);
				var submitTime = userorder.payTime;
				var submit_data = submitTime.split(' ')[0];
				var submit_time = submitTime.split(" ")[1];
				$(".submit_data").html(submit_data);
				$(".submit_time").html(submit_time);

				var audittime = userorder.submitTime;
				var audit_data = audittime.split(' ')[0];
				var audit_time = audittime.split(" ")[1];
				$(".audit_data").html(audit_data);
				$(".audit_time").html(audit_time);

				var finishtime = userorder.signTime;
				var finish_data = finishtime.split(' ')[0];
				var finish_time = finishtime.split(" ")[1];
				$(".finish_data").html(finish_data);
				$(".finish_time").html(finish_time);

				$(".material_audit p").css({"color":"#67c0ee"});
				$(".material_audit h2").css({"color":"#67c0ee"});
				$("#cartoon2").attr('src',"images/finish.png");
				$(".audit_bg").css({"background-color":"#67c0ee"});

				$(".finish_order p").css({"color":"#67c0ee"});
				$(".finish_order h2").css({"color":"#67c0ee"});
				$("#cartoon3").attr('src',"images/finish.png");
				$("#cartoon3").css({"height":"30px","width":"30px"});
				$(".finish_bg").css({"background-color":"#67c0ee"});
				$(".hint").html("您已完成报名，材料正在返还的路上，请注意查收。");

				$(".delivery").css({"display":"inline"});
				//已送达接口
				$(".delivery").click(function(){
					$.post("changeStatus.action",{"ordernumber":userorder.orderNumber},function(obj){
						$(".chedule_content").empty();
						$(".chedule_content").html("交易完成");
						$(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#01b468"});
						$(".hint").empty();
						$(".hint").html("材料已送达，欢迎您再次使用！");
						$(".delivery").html("已送达");
						$(".delivery").css({"background-color":"#d2e9ff","display":"inline","text-align":"center","line-height":"28px"});
					},'json');
				});
				$('delivery').unbind("click"); //移除点击事件
				var result = "<div class='result'><p>您已报名完成。</p><p>欢迎您再次使用。</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").html(result);
			}else if (userorder.orderStatus=="4") {
				$(".chedule_content").empty();
				$(".chedule_content").html("交易完成");
				$(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#01b468"});
				$(".hint").empty();
				$(".hint").html("材料已送达，欢迎您再次使用！");
				$(".delivery").html("已送达");
				$(".delivery").css({"background-color":"#d2e9ff","display":"inline","text-align":"center","line-height":"28px"});
				var result = "<div class='result'><p>您已报名完成。</p><p>欢迎您再次使用。</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").html(result);
			}else if (userorder.orderStatus=="5") {
				$(".chedule_content").empty();
				$(".chedule_content").html("已取消");
				$(".chedule_content").css({"height":"110px","padding":"0 16px","line-height":"110px","font-size":"20px","color":"white","background-color":"#01b468"});
				$(".hint").empty();
				$(".hint").html("北京漂洋过海，欢迎您再次使用！");
				var result = "<div class='result'><p>您已报名完成。</p><p>欢迎您再次使用。</p><p>北京漂洋过海，一切因你而在！</p></div>";
				$(".footer").html(result);
			}
		}
	},"json");
	
});
