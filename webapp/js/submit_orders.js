var dsname = $.cookie("dsname"); //驾校名字
var dstype = $.cookie("dstype"); //班型套餐
var models = $.cookie("models"); //班型类别，如C1，C2
var price = $.cookie("price"); //班型套餐价格
var packageid = $.cookie("packageid"); //班型编号id
var traintime = $.cookie("traintime"); //训练时间
var coupons_sum = ""; //优惠券金额
var makeProtection = $.cookie("makeProtection"); //补考保障

var realname = "";
var address = "";
var note = "";
var select = "";
var mustProtection = "";
var ProtectionMoney = 180; //补考保障金额
var payProtectionMoney = 0; //需要额外支付补考保障的钱
//获取优惠券金额
function get_coupons() {
    $.ajax({
        type: "POST",
        url: "queryCoupon.action",
        dataType: "text",
        success: function(data) {
            var obj = eval('(' + data + ')');
            if (obj.status == "0") {
                coupons_sum = obj.data.price;
                $(".coupons span").html("¥" + coupons_sum); //优惠券价格
                $(".coupons span").css({
                    "color": "orange"
                });
                select = "1";

            } else {
                coupons_sum = 0;
                $(".coupons span").html("无可用优惠券&nbsp;&gt;");
                select = "0";

            }
            if (makeProtection == 0 || makeProtection == 1) {
                // payMoney();
            }
        }
    });
}
//获取用户手机号
function get_tel() {
    $.post("personal.action", {}, function(obj) {
        if (obj.status == "0") {
            var user = obj.data.userInfo;
            $(".tel").html(user.phoneNumber);
        }
    }, "json");
}

// function payMoney() {
//     var mustProtection = $("input[name='makeProtection']:checked").val();
//     if (mustProtection == 1) {
//         payProtectionMoney = ProtectionMoney;
//     } else {
//         payProtectionMoney = 0;
//     }
//     $(".price").empty();
//     $(".price").html("¥" + (price - coupons_sum + payProtectionMoney)); //需支付金额
// }

