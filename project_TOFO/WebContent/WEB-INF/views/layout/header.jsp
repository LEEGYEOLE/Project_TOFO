<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<div class="header-top">
	<div class="header-left">
		<p style="margin: 10px; float: left;">
		<c:if test="${empty sessionScope.member}">
			<a href="<%=cp%>/" style="text-decoration: none;"> <span
				style="width: 200px; height: 70; position: relative; left: 5px; top: 5px; color: black; font-family: aaaB; font-size: 35px; font-weight: bold;text-shadow: 0px 1px #ffffff, 4px 4px 0px #dad7d7;">T&nbsp;O&nbsp;F&nbsp;O</span></a>
		</c:if>
		<c:if test="${not empty sessionScope.member}">
			<a href="<%=cp%>/main/myMain.do" style="text-decoration: none;"> <span
				style="width: 200px; height: 70; position: relative; left: 5px; top: 5px; color: black; font-family: aaaB; font-size: 35px; font-weight: bold;text-shadow: 0px 1px #ffffff, 4px 4px 0px #dad7d7;">T&nbsp;O&nbsp;F&nbsp;O</span></a>
		</c:if>
		</p>
		<c:if test="${not empty sessionScope.title}">
		<p style="margin: 10px;">
			<a href="<%=cp%>/schedule/list.do?num=${num}" style="text-decoration: none;"> <span
				style="width: 200px; height: 70; position: relative; left: 5px; top: 5px; color: black; font-family: aaaB; font-size: 30px; font-weight: bold;">&nbsp; : &nbsp;${sessionScope.title}</span></a>
		</p></c:if>
	</div>
	<div class="header-right">
	<!-- 		로그인 했으면 보여주기 -->
	<c:if test="${not empty sessionScope.member}">
		<ul class="nav">
			<li><a style="margin-top: 0px;"><button
						class="my-header-button">
					
						<img alt="" src="<%=cp%>/resource/img/icon/my.png"
							style="width: 25px; height: auto; margin-right: 10px; vertical-align: middle;"><span
							style="vertical-align: middle;">${sessionScope.member.userName}</span><img alt=""
							src="<%=cp%>/resource/img/icon/arrow-down-wh.png"
							style="width: 25px; height: auto; vertical-align: middle;">
					</button></a>
				<ul class="my">
					<li><a href="<%=cp%>/member/pwd.do">마이페이지</a></li>
					<li><a href="<%=cp%>/member/logout.do">로그아웃</a></li>
				</ul></li>
		</ul>
		</c:if>
		
		<!--로그인 안했을 시 보여주기-->
		<c:if test="${empty sessionScope.member}">
		<ul class="nav">
			<li><a><button
						onclick="javascript:location.href='<%=cp%>/member/created.do'"
						class="header-button">회원가입</button></a></li>
		</ul>

		<ul class="nav">
			<li><a><button
						onclick="javascript:location.href='<%=cp%>/member/login.do'"
						class="header-button">로그인</button></a></li>
		</ul>
		   </c:if>
	</div>
</div>