$(function() {
  var addressIdData = 0;
  var height = $(window).height() - 20;
  var height2 = $(window).height() - 121;
  $(".addressBox").css({
    "height": height
  });
  $(".address-list").css({
    "height": height2
  });
  selectAddresses();
  $(".newAddress p").click(function() {
    $("input[name='modal-userName']").empty();
    $("input[name='modal-detail']").empty();
    $("input[name='modal-mobile']").empty();
    $('#myModal').modal({
      keyboard: true
    });
  });

  //添加地址
  $(".btn-primary").click(function() {
    var userName = $("input[name='modal-userName']").val();
    var detail = $("input[name='modal-detail']").val();
    var mobile = $("input[name='modal-mobile']").val();
    if (userName == "" || !userName) {
      modalHintText("请输入收货人");
      $(".modal-hintText").css({
        "top": "12%"
      });
    } else if (detail == "" || !detail) {
      modalHintText("请输入收货地址");
      $(".modal-hintText").css({
        "top": "12%"
      });
    } else if (mobile == "" || !mobile) {
      modalHintText("请输入手机号码");
      $(".modal-hintText").css({
        "top": "12%"
      });
    } else {
      var addressData = {};
      if (addressData == 0) {
        addressData = {
          "userName": userName,
          "detail": detail,
          "mobile": mobile
        }
      } else {
        addressData = {
          "addressId": addressData,
          "userName": userName,
          "detail": detail,
          "mobile": mobile
        }
      }

      $.post("inserAddress", addressData, function(datas) {
        if (dats.status == 0) {
          modalHintText("添加成功");
        }
      }, "json");
    }

  });
});

function selectAddresses() {
  $.post("selectAddresses", {}, function(datas) {
    if (datas.status == 0) {
      var addressData = datas.data.addresses;
      var addressDataHtml = "";
      $.each(addressData, function(item, comment) {
        addressDataHtml += '<div class="address-content" addressId="' + comment.addressId + '">' +
          '<div class="choose"></div>' +
          '<div class="address-content-info">' +
          '<div class="address-content-info-header"><span class="address-content-info-name">' + comment.userName + '</span><span class="address-content-info-tel">' + comment.mobile + '</span></div>' +
          '<div class="address-content-info-content"><span class="moren">[默认]</span><span>' + comment.addressId + '</span></div>' +
          '</div>' +
          '<div>' +
          '<div><img src="./images/delete.png" height="18px" class="deleteImg"></div>' +
          '<div><img src="./images/edit.png" height="18px" class="editImg"></div>' +
          '</div>' +
          '</div>';

      });
      $(".address-list").html(addressDataHtml);

      $(".deleteImg").click(function() {
        var addressId = $(this).parents(".address-content").attr("addressId");
        $.post("deleteAddress", {
          "addressId": addressId
        }, function(datas) {
          if (datas.status == 0) {
            modalHintText("删除成功");
            $(this).parents(".address-content").hide();
          }
        }, "json");
      });
      $(".editImg").click(function() {

        var addressId = $(this).parents(".address-content").attr("addressId");
        $.post("selectAddress", {
          "addressId": addressId
        }, function(datas) {
          if (datas.status == 0) {
            var addressData = datas.data.addresses;
            $("input[name='modal-userName']").val(addressData.userName);
            $("input[name='modal-detail']").val(addressData.detail);
            $("input[name='modal-mobile']").val(addressData.mobile);
            addressData = addressId;
            $('#myModal').modal({
              keyboard: true
            });
          }
        }, "json");
      });
    }
  }, "json");
}