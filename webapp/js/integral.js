$(function(){
	var phone_height = $(window).height();//获取屏幕高度
    $('.integral_record_container').height(($(window).height())-140-16-4-49);//设置元素高度	
   	$.post("selectRecord.action",{},function(data){
   		if (data.status=="0") {
   			var record = "";
            var record_list = data.data.records;
            // $.each循环实现添加订单列表  
            $.each(record_list,function(commentIndex,comment){
                record += '<div class="list"><div class="integral_record_list"><span class="integral_record_type">'+comment.note+'</span><span class="integral_record_count">'+comment.value+'</span><p class="integral_record_time">'+comment.time+'</p></div></div>';
            });
            $('.integral_record_container').empty();
   			$('.integral_record_container').html(record);
   		}else{
   			var record = "<div class='no_record'><img src='images/no_record.png' width='120px'><p>暂无积分记录</p></div>";
   			$('.integral_record_container').html(record);
   		}
   	},"json");
	$.post("personal.action",{},function(obj){
		if (obj.status=="0") {
			var integral = obj.data.userInfo;
			$(".my_integral").html(integral.memberPoints);
		}
	},"json");
	$(".recharge").click(function(){
		window.location.href="recharge.html";
	});
	
});

   