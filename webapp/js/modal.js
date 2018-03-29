//文字提示
function modalHintText(data) {
	console.log($(".modal-hintText").length);
	if ($(".modal-hintText").length == 0) {
		var madol = document.createElement("div");
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
	}
	$(".modal-hintText").html(data);
	$(".modal-hintText").fadeIn(100);
	setTimeout(function() {
		$(".modal-hintText").fadeOut(1000);
	}, 1000); //显示1,秒后进行隐藏
}



//打开弹窗弹窗
function openModalHint() {
	$(".modalHint-layer").fadeIn(200);
	$(".modalHint").fadeIn(400);
	console.log("开启")
}
//关闭弹窗弹窗
function closeModalHint() {
	$(".modalHint-layer").fadeOut(200);
	$(".modalHint").fadeOut(400);
	console.log("关闭")
}

$(".modalHint-footer-ok").click(function() {
	$(".modalHint-layer").fadeOut(400);
	$(".modalHint").fadeOut(200);
});
//关闭弹窗
$(".modalHint-footer-cancel").click(function() {
	$(".modalHint-layer").fadeOut(200);
	$(".modalHint").fadeOut(400);
	console.log("关闭");
});