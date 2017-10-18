var type = "";
function ShowMessage() { 
	var thisURL = document.URL;     
	var getval = thisURL.split('?')[1];
	type = getval.split("=")[1];
} 
window.onload=ShowMessage(); 
var html = "";
$(function(){
	// type(1为别墅评论 2为驾校 3为军旅)
	if (type==1) {
		$("body").css({"background":"#B4DADA"});
	}else if (type==3) {
		$("body").css({"background":"#B4DADA"});
	}
	
	$.post("getComment",{"type":type},function(datas){//请求参数 ：type(1为别墅评论 2为驾校 3为军旅)
		if (datas.status==0) {
			var villa_assess_list = datas.data.Comment;
			html = "";
			var commentTime = "";
			var star = "";
			var anonymous = "";
			var nickname = "";
			$.each(villa_assess_list,function(commentIndex,comment){
				//获取星星
				var enterStar = comment.enterStar;//娱乐星星
				var stayStar = comment.stayStar;//住宿星星
				var supportStar = comment.supportStar;//设备星星
				var finishStar = Math.round((enterStar+stayStar+supportStar)/3);//星星四舍五入取整
				console.log(finishStar);
				if (finishStar == 1) {
					star = '<img src="./images/star1.png" height="16px">';
				}else if (finishStar == 2) {
					star = '<img src="./images/star2.png" height="16px">';
				}else if (finishStar == 3) {
					star = '<img src="./images/star3.png" height="16px">';
				}else if (finishStar == 4) {
					star = '<img src="./images/star4.png" height="16px">';
				}else if (finishStar == 5) {
					star = '<img src="./images/star5.png" height="16px">';
				}else{
					star = '<img src="./images/star5.png" height="16px">';
				}
				//匿名与否,0,flase不匿名，1,true是匿名
				anonymous = comment.anonymous;
				nickname = comment.nickname;
				if (anonymous==true) {
					nickname=nickname.substring(0,1)+"**"+nickname.charAt(nickname.length - 1);
				}
				//获取时间年月日
				commentTime = comment.commentTime.split(' ')[0];
				//获取用户上传的几张照片
				console.log(comment.picture);
				if (comment.picture==""||comment.picture==null) {
					pictureUrl="";
				}else{
					var picture = comment.picture.split(","); 
					var pictureUrl="";
					var pictureList = "";
					for (var i = 0;i<picture.length; i++) {
						pictureList +='<img src="/gzh/CommentImg/'+picture[i]+'" height="56px" width="56px">';
					}
					pictureUrl = '<div class="assess-pic">'+pictureList+'</div>';
				}
				
				//将用户数据循环添加
				html+='<div class="assessBox"><div class="username">'
					+nickname+'</div><div class="assess-header"><div class="assess-header-icon"><img src="'
					+comment.headimageurl+'" height="56px"></div><div class="assess-header-content"><div class="assess-header-star">'
					+star+'</div><div class="assess-header-time">'
					+commentTime+'</div><p class="assess-header-text">'
					+comment.content+'</p></div></div>'
					+pictureUrl+'</div>'
			});
			$(".main").html(html);
		}else if (datas.status=="-20") {
			html  = "<div class='assess_bg'><div class='bg_hint'><img src='images/no_content.png' height='100px'><p>当前没有相关评论</p></div></div>";
			$(".main").html(html);
		}
	},"json");
});