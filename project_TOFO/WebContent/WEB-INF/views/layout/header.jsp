<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<div class="header-top">
	<div class="header-left">
		<p style="margin: 10px;">
			<a href="<%=cp%>/" style="text-decoration: none;"> <span
				style="width: 200px; height: 70; position: relative; left: 5px; top: 5px; color: black; filter: mask(color = red) shadow(direction = 135) chroma(color = red); font-style: italic; font-family: aaaB; font-size: 35px; font-weight: bold;">T&nbsp;O&nbsp;F&nbsp;O</span></a>
		</p>
	</div>
	<div class="header-right">
	<!-- 		로그인 했으면 보여주기 -->
		<ul class="nav">
			<li><a style="margin-top: 0px;"><button
						class="my-header-button">
						<img alt="" src="<%=cp%>/resource/img/icon/my.png"
							style="width: 25px; height: auto; margin-right: 10px; vertical-align: middle;"><span
							style="vertical-align: middle;">이겨레</span><img alt=""
							src="<%=cp%>/resource/img/icon/arrow-down-wh.png"
							style="width: 25px; height: auto; vertical-align: middle;">
					</button></a>
				<ul class="my">
					<li><a href="<%=cp%>/member/created.do">마이페이지</a></li>
					<li><a href="<%=cp%>">로그아웃</a></li>
				</ul></li>
		</ul>
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

	</div>
</div>