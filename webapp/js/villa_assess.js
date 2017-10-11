$(function(){
	$.post("getComment",{"type":3},function(datas){//请求参数 ：type(1为别墅评论 2为驾校 3为军旅)
		if (datas.status==0) {
			var villa_assess_list = datas.data.Comment;
			var html = "";
			var pictureUrl="";
			var commentTime = "";
			var star = "";
			$.each(villa_assess_list,function(commentIndex,comment){
				//获取星星
				if (comment.star == 1) {
					star = '<img src="./images/star1.png" height="16px">';
				}else if (comment.star == 2) {
					star = '<img src="./images/star2.png" height="16px">';
				}else if (comment.star == 3) {
					star = '<img src="./images/star3.png" height="16px">';
				}else if (comment.star == 4) {
					star = '<img src="./images/star4.png" height="16px">';
				}else if (comment.star == 5) {
					star = '<img src="./images/star5.png" height="16px">';
				}
				//获取时间年月日
				commentTime = comment.commentTime.split(' ')[0];
				//获取用户上传的几张照片
				// pictureUrl = comment.picture;
				pictureUrl = '<div class="assess-pic"><img src="./images/alipay.png" height="56px"><img src="./images/alipay.png"  height="56px"><img src="./images/circle.png"  height="56px"></div>';
				//将用户数据循环添加
				html+='<div class="assessBox"><div class="assess-header"><div class="assess-header-icon"><img src="'
					+comment.headimageurl+'" height="56px"></div><div class="assess-header-content"><div class="assess-header-star">'
					+star+'</div><div class="assess-header-time">'
					+commentTime+'</div><p class="assess-header-text">'
					+comment.content+'</p></div></div>'
					+pictureUrl+'</div>'
			});
			$(".main").html(html);
		}else if (datas.status=="-20") {
			$(".main").html("当前没有想过评价");
		}
	},"json");
});