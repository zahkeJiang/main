
var container = "<div class='orders_bg'><div class='bg_hint'><img src='images/no_content.png' height='100px'><p>您当前没有相关订单</p></div></div>";
$(function(){
    all_orders();
	$(".all_orders").click(function(){   
        $(this).css({"color":"orange","border-bottom":"2px solid orange"});
        $(this).siblings("p").css({"color":"black","border-bottom":"0"});  
		all_orders();
	});
    $(".orders_pay").click(function(){     
        $(this).css({"color":"orange","border-bottom":"2px solid orange"});
        $(this).siblings("p").css({"color":"black","border-bottom":"0"});  
        orders_pay();
    });
	$(".orders_success").click(function(){ 
        $(this).css({"color":"orange","border-bottom":"2px solid orange"});
        $(this).siblings("p").css({"color":"black","border-bottom":"0"}); 
		orders_success();
	});
	$(".orders_finished").click(function(){
        $(this).css({"color":"orange","border-bottom":"2px solid orange"});
        $(this).siblings("p").css({"color":"black","border-bottom":"0"}); 
		orders_finished();
	});
    $(".orders_cencer").click(function(){
        $(this).css({"color":"orange","border-bottom":"2px solid orange"});
        $(this).siblings("p").css({"color":"black","border-bottom":"0"}); 
        orders_cencer();
    });

});

//定义查询全部订单方法
function all_orders(){
    $.post("orderList",{},function(datas){
        if (datas.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = datas.data.orders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                var order_assess = "";
                if (comment.orderStatus=="0") {//用户未支付订单
                    var result = "<p class='topay' odnumber='"+comment.orderNumber+"'>待付款</p>";
                }else if (comment.orderStatus=="1"||comment.orderStatus=="2") {//用户已付款，审核过程
                    var result = "<p class='refund' odnumber='"+comment.orderNumber+"'>取消订单</p>";
                }else if (comment.orderStatus=="3") {//用户报名完成
                    var result = "<p class='result'>材料正在返还</p>";
                }else if (comment.orderStatus=="4") {//用户成功接收返还材料
                    var result = "<p class='result'>已完成</p>";
                    order_assess = "<span class='order_assess'>评价</span>|";
                }else if (comment.orderStatus=="5"||comment.orderStatus=="6") {//用户取消订单
                    var result = "<p class='result'>已取消</p>";
                }
                dsorderh_tml += "<div class='dsorder_list' onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.orderName+"</p>"+result+"</div><div class='dsoder_container' ><img src='"
                                +comment.orderImage+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.orderDescripe+"</p></div><div class='dsorder_footer'>"+order_assess+"<span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+".00</span></div></div>";
            });
            $(".container").empty();
            $(".container").html(dsorderh_tml);

            // 为订单列表设置点击事件
            $(".dsoder_container").click(function(){
                var ordernumber= $(this).parents().attr("onumber");  
                window.location.href="order_information.html?ordernumber="+ordernumber;
            });
            //未支付订单付款
            // $(".topay").click(function(){
            //     var ordernumber = $(this).attr("odnumber");
            //     window.location.href="determine_browser.html?ordernumber="+ordernumber;
            // });
            //退款
            $(".refund").click(function(){
                var ordernumber = $(this).attr("odnumber");
                window.location.href="ds_refund.html?ordernumber="+ordernumber;
            });
            //评价
            $(".order_assess").click(function(){
                window.location.href="assessSet.html";
            });
        }else{
            $(".container").empty();
            $(".container").html(container);
        }
    },"json");
}

