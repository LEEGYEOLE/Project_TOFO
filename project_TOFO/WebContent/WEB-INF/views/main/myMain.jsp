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
	border-color: #e4e4e4;
	width: 49%;
	margin: 30px 0;
}

.backcolor {
	background: #e4e4e4;
}

.teamImg {
	text-align: center;
	padding: 4%;
	width: 183px;
	height: auto;
	display: inline-block;
	/* 	border-right: 3px dotted; */
	/* 	border-left: 3px dotted; */
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

.btnConfirm {
	 margin-bottom: 0px;
	font-size: 20px;
	border: none;
	color: #ffffff;
	background: #FFC107;
	width: 120px;
	height: 35px;
	line-height: 30px;
	border-radius: 4px;
}

.btnConfirm:hover {
     margin-bottom: 0px;
	font-size: 20px;
	border: none;
	color: #ffdd79;
	background: #FFC107;
	width: 120px;
	height: 35px;
	line-height: 30px;
	border-radius: 4px;
}


/* 모달모달 */

/* 모달대화상자 타이틀바 */
.ui-widget-header {
	background: none;
	border: none;
	height:35px;
	line-height:35px;
	border-bottom: 1px solid #cccccc;
	border-radius: 0px;
}
.help-block {
	margin-top: 3px; 
}

.titleDate {
	display: inline-block;
	font-weight: 600; 
	font-size: 19px;
	font-family: 나눔고딕, "맑은 고딕", 돋움, sans-serif;
	padding:2px 4px 4px;
	text-align:center;
	position: relative;
	top: 4px;
}
.btnDate {
	display: inline-block;
	font-size: 10px;
	font-family: 나눔고딕, "맑은 고딕", 돋움, sans-serif;
	color:#333333;
	padding:3px 5px 5px;
	border:1px solid #cccccc;
    background-color:#fff;
    text-align:center;
    cursor: pointer;
    border-radius:2px;
}

.textDate {
      font-weight: 500; cursor: pointer;  display: block; color:#333333;
}
.preMonthDate, .nextMonthDate {
      color:#aaaaaa;
}
.nowDate {
      color:#111111;
}
.saturdayDate{
      color:#0000ff;
}
.sundayDate{
      color:#ff0000;
}

.scheduleSubject {
   display:block;
   /*width:100%;*/
   width:110px;
   margin:1.5px 0;
   font-size:13px;
   color:#555555;
   background:#eeeeee;
   cursor: pointer;
   white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
}
.scheduleMore {
   display:block;
   width:110px;
   margin:0 0 1.5px;
   font-size:13px;
   color:#555555;
   cursor: pointer;
   text-align:right;
}
</style>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
	//스케쥴 등록 -----------------------
	//등록 대화상자 출력
	$(function() {
		$(".mkGroupBtn").click(function() {
			// 폼 reset
// 			$("form[name=scheduleForm]").each(function() {
// 				this.reset();
// 			});
// 			$("#form-repeat_cycle").hide();
// 			$("#form-allDay").prop("checked", true);
// 			$("#form-allDay").removeAttr("disabled");
// 			$("#form-eday").closest("tr").show();

// 			$("#form-sday").datepicker({
// 				showMonthAfterYear : true
// 			});
// 			$("#form-eday").datepicker({
// 				showMonthAfterYear : true
// 			});

// 			$("#form-sday").datepicker("option", "defaultDate", date);
// 			$("#form-eday").datepicker("option", "defaultDate", date);

			$('#schedule-dialog').dialog({
				modal : true,
				height : 650,
				width : 600,
				title : '모임 등록',
				close : function(event, ui) {
				}
			});

		});
	});

	//등록 대화상자 닫기
	$(function() {
		$("#btnScheduleSendCancel").click(function() {
			$('#schedule-dialog').dialog("close");
		});
	});
	
	//그룹 클릭 했을 때, 그룹 번호랑 타이틀을 넘겨줘서 서버에 저장한다.
	function goToGroup(num, title, userId) {
		title = encodeURIComponent(title);
		userId = encodeURIComponent(userId);
		var url ="<%=cp%>/schedule/list.do?num="+num+"&title="+title+"&leaderId="+userId;
		location.href=url;
	}
</script>
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
					<h2 style="padding: 10px 0px; font-size: 30px;">이번주 일정 목록</h2>
					<table class="mainTb">
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
				<div class="vl" style="float: right;">
					<div style="padding: 8px 0px; display: inline-block;">
						<h2 style="float: left; font-size: 30px;">My Group</h2>
						<button class="btnConfirm mkGroupBtn" style="float: left;">모임만들기</button>
					</div>
					<div class="backcolor" style="background: #e4e4e4;">
						<div class="teamImg"
							onclick="goToGroup('3','맛따라멋따라','admin');">
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
						<div class="teamImg">
							<img src="<%=cp%>/resource/img/teamwork.png"
								style="width: 150px;">
							<p>맛따라멋따라</p>
						</div>
					</div>
				</div>
			</div>
		</div>

    <div id="schedule-dialog" style="display: none;">
		<form name="scheduleForm">
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">제목</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="subject" id="form-subject" maxlength="100" class="boxTF" style="width: 95%;">
			        </p>
			        <p class="help-block">* 제목은 필수 입니다.</p>
			      </td>
			  </tr>
			
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">일정분류</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			          <select name="color" id="form-color" class="selectField">
			              <option value="green">개인일정</option>
			              <option value="blue">가족일정</option>
			              <option value="tomato">회사일정</option>
			              <option value="purple">기타일정</option>
			          </select>
			        </p>
			      </td>
			  </tr>
			
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">종일일정</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 5px; margin-bottom: 5px;">
			             <input type="checkbox" name="allDay" id="form-allDay" value="1" checked="checked">
			             <label for="allDay">하루종일</label>
			        </p>
			      </td>
			  </tr>
			
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">시작일자</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="sday" id="form-sday" maxlength="10" class="boxTF" readonly="readonly" style="width: 25%; background: #ffffff;">
			            <input type="text" name="stime" id="form-stime" maxlength="5" class="boxTF" style="width: 15%; display: none;" placeholder="시작시간">
  			        </p>
			        <p class="help-block">* 시작날짜는 필수입니다.</p>
			      </td>
			  </tr>

			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">종료일자</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="eday" id="form-eday" maxlength="10" class="boxTF" readonly="readonly" style="width: 25%; background: #ffffff;">
			            <input type="text" name="etime" id="form-etime" maxlength="5" class="boxTF" style="width: 15%; display: none;" placeholder="종료시간">
			        </p>
			        <p class="help-block">종료일자는 선택사항이며, 시작일자보다 작을 수 없습니다.</p>
			      </td>
			  </tr>

			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">일정반복</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <select name="repeat" id="form-repeat" class="selectField">
			              <option value="0">반복안함</option>
			              <option value="1">년반복</option>
			            </select>
			            <input type="text" name="repeat_cycle" id="form-repeat_cycle" maxlength="2" class="boxTF" style="width: 20%; display: none;" placeholder="반복주기">
			        </p>
			      </td>
			  </tr>
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">메모</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <textarea name="memo" id="form-memo" class="boxTA" style="width:93%; height: 70px;"></textarea>
			        </p>
			      </td>
			  </tr>
			  
			  <tr height="45">
			      <td align="center" colspan="2">
			        <button type="button" class="btn" id="btnScheduleSendOk">일정등록</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" id="btnScheduleSendCancel">등록취소</button>
			      </td>
			  </tr>
			</table>
		</form>
    </div>
	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/jquery.ui.datepicker-ko.js"></script>
</body>
</html>