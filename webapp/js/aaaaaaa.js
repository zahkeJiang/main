var liNuber="";
var satisfaction01=";"
var satisfaction02=";"
var satisfaction03=";"
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
		$(this).parent().siblings(".satisfaction").html("非常差");
	}else if(liNuber == 2){
		$(this).parent().siblings(".satisfaction").html("差");
	}else if(liNuber == 3){
		$(this).parent().siblings(".satisfaction").html("一般");
	}else if(liNuber == 4){
		$(this).parent().siblings(".satisfaction").html("好");
	}else if(liNuber == 5){
		$(this).parent().siblings(".satisfaction").html("非常好");
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
		$.post("comment_picture",{},function(){

		},"json");
	}
	
});



	// $("#picture_form").click(function(){

	// 	//创建新的input图片文件框
	// 	var form = document.getElementById ("picture_form");
	// 	var input1 = document.createElement('input');
 //    	input1.setAttribute('type', 'file');
 //    	input1.setAttribute('name', 'images[1]');
 //    	input1.setAttribute('multiple', 'multiple');
 //    	input1.setAttribute('onchange', 'imgChange("assessBox-picture")');
 //    	input1.setAttribute('accept', 'image/*');
 //    	input1.setAttribute('value', 'image/*');
 //    	form.appendChild(input1);
	// })
})
//添加照片
var a=1;
var imgfile =[];
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

        imgfile.push(file.files[i]);

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

     // console.log(imgArr);
    console.log("第"+a+"次添加"+(a)+"张");
    // console.log(fileList);
    console.log(imgfile);
    a++;
    // console.log(Arrays.asList(imgfile));
    // alert(stooges)
	// file.files = imgfile;
  //   	j++
  //   	//创建新的input图片文件框
		// var form = document.getElementById ("picture_form");
		// var input1 = document.createElement('input');
  //   	input1.setAttribute('type', 'file');
  //   	input1.setAttribute('name', 'images['+j+']');
  //   	input1.setAttribute('multiple', 'multiple');
  //   	input1.setAttribute('onchange', 'imgChange("assessBox-picture")');
  //   	input1.setAttribute('accept', 'image/*');
  //   	input1.setAttribute('value', 'image/*');
  //   	form.appendChild(input1);
  //   	$("input[name='images["+j+"]']").siblings("input[type='file']").css({"opacity":"0.1"})


    imgRemove();
};
//删除照片 
function imgRemove() {
    $(".delete").click(function(){
    	var ind = $(this).parent(".assessBox-picture-img").index(".assessBox-picture-img")+1;//获取当前点击的span下标
        // alert(ind)//弹出第几个
        $(this).parent().remove();
        console.log(ind)
    })
            
};