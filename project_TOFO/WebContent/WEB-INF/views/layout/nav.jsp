<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String cp = request.getContextPath();
%>
<div class="menu">
    <ul class="nav">
        <li>
            <a href="<%=cp%>/schedule/list.do">일정관리</a>
        </li>
			
        <li>
            <a href="<%=cp%>/review/list.do">후기게시판</a>
        </li>

        <li>
            <a href="#">건의게시판</a>
        </li>

        <li>
            <a href="#">공지사항</a>
        </li>

        <li>
            <a href="<%=cp%>/team/memberList.do">회원관리</a>
        </li>
    </ul>      
</div>