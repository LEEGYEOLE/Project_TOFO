<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>쌍용1조 - 회원관리</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/table.css"
	type="text/css">
	<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
	
<style type="text/css">
.tableList td {
	font-size: 15px;
}

.tableList {
	margin-bottom: 10px;
}
</style>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">


//스케쥴 등록 -----------------------
//등록 대화상자 출력
$(function(){
	$(".addMember").click(function(){
		
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '회원 추가',
			  close: function(event, ui) {
			  }
		});

	});
});

//등록 대화상자 닫기
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$('#schedule-dialog').dialog("close");
	});
});

// 버튼을 누르면 찾아가기
function userSearch(){
	var userId = document.getElementById().value;
	
	$(function(){
		var url="<%=cp%>/team/userSearch.do";
		$.get(url, {userId:userId}, function(data){
			$("#form-subject").closest("td").after(data); // 옆에 정보가 뜨게 만들기
		});
	});
}

function updateRank(userId) {
	var num =7;
	if(confirm(userId+"님께 모임장 권한을 위임하시겠습니까?")) {
		var url ="<%=cp%>/teamList/updateRank.do?num="+num+"&leader=${sessionScope.member.userId}&userId="+userId;
		location.href=url;
	}
}


function deleteTeamList(userId) {
	var num = 7;
	if(confirm(userId+"님을 강퇴 하시겠습니까 ?")) {
		var url = "<%=cp%>/teamList/deleteTeamList.do?num="+num+"&userId="+userId;
		location.href=url;
	}
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
				<div style="clear: both; margin-top: 30px;">
					<div id="reviewTitle" style="margin-bottom: 15px;">회원 관리</div>
					<table class="tableList">
						<tr>
							<th width="50">NO.</th>
							<th width="60">직책</th>
							<th width="80">아이디</th>
							<th width="80">이름</th>
							<th width="80">생일</th>
							<th width="80">전화번호</th>
							<th width="50">-</th>
							<th width="50">-</th>
						</tr>
			<c:forEach var="map" items="${list}">
			  <tr> 
			      <td>${map.listNum}</td>
			      <td>${map.rank=='0'?'모임장':'모임원'}</td>
			      <td>${map.userId}</td>
			      <td>${map.userName}</td> 
			      <td>${map.birth}</td>
			      <td>${map.tel}</td>
			      <td><input type="button" value="위임하기" onclick="updateRank('${map.userId}');" class="btn"></td>			 
 			      <td><input type="button" value="강퇴하기" onclick="deleteTeamList('${map.userId}');" class="btn"></td>
			  </tr>
			  </c:forEach>
			 
					</table>
					<div style="width: 100%; clear: both; height: 50px;">
						<div style="width: 120px; height: 100%; float: left;">
							<button class="btnConfirm ">모임정보수정</button>
						</div>
						<div style="float: right; width: 120px; height: 100%;">
							<button class="btnConfirm addMember">회원추가</button>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div id="schedule-dialog" style="display: none;">
		<form name="teamMemberForm">
			<table
				style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">아이디</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="subject" id="form-subject"
								maxlength="100" class="boxTF" style="width: 85%;">
								<button onclick="userSearch();" class="btn">검색</button>
						</p>
						<p class="help-block">* 검색하려는 아이디의 문자열을 입력하세요.</p>
			  <c:forEach var="map" items="${memberList}">
			  <tr> 
			      <td>${map.userId}&nbsp;&nbsp;${map.userName}&nbsp;&nbsp;${map.birth}&nbsp;&nbsp;${map.created_Date}</td>
			  </tr>
			  </c:forEach>
					</td>	
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">등급</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<select name="color" id="form-color" class="selectField">
								<option value="green">모임장</option>
								<option value="blue">모임원</option>
							</select>
						</p>
					</td>
				</tr>

				<tr height="45">
					<td align="center" colspan="2">
						<button type="button" class="btn" id="btnScheduleSendOk">회원등록</button>
						<button type="reset" class="btn">다시입력</button>
						<button type="button" class="btn" id="btnScheduleSendCancel">등록취소</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>