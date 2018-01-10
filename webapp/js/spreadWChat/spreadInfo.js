var awardId = "";

function ShowMessage() {
  var thisURL = document.URL;
  var getval = thisURL.split('?')[1];
  awardId = getval.split("=")[1];
}
$(function() {

  ShowMessage();
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
    }
  }, "json");
});