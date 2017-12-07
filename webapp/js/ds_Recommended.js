$(function() {
    var i = 1;
    var classQuestion = ""; //第几个question
    var inputName = ""; //第几个input
    var questions = []; //存放用户填写的信息数组

    // var questionCookie = $.cookie("question");
    // console.log(questionCookie)
    // if (!questionCookie) {
    //     console.log("不存在", );
    //     console.log("最开始");
    $(".question1").show();
    // } else if (questionCookie && questionCookie.split(",").length == 1) {
    //     console.log(questionCookie, questionCookie.split(",").length)
    //     console.log("1存在,现在显示问题二", );
    //     var i = 2;
    // } else if (questionCookie && questionCookie.split(",").length == 2) {
    //     console.log(questionCookie, questionCookie.split(",").length)
    //     console.log("2存在，现在显示问题三", )
    //     var i = 3;
    // } else if (questionCookie && questionCookie.split(",").length == 3) {
    //     console.log(questionCookie, questionCookie.split(",").length)
    //     console.log("3存在,现在显示是问题四");
    //     var i = 4;
    // } else if (questionCookie && questionCookie.split(",").length == 4) {
    //     console.log(questionCookie.split(",").length)
    //     console.log("2存在,现在显示是问题五");
    //     var i = 5;
    // } else if (questionCookie && questionCookie.split(",").length == 5) {
    //     console.log(questionCookie, questionCookie.split(",").length)
    //     console.log("5存在,现在显示是填写完的页面，确认是否重新填写页面");
    //     var i = 6;
    // } else {
    //     console.log(questionCookie, questionCookie.split(",").length)
    //     console.log("出来结果")
    // }
    //为支付方式的radio设置点击样式
    $("li").click(function() {
        $(this).find(".radioDiv").html('<div class="radioDivChild"></div>');
        $(this).siblings("li").find(".radioDiv").empty();
    });



    $(".fangxiangpang").click(function() {
        if ($(".radioDivChild") && $(".radioDivChild").length > 0) {
            var inputName = "input[name='question" + i + "']:checked";
            console.log(inputName);
            var question = $(inputName).val();
            questions.push(question);
            $.cookie("question", questions);
            console.log(questions);
            i++;
            if (i > 5) {
                $(".car").hide();
                $(".right").hide();
                $(".bg").fadeOut(100);
                $(".textBox").hide();
                $(".fangxiangpang").hide();
                $(".content").fadeOut(100);

                console.log("最终的答案是", $.cookie("question"));
                var questionDatas = {
                    shortTerm: questions[0],
                    workDay: questions[1],
                    customize: questions[2],
                    scale: questions[3],
                    price: questions[4]
                }
                $.post("setRecommend", questionDatas, function(datas) {
                    $(".content").css({
                        "padding-bottom": "20px"
                    });
                    $(".content").fadeIn(500);
                    $(".dsListBox").fadeIn(500);
                    if (datas.status == 0) {
                        var dsListHtml = "";
                        var dsListDatas = datas.data.packages;
                        $.each(dsListDatas, function(commentIndex, comment) {
                            dsListHtml += '<div class="dsList"><img src="./images/armyImages/1.png"><div class="dsContent"><h2>' + comment.dsName + '</h2><p>¥' + comment.price + '</p><div><span>' + comment.dsType + '</span><span>' + comment.models + '</span><span>' + comment.trainTime + '</span></div></div></div>';
                        });
                        // dsListHtml = '<div class="dsList"><img src="./images/armyImages/1.png"><div class="dsContent"><h2>海淀驾校</h2><p>¥6000</p><div><span>预约计时班</span><span>C1 手动挡</span><span>全周</span></div></div></div><div class="dsList"><img src="./images/armyImages/1.png"><div class="dsContent"><h2>海淀驾校</h2><p>¥6000</p><div><span>预约计时班</span><span>C1 手动挡</span><span>全周</span></div></div></div><div class="dsList"><img src="./images/armyImages/1.png"><div class="dsContent"><h2>海淀驾校</h2><p>¥6000</p><div><span>预约计时班</span><span>C1 手动挡</span><span>全周</span></div></div></div><div class="dsList"><img src="./images/armyImages/1.png"><div class="dsContent"><h2>海淀驾校</h2><p>¥6000</p><div><span>预约计时班</span><span>C1 手动挡</span><span>全周</span></div></div></div><div class="dsList"><img src="./images/armyImages/1.png"><div class="dsContent"><h2>海淀驾校</h2><p>¥6000</p><div><span>预约计时班</span><span>C1 手动挡</span><span>全周</span></div></div></div>';

                        $(".dsListContent").html(dsListHtml);
                    }
                }, "json");

            } else {
                $(".radioDivChild").remove();

                $(".textBox").fadeOut(500);
                $(".content").fadeOut(200);
                $(".car").fadeOut(500);

                classQuestion = ".question" + i;
                $(classQuestion).fadeIn(1500);
                $(".content").fadeIn(500);
                // $(".car").fadeIn(500);
            }
        }
    });
});