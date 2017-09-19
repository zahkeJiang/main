$(function(){

    // getLocation();//获取当前的经纬度对应的国家，省份以及城市
    $(".allcity li").click(function(){
      var province =  $(this).children().attr("province");
      var num =  $(this).children().attr("city");
      var provinceurl="citylist.html?province="+province+"&num="+num;   
      window.location.assign(encodeURI(provinceurl));
    });
});




//判断浏览器是否支持定位
function getLocation()  {  
    if (navigator.geolocation)  {  //浏览器支持geolocation
        navigator.geolocation.getCurrentPosition(showPosition,showError);  
    }else{
        alert("未知错误");
    }  
}  
  
  function showPosition(position)
{
  //返回用户位置
  //经度
   var longitude =position.coords.longitude;
  //纬度
  var latitude = position.coords.latitude;
  // alert('经度'+longitude+'，纬度'+latitude);
  //根据经纬度获取地理位置，不太准确，获取城市区域还是可以的
  var map = new BMap.Map("allmap");// 创建地图实例
  var point = new BMap.Point(longitude,latitude);
  var gc = new BMap.Geocoder();
  gc.getLocation(point, function(rs){
    var addComp = rs.addressComponents;//地址服务类
    var location="";
    if (addComp.province==addComp.city) {
      location = "<img src='images/address.png' height='18px' width='18px'>"+"&nbsp;"+"&nbsp;中国&nbsp;"+addComp.province;
    }else{
      location = "<img src='images/address.png' height='18px' width='18px'>"+"&nbsp;"+"&nbsp;中国&nbsp;"+addComp.province + "&nbsp;" + addComp.city;
    }
    $(".getlocation").html(location);
  });   

}
  
function showError(error){  
  switch(error.code){  
    case error.PERMISSION_DENIED:  //Permission denied - 用户不允许地理定位
       alert("您未授权地理定位"); 
      break;  
    case error.POSITION_UNAVAILABLE:  //Position unavailable - 无法获取当前位置
      alert("当前位置信息不可用"); 
      break;  
    case error.TIMEOUT:  //Timeout - 操作超时
     alert("请求超时"); 
      break;  
    case error.UNKNOWN_ERROR:  
      alert("未知错误"); 
      break;  
    }  
  }  