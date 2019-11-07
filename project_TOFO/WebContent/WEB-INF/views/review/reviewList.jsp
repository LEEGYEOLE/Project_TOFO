<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
.left-box {
	float: left;
	width: 50%;
}

.right-box {
	float: right;
	width: 50%;
}

.container-block {
	width: 90%;
	margin: 2% auto;
}
   

    p {
    text-align: left;
    color: black;
    font-weight: 900;
    }
    

.modal-content{
height: 530px;}

.modalForm {
margin-top: 2px;
}


.middleinput {
margin-left: 11px;
}


.shortbtn {
float: left;
margin-top: 3px;
}

.graybtn {
margin-left: 120px;
margin-top: 3px;
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
					<div style="clear: both;
    padding-bottom: 10px;">
						<p id="reviewTitle">일정 후기 게시판</p>

						<div id="selectbox">
							<select id="viewNum">
								<option value="참석한 모임만 보기" selected="selected">참석한 모임만
									보기</option>
								<option value="#">#</option>
								<option value="#">#</option>
							</select>
						</div>
					</div>

					<table class="tableList">
					<colgroup>
    <col width="5%"/>
    <col width="28%"/>
    <col width="25%"/>
    <col width="5%"/>
    <col width="12%"/>
    <col width="5%"/>
  </colgroup>
						<thead>
							<tr>
								<th>No.</th>
								<th>일정명</th>
								<th>장소</th>
								<th>참여인원</th>
								<th>날짜</th>
								<th>참석여부</th>

							</tr>
						</thead>
						<tbody>
							<tr onclick="javacript:location.href='<%=cp%>/review/review.do'">
								<td>1</td>
								<td>A모임</td>
								<td>서울</td>
								<td>20</td>
								<td>2019-11-10</td>
								<td>참여</td>
							</tr>
							<tr>
								<td>#</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>#</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>#</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>#</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>#</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>

<!-- 					<button class="btnConfirm" id="button5" onclick="button5_click();">후기 -->
<!-- 						게시판 등록</button> -->
			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	
</body>
</html>