$(function() {
    document.getElementById("fangxiangpang").addEventListener('touchstart', function() {}); //忘记了做什么用的，按钮动画触发？


    if ($(document).height() > 800) {
        $(".dsListContent").css({
            "max-height": "420px"
        });
    }

    $.post("getRecommend", {}, function(datas) {
        if (datas.status == 0) {
            $(".content").css({
                "padding-bottom": "20px"
            });
            $(".content").fadeIn(500);
            $(".dsListBox").fadeIn(500);
            var dsListHtml = "";
            var dsListDatas = datas.data.packages;
            $.each(dsListDatas, function(commentIndex, comment) {
                dsListHtml += '<div class="dsList" makeProtection="' + comment.makeProtection + '" packageid="' + comment.packageid + '" description="' + comment.description + '" brand="' + comment.brand + '"><div class="dsImageBox"><img src="' + comment.imageUrl + '"><h2 class="dsName">' + comment.dsName + '</h2></div><div class="dsContent"><p class="dsType">' + comment.dsType + '</p><p class="priceBox">¥<span class="price">' + comment.price + '</span></p><div class="spanBox"><span class="models">' + comment.models + '</span><span class="trainTime">' + comment.trainTime + '</span></div></div></div>';
            });
            $(".dsListContent").html(dsListHtml);
            $(".dsList").click(function() {
                var dsname = $(this).find(".dsName").html();
                var dstype = $(this).find(".dsType").html();
                var models = $(this).find(".models").html();
                var price = $(this).find(".price").html();
                var traintime = $(this).find(".traintime").html();
                var packageid = $(this).attr("packageid");
                var description = $(this).attr("description");
                var makeProtection = $(this).attr("makeProtection");
                var brand = $(this).attr("brand");
                $.cookie("dsname", dsname); //驾校名字
                $.cookie("dstype", dstype); //班型套餐
                $.cookie("models", models); //班型类别，如C1，C2
                $.cookie("price", price); //班型套餐价格
                $.cookie("packageid", packageid); //班型编号id
                $.cookie("traintime", traintime); //训练时间
                $.cookie("description", description); //套餐描述
                $.cookie("makeProtection", makeProtection); //补考保障
                $.cookie("brand", brand); //套餐描述
                window.location.href = "ds_apply.html";
            })
            $(".again").click(function() {
                $(".again").unbind("click");
                console.log("我点击了重新筛选1");
                $(".dsListBox").hide();
                $(".content").css({
                    "padding-bottom": "0px"
                });
                $(".content").hide();
                gogo();
            })
        } else {
            gogo();

        }
    }, "json");


});

function gogo() {
    var i = 1;
    var classQuestion = ""; //第几个question
    var questions = []; //存放用户填写的信息数组

    $(".question1").show();
    $(".content").show();
    $(".bg").show();
    $(".right").show();
    $(".fangxiangpang").show();
    //为radio设置点击样式
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
            if (i > 6) {
                console.log("显示结果，i=" + i);
                $(".car").hide();
                $(".right").hide();
                $(".bg").fadeOut(100);
                $(".textBox").hide();
                $(".fangxiangpang").hide();
                $(".content").fadeOut(100);

                console.log("最终的答案是", $.cookie("question"));
                var questionDatas = {
                    "models": questions[0],
                    "shortTerm": questions[1],
                    "workDay": questions[2],
                    "customize": questions[3],
                    "scale": questions[4],
                    "price": questions[5]
                }
                $.post("setRecommend", questionDatas, function(datas) {
                    if (datas.status == 0) {
                        $(".content").css({
                            "padding-bottom": "20px"
                        });
                        $(".content").fadeIn(500);
                        $(".dsListBox").fadeIn(500);
                        var dsListHtml = "";
                        var dsListDatas = datas.data.packages;
                        $.each(dsListDatas, function(commentIndex, comment) {
                            dsListHtml += '<div class="dsList" makeProtection="' + comment.makeProtection + '" packageid="' + comment.packageid + '" description="' + comment.description + '" brand="' + comment.brand + '"><div class="dsImageBox"><img src="' + comment.imageUrl + '"><h2 class="dsName">' + comment.dsName + '</h2></div><div class="dsContent"><p class="dsType">' + comment.dsType + '</p><p class="priceBox">¥<span class="price">' + comment.price + '</span></p><div class="spanBox"><span class="models">' + comment.models + '</span><span class="trainTime">' + comment.trainTime + '</span></div></div></div>';
                        });
                        $(".dsListContent").html(dsListHtml);
                        $(".dsList").click(function() {
                            var dsname = $(this).find(".dsName").html();
                            var dstype = $(this).find(".dsType").html();
                            var models = $(this).find(".models").html();
                            var price = $(this).find(".price").html();
                            var traintime = $(this).find(".traintime").html();
                            var packageid = $(this).attr("packageid");
                            var description = $(this).attr("description");
                            var makeProtection = $(this).attr("makeProtection");
                            var brand = $(this).attr("brand");
                            $.cookie("dsname", dsname); //驾校名字
                            $.cookie("dstype", dstype); //班型套餐
                            $.cookie("models", models); //班型类别，如C1，C2
                            $.cookie("price", price); //班型套餐价格
                            $.cookie("packageid", packageid); //班型编号id
                            $.cookie("traintime", traintime); //训练时间
                            $.cookie("description", description); //套餐描述
                            $.cookie("makeProtection", makeProtection); //补考保障
                            $.cookie("brand", brand); //套餐描述
                            window.location.href = "ds_apply.html";
                        })
                        $(".again").click(function() {
                            $(".again").unbind("click");
                            console.log("我点击了重新筛选2");
                            $(".dsListBox").hide();
                            $(".content").css({
                                "padding-bottom": "0px"
                            });
                            $(".content").hide();
                            gogo();

                        })
                    }
                }, "json");
                $(".fangxiangpang").unbind("click");

            } else {
                console.log("显示第i=" + i + "题");
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
}