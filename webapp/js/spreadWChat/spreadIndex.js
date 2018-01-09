$(function() {
  $.get("../getQrcode", function(datas) {
    if (datas.status == 0) {
      $(".codeImg").attr("src", "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + datas.data.ticket);
    }
  }, "json");
  $.get("../getConcern", function(datas) {
    if (datas.status == 0) {
      var concernData = datas.data.ticket;
      $(".concerned").html(concernData.concerned + "人");
      $(".unconcern").html(concernData.unconcern + "人");
      $(".concern").html(concernData.concern + "人");
      $(".onconcern").html(concernData.onconcern + "人");
    }
  }, "json");
  $(".xiala").click(function() {
    if ($(".info-text").height() == 54) {
      $(".info-text").css({
        "height": "auto"
      });
      $(this).find("span").html("点我上拉");
      $(this).find(".bottomImage").css({
        "transform": "rotate(180deg)"
      });
    } else {
      $(".info-text").css({
        "height": "54px"
      });
      $(this).find("span").html("点我下拉");
      $(this).find(".bottomImage").css({
        "transform": "rotate(0deg)"
      });
    }
  });
})