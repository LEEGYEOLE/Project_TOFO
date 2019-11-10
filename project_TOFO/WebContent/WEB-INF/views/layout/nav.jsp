<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<div class="menu">
	<ul class="nav">
		<li><a href="<%=cp%>/schedule/list.do?num=${num}">일정관리</a></li>

		<li><a href="<%=cp%>/review/list.do">후기게시판</a></li>

		<li><a href="<%=cp%>/board/list.do">건의게시판</a></li>

		<li><a href="<%=cp%>/notice/list.do">공지사항</a></li>

		<c:if test="${sessionScope.member.userId==sessionScope.leaderId}">
			<li><a href="<%=cp%>/teamList/memberList.do">모임관리</a></li>
		</c:if>
	</ul>
</div>