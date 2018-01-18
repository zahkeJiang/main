var awardId = "";

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

});

//获取地址
function getadAddress() {
  if ($.cookie("addressData") != null && $.cookie("addressData") != "") {
    $(".addressDetail").html($.cookie("addressData"));
  } else {
    $.post("selectDefaultAddress", {}, function(datas) {
      if (datas.status == 0) {
        $(".addressDetail").html(datas.data.address.detail);
      }
    }, "json");
  }
}