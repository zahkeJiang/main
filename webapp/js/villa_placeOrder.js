$(function(){

     //日历渲染--------------------------------------------------------
    renderHtml(0);
    // 表格中显示日期
    showCalendarData(0,0);
    //展示价格
    setPrice(getYandM(0).dataPara+"");

//    日历结束
//    点击上一月
    var monthNum = 0;
    $('.sit-pre-month').on('click',function(e){
        e.preventDefault();
        // 第二个参数 0为本月 上一个月1，下一个月-1
        ++monthNum;
        showCalendarData(0,monthNum);
        setPrice(getYandM(monthNum).dataPara+"");
    });
//    点击下一月
    $('.sit-next-month').on('click',function(e){
        e.preventDefault();
        --monthNum;
        showCalendarData(0,monthNum);
        setPrice(getYandM(monthNum).dataPara+"");
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

var realName="";
var peopleNumber ="";
var realNumber = "";
var villaNames = [];//用于存储别墅的数组
var villaName="";
var re = /^[1-9]+[0-9]*]*$/; //正整数
var date = "2017-11-11";
var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
    //填写信息后，点击支付
    $(".footer p:last-child").click(function(){
        realName = $("input[name='realName']").val();//获取姓名
        peopleNumber = $(".villa-choose-content input").val();//获取报名人数

        villaNames=[];
        $("input[name='villa-radio']:checkbox").each(function () {//便利所有checkbox，
            if ($(this).is(":checked")) {
                villaNames.push($(this).attr("value"));
            }
        });
        villaName = JSON.stringify(villaNames);//将对象转化为json字符串

        realNumber = $("input[name='realNumber']").val();//获取身份证号码

        if (re.test(peopleNumber) && peopleNumber>0) {//报名人数不为空并且是正整数
            if ($("input[name='villa-radio']:checked").length>0) {//别墅选择的个数大于0
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
            
        }else if (payMode=="aliPay") {//支付宝支付
            $.post("createVillaOrder",{"villaName":villaName,"date":date,"peopleNumber":peopleNumber,"realName":realName},function(datas){
                if (datas.status==0) {
                    var orderNumber = datas.data.orderNumber;
                    console.log(orderNumber);
                    // window.location.href="payHint.html?orderNumber="+orderNumber;
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
