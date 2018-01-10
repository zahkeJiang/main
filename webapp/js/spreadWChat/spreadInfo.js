var awardId = "";

function ShowMessage() {
  var thisURL = document.URL;
  var getval = thisURL.split('?')[1];
  awardId = getval.split("=")[1];
}
$(function() {
  $(".chooseAdress").click(function() {
    window.location.href = "../chooseAddress.html"
  })
  ShowMessage();
  if ($.cookie("addressData") != null && $.cookie("addressData") != "") {
    $(".addressDetail").html($.cookie("addressData"));
  };

  $.post("../getAward", {
    "awardId": awardId
  }, function(datas) {
    if (datas.status == 0) {
      var awardData = datas.data.award;
      $(".awardImg").attr("src", awardData.image);
      $(".awardName").html(awardData.awardName);
      $(".coin").html(awardData.coin);
      $(".generalCoin").html(awardData.generalCoin);
      $(".awardIntro").html(awardData.awardIntro);
      if ($.cookie("addressData") == null || $.cookie("addressData") == "") {
        if (awardData.detail != "") {
          $(".addressDetail").html(awardData.detail);
        }
      }
    }
  }, "json");

});