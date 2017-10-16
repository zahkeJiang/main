var liNuber="";
var satisfaction01="";
var satisfaction02="";
var satisfaction03="";
var imgData=[];
var picture = "";
var pictureData="";
$(function(){


//匿名与否
$(".circle").click(function(){
	if ($(".circle-choose").length > 0) {
		$(".circle-choose").remove();
	}else{
		$(this).append('<div class="circle-choose"></div>');
	}
});
//评价星星
$(".assessBox-row li").click(function(){
	//将当前点击所在的li已取选择状态：星星中间为空
	$(this).siblings("li").find("img").attr("src","./images/starOFF.png");
	//获取当前点击属于第n个li元素
	liNuber = $(this).index()+1;
	if(liNuber==1){
		$(this).parent().siblings(".satisfaction").html("<span star='1'>非常差</span>");
	}else if(liNuber == 2){
		$(this).parent().siblings(".satisfaction").html("<span  star='2'>差</span>");
	}else if(liNuber == 3){
		$(this).parent().siblings(".satisfaction").html("<span  star='3'>一般</span>");
	}else if(liNuber == 4){
		$(this).parent().siblings(".satisfaction").html("<span  star='4'>好</span>");
	}else if(liNuber == 5){
		$(this).parent().siblings(".satisfaction").html("<span  star='5'>非常好</span>");
	}
	//循环将第一个到第liNumber个的li元素进行样式更改
	for (var i = 1; i <= liNuber; i++) {
		$(this).parent().find("li:nth-child("+i+")").find("img").attr("src","./images/starON.png");
	}
});
//提交，如果尚未进行评价，将提示
$(".submit").click(function(){
	satisfaction01= $(".satisfaction01").html();
	satisfaction02= $(".satisfaction02").html();
	satisfaction03= $(".satisfaction03").html();
	if (satisfaction01==""||satisfaction02==""||satisfaction03=="") {
		$(".hint").show();
		setTimeout(function(){$(".hint").hide(1000);},1000);//显示1,秒后进行隐藏
	}else{
		var content = $("textarea[name='assess']").val();
		var star = $(".satisfaction01 span").attr("star");
		pictureData  = JSON.stringify(imgData);//将对象转化为json字符串
		picture = JSON.parse(pictureData);//这样就是把字符串解析 其实就是把外面的中括号去掉；
		var str=imgData.join(",");
		console.log(imgData);
		console.log(pictureData);
		console.log(picture);
		console.log(str);
		$.post("putComment",{"content":content,"star":star,"picture":str,"type":1},function(datas){
			if (datas.status==0) {
				alert("上传成功");
			}else{
				alert("上传失败");
			}
		},"json");
	}
	
});

})
//添加照片
function imgChange() {
    //获取点击的文本框
    var file = document.getElementById("file");
    //存放图片的父级元素
    var imgContainer = document.getElementsByClassName('assessBox-picture')[0];
    //获取的图片文件
    var fileList = file.files;
    //文本框的父级元素
    var imgArr = [];
    //遍历获取到得图片文件
    for (var i = 0; i < fileList.length; i++) {
        var imgUrl = window.URL.createObjectURL(file.files[i]);
        imgArr.push(imgUrl);

        //图片
        var img = document.createElement("img");
        img.setAttribute("src", imgArr[i]);
		    //图片上方删除按钮
        var deleteButton = document.createElement("span");
        deleteButton.setAttribute("class", "delete");

        //创建用于存放图片的div
        var imgAdd = document.createElement("div");
        imgAdd.setAttribute("class", "assessBox-picture-img");

        //添加图片、删除，并加载到父div
        imgAdd.appendChild(img);
        imgAdd.appendChild(deleteButton);
        imgContainer.appendChild(imgAdd);
    };

    if (fileList.length>0) {
      console.log("fileList"+fileList);
      var form = new FormData(document.getElementById("picture_form"));
      console.log("form"+form);
      $.ajax({
        url:"comment_picture",
        type:"post",
        data:form,
        cache: false,
        processData: false,
        contentType: false,
        success:function(datas){
            console.log(datas.data.picture);
            var imgUrl = datas.data.picture;
            // var imgdata = imgUrl;.join(',');
            for (var i = 0; i<imgUrl.length; i++) {
              imgData.push(imgUrl[i]);
            }
            console.log("imgData"+imgData);
        }
      });   
    }

    imgRemove();
};
//删除照片 
function imgRemove() {
  // 目前有bug，添加两次图片后，删除第一个添加的图片，会多删一张；
  $(".delete").click(function(){
    var imgNumber = $(this).parent(".assessBox-picture-img").index(".assessBox-picture-img")+1;//获取当前是第几个图片
    // console.log("imgNumber="+imgNumber);
    imgData.splice(imgNumber-1,1);//删除照片数组的第imgNumber个，第一个为第0个
    console.log("imgData="+imgData);
    $(this).parent().remove();
  });
}
    