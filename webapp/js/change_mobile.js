var oldmobile = "";

function ShowMessage() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	oldmobile = getval.split("=")[1];
}
window.onload = ShowMessage();


var newnumber = /^1[3|4|5|8]\d{9}$/;
var number = "";
var mobile = "";
var code = "";
$(function() {
	$("#oldmobile").html(oldmobile);
	$("#btn").click(function() {
		bt();
	});
});

/*发送验证码*/
function setCode(obj) {

	mobile = $("#tel").val(); //获取发送验证码时所输入的电话好号码	
	if (newnumber.test(mobile)) {
		code = "";

		var codeLength = 4; //设置验证码长度 
		var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9); //验证码候选数字
		for (var i = 0; i < codeLength; i++) {
			var charIndex = Math.floor(Math.random() * 10); //向下取整0-10的随机数
			code += selectChar[charIndex];
		}


		$.post("sms.action", {
			"mobile": mobile,
			"tpl_id": 37597,
			"tpl_value": code
		}, function(object) {
			if (object.status == "0") {
				modalHintText("验证码发送成功");
				settime(obj);
			} else {
				modalHintText("系统繁忙，请稍后再试！");
			}
		}, "json");
	} else {
		modalHintText("请输入正确的手机号码")
	}
}
/*发送验证码*/
var countdown = 60;

function settime(obj) {
	if (countdown == 0) {
		obj.removeAttribute("disabled");
		obj.value = "免费获取验证码";
		countdown = 60;
		return;
	} else {
		obj.setAttribute("disabled", true);
		obj.value = "重新发送(" + countdown + ")";
		countdown--;
	}
	setTimeout(function() {
		settime(obj)
	}, 1000);
}
/*确认更改手机号并提交*/
function bt() {
	newcode = document.getElementById("getcode").value; //获取此刻输入框验证码
	number = document.getElementById("tel").value; //获取此刻输入框手机号
	if (newnumber.test(number)) {
		if (mobile != "" && mobile == number) {
			if (code == newcode) {
				$.post("bond.action", {
					"phonenumber": mobile
				}, function(obj) {
					if (obj.status == 0) {
						location.href = "user.html";
					} else {
						modalHintText("手机号已被注册");
					}
				}, "json");
			} else {
				modalHintText("验证码输入错误");
			}
		} else {
			modalHintText("验证码输入错误");
		}
	} else {
		modalHintText("请输入正确的手机号码！");
	}
}