<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<style type="text/css">
.btnLike {
    color:#333333;
    font-weight:500;
    border:1px solid #cccccc;
    background-color:#ffffff;
    text-align:center;
    cursor:pointer;
    padding:6px 10px 5px;
    border-radius:4px;
}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function deleteBoard(num) {
<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
    var query = "num="+num+"&${query}";
    var url = "<%=cp%>/bbs/delete.do?" + query;

    if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
    	location.href=url;
</c:if>    
<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
    alert("게시물을 삭제할 수  없습니다.");
</c:if>
}

function updateBoard(num) {
<c:if test="${sessionScope.member.userId==dto.userId}">
    var page = "${page}";
    var query = "num="+num+"&page="+page;
    var url = "<%=cp%>/bbs/update.do?" + query;

    location.href=url;
</c:if>

<c:if test="${sessionScope.member.userId!=dto.userId}">
   alert("게시물을 수정할 수  없습니다.");
</c:if>
}
</script>

<script type="text/javascript">
// 게시물 공감 개수
function countBoardLike(num) {
	var url="<%=cp%>/bbs/countBoardLike.do";
	
	$.ajax({
		type:"post"
		,url:url
		,data:{num:num}
		,dataType:"json"
		,success:function(data) {
			var count=data.countBoardLike;
			$("#countBoardLike").html(count);
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}

// 게시물 공감 추가
function sendBoardLike(num) {
	var msg="게시물에 공감하십니까 ?";
	if(! confirm(msg))
		return;
	
	var url="<%=cp%>/bbs/insertBoardLike.do";
	$.ajax({
		type:"post"
		,url:url
		,data:{num:num}
		,dataType:"json"
		,success:function(data) {
			var state=data.state;
			if(state=="true") {
				countBoardLike(num);
			} else if(state=="false") {
				alert("좋아요는 한번만 가능합니다. !!!");
			}
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}

//-------------------------------------
// 댓글
function login() {
	location.href="<%=cp%>/member/login.do";
}

// 댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/bbs/listReply.do";
	var query="num=${dto.num}&pageNo="+page;
	
	$.ajax({
		type:"get"
		,url:url
		,data:query
		,success:function(data) {
			$("#listReply").html(data);
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}

// 댓글 등록
$(function(){
	$(".btnSendReply").click(function(){
		var num="${dto.num}";
		// var content=$(".boxTA:first").val();
		var $tb = $(this).closest("table");
		var content=$tb.find("textarea").val().trim();
		if(! content) {
			$tb.find("textarea").focus();
			return;
		}
		content = encodeURIComponent(content);
		
		var query="num="+num+"&content="+content;
		var url="<%=cp%>/bbs/insertReply.do";
		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				$tb.find("textarea").val("");
				
				var state=data.state;
				if(state=="true") {
					listPage(1);
				} 
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
});

// 댓글 삭제
$(function(){
	$("body").on("click", ".deleteReply", function(){
		if(! confirm("게시물을 삭제하시겠습니까 ? "))
		    return;
		
		var url="<%=cp%>/bbs/deleteReply.do";
		var replyNum=$(this).attr("data-replyNum");
		var page=$(this).attr("data-pageNo");
		
		$.ajax({
			type:"post"
			,url:url
			,data:{replyNum:replyNum}
			,dataType:"json"
			,success:function(data) {
				listPage(page);
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
});

// 댓글 좋아요 / 싫어요
$(function(){
	// 댓글 좋아요 / 싫어요 등록
	$("body").on("click", ".btnSendReplyLike", function(){
		var replyNum=$(this).attr("data-replyNum");
		var replyLike=$(this).attr("data-replyLike");
		var $btn = $(this);
		
		var msg="게시물이 마음에 들지 않으십니까 ?";
		if(replyLike==1)
			msg="게시물에 공감하십니까 ?";
		if(! confirm(msg))
			return;
		
		var query="replyNum="+replyNum+"&replyLike="+replyLike;
		
		var url="<%=cp%>/bbs/insertReplyLike.do";
		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var state=data.state;
				if(state=="true") {
					countReplyLike(replyNum, $btn);
				} else if(state=="false") {
					alert("좋아요/싫어요는 한번만 가능합니다. !!!");
				}
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
	
	// 댓글 좋아요 / 싫어요 개수
	function countReplyLike(replyNum, $btn) {
		var url="<%=cp%>/bbs/countReplyLike.do";
		$.ajax({
			type:"post"
			,url:url
			,data:{replyNum:replyNum}
			,dataType:"json"
			,success:function(data) {
				var likeCount=data.likeCount;
				var disLikeCount=data.disLikeCount;
				
				$btn.parent("td").children().eq(0).find("span").html(likeCount);
				$btn.parent("td").children().eq(1).find("span").html(disLikeCount);
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	}
});

// 댓글별 답글 리스트
function listAnswer(answer) {
	var url="<%=cp%>/bbs/listReplyAnswer.do";
	$.ajax({
		type:"get"
		,url:url
		,data:{answer:answer}
		,success:function(data) {
			var idAnswerList="#listReplyAnswer"+answer;
			$(idAnswerList).html(data);
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}

// 답글 버튼(댓글별 답글 등록폼 및 답글리스트)
$(function(){
	// $(".btnReplyAnswerLayout").click(function(){
	$("body").on("click", ".btnReplyAnswerLayout", function(){
		var $trReplyAnswer = $(this).closest("tr").next();
		// var $trReplyAnswer = $(this).parent().parent().next();
		// var $answerList = $trReplyAnswer.children().children().eq(0);
		
		var isVisible = $trReplyAnswer.is(':visible');
		var replyNum = $(this).attr("data-replyNum");
			
		if(isVisible) {
			$trReplyAnswer.hide();
		} else {
			$trReplyAnswer.show();
            
			// 답글 리스트
			listAnswer(replyNum);
			// 답글 개수
			countAnswer(replyNum);
		}
	});
});

// 댓글별 답글 등록
$(function(){
	$("body").on("click", ".btnSendReplyAnswer", function(){
		var num="${dto.num}";
		var replyNum = $(this).attr("data-replyNum");
		var $td = $(this).closest("td");
		var content=$td.find("textarea").val().trim();
		if(! content) {
			$td.find("textarea").focus();
			return;
		}
		content = encodeURIComponent(content);
		
		var query="num="+num+"&content="+content+"&answer="+replyNum;
		var url="<%=cp%>/bbs/insertReplyAnswer.do";
		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				$td.find("textarea").val("");
				
				var state=data.state;
				if(state=="true") {
					listAnswer(replyNum);
					countAnswer(replyNum);
				} 
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
});

// 댓글별 답글 개수
function countAnswer(answer) {
	var url="<%=cp%>/bbs/countReplyAnswer.do";
	$.ajax({
		type:"post"
		,url:url
		,data:{answer:answer}
		,dataType:"json"
		,success:function(data) {
			var count=data.count;
			var idAnswerCount="#answerCount"+answer;
			$(idAnswerCount).html(count);
		}
	    ,beforeSend :function(jqXHR) {
	    	jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}

// 댓글별 답글 삭제
$(function(){
	$("body").on("click", ".deleteReplyAnswer", function(){
		if(! confirm("게시물을 삭제하시겠습니까 ? "))
		    return;
		
		var url="<%=cp%>/bbs/deleteReplyAnswer.do";
		var replyNum=$(this).attr("data-replyNum");
		var answer=$(this).attr("data-answer");
		
		$.ajax({
			type:"post"
			,url:url
			,data:{replyNum:replyNum}
			,dataType:"json"
			,success:function(data) {
				listAnswer(answer);
			    countAnswer(answer);
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	});
});
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container2">
    <div class="body-container" style="width: 700px;">
        <div class="body-title">
            <h3><span style="font-family: Webdings">2</span> 게시판 </h3>
        </div>
        
        <div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				   ${dto.subject}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       이름 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.created} | 조회 ${dto.hitCount}
			    </td>
			</tr>
			
			<tr>
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			      ${dto.content}
			   </td>
			</tr>
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="center" height="40" style="padding-bottom: 15px;">
			       <button type='button' class='btnLike' onclick="sendBoardLike('${dto.num}')">&nbsp;&nbsp;<span style="font-family: Wingdings;">C</span>&nbsp;&nbsp;<span id="countBoardLike">${countBoardLike}</span></button>
			   </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			       이전글 :
			         <c:if test="${not empty preReadDto}">
			              <a href="<%=cp%>/bbs/article.do?${query}&num=${preReadDto.num}">${preReadDto.subject}</a>
			        </c:if>
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			    다음글 :
			         <c:if test="${not empty nextReadDto}">
			              <a href="<%=cp%>/bbs/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.subject}</a>
			        </c:if>
			    </td>
			</tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn" onclick="updateBoard('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="deleteBoard('${dto.num}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/bbs/list.do?${query}';">리스트</button>
			    </td>
			</tr>
			</table>
        </div>

 	    <div>
            <table style='width: 100%; margin: 15px auto 0px; border-spacing: 0px;'>
            <tr height='30'> 
	            <td align='left'>
	            	<span style='font-weight: bold;' >댓글쓰기</span><span> - 남겨주신 의견은 ${dto.userName}님에게 큰 보탬이 됩니다.</span>
	            </td>
            </tr>
            <tr>
               <td style='padding:5px 5px 0px;'>
                    <textarea class='boxTA' style='width:99%; height: 70px;'></textarea>
                </td>
            </tr>
            <tr>
               <td align='right'>
                    <button type='button' class='btn btnSendReply' style='padding:10px 20px;'>댓글 등록</button>
                </td>
            </tr>
            </table>
            
            <div id="listReply"></div>
 	    </div>

    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>