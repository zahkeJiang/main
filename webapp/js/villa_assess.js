$(function(){
	$.post("getComment",{"type":3},function(datas){//请求参数 ：type(1为别墅评论 2为驾校 3为军旅)
		if (datas.status==0) {
			var villa_assess_list = datas.data.Comment;
			var html = "";
			$.each(villa_assess_list,function(commentIndex,comment){
				html+='<div class="assess-header"><div class="assess-header-icon"><img src="'+comment.headimageurl+'" height="56px"></div><div class="assess-header-content"><div class="assess-header-star">'+comment.star+'</div><div class="assess-header-time">'+comment.commentTime+'</div><p class="assess-header-text">'+comment.content+'</p></div></div><div class="assess-pic"><img src="./images/alipay.png" height="56px"><img src="./images/circle.png"  height="56px"><img src="./images/circle.png"  height="56px"></div>'
			});
			$(".assessBox").html(html);
		}else if (datas.status=="-20") {
			$(".assessBox").html("当前没有想过评价");
		}
	},"json");
});