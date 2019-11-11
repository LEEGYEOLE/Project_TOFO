<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<%
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">

<style type="text/css">

.noticetitle{
margin-top: 30px;
font-size: 40px;

}


</style>

<script type="text/javascript">

function selectList(){
	var f=document.selectForm;
	f.submit();
}


	function searchList() {
		var f=document.searchForm;
		f.submit();
	}


</script>


</head>
<body>



<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
</div>



<div class="container">
    <div class="body-container" style="width: 90%;">
        <div>
            <h3 class="noticetitle"><span style="font-family: Webdings"></span> 공지사항 </h3>
        </div>
        
        <div>

			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="left" width="50%">
			          ${dataCount}개(${page}/${total_page} 페이지)
			      </td>
			      <td align="right">
			        <form name="selectForm" action="<%=cp%>/notice/list.do" method="post" onchange="selectList();">
			      		<select name="rows" class="selectField">
			      			<option value="5"  ${rows==5? "selected='selected' ":""}>5개보기</option>
			      			<option value="10"  ${rows==10? "selected='selected' ":""}>10개보기</option>
			      			<option value="20"  ${rows==20? "selected='selected' ":""}>20개보기</option>
			      			<option value="30"  ${rows==30? "selected='selected' ":""}>30개보기</option>
			      		</select>
			      			<input type="hidden" name="condition" value="${condition}">
			      			<input type="hidden" name="keyword" value="${keyword}">
			      	</form>
			      </td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
			  <tr class="tableList" align="center" style="font-size: 20px; text-align: center; border: 1px solid #ccc; border-bottom: 1px solid #444444; padding: 10px;"> 
			      <th width="60" style="font-size: 20px; text-align: center; border: 1px solid #ccc; border-bottom: 1px solid #444444; padding: 10px; background-color: #dcdcdc;">번호</th>
			      <th style="font-size: 20px; text-align: center; border: 1px solid #ccc; border-bottom: 1px solid #444444; padding: 10px; background-color: #dcdcdc;">제목</th>
			      <th width="100" style="font-size: 20px; text-align: center; border: 1px solid #ccc; border-bottom: 1px solid #444444; padding: 10px; background-color: #dcdcdc;">작성자</th>
			      <th width="80" style="font-size: 20px; text-align: center; border: 1px solid #ccc; border-bottom: 1px solid #444444; padding: 10px; background-color: #dcdcdc;">작성일</th>
			      <th width="60" style="font-size: 20px; text-align: center; border: 1px solid #ccc; border-bottom: 1px solid #444444; padding: 10px; background-color: #dcdcdc;">조회수</th>
			      
			  </tr>
			 
			 <c:forEach var="dto" items="${listNotice}">
			  <tr class="tableList" align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td><span style="display: inline-block;padding: 1px 3px; background: #ED4C00; color: #FFFFFF;">공지</span></td>
			      <td align="left" style="padding-left: 10px;">
			           <a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
			           <img src="/project_TOFO/resource/img/icon/new.gif">
			      </td>
			      <td>${dto.userName}</td>
			      <td>${dto.created}</td>
			      <td>${dto.hitCount}</td>
			      <td> 
			     </td>
			  </tr>
			  
			  </c:forEach>
			  
			  
			   <c:forEach var="dto" items="${list}">
			  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td>${dto.listNum}</td>
			      <td align="left" style="padding-left: 10px;">
			           <a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
			           <c:if test="${dto.gap<=1}">
			           
			           <img src="/project_TOFO/resource/img/icon/new.gif">
			           
			           </c:if>
			      </td>
			      <td>${dto.userName}</td>
			      <td>${dto.created}</td>
			      <td>${dto.hitCount}</td>
			      <td>
			      	<c:if test="${not empty dto.saveFilename}">
			      		<a href="<%=cp%>/notice/download.do?num=${dto.num}"></a>
			      	</c:if>
			      	
			      </td>
			  </tr>
			  
			  </c:forEach>

			</table>
			 
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			         ${dataCount==0?"등록된 게시물이 없습니다.":paging}
				</td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
			   <tr height="40">
			      <td align="left" width="100">
			          <button type="button" class="btnConfirm" onclick="javascript:location.href='<%=cp%>/notice/list.do';">새로고침</button>
			      </td>
			      <td align="center">
			          <form name="searchForm" action="<%=cp%>/notice/list.do" method="post">
			              <select name="condition" class="selectField">
			                  <option value="subject" ${condition=="subject"?" selected='selected' ":""}>제목</option>
			                  <option value="userName" ${condition=="userName"?" selected='selected' ":""}>작성자</option>
			                  <option value="content" ${condition=="content"?" selected='selected' ":""}>내용</option>
			                  <option value="created" ${condition=="created"?" selected='selected' ":""}>등록일</option>
			            </select>
			            <input type="text" name="keyword" class="boxTF" value="${keyword}">
			             <input type="hidden" name="rows" value="${rows}">
			            <button type="button" class="btn" onclick="searchList()" style="background: #FFC107; color: white;">검색</button>
			        </form>
			      </td>
			      <td align="right" width="100">
			      <c:if test="${sessionScope.member.userId == sessionScope.leaderId}">
			          <button type="button" class="btnConfirm" onclick="javascript:location.href='<%=cp%>/notice/created.do';">글올리기</button>
			      </c:if>
			      
			      </td>
			   </tr>
			</table>

        </div>
    </div>
</div>






<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>





</body>
</html>