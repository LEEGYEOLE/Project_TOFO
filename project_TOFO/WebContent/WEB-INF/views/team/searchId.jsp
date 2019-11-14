<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach var="map" items="${memberList}">
	<p><a onClick="addMember('${map.userId}');">${map.userId}, ${map.userName}, ${map.birth}, ${map.created_Date}</a></p>
</c:forEach>