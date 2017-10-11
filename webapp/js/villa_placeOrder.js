
     //日历渲染--------------------------------------------------------
    renderHtml(0);
    // 表格中显示日期
    showCalendarData(0,0);
    //展示价格
    setPrice(0);

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
// 获取当前时间
    var myDate = new Date(); 
    var todyMonth = myDate.getMonth()+1;
    var todyDate = myDate.getDate();
     //点击每天的操作，要是有其他操作可在此处写事件，只有展示功能的日历 ，注释此段代码 点击不需要事件
    $('.currentMonth0').on('click',function(e){
        console.log($(this));
        var thisDayNumber = $(this).children(".borderr").attr("thisDayNumber");//获点击所对应的当天日期
        if (monthNum==0) {
            //当今天的号码小于点击的号码时
            if(todyDate<thisDayNumber){
                //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
                //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参
                if($(this).hasClass('no-ticket')){ //余票不能点击 具体需求具体实现
                    return;
                }
                if ($(this).children(".selectedDay").length > 0 ) {
                    $(this).children(".selectedDay").remove();
                }else{
                    $(this).append("<i class='selectedDay'></i>");
                }
            }
            
        }else if(monthNum<0){
             console.log($(this));
            //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
            //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参
            if($(this).hasClass('no-ticket')){ //余票不能点击 具体需求具体实现
                return;
            }
            if ($(this).children(".selectedDay").length > 0 ) {
                $(this).children(".selectedDay").remove();
            }else{
                $(this).append("<i class='selectedDay'></i>");
            }
        } 
    });
    $('.otherMonth').on('click',function(e){
        console.log($(this));
        //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
        //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参
        if($(this).hasClass('no-ticket')){ //余票不能点击 具体需求具体实现
            return;
        }
        if ($(this).children(".selectedDay").length > 0 ) {
                $(this).children(".selectedDay").remove();
            }else{
                $(this).append("<i class='selectedDay'></i>");
        }
    });

    //别墅选择自定义点击样式,选择时与未选择时。
    $(".villa-radio").click(function() {
        if ($(this).find("input[name='villa-radio']").is(':checked')) {//别墅被选中时
            $(this).find(".villa-choose").css({"background":"url(./images/circle_choose.png)","background-size":"20px"});
        }else{
            $(this).find(".villa-choose").css({"background":"url(./images/circle.png)","background-size":"20px"});
        }
    });
        //为支付方式的radio设置自定义点击样式
    $(".payMode").click(function() {
        $(this).children(".payModeImg").css({"background":"url(./images/circle_choose.png)","background-size":"20px"});
        $(this).parents().siblings("label").find(".payModeImg").css({"background":"url(./images/circle.png)","background-size":"20px"});
    });

    //填写信息后，点击支付
    $(".footer p:last-child").click(function(){
        if ($(".villa-choose-content input").val()>0) {//报名人数不为空
            if ($("input[name='villa-radio']:checked").length>0) {
                if ($("input[name='realName']").val()!=""&&$("input[name='realNumber']").val()!="") {//真实姓名以及身份证不为空
                    $(".layer").show();
                    $(".payBox").show(); 
                }else{
                    alert("请完善您的信息");
                }
            }else{
                alert("请选择别墅");
            }
            
        }else{
            alert("您尚未填写参加人数");
        }
    });
    //选择支付方式后点击确认
    $(".toPay").click(function(){
        window.location.href="payHint.html";
    });
    //关闭弹窗
    $(".payBox p").click(function(){
        $(".layer").hide();
        $(".payBox").hide();
    })
