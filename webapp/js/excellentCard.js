$(function(){
	$(".apply").click(function(){
		$(".layer").show();
		$(".infoBox").show();
	});
	$(".submitAplly").click(function(){
		var telNumber = /^1[3|4|5|8]\d{9}$/;
		var userName = $("#userName").val();
		var old = $("#old").val();
		var school = $("#school").val();
		var tel = $("#tel").val();
		var sexSelect = $("#sexSelect option:selected").val();
		console.log(userName,old,school,tel,sexSelect);
		if (userName!="" && old!="" && school!="" && tel!="" && sexSelect!="") {
			if (telNumber.test(tel)) {
				$.post("applyCard",{"reacName":userName,"age":old,"school":school,"mobile":tel,"gender":sexSelect},function(datas){
					if (datas.status==0) {
						alert(提交成功，请等待审核);
						window.location.href="excellent.html";
					}
				},"json");
			}else{
				alert("手机号格式有误，请重新输入");
			}
		}else{
			alert("请完善您的信息");
		}
	});
});