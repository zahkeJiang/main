$(function(){

    $.post("getGoodComment",{"type":1},function(datas){
        if (datas.status==0) {
            var assessUrl = datas.data.comment;
            //获取星星
            var enterStar = assessUrl.enterStar;//娱乐星星
            var stayStar = assessUrl.stayStar;//住宿星星
            var supportStar = assessUrl.supportStar;//设备星星
            var finishStar = Math.round((enterStar+stayStar+supportStar)/3);//星星四舍五入取整
            console.log(finishStar);
            if (finishStar == 1) {
                $("#star").attr("src","./images/star1.png");
            }else if (finishStar == 2) {
                $("#star").attr("src","./images/star2.png");
            }else if (finishStar == 3) {
                $("#star").attr("src","./images/star3.png");
            }else if (finishStar == 4) {
                $("#star").attr("src","./images/star4.png");
            }else if (finishStar == 5) {
                $("#star").attr("src","./images/star5.png");
            }else{
                $("#star").attr("src","./images/star5.png");
            }
            //匿名与否,0,flase不匿名，1,true是匿名
            var anonymous = assessUrl.anonymous;
            var nickname = assessUrl.nickname;
            if (anonymous==true) {
                nickname=nickname.substring(0,1)+"**"+nickname.charAt(nickname.length - 1);
            }
            //获取时间年月日
            commentTime = assessUrl.commentTime.split(' ')[0];
            $(".assessBox-nickname").html(nickname);
            $(".assessBox-time").html(commentTime);
            $(".assessBox-assess").html(assessUrl.content);
            $("#icon").attr("src",assessUrl.headimageurl);
        }
    },"json");
	//日历渲染--------------------------------------------------------
    renderHtml(0);
    // 表格中显示日期
    showCalendarData(0,0);
    //展示价格
    setPrice(getYandM(0).dataPara);

//    日历结束
//    点击上一月
    var monthNum = 0;
    $('.sit-pre-month').on('click',function(e){
        e.preventDefault();
        // 第二个参数 0为本月 上一个月1，下一个月-1
        ++monthNum;
        showCalendarData(0,monthNum);
        setPrice(getYandM(monthNum).dataPara);
    });
//    点击下一月
    $('.sit-next-month').on('click',function(e){
        e.preventDefault();
        --monthNum;
        showCalendarData(0,monthNum);
        setPrice(getYandM(monthNum).dataPara);
    });
	
	 //查看评价
    // $("#assess").click(function(){
    //     // type(1为别墅评论 2为驾校 3为军旅)
    //     window.location.href="assess.html?type=1"; 
    // });
    //查看评价
    $(".assessBox-view").click(function(){
        // type(1为别墅评论 2为驾校 3为军旅)
        window.location.href="assess.html?type=1"; 
    })
    
});