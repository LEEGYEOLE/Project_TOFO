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
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css"
	type="text/css">

<link rel="stylesheet" href="<%=cp%>/resource/css/table.css"
	type="text/css">

<style type="text/css">
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
	font-size: 15px;
	font-family: 나눔고딕, "맑은 고딕", 돋움, sans-serif;
	padding: 2px 4px 4px;
	text-align: center;
	position: relative;
	top: 4px;
}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
<c:if test="${not empty dto}">
$(function(){
	$("#btnUpdate").click(function(){
		// 폼 reset
		$("form[name=scheduleForm]").each(function(){
			this.reset();
		});
		
		$("#form-sday").datepicker({showMonthAfterYear:true});
		$("#form-eday").datepicker({showMonthAfterYear:true});
		
		var date1="${dto.sDate}";
		var date2="${dto.eDate}";
		if(date2=="") {
			date2=date1;
			$("#form-eday").val(date2);
		}
		
		$("#form-sday").datepicker("option", "defaultDate", date1);
		$("#form-eday").datepicker("option", "defaultDate", date2);
		
		var stime="${dto.stime}";
		if(stime!="") {
			$("#form-stime").show();
			$("#form-etime").show();
		} else {
			$("#form-stime").hide();
			$("#form-etime").hide();
		}
		var repeat="${dto.repeat}";
		if(repeat=="1") {
			$("#form-repeat_cycle").show();
			$("#form-eday").closest("tr").hide();
		} else {
			$("#form-repeat_cycle").hide();
			$("#form-eday").closest("tr").show();
		}
		
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '스케쥴 수정',
			  close: function(event, ui) {
			  }
		});
	});
});

// 수정완료
$(function(){
	$("#btnScheduleSendOk").click(function(){
		var scheduleForm = document.scheduleForm;
		
		var date="${date}";
		var scheNum="${dto.scheNum}";
		var num="${dto.num}";
		scheduleForm.action="<%=cp%>/schedule/update.do?date="+date+"&num="+num+"&scheNum="+scheNum;
		scheduleForm.submit();
	});
});

// 수정 대화상자 닫기
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$('#schedule-dialog').dialog("close");
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

// 수정내용 유효성 검사
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

//시간 형식 유효성 검사
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

function deleteOk(scheNum) {
	if(confirm("일정을 삭제 하시 겠습니까 ? ")) {
		var date="${date}";
		var num="${num}";
		var url="<%=cp%>/schedule/delete.do?date="+date+"&num="+num+"&scheNum="+scheNum;
		location.href=url;
	}
}
	$("#form-allDay").click(function(){
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '스케쥴 수정',
			  close: function(event, ui) {
			  }
		});
	});
</c:if>
function hi() {
	var title = '${title}';
	var url="<%=cp%>/schedule/updateAddr.do?scheNum=${dto.scheNum}&date=${date}&num=${num}&title="+title;
	location.href = url;
}

