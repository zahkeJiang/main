var ordernumber = "";

function ShowMessage() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	ordernumber = getval.split("=")[1];
}
window.onload = ShowMessage();
$(function() {
	var typeLetter = ordernumber.substr(0, 1); //提取订单号收个字母，D驾校，V别墅，A军旅
	$.post("schedule.action", {
		"ordernumber": ordernumber
	}, function(datas) {
		if (datas.status == "0") {
			var userorder = datas.data.order;
			$(".user-name").html("真实姓名：&nbsp;" + userorder.realName); //姓名
			$(".user-tel").html("联系方式：&nbsp;" + userorder.phoneNumber); //手机号码
			$(".order-name").html(userorder.orderName); //订单名称
			$("#order-image").attr("src", userorder.orderImage); //订单图片
			// $(".order-description").html(userorder.orderDescripe);//订单描述
			var miaosu = userorder.orderDescripe.split('/');


			var mioasuxinxi = "";
			for (var j = 0; j < miaosu.length; j++) {
				mioasuxinxi += miaosu[j] + "<br />"
			}
			console.log(mioasuxinxi);
			$(".order-description").html(mioasuxinxi); //订单描述


			$(".order-number").html("订单编号：&nbsp;" + userorder.orderNumber); //订单号码
			$(".order-time").html("下单时间：&nbsp;" + userorder.orderTime); //创建订单时间
			$(".order-price").html("¥" + userorder.originalPrice); //套餐原价
			$(".price-pay").html("¥" + userorder.orderPrice); //实际付款
			$(".price-coupon").html("- ¥" + (datas.data.price)); //优惠价格
			$(".price-weikuan").html("¥" + (userorder.originalPrice - datas.data.price - userorder.orderPrice)); //尾款
			// 订单状态值，0用户未支付，驾校已预约；1已付款，4交易完成，未评价；5未付款取消订单；6已付款取消订单；7交易完成，已评价
			if (userorder.orderStatus == "0") { //用户提交订单未支付
				if (typeLetter == "D") {
					$(".shedule-hint-text").html("您已成功预约驾考报名，工作人员将在24小时之内与您取得联系，请保持电话畅通。");
					$(".chedule-content").html("已预约");
					$(".cancel").html("取消预约");
				} else {
					$(".shedule-hint-text").html("您已提交订单,请尽快完成支付！");
					$(".chedule-content").html("未付款");
				}
				$(".chedule-content").css({
					"color": "white",
					"background": "orange"
				});

				$(".footer").show();
				$(".toPay").show();
				$(".cancel").show();
				$(".price").css({
					"margin-bottom": "58px"
				});
				$(".pay_text").html("应付款");

				//未支付，去付款
				$(".toPay").click(function() {
					//拿到订单编号，进行判断属于那种类型，再加载不同
					var cancelUrl = ""; // type(1为别墅评论 2为驾校 3为军旅)
					if (typeLetter == "V") {
						//是别墅，需要先判断当前选择的别墅是否已经被预定
						$.post("villaCheck", {
							"ordernumber": ordernumber
						}, function(datas) {
							if (datas.status == 0) {
								$(".layer").show();
								$(".payBox").show();
							} else {
								alert(dats.msg); //您的套餐中别墅或日期已被预约
							}
						}, "json");
					} else {
						$(".layer").show();
						$(".payBox").show();
					}

				});
				//未支付，取消订单
				$(".cancel").click(function() {
					//拿到订单编号，进行判断属于那种类型，再加载不同

					var cancelUrl = ""; // type(1为别墅评论 2为驾校 3为军旅)
					if (typeLetter == "V") {
						cancelUrl = "cancelVillaOrder";
					} else if (typeLetter == "A") {
						cancelUrl = "cancelArmyOrder";
					} else if (typeLetter == "D") {
						cancelUrl = "cancelOrder.action";
					}

					var r = confirm("确定取消本订单吗？");
					if (r == true) {
						$.post(cancelUrl, {
							"ordernumber": ordernumber
						}, function(datas) {
							if (status == 0) {
								window.location.href = "orderInformation.html?ordernumber=" + ordernumber;
							} else {
								alert("网络异常，请稍后再试");
							}
						}, "json");
					}
				});
			} else if (userorder.orderStatus == "1" || userorder.orderStatus == "2") { //1代表用户已付款,驾校特有状态值2，代表材料尚未拿过来
				//拿到订单编号，进行判断属于那种类型，再加载不同

				if (typeLetter == "V") {
					$(".shedule-hint-text").html("订单已支付预约定金，请入住别墅时交付尾款，小漂为您服务。");
					$(".chedule-content").html("已付款");
				} else if (typeLetter == "A") {
					$(".shedule-hint-text").html("订单已支付预约定金，请到达作战基地时交付尾款，小漂为您服务。");
					$(".chedule-content").html("已付款");
				} else if (typeLetter == "D") {
					$(".shedule-hint-text").html("您已成功预约驾考报名，工作人员将在24小时之内与您取得联系，请保持电话畅通。");
					$(".chedule-content").html("已预约");
					$(".cancel").html("取消预约");
				}

				$(".chedule-content").css({
					"color": "white",
					"background": "green"
				});

				$(".footer").show();
				$(".cancel").show();
				$(".price").css({
					"margin-bottom": "58px"
				});

				//已支付，取消订单，此时是退款，驾校是取消预约
				$(".cancel").click(function() {
					window.location.href = "refund.html?ordernumber=" + ordernumber;
				});
			} else if (userorder.orderStatus == "3") { //驾校特有状态值，用户报名成功
				$(".chedule-content").html("已报名");
				$(".chedule-content").css({
					"color": "white",
					"background": "green"
				});
				$(".shedule-hint-text").html("您已完成报名，材料正在返还的路上，请注意查收。");
				$(".shedule-hint-delivery").show(); //显示材料收到按钮
				//材料已送达接口，确认，将状态值由3改为4
				$(".shedule-hint-delivery").click(function() {
					$.post("changeStatus.action", {
						"ordernumber": ordernumber
					}, function(obj) {
						window.location.href = "orderInformation.html?ordernumber=" + ordernumber; //刷新当前页面
					}, 'json');
				});
				$('delivery').unbind("click"); //移除点击事件
			} else if (userorder.orderStatus == "4") { //订单交易完成,7已评价
				$(".chedule-content").html("交易完成");
				$(".chedule-content").css({
					"color": "white",
					"background": "green"
				});
				$(".shedule-hint-text").html("北京漂洋过海欢迎您再次使用!");
				$(".footer").show();
				$(".assess").show();
				$(".price").css({
					"margin-bottom": "58px"
				});
				//评价,获取当前订单号，进行判断是属于哪个类型
				$(".assess").click(function() {
					var type = ""; // type(1为别墅评论 2为驾校 3为军旅)
					if (typeLetter == "V") {
						type = 1;
					} else if (typeLetter == "A") {
						type = 3;
					} else if (typeLetter == "D") {
						type = 2;
					}
					window.location.href = "assessSet.html?type=" + type + "&ordernumber=" + ordernumber;
				});
			} else if (userorder.orderStatus == "5" || userorder.orderStatus == "6") { //订单交易关闭，5付款后取消，6未付款取消
				$(".chedule-content").html("交易关闭");
				$(".chedule-content").css({
					"color": "white",
					"background": "#ccc"
				});
				$(".shedule-hint-text").html("北京漂洋过海欢迎您再次使用!");
			} else if (userorder.orderStatus == "7") { //订单交易完成
				$(".chedule-content").html("交易完成");
				$(".chedule-content").css({
					"color": "white",
					"background": "green"
				});
				$(".shedule-hint-text").html("北京漂洋过海欢迎您再次使用!");
			}
		}
	}, "json");


	// 为支付订单，点击去支付弹窗操作
	//为支付方式的radio设置自定义点击样式
	$(".payMode").click(function() {
		$(this).children(".payModeImg").css({
			"background": "url(./images/circle_choose.png)",
			"background-size": "20px"
		});
		$(this).parents().siblings("label").find(".payModeImg").css({
			"background": "url(./images/circle.png)",
			"background-size": "20px"
		});
	});

	//选择支付方式后点击确认
	$(".surePay").click(function() {
		var payMode = $("input[name='payMode-radio']:checked").val();
		console.log(payMode);
		if (payMode == "JD") { //京东支付
			$.post("JDPay", {
				"ordernumber": ordernumber
			}, function(data) {
				if (data.status == 0) {
					var jdOrderPay = data.data.jdOrderPay;
					$("#version").val(jdOrderPay.version);
					$("#merchant").val(jdOrderPay.merchant);
					$("#sign").val(jdOrderPay.sign);
					$("#tradeNum").val(jdOrderPay.tradeNum);
					$("#tradeName").val(jdOrderPay.tradeName);
					$("#tradeTime").val(jdOrderPay.tradeTime);
					$("#amount").val(jdOrderPay.amount);
					$("#currency").val(jdOrderPay.currency);
					$("#callbackUrl").val(jdOrderPay.callbackUrl);
					$("#notifyUrl").val(jdOrderPay.notifyUrl);
					$("#userId").val(jdOrderPay.userId);
					$("#orderType").val(jdOrderPay.orderType);
					document.getElementById("batchForm").submit();
				}
			}, 'json');

		} else if (payMode == "aliPay") { //支付宝支付
			window.location.href = "payHint.html?ordernumber=" + ordernumber;
		}
	});

	//关闭弹窗
	$(".payBox p").click(function() {
		$(".layer").hide();
		$(".payBox").hide();
	});

});