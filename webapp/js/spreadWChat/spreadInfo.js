var awardId = "";
var addressId = "";

function ShowMessage() {
  var thisURL = document.URL;
  var getval = thisURL.split('?')[1];
  awardId = getval.split("=")[1];
}
$(function() {
  $(".chooseAdress").click(function() {
    window.location.href = "chooseAddress.html"
  })
  ShowMessage();
  getadAddress();

  $.post("getAward", {
    "awardId": awardId
  }, function(datas) {
    if (datas.status == 0) {
      var awardData = datas.data.award;
      $(".awardImg").attr("src", awardData.image);
      $(".awardName").html(awardData.awardName);
      $(".coin").html(awardData.coin);
      $(".generalCoin").html(awardData.generalCoin);
      $(".awardIntro").html(awardData.awardIntro);
    }
  }, "json");

  //删除
  $(".duihuan").click(function() {
    if (addressId === "") {
      modalHintText("您的地址还没选择呢");
      return;
    }
    console.log(addressId);
    openModalHint(); //打开确认弹窗
    $(".modalHint-footer-sure").click(function() {
      $.post("insertExchanges", {
        "addressId": addressId,
        "awardId": awardId
      }, function(datas) {
        if (datas.status == 0) {
          $(".modalHint-body").html("兑换成功")
          $(".modalHint-footer").html('<a href="exchangeInfo.html?exchangeId=' + datas.data.exchangeId + '"><i class="modalHint-footer-ok">立即查看</i></a><i class="modalHint-footer-cancel">我知道了</i>')

        } else {
          closeModalHint(); //关闭弹窗
          modalHintText(datas.msg);
        }
      }, "json");
      $('.modalHint-footer-sure').unbind("click"); //移除click
    });

  });
});

//获取地址
function getadAddress() {
  if ($.cookie("addressData") != null && $.cookie("addressData") != "") {
    addressId = $.cookie("addressId");
    $(".addressDetail").html($.cookie("addressData"));
  } else {
    $.post("selectDefaultAddress", {}, function(datas) {
      if (datas.status == 0) {
        addressId = datas.data.address.addressId;
        $(".addressDetail").html(datas.data.address.detail);
      }
    }, "json");
  }
}