var school = "";
function ShowMessage() 
{ 
   var thisURL = decodeURI(location.href);  
   var getval = thisURL.split('?')[1];  
   school = getval.split("=")[1];  
} 
window.onload=ShowMessage(); 
$(function(){
	$("input[name='school']").val(school);
	$(".clear").click(function(){
		$("input[name='school']").val("").focus(); // 清空并获得焦点
	});

	$(".save").click(function(){
		school = $("input[name='school']").val();
		window.location.href="user.html";
		$.post("changeInfo.action",{"school":school},function(obj){	
		},'json');
	});
});