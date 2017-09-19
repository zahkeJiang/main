
	
$(function(){
	$.post("personal.action",{},function(obj){
    		if (obj.status == "0") {
    			var user = obj.data.userInfo;
    			$("#icon").attr('src',user.headimageurl);
    			$("#nickname").html(user.nickname);
    			if (user.integral>4999) {
    				$(".member_live").html("铂金会员");
    			}else if (user.integral>499) {
					$(".member_live").html("黄金会员");
    			}else if (user.integral>14) {
					$(".member_live").html("白银会员");
    			}else{
    				$(".member_live").html("普通用户");
    			}
                $(".coupons_sum").html(obj.count);
    			$(".member_integral").html(user.memberPoints);
    		}else{
    			alert(error);
    		}
    	},'json');
});