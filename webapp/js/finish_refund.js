//var userid = "";
var refund_status="";
function ShowMessage() 
{ 
    var thisURL = document.URL;    
    var getval = thisURL.split('?')[1];  
    refund_status = getval.split("=")[1];  
//    refund_status = getval.split("=")[2]; 
} 
window.onload=ShowMessage(); 


$(function(){
	if (refund_status =="1") {
		var refund_success = "<img src='images/refund_success.png' height='80px' width='80px'><p>退款成功</p>"
		$(".refund_infor").html(refund_success);
	}else{
		var refund_fail = "<img src='images/refund_fail.png' height='80px' width='80px><p>退款失败</p>"
		$(".refund_infor").html(refund_fail);
	}
});