$(function() {
    //班型套餐是否有补考保障，0必须没有，1必须有，2可选择
    if (makeProtection == 0) {
        $(".ProtectionDiv").empty();
        $(".ProtectionDiv").html("套餐不提供补考保障");
        $(".ProtectionDiv").css({
            "text-align": "left",
            "font-size": "14px",
            "color": "555",
            "height": "40px",
            "line-height": "40px"
        })
        $(".ProtectionDivBox").show();

    } else if (makeProtection == 1) {
        $(".ProtectionDiv").empty();
        $(".ProtectionDiv").html("套餐赠送补考保障");
        $(".ProtectionDiv").css({
            "text-align": "left",
            "font-size": "14px",
            "color": "555",
            "height": "40px",
            "line-height": "40px"
        })
        $(".ProtectionDivBox").show();

    } else if (makeProtection == 2) {
        $(".ProtectionDivBox").show();
    }

    //为radio法培方式设置点击样式
    $("input[name='radio_training']").click(function() {
        $(this).siblings(".user-defined").html('<span class="chooseActive"></span>');
        $(this).parents(".trainDiv").siblings("div").find(".user-defined").empty();
    });
    //为补考保障设置点击样式
    $("input[name='makeProtection']").click(function() {
        $(this).siblings(".user-defined").html('<span class="chooseActive"></span>');
        $(this).parents(".mustProtectionDiv").siblings("div").find(".user-defined").empty();

        // payMoney();
    });

    //为补考保障说明
    $(".look").click(function() {
        // $(this).parent(".ds_training_mode").siblings(".ProtectionDivBox-hint").toggle();
        $(".layer").fadeIn(100);
        $(".secureHintPopup").fadeIn(100);

    });
    $(".secureHintPopupDiv img").click(function() {
        $(".layer").fadeOut(100);
        $(".secureHintPopup").fadeOut(100);
    });

    $(".ds_type").html(dstype);
    $(".dstype_information_content").html(dsname + "&nbsp;&frasl;&nbsp;" + models + "&nbsp;&frasl;&nbsp;" + traintime);
    $(".ds_price").html(price);

    get_tel(); //获取用户手机号
    get_coupons(); //获取优惠券金额

    //提交订单支付宝支付
    $(".footer").click(function() {
        //获取用户姓名、联系方式、性别、地址
        realname = $("#real_name").val();
        address = $("#address").val();
        note = $("input[name='radio_training']:checked").val();

        //班型套餐是否有补考保障，0必须没有，1必须有，2可选择
        if (makeProtection == 2) {
            mustProtection = $("input[name='makeProtection']:checked").val();
        } else if (makeProtection == 1) {
            mustProtection = 1;
        } else {
            mustProtection = 0;
        }

        console.log("补考保障", mustProtection, "法培方式", note);
        if (realname == "") {
            modalHintText("请输入您的手机号");
        } else if (address == "") {
            modalHintText("请输入您的地址");
        } else if (note == null) {
            modalHintText("请选择您的法培方式");
        } else if (mustProtection == null) {
            modalHintText("请选择是否购买补考保障");
        } else {
            $.post("note.action", {
                "realname": realname,
                "address": address,
                "note": note,
            }, function(datas) { //保存用户信息
                if (datas.status == "0") { //代表用户目前没有驾校的完成订单
                    var objData = {
                        "packageid": packageid,
                        "select": select,
                        "protecttion": mustProtection
                    }
                    $.post("createOrder.action", objData, function(datass) {
                        if (datass.status == 0) { //预约成功
                            $(".modal-body").html("您已成功预约" + dsname + "驾考报名，工作人员将在24小时之内与您取得联系，请保持电话畅通。");
                            $('#myModal').modal({
                                keyboard: true
                            });
                            window.location.href = "myorder.html";
                        } else {
                            $(".modal-body").html("您已成功预约" + dsname + "驾考报名,请勿重复预约。如有问题，请联系小漂哦。");
                            $('#myModal').modal({
                                keyboard: true
                            });
                        }
                    }, "json");
                } else if (datas.status == "-20") {
                    // $(".modal-dialog").css({
                    //     "margin": "20px",
                    //     "margin-top": "200px",
                    // });
                    // $(".modal-content").css({
                    //     "padding": "10px"
                    // })
                    $(".modal-body").html("您已成功预约" + dsname + "驾考报名,请勿重复预约。如有问题，请联系小漂哦。");
                    $('#myModal').modal({
                        keyboard: true
                    });
                }
            }, "json");
        }
    });

    // //选择支付方式后点击确认
    // $(".toPay").click(function() {
    //     var payMode = $("input[name='payMode-radio']:checked").val();
    //     console.log(payMode);
    //     //请求参数
    //     var objData = {
    //         "packageid": packageid,
    //         "select": select,
    //         "protecttion": mustProtection
    //     }
    //     var objUrl = "createOrder.action"; //请求接口
    //     if (payMode == "JD") { //京东支付
    //         $.post(objUrl, objData, function(datas) { //创建驾校报名订单
    //             if (datas.status == 0) {
    //                 var ordernumber = datas.data.ordernumber;
    //                 console.log(ordernumber);
    //                 $(".layer").hide();
    //                 $(".payBox").hide();
    //                 //京东支付请求
    //                 $.post("JDPay", {
    //                     "ordernumber": ordernumber
    //                 }, function(data) {
    //                     if (data.status == 0) {
    //                         var jdOrderPay = data.data.jdOrderPay;
    //                         $("#version").val(jdOrderPay.version);
    //                         $("#merchant").val(jdOrderPay.merchant);
    //                         $("#sign").val(jdOrderPay.sign);
    //                         $("#tradeNum").val(jdOrderPay.tradeNum);
    //                         $("#tradeName").val(jdOrderPay.tradeName);
    //                         $("#tradeTime").val(jdOrderPay.tradeTime);
    //                         $("#amount").val(jdOrderPay.amount);
    //                         $("#currency").val(jdOrderPay.currency);
    //                         $("#callbackUrl").val(jdOrderPay.callbackUrl);
    //                         $("#notifyUrl").val(jdOrderPay.notifyUrl);
    //                         $("#userId").val(jdOrderPay.userId);
    //                         $("#orderType").val(jdOrderPay.orderType);
    //                         document.getElementById("batchForm").submit();
    //                     }
    //                 }, 'json');
    //             } else if (datas.status == "-40") {
    //                 modalHintText("您当前存在多个未支付订单，请勿重复下单。")
    //             }
    //         }, "json");
    //     } else if (payMode == "aliPay") { //支付宝支付
    //         $.post(objUrl, objData, function(datas) { //创建驾校报名订单
    //             if (datas.status == 0) {
    //                 var ordernumber = datas.data.ordernumber;
    //                 console.log(ordernumber);
    //                 $(".layer").hide();
    //                 $(".payBox").hide();
    //                 window.location.href = "payHint.html?ordernumber=" + ordernumber;

    //             } else if (datas.status == "-40") {
    //                 modalHintText("您当前存在多个未支付订单，请勿重复下单。")
    //             }

    //         }, "json");
    //     }
    // });

    //为支付方式的radio设置自定义点击样式
    $(".payMode").click(function() {
        $(this).children(".payModeImg").css({
            "background": "url(./images/circle_choose.png)",
            "background-size": "20px"
        });
        $(this).parents().siblings("label").find(".payModeImg").css({
            "background": "url(./images/circle.png)",
            "background-size": "20px"
        });
    });
    //关闭弹窗
    $(".payBox p").click(function() {
        $(".layer").hide();
        $(".payBox").hide();
    });
});