//定义查询未支付订单方法
function orders_pay(){

    $.post("orderList",{},function(datas){
        if (datas.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = datas.data.orders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="0") {
                    var result = "<p class='topay' odnumber='"+comment.orderNumber+"'>待付款</p>";
                    dsorderh_tml += "<div class='dsorder_list'  onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.orderName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.orderImage+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.orderDescripe+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>需付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+".00</span></div></div>";
                }
            });
            //遍历之后判断订单列表是否依旧为空，如果是空，显示其他内容
            if (dsorderh_tml=="") {
                dsorderh_tml=container;
            }
            $(".container").empty();
            $(".container").html(dsorderh_tml);

            //未支付订单付款
            // $(".topay").click(function(){
            //     var ordernumber = $(this).attr("odnumber");
            //     window.location.href="determine_browser.html?ordernumber="+ordernumber;
            // });
            // 为订单列表设置点击事件
            $(".dsoder_container").click(function(){  
                var ordernumber = $(this).parents().attr("onumber");
                window.location.href="order_information.html?ordernumber="+ordernumber;
            });
        }else{
            $(".container").empty();
            $(".container").html(container);
        }
    },"json");
}


//定义查询已付款订单方法
function orders_success(){

    $.post("orderList",{},function(datas){
        if (datas.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = datas.data.orders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="1"||comment.orderStatus=="2"||comment.orderStatus=="3"){
                    if (comment.orderStatus=="1"||comment.orderStatus=="2") {
                        var result = "<p class='refund' odnumber='"+comment.orderNumber+"'>取消订单</p>";
                    
                    }else if (comment.orderStatus=="3") {
                        var result = "<p class='result'>材料正在返还</p>";
                    }
                    dsorderh_tml += "<div class='dsorder_list'  onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.orderName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.orderImage+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.orderDescripe+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+".00</span></div></div>";                                 
                }
            });
            //遍历之后判断订单列表是否依旧为空，如果是空，显示其他内容
            if (dsorderh_tml=="") {
                dsorderh_tml=container;
            }
            $(".container").empty();
            $(".container").html(dsorderh_tml);
            
            // 为订单列表设置点击事件
            $(".dsoder_container").click(function(){  
                var ordernumber = $(this).parents().attr("onumber");
                window.location.href="order_information.html?ordernumber="+ordernumber;
            });
            $(".refund").click(function(){
                var ordernumber = $(this).attr("odnumber");
                window.location.href="ds_refund.html?ordernumber="+ordernumber;
            });
        }else{
            $(".container").empty();
            $(".container").html(container);
        }
    },"json");
}

//定义查询已完成订单方法
function orders_finished(){
    $.post("orderList",{},function(datas){
        if (datas.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = datas.data.orders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="4") {//用户订单完成以及材料返还成功
                    var result = "<p class='result'>已完成</p>";
                    var  order_assess = "<span class='order_assess'>评价</span>|";
                    dsorderh_tml += "<div class='dsorder_list' onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.orderName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.orderImage+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.orderDescripe+"</p></div><div class='dsorder_footer'>"+order_assess+"<span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+".00</span></div></div>";
                }
            });
            //遍历之后判断订单列表是否依旧为空，如果是空，显示其他内容
            if (dsorderh_tml=="") {
                dsorderh_tml=container;
            }
            $(".container").empty();
            $(".container").html(dsorderh_tml);

            // 为订单列表设置点击事件
            $(".dsoder_container").click(function(){  
                var ordernumber = $(this).parents().attr("onumber");
                window.location.href="order_information.html?ordernumber="+ordernumber;
            });
        }else{
            $(".container").empty();
            $(".container").html(container);
        }
    },"json");
}



//定义查询已取消订单方法
function orders_cencer(){
    $.post("orderList",{},function(datas){ 
        if (datas.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = datas.data.orders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="5"||comment.orderStatus=="6") {//用户取消订单
                    var result = "<p class='result'>已取消</p>";
                    dsorderh_tml += "<div class='dsorder_list' onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.orderName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.orderImage+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.orderDescripe+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+".00</span></div></div>";
                }
            });
            //遍历之后判断订单列表是否依旧为空，如果是空，显示其他内容
            if (dsorderh_tml=="") {
                dsorderh_tml=container;
            }

            $(".container").empty();
            $(".container").html(dsorderh_tml);

            // 为订单列表设置点击事件
            $(".dsoder_container").click(function(){  
                var ordernumber = $(this).parents().attr("onumber");
                window.location.href="order_information.html?ordernumber="+ordernumber;
            });
        }else{
            $(".container").empty();
            $(".container").html(container);
        }
    },"json");
}