$(function(){
	     //日历渲染--------------------------------------------------------
    renderHtml(0);
    // 表格中显示日期
    showCalendarData(0,0);
    //展示价格
    setPrice(getYandM(0).dataPara+"");

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
    $("#assess").click(function(){
        // type(1为别墅评论 2为驾校 3为军旅)
        window.location.href="assess.html?type=1"; 
    });
    //查看评价
    $(".assessBox-view").click(function(){
        // type(1为别墅评论 2为驾校 3为军旅)
        window.location.href="assess.html?type=1"; 
    })
     var a ="";
    var rowNum=Math.round($(".assessBox-assess").height()/parseFloat($(".assessBox-assess").css('line-height')));
    console.log(rowNum);
    if (rowNum>3) {
        a=1;
        $(".assessBox-assess").css({"-webkit-line-clamp":"3"});
        $(".assessBox-assess-view").show();
        console.log();
        $(".assessBox-assess-view").click(function(){
            if (a==1) {
                $(".assessBox-assess").css({"-webkit-line-clamp":"1000"});
                $(this).html("收起");
                a=0;
            }else{
                $(".assessBox-assess").css({"-webkit-line-clamp":"3"});
                $(this).html("阅读更多");
                a=1;
            }
        });
    }
});