$(function() {
	//获取用户信息
	userload();
	getUserCoin();
	//跳转到用户昵称页面
	// $(".nickname").click(function(){
	// 	var nickname = $("#nickname").html();
	//  		var nicknameurl="nickname.html?nickname="+nickname;   
	//        window.location.assign(encodeURI(nicknameurl));   
	// });
	//跳转到更换手机号页面
	// $(".mobile").click(function(){
	// 	var oldmobile = $("#mobile").html();
	// 	if (oldmobile=="") {
	// 		window.location.href="login.html";
	// 	}else{
	// 		window.location.href="mobile.html?mobile="+oldmobile;   
	// 	}

	// });
	//跳转我的订单
	$(".orders").click(function() {
		window.location.href = "myorder.html";
	});
	//跳转到我的推广页面
	$(".spread").click(function() {
			window.location.href = "spreadIndex.html";
		})
		//跳转到更换学校页面
	$(".school").click(function() {
		var school = $("#school").html();
		var schoolurl = "school.html?school=" + school;
		window.location.assign(encodeURI(schoolurl));
	});
	//跳转到更换城市页面
	$(".city").click(function() {
		var city = $("#city").html();
		window.location.href = "address.html";
	});


	//跳转到性别页面
	// $(".sex").click(function(){
	// 	var usersex = $("#sex").html();
	// 	var sex = "";
	// 	if (usersex=="男") {
	// 		sex = 1;
	// 	}else if (usersex == "女") {
	// 		sex = 0;
	// 	}else{
	// 		sex = "";
	// 	}
	//  		window.location.href="sex.html?sex="+sex;  
	//  	})
});


function userload() {
	$.post("personal.action", {}, function(obj) {

		if (obj.status == "0") {
			var userobj = obj.data.userInfo;
			// document.getElementById('icon').src=obj.headimageurl;
			// $("#icon").attr('src',userobj.headimageurl);
			// if (userobj.headimageurl!=null||userobj.headimageurl!="") {
			// 	$("#icon").attr('src',userobj.headimageurl);
			// }else{
			// 	if(userobj.sex=="1"){//男
			// 		$("#icon").attr('src',"./images/boy.jpg");
			// 	}else if(userobj.sex=="2"){//女
			// 		$("#icon").attr('src',"./images/girl.jpg");
			// 	}else{//不确定
			// 		$("#icon").attr('src',"./images/boy.jpg");
			// 	}	
			// }
			$('#nickname').html(userobj.nickname);
			$('#mobile').html(userobj.phoneNumber);
			if (userobj.sex != null) {
				if (userobj.sex == "1") {
					$('#sex').html("男");
					$("#icon").attr('src', "./images/boy.jpg");
				} else {
					$('#sex').html("女");
					$("#icon").attr('src', "./images/girl.jpg");
				}
			} else {
				$('#sex').html("保密");
				$("#icon").attr('src', "./images/boy.jpg");
			}
			if (userobj.school != null) {
				$('#school').html(userobj.school);
			} else {
				$('#school').html("");
			}
			if (userobj.city != null) {
				$('#city').html(userobj.city);
			} else {
				$('#city').html("");
			}
			var integral = userobj.integral;
			if (integral > 4999) {
				$('#integral').html("铂金会员");
			} else if (integral > 499) {
				$('#integral').html("黄金会员");
			} else if (integral > 14) {
				$('#integral').html("白银会员");
			} else {
				$('#integral').html("普通用户");
			}
		}
	}, 'json');
}


//获取用户金币，是否签过到
function getUserCoin() {
	$.post("selectUserCoin", {}, function(datas) {
		if (datas.status == 0) {
			var coinData = datas.data.userCoin;
			$(".spread-gold").html(coinData.coin); //推广币
			$(".normal-gold").html(coinData.generalCoin); //普通币
			if (coinData.isSign == 1) {
				$(".qiandao").css({
					"background": "#f6f1f1",
					"box-shadow": "-1px -2px 1px #ddd inset",
					"color": "#4ea0ff"
				});
				$(".qiandao").html('<a href="integral.html"><span>已签到</span><span class="glyphicon glyphicon-menu-right qiandaoRight"></span></a>');

			} else {
                $(".qiandao").html('签到');
                //签到
				$(".qiandao").click(function() {
					updateUserSign(coinData.generalCoin);
					$(this).unbind("click");
				});

			}
		}
	}, "json");
}

//签到
function updateUserSign(generalCoin) {
	$.post("updateUserSign", {}, function(datas) {
		if (datas.status == 0) {
			var generalCoinCount = generalCoin;
			if (datas.data.map.generalCoin != null) {
				generalCoinCount = generalCoin + datas.data.map.generalCoin;
				$(".jiayi").html("+" + datas.data.map.generalCoin);
				//执行签到动画
			}
			jiayi(generalCoinCount);
		}
	}, "json");
}
//签到动画
function jiayi(generalCoinCount) {
	$(".jiayi").fadeIn(200);
	$(".qiandao").html("签到中..");
	setTimeout(function() {
		$(".jiayi").fadeOut(500);
		$(".normal-gold").html(generalCoinCount);
		$(".qiandao").css({
			"background": "#f6f1f1",
			"box-shadow": "-1px -2px 1px #ddd inset",
			"color": "#4ea0ff"
		});
		$(".qiandao").html('<a href="integral.html"><span>已签到</span><span class="glyphicon glyphicon-menu-right qiandaoRight"></span></a>');
	}, 1000); //显示1,秒后进行隐藏
}