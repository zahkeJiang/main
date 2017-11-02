var orderNumber = "";
function ShowMessage() 
{ 
   var thisURL = document.URL;    
   var getval = thisURL.split('?')[1];  
   orderNumber = getval.split("=")[1];  
} 
window.onload=ShowMessage(); 

$(function(){
    //发送请求判断支付完成与否
    $.post("queryOrder.action",{"ordernumber":orderNumber},function(obj){
        if(obj.status=="0"){
            var order_number = obj.out_trade_no;
            var ds_price = obj.price;
            $(".order_number").html(orderNumber);
            $(".ds_price").html(ds_price);
            $(".pay_success").show();
            $(".view_order").click(function(){
                window.location.href="orderInformation.html?ordernumber="+order_number;
            });
        }else if (obj.status=="-30") {
            $(".pay_fail").show();
        }
    },"json");
});
