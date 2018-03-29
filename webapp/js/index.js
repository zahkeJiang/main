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
    $(".ds_Recommended").click(function() {
        window.location.href = "ds_Recommended.html";
    });

    $.post("sdi.action", {}, function(datas) {
        if (datas.status == 0) {
            var dsNames = datas.data.dsNames;
            var dsNameListHtml = "";
            $.each(dsNames, function(commentIndex, comment) {
                dsNameListHtml += '<div class="swiper-slide"><div class="content"><p class="ds_name">' + comment.ds_name + '</p><p class="description">' + comment.description + '</p><div class="buttonClick" jiaxiao="' + comment.ds_name + '"><img src="./images/index_content3.png" class="images3" /></div></div></div>'
            });

            $(".swiper-wrapper").html(dsNameListHtml);
            doPlace();
            $(".buttonClick").click(function() {
                var dsname = $(this).attr("jiaxiao");
                $.cookie("dsname", dsname); //驾校名字   
                console.log(dsname);
                window.location.href = "ds_information.html";
            });
        }
    }, "json");



    function doPlace() {
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            // spaceBetween: 10,//幻灯片间隔
            // nextButton: '.swiper-button-next',
            // prevButton: '.swiper-button-prev',
            paginationClickable: true,
            // effect: 'coverflow',
            grabCursor: true,
            centeredSlides: true,
            slidesPerView: 'auto',
            parallax: true,
            // coverflow: {
            //     rotate: 50,
            //     stretch:10,
            //     depth:-10,
            //     modifier:1,
            //     slideShadows : true
            // }
        });
    }


});