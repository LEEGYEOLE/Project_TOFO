<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>TOFO</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet"
	href="https://han3283.cafe24.com/js/lightslider/css/lightslider.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="https://han3283.cafe24.com/js/lightslider/js/lightslider.js"></script>
<style type="text/css">
/*이미지 슬라이드 */
ul {
	list-style: none outside none;
	padding-left: 0;
	margin: 0;
}

.slide-content .slide-content {
	margin-bottom: 6px;
}

.slider li {
	text-align: center;
	color: #FFF;
	background-size: cover;
	background-position: center;
}

.slider h3 {
	margin: 0;
	padding: 100px 0;
	height: 250px;
}

.slide-content {
	width: 305%;
	height: 300px;
}

.item1 {
	background-image: url('http://han3283.cafe24.com/images/strawberry.jpg');
}

.item2 {
	background-image: url('http://han3283.cafe24.com/images/cherry.jpg');
}

.item3 {
	background-image: url('http://han3283.cafe24.com/images/strawberry.jpg');
}

.item4 {
	background-image: url('http://han3283.cafe24.com/images/grape.jpg');
}

.item5 {
	background-image: url('http://han3283.cafe24.com/images/lemon.jpg');
}

.item6 {
	background-image: url('http://han3283.cafe24.com/images/grapefruit.jpg');
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#slider").lightSlider({
			mode : 'slide',
			loop : true,
			auto : true,
			keyPress : true,
			pager : false,
			speed : 800,
			pause : 3000
		});
	});
</script>


</head>

<body>

	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	<div class="container">
		<div class="body-container">
			<div class="slide-wrap" style="padding-bottom: 150px;">
				<div class="slide-content">
					<ul id="slider" class="slider">
						<li class="item1">
							<h3></h3>
						</li>
						<li class="item2">
							<h3></h3>
						</li>
						<li class="item3">
							<h3></h3>
						</li>
						<li class="item4">
							<h3></h3>
						</li>
						<li class="item5">
							<h3></h3>
						</li>
						<li class="item6">
							<h3></h3>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
</body>
</html>

