$(function() {

  getUserCoin(); //获取用户推广币和普通币
  getAwards(0); //获取奖品兑换列表


  $(".prizes").click(function() {
    //     if(typeof(WeixinJSBridge)!="undefined"){  
    // WeixinJSBridge.call('closeWindow');  
    // }else{  
    //   if (navigator.userAgent.indexOf("MSIE") > 0) {    
    //     if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {    
    //       window.opener = null; window.close();    
    //     } else {    
    //       window.open('', '_top'); window.top.close();    
    //     }    
    //   } else if (navigator.userAgent.indexOf("Firefox") > 0) {    
    //     window.location.href = 'about:blank ';    
    //     //window.history.go(-2);    
    //   } else {    
    //     window.opener = null;     
    //     window.open('', '_self', '');    
    //     window.close();    
    //   }  
    // }
  });
});

//获取用户推广币和普通币
function getUserCoin() {
  $.post("getUserCoin", {}, function(datas) {
    if (datas.status == 0) {
      $(".spread-gold").html(datas.data.userCoin.coin);
      $(".normal-gold").html(datas.data.userCoin.generalCoin);
    }
  }, "json");
}
//获取奖品兑换列表
function getAwards(filter) {
  $.post("getAwards", {
    "filter": filter
  }, function(datas) {
    if (datas.status == 0) {
      var awardsList = datas.data.awards;
      var awardsListHtml = "";
      $.each(awardsList, function(index, comment) {
        if (comment.status == 0) {
          var duihuanStatus = '';
        } else if (comment.status == 1) {
          var duihuanStatus = '<div class="duihuan-content-hint-xianshi">限时</div>';
        }
        if (comment.status == 2) {
          var duihuanStatus = '<div class="duihuan-content-hint-youhui">优惠</div>';
        }
        if (comment.status == 3) {
          var duihuanStatus = '<div class="duihuan-content-hint-yiduiwan">已兑完</div>';
        }
        if (comment.status == 4) {
          var duihuanStatus = '<div class="duihuan-content-hint-xinpin">新品</div>';
        }
        awardsListHtml += '<div class="duihuan-content" awardId="' + comment.awardId + '">' + '<img src="' + comment.image + '" height="80px" width="80px">' + duihuanStatus + '<div class="duihuan-content-text">' + '<p class="name">' + comment.awardName + '</p>' + '<div class="duihuan-money">' + '<span>' + comment.coin + '</span>' + '<img src="../images/spreadWChat/spreadGold.png" height="20px">' + '<span>' + comment.generalCoin + '</span>' + '<img src="../images/spreadWChat/normalGold.png" height="20px">' + '</div>' + '<p class="shengyu">剩余：' + comment.amount + '</p>' + '</div>' + '</div>'
      });
    }
    $(".duihuan-list").html(awardsListHtml);
    $(".duihuan-content").click(function() {
      var awardId = $(this).attr("awardId");
      window.location.href = "prizesInfo.html?awardId=" + awardId;
    });
  }, "json");
}