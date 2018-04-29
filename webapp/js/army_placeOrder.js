var realName = ""; //真实姓名
var peopleNumber = ""; //报名人数
var realNumber = ""; //身份证号码
var secureNumber = ""; //保险
var villaNames = []; //用于存储别墅的数组
var re = /^[1-9]+[0-9]*]*$/; //正整数
var date = "";
var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/; //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 

$(function() {
//关闭弹窗
    $(".modalHint-footer-ok").click(function() {
        $(".modalHint-layer").fadeOut(100);
        $(".modalHint").hide();
        console.log("关闭");
    });
    //获取用户手机号码
    $.post("personal.action", {}, function(datas) {
        if (datas.status == 0) {
            var userobj = datas.data.userInfo;
            $('.villa-user p.tel').html(userobj.phoneNumber);
        }
    }, "json");


    //日历渲染--------------------------------------------------------
    renderHtml(0);
    // 表格中显示日期
    showCalendarData(0, 0);
    //展示价格
    setArmyPrice(getYandM(0).dataPara);

    //    日历结束
    //    点击上一月
    var monthNum = 0;
    $('.sit-pre-month').on('click', function(e) {
        e.preventDefault();
        // 第二个参数 0为本月 上一个月1，下一个月-1
        ++monthNum;
        showCalendarData(0, monthNum);
        setArmyPrice(getYandM(monthNum).dataPara);
    });

    //    点击下一月
    $('.sit-next-month').on('click', function(e) {
        e.preventDefault();
        --monthNum;
        showCalendarData(0, monthNum);
        setArmyPrice(getYandM(monthNum).dataPara);
    });

    // 获取当前时间
    var myDate = new Date();
    var todyMonth = myDate.getMonth() + 1;
    var todyDate = myDate.getDate();
    var flag = false;
    //点击每天的操作，要是有其他操作可在此处写事件，只有展示功能的日历 ，注释此段代码 点击不需要事件
    $('.currentMonth0').on('click', function(e) {
        console.log($(this));
        var thisDayNumber = $(this).children(".borderr").attr("thisDayNumber"); //获点击所对应的当天日期
        //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
        //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参

        if ($(this).hasClass('no-ticket')) { //余票不能点击 具体需求具体实现
            return;
        }
        if ($(this).hasClass('beforeDay')) { //今天之前的日期不能点击
            return;
        }

        var clength = $(".currentMonth0").length;
        //点击第三次重新选中
        if (flag) {
            for (var k = 0; k < clength; k++) {
                $(".currentMonth0").eq(k).children(".selectedDay").remove();
            }
        }
        if ($(this).children(".selectedDay").length > 0) {
            $(this).children(".selectedDay").remove();
        } else {
            $(this).append("<i class='selectedDay'></i>");
        }

        var indexArr = []; //保存下标
        var startIndex = -1;
        var endIndex = -1;
        for (var i = 0; i < clength; i++) {
            //如果有选中的
            if ($(".currentMonth0").eq(i).children(".selectedDay").length > 0) {
                // console.log($(".currentMonth0").eq(i));
                indexArr.push(i);
                startIndex = indexArr[0];
                endIndex = indexArr[indexArr.length - 1];
                if (indexArr.length >= 2) { //选中两个日期，第三次重新赋值
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
        }
        //两个日期之间的日期全部选中
        for (var j = 0; j < clength; j++) {
            $(".currentMonth0").eq(j).children(".selectedDay").remove();
            if (j >= startIndex && j <= endIndex) {
                if (!$(".currentMonth0").eq(j).hasClass('no-ticket'))
                    $(".currentMonth0").eq(j).append("<i class='selectedDay'></i>");
            }
        }

        //如果有选中的，将其添加到数组
        var selectDate = [];
        for (var i = 0; i < clength; i++) {
            if ($(".currentMonth0").eq(i).children(".selectedDay").length > 0) { //当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }
        }
        var date = selectDate.join(); //数组中的所有元素放入一个字符串
        console.log(date);

        //当报名日期改动时，修改金额
        getPrice();

    });

    $('.otherMonth').on('click', function(e) {
        console.log($(this));
        //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
        //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参

        if ($(this).hasClass('no-ticket')) { //余票不能点击 具体需求具体实现
            return;
        }
        if ($(this).hasClass('beforeDay')) { //今天之前的日期不能点击
            return;
        }

        var clength = $(".currentMonth0").length;
        //点击第三次重新选中
        if (flag) {
            for (var k = 0; k < clength; k++) {
                $(".currentMonth0").eq(k).children(".selectedDay").remove();
            }
        }
        if ($(this).children(".selectedDay").length > 0) {
            $(this).children(".selectedDay").remove();
        } else {
            $(this).append("<i class='selectedDay'></i>");
        }

        var indexArr = []; //保存下标
        var startIndex = -1;
        var endIndex = -1;
        for (var i = 0; i < clength; i++) {
            //如果有选中的
            if ($(".currentMonth0").eq(i).children(".selectedDay").length > 0) {
                // console.log($(".currentMonth0").eq(i));
                indexArr.push(i);
                startIndex = indexArr[0];
                endIndex = indexArr[indexArr.length - 1];
                if (indexArr.length >= 2) { //选中两个日期，第三次重新赋值
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
        }
        //两个日期之间的日期全部选中
        for (var j = 0; j < clength; j++) {
            $(".currentMonth0").eq(j).children(".selectedDay").remove();
            if (j >= startIndex && j <= endIndex) {
                if (!$(".currentMonth0").eq(j).hasClass('no-ticket'))
                    $(".currentMonth0").eq(j).append("<i class='selectedDay'></i>");
            }
        }

        //如果有选中的，将其添加到数组，并发送post请求给后台
        var selectDate = [];
        for (var i = 0; i < clength; i++) {
            if ($(".currentMonth0").eq(i).children(".selectedDay").length > 0) { //当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }
        }
        var date = selectDate.join(); //数组中的所有元素放入一个字符串
        console.log(date);

        //当报名日期改动时，修改金额
        getPrice();

    });

    //保单说明的弹窗   
    $(".secureHint").click(function() {
        $(".layer").show();
        $(".secureHintPopup").show();
    });
    $(".secureHintPopupDiv img").click(function() {
        $(".layer").hide();
        $(".secureHintPopup").hide();
    });


    //选择人数，帐篷，室内，等 ,时时监听变动
    //当报名人数改动时，修改金额
    $("#army-peoplenumber").bind('input porpertychange', function() {
        var indoor = $(".indoor").html(); //室内人使用
        var tend = $(".tend").html(); //帐篷人使用
        $(".tend").html(0);
        $(".indoor").html(0);
        var peopleNumber = $("#army-peoplenumber").val(); //报名人数
        if (re.test(peopleNumber) && peopleNumber > 0) {
            $(".nosleep").html(peopleNumber);


        } else {
            $(".nosleep").html(0);
        }

        //当报名人数改动时，修改金额
        getPrice();
    });

    //当保险人数改动时，修改金额
    $("#army-securenumber").bind('input porpertychange', function() {

        //当报名人数改动时，修改金额
        getPrice();
    });

    //填写保险人数，提示不大于报名人数
    var securenumberTest = document.getElementById("army-securenumber");
    securenumberTest.onblur = function() {
            var peopleNumber = $("#army-peoplenumber").val(); //报名人数
            if (re.test(peopleNumber) && peopleNumber > 0) {
                if (peopleNumber < $("#army-securenumber").val()) {
                    modalHintText("温馨提示：购买保险人数应不大于报名人数");
                }
            }

        }
        //填写报名人数，若小于保险人数，提示
    var joinPeopleTest = document.getElementById("army-peoplenumber");
    joinPeopleTest.onblur = function() {
        var secureNumber = $("#army-securenumber").val(); //保险人数
        if (re.test(secureNumber) && secureNumber > 0) {
            if (secureNumber > $("#army-peoplenumber").val()) {
                modalHintText("温馨提示：报名人数应不小于购买保险人数");
            }
        }

    }

    //是否选择烧烤
    $(".barbecue-row").click(function() {
        $(this).children(".barbecue1").html("<img src='./images/circle_choose.png' height='18px' width='18px'>");
        $(this).parents().siblings("label").find(".barbecue1").empty();
        getPrice();
    });

    //军旅选择时间段
    $(".army-time-row").click(function() {
        $(this).children(".army-time1").html("<img src='./images/circle_choose.png' height='18px' width='18px'>");
        $(this).parents().siblings("label").find(".army-time1").empty();
        if ($("input[name='timeRadio']:checked").val() == 0) {
            $(".period").html("时间段:8:00-11:30");
        } else {
            $(".period").html("时间段:13:30-17:00");
        }
        getPrice();
    });

    //全额支付与预约定金
    $(".Reserve-row").click(function() {
        $(this).children(".Reserve1").html("<img src='./images/circle_choose.png' height='18px' width='18px'>");
        $(this).parents().siblings("label").find(".Reserve1").empty();
        //付款类型，1全额，或0预约定金
        if ($("input[name='Reserve1Radio']:checked").val() == 0) {
            $(".reserve1-mode").html("付款类型：预约定金");

        } else {
            $(".reserve1-mode").html("付款类型：全额支付");

        }
        getPrice();
    });

    //是否阅读北京漂洋过海房屋守则
    $(".roomCode").click(function() {
        if ($(this).find("img").length == 0) {
            $(".roomCode").html("<img src='./images/circle_choose.png' height='16px' width='16px'>");
        } else {
            $(".roomCode").empty();
        }
    });

    //为帐篷、室内、不住宿人数添加加减功能
    //更改帐篷人数
    $(".armyNumber-tend img:last-child").click(function() {
        var peopleNumber = $("#army-peoplenumber").val(); //报名人数
        var nosleep = $(".nosleep").html(); //不住宿人数
        var indoor = $(".indoor").html(); //室内人数
        var armyNumber = $(this).siblings("span").html(); //当前点击对应的人数,帐篷人数
        if (re.test(peopleNumber) && peopleNumber) {


            // console.log(armyNumber+","+indoor+","+peopleNumber);

            armyNumber++;
            if (armyNumber >= (peopleNumber - indoor)) {
                armyNumber = peopleNumber - indoor;
            }
            nosleep--;
            if (nosleep < 1) {
                nosleep = 0;
            }

            $(this).siblings("span").html(armyNumber);
            $(".nosleep").html(nosleep);

        } else {
            modalHintText("请输入报名人数");
        }

        //当报名人数改动时，修改金额
        getPrice();

    });
    $(".armyNumber-tend img:first-child").click(function() {
        var peopleNumber = $("#army-peoplenumber").val(); //报名人数
        var indoor = $(".indoor").html(); //室内人数
        var armyNumber = $(this).siblings("span").html();
        var nosleep = $(".nosleep").html(); //不住宿人数
        if (re.test(peopleNumber) && peopleNumber) {


            armyNumber--;
            if (armyNumber < 1) {
                armyNumber = 0;
            }
            nosleep++;
            if (nosleep >= (peopleNumber - indoor)) {
                nosleep = peopleNumber - indoor;
            }


            $(this).siblings("span").html(armyNumber);
            $(".nosleep").html(nosleep);

        } else {
            modalHintText("请输入报名人数");
        }
        getPrice();

    });
    //更改室内人数
    $(".armyNumber-indoor img:last-child").click(function() {
        var peopleNumber = $("#army-peoplenumber").val(); //报名人数
        var nosleep = $(".nosleep").html(); //不住宿人数
        var tend = $(".tend").html(); //室内人数
        var armyNumber = $(this).siblings("span").html(); //当前点击对应的人数,帐篷人数
        if (re.test(peopleNumber) && peopleNumber) {


            // console.log(armyNumber+","+tend+","+peopleNumber);

            armyNumber++;
            if (armyNumber >= (peopleNumber - tend)) {
                armyNumber = peopleNumber - tend;
            }
            nosleep--;;
            if (nosleep < 1) {
                nosleep = 0;
            }

            $(this).siblings("span").html(armyNumber);
            $(".nosleep").html(nosleep);

        } else {
            modalHintText("请输入报名人数");
        }

        getPrice();

    });
    $(".armyNumber-indoor img:first-child").click(function() {
        var peopleNumber = $("#army-peoplenumber").val(); //报名人数
        var tend = $(".tend").html(); //帐篷人数
        var armyNumber = $(this).siblings("span").html();
        var nosleep = $(".nosleep").html(); //不住宿人数
        if (re.test(peopleNumber) && peopleNumber) {


            armyNumber--;
            if (armyNumber < 1) {
                armyNumber = 0;
            }
            nosleep++;
            if (nosleep >= (peopleNumber - tend)) {
                nosleep = peopleNumber - tend;
            }


            $(this).siblings("span").html(armyNumber);
            $(".nosleep").html(nosleep);

        } else {
            modalHintText("请输入报名人数");
        }


        getPrice();
    });



    //明细
    $(".footer-detail").click(function() {
        if ($(".detailBox").css("display") == "none") {
            $(".detailBox").show();
            $(this).find("img").attr("src", "./images/up.png");
        } else {
            $(".detailBox").hide();
            $(this).find("img").attr("src", "./images/bottom.png");
        }

    });

    //为支付方式的radio设置点击样式
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


    //填写信息后，点击支付
    $(".footer2-toPay").click(function() {
        var clength = $(".currentMonth0").length;
        var selectDate = []; //用于选中日期的数组
        //如果有选中的，将其添加到数组
        for (var i = 0; i < clength; i++) {
            if ($(".currentMonth0").eq(i).children(".selectedDay").length > 0) { //当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }
        }
        // console.log(JSON.stringify(selectDate));//将对象转化为json字符串
        // console.log(selectDate.join());//数组中的所有元素放入一个字符串

        date = selectDate.join(); //数组中的所有元素放入一个字符串
        realName = $("input[name='realName']").val(); //获取姓名
        peopleNumber = $("#army-peoplenumber").val(); //获取报名人数
        secureNumber = $("#army-securenumber").val(); //保险人数
        realNumber = $("input[name='realNumber']").val(); //获取身份证号码

        if (re.test(secureNumber) && secureNumber > 0) {
            //不处理
        } else {
            secureNumber = 0;
        }
        if (re.test(peopleNumber) && peopleNumber > 0) { //报名人数不为空并且是正整数

            if ($("input[name='timeRadio']:checked").val() == 0 || $("input[name='timeRadio']:checked").val() == 1) {
                if ($(".currentMonth0").children(".selectedDay").length > 0) { //当月存在被选中日期
                    if (realName != "") { //真实姓名不为空
                        if (reg.test(realNumber)) {
                            if ($(".roomCode").find("img").length == 1) {
                                console.log("姓名" + realName + "/报名日期" + date + "/人数" + peopleNumber + "/身份证" + realNumber + "/保险" + secureNumber + "/参战时刻" + $("input[name='Reserve1Radio']:checked").val());
                                $(".layer").show();
                                $(".payBox").show();
                            } else {
                                modalHintText("请遵守北京漂洋过海《参战守则》");
                            }

                        } else {
                            modalHintText("身份证格式不对");
                        }
                    } else {
                        modalHintText("输入您的真实姓名");
                    }
                } else {
                    modalHintText("请选择报名日期");
                }
            } else {

                modalHintText("您尚未选择参战时刻");
            }



        } else {
            modalHintText("请填写正确的报名人数");
        }
    });


    //选择支付方式后点击确认
    $(".toPay").click(function() {
        var payMode = $("input[name='villa-radio']:checked").val(); //支付方式
        console.log(payMode,"付款方式");
        var tend = $(".tend").html(); //帐篷人数
        var indoor = $(".indoor").html(); //室内人数
        var nosleep = $(".nosleep").html(); //不住宿人数
        var reserve = $("input[name='Reserve1Radio']:checked").val(); //付款类型，1全额，或0预约定金
        var armyTime = $("input[name='timeRadio']:checked").val(); //付款类型，1下午，或0上午
        if ($("input[name='barbecueRadio']:checked").val() == 1) {
            var barbecueChoose = 1; //选中烧烤
        } else {
            var barbecueChoose = 0; //没有选中烧烤
        }
        if (payMode != "" && payMode != null && payMode != "undefined") { //京东支付
            $.post("createArmyOrder", {
                "date": date,
                "peopleNumber": peopleNumber,
                "realName": realName,
                "idNumber": realNumber,
                "insurance": secureNumber,
                "noroomNumber": nosleep,
                "roomNumber": indoor,
                "fullAmount": reserve,
                "barbecue": barbecueChoose,
                "period": armyTime
            }, function(datas) {
                if (datas.status == 0) {
                    var orderNumber = datas.data.orderNumber;
                    console.log(orderNumber);
                    $(".layer").hide();
                    $(".payBox").hide();

                    if (payMode == "JD") {
                        //京东支付请求
                        $.post("JDPay", {
                            "ordernumber": orderNumber
                        }, function(data) {
                            if (data.status == 0) {
                                var jdOrderPay = data.data.jdOrderPay;
                                $("#version").val(jdOrderPay.version);
                                $("#merchant").val(jdOrderPay.merchant);
                                $("#sign").val(jdOrderPay.sign);
                                $("#tradeNum").val(jdOrderPay.tradeNum);
                                $("#tradeName").val(jdOrderPay.tradeName);
                                $("#tradeTime").val(jdOrderPay.tradeTime);
                                $("#amount").val(jdOrderPay.amount);
                                $("#currency").val(jdOrderPay.currency);
                                $("#callbackUrl").val(jdOrderPay.callbackUrl);
                                $("#notifyUrl").val(jdOrderPay.notifyUrl);
                                $("#userId").val(jdOrderPay.userId);
                                $("#orderType").val(jdOrderPay.orderType);
                                document.getElementById("batchForm").submit();
                            }
                        }, 'json');
                    } else if (payMode == "aliPay") { //支付宝支付
                        window.location.href = "payHint.html?ordernumber=" + orderNumber;
                    } else if (payMode == "wxpay") { //支付宝支付
                        recharge(orderNumber); //微信支付
                    }

                } else {
                    modalHintText("您当前存在多个未支付订单，请勿重复下单。")
                }
            }, "json");
        } else {
            modalHintText("请选择支付方式");
        }
    });

    //关闭弹窗
    $(".payBox p").click(function() {
        $(".layer").hide();
        $(".payBox").hide();
    });



});

//计算价格
function getPrice() {
    var peopleNumber = $("#army-peoplenumber").val(); //报名人数
    var secureNumber = $("#army-securenumber").val(); //保险人数
    var reserve = $("input[name='Reserve1Radio']:checked").val(); //付款类型，1全额，或0预约定金
    if (re.test(secureNumber) && secureNumber > 0) {

    } else {
        secureNumber = 0;
    }
    if ($("input[name='barbecueRadio']:checked").val() == 1) {
        var barbecueChoose = 1; //选中烧烤
    } else {
        var barbecueChoose = 0; //没有选中烧烤
    }
    var tendNumber = $(".tend").html(); //帐篷人数
    var indoorNumber = $(".indoor").html(); //室内人数
    var selectDay = $(".currentMonth0").children(".selectedDay").length; //报名天数
    if (re.test(peopleNumber) && peopleNumber > 0 && selectDay > 0 && $("input[name='timeRadio']:checked").val() != null) {
        $(".liVilla").html("<div><span>军旅</span><span class='detailBox-liVilla'>¥" + 120 * peopleNumber * selectDay + "</span></div><span class='detailBox-liVilla-hint'>(120元x" + peopleNumber + "人x" + selectDay + "天)</span>"); //军旅基础费用
        $(".libarbecue").html("<div><span>烧烤</span><span class='detailBox-liVilla'>¥" + 10 * barbecueChoose * peopleNumber * selectDay + "</span></div><span class='detailBox-liVilla-hint'>(10元x" + barbecueChoose * peopleNumber + "人x" + selectDay + "天)</span>") //烧烤费用
        $(".lilitend").html("<div><span>帐篷</span><span class='detailBox-liVilla'>¥" + 20 * tendNumber * selectDay + "</span></div><span class='detailBox-liVilla-hint'>(20元x" + tendNumber + "人x" + selectDay + "天)</span>") //帐篷费用
        $(".liindoor").html("<div><span>室内</span><span class='detailBox-liVilla'>¥" + 40 * indoorNumber * selectDay + "</span></div><span class='detailBox-liVilla-hint'>(40元x" + indoorNumber + "人x" + selectDay + "天)</span>") //室内费用
        $(".liSecure").html("<div><span>保险</span><span class='detailBox-liVilla'>¥" + 15 * secureNumber + "</span></div><span class='detailBox-liVilla-hint'>(15元x" + secureNumber + "人)</span>") //室内费用

        if (reserve == 0) {
            $(".reserve1-mode").html("付款类型：预约定金");
            $("#footer-price").html("¥" + (Math.ceil((120 * peopleNumber * selectDay + 10 * barbecueChoose * peopleNumber * selectDay + 20 * tendNumber * selectDay + 40 * indoorNumber * selectDay) / 2) + 15 * secureNumber));
        } else {
            $(".reserve1-mode").html("付款类型：全额支付");
            $("#footer-price").html("¥" + (120 * peopleNumber * selectDay + 10 * barbecueChoose * peopleNumber * selectDay + 20 * tendNumber * selectDay + 40 * indoorNumber * selectDay + 15 * secureNumber));
        }
    } else {
        $("#footer-price").html("¥0");
        $(".liVilla").html(""); //军旅基础费用
        $(".libarbecue").html("") //烧烤费用
        $(".lilitend").html("") //帐篷费用
        $(".liindoor").html("") //室内费用
        $(".liSecure").html("") //室内费用

    }
}


//付款
function recharge(orderNumber) {
    $.post("wxpay.action", {
        "orderNumber": orderNumber
    }, function(obj) {
        if (obj.status == "0") {
            determine(obj);
        } else {
            modalHintText("系统繁忙，请稍后再试。");
        }

    }, "json");
}
//付款判断
function determine(obj) {
    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady(obj);
    }
}

//微信支付
function onBridgeReady(obj) {
    var payData = obj.data;
    var payDataChild = payData.data;
    console.log("微信支付返回",obj);
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId": payDataChild.appid, //公众号名称，由商户传入
            "timeStamp": payData.timeStamp, //时间戳，自1970年以来的秒数
            "nonceStr": payDataChild.nonce_str, //随机串
            "package": "prepay_id=" + payDataChild.prepay_id,
            "signType": "MD5", //微信签名方式：
            "paySign": payData.paySign //微信签名
        },
        function(res) {
            if (res.err_msg == "get_brand_wcpay_request:ok") {
                $(".modalHint-body").html("支付成功");
                openModalHint(); //打开确认弹窗
            } // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            else {
                $(".modalHint-body").html("未支付");
                openModalHint(); //打开确认弹窗

            }
        }

    );
}