$(function(){
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