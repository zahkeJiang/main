// var dsname = "";
// function ShowMessage() { 
//     var thisURL = decodeURI(location.href);    
//     var getval = thisURL.split('?')[1];
//     dsname = getval.split("=")[1];
// } 
// window.onload=ShowMessage(); 
var dsname = $.cookie("dsname"); //驾校名字	
var models = "";
var dsType = "";
var trainTime = "";
var dsInfo = "";
var reservation = "";
var DsAddress = "";
$(function() {
    //获取驾校地址，轮播图，简介
    $.post("sdp.action", {
        "dsName": dsname
    }, function(datas) {
        if (datas.status == 0) {
            var imagesList = datas.data.dsInfos.dsImage;
            var imagesHtml = "";
            var picture = "";
            if (imagesList) {
                picture = imagesList.split(",");
            }
            dsInfo = datas.data.dsInfos;
            $(".ds_information_text").html(dsInfo.dsIntro);
            $(".dsAddress").html(dsInfo.address);
            gogo(picture); //动态加载轮播图
            DsAddress = dsInfo.address;
            getLocal(dsInfo.address); //显示地图
        }
    }, "json");


    getGoodComment(); //查看评价

    $(".assessBox-view").click(function() {
        // type(1为别墅评论 2为驾校 3为军旅)
        window.location.href = "assess.html?type=2";
    })
    getDs();
    //点击屏幕，如果元素不是dsp_type_div，则执行navStyle();
    $(document).click(function() {
        navStyle();
    });
    $(".dsp_type_div").click(function(event) {
        event.stopPropagation();
    });

    //驾照类型、班型、学习时间等选择
    $(".dsp_type_div").click(function() {
        $(this).parents("div").siblings(".dsp_type_Div").find("img").attr("src", "./images/bottom.png");
        $(this).parents("div").siblings(".dsp_type_Div").find("span").css({
            "color": "#555"
        })
        $(this).parents("div").siblings(".dsp_type_Div").find("div").css({
            "background": "#f6f1f1"
        });
        $(this).parents("div").siblings(".dsp_type_Div").find(".dsp_type_Div_a").css({
            "border": "1px solid #f6f1f1"
        });
        $(this).parents("div").siblings(".dsp_type_Div").find("p").hide();
        // $(this).find("span").css({"color":"#555"});
        // $(this).find("div").css({"background":"#f6f1f1"});
        // $(this).css({"border":"1px solid #f6f1f1"});
        // $(this).find("img").attr("src","./images/bottom.png");
        // navStyle();
        if ($(this).siblings("p").css("display") == "none") {
            // 当前点击的菜单样式改变
            $(this).find("span").css({
                "color": "white"
            });
            $(this).parent().css({
                "border": "1px solid #ffc3a9"
            });
            $(this).siblings("p").show();
            $(this).css({
                "background": "#FFC3A9"
            });
            $(this).find("img").attr("src", "./images/top.png");
        } else {
            $(this).find("span").css({
                "color": "#555"
            });
            $(this).parent().css({
                "border": "1px solid #f6f1f1"
            });
            $(this).siblings("p").hide();
            $(this).css({
                "background": "#f6f1f1"
            });
            $(this).find("img").attr("src", "./images/bottom.png");
        }
    });

    $(".dsp_type_Div_models p").click(function() {
        if ($(this).attr("text") == "") {
            $(this).siblings(".dsp_type_div").find("span").html("驾考类型");
        } else {
            $(this).siblings(".dsp_type_div").find("span").html($(this).attr("text"));
        }

        $(this).siblings(".dsp_type_div").find("span").css({
            "font-size": "14px",
            "color": "#555"
        });
        models = $(this).attr("text");
        console.log(models);
        getDs();

    });
    $(".dsp_type_Div_dsType p").click(function() {
        if ($(this).attr("text") == "") {
            $(this).siblings(".dsp_type_div").find("span").html("班型");
        } else {
            $(this).siblings(".dsp_type_div").find("span").html($(this).attr("text"));
        }

        $(this).siblings(".dsp_type_div").find("span").css({
            "font-size": "14px",
            "color": "#555"
        });
        reservation = $(this).attr("text");
        console.log(reservation);
        getDs();
    });
    $(".dsp_type_Div_trainTime p").click(function() {
        if ($(this).attr("text") == "") {
            $(this).siblings(".dsp_type_div").find("span").html("学习时间");
        } else {
            $(this).siblings(".dsp_type_div").find("span").html($(this).attr("text"));
        }
        $(this).siblings(".dsp_type_div").find("span").css({
            "font-size": "14px",
            "color": "#555"
        });

        trainTime = $(this).attr("text");
        console.log(trainTime);
        getDs();
    });
    $(".villa-map-address").click(function() {
        getLocal(DsAddress);
    });


});

//地图显示
function getLocal(address) {
    var map = new AMap.Map('villa-map-view', {
        resizeEnable: true,
        zoom: 13,
        center: [116.39, 39.9]
    });
    AMap.plugin('AMap.Geocoder', function() {
        var geocoder = new AMap.Geocoder({
            city: "010" //城市，默认：“全国”
        });
        var marker = new AMap.Marker({
                map: map,
                bubble: true
            })
            // var address = "海淀区苏家坨北安河河滩69号";
        geocoder.getLocation(address, function(status, result) {
            if (status == 'complete' && result.geocodes.length) {
                marker.setPosition(result.geocodes[0].location);
                map.setCenter(marker.getPosition());

            } else {

            }
        })

    });
}

