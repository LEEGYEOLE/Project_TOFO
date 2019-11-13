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
	<link rel="stylesheet"
	href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css"
	type="text/css">
<style type="text/css">
.mainTb th,.mainTb tr,.mainTb td {
	border: 1px solid black;
	height: 40px;
	border-collapse: collapse;
	border-spacing: 0;
	text-align: center;
}
.mainTb td{
	border-collapse: collapse;
	font-size: 14px;
}
.mainTb tr:hover{
	background: whitesmoke;border: 1px solid white;
}
.mainTb {
	border-collapse: collapse;
	font-size: 20px;
}

.mainTb1 {
	width: 80px;
	background-color: #e4e4e4;
}

.mainTb2 {
	width: 200px;
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
	    padding-top: 20px;
    padding-left: 33px;
}

.teamImg {
border: 1px solid white;
    margin-right: 33px;
    margin-bottom: 20px;
    border-radius: 40px;
    background: whitesmoke;
	text-align: center;
	padding: 20px;
	width: 80px;
	height: auto;
	display: inline-block;
}

.teamImg:hover {
	background: #c0c0c0;
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
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
	//스케쥴 등록 -----------------------
	//등록 대화상자 출력
	$(function() {
		$(".mkGroupBtn").click(function() {
// 			폼 reset
// 			$("form[name=teamForm]").each(function() {
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

			$('#team-dialog').dialog({
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
			$('#team-dialog').dialog("close");
		});
	});
	
	//그룹 클릭 했을 때, 그룹 번호랑 타이틀을 넘겨줘서 서버에 저장한다.
	function goToGroup(num, title, userId) {
		title = encodeURIComponent(title);
		userId = encodeURIComponent(userId);
		var url ="<%=cp%>/schedule/list.do?num="+num+"&title="+title+"&leaderId="+userId;
		location.href=url;
	}
	
	//모임 등록하기
	function teamSend() {
		  var f = document.teamForm;

	    	var str = f.title.value;
	        if(!str) {
	            alert("모임명을 입력하세요. ");
	            f.title.focus();
	            return;
	        }

	    	str = f.content.value;
	        if(!str) {
	            alert("상세설명를 입력하세요. ");
	            f.content.focus();
	            return;
	        }
	        str = f.userCount.value;
	        if(!str) {
	            alert("정원수를 입력하세요. ");
	            f.userCount.focus();
	            return;
	        }
	    	  if(f.imageFilename.value!="") {
	    		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.imageFilename.value)) {
	    			alert('이미지 파일만 가능합니다. !!!');
	    			f.imageFilename.focus();
	    			return;
	    		}
	    	  }
	        		f.action="<%=cp%>/team/insert.do";

	        f.submit();
	}
</script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	<div class="container">
		<hr>
				<div class="body-container">
		<div class="container-block">
			<div style="width: 100%;">
				<div class="vl" style="float: left;">
					<h2 style="padding: 10px 0px; font-size: 30px; float: left;">이번주 일정 목록<span style="clear: both;
    font-size: 15px;
    color: #ffc107;
    padding-left: 15px;">${priod}</span></h2>
					
					<table class="mainTb" style="    clear: both;">
						<tr>
							<th class="mainTb1">날짜</th>
							<th class="mainTb2">모임명</th>
							<th class="mainTb3">일정</th>
						</tr>
						<c:forEach items="${weeks}" var="s">
							${s}
						</c:forEach>
					</table>
				</div>
				<div class="vl" style="float: right;">
					<div style="padding: 8px 0px; display: inline-block;">
						<h2 style="float: left; font-size: 30px;">내 모임</h2>
						<button class="btnConfirm mkGroupBtn" style="float: left;margin-left: 10px;">모임만들기</button>
					</div>
					<div class="backcolor" style="background: #e4e4e4;">
						<c:if test="${not empty list}">
						<c:forEach var="dto" items="${list}">
						<div class="teamImg"
							onclick="goToGroup('${dto.num}','${dto.title}','${dto.userId}');">
							<c:if test="${empty dto.imageFilename}">
							<img src="<%=cp%>/resource/img/teamwork.png"
								style="    border-radius: 40px;
    width: 80px;
    height: 80px;">
								</c:if>
								<c:if test="${not empty dto.imageFilename}">
							<img src="<%=cp%>/uploads/photo/team/${dto.imageFilename}"
								style="    border-radius: 40px;
    width: 80px;
    height: 80px;">
								</c:if>
							<p>${dto.title}</p>
						</div>
						</c:forEach>
						</c:if>
						<c:if test="${empty list}">
						<div style="padding: 0 20px 20px 0;
    display: block;
    height: 20px;">
						가입한 모입이 없습니다. 새로운 모임을 만들고 참여하세요!
						</div>
						
						</c:if>
					</div>
				</div>
			</div>
		</div>

    <div id="team-dialog" style="display: none;">
		<form name="teamForm" method="post" enctype="multipart/form-data">
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">모임명</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="title" id="form-title" maxlength="20" class="boxTF" style="width: 95%;">
			        </p>
			        <p class="help-block">* 모임명은 필수 입니다.</p>
			      </td>
			  </tr>
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">상세정보</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="content" id="form-title" maxlength="100" class="boxTF" style="width: 95%;">
			        </p>
			        <p class="help-block">* 상세정보는 필수 입니다.</p>
			      </td>
			  </tr>
			
			 <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">정원</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="number" name="userCount" id="form-userCount" min="1" max="100" class="boxTF" style="width: 95%;">
			        </p>
			        <p class="help-block">* 정원수는 필수 입니다.</p>
			      </td>
			  </tr>

			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">프로필 사진</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			             <input type="file" name="imageFilename" accept="img/*"
			                      class="boxTF" size="53" style="width: 95%;"> </p>
			        <p class="help-block">* 이미지 파일만 업로드가능합니다.</p>
			      </td>
			  </tr>
			  <tr height="45">
			      <td align="center" colspan="2">
			        <button type="button" class="btn" onclick="teamSend();">일정등록</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" id="btnScheduleSendCancel">등록취소</button>
			      </td>
			  </tr>
			</table>
		</form>
    </div>
	</div>
	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>