// var dsname = "";
// var dstype = "";
// var models = "";
// var price = "";
// var packageid = "";
// var traintime = "";
// var description = "";

// function ShowMessage() { 
//     var thisURL = decodeURI(location.href);    
//     var getval = thisURL.split('?')[1];
//     dsname = getval.split("=")[1].split('&')[0];
//     dstype = getval.split("=")[2].split('&')[0];
//     models = getval.split("=")[3].split('&')[0];
//     price = getval.split("=")[4].split('&')[0];
//     packageid = getval.split("=")[5].split('&')[0];
//     traintime = getval.split("=")[6].split('&')[0];
//     description = getval.split("=")[7];
// } 
// window.onload=ShowMessage(); 

var dsname = $.cookie("dsname"); //驾校名字
var dstype = $.cookie("dstype"); //班型套餐
var models = $.cookie("models"); //班型类别，如C1，C2
var price = $.cookie("price"); //班型套餐价格
var packageid = $.cookie("packageid"); //班型编号id
var traintime = $.cookie("traintime"); //训练时间
var description = $.cookie("description"); //套餐描述


$(function() {
    $(".ds_type").html(dstype);
    $(".ds_name").html(dsname + "&nbsp;/&nbsp;" + models);
    $(".ds_price").html("¥" + price);
    $(".description").html(description);
    //立即报名时。判断用户是否已经绑定手机号
    $("#submit").click(function() {
        $.post("isBond.action", {}, function(datas) {
            if (datas.status == "0") {
                // var myurl="submit_orders.html?dsname="+dsname+"&dstype="+dstype+"&models="+models+"&price="+price+"&packageid="+packageid+"&traintime="+traintime;                                      
                // window.location.assign(encodeURI(myurl));
                window.location.href = "submit_orders.html";
            } else if (datas.status == "-20") {
                var r = confirm("您尚未绑定手机号，是否立即绑定？");
                if (r == true) {
                    window.location.href = "login.html";
                    return true;
                } else {
                    return false;
                }
            }
        }, "json");
    });
});