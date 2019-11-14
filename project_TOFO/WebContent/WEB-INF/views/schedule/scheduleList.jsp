<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쌍용1조 - 일정관리</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css"
	type="text/css">

<link rel="stylesheet" href="<%=cp%>/resource/css/map.css"
	type="text/css">

<style type="text/css">
/* 모달대화상자 타이틀바 */
.ui-widget-header {
	background: none;
	border: none;
	height: 35px;
	line-height: 35px;
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
	padding: 2px 4px 4px;
	text-align: center;
	position: relative;
	top: 4px;
}

.btnDate {
	display: inline-block;
	font-size: 10px;
	font-family: 나눔고딕, "맑은 고딕", 돋움, sans-serif;
	color: #333333;
	padding: 3px 5px 5px;
	border: 1px solid #cccccc;
	background-color: #fff;
	text-align: center;
	cursor: pointer;
	border-radius: 2px;
}

.textDate {
	font-weight: 500;
	cursor: pointer;
	display: block;
	color: #333333;
}

.preMonthDate, .nextMonthDate {
	color: #aaaaaa;
}

.nowDate {
	color: #111111;
}

.saturdayDate {
	color: #0000ff;
}

.sundayDate {
	color: #ff0000;
}

.scheduleSubject {
	display: block;
	/* 	width:100%; */
	width: 60px;
	margin: 1.5px 0;
	font-size: 13px;
	color: #555555;
	background: #eeeeee;
	cursor: pointer;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.scheduleMore {
	display: block;
	width: 110px;
	margin: 0 0 1.5px;
	font-size: 13px;
	color: #555555;
	cursor: pointer;
	text-align: right;
}

#style1::-webkit-scrollbar {
	width: 8px;
	background-color: rgba(255, 255, 255, 1);
}

#style1::-webkit-scrollbar-thumb {
	background-color: rgba(128, 128, 128, 0.7);
	border-radius: 5px;
}li.on{color:tomato;}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
function changeDate(year, month) {
	var url="<%=cp%>/schedule/list.do?year="+year+"&month="+month+"&num="+${num};
	location.href=url;
}
$(function () {
	$('li a').click(function () {
		$('li').removeClass();
			$('li').addClass('on');
	});
});
//장소검색 --------------------------
//검색 대화상자 출력
$(function(){
	$(".locationBtn").click(function(){
		$('#location-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '스케쥴 등록',
		});

	});
});

//스케쥴 등록 -----------------------
//등록 대화상자 출력
$(function(){
	$(".textDate").click(function(){
		// 폼 reset
		$("form[name=scheduleForm]").each(function(){
			this.reset();
		});
		$("#form-repeat_cycle").hide();
		$("#form-allDay").prop("checked", true);
		$("#form-allDay").removeAttr("disabled");
		$("#form-eday").closest("tr").show();
		
		var date=$(this).attr("data-date");
		date=date.substr(0,4)+"-"+date.substr(4,2)+"-"+date.substr(6,2);

		$("form[name=scheduleForm] input[name=sday]").val(date);
		$("form[name=scheduleForm] input[name=eday]").val(date);
		
		$("#form-sday").datepicker({showMonthAfterYear:true});
		$("#form-eday").datepicker({showMonthAfterYear:true});
		
		$("#form-sday").datepicker("option", "defaultDate", date);
		$("#form-eday").datepicker("option", "defaultDate", date);
		
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '스케쥴 등록',
		});

	});
});

$(function(){
	$("#form-allDay").click(function(){
		if(this.checked==true) {
			$("#form-stime").val("").hide();
			$("#form-etime").val("").hide();
		} else if(this.checked==false && $("#form-repeat").val()==0){
			$("#form-stime").val("00:00").show();
			$("#form-etime").val("00:00").show();
		}
	});

	$("#form-sday").change(function(){
		$("#form-eday").val($("#form-sday").val());
	});
	
	$("#form-repeat").change(function(){
		if($(this).val()=="0") {
			$("#form-repeat_cycle").val("").hide();
			
			$("#form-allDay").prop("checked", true);
			$("#form-allDay").removeAttr("disabled");
			
			$("#form-eday").val($("#form-sday").val());
			$("#form-eday").closest("tr").show();
		} else {
			$("#form-repeat_cycle").show();
			
			$("#form-allDay").prop("checked", true);
			$("#form-allDay").attr("disabled","disabled");

			$("#form-stime").val("").hide();
			$("#form-eday").val("");
			$("#form-etime").val("");
			$("#form-eday").closest("tr").hide();
		}
	});
	
});

