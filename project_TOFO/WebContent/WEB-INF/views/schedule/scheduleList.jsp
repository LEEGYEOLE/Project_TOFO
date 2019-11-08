<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">

<style type="text/css">
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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">

//스케쥴 등록 -----------------------
//등록 대화상자 출력
$(function(){
	$(".textDate").click(function(){
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '스케쥴 등록',
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
				<div class="body-title">
            <h3><span style="font-family: Webdings">2</span> 일정관리 </h3>
            <button class="btnConfirm" style="float: right;"
								onclick="scheduleModal('ScheduleAdd','show');">등록하기</button>
        </div>
        
        <div>

            <div style="clear: both;">
	           <ul class="tabs">
			       <li id="tab-month" data-tab="month">월별일정</li>
			       <li id="tab-day" data-tab="day">상세일정</li>
			       <li id="tab-year" data-tab="year">년도</li>
			   </ul>
		   </div>
		
		   <div id="tab-content" style="clear:both; padding: 20px 0px 0px;">
		   
		   		<table style="width: 840px; margin:0px auto; border-spacing: 0;" >
		   			<tr height="60">
		   			     <td width="200">&nbsp;</td>
		   			     <td align="center">
		   			         <span class="btnDate" onclick="changeDate(${todayYear}, ${todayMonth});">오늘</span>
		   			         <span class="btnDate" onclick="changeDate(${year}, ${month-1});">＜</span>
		   			         <span class="titleDate">${year}年 ${month}月</span>
		   			         <span class="btnDate" onclick="changeDate(${year}, ${month+1});">＞</span>
		   			     </td>
		   			     <td width="200">&nbsp;</td>
		   			</tr>
		   		</table>
		   		
			    <table id="largeCalendar" style="width: 840px; margin:0px auto; border-spacing: 1px; background: #cccccc;" >
					<tr align="center" height="30" bgcolor="#ffffff">
						<td width="120" style="color:#ff0000;">일</td>
						<td width="120">월</td>
						<td width="120">화</td>
						<td width="120">수</td>
						<td width="120">목</td>
						<td width="120">금</td>
						<td width="120" style="color:#0000ff;">토</td>
					</tr>

				<c:forEach var="row" items="${days}" >
						<tr align="left" height="120" valign="top" bgcolor="#ffffff">
							<c:forEach var="d" items="${row}">
								<td style="padding: 5px; box-sizing:border-box;">
									${d}
								</td>
							</c:forEach>
						</tr>
				</c:forEach>
			    </table>		   
		   
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
	<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>