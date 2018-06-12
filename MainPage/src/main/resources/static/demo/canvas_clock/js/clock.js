var ctx = document.getElementById("clock").getContext("2d");
var w = ctx.canvas.width;
var h = ctx.canvas.height;
var r = w / 2;
//相对于200px的比例值，因为以下的常量都只在宽高为200的情况下最恰当，
//为了使时钟扩大或缩小后不变形，所有常量要乘以scale
var scale = w / 200 * 1.0;

function drawBackground() {
	//把绘制圆点移到中心
	ctx.translate(r, r);

	//绘制背景的外部圆圈
	ctx.beginPath();
	ctx.strokeStyle = "blueviolet";
	ctx.lineWidth = 10 * scale;
	ctx.arc(0, 0, r - 5 * scale, 0, 2 * Math.PI, false);
	ctx.stroke();

	//绘制背景的60个圆点
	for(var i = 0; i < 60; i++) {
		var rad = (2 * Math.PI / 60) * i;
		var x = (r - 18 * scale) * Math.cos(rad);
		var y = (r - 18 * scale) * Math.sin(rad);
		ctx.beginPath();
		ctx.fillStyle = i % 5 == 0 ? "#000" : "#A9A9A9";
		ctx.arc(x, y, 2 * scale, 0, 2 * Math.PI, false);
		ctx.fill();
	}

	//绘制背景的12个数字
	var numberList = [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2];
	var lomaList = ["Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ", "Ⅶ", "Ⅷ", "Ⅸ", "Ⅹ",
		"ⅩⅠ", "ⅩⅡ", "Ⅰ", "Ⅱ"
	]
	ctx.textAlign = "center";
	ctx.textBaseline = "middle";
	ctx.fillStyle = "black";
	ctx.font = 18 * scale + "px Arial";
	for(var i = 0; i < numberList.length; i++) {
		var rad = (2 * Math.PI / 12) * i;
		var x = (r - 30 * scale) * Math.cos(rad);
		var y = (r - 30 * scale) * Math.sin(rad);
		ctx.beginPath();
		ctx.fillText(numberList[i], x, y);
	}
}

function drawHour(hour, minute) {
	ctx.save();

	ctx.beginPath();
	ctx.strokeStyle = "black";
	ctx.lineWidth = 6 * scale;
	//直线两端为半圆
	ctx.lineCap = "round";
	//向右旋转
	var rad = (2 * Math.PI / 12) * (hour + (minute / 60));
	ctx.rotate(rad);
	//把画笔的起始点放到中心偏下10px的位置
	ctx.moveTo(0, 10 * scale);
	//把画笔的结束点放到中心偏上40px的位置
	ctx.lineTo(0, -45 * scale);
	//从起始点到结束点绘制直线
	ctx.stroke();

	ctx.restore();
}

function drawMinute(minute) {
	ctx.save();

	ctx.beginPath();
	ctx.strokeStyle = "black";
	ctx.lineWidth = 4 * scale;
	ctx.lineCap = "round";
	var rad = (2 * Math.PI / 60) * minute;
	ctx.rotate(rad);
	ctx.moveTo(0, 20 * scale);
	ctx.lineTo(0, -65 * scale);
	ctx.stroke();

	ctx.restore();
}

function drawSecond(second) {
	ctx.save();

	ctx.beginPath();
	ctx.fillStyle = "red";
	ctx.lineWidth = 2 * scale;
	ctx.lineCap = "round";
	var rad = (2 * Math.PI / 60) * second;
	ctx.rotate(rad);
	ctx.moveTo(-2 * scale, 8 * scale);
	ctx.lineTo(2 * scale, 8 * scale);
	ctx.lineTo(1 * scale, -85 * scale);
	ctx.lineTo(-1 * scale, -85 * scale);
	ctx.fill();

	ctx.restore();
}

function drawCentralDot() {
	ctx.beginPath();
	ctx.fillStyle = "white";
	ctx.arc(0, 0, 4 * scale, 0, 2 * Math.PI, false);
	ctx.fill();
}

function draw() {
	ctx.clearRect(0, 0, w, h);
	ctx.save();

	var date = new Date();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();

	drawBackground();
	drawHour(hour, minute);
	drawMinute(minute);
	drawSecond(second);
	drawCentralDot();

	ctx.restore();
}

draw();
setInterval(draw, 1000);