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
<meta charset="EUC-KR">
<title>회원가입</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet"
	href="https://han3283.cafe24.com/js/lightslider/css/lightslider.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="https://han3283.cafe24.com/js/lightslider/js/lightslider.js"></script>
<script type="text/javascript">

function isValidDateFormat(data){
    var regexp = /[12][0-9]{3}[\.|\-|\/]?[0-9]{2}[\.|\-|\/]?[0-9]{2}/;
    if(! regexp.test(data))
        return false;

    regexp=/(\.)|(\-)|(\/)/g;
    data=data.replace(regexp, "");
    
	var y=parseInt(data.substr(0, 4));
    var m=parseInt(data.substr(4, 2));
    if(m<1||m>12) 
    	return false;
    var d=parseInt(data.substr(6));
    var lastDay = (new Date(y, m, 0)).getDate();
    if(d<1||d>lastDay)
    	return false;
		
	return true;
}

//이메일 형식 검사
function isValidEmail(data){
    var format = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    return format.test(data); // true : 올바른 포맷 형식
}
</script>
	
	
<script type="text/javascript">
function memberOk() {
	
	var f = document.memberForm;
	var str;

	str = f.id.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요. ");
		f.id.focus();
		return;
	}
	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) { // i : 대소문자를 구분하지 않겠다.
		alert("아이디는 5~10자이며 첫글자는 영문자이어야 합니다.");
		f.id.focus();
		return;
	}
	f.id.value = str;

	str = f.pwd.value;
	str = str.trim();
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.pwd.focus();
		return;
	}
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) { 
		alert("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
		f.pwd.focus();
		return;
	}
	f.pwd.value = str;

	if(str!= f.pwdcheck.value) {
        alert("패스워드가 일치하지 않습니다. ");
        f.pwdcheck.focus();
        return;
	}
	
    str = f.name.value;
	str = str.trim();
    if(!str) {
        alert("이름을 입력하세요. ");
        f.name.focus();
        return;
    }   
    f.name.value = str;

    str = f.birth.value;
	str = str.trim();
    if(!str || !isValidDateFormat(str)) {
        alert("생년월일를 입력하세요[YYYY-MM-DD]. ");
        f.birth.focus();
        return;
    }
    
    str = f.email.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email.focus();
        return;
    }
    
    if(!str || !isValidEmail(str)) {
        alert("이메일 형식을 맞춰주세요 ");
        f.email.focus();
        return;
    }
    
    str = f.tel.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel.focus();
        return;
    }

    
   
    var mode="${mode}";
    if(mode=="created") {
    	f.action = "<%=cp%>/member/created_ok.do";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/member/update_ok.do";
    }

    f.submit();
}
</script>
<style type="text/css">

/*테이블 중앙정렬*/
.tb1 {
	width: 150px;
	height: 100px;
}

/*input폼*/
/*로그인 화인 input폼 */
.if1 {
	padding-left: 10px;
	background-color: #eeeeee;
	border-radius: 10px;
	width: 250px;
	height: 30px;
	background-color: #eeeeee;
}

.if2 {
	background-color: #eeeeee;
	border-radius: 10px;
	width: 250px;
	height: 25px;
		padding-left: 10px;
}

/*인폿품이 들어잇는 네모 바디2(회원가입)) */
/* .ipb2 { */
/* 	background-color: rgba(128, 128, 130, 0.5); */
/* 	position: absolute; */
/* 	top: 33%; */
/* 	left: 45%; */
/* 	margin-top: -100px; */
/* 	margin-left: -100px; */
/* 	width: 400px; */
/* 	height: 550px; */
/* } */
.ipb2 {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 600px;
	height: 400px;
	border-radius: 20px;

}

.container {
background: lightsteelblue;
<%-- 	background-image: url('<%=cp%>/resource/img/bg/bgImg1.png'); --%>
	background-repeat: no-repeat;
	background-size: cover;
}

/* 테이블1*/

/* 테이블2*/
.tb2 {
	margin: 15px auto 0;
	border-spacing: 10px;
	width: 150px;
	height: 100px;
}

.bt1 {
	background-color: #eeeeee;
	border-radius: 10px;
	height: 40px;
	width: 100%;
	margin-top: 10px;
	margin-bottom: 10px;
}

/* 등록하기 버튼 표준*/
.bt2 {
	background-color: #444444;
	border: 1px solid #444444;
	border-radius: 5px;
	color: #fff;
	display: block;
	font-size: 15px;
	font-weight: 500;
	margin: 5px auto;
	padding: 10px 30px;
	position: relative;
	text-transform: uppercase;
}

.bt2:hover {
	color: white;
	font-weight: bold;
	transform: translateY(-3px);
}

.wrapper {
	display: table-cell;
	vertical-align: middle;
}

.title40 {
	text-align: center;
	font-size: 40px;
	color: white;
}
/*
.signUpLayout {
	padding: 50px 10px;
}
*/
.body-container{    display: table;}
</style>

</head>

<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>

	<div class="container">
		<div class="body-container">
			<div class="wrapper">
				<div class="ipb2">
					<div class="signUpLayout">
						<h1 class="title40" style="margin-top: 30px;">${title}</h1>
						
						<form name="memberForm" method="post" style="height: 400px; margin-top: 20px;">
						<table class="tb2">
							<tr>
								<td><input class="if2" type="text" name="id" 
									placeholder="아이디" value="${dto.userId}" ${mode=="update" ? "readonly='readonly' ":""}></td>

							</tr>

							<tr>
								<td><input class="if2" type="password" name="pwd"
									placeholder="패스워드" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="password" name="pwdcheck"
									placeholder="패스워드확인" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="name"
									placeholder="이름" value="${dto.userName}" ${mode=="update" ? "readonly='readonly' ":""}></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="birth"
									placeholder="생년월일" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="email"
									placeholder="이메일" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="tel"
									placeholder="전화번호" value=""></td>

							</tr>

							<tr>
								<td valign="top"
									style="text-align: left; padding-top: 5px; width: 30px"><label
									style="font-weight: 900; font-size: 13px">약관동의</label></td>

							</tr>

							<tr>

								<td>
									<p style="margin-top: -5px; margin-bottom: 5px;">
										<label> <input id="agree" name="agree" type="checkbox"
											checked="checked"
											onchange="form.sendButton.disabled = !checked"> <a
											href="#">이용 약관</a>에 동의하시겠습니까?
										</label>
									</p>
								</td>
							</tr>


							<tr>
								<td>
									<button class="bt2 btn-3 jq1" type="button"
										onclick="javascript:location.href='<%=cp%>/';" style="align-content: left; right: 130px; top: -10px">${mode=="created"?"가입취소":"수정취소"}</button>
									<button class="bt2 btn-3 jq1" type="button"
										onclick="memberOk();"
										style="position: relative; right: -130px; top: -58px">${mode=="created"?"회원가입":"정보수정"}</button>
								</td>
							</tr>
					 <tr height="30">
			        <td align="center" style="color: blue;">${message}</td>
			    	</tr>
						</table>
					</form>
					</div>
				</div>

			</div>
		</div>

	</div>
	
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>



</body>
</html>

