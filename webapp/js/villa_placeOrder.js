$(function(){
     //日历渲染--------------------------------------------------------
    renderHtml(0);
    // 表格中显示日期
    showCalendarData(0,0);
    //展示价格
    setPrice(getYandM(0).dataPara);

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
     var flag = false;
     //点击每天的操作，要是有其他操作可在此处写事件，只有展示功能的日历 ，注释此段代码 点击不需要事件
    $('.currentMonth0').on('click',function(e){
        console.log($(this));
        var thisDayNumber = $(this).children(".borderr").attr("thisDayNumber");//获点击所对应的当天日期
        //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
        //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参

        if($(this).hasClass('no-ticket')){ //余票不能点击 具体需求具体实现
            return;
        }
        if($(this).hasClass('beforeDay')){ //今天之前的日期不能点击
            return;
        }

        var clength = $(".currentMonth0").length;
        //点击第三次重新选中
        if(flag){
            for(var k = 0 ;k < clength; k ++){
                $(".currentMonth0").eq(k).children(".selectedDay").remove();
            }
        }
        if ($(this).children(".selectedDay").length > 0 ) {
                $(this).children(".selectedDay").remove();
        }else{
                $(this).append("<i class='selectedDay'></i>");
        }

        var indexArr = []; //保存下标
        var startIndex = -1;
        var endIndex = -1;
        for(var i = 0 ;i < clength; i ++){
            //如果有选中的
            if($(".currentMonth0").eq(i).children(".selectedDay").length > 0){
                // console.log($(".currentMonth0").eq(i));
                indexArr.push(i);
                startIndex = indexArr[0];
                endIndex = indexArr[indexArr.length-1];
                if(indexArr.length>=2){  //选中两个日期，第三次重新赋值
                    flag = true;
                    break;
                }else{
                    flag = false;
                }
            }
        }
        //两个日期之间的日期全部选中
        for(var j = 0 ;j < clength; j ++){
            $(".currentMonth0").eq(j).children(".selectedDay").remove();
            if(j>=startIndex&&j<=endIndex){
                if(!$(".currentMonth0").eq(j).hasClass('no-ticket'))
                $(".currentMonth0").eq(j).append("<i class='selectedDay'></i>");
            }
        }

        //如果有选中的，将其添加到数组,并发送post请求给后台
        var selectDate = [];
        for(var i = 0 ;i < clength; i ++){
            if($(".currentMonth0").eq(i).children(".selectedDay").length > 0){//当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }  
        }        
        var date = selectDate.join();//数组中的所有元素放入一个字符串
        console.log(date);
        $.post("getVillaRes",{"date":date},function(datas){
            if (datas.status==0) {
                var selected = datas.data.selected;
                if (selected!="") {
                    alert("所选日期中，"+selected+"已被预定");
                }else{
                    var dateHundred=0;
                    var dateSix=0;
                    
                    for(var x=0;x<selectDate.length;x++){
                        var myDate = new Date(selectDate[x]);
                        if(myDate.getDay()==0 || myDate.getDay()==5 || myDate.getDay()==6){
                            dateHundred++;
                        }else{
                            dateSix++;  
                        }
                        
                    }
                    console.log("100元"+dateHundred+"天,66元"+dateSix+"天");
                }
            }
        },"json");

    });
    
    $('.otherMonth').on('click',function(e){
         console.log($(this));
        //选择完日期后 点击按钮去支付 ，可以将需要的参数传进去
        //如果有限制条件 直接return 不添加标签并且支付也不不能点击传参

        if($(this).hasClass('no-ticket')){ //余票不能点击 具体需求具体实现
            return;
        }
        if($(this).hasClass('beforeDay')){ //今天之前的日期不能点击
            return;
        }

        var clength = $(".currentMonth0").length;
        //点击第三次重新选中
        if(flag){
            for(var k = 0 ;k < clength; k ++){
                $(".currentMonth0").eq(k).children(".selectedDay").remove();
            }
        }
        if ($(this).children(".selectedDay").length > 0 ) {
                $(this).children(".selectedDay").remove();
        }else{
                $(this).append("<i class='selectedDay'></i>");
        }

        var indexArr = []; //保存下标
        var startIndex = -1;
        var endIndex = -1;
        for(var i = 0 ;i < clength; i ++){
            //如果有选中的
            if($(".currentMonth0").eq(i).children(".selectedDay").length > 0){
                // console.log($(".currentMonth0").eq(i));
                indexArr.push(i);
                startIndex = indexArr[0];
                endIndex = indexArr[indexArr.length-1];
                if(indexArr.length>=2){  //选中两个日期，第三次重新赋值
                    flag = true;
                    break;
                }else{
                    flag = false;
                }
            }
        }
        //两个日期之间的日期全部选中
        for(var j = 0 ;j < clength; j ++){
            $(".currentMonth0").eq(j).children(".selectedDay").remove();
            if(j>=startIndex&&j<=endIndex){
                if(!$(".currentMonth0").eq(j).hasClass('no-ticket'))
                $(".currentMonth0").eq(j).append("<i class='selectedDay'></i>");
            }
        }

         //如果有选中的，将其添加到数组，并发送post请求给后台
        var selectDate = [];
        for(var i = 0 ;i < clength; i ++){
            if($(".currentMonth0").eq(i).children(".selectedDay").length > 0){//当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }  
        }        
        var date = selectDate.join();//数组中的所有元素放入一个字符串
        console.log(date);
        $.post("getVillaRes",{"date":date},function(datas){
            if (datas.status==0) {
                var selected = datas.data.selected;
                if (selected!="") {
                    alert("所选日期中，"+selected+"已被预定");
                }else{
                    var dateHundred=0;
                    var dateSix=0;
                    for(var x=0;x<selectDate.length;x++){
                        var myDate = new Date(selectDate[x]);//将字符串转为对象
                        if(myDate.getDay()==0 || myDate.getDay()==5 || myDate.getDay()==6){
                            dateHundred++;
                        }else{
                            dateSix++;  
                        }
                    }
                    console.log("100元"+dateHundred+"天,66元"+dateSix+"天");
                }
            }
        },"json");
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



//计算价格
    // var lenght = $("input[name='villa-radio']:checked").length;//用户选择别墅个数
    // var personNumber = $(".villa-choose-content input").val();//报名人数

var realName="";
var peopleNumber ="";
var realNumber = "";
var villaNames = [];//用于存储别墅的数组
var villaName="";
var re = /^[1-9]+[0-9]*]*$/; //正整数
var date = "";
var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 

    //填写信息后，点击支付
    $(".footer2-toPay").click(function(){
        var clength = $(".currentMonth0").length;
        var selectDate = []; //用于选中日期的数组
        //如果有选中的，将其添加到数组
        for(var i = 0 ;i < clength; i ++){
            if($(".currentMonth0").eq(i).children(".selectedDay").length > 0){//当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }  
        }
        // console.log(JSON.stringify(selectDate));//将对象转化为json字符串
        // console.log(selectDate.join());//数组中的所有元素放入一个字符串
        
        date = selectDate.join();//数组中的所有元素放入一个字符串
        realName = $("input[name='realName']").val();//获取姓名
        peopleNumber = $(".villa-choose-content input").val();//获取报名人数

        villaNames=[];
        $("input[name='villa-radio']:checkbox").each(function () {//便利所有checkbox，
            if ($(this).is(":checked")) {
                villaNames.push($(this).attr("value"));
            }
        });
        villaName = villaNames.join();//数组中的所有元素放入一个字符串

        realNumber = $("input[name='realNumber']").val();//获取身份证号码

        if (re.test(peopleNumber) && peopleNumber>0) {//报名人数不为空并且是正整数
            if ($("input[name='villa-radio']:checked").length>0) {//别墅选择的个数大于0
                if ($(".currentMonth0").children(".selectedDay").length > 0) {//当月存在被选中日期
                    if (realName!="") {//真实姓名不为空
                        if (reg.test(realNumber)) {
                            $.cookie("realNumber",realNumber);//身份证号码
                            $.cookie("realName",realName);//真实姓名
                            $.cookie("date",date);//选择日期
                            $.cookie("peopleNumber",peopleNumber);//选择人数
                            $.cookie("villaName",villaName);//别墅名称
                            console.log(realName+"/"+date+"/"+peopleNumber+"/"+villaName+"/"+realNumber)
                            $(".layer").show();
                            $(".payBox").show(); 
                        }else{
                            alert("身份证格式不对");
                        } 
                    }else{
                        alert("输入您的真实姓名");
                    }
                }else{
                    alert("请选择报名日期");
                }
                
            }else{
                alert("请选择别墅");
            }
            
        }else{
            alert("请填写正确的报名人数");
        }
    });


    //选择支付方式后点击确认
    $(".toPay").click(function(){
        var payMode = $("input[name='payMode-radio']:checked").val();
        console.log(payMode);
        if (payMode=="JD") {//京东支付
            $.post("createVillaOrder",{"villaName":villaName,"date":date,"peopleNumber":peopleNumber,"realName":realName},function(datas){
                if (datas.status==0) {
                    var orderNumber = datas.data.orderNumber;
                    console.log(orderNumber);
                    $(".layer").hide();
                    $(".payBox").hide();
                    //是别墅，需要先判断当前选择的别墅是否已经被预定
                    $.post("villaCheck",{"ordernumber":orderNumber},function(datas){
                        if (datas.status==0) {
                            $.post("JDPay",{"ordernumber":orderNumber},function (data) {
                                if(data.status == 0){
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
                            },'json');
                        }else{
                            alert(dats.msg);//您的套餐中别墅或日期已被预约
                        }
                    },"json");
                }
            },"json");
            
        }else if (payMode=="aliPay") {//支付宝支付
            $.post("createVillaOrder",{"villaName":villaName,"date":date,"peopleNumber":peopleNumber,"realName":realName},function(datas){
                if (datas.status==0) {
                    var orderNumber = datas.data.orderNumber;
                    console.log(orderNumber);
                    $(".layer").hide();
                    $(".payBox").hide();
                    //是别墅，需要先判断当前选择的别墅是否已经被预定
                    $.post("villaCheck",{"ordernumber":orderNumber},function(datas){
                        if (datas.status==0) {
                            window.location.href="payHint.html?ordernumber="+orderNumber;
                        }else{
                            alert(dats.msg);//您的套餐中别墅或日期已被预约
                        }
                    },"json");
                }
            },"json");
        }      
    });
    
    //关闭弹窗
    $(".payBox p").click(function(){
        $(".layer").hide();
        $(".payBox").hide();
    });

});
