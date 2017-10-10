

// var dsname = "";
// function ShowMessage() { 
//     var thisURL = decodeURI(location.href);    
//     var getval = thisURL.split('?')[1];
//     dsname = getval.split("=")[1];
// } 
// window.onload=ShowMessage(); 
var dsname = $.cookie("dsname");//驾校名字			
$(function(){

    //发送post请求，获取班型列表
    $.post("sdp.action",{"dsname":dsname},function(result){
        $(".container").empty();
        // if (obj.status=="1") {
            var dshtml = "";
            var obj = result.data.dsInfos;
            var dsp_list = obj.dspList;

            //添加驾校图片/名称/简介/地址
            // var ds_image = "<img id='ds_image' src="+" ' "+obj.dsImage+" ' "+"><span class='ds_name'></span>";
            // $(".ds_image").html(ds_image);
            $(".ds_name").html(obj.dsName);
            $(".dsintro_name").html(obj.dsName);
            $(".dsintro p").html(obj.dsIntro);
            $(".address").html(obj.address);


             // $.each循环实现添加  
            $.each(dsp_list,function(commentIndex,comment){
                dshtml += "<div class='dsp_list'><h2 class='dstype'>"
                +comment.dsType+"</h2><div class='dsp_infor'><span class='models'>"
                +comment.models+"</span><span class='traintime'>"
                +comment.trainTime+"</span><span class='description'>"
                +comment.description+"</span><span class='packageid'>"
                +comment.packageid+"</span></div><span class='priceDiv'><span  class='price_symbol'>¥</span><span class='price'>"
                +comment.price+"</span><span class='pointzero'>.00</span></span><div class='line'></div></div>";
            });
            $(".container").html(dshtml);

            // 为班型列表设置点击事件
             $(".dsp_list").click(function(){    
                var dstype = $(this).find(".dstype").html(); 
                var models = $(this).find(".models").html(); 
                var price = $(this).find(".price").html(); 
                var traintime = $(this).find(".traintime").html();  
                var packageid = $(this).find(".packageid").html();   
                var description = $(this).find(".description").html();
                // var myurl="ds_apply.html?dsname="+dsname+"&dstype="+dstype+"&models="+models+"&price="+price+"&packageid="+packageid+"&traintime="+traintime+"&description="+description;                                      
                // window.location.assign(encodeURI(myurl));
                
                $.cookie("dstype",dstype);//班型
                $.cookie("models",models);//班型类别，如C1，C2
                $.cookie("price",price);//班型价格
                $.cookie("traintime",traintime);//训练时间
                $.cookie("packageid",packageid);//班型编号id
                $.cookie("description",description);//班型秒速
                window.location.href="ds_apply.html";
            });
        // }
    },'json');

    //驾照类型、班型、学习时间等选择
    $(".dsp_type_div").click(function(){
        if ($(this).siblings("p").css("display")=="none") {
            $(this).find("span").css({"color":"white"});
            $(this).parent().css({"border":"1px solid #ffc3a9"});
            $(this).siblings("p").show();
            $(this).css({"background":"#FFC3A9"});
            $(this).find("img").attr("src","./images/top.png");
        }else{
            $(this).find("span").css({"color":"black"});
            $(this).parent().css({"border":"1px solid #f6f1f1"});
            $(this).siblings("p").hide();
            $(this).css({"background":"#f6f1f1"});
            $(this).find("img").attr("src","./images/bottom.png");
        }
    });
});
