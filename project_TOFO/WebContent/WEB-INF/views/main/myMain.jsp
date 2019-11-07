<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOFO</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">
<style type="text/css">
.mainTb th, tr, td {
	border: 1px solid black;
	border-collapse: collapse;
	border-spacing: 0;
	text-align: center;
}

.mainTb td {
	font-size: 15px;
}

.mainTb {
	border-collapse: collapse;
	height: 550px;
	margin-left: 50px;
	font-size: 20px;
}

.mainTb1 {
	width: 250px;
	background-color: #e4e4e4;
}

.mainTb2 {
	width: 300px;
	background-color: #e4e4e4;
}

.mainTb3 {
	width: 570px;
	background-color: #e4e4e4;
}

.vl {
	border-right: 2px solid;
	border-color: #e4e4e4;
	height: 800px;
	width: 49%;
	margin-top: 10px;
}

.backcolor {
	background: #e4e4e4;
}

.teamImg {
	text-align: center;
	padding: 5% 4%;
	width: 24%;
	height: auto;
	display: inline-block;
	border-right: 3px dotted;
	border-left: 3px dotted;
}

.teamImg:hover {
	background: #ccc;
}

.container-block {
	width: 90%;
	margin: 0 auto;
}

.slider lightSlider lsGrab lSSlide {
	height: 215px;
}

.shortbtn {
	float: left;
}

.modalForm {
	margin-top: 40px;
}
</style>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>


	</div>


	<div class="container">
		<hr>
		<div></div>
		<div class="container-block">
			<div style="width: 100%;">
				<div class="vl" style="float: left;">
					<h2 style="padding: 30px 50px; font-size: 30px;">이번주 일정 목록</h2>
					<table class="mainTb" style="margin-right: 20px;">
						<tr>
							<th class="mainTb1">날짜</th>
							<th class="mainTb2">모임명</th>
							<th class="mainTb3">일정</th>
						</tr>

						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
						<tr>
							<td>04(목)</td>
							<td>My Group1</td>
							<td>오후 18:00[홍대] 웹 페이지 만들기 스터디 / 참여 3명</td>
						</tr>
					</table>
				</div>
				<div class="vl" style="float: left; border-right: none;">
					<h2 style="padding: 30px 50px; float: left; font-size: 30px;">My
						Group</h2>
					<button class="btnConfirm trigger"
						style="float: left; margin-top: 25px;">모임만들기</button>
					<div style="clear: both;">
						<div class="backcolor"
							style="background: #e4e4e4; margin: 0 20px;">
							<div class="teamImg"
								onclick="javascript:location.href='<%=cp%>/schedule/list.do'">
								<img src="<%=cp%>/resource/img/teamwork.png"
									style="width: 150px;">
								<p>맛따라멋따라</p>
							</div>
							<div class="teamImg">
								<img src="<%=cp%>/resource/img/teamwork.png"
									style="width: 150px;">
								<p>맛따라멋따라</p>
							</div>
							<div class="teamImg">
								<img src="<%=cp%>/resource/img/teamwork.png"
									style="width: 150px;">
								<p>맛따라멋따라</p>
							</div>

						</div>


					</div>
				</div>
			</div>
		</div>
		<!-- 팝업 될 레이어 -->
		<div class="modal show-modal">

			<div class="modal-content">

				<div>
					<table id="moo">
						<tr>
							<th>모임 만들기 <span class="close-button">×</span>
							</th>

						</tr>
					</table>
				</div>
				<form class="modalForm" action="#post.php" method="POST">

					<input class="longinput" required="required" type="text"
						placeholder="모임명">

					<textarea class="textwrite" required="required" placeholder="상세설명"></textarea>
					<input class="middleinput" required="required" type="text"
						placeholder="정원">
					<div style="clear: both;">
						<button class="shortbtn" type="button">프로필사진</button>
						<input class="middleinput" placeholder="이미지파일명">
					</div>

					<div style="clear: both;">
						<button class="middlebtn" type="button">인원추가하기</button>
						<p style="padding-top: 20px;">(1/5)</p>
					</div>
					<input class="graylongbtn" type="button" value="등록">

				</form>
			</div>
		</div>
	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
		
			var modal = document.querySelector(".modal");
			var trigger = document.querySelector(".trigger");
			var closeButton = document.querySelector(".close-button");

			function toggleModalOn(s) {
				modal.classList.toggle("show-modal", true);
			}
			function toggleModalOff() {
				modal.classList.toggle("show-modal",false);
			}

			trigger.addEventListener("click", toggleModalOn); //자동으로 로그인 창이 뜸
			closeButton.addEventListener("click", toggleModalOff);
		
			toggleModalOff();
	</script>
</body>
</html>