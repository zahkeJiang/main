var liNuber="";
var satisfaction01=";"
var satisfaction02=";"
var satisfaction03=";"
//匿名与否
$(".circle").click(function(){
	if ($(".circle-choose").length > 0) {
		$(".circle-choose").remove();
	}else{
		$(this).append('<div class="circle-choose"></div>');
	}
});
//评价星星
$(".assessBox-row li").click(function(){
	//将当前点击所在的li已取选择状态：星星中间为空
	$(this).siblings("li").find("img").attr("src","./images/starOFF.png");
	//获取当前点击属于第n个li元素
	liNuber = $(this).index()+1;
	if(liNuber==1){
		$(this).parent().siblings(".satisfaction").html("非常差");
	}else if(liNuber == 2){
		$(this).parent().siblings(".satisfaction").html("差");
	}else if(liNuber == 3){
		$(this).parent().siblings(".satisfaction").html("一般");
	}else if(liNuber == 4){
		$(this).parent().siblings(".satisfaction").html("好");
	}else if(liNuber == 5){
		$(this).parent().siblings(".satisfaction").html("非常好");
	}
	//循环将第一个到第liNumber个的li元素进行样式更改
	for (var i = 1; i <= liNuber; i++) {
		$(this).parent().find("li:nth-child("+i+")").find("img").attr("src","./images/starON.png");
	}
});
//提交，如果尚未进行评价，将提示
$(".submit").click(function(){
	satisfaction01= $(".satisfaction01").html();
	satisfaction02= $(".satisfaction02").html();
	satisfaction03= $(".satisfaction03").html();
	if (satisfaction01==""||satisfaction02==""||satisfaction03=="") {
		$(".hint").show();
		setTimeout(function(){$(".hint").hide(1000);},1000);//显示1,秒后进行隐藏
	}else{
		alert("我是评价");
	}
	
});