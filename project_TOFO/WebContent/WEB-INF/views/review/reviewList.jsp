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
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/table.css"
	type="text/css">

<script type="text/javascript">
	function selectList() {
		var f = document.selectForm;
		f.submit();
	}
</script>


<style>
.left-box {
	float: left;
	width: 50%;
}

.right-box {
	float: right;
	width: 50%;
}
.body-container{width: 80%;
	margin: 0% auto;
}
.container-block {
	width: 100%;
    margin: 0;
}

p {
	text-align: left;
	color: black;
	font-weight: 900;
}

.modal-content {
	height: 530px;
}

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

.colorhover:hover{
background-color: gray;
cursor: pointer;
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
				<div style="clear: both; padding-bottom: 10px;margin-top: 30px;">
					<div id="reviewTitle" style="margin-bottom: 15px;">일정 후기 게시판</div>

					<div id="selectbox">
						<form name="selectForm" action="<%=cp%>/review/list.do"
							method="post" onchange="selectList();">
							<select name="condition" id="viewNum">
								<option value="att" ${condition.equalsIgnoreCase("att") ? "selected='selected'" : ""}>참석여부 보기</option>
								<option value="attend" ${condition.equalsIgnoreCase("attend") ? "selected='selected'" : ""}>참석</option>
								<option value="notattend" ${condition.equalsIgnoreCase("notattend") ? "selected='selected'" : ""}>불참</option>
							</select> 
							<input type="hidden" name="condition" value="${condition}">
						</form>
					</div>
				</div>

				<table class="tableList">
					<thead>
						<tr>
							<th width="5">No.</th>
							<th width="220">일정명</th>
							<th width="170">장소</th>
							<th width="40">참여인원</th>
							<th width="55">날짜</th>
							<th width="38">참석여부</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="dto" items="${list}">
							<tr class="colorhover" onclick="javacript:location.href='${articleUrl}&num=${dto.reviewNum}&groupnum=${dto.groupNum}'">
								<td>${dto.listNum}</td>
								<td>${dto.title}</td>
								<c:if test="${dto.location!='empty'}">
								<td>${dto.location}</td>
								</c:if>
								
								<c:if test="${dto.location=='empty'}">
								<td style="color: #333333; font-size: 15px">정해진 모임 장소가 없습니다</td>
								</c:if>
								<td>${dto.personnel}</td>
								<td>${dto.startDate} 
								<c:if test="${dto.endDate ne null}">
								~${dto.endDate}
								</c:if>
								</td>
								<td>${dto.attendCheck==1 ? "참석" : "불참"}</td>
								
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			        ${dataCount==0? "불참한 모임이 없습니다." : paging}
				</td>
			   </tr>
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