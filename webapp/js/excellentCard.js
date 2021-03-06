$(function() {

	$.post("isApply", {}, function(datas) {
		if (datas.status != 0) {
			$(".apply").css({
				"background-color": "#ccc",
				"color": "#555",
			});
			$(".apply").html("已申请");
		} else {
			$(".apply").click(function() {
				$(".layer").show();
				$(".infoBox").show();
			});
		}
	}, "json");


	$("#close").click(function() {
		$(".layer").hide();
		$(".infoBox").hide();
	});


	$(".userInfoBox-row-sex-header").click(function() {
		$(".userInfoBox-row-sex").css({
			"border": "1px solid #4ea0ff"
		});
		$(".userInfoBox-row-sex-header").css({
			"border-bottom": "1px solid #ddd"
		});
		$(".userInfoBox-row-sex-choose").show();
	});

	//点击屏幕，如果元素不是userInfoBox-row-sex-header，则执行...
	$(document).click(function() {
		$(".userInfoBox-row-sex").css({
			"border": "1px solid #f6f1f1"
		});
		$(".userInfoBox-row-sex-header").css({
			"border-bottom": "0"
		});
		$(".userInfoBox-row-sex-choose").hide();
	});
	$(".userInfoBox-row-sex-header").click(function(event) {
		event.stopPropagation();
	});

	//选择性别
	$(".userInfoBox-row-sex-choose li").click(function() {
		$(".userInfoBox-row-sex-header span").html($(this).html());
	});

	$(".submitAplly").click(function() {
		var telNumber = /^1[3|4|5|8]\d{9}$/;
		var userName = $("#userName").val();
		var old = $("#old").val();
		var school = $("#school").val();
		var tel = $("#tel").val();
		var sexSelect = $(".userInfoBox-row-sex-header span").html();
		var age;
		if (sexSelect == "男") {
			age = 1;
		} else {
			age = 2;
		}
		console.log(userName, old, school, tel, sexSelect);
		if (userName != "" && old != "" && school != "" && tel != "" && sexSelect != "" && sexSelect != null) {
			if (telNumber.test(tel)) {
				$.post("applyCard", {
					"realName": userName,
					"age": old,
					"school": school,
					"mobile": tel,
					"gender": age
				}, function(datas) {
					if (datas.status == 0) {
						modalHintText("提交成功，请等待审核");
						$(".layer").hide();
						$(".infoBox").hide();
						$(".apply").css({
							"background-color": "#ccc",
							"color": "#555",
						});
						$(".apply").html("已申请");
						$(".apply").unbind("click"); //移除click
						// window.location.href="excellentCard.html";
					} else {
						modalHintText("您已提交申请，请勿重复提交");
					}
				}, "json");
			} else {
				modalHintText("手机号格式有误，请重新输入");
			}
		} else {
			modalHintText("请完善您的信息");
		}
	});
});