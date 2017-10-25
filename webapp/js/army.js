$(function(){
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


	//娱乐项目
	$("#villa-item-li li:first-child").append("<p class='villa-item-li-traingle'></p>");
    // $(".villa-item-hint").html($("#villa-item-li li:first-child").find("p").attr("text"));
    $("#villa-item-li li").click(function(){
        $(this).append("<p class='villa-item-li-traingle'></p>");
        $(this).siblings("li").find(".villa-item-li-traingle").remove();

        var a =$(this).find("p").attr("text");
        // alert(a)
        $(".villa-item-hint").html(a);
    });
	 //查看评论
    $("#assess").click(function(){
        // type(1为别墅评论 2为驾校 3为军旅)
        window.location.href="assess.html?type=3"; 
    });
});