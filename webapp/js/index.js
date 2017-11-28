/*
$(function(){
    $(".address").click(function(){
        alert("正在开拓其他城市");
    });
    //获取驾校列表
    $.post("sdi.action",{},function(obj){
        $(".container").empty();
        var html = "";
        if(obj.status=="0"){
            var ds_list = obj.data.dspInfolist;
            // $.each循环实现添加  
            $.each(ds_list,function(commentIndex,comment){
                html += "<div class='ds_list'><img src="+" ' "
                    +comment.dsImage+" ' "+" height='60px' width='80px'><div class='information'><div class='ds_name_price'> <span class='ds_name'>"
                    +comment.dsName+"</span><span class='ds_price'></span></div><span class='ds_address'>"
                    +comment.address+"</span></div></div>";
            });
            $(".container").html(html);

            //为驾校列表注册点击事件
            $(".ds_list").click(function(){                       
                var dsname = $(this).find(".ds_name").html();                          
                var myurl="ds_information.html?dsname="+dsname;
                window.location.assign(encodeURI(myurl));
            }); 
        }
    },'json');
});*/
$(function() {
    // $.post("sdi.action", {}, function(datas) {
    //     if (datas.status == 0) {
    //         var dsNames = datas.data.dsNames;
    //         var dsNameListHtml = "";
    //         for (var i = 0; i < dsNames.length; i++) {
    //             dsNameListHtml += '<div class="swiper-slide"><div class="content"><p>海淀驾校</p><img src="./images/index_content2.png" class="images2"><div class="buttonClick" jiaxiao="海淀驾校"><img src="./images/index_content3.png" class="images3"></div></div></div>'
    //         }
    //         $(".swiper-wrapper").html(dsNameListHtml);
    //     }
    // }, "json");
    $(".buttonClick").click(function() {
        var dsname = $(this).attr("jiaxiao");
        $.cookie("dsname", dsname); //驾校名字   
        console.log(dsname);
        window.location.href = "ds_information.html";
    });


});