//隐藏菜单栏样式
function navStyle() {
    $(".dsp_type_Div_a").find("p").hide();
    $(".dsp_type_Div_a").find("span").css({
        "color": "#555"
    });
    $(".dsp_type_Div_a").find("div").css({
        "background": "#f6f1f1"
    });
    $(".dsp_type_Div_a").css({
        "border": "1px solid #f6f1f1"
    });
    $(".dsp_type_Div_a").find("img").attr("src", "./images/bottom.png");
}

//轮播图
function gogo(picture) {
    var imagesHtml = "";
    for (var i = 0; i < picture.length; i++) {
        imagesHtml += '<div class="swiper-slide"><img src="http://glxt.bjpygh.com/dsimage/' + picture[i] + '"></div>'
    }
    var htmlTest = '<div class="swiper-wrapper">' + imagesHtml + '</div><div class="swiper-pagination swiper-pagination-white"></div>'
    $(".swiper-container").html(htmlTest);

    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        // spaceBetween: 10,//幻灯片间隔
        paginationClickable: true,
        grabCursor: true,
        centeredSlides: true,
        slidesPerView: 'auto',
        parallax: true,
    });

}

function getDs() {
    //发送post请求，获取班型列表
    $.post("sdp.action", {
        "dsName": dsname,
        "models": models,
        "reservation": reservation,
        "trainTime": trainTime
    }, function(result) {
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
        $.each(dsp_list, function(commentIndex, comment) {
            dshtml += "<div class='dsp_list' makeProtection='" + comment.mustProtection + "' miaosu='" + comment.description + "' packageid='" + comment.packageid + "'><h2 class='dstype'>" + comment.dsType + "</h2><div class='dsp_infor'><span class='models'>" + comment.models + "</span><span class='traintime'>" + comment.trainTime + "</span><span class='brand'>" + comment.brand + "</span></div><span class='priceDiv'><span  class='price_symbol'>¥</span><span class='price'>" + comment.price + "</span><span class='pointzero'></span></span></div>";
        });
        $(".container").html(dshtml);

        // 为班型列表设置点击事件
        $(".dsp_list").click(function() {
            var dstype = $(this).find(".dstype").html();
            var models = $(this).find(".models").html();
            var price = $(this).find(".price").html();
            var traintime = $(this).find(".traintime").html();
            var packageid = $(this).attr("packageid");
            var description = $(this).attr("miaosu");
            var makeProtection = $(this).attr("makeProtection");
            var brand = $(this).find(".brand").html();
            // var myurl="ds_apply.html?dsname="+dsname+"&dstype="+dstype+"&models="+models+"&price="+price+"&packageid="+packageid+"&traintime="+traintime+"&description="+description;                                      
            // window.location.assign(encodeURI(myurl));

            $.cookie("dstype", dstype); //班型
            $.cookie("models", models); //班型类别，如C1，C2
            $.cookie("price", price); //班型价格
            $.cookie("traintime", traintime); //训练时间
            $.cookie("packageid", packageid); //班型编号id
            $.cookie("description", description); //班型秒速
            $.cookie("makeProtection", makeProtection); //补考保障
            $.cookie("brand", brand); //车型
            window.location.href = "ds_apply.html";
        });
    }, 'json');
}

function getGoodComment() {
    $.post("getGoodComment", {
        "type": 2
    }, function(datas) {
        if (datas.status == 0) {
            var assessUrl = datas.data.comment;
            //获取星星
            var enterStar = assessUrl.enterStar; //娱乐星星
            var stayStar = assessUrl.stayStar; //住宿星星
            var supportStar = assessUrl.supportStar; //设备星星
            var finishStar = Math.round((enterStar + stayStar + supportStar) / 3); //星星四舍五入取整
            if (finishStar == 1) {
                $("#star").attr("src", "./images/star1.png");
            } else if (finishStar == 2) {
                $("#star").attr("src", "./images/star2.png");
            } else if (finishStar == 3) {
                $("#star").attr("src", "./images/star3.png");
            } else if (finishStar == 4) {
                $("#star").attr("src", "./images/star4.png");
            } else if (finishStar == 5) {
                $("#star").attr("src", "./images/star5.png");
            } else {
                $("#star").attr("src", "./images/star5.png");
            }
            //匿名与否,0,flase不匿名，1,true是匿名
            var anonymous = assessUrl.anonymous;
            var nickname = assessUrl.nickname;
            if (anonymous == true) {
                nickname = nickname.substring(0, 1) + "**" + nickname.charAt(nickname.length - 1);
            }
            //获取时间年月日
            commentTime = assessUrl.commentTime.split(' ')[0];
            $(".assessBox-nickname").html(nickname);
            $(".assessBox-time").html(commentTime);
            $(".assessBox-assess").html(assessUrl.content);
            $("#icon").attr("src", assessUrl.headimageurl);
        }
    }, "json");
}