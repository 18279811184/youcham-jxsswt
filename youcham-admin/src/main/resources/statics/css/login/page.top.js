$(function() {
	// 根据页面高度来判断是否显示按钮；
	$(window).scroll(function(){
		toggleTop(this);
	});
	
	toggleTop(window);
});

// 返回顶部；
function toTop() {
	$("html,body").animate({scrollTop:0},400);
}

// 返回顶部的按钮隐藏；
function toggleTop(obj) {
	if($(obj).scrollTop() > 10){
		$("#goto_top").fadeIn();
	} else {
		$("#goto_top").fadeOut();
	}
}