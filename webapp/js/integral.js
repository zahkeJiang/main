$(function() {
  var phone_height = $(window).height(); //获取屏幕高度
  $('.integral_record_container').height(($(window).height()) - 48 - 20 - 20); //设置元素高度
  $('.list').height(($(window).height()) - 48 - 20 - 20); //设置元素高度
  inCoinRecord();
  $(".inCold").click(function() {
    $(this).css({
      "border-bottom": "2px solid #ff7800"
    })
    $(this).siblings("p").css({
      "border-bottom": "2px solid #f6f1f1"
    });
    inCoinRecord();
  })
  $(".outCold").click(function() {
    $(this).css({
      "border-bottom": "2px solid #ff7800"
    })
    $(this).siblings("p").css({
      "border-bottom": "2px solid #f6f1f1"
    });
    outCoinRecord();
  })
});


//获取收入列表
function outCoinRecord() {
  $.post("outCoinRecord", {}, function(datas) {
    if (datas.status == "0") {
      var record = "";
      var record_list = datas.data.coinRecord;
      // $.each循环实现添加订单列表  
      $.each(record_list, function(commentIndex, comment) {
        record += '<div class="list"><div class="integral_record_list"><span class="integral_record_type">' + comment.note + '</span><span class="integral_record_count goldhide' + comment.coindValue + '"><img src="./images/spreadWChat/normalGold.png" class="goldImg"><span class="normalGold">' + comment.coindValue + '</span></span><span class="integral_record_count goldhide' + comment.generalValue + '"><img src="./images/spreadWChat/spreadGold.png" class="goldImg" /><span class="spreadGold">' + comment.generalValue + '</span></span><p class="integral_record_time">' + comment.createTime + '</p></div></div>';
      });
      $('.integral_record_container').empty();
      $('.integral_record_container').html(record);
    } else {
      var record = "<div class='no_record'><img src='images/no_record.png' width='120px'><p>暂无记录</p></div>";
      $('.integral_record_container').html(record);
    }
  }, "json");
}
//获取收入列表
function inCoinRecord() {
  $.post("inCoinRecord", {}, function(datas) {
    if (datas.status == "0") {
      var record = "";
      var record_list = datas.data.coinRecord;
      // $.each循环实现添加订单列表  
      $.each(record_list, function(commentIndex, comment) {
        record += '<div class="list"><div class="integral_record_list"><span class="integral_record_type">' + comment.note + '</span><span class="integral_record_count goldhide' + comment.coindValue + '"><img src="./images/spreadWChat/normalGold.png" class="goldImg"><span class="normalGold">' + comment.coindValue + '</span></span><span class="integral_record_count goldhide' + comment.generalValue + '"><img src="./images/spreadWChat/spreadGold.png" class="goldImg" /><span class="spreadGold">' + comment.generalValue + '</span></span><p class="integral_record_time">' + comment.createTime + '</p></div></div>';
      });
      $('.integral_record_container').empty();
      $('.integral_record_container').html(record);
    } else {
      var record = "<div class='no_record'><img src='images/no_record.png' width='120px'><p>暂无记录</p></div>";
      $('.integral_record_container').html(record);
    }
  }, "json");
}