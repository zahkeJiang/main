$(function() {
  var height = $(window).height() - 20;
  var height2 = $(window).height() - 121;
  $(".addressBox").css({
    "height": height
  });
  $(".address-list").css({
    "height": height2
  });
  selectAddresses();
  //点击添加新地址
  $(".newAddress p").click(function() {
    $("input[name='modal-userName']").val("");
    $("input[name='modal-detail']").val("");
    $("input[name='modal-mobile']").val("");
    $(".saveAddress-select").show();
    $(".saveAddress-update").hide();
    $('#myModal').modal({
      keyboard: true
    });
  });

  //添加地址
  $(".saveAddress-select").click(function() {
    updatAddress(0);
  });
});

function updatAddress(addressId) {
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
    var insertAddressUrl = "";
    var hint = "";
    if (addressId == 0) {

      addressData = {
        "userName": userName,
        "detail": detail,
        "mobile": mobile
      }
      insertAddressUrl = "insertAddress";
      hint = "添加成功";
    } else {

      addressData = {
        "addressId": addressId,
        "userName": userName,
        "detail": detail,
        "mobile": mobile
      }
      insertAddressUrl = "updateAddress";
      hint = "更新成功";
    }

    $.post(insertAddressUrl, addressData, function(datas) {
      if (datas.status == 0) {
        location.reload();
        modalHintText(hint);
      }
    }, "json");
  }

}

function selectAddresses() {
  $.post("selectAddresses", {}, function(datas) {
    if (datas.status == 0) {
      var addressData = datas.data.addresses;
      var addressDataHtml = "";
      $.each(addressData, function(item, comment) {
        addressDataHtml += '<div class="address-content address-content-' + comment.addressId + '" addressId="' + comment.addressId + '">' +
          '<div class="choose"></div>' +
          '<div class="address-content-info">' +
          '<div class="address-content-info-header"><span class="address-content-info-name">' + comment.userName + '</span><span class="address-content-info-tel">' + comment.mobile + '</span></div>' +
          '<div class="address-content-info-content"><span class="moren">[默认]</span><span>' + comment.detail + '</span></div>' +
          '</div>' +
          '<div>' +
          '<div><img src="./images/delete.png" height="18px" class="deleteImg"></div>' +
          '<div><img src="./images/edit.png" height="18px" class="editImg"></div>' +
          '</div>' +
          '</div>';

      });
      $(".address-list").html(addressDataHtml);

      //删除
      $(".deleteImg").click(function() {
        var addressId = $(this).parents(".address-content").attr("addressId");
        $.post("deleteAddress", {
          "addressId": addressId
        }, function(datas) {
          if (datas.status == 0) {
            modalHintText("删除成功");
            var hideaddress = ".address-content-" + addressId;
            $(hideaddress).hide();
          }
        }, "json");
      });

      //编辑
      $(".editImg").click(function() {
        $(".saveAddress-select").hide();
        $(".saveAddress-update").show();
        var addressId = $(this).parents(".address-content").attr("addressId");
        $.post("selectAddress", {
          "addressId": addressId
        }, function(datas) {
          if (datas.status == 0) {
            var addressData = datas.data.addresses;
            $("input[name='modal-userName']").val(addressData.userName);
            $("input[name='modal-detail']").val(addressData.detail);
            $("input[name='modal-mobile']").val(addressData.mobile);
            $('#myModal').modal({
              keyboard: true
            });
          }
        }, "json");
        //添加地址
        $(".saveAddress-update").click(function() {
          updatAddress(addressId);
        });
      });
    } else if (datas.status == "-10") {
      var addressDataHtml = '<img src="./images/no_content.png" class="imgBg"><p class="address-hint">主人，您还没有添加地址呢~</p>';
      $(".address-list").html(addressDataHtml);
    }
  }, "json");
}