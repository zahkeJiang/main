//var userid = "";
//function ShowMessage() 
//{ 
//    var thisURL = document.URL;    
//    var getval = thisURL.split('?')[1];  
//    userid = getval.split("=")[1];  
//} 
//window.onload=ShowMessage(); 

var container = "<div class='orders_bg'><div class='bg_hint'><img src='images/no_content.png' height='100px'><p>您当前没有相关订单</p></div></div>";
$(function(){
    all_orders();
	$(".all_orders").click(function(){     
		all_orders();
	});
    $(".orders_pay").click(function(){     
        orders_pay();
    });
	$(".orders_success").click(function(){ 
		orders_success();
	});
	$(".orders_finished").click(function(){
		orders_finished();
	});
    $(".orders_cencer").click(function(){
        orders_cencer();
    });

});

//定义查询全部订单方法
function all_orders(){
    $(".orders_finished").css({"color":"black","border-bottom":"0"});
    $(".orders_success").css({"color":"black","border-bottom":"0"});
    $(".orders_cencer").css({"color":"black","border-bottom":"0"});
    $(".orders_pay").css({"color":"black","border-bottom":"0"});
	$(".all_orders").css({"color":"red","border-bottom":"2px solid red"});

    $.post("selectOrder.action",{},function(obj){
        if (obj.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = obj.data.dsOrders;
            // alert("dsorder_list.userid="+dsorder_list[0].userid+"dsorder_list.dstype="+dsorder_list[0].dstype+"dsorder_list.dsname="+dsorder_list[0].dsname+"dsorder_list.traintime="+dsorder_list[0].traintime+"dsorder_list.orderid="+dsorder_list[0].orderid);
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="1"||comment.orderStatus=="2") {//用户已付款，审核过程
                    var result = "<p class='refund' odnumber='"+comment.orderNumber+"'>取消订单</p>";
                }else if (comment.orderStatus=="3") {//用户报名完成
                    var result = "<p class='result'>材料正在返还</p>";
                }else if (comment.orderStatus=="4") {//用户成功接收返还材料
                    var result = "<p class='result'>已完成</p>";
                }else if (comment.orderStatus=="5") {//用户取消订单
                    var result = "<p class='result'>已取消</p>";
                }
                dsorderh_tml += "<div class='dsorder_list' onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.dsName+"</p>"+result+"</div><div class='dsoder_container' ><img src='"
                                +comment.imageurl+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.dsType+"&nbsp;/&nbsp;"+comment.models+"&nbsp;/&nbsp;"+comment.trainTime+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：¥</span><span class='order_price'>"
                                +comment.orderPrice+".00</span></div></div>";
            });
            $(".container").empty();
            $(".container").html(dsorderh_tml);

            // 为订单列表设置点击事件
            $(".dsoder_container").click(function(){
                var ordernumber= $(this).parents().attr("onumber");  
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

//定义查询未支付订单方法
function orders_pay(){
    $(".all_orders").css({"color":"black","border-bottom":"0"});
    $(".orders_success").css({"color":"black","border-bottom":"0"});
    $(".orders_cencer").css({"color":"black","border-bottom":"0"});
    $(".orders_finished").css({"color":"black","border-bottom":"0"});
    $(".orders_pay").css({"color":"red","border-bottom":"2px solid red"});

    $.post("selectOrder.action",{},function(obj){
        if (obj.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = obj.data.dsOrders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="0") {
                    var result = "<p class='refund' odnumber='"+comment.orderNumber+"'>取消订单</p>";
                    dsorderh_tml += "<div class='dsorder_list'  onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.dsName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.imageurl+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.dsType+"&nbsp;/&nbsp;"+comment.models+"&nbsp;/&nbsp;"+comment.trainTime+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+"</span></div></div>";
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


//定义查询已付款订单方法
function orders_success(){
    $(".all_orders").css({"color":"black","border-bottom":"0"});
    $(".orders_finished").css({"color":"black","border-bottom":"0"});
    $(".orders_cencer").css({"color":"black","border-bottom":"0"});
    $(".orders_pay").css({"color":"black","border-bottom":"0"});
    $(".orders_success").css({"color":"red","border-bottom":"2px solid red"});

    $.post("selectOrder.action",{},function(obj){
        if (obj.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = obj.data.dsOrders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="1"||comment.orderStatus=="2") {
                    var result = "<p class='refund' odnumber='"+comment.orderNumber+"'>取消订单</p>";
                    dsorderh_tml += "<div class='dsorder_list'  onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.dsName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.imageurl+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.dsType+"&nbsp;/&nbsp;"+comment.models+"&nbsp;/&nbsp;"+comment.trainTime+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+"</span></div></div>";
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
    $(".all_orders").css({"color":"black","border-bottom":"0"});
    $(".orders_success").css({"color":"black","border-bottom":"0"});
    $(".orders_cencer").css({"color":"black","border-bottom":"0"});
    $(".orders_pay").css({"color":"black","border-bottom":"0"});
    $(".orders_finished").css({"color":"red","border-bottom":"2px solid red"});

    $.post("selectOrder.action",{},function(obj){
        
        if (obj.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = obj.data.dsOrders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="4") {//用户订单完成以及材料返还成功
                    var result = "<p class='result'>已完成</p>";
                    dsorderh_tml += "<div class='dsorder_list' onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.dsName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.imageurl+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.dsType+"&nbsp;/&nbsp;"+comment.models+"&nbsp;/&nbsp;"+comment.trainTime+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+"</span></div></div>";
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

    $(".orders_finished").css({"color":"black","border-bottom":"0"});
    $(".orders_success").css({"color":"black","border-bottom":"0"});
    $(".all_orders").css({"color":"black","border-bottom":"0"});
    $(".orders_pay").css({"color":"black","border-bottom":"0"});
    $(".orders_cencer").css({"color":"red","border-bottom":"2px solid red"});

    $.post("selectOrder.action",{},function(obj){ 
        if (obj.status=="0") {
            var dsorderh_tml = "";
            var dsorder_list = obj.data.dsOrders;
            // $.each循环实现添加订单列表  
            $.each(dsorder_list,function(commentIndex,comment){
                if (comment.orderStatus=="5") {//用户取消订单
                    var result = "<p class='result'>已取消</p>";
                    dsorderh_tml += "<div class='dsorder_list' onumber='"+comment.orderNumber+"'><div class='dsorder_titie'><p class='ds_name'>"
                                +comment.dsName+"</p>"+result+"</div><div class='dsoder_container'><img src='"
                                +comment.imageurl+"' height='48px' width='64px'><p class='dsorder_information'>"
                                +comment.dsType+"&nbsp;/&nbsp;"+comment.models+"&nbsp;/&nbsp;"+comment.trainTime+"</p></div><div class='dsorder_footer'><span class='dsorder_pay'>实付款：</span><span class='order_price'>¥"
                                +comment.orderPrice+"</span></div></div>";
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