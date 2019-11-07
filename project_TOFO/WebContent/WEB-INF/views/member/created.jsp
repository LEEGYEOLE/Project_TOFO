<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	String cp = request.getContextPath();
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ������</title>
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
<style type="text/css">

/*���̺� �߾�����*/
.tb1 {
	width: 150px;
	height: 100px;
}

/*input��*/
/*�α��� ȭ�� input�� */
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

/*����ǰ�� ����մ� �׸� �ٵ�2(ȸ������)) */
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

/* ���̺�1*/

/* ���̺�2*/
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

/* ����ϱ� ��ư ǥ��*/
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

.signUpLayout {
	padding: 50px 10px;
}

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
						<h1 class="title40">ȸ������ / ����������</h1>
						<table class="tb2">
							<tr>
								<td><input class="if2" type="text" name="id"
									placeholder="���̵�" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="password" name="pwd"
									placeholder="�н�����" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="password" name="pwdcheck"
									placeholder="�н�����Ȯ��" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="name"
									placeholder="�̸�" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="birth"
									placeholder="�������" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="email"
									placeholder="�̸���" value=""></td>

							</tr>

							<tr>
								<td><input class="if2" type="text" name="tel"
									placeholder="��ȭ��ȣ" value=""></td>

							</tr>

							<tr>
								<td valign="top"
									style="text-align: left; padding-top: 5px; width: 30px"><label
									style="font-weight: 900; font-size: 13px">�������</label></td>

							</tr>

							<tr>

								<td>
									<p style="margin-top: -5px; margin-bottom: 5px;">
										<label> <input id="agree" name="agree" type="checkbox"
											checked="checked"
											onchange="form.sendButton.disabled = !checked"> <a
											href="#">�̿� ���</a>�� �����Ͻðڽ��ϱ�?
										</label>
									</p>
								</td>
							</tr>


							<tr>
								<td>
									<button class="bt2 btn-3 jq1" type="button"
										onclick="javascript.location.href="
										#" style="align-content: left; right: 130px; top: -10px">����ϱ�</button>
									<button class="bt2 btn-3 jq1" type="button"
										onclick="javascript.location.href="
										#" style="position: relative; right: -130px; top: -58px">����ϱ�</button>
								</td>
							</tr>

						</table>

					</div>
				</div>

			</div>
		</div>

	</div>



</body>
</html>

