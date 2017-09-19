var sex = "";
function ShowMessage() 
{ 
   var thisURL = document.URL;    
   var getval = thisURL.split('?')[1];  
   sex = getval.split("=")[1];  
} 
window.onload=ShowMessage(); 
$(function(){
	if (sex=="1") {
		$(".girl img").hide();
	}else if(sex=="0"){
		$(".boy img").hide();
	}else{
		$(".girl img").hide();
		$(".boy img").hide();
	}
	$(".boy").click(function(){
		window.location.href="user.html";
		$.post("changeInfo.action",{"sex":1},function(obj){
		},'json');
	});
	$(".girl").click(function(){
		window.location.href="user.html";
		$.post("changeInfo.action",{"sex":0},function(obj){
		},'json');
	});

});