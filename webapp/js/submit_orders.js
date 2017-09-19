var userid = "";
var dsname = "";
var dstype = "";
var models = "";
var price = "";
var packageid = "";
var traintime = "";
var coupons_sum = "";

function ShowMessage() { 
    var thisURL = decodeURI(location.href);    
    var getval = thisURL.split('?')[1];
    dsname = getval.split("=")[1].split('&')[0];
    dstype = getval.split("=")[2].split('&')[0];
    models = getval.split("=")[3].split('&')[0];
    price = getval.split("=")[4].split('&')[0];
    packageid = getval.split("=")[5].split('&')[0];
    traintime = getval.split("=")[6];
    getId();
} 
window.onload=ShowMessage(); 
var realname="";
var address = "";
var note = "";
var select="";

//获取用户id
function getId(){
	$.post("getid.action",{},function(obj){
		if (obj.status=="0"){
			userid = obj.data.userid;
		}else{
			alert(obj.msg);
		}
	},'json');
}
//获取优惠券金额
function get_coupons(){
	$.ajax({
        type:"POST",
        url:"queryCoupon.action",
        dataType:"text",
        success:function(data){
			var obj = eval('(' + data + ')');
			if (obj.status=="0") {
				coupons_sum = obj.data.price;
				$(".coupons span").html(coupons_sum+"元&nbsp;&gt;");
				$(".coupons span").css({"color":"red"});
				select ="1";
				$(".price").html((price-coupons_sum)+".00");
			}else{
				$(".coupons span").html("无可用优惠券&nbsp;&gt;");
				select ="0";
				$(".price").html(price+".00");
			}
        }	
    });
}
//获取用户手机号
function get_tel(){
	$.post("personal.action",{},function(obj){
		if (obj.status=="1") {
			var user = obj.data.userInfo;
			$(".tel").html(user.phoneNumber);
		}
	},"json");
}
$(function(){
	//为radio法培方式设置点击样式
	$("input[type='radio']").click(function() {
    	$(this).siblings(".user-defined").children("span").addClass("active");
    	$(this).parents("div").siblings("div").find("span").removeClass("active");
	});
	$(".ds_type").html(dstype);
	$(".dstype_information_content").html(dsname+"&nbsp;&frasl;&nbsp;"+models+"&nbsp;&frasl;&nbsp;"+traintime);
	$(".ds_price").html(price+".00");

	get_tel();//获取用户手机号
	get_coupons();//获取优惠券金额
	//点击进入优惠券页面
	$(".coupons").click(function(){
		window.location.href="coupon.html";
	});
	//提交订单支付宝支付
	$(".submit").click(function(){
		//获取用户姓名、联系方式、性别、地址
		realname = $("#real_name").val();
		address = $("#address").val();
		note = $("input[type='radio']:checked").val();
		if (realname=="") {
			alert("请输入您的真实姓名");
		}else if (address == "") {
			alert("请输入您的地址");
		}else if (note == null) {
			alert("请选择您的法培方式");
		}else {
    		$.post("note.action",{"realname":realname,"address":address,"note":note},function(obj){
    			if (obj.status=="0") {
        			window.location.href="determine_browser.html?userid="+userid+"&packageid="+packageid+"&select="+select;	
        		}else{
        			alert("您已报名成功，请勿重复报名。");
        		}
    		},"json");
		}
	});
});