function attendOk() {
	if(confirm("일정에 참여하시겠습니까 ? ")) {
		var date="${date}";
		var num="${num}";
		var scheNum="${scheNum}";
		var url="<%=cp%>/schedule/attend.do?date="+date+"&num="+num+"&scheNum="+scheNum;
		location.href=url;
	}
}
</script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=59052bf5ebf62c8b0beb8b42b5faaeee"></script>
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


					<div id="scheduleRight"
						style="background: ${dto.color}14;
    border: 1px solid;
    border-radius:25px;
    padding: 25px 40px;
    margin: 30px auto;
    width: 800px; box-sizing: border-box;">

						<table style="width: 100%; border-spacing: 0px;">
							<tr height="35">
								<td align="left"><span class="titleDate">${year}년
										${month}월 ${day}일 일정</span></td>
								<td align="right"><c:if test="${dto.attend eq 0}">
										<button type="button" class="btn" id="attendBtn"
											onclick="attendOk();">일정참여하기</button>
									</c:if> <c:if test="${dto.attend eq 1}">
										<button type="button" class="btn" disabled="disabled">이미
											참여한 일정입니다</button>
									</c:if></td>
							</tr>
						</table>

						<c:if test="${empty dto}">
							<table
								style="width: 100%; margin-top: 5px; border-spacing: 0px; border-collapse: collapse;">
								<tr height="35">
									<td align="center">등록된 일정이 없습니다.</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${not empty dto}">
							<table
								style="width: 100%; margin-top: 5px; border-spacing: 0px; border-collapse: collapse;">
								<tr height="35"
									style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
									<td width="100"
										style="text-align: right; border-right: 2px solid #ccc; padding-right: 20px;"><label
										style="font-weight: 900;">제목</label></td>
									<td style="text-align: left; padding-left: 7px;">
										<p style="margin-top: 1px; margin-bottom: 1px;">
											${dto.title}</p>
									</td>
								</tr>
								<tr height="35" style="border-bottom: 1px solid #cccccc;">
									<td width="100"
										style="text-align: right; border-right: 2px solid #ccc; padding-right: 20px;"><label
										style="font-weight: 900;">날짜</label></td>
									<td style="text-align: left; padding-left: 7px;">
										<p style="margin-top: 1px; margin-bottom: 1px;">
											${dto.period}</p>
									</td>
								</tr>
								<tr height="35" style="border-bottom: 1px solid #cccccc;">
									<td width="100"
										style="text-align: right; border-right: 2px solid #ccc; padding-right: 20px;"><label
										style="font-weight: 900;">일정분류</label></td>
									<td style="text-align: left; padding-left: 7px;">
										<p style="margin-top: 1px; margin-bottom: 1px;">${empty dto.stime?"종일일정":"시간일정"}
										</p>
									</td>
								</tr>
								<tr height="35" style="border-bottom: 1px solid #cccccc;">
									<td width="100"
										style="text-align: right; border-right: 2px solid #ccc; padding-right: 20px;"><label
										style="font-weight: 900;">등록일</label></td>
									<td style="text-align: left; padding-left: 7px;">
										<p style="margin-top: 1px; margin-bottom: 1px;">
											${dto.created}</p>
									</td>
								</tr>
								<tr height="35" style="border-bottom: 1px solid #cccccc;">
									<td width="100"
										style="text-align: right; border-right: 2px solid #ccc; padding-right: 20px;"><label
										style="font-weight: 900;">색깔</label></td>
									<td style="text-align: left; padding-left: 7px;">

										<div style="color: ${dto.color};">
											<p style="margin-top: 1px; margin-bottom: 1px;">●</p>
										</div>
									</td>
								</tr>
								<tr height="30" style="border-bottom: 1px solid #cccccc;">
									<td width="100" valign="top"
										style="text-align: right; margin-top: 5px; border-right: 2px solid #ccc; padding-right: 20px; padding-top: 8px; padding-bottom: 8px;"><label
										style="font-weight: 900;">메모</label></td>
									<td valign="top"
										style="text-align: left; margin-top: 5px; padding-left: 7px;">
										<p
											style="margin-top: 0px; margin-bottom: 1px; padding-top: 8px; padding-bottom: 8px;">
											<span style="white-space: pre;">${dto.content}</span>
										</p>
									</td>
								</tr>
								<tr height="35">
									<td width="100"
										style="text-align: right; border-right: 2px solid #ccc; padding-right: 20px;"><label
										style="font-weight: 900;">장소</label></td>
									<td style="text-align: left; padding-left: 7px;">
										<p style="margin-top: 1px; margin-bottom: 1px;">
											<c:if
												test="${not (dto.addr=='empty' or empty dto.lat or empty dto.lon)}">
											${dto.addr}
											</c:if>
											<c:if
												test="${dto.addr=='empty' or empty dto.lat or empty dto.lon}">
											등록된 장소가 없습니다.
											</c:if>
										</p>
									</td>
								</tr>
								<c:if
									test="${not (dto.addr=='empty' or empty dto.lat or empty dto.lon)}">
									<tr height="35" style="border-bottom: 1px solid #cccccc;">
										<td width="100" style="border-right: 2px solid #ccc;"></td>
										<td><div id="staticMap"
												style="width: 300px; height: 300px;"></div></td>
									</tr>
								</c:if>
								<tr height="45">
									<td colspan="2" align="right"
										style="padding-right: 5px; clear: both;">
										<button type="button" class="btn" style="float: left;"
											onclick="javascript:location.href='<%=cp%>/schedule/list.do?year=${year}&month=${month}&num=${num}'">목록</button>
										<c:if test="${sessionScope.leaderId eq sessionScope.member.userId }">
										<button type="button" id="btnDelete" class="btn"
											style="float: right;" onclick="deleteOk('${dto.scheNum}');">삭제</button>
										<button type="button" id="btnUpdate" class="btn"
											style="float: right;">수정</button>
										<button type="button" id="btnLocation" class="btn"
											style="float: right;" onclick="hi();">장소등록/수정</button></c:if>
									</td>
								</tr>

							</table>
						</c:if>

						<c:if test="${list.size()>1}">
							<hr style="margin-top: 20px;">
							<table
								style="width: 100%; margin-top: 15px; border-spacing: 0px;">
								<tr height="35">
									<td align="left"><span class="titleDate">${year}년
											${month}월 ${day}일 다른 일정</span></td>
								</tr>
							</table>

							<table
								style="width: 100%; margin: 5px 0 20px; border-spacing: 0px; border-collapse: collapse;">
								<tr align="center" bgcolor="#eeeeee" height="35"
									style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
									<th style="color: #787878;">제목</th>
									<th width="100" style="color: #787878;">등록일</th>
								</tr>

								<c:forEach var="vo" items="${list}">
									<c:if test="${dto.scheNum != vo.scheNum}">
										<tr align="center" bgcolor="#ffffff" height="35"
											style="border-bottom: 1px solid #cccccc;">
											<td align="left" style="padding-left: 10px;"><a
												href="<%=cp%>/schedule/day.do?date=${date}&num=${vo.num}&scheNum=${vo.scheNum}">${vo.title}</a>
											</td>
											<td>${vo.created}</td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${not empty dto}">
			<div id="schedule-dialog" style="display: none;">
				<form name="scheduleForm">
					<table
						style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
						<tr>
							<td width="100" valign="top"
								style="text-align: right; padding-top: 5px;"><label
								style="font-weight: 900;">제목</label></td>
							<td style="padding: 0 0 15px 15px;">
								<p style="margin-top: 1px; margin-bottom: 5px;">
									<input type="text" name="title" id="form-subject"
										maxlength="100" class="boxTF" value="${dto.title}"
										style="width: 95%;">
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
										<option ${dto.color=="black"?"selected='selected' ":""}
											value="black" style="background: black; color: white;">black</option>
										<option ${dto.color=="red"?"selected='selected' ":""}
											value="red" style="background: red;">red</option>
										<option ${dto.color=="orange"?"selected='selected' ":""}
											value="orange" style="background: orange;">orange</option>
										<option ${dto.color=="yellow"?"selected='selected' ":""}
											value="yellow" style="background: yellow;">yellow</option>
										<option ${dto.color=="yellowgreen"?"selected='selected' ":""}
											value="yellowgreen" style="background: yellowgreen;">yellowgreen</option>
										<option ${dto.color=="forestgreen"?"selected='selected' ":""}
											value="forestgreen" style="background: forestgreen;">forestgreen</option>
										<option ${dto.color=="skyblue"?"selected='selected' ":""}
											value="skyblue" style="background: skyblue;">skyblue</option>
										<option ${dto.color=="royalblue"?"selected='selected' ":""}
											value="royalblue" style="background: royalblue;">royalblue</option>
										<option ${dto.color=="violet"?"selected='selected' ":""}
											value="violet" style="background: violet;">violet</option>
										<option ${dto.color=="blueviolet"?"selected='selected' ":""}
											value="blueviolet" style="background: blueviolet;">blueviolet</option>
										<option ${dto.color=="hotpink"?"selected='selected' ":""}
											value="hotpink" style="background: hotpink;">hotpink</option>
										<option ${dto.color=="brown"?"selected='selected' ":""}
											value="brown" style="background: brown;">brown</option>
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
										${empty dto.stime?"checked='checked'":""}> <label
										for="allDay">하루종일</label>
								</p>
							</td>
						</tr>

						<tr>
							<td width="100" valign="top"
								style="text-align: right; padding-top: 5px;"><label
								style="font-weight: 900;">시작일자</label></td>
							<td style="padding: 0 0 15px 15px;">
								<p style="margin-top: 1px; margin-bottom: 5px;">
									<input type="text" name="sDate" id="form-sday" maxlength="10"
										class="boxTF" value="${dto.sDate}" readonly="readonly"
										style="width: 25%; background: #ffffff;"> <input
										type="text" name="stime" id="form-stime" maxlength="5"
										class="boxTF" value="${dto.stime}"
										style="width: 15%; display: none;" placeholder="시작시간">
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
									<input type="text" name="eDate" id="form-eday" maxlength="10"
										class="boxTF" value="${dto.eDate}" readonly="readonly"
										style="width: 25%; background: #ffffff;"> <input
										type="text" name="etime" id="form-etime" maxlength="5"
										class="boxTF" value="${dto.etime}"
										style="width: 15%; display: none;" placeholder="종료시간">
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
										<option value="0" ${dto.repeat==0?"selected='selected' ":""}>반복안함</option>
										<option value="1" ${dto.repeat==1?"selected='selected' ":""}>년반복</option>
									</select> <input type="text" name="repeat_cycle" id="form-repeat_cycle"
										maxlength="2" class="boxTF" value="${dto.repeat_cycle}"
										style="width: 20%; display: none;" placeholder="반복주기">
								</p>
							</td>
						</tr>
						<tr>
							<td width="100" valign="top"
								style="text-align: right; padding-top: 5px;"><label
								style="font-weight: 900;">예상금액</label></td>
							<td style="padding: 0 0 15px 15px;">
								<p style="margin-top: 1px; margin-bottom: 5px;">
									<input type="text" name="money" id="form-subject"
										value="${dto.money}" maxlength="7" class="boxTF"
										style="width: 95%;">
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
										style="width: 93%; height: 70px;">${dto.content}</textarea>
								</p>
							</td>
						</tr>

						<tr height="45">
							<td align="center" colspan="2"><input type="hidden"
								name="num" value="${dto.num}"><input type="hidden"
								name="scheNum" value="${dto.scheNum}"><input
								type="hidden" name="date" value="${date}">
								<button type="button" class="btn" id="btnScheduleSendOk">수정완료</button>
								<button type="button" class="btn" id="btnScheduleSendCancel">수정취소</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</c:if>

	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

	<script>    
	<c:if test="${not (dto.addr=='empty' or empty dto.lat or empty dto.lon)}">

	// 이미지 지도에 표시할 마커입니다
	// 이미지 지도에 표시할 마커를 아래와 같이 배열로 넣어주면 여러개의 마커를 표시할 수 있습니다 
	var markers = [
	    {
	        position: new kakao.maps.LatLng(${dto.lat},${dto.lon}), 
	        text: '${dto.addr}' // text 옵션을 설정하면 마커 위에 텍스트를 함께 표시할 수 있습니다     
	    }
	];

	var staticMapContainer  = document.getElementById('staticMap'), // 이미지 지도를 표시할 div  
	    staticMapOption = { 
	        center: new kakao.maps.LatLng(${dto.lat},${dto.lon}), // 이미지 지도의 중심좌표
	        level: 3, // 이미지 지도의 확대 레벨
	        marker: markers // 이미지 지도에 표시할 마커 
	    };    

	// 이미지 지도를 생성합니다
	var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
    
	</c:if>
	console.log('${dto.lat}'+":"+'${dto.lon}');
</script>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>