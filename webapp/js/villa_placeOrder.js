var hundredDate = 0;//初始化100天数
var sixtysixDate = 0;//初始化66天数
// var joinNumber = 0;//报名人数

var realName="";
var peopleNumber ="";
var realNumber = "";
var villaNames = [];//用于存储别墅的数组
var villaName="";
var re = /^[1-9]+[0-9]*]*$/; //正整数
var date = "";
var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 

$(function(){
    //获取用户手机号码
    $.post("personal.action",{},function(datas){
        if (datas.status==0) {
            var userobj = datas.data.userInfo;
            $('.villa-user p.tel').html(userobj.phoneNumber);
        }
    },"json");


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

        villaDateCheck();//获取用户选择日期，请求后台，判断所选别墅是否被预定，并显示明细

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

        villaDateCheck();//获取用户选择日期，请求后台，判断所选别墅是否被预定，并显示明细

    });

    //别墅选择自定义点击样式,选择时与未选择时。
    $(".villa-radioBox").click(function() {
        if ($(this).find(".villa-choose img").length==0) {
            $(this).find(".villa-choose").html('<img src="./images/circle_choose.png" height="20px" width="20px">');
            $(this).find("input[name='villa-radio']").attr("checked","true");
        }else{
            $(this).find(".villa-choose").empty();
            $(this).find("input[name='villa-radio']").removeAttr("checked");
        }
        console.log($("input[name='villa-radio']:checked").length+"栋别墅");

        //更改别墅时，将对总价进行调整
        getPrice();
    });
    //为支付方式的radio设置自定义点击样式
    $(".payMode").click(function() {
        $(this).children(".payModeImg").css({"background":"url(./images/circle_choose.png)","background-size":"20px"});
        $(this).parents().siblings("label").find(".payModeImg").css({"background":"url(./images/circle.png)","background-size":"20px"});
    });

    //时时监听报名人数变动，别墅选择变动
    $(".villa-choose-content input").bind('input porpertychange',function(){
        //更改报名人数时，将对总价进行调整
                    getPrice();
    });

    //军旅不设立保险，所有注掉
    // $(".villa-secure-content input").bind('input porpertychange',function(){
    //     //更改购买保险人数时，将对总价进行调整
    //                 getPrice();
    // });

    
    //明细
    $(".footer-detail").click(function(){
        if ($(".detailBox").css("display")=="none") {
            $(".detailBox").show();
            $(this).find("img").attr("src","./images/up.png");
        }else{
            $(".detailBox").hide();
            $(this).find("img").attr("src","./images/bottom.png");
        }
        
    });


    //全额支付与预约定金
    $(".Reserve-row").click(function(){
        $(this).children(".Reserve1").html("<img src='./images/circle_choose.png' height='18px' width='18px'>");
        $(this).parents().siblings("label").find(".Reserve1").empty();
        //付款类型，1全额，或0预约定金
        if ($("input[name='Reserve1Radio']:checked").val()==0) {
            $(".reserve1-mode").html("付款类型：预约定金");

        }else{
            $(".reserve1-mode").html("付款类型：全额支付");

        }
        getPrice();
    });

    //是否阅读北京漂洋过海房屋守则
    $(".roomCode").click(function(){
        if ($(this).find("img").length==0) {
            $(".roomCode").html("<img src='./images/circle_choose.png' height='16px' width='16px'>");
        }else{
            $(".roomCode").empty();
        }
    });
    
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
                            if ($(".roomCode").find("img").length==1) {
                                $.cookie("realNumber",realNumber);//身份证号码
                                $.cookie("realName",realName);//真实姓名
                                $.cookie("date",date);//选择日期
                                $.cookie("peopleNumber",peopleNumber);//选择人数
                                $.cookie("villaName",villaName);//别墅名称
                                console.log(realName+"/"+date+"/"+peopleNumber+"/"+villaName+"/"+realNumber)
                                $(".layer").show();
                                $(".payBox").show(); 
                            }else{
                                alert("请遵守北京漂洋过海《房屋守则》");
                            }
                            
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
        var reserve = $("input[name='Reserve1Radio']:checked").val();//是否全额,0预约定金，1全款
        console.log(payMode);
        if (payMode=="JD") {//京东支付
            $.post("createVillaOrder",{"villaName":villaName,"date":date,"peopleNumber":peopleNumber,"realName":realName,"idNumber":realNumber,"fullAmount":reserve},function(datas){
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
                            alert(datas.msg);//您的套餐中别墅或日期已被预约
                        }
                    },"json");
                }else{
                    alert("您当前存在多个未支付订单，请勿重复下单。")
                }
            },"json");
            
        }else if (payMode=="aliPay") {//支付宝支付
            $.post("createVillaOrder",{"villaName":villaName,"date":date,"peopleNumber":peopleNumber,"realName":realName,"idNumber":realNumber,"fullAmount":reserve},function(datas){
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
                            alert(datas.msg);//您的套餐中别墅或日期已被预约
                        }
                    },"json");
                }else{
                    alert("您当前存在多个未支付订单，请勿重复下单。")
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

//移除别墅的选中状态和日期的选中状态
function removeCheck(){
    //移除别墅选中状态
    $("input[name='villa-radio']").removeAttr("checked");
    $(".villa-radioBox").find(".villa-choose").empty();
    //移除选中的日期
    $(".currentMonth0").children(".selectedDay").remove();
}

//获取日历价格
function  getPrice(){
                    var moneysix=0;
                    var moneyhundred=0;
                    var villacheck=$("input[name='villa-radio']:checked").length;//用户选择别墅个数
                    var peopleNumber = $(".villa-choose-content input").val();//获取报名人数
                    var reserve = $("input[name='Reserve1Radio']:checked").val();//付款类型，1全额，或0预约定金

                    // var secureNumber = $(".secureNumber").val();//购买保险人数
                    // if (re.test(secureNumber) && secureNumber) {
                    //     //不作处理
                    // }else{
                    //     secureNumber=0;
                    // }
                    if (re.test(peopleNumber) && peopleNumber>0 && villacheck>0 && $(".currentMonth0").children(".selectedDay").length > 0){
                        // $(".detailBox-secure").html("¥"+secureNumber*15);
                        if (sixtysixDate>0) {//66的天数大于0
                            if (peopleNumber*66>1688*villacheck) {
                                moneysix = 66*peopleNumber*sixtysixDate*villacheck;//66x人数x天数x别墅栋数
                                $(".detailBox li.li66").html("<div><span>别墅(66元)</span><span class='detailBox-66'>"+"¥"+moneysix+"</span></div><span class='detailBox-66-hint'>(66元x"+peopleNumber+"人x"+sixtysixDate+"晚)</span>")
                            }else{
                                moneysix = 1688*sixtysixDate*villacheck;//66x人数x天数x别墅栋数
                                $(".detailBox li.li66").html("<div><span>别墅(66元)</span><span class='detailBox-66'>"+"¥"+moneysix+"</span></div><span class='detailBox-66-hint'>(1688元x"+villacheck+"栋x"+sixtysixDate+"晚)</span>")
                            }
                        }else{
                            $(".detailBox li.li66").html("<div><span>别墅(66元)</span><span class='detailBox-66'>¥0</span></div><span class='detailBox-66-hint'>(66元x0人x0晚)</span>")
                        }
                        if (hundredDate>0) {//100的天数大于0
                            if (peopleNumber*100>2888*villacheck) {
                                moneyhundred = 100*peopleNumber*hundredDate*villacheck;//66x人数x天数x别墅栋数
                                $(".detailBox li.li100").html("<div><span>别墅(100元)</span><span class='detailBox-100'>"+"¥"+moneyhundred+"</span></div><span class='detailBox-100-hint'>(100元x"+peopleNumber+"人x"+hundredDate+"晚)</span>")
                            }else{
                                moneyhundred = 2888*hundredDate*villacheck;//66x人数x天数x别墅栋数
                                $(".detailBox li.li100").html("<div><span>别墅(100元)</span><span class='detailBox-100'>"+"¥"+moneyhundred+"</span></div><span class='detailBox-100-hint'>(2888元x"+villacheck+"栋x"+hundredDate+"晚)</span>")
                            }
                        }else{
                            $(".detailBox li.li100").html("<div><span>别墅(100元)</span><span class='detailBox-100'>¥0</span></div><span class='detailBox-100-hint'>(100元x0人x0晚)</span>")
                        }

                        if (reserve==0) {
                            $(".reserve1-mode").html("付款类型：预约定金");
                            $("#footer-price").html("¥"+Math.ceil((moneysix+moneyhundred)/2));
                        }else{
                            $(".reserve1-mode").html("付款类型：全额支付");
                            $("#footer-price").html("¥"+(moneysix+moneyhundred));
                        }
                        
                    }else{
                        $("#footer-price").html("¥0");
                        $(".detailBox li.li100").empty();
                        $(".detailBox li.li66").empty();
                        $(".detailBox-secure").html("¥0");
                    }
}

//获取用户选择日期，请求后台，判断所选别墅是否被预定，并显示明细
function villaDateCheck(){
     var currentMonth0clength = $(".currentMonth0").length;
     //如果有选中的，将其添加到数组,并发送post请求给后台
        var selectDate = [];
        for(var i = 0 ;i < currentMonth0clength; i ++){
            if($(".currentMonth0").eq(i).children(".selectedDay").length > 0){//当月存在被选中日期
                //将选择的天循环添加到selectDate
                selectDate.push($(".currentMonth0").eq(i).children(".selectedDay").siblings(".borderr").attr("thisdaynumber"));
            }  
        }        
        var date = selectDate.join();//数组中的所有元素放入一个字符串
        console.log(date);
        $.post("getVillaRes",{"date":date},function(datas){
            if (datas.status==0) {
                var villaCheckName_xiangtong=[];//所选别墅和已经被预定别墅的相同别墅名称字符串
                //获取选中的别墅
                var villaCheckNames=[];
                
                $("input[name='villa-radio']:checkbox").each(function () {//便利所有checkbox，
                    if ($(this).is(":checked")) {
                        villaCheckNames.push($(this).attr("value"));
                    }
                });
                
                //获取已经被预定的别墅，如果不为空，便利出相同的别墅名称
                var selected = datas.data.selected;
                if (selected!="") {//存在相同的别墅
                    var selectedVillaCheckNames = selected.split(",");//已经被预定的别墅数组

                    for (var i = 0; i < selectedVillaCheckNames.length; i++) {
                        
                        for (var j = 0; j < villaCheckNames.length; j++) {
                            
                            if (selectedVillaCheckNames[i]==villaCheckNames[j]) {
                                villaCheckName_xiangtong.push(selectedVillaCheckNames[i]);
                            }
                            
                        }
                    }
                    console.log(villaCheckName_xiangtong.join());
                    var villaCheckName_xiangtongzifuchuang = villaCheckName_xiangtong.join();

                    if (villaCheckName_xiangtongzifuchuang!="") {
                        alert('所选日期中，"'+villaCheckName_xiangtong+'"已被预定,请重新选择');
                        //移除别墅选中状态
                        // $("input[name='villa-radio']").removeAttr("checked");
                        // $(".villa-radioBox").find(".villa-choose").empty();


                        //移除选中的日期
                        $(".currentMonth0").children(".selectedDay").remove();
                    }else{//不存在相同的别墅
                        var dateHundred=0;//一百天数
                        var dateSix=0;//66天数
                    
                        for(var x=0;x<selectDate.length;x++){
                            var myDate = new Date(selectDate[x]);
                            if(myDate.getDay()==0 || myDate.getDay()==5 || myDate.getDay()==6){
                                dateHundred++;
                            }else{
                                dateSix++;  
                            }
                        
                        }
                        hundredDate = dateHundred;
                        sixtysixDate = dateSix;
                        console.log("100元"+hundredDate+"天,66元"+sixtysixDate+"天");

                        //更改选择的日期时，将对总价进行调整
                        getPrice();
                    }
                }else{//用户选中日期中没有别墅被预定
                    var dateHundred=0;//一百天数
                    var dateSix=0;//66天数
                    
                    for(var x=0;x<selectDate.length;x++){
                        var myDate = new Date(selectDate[x]);
                        if(myDate.getDay()==0 || myDate.getDay()==5 || myDate.getDay()==6){
                            dateHundred++;
                        }else{
                            dateSix++;  
                        }
                        
                    }
                    hundredDate = dateHundred;
                    sixtysixDate = dateSix;
                    console.log("100元"+hundredDate+"天,66元"+sixtysixDate+"天");

                    //更改选择的日期时，将对总价进行调整
                    getPrice();
                }
            }
        },"json");
}
