<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/table.css"
	type="text/css">
<style>
#style1::-webkit-scrollbar {
	width: 8px;
	background-color: rgba(255, 255, 255, 1);
}

#style1::-webkit-scrollbar-thumb {
	background-color: rgba(128, 128, 128, 0.7);
	border-radius: 5px;
}

.left-box {
	float: left;
	width: 55%;
}

.right-box {
	float: right;
	width: 45%;
}

.left-box2 {
	float: left;
	width: 70%;
}

.right-box2 {
	float: right;
	width: 30%;
}

ul, li {
	list-style: none;
}

.slide {
	height: 400px;
	overflow: hidden;
	position: relative;
	margin-top: 10px;
	/* margin-bottom: 50px; */
	border-radius: 30px;
}

.slide li {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	opacity: 0;
	transition: 1s;
}

.slide li:nth-child(1) {
	background: #ffa;
}

.slide li:nth-child(2) {
	background: #faa;
}

.slide li:nth-child(3) {
	background: #afa;
}

.slide li:nth-child(4) {
	background: #aaf;
}

.slide input {
	display: none;
}

.slide .bullet {
	position: absolute;
	bottom: 20px;
	left: 0;
	right: 0;
	text-align: center;
	z-index: 10;
}

.slide .bullet label {
	width: 10px;
	height: 10px;
	border-radius: 10px;
	border: 2px solid #666;
	display: inline-block;
	background: #fff;
	font-size: 0;
	transition: 0.5s;
	cursor: pointer;
}
/* 슬라이드 조작 */
#pos1:checked ~ ul li:nth-child(1), #pos2:checked ~ ul li:nth-child(2),
	#pos3:checked ~ ul li:nth-child(3), #pos4:checked ~ ul li:nth-child(4)
	{
	opacity: 1;
}
/* bullet 조작 */
#pos1:checked ~ .bullet label:nth-child(1), #pos2:checked ~ .bullet label:nth-child(2),
	#pos3:checked ~ .bullet label:nth-child(3), #pos4:checked ~ .bullet label:nth-child(4)
	{
	background: #666;
}

.myfont {
	font-size: 23px;
	font-weight: 900;
}

.myfont2 {
	font-size: 18px;
	font-weight: 500;
}

.ipb {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 600px;
	height: 400px;
	border-radius: 20px;
}

.ipb2 {
	display: table-cell;
	vertical-align: middle;
	background-color: white;
	width: 3000px;
	height: 100px;
	border-radius: 20px;
	padding: 10px;
}

.ipb3 {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 80%;
	height: 80%;
	border-radius: 20px;
	padding: 10px;
}
</style>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
	</div>
	<div class="container">
		<div class="body-container">
			<div class="container-block">
				<div style="margin: 10px 10px">
					<div class='left-box' style="">
						<div>
							<span style="font-size: 30px; font-family: Webdings">2</span> <span
								style="font-size: 25px;">사진첩</span>
						</div>
						<div class="slide">
							<input type="radio" name="pos" id="pos1" checked> <input
								type="radio" name="pos" id="pos2"> <input type="radio"
								name="pos" id="pos3"> <input type="radio" name="pos"
								id="pos4">
							<ul>
								<li style="background-image: url('a.jpeg')"></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>
							<p class="bullet">
								<label for="pos1">1</label> <label for="pos2">2</label> <label
									for="pos3">3</label> <label for="pos4">4</label>
							</p>
						</div>


						<div
							style="margin-left: 35px; position: relative; width: 500px; bottom: -50px;">
							<img alt="" src="<%=cp%>/resource/images/what.png"
								style="max-width: 300px; height: auto;">
						</div>



						<div class="ipb3">
							<div class="ipb2">
								<div class="ipb2" style="text-align: left; padding-left: 30px;">
									<div style="text-align: right;">
										<a href="">참여인원보기</a>
									</div>
									<div style="float: left; width: 30%">
										<div>
											<p class="myfont">일시</p>
										</div>
										<p class="myfont2">2010-10-11</p>
									</div>

									<div style="float: left; width: 40%">
										<p class="myfont">상세설명</p>
										<div id="style1"
											style="overflow: scroll; overflow-x: hidden; width: 90%; height: 150px;">
											<p class="myfont2">증말루
												에증에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말에증말루증말루증말루증말루증말말루증말루증말루증말루증말루넘ㅊ바리다~~~~dfadfasfasdfadsfasfasd~~~~~~~~~</p>
										</div>
									</div>

									<div style="float: left; width: 30%">
										<p class="myfont">장소</p>
										<p class="myfont2">
											<span>홍대입구역 2번 출구 할리스</span><span><button
													id="click_button" style="margin-left: 5px;">지도</button></span>
										</p>
									</div>


								</div>


								<!-- 지도를 표시할 div 입니다 -->
								<div class="aa" id="staticMap"
									style="max-width: 100%; height: 500px; margin-top: 10px; display: none;"></div>

							</div>

						</div>


					</div>



					<div class='right-box'>
						<span style="font-size: 30px; font-family: Webdings">2</span> <span
							style="font-size: 25px;">후기</span> <span style="float: right"><button>후기등록</button></span>


						<!--날짜가 들어가는 부분-->
						<table
							style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">

							<tr align="left" bgcolor="#eeeeee" height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<th colspan="5" width="60"
									style="color: #787878; padding-left: 15px;">이겨레 님의 후기</th>
							</tr>


							<tr align="left" bgcolor="#ffffff" height="35"
								style="border-bottom: 1px solid #cccccc;">
								<td colspan="5">너무 잼없당 키키 에바링이라구할 수 있찌 글지 ? 집에 갈 시간은 언제인가
									두비두밥 두바두바~ 여기는 내용입니다 내용칸을 표시하기 위한 노력~ 쿄쿄쿄</td>


							</tr>

						</table>
						<!--날짜별 일정이 들어가는 부분-->
						<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
							<tr height="35">
								<td align="center">
									<!--   ${dataCount==0? "등록된 게시글이 없습니다." : paging} --> 1 2 3
								</td>
							</tr>
						</table>
					</div>



				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e99194984cb0bb9f3ba4db6533fbba3c"></script>
	<script>
		// 이미지 지도에서 마커가 표시될 위치입니다 
		var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

		// 이미지 지도에 표시할 마커입니다
		// 이미지 지도에 표시할 마커는 Object 형태입니다
		var marker = {
			position : markerPosition
		};

		var staticMapContainer = document.getElementById('staticMap'), // 이미지 지도를 표시할 div  
		staticMapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 이미지 지도의 중심좌표
			level : 3, // 이미지 지도의 확대 레벨
			marker : marker
		// 이미지 지도에 표시할 마커 
		};

		//이미지 토글 
		$('#click_button').click(
				function() {

					$('.aa').slideToggle(
							'normal',
							function() {
								// 객체가 다 펼치지거나 접히고 나면 여기에 든 내용이 실행된다.
								var staticMap = new kakao.maps.StaticMap(
										staticMapContainer, staticMapOption);
							});

				});
	</script>
</body>
</html>