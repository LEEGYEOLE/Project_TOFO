<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet"
	href="https://han3283.cafe24.com/js/lightslider/css/lightslider.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="https://han3283.cafe24.com/js/lightslider/js/lightslider.js"></script>
<style type="text/css">
/*로그인 폼 중앙정렬 */
.body-container {
	display: table;
}
/*테이블 중앙정렬*/
.tb1 {
	/* 	position: absolute; */
	/* 	top: 60%; */
	/* 	left: 50%; */
	/* 	margin-top: -100px; */
	/* 	margin-left: -100px; */
	width: 150px;
	height: 100px;
}

/*input폼*/
.if1 {
padding-left:15px;
	background-color: #eeeeee;
	border-radius: 10px;
	width: 300px;
	height: 40px;
}
/*인폿품 바디 */
.ipb {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 600px;
	height: 400px;
	border-radius: 20px;
}

.tb1 {
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

.container {
background: lightsteelblue;
<%-- 	background-image: url('<%=cp%>/resource/img/bg/bgImg1.png'); --%>
	background-repeat: no-repeat;
	background-size: cover;
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
</style>
<script type="text/javascript">
function sendLogin() {
    var f = document.loginForm;

	
    str = f.pwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/pwd_ok.do";
    f.submit();
}
</script>
</head>

<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>

	<div class="container">
		<div class="body-container">
			<div class="wrapper">
				<div class="ipb">
					<div class=logInLayout">
						<h1 class="title40">패스워드 확인</h1>
						
						
					<form name="loginForm" method="post" action="">
						<table class="tb1">
							<tr>
							<tr style="height:50px;"> 
			      				<td style="padding-left: 30px; text-align: left;">
			        				  정보보호를 위해 패스워드를 다시 한 번 입력해주세요.
			     				 </td>
			  				</tr>

							<tr>

								<td><input class="if1" type="password" name="pwd" value=""
									placeholder="비밀번호"></td>
							</tr>

							<tr style="border-spacing: 3px">
								<td colspan="2"><button class="bt1" onclick="sendLogin();">회원정보수정</button>
							</tr>
							
										  <tr align="center" height="30" >
			    	<td><span style="color: blue;">${message}</span></td>
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