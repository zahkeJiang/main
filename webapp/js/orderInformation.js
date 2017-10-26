var ordernumber = "";
function ShowMessage() 
{ 
  	var thisURL = document.URL;    
   	var getval = thisURL.split('?')[1];  
   	ordernumber = getval.split("=")[1];  	
} 
window.onload=ShowMessage(); 
$(function(){
	
   	$.post("schedule.action",{"ordernumber":ordernumber},function(datas){
   		if (datas.status=="0") {
   			var userorder = datas.data.order;
			$(".user-name").html("真实姓名：&nbsp;"+userorder.realName);//姓名
			$(".user-tel").html("联系方式：&nbsp;"+userorder.phoneNumber);//手机号码
			$(".order-name").html(userorder.orderName);//订单名称
			$("#order-image").attr("src",userorder.orderImage);//订单图片
			$(".order-description").html(userorder.orderDescripe);//订单描述
			$(".order-mode").html(userorder.dsNote);//特有描述
			$(".order-number").html("订单编号：&nbsp;"+userorder.orderNumber);//订单号码
			$(".order-time").html("下单时间：&nbsp;"+userorder.orderTime);//创建订单时间
			$(".order-price").html("¥"+userorder.originalPrice);//套餐原价
			$(".price-pay").html("¥"+userorder.orderPrice);//实际付款
			$(".price-coupon").html("¥"+(userorder.originalPrice-userorder.orderPrice));//优惠价格
			// 订单状态值，0用户未支付；1已付款；2驾校特有，未上交材料；3驾校特有，驾校已报名，正在返还材料；4交易完成，未评价；5未付款取消订单；6已付款取消订单；7交易完成，已评价
			if (userorder.orderStatus=="0") {//用户提交订单未支付
				$(".chedule-content").html("未付款");
				$(".chedule-content").css({"color":"white","background":"orange"});
				$(".shedule-hint-text").html("您已提交订单,请尽快完成支付！");
				$(".footer").show();
				$(".toPay").show();
				$(".cancel").show();
				$(".price").css({"margin-bottom":"58px"});

				//未支付，去付款
				$(".toPay").click(function(){
					//拿到订单编号，进行判断属于那种类型，再加载不同
					var typeLetter = ordernumber.substr(0,1);//提取订单号收个字母，D驾校，V别墅，A军旅
    				var cancelUrl="";// type(1为别墅评论 2为驾校 3为军旅)
        			if (typeLetter=="V") {
            			//是别墅，需要先判断当前选择的别墅是否已经被预定
            			$.post("villaCheck",{"ordernumber":ordernumber},function(datas){
            				if (datas.status==0) {
            					window.location.href="payHint.html?ordernumber="+ordernumber;
            				}else{
            					alert(dats.msg);//您的套餐中别墅或日期已被预约
            				}
            			},"json");
        			}else{
           			 	window.location.href="payHint.html?ordernumber="+ordernumber;
        			}
					
				});
				//未支付，取消订单
				$(".cancel").click(function(){
					//拿到订单编号，进行判断属于那种类型，再加载不同
					var typeLetter = ordernumber.substr(0,1);//提取订单号收个字母，D驾校，V别墅，A军旅
    				var cancelUrl="";// type(1为别墅评论 2为驾校 3为军旅)
        			if (typeLetter=="V") {
            			cancelUrl="cancelVillaOrder";
        			}else if (typeLetter=="A") {
            			cancelUrl="cancelArmyOrder";
        			}else if (typeLetter=="D") {
           			 	cancelUrl="cancelOrder.action";
        			}

					var r = confirm("确定取消本订单吗？");
					if(r == true){
						$.post(cancelUrl,{"ordernumber":ordernumber},function(datas){
							if (status==0) {
								window.location.href="orderInformation.html?ordernumber="+ordernumber;
							}else{
								// alert("系统繁忙，请稍后再试");
							}
						},"json");
					}
				});
			}else if (userorder.orderStatus=="1"||userorder.orderStatus=="2") {//1代表用户已付款,驾校特有状态值2，代表材料尚未拿过来
				$(".chedule-content").html("已付款");
				$(".chedule-content").css({"color":"white","background":"green"});
				$(".shedule-hint-text").html("订单已支付，请准备好所需材料，24小时之内将会有工作人员与您联系上门提取，请保持电话畅通。");
				$(".footer").show();
				$(".cancel").show();
				$(".price").css({"margin-bottom":"58px"});

				//已支付，取消订单，此时是退款
				$(".cancel").click(function(){
					window.location.href="ds_refund.html?ordernumber="+ordernumber;
				});
			}else if (userorder.orderStatus=="3") {//驾校特有状态值，用户报名成功
				$(".chedule-content").html("已报名");
				$(".chedule-content").css({"color":"white","background":"green"});
				$(".shedule-hint-text").html("您已完成报名，材料正在返还的路上，请注意查收。");
				$(".shedule-hint-delivery").show();//显示材料收到按钮
				//材料已送达接口，确认，将状态值由3改为4
				$(".shedule-hint-delivery").click(function(){
					$.post("changeStatus.action",{"ordernumber":ordernumber},function(obj){
						window.location.href="orderInformation.html?ordernumber="+ordernumber;//刷新当前页面
					},'json');
				});
				$('delivery').unbind("click"); //移除点击事件
			}else if (userorder.orderStatus=="4") {//订单交易完成,7已评价
				$(".chedule-content").html("交易完成");
				$(".chedule-content").css({"color":"white","background":"green"});
				$(".shedule-hint-text").html("北京漂洋过海欢迎您再次使用!");
				$(".footer").show();
				$(".assess").show();
				$(".price").css({"margin-bottom":"58px"});
				//评价,获取当前订单号，进行判断是属于哪个类型
            	$(".assess").click(function(){
                	var typeLetter = ordernumber.substr(0,1);//提取订单号收个字母，D驾校，V别墅，A军旅
                	var type="";// type(1为别墅评论 2为驾校 3为军旅)
                	if (typeLetter=="V") {
                    	type=1;
                	}else if (typeLetter=="A") {
                    	type=3;
                	}else if (typeLetter=="D") {
                    	type=2;
                	}
               	 	window.location.href="assessSet.html?type="+type+"&ordernumber="+ordernumber;
            	});
			}else if (userorder.orderStatus=="5"||userorder.orderStatus=="6") {//订单交易关闭，5付款后取消，6未付款取消
				$(".chedule-content").html("交易关闭");
				$(".chedule-content").css({"color":"white","background":"#ccc"});
				$(".shedule-hint-text").html("北京漂洋过海欢迎您再次使用!");
			}else if (userorder.orderStatus=="7") {//订单交易完成
				$(".chedule-content").html("交易完成");
				$(".chedule-content").css({"color":"white","background":"green"});
				$(".shedule-hint-text").html("北京漂洋过海欢迎您再次使用!");	
			}
   		}
   	},"json");

});