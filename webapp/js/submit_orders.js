
// var dsname = "";
// var dstype = "";
// var models = "";
// var price = "";
// var packageid = "";
// var traintime = "";
// var coupons_sum = "";//优惠券金额

// function ShowMessage() { 
//     var thisURL = decodeURI(location.href);    
//     var getval = thisURL.split('?')[1];
//     dsname = getval.split("=")[1].split('&')[0];
//     dstype = getval.split("=")[2].split('&')[0];
//     models = getval.split("=")[3].split('&')[0];
//     price = getval.split("=")[4].split('&')[0];
//     packageid = getval.split("=")[5].split('&')[0];
//     traintime = getval.split("=")[6];
// } 
// window.onload=ShowMessage(); 
// 
var dsname = $.cookie("dsname");//驾校名字
var dstype = $.cookie("dstype");//班型套餐
var models = $.cookie("models");//班型类别，如C1，C2
var price = $.cookie("price");//班型套餐价格
var packageid = $.cookie("packageid");//班型编号id
var traintime = $.cookie("traintime");//训练时间
var coupons_sum = "";//优惠券金额


var realname="";
var address = "";
var note = "";
var select="";


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
				$(".coupons span").html("¥"+coupons_sum);//优惠券价格
				$(".coupons span").css({"color":"orange"});
				select ="1";
				$(".price").html("¥"+(price-coupons_sum));//需支付金额
			}else{
				$(".coupons span").html("无可用优惠券&nbsp;&gt;");
				select ="0";
				$(".price").html("¥"+price);//需支付金额
			}
        }	
    });
}
//获取用户手机号
function get_tel(){
	$.post("personal.action",{},function(obj){
		if (obj.status=="0") {
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
	$(".ds_price").html(price);

	get_tel();//获取用户手机号
	get_coupons();//获取优惠券金额
	//点击进入优惠券页面
	// $(".coupons").click(function(){
	// 	window.location.href="coupon.html";
	// });
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
    		$.post("note.action",{"realname":realname,"address":address,"note":note},function(datas){//保存用户信息
    			if (datas.status=="0") {//代表用户目前没有驾校的完成订单
    				$(".layer").show();
            		$(".payBox").show(); 
        		}else if (datas.status == "-20"){
        			alert("每位用户仅能一次报名学车，为避免重复报名，请勿下单。");
        		}
    		},"json");
		}
	});

//选择支付方式后点击确认
    $(".toPay").click(function(){
        var payMode = $("input[name='payMode-radio']:checked").val();
        console.log(payMode);
        if (payMode=="JD") {//京东支付
            $.post("createOrder.action",{"packageid":packageid,"select":select},function(datas){//创建驾校报名订单
                if (datas.status==0) {
                    var ordernumber = datas.data.ordernumber;
                    console.log(ordernumber);
                    $(".layer").hide();
                    $(".payBox").hide(); 
                    //京东支付请求
                    $.post("JDPay",{"ordernumber":ordernumber},function (data) {
                        if(data.status == 0){
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
                    },'json');
                }else if (datas.status == "-40") {
                    alert("您当前已存在多个未支付订单，请勿重复多次下单。")
                }
            },"json");   
        }else if (payMode=="aliPay") {//支付宝支付
            $.post("createOrder.action",{"packageid":packageid,"select":select},function(datas){//创建驾校报名订单
                if (datas.status==0) {
                    var ordernumber = datas.data.ordernumber;
                    console.log(ordernumber);
                    $(".layer").hide();
                    $(".payBox").hide(); 
                    window.location.href="payHint.html?ordernumber="+ordernumber;
                      
                }else if (datas.status == "-40") {
                    alert("您当前已存在多个未支付订单，请勿重复多次下单。")
                } 
           
            },"json");   
        }   
    });

//为支付方式的radio设置自定义点击样式
    $(".payMode").click(function() {
        $(this).children(".payModeImg").css({"background":"url(./images/circle_choose.png)","background-size":"20px"});
        $(this).parents().siblings("label").find(".payModeImg").css({"background":"url(./images/circle.png)","background-size":"20px"});
    });
//关闭弹窗
    $(".payBox p").click(function(){
        $(".layer").hide();
        $(".payBox").hide();
    });
});