// 등록
$(function(){
	$("#btnScheduleSendOk").click(function(){
		if(! check() ) {
			return;
		}
		
		var query=$("form[name=scheduleForm]").serialize();
		var url="<%=cp%>/schedule/insert.do";

		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var state=data.state;
				if(state=="true") {
					var dd=$("#form-sday").val().split("-");
					var y=dd[0];
					var m=dd[1];
					if(m.substr(0,1)=="0") 	m=m.substr(1,1);
				
					location.href="<%=cp%>/schedule/list.do?year="+y+"&month="+m+"&num="+${num};
				}
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		location.href="<%=cp%>/member/login.do";
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
		
	});
});

// 등록 대화상자 닫기
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$('#schedule-dialog').dialog("close");
	});
});

// 등록내용 유효성 검사
function check() {
	if(! $("#form-subject").val()) {
		$("#form-subject").focus();
		return false;
	}

	if(! $("#form-sday").val()) {
		$("#form-sday").focus();
		return false;
	}

	if($("#form-eday").val()) {
		var s1=$("#form-sday").val().replace("-", "");
		var s2=$("#form-eday").val().replace("-", "");
		if(s1>s2) {
			$("#form-sday").focus();
			return false;
		}
	}
	
	if($("#form-stime").val()!="" && !isValidTime($("#form-stime").val())) {
		$("#form-stime").focus();
		return false;
	}

	if($("#form-etime").val()!="" && !isValidTime($("#form-etime").val())) {
		$("#form-etime").focus();
		return false;
	}
	
	if($("#form-etime").val()) {
		var s1=$("#form-stime").val().replace(":", "");
		var s2=$("#form-etime").val().replace(":", "");
		if(s1>s2) {
			$("#form-stime").focus();
			return false;
		}
	}	
	
	if($("#form-repeat").val()!="0" && ! /^(\d){1,2}$/g.test($("#form-repeat_cycle").val())) {
		$("#form-repeat_cycle").focus();
		return false;
	}
	
	if($("#form-repeat").val()!="0" && $("#form-repeat_cycle").val()<1) {
		$("#form-repeat_cycle").focus();
		return false;
	}
	
	return true;
}

// 시간 형식 유효성 검사
function isValidTime(data) {
	if(! /(\d){2}[:](\d){2}/g.test(data)) {
		return false;
	}
	
	var t=data.split(":");
	if(t[0]<0||t[0]>23||t[1]<0||t[1]>59) {
		return false;
	}

	return true;
}

// 스케쥴 제목 클릭 -----------------------
$(function(){
	$(".scheduleSubject").click(function(){
		var date=$(this).attr("data-date");
		var scheNum=$(this).attr("data-num");
		var num=${num};
		var url="<%=cp%>/schedule/day.do?date="+date+"&scheNum="+scheNum+"&num="+${num};
		location.href=url;
	});
});

