<%@page import="com.team.TeamDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");

	String userId = request.getParameter("userId");
	String rank = request.getParameter("rank");
	
	TeamDAO dao = new TeamDAO();
	dao.deleteTeamList(userId, rank);
	
	response.sendRedirect("memberList.jsp");
%>