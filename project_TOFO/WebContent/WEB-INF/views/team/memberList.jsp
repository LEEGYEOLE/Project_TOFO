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
<title>�ֿ�1�� - ȸ������</title>
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


//������ ��� -----------------------
//��� ��ȭ���� ���
$(function(){
	$(".addMember").click(function(){
		
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: 'ȸ�� �߰�',
			  close: function(event, ui) {
			  }
		});

	});
});

//��� ��ȭ���� �ݱ�
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$('#schedule-dialog').dialog("close");
	});
});

// ��ư�� ������ ã�ư���
function userSearch(){
	var userId = document.getElementById("form-subject").value;
	
	$(function(){
		var url="<%=cp%>/team/userSearch.do";
		$.get(url, {userId:userId}, function(data){
			$("#form-subject").closest("td").after(data);
		});
	});
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
					<div id="reviewTitle" style="margin-bottom: 15px;">ȸ�� ����</div>
					<table class="tableList">
						<tr>
							<th width="50">NO.</th>
							<th width="60">��å</th>
							<th width="80">���̵�</th>
							<th width="80">�̸�</th>
							<th width="80">����</th>
							<th width="80">��ȭ��ȣ</th>
							<th width="50">-</th>
							<th width="50">-</th>
						</tr>
			<c:forEach var="dto" items="${list}">
			  <tr> 
			      <td>1</td>
			      <td>${dto.rank}</td>
			      <td>${dto.userId}</td>
			      <td>${dto.userName}</td>
			      <td>${dto.birth}</td>
			      <td>${dto.tel}</td>
			      <td><button>�����ϱ�</button></td>
				  <td><button>�����ϱ�</button></td>
			  </tr>
			  </c:forEach>
			 
					</table>
					<div style="width: 100%; clear: both; height: 50px;">
						<div style="width: 120px; height: 100%; float: left;">
							<button class="btnConfirm ">������������</button>
						</div>
						<div style="float: right; width: 120px; height: 100%;">
							<button class="btnConfirm addMember">ȸ���߰�</button>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div id="schedule-dialog" style="display: none;">
		<form name="scheduleForm">
			<table
				style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">���̵�</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="subject" id="form-subject"
								maxlength="100" class="boxTF" style="width: 85%;">
								<button onclick="userSearch();">�˻�</button>
						</p>
						<p class="help-block">* �˻��Ϸ��� ���̵��� ���ڿ��� �Է��ϼ���.</p>
					</td>
					
				</tr>
					
				

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">���</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<select name="color" id="form-color" class="selectField">
								<option value="green">������</option>
								<option value="blue">���ӿ�</option>
							</select>
						</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">��������</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 5px; margin-bottom: 5px;">
							<input type="checkbox" name="allDay" id="form-allDay" value="1"
								checked="checked"> <label for="allDay">�Ϸ�����</label>
						</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">��������</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="sday" id="form-sday" maxlength="10"
								class="boxTF" readonly="readonly"
								style="width: 25%; background: #ffffff;"> <input
								type="text" name="stime" id="form-stime" maxlength="5"
								class="boxTF" style="width: 15%; display: none;"
								placeholder="���۽ð�">
						</p>
						<p class="help-block">* ���۳�¥�� �ʼ��Դϴ�.</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">��������</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<input type="text" name="eday" id="form-eday" maxlength="10"
								class="boxTF" readonly="readonly"
								style="width: 25%; background: #ffffff;"> <input
								type="text" name="etime" id="form-etime" maxlength="5"
								class="boxTF" style="width: 15%; display: none;"
								placeholder="����ð�">
						</p>
						<p class="help-block">�������ڴ� ���û����̸�, �������ں��� ���� �� �����ϴ�.</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">�����ݺ�</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<select name="repeat" id="form-repeat" class="selectField">
								<option value="0">�ݺ�����</option>
								<option value="1">��ݺ�</option>
							</select> <input type="text" name="repeat_cycle" id="form-repeat_cycle"
								maxlength="2" class="boxTF" style="width: 20%; display: none;"
								placeholder="�ݺ��ֱ�">
						</p>
					</td>
				</tr>

				<tr>
					<td width="100" valign="top"
						style="text-align: right; padding-top: 5px;"><label
						style="font-weight: 900;">�޸�</label></td>
					<td style="padding: 0 0 15px 15px;">
						<p style="margin-top: 1px; margin-bottom: 5px;">
							<textarea name="memo" id="form-memo" class="boxTA"
								style="width: 93%; height: 70px;"></textarea>
						</p>
					</td>
				</tr>

				<tr height="45">
					<td align="center" colspan="2">
						<button type="button" class="btn" id="btnScheduleSendOk">�������</button>
						<button type="reset" class="btn">�ٽ��Է�</button>
						<button type="button" class="btn" id="btnScheduleSendCancel">������</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>