<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String cp = request.getContextPath();

	request.setCharacterEncoding("UTF-8");

	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;

	String sy = request.getParameter("year");
	String sm = request.getParameter("month");

	if (sy != null) {
		year = Integer.parseInt(sy);
	}
	if (sm != null) {
		month = Integer.parseInt(sm);
	}

	cal.set(year, month - 1, 1);

	year = cal.get(Calendar.YEAR);
	month = cal.get(Calendar.MONTH) + 1;

	int week = cal.get(Calendar.DAY_OF_WEEK); // 요일 (1:일, 7:토) 1~7
	int endDay = cal.getActualMaximum(Calendar.DATE); //해당 월의 마지막 일자
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">
<style>
.left-box {
	float: left;
	width: 50%;
}

.right-box {
	float: right;
	width: 50%;
}

.shortbtn {
	margin-top: 3px;
}

.container-block {
	width: 90%;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
	function change() {
		var f = document.yearMonthForm;
		var lenY = f.selectYear.length;
		var lenM = f.selectMonth.length;
		var nowYear, nowMonth;
		for (var i = 0; i < lenY; i++) {
			if (f.selectYear[i].selected) {
				nowYear = f.selectYear[i].value;
				break;
			}
		}
		for (var i = 0; i < lenM; i++) {
			if (f.selectMonth[i].selected) {
				nowMonth = f.selectMonth[i].value;
				break;
			}
		}
		var url = "cal.jsp?year=" + nowYear + "&month=" + nowMonth;
		location.href = url;
	}
</script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
	</div>
	<div class="container">
		<div class="body-container">
			<div class="container-block">
				<div style="margin: 10px 10px;">
					<!----------------div 왼쪽------------------------->

					<div class='left-box'>
						<div style="width: 90%; height: 90%; margin-left: 5%">
							<form name="yearMonthForm" method="post">
								<table
									style="width: 100%; border-spacing: 0; border-collapse: collapse;">
									<tr height="35" align="left">
										<td style="padding-top: 17px;"><select
											style="height: 30px; width: 90px" name="selectYear"
											onchange="change();">
												<%
													for (int i = year - 5; i <= year + 5; i++) {
														if (i == year) {
															out.println("<option value='" + i + "' selected='selected'>" + i + "</option>");
														} else
															out.println("<option value='" + i + "'>" + i + "</option>");
													}
												%>

										</select> <select style="height: 30px; width: 60px" name="selectMonth"
											onchange="change();">
												<%
													for (int i = 1; i <= 12; i++) {
														if (i == month)
															out.println("<option value='" + i + "' selected='selected'>" + i + "</option>");
														else
															out.println("<option value='" + i + "'>" + i + "</option>");
													}
												%>
										</select></td>
									</tr>
								</table>
							</form>
							<table border="1"
								style="width: 100%; border-spacing: 0; border-collapse: collapse;">
								<tr height="30" align="center" bgcolor="#e4e4e4">
									<td width="100" style="color: red;">일</td>
									<td width="100">월</td>
									<td width="100">화</td>
									<td width="100">수</td>
									<td width="100">목</td>
									<td width="100">금</td>
									<td width="100" style="color: blue;">토</td>
								</tr>

								<%
									int col = 0;
									out.println("<tr height='30' align='center'>");

									//1일 앞 공백
									for (int i = 1; i < week; i++) {
										out.println("<td>&nbsp;</td>");
										col++;
									}

									//1~마지막일자까지 출력
									String color;
									for (int i = 1; i <= endDay; i++) {
										color = col == 0 ? "red" : col == 6 ? "blue" : "black";
										out.println("<td style='color:" + color + ";'>" + i + "</td>");
										col++;
										if (col == 7 && i != endDay) {
											out.println("</tr>");

											out.println("<tr height='100' align='center'>");
											for (int j = 0; j < 7; j++) {
												out.println("<td>&nbsp;</td>");
											}
											out.println("</tr>");

											out.println("<tr height='30' align='center'>");
											col = 0;
										}
									}
									//마지막 주의 마지막 일자 출력 후 뒷 부분 공백
									while (col > 0 && col < 7) {
										out.println("<td>&nbsp;</td>");
										col++;
									}
									out.println("</tr>");

									out.println("<tr height='100' align='center'>");
									for (int j = 0; j < 7; j++) {
										out.println("<td>&nbsp;</td>");
									}
									out.println("</tr>");
								%>
							</table>
						</div>
					</div>








					<!----------------div 오른쪽------------------------->

					<div class='right-box'>

						<div style="">
							<button class="btnConfirm" style="float: right;" onclick="scheduleModal('ScheduleAdd','show');">등록하기</button>
						</div>

						<!--날짜가 들어가는 부분-->
						<table
							style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">

							<tr align="left" bgcolor="#eeeeee" height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<th colspan="5" width="60"
									style="color: #787878; padding-left: 15px;">17일</th>
							</tr>


							<tr align="center" bgcolor="#ffffff" height="35"
								style="border-bottom: 1px solid #cccccc;" onclick="scheduleModal('scheduleArticle','show');">
								<td colspan="2">오후 18:00</td>
								<td>[홍대]</td>
								<td align="left" style="padding-left: 10px;"><a href="#">웹
										페이지 만들기 스터디</a></td>
								<td>참여인원 3명</td>

							</tr>

						</table>
						<!--날짜별 일정이 들어가는 부분-->
						<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
							<tr height="35">
								<td align="center">
									<!-- 								  ${dataCount==0? "등록된 게시글이 없습니다." : paging} 1 2 3 -->
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			</div>

		<!-- 			일정 등록 모달 -->
		<div class="modal show-modal" id="ScheduleAdd">
				<div class="modal-content">
					<div>
						<table id="moo">
							<tr>
								<th>일정 등록 입니다. <span class="close-button" onclick="scheduleModal('ScheduleAdd','none');">×</span>
								</th>

							</tr>
						</table>
					</div>

					<form class="modalForm" action="#post.php" method="POST" name="ScheduleAddForm">

						<input class="shortinput" placeholder="시작날짜"> <input
							class="shortinput" placeholder="시작시간"> <input
							class="shortinput" placeholder="종료날짜"> <input
							class="shortinput" placeholder="종료시간"> <input
							class="longinput" required="required" type="text"
							placeholder="일정명">

						<textarea class="textwrite" required="required" placeholder="상세일정"></textarea>

						<input class="middleinput" placeholder="장소명/주소">

						<button class="shortbtn" type="button">주소검색</button>


						<button class="middlebtn" type="button">인원추가하기</button>
						<p>(1/5)</p>

						<button class="graylongbtn" type="button">등록</button>


					</form>
				</div>
			</div>
		</div>
	
	<!-- 일정 보기 --> 
    <div class="modal show-modal" id="scheduleArticle">

        <div class="modal-content">
        
        <div>
        	    <table id="moo">
            	<tr>
	            	<th>여기는 모임명 들어갈 곳
	            	<span class="close-button" onclick="scheduleModal('scheduleArticle','none');">×</span>
	            	</th>
            	</tr>
            </table>
        </div>
         
            <form class="modalForm" action="#post.php" method="POST">
           
			  <div class="scheduleList">
			  
			  	<p>일시</p>
			  	<p>일시내용</p>  	
			  	<p>상세설명</p> 	
			  	<p>상세설명(내용)</p>			  	
			  	<p>장소</p>			  	
			  	<p>장소내용</p>			  	
			  	<p>지도나오기</p>
			  		  	
			  	
			  	<p><button class="middlebtn">참여인원보기(인원수)</button></p>
			  
			  </div>


			  <button class="shortbtn" type="button" onclick="scheduleModal('scheduleArticle','none');">닫기</button>
			  <button class="graybtn" type="button">수정하기</button>
			  <button class="graybtn" type="button">삭제하기</button>
			  
			</form>
        </div>
    </div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
	window.onload = function () {
		var ScheduleAdd = document.getElementById("ScheduleAdd");
		ScheduleAdd.style.display='none';
		
		var scheduleArticle = document.getElementById("scheduleArticle");
		scheduleArticle.style.display='none';
	}
	function scheduleModal(name, state) {
		if(state=='show'){
			document.getElementById(name).style.display='block';
		}else if(state=='none'){
			document.getElementById(name).style.display='none';
		}
	};
	
	</script>
</body>
</html>