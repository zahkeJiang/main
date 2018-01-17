$(function() {
	var madol = document.createElement("div");
	madol.innerHTML = "woshi tangc"
	madol.className = "modal-hintText";
	document.body.appendChild(madol);

	$(".modal-hintText").css({
		"display": "none",
		"position": "fixed",
		"top": "40%",
		"left": "50%",
		"height": "32px",
		"width": "200px",
		"line-height": "32px",
		"text-align": " center",
		"color": "white",
		"border-radius": "10px",
		"margin-left": "-100px",
		"background": "#999",
		"z-index": "10000"
	})
});

//文字提示
function modalHintText(data) {
	$(".modal-hintText").html(data);
	$(".modal-hintText").fadeIn(100);
	setTimeout(function() {
		$(".modal-hintText").fadeOut(1000);
	}, 1000); //显示1,秒后进行隐藏
}