//스케쥴 more(더보기) -----------------------
$(function(){
	$(".scheduleMore").click(function(){
		var date=$(this).attr("data-date");
		var num=3;
		var url="<%=cp%>/schedule/day.do?date="+date+"&num="+${num}+"&scheNum="+scheNum;
		location.href=url;
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
				<div style="clear: both; width: 100%;">
				<div style="    width: 90%;
    margin: 0 auto;">
				<div style="margin-top: 20px;    font-size: 30px;
    font-weight: bold;">일정 관리</div></div>
					<div id="tab-content" style="clear: both;">
						<div id="scheduleLeft"
							style="width: 53%; float: left; padding-top: 10px; padding-bottom: 30px;">
							<table style="width: 600px; margin: 0px auto; border-spacing: 0;">
								<tr height="60">
									<td width="100">&nbsp;</td>
									<td align="center"><span class="btnDate"
										onclick="changeDate(${todayYear}, ${todayMonth});">오늘</span> <span
										class="btnDate" onclick="changeDate(${year}, ${month-1});">＜</span>
										<span class="titleDate">${year}년 ${month}월</span> <span
										class="btnDate" onclick="changeDate(${year}, ${month+1});">＞</span>
									</td>
									<td width="100">&nbsp;</td>
								</tr>
							</table>

							<table id="largeCalendar"
								style="width: 600px; margin: 0px auto; border-spacing: 1px; background: #cccccc;">
								<tr align="center" height="30" bgcolor="#ffffff">
									<td width="70" style="color: #ff0000;">일</td>
									<td width="70">월</td>
									<td width="70">화</td>
									<td width="70">수</td>
									<td width="70">목</td>
									<td width="70">금</td>
									<td width="70" style="color: #0000ff;">토</td>
								</tr>

								<c:forEach var="row" items="${days}">
									<tr align="left" height="90" valign="top" bgcolor="#ffffff">
										<c:forEach var="d" items="${row}">
											<td style="padding: 5px; box-sizing: border-box;">${d}</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</table>

						</div>
						<div id="scheduleRight"
							style="width: 47%; padding-top: 10px; padding-bottom: 30px; float: right; box-sizing: border-box;">


							<table style="width: 100%; border-spacing: 0px; height: 60px;">
								<tr height="60">
									<th align="left"><span class="titleDate">${year}년
											${month}월 일정목록</span></th>
								</tr>

							</table>

							<table
								style="width: 100%; border-spacing: 0px; border-collapse: collapse;">
								<tr align="center" bgcolor="#eeeeee" height="35"
									style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc; margin: auto 0px;">
									<th width="10" style="color: #676767; padding-left: 1px;">날짜</th>
									<th width="8" style="color: #676767;">색깔</th>
									<th width="100" style="color: #676767;">제목</th>
								</tr>
							</table>

							<div id="style1" style="overflow-y: scroll; height: 453px;">
								<table
									style="width: 100%; border-spacing: 0px; border-collapse: collapse;">
									<c:forEach var="vo" items="${list}">
										<tr align="center" bgcolor="#ffffff" height="35"
											style="border-bottom: 1px solid #cccccc;">

											<td style="padding-left: 5px; width: 55px;">${fn:substring(vo.sDate,4,6)}/${fn:substring(vo.sDate,6,8)}</td>
											<td style="width: 60px;"><div
													style="color: ${vo.color};">●</div></td>
											<td align="left"><a
												href="<%=cp%>/schedule/day.do?date=${vo.sDate}&num=${vo.num}&scheNum=${vo.scheNum}"><span
													style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 430px; display: block;">
														${vo.title}</span></a></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</div>

	<div id="schedule-dialog" style="display: none;">
		<form name="scheduleForm">
			<input type="hidden" name="num" value="${num}">
			<table
				style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">제목</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="title" id="form-subject" maxlength="100"
								class="boxTF" style="width: 95%;">
						</p>
						<p class="help-block">* 제목은 필수 입니다.</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">색깔</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<select name="color" id="form-color" class="selectField"
								style="border-radius: 10px;">
								<option value="black" style="background: black; color: white;">black</option>
								<option value="red" style="background: red;">red</option>
								<option value="orange" style="background: orange;">orange</option>
								<option value="yellow" style="background: yellow;">yellow</option>
								<option value="yellowgreen" style="background: yellowgreen;">yellowgreen</option>
								<option value="forestgreen" style="background: forestgreen;">forestgreen</option>
								<option value="skyblue" style="background: skyblue;">skyblue</option>
								<option value="royalblue" style="background: royalblue;">royalblue</option>
								<option value="violet" style="background: violet;">violet</option>
								<option value="blueviolet" style="background: blueviolet;">blueviolet</option>
								<option value="hotpink" style="background: hotpink;">hotpink</option>
								<option value="brown" style="background: brown;">brown</option>
							</select>
						</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">종일일정</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 5px; margin-bottom: 5px;">
							<input type="checkbox" name="allDay" id="form-allDay" value="1"
								checked="checked"> <label for="allDay">하루종일</label>
						</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">시작일자</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="sday" id="form-sday" maxlength="10"
								class="boxTF" readonly="readonly"
								style="width: 25%; background: #ffffff;"> <input
								type="text" name="stime" id="form-stime" maxlength="5"
								class="boxTF" style="width: 15%; display: none;"
								placeholder="시작시간">
						</p>
						<p class="help-block">* 시작날짜는 필수입니다.</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">종료일자</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="eday" id="form-eday" maxlength="10"
								class="boxTF" readonly="readonly"
								style="width: 25%; background: #ffffff;"> <input
								type="text" name="etime" id="form-etime" maxlength="5"
								class="boxTF" style="width: 15%; display: none;"
								placeholder="종료시간">
						</p>
						<p class="help-block">종료일자는 선택사항이며, 시작일자보다 작을 수 없습니다.</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">일정반복</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<select name="repeat" id="form-repeat" class="selectField">
								<option value="0">반복안함</option>
								<option value="1">년반복</option>
							</select> <input type="text" name="repeat_cycle" id="form-repeat_cycle"
								maxlength="2" class="boxTF" style="width: 20%; display: none;"
								placeholder="반복주기">
						</p>
					</td>
				</tr>
				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">예상금액</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="money" id="form-subject" maxlength="7"
								class="boxTF" style="width: 95%;">
						</p>
						<p class="help-block">* 금액은 1억(100,000,000)원 이하로만 설정 가능합니다.</p>
					</td>
				</tr>
				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">메모</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<textarea name="content" id="form-memo" class="boxTA"
								style="width: 93%; height: 70px;"></textarea>
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


	<div id="location-dialog" style="display: none;"></div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>