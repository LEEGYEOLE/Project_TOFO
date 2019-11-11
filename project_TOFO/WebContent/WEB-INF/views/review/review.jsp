<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/modal.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/table.css"
	type="text/css">
<link rel="stylesheet"
	href="https://han3283.cafe24.com/js/lightslider/css/lightslider.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="https://han3283.cafe24.com/js/lightslider/js/lightslider.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

<!--이미지 슬라이드 스크립트-->
  <script>
    $(document).ready(function(){
      $('.slider').bxSlider({
    	  speed:700,
    	  auto:true,
    	  controls:false
 
    	  
      });
    });
  </script>
  

<!--이미지 등록-->
<script type="text/javascript">
	$(function() {
		$("body").on("change","form input[name=upload]",function() {	//동적으로 추가된 객체도 이벤트 처리 가능하도록.
			if (!$(this).val())
				return false;

			var b = false;
			$("form input[name=upload]").each(function() {
				if (!$(this).val()) {
					b = true;
					return false;
				}
			});
			
			if(b) return;
			
			var $input;
			//객체의 이름이 똑같으면 다중 업로드가 불가능하다
			var n=0;
			n++;
		
			$input=$("<input>",{type:"file",name:"upload"+n, class:"boxTF",size:"53",style:"height:25px; margin-bottom: 7px;"});
			
			$("#imageupload").append($input);
				
		});
	})
</script>

<script type="text/javascript">
	$(function() {
		$("body").on("change","form input[name=upload1]",function() {	//동적으로 추가된 객체도 이벤트 처리 가능하도록.
			if (!$(this).val())
				return false;

			var b = false;
			$("form input[name=upload1]").each(function() {
				if (!$(this).val()) {
					b = true;
					return false;
				}
			});
			
			if(b) return;
			
			var $input;
			//객체의 이름이 똑같으면 다중 업로드가 불가능하다
			var n=1;
			n++;
			
			$input=$("<input>",{type:"file",name:"upload"+n,class:"boxTF",size:"53",style:"height:25px; margin-bottom: 7px;"});
			
			$("#imageupload").append($input);
				
		});
	})
</script>




<style>
th, td {
	padding: 0 15px;
}

th {
	color: #787878;
}

#style1::-webkit-scrollbar {
	width: 8px;
	background-color: rgba(255, 255, 255, 1);
}

#style1::-webkit-scrollbar-thumb {
	background-color: rgba(128, 128, 128, 0.7);
	border-radius: 5px;
}

.left-box {
	float: left;
	width: 49%;
}

.right-box {
	float: right;
	width: 49%;
}

.left-box2 {
	float: left;
	width: 70%;
}

.right-box2 {
	float: right;
	width: 30%;
}

ul, li {
	list-style: none;
}

.slide {
	height: 400px;
	overflow: hidden;
	position: relative;
	margin-top: 10px;
	margin-bottom: 50px;
	border-radius: 30px;
}

.slide li {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	opacity: 0;
	transition: 1s;
}

.slide li:nth-child(1) {
	background: #ffa;
}

.slide li:nth-child(2) {
	background: #faa;
}

.slide li:nth-child(3) {
	background: #afa;
}

.slide li:nth-child(4) {
	background: #aaf;
}

.slide input {
	display: none;
}

.slide .bullet {
	position: absolute;
	bottom: 20px;
	left: 0;
	right: 0;
	text-align: center;
	z-index: 10;
}

.slide .bullet label {
	width: 10px;
	height: 10px;
	border-radius: 10px;
	border: 2px solid #666;
	display: inline-block;
	background: #fff;
	font-size: 0;
	transition: 0.5s;
	cursor: pointer;
}
/* 슬라이드 조작 */
#pos1:checked ~ ul li:nth-child(1), #pos2:checked ~ ul li:nth-child(2),
	#pos3:checked ~ ul li:nth-child(3), #pos4:checked ~ ul li:nth-child(4)
	{
	opacity: 1;
}
/* bullet 조작 */
#pos1:checked ~ .bullet label:nth-child(1), #pos2:checked ~ .bullet label:nth-child(2),
	#pos3:checked ~ .bullet label:nth-child(3), #pos4:checked ~ .bullet label:nth-child(4)
	{
	background: #666;
}

.myfont {
	font-size: 23px;
	font-weight: 900;
	padding-bottom: 15px;
}

.myfont2 {
	font-size: 18px;
	font-weight: 500;
}

.ipb {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 600px;
	height: 400px;
	border-radius: 20px;
}

.ipb2 {
	display: table-cell;
	vertical-align: middle;
	background-color: white;
	width: 3000px;
	height: 100px;
	border-radius: 20px;
	padding: 10px;
}

.ipb3 {
	display: table-cell;
	vertical-align: middle;
	background-color: rgba(0, 0, 0, 0.18);
	width: 80%;
	height: 80%;
	border-radius: 20px;
	padding: 10px;
}

#aHover {
	color: blue;
}

#aHover:hover {
	color: #00008c;
	font-weight: 900;
}
</style>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/layout/nav.jsp"></jsp:include>
	</div>
	<div class="container">
		<div class="body-container">
			<div class="container-block">
				<div style="margin: 10px 10px">
					<div class='left-box' style="margin-bottom: 30px;">
						<div style="margin-bottom: 20px;">
							<span style="font-size: 30px; font-family: Webdings">N</span> <span
								style="font-size: 25px;">사진첩</span>
						</div>
						
							
						<div class="slider">
						<c:forEach var="photolist" items="${photolist}">
							<div style="height: 300px;"><img  title="${photolist.userId}님의 사진" src="<%=cp%>/uploads/picture/${photolist.imageFilename}" style="width: 100%; height: 100%;"></div>							
						</c:forEach>
						
						<c:if test="${empty photolist }">
						<div style="height: 300px; text-align: center; font-size: 30px; line-height: 300px;">아직 사진이 존재하지 않습니다</div>
						</c:if>
						</div>
							
							
						
						
						<div
							style="margin-left: 35px; position: relative; width: 500px; bottom: -50px;">
							<%-- <img alt="" src="<%=cp%>/resource/images/what.png" style="max-width: 300px; height: auto;"> --%>
						</div>



						<div class="ipb3">
							<div class="ipb2">
								<div class="ipb2" style="text-align: left; padding-left: 30px;">
									<div style=" text-align: right;">
										<a style="color: gray;" id="aHover" onclick="scheduleModal('peapleList','show');">참여인원보기</a>
									</div>
									
									<div style="float: left; width: 30%; height: 50px;">
										<div>
											<p class="myfont" >일시</p>
										</div>
										<p class="myfont2">${dto.startDate}</p>
									</div>

									<div style="float: left; width: 40%">
										<p class="myfont">상세설명</p>
										<div id="style1"
											style="overflow: scroll; overflow-x: hidden; width: 90%; height: 100px;">
											<p class="myfont2">${dto.contentDetail}</p>
										</div>
									</div>

									<div style=" float: left; width: 30% ;">
										<p class="myfont">장소</p>
										<p class="myfont2">
											<span id="forkakaomap">${dto.location}</span><span>
											<c:if test="${dto.location != 'empty'}">
											<button id="click_button" style="margin-left: 5px;">지도</button>
											</c:if>
											</span>
											
										</p>
									</div >
								</div>

								</div>


								<!-- 지도를 표시할 div 입니다 -->
								<div class="aa" id="map"
									style="max-width: 100%; height: 500px; display: none;"></div>

							</div>

						</div>


					</div>



					<div class='right-box'>
						<span style="font-size: 30px; font-family: Webdings">_</span> <span
							style="font-size: 25px;">후기</span> <span style="float: right">

							<button class="btnConfirm" name="hello"
								onclick="scheduleModal('ReviewAdd','show','created');">후기등록</button>
						</span>

					<div id="style1" style="overflow: scroll; overflow-x: hidden; width: 100%; height: 550px; " >
						<!--날짜가 들어가는 부분-->
						<table
							style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">

							<c:forEach var="listdto" items="${listdto}">
								
								<tr align="left" bgcolor="#eeeeee" height="35"
									style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
									<th colspan="5" width="60">${listdto.userName}님의 후기
									
										<div style="float: right; display: inline-block;">
											<span>${listdto.created}</span>
											<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==listdto.userId}">
											<button onclick="scheduleModal('ReviewUpdate','show','update',${listdto.reviewNum},'${listdto.contentDetail}');">수정</button>
											<button onclick="javascript:location.href='<%=cp%>/review/delete.do?reviewnum=${listdto.reviewNum}&page=${page}&rows=${rows}&condition=${condition}&num=${schedule_num}&groupnum=${groupNum}'">삭제</button>
											</c:if>
										</div>
									</th>
								</tr>


								<tr align="left" bgcolor="#ffffff" height="35"
									style="border-bottom: 1px solid #cccccc;">
									<td colspan="5">${listdto.contentDetail}</td>


								</tr>
							</c:forEach>
						</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>
	<!-- 참여인원목록 -->
	<div class="my-modal show-modal" id="peapleList">

		<div class="modal-content">

			<div>
				<table id="moo">
					<tr>
						<th>참여인원목록
							
							 <span class="close-button"
							onclick="scheduleModal('peapleList', 'none');">×</span>
						</th>
					</tr>
				</table>
			</div>


			<form action="#post.php" method="POST">


				<div style="overflow: scroll; overflow-x: hidden; overflow-y : auto;width: 100%; height: 300px;">
					<ol class="list">
					<c:forEach var="personlist" items="${personlist}">
						<li style="font-size: 15px; padding:13px 15px 13px 15px; background-color: white;  margin: 10px; border-radius: 5px;">

						아이디 :${personlist.userId} <br>
						이름 : ${personlist.userName} <br>
						번호 : ${personlist.tel}<br>

						</li>
					</c:forEach>
					</ol>
				</div>


			</form>
		</div>
	</div>

	<!-- 후기등록 -->
	<div class="modal show-modal" id="ReviewAdd">

		<div class="modal-content">

			<div>
				<table id="moo">
					<tr>
						<th style="font-size: 26px; text-align: left">후기 등록<span class="close-button"
							onclick="scheduleModal('ReviewAdd', 'none');">×</span>
						</th>

					</tr>
				</table>
			</div>
			

			<form class="modalForm" name="modalForm" action="#post.php"
				method="POST" enctype="multipart/form-data">


				<p>${sessionScope.member.userName}님의후기</p>

				<textarea class="textwrite" name="reviewcontetn" placeholder="상세일정"></textarea>

				<div style="text-align: right; font-size: 12px ;margin: 6px 0px;">*파일 업로드는 3장까지 가능합니다.</div>


				<div id =imageupload>
					<input type="file" name="upload" accept="image/*" class="boxTF" size="53" style="height: 25px; margin-bottom: 7px;">
					<input type="hidden" name="reviewNum" value="${schedule_num}">
					
				
				</div>

					
				<div>
							
					<button class="shortbtn" type="button"
						onclick="scheduleModal('ReviewAdd', 'none');">취소하기</button>
						
					<button name="graybtnname"class="graybtn" type="button" onclick="sendOk();"></button>
					
						
					
					
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="rows" value="${rows}">
					<input type="hidden" name="condition" value="${condition}">
					<input type="hidden" name="groupnum" value="${groupNum}">
				</div>

			</form>
		</div>

	</div>
	
	<!-- 후기수정 -->
	<div class="modal show-modal" id="ReviewUpdate">

		<div class="modal-content">

			<div>
				<table id="moo">
					<tr>
						<th style="font-size: 26px; text-align: left">후기 수정 <span class="close-button"
							onclick="scheduleModal('ReviewUpdate', 'none');">×</span>
						</th>

					</tr>
				</table>
			</div>
			
		
		<form class="modalForm" name="modalFormUpdate" action="#post.php">
				

<input type="hidden" name="forreviewNum" value=""/>
				<p>${sessionScope.member.userName}님의후기</p>

				<textarea class="textwrite" name="reviewcontetnUpdate" placeholder="상세일정"></textarea>
				
				

				

				<div>
					
					<button class="shortbtn" type="button"
						onclick="scheduleModal('ReviewUpdate', 'none');">취소하기</button>
						
					<button name="graybtnnameupdate"class="graybtn" type="button" onclick="sendOkUpdate();"></button>
				
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="rows" value="${rows}">
					<input type="hidden" name="condition" value="${condition}">
					<input type="hidden" name="groupnum" value="${groupNum}">
				</div>

			</form>
		</div>

	</div>







	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e99194984cb0bb9f3ba4db6533fbba3c&libraries=services"></script>
		 <!--후기 등록 스크립트--> 
<script type="text/javascript">
    function sendOk() {
        var f = document.modalForm;
        
    	var str = f.reviewcontetn.value;
	  	
        if(!str) {
            alert("후기을 입력하세요. ");
            f.subject.focus();
            return;
        } 

		

        f.action="<%=cp%>/review/created_ok.do";

        
        f.submit();
    	
    
    }   
    
</script>

<script type="text/javascript">
    function sendOkUpdate() {
        var f = document.modalFormUpdate;
        
    	var str = f.reviewcontetnUpdate.value;
	  	
        if(!str) {
            alert("후기을 입력하세요. ");
            f.subject.focus();
            return;
        } 

		

        f.action="<%=cp%>/review/update_ok.do";

        
        f.submit();
    	
    
    }   
    
</script>





	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};

		//이미지 토글 
		$('#click_button').click(function()
				{$('.aa').slideToggle('normal',function() {
					
												// 지도를 생성합니다    
												var map = new kakao.maps.Map(
														mapContainer, mapOption);

												// 주소-좌표 변환 객체를 생성합니다
												var geocoder = new kakao.maps.services.Geocoder();
												
/* 												//주소가 정해지지 않았을 시
												if($("#forkakaomap").text()=='empty'){
													$("#forkakaomap").innerHTML ="장소 미정";
												} */
												
												// 주소로 좌표를 검색합니다
												geocoder.addressSearch(
														
													$("#forkakaomap").text(),function(result, status) {

																	// 정상적으로 검색이 완료됐으면 
																	if (status === kakao.maps.services.Status.OK) {

																		var coords = new kakao.maps.LatLng(
																				result[0].y,
																				result[0].x);

																		// 결과값으로 받은 위치를 마커로 표시합니다
																		var marker = new kakao.maps.Marker(
																				{
																					map : map,
																					position : coords
																				});

																		// 인포윈도우로 장소에 대한 설명을 표시합니다
																		var infowindow = new kakao.maps.InfoWindow(
																				{
																					content : '<div style="width:150px;text-align:center;padding:3px 0;">만났던 장소</div>'
																				});
																		infowindow
																				.open(
																						map,
																						marker);

																		// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
																		map
																				.setCenter(coords);
																	}
																});
											});

						});
	</script>
	
<script type="text/javascript">
	window.onload = function() {
		
		var ReviewAdd = document.getElementById("ReviewAdd");
		ReviewAdd.style.display = 'none';

		var peapleList = document.getElementById("peapleList");
		peapleList.style.display = 'none';
		
		var ReviewUpdate = document.getElementById("ReviewUpdate");
		ReviewUpdate.style.display = 'none';
	}

	function scheduleModal(name, state, mode,reviewNum,content) {
		
		if (state == 'show') {
			document.getElementById(name).style.display = 'block';
		} else if (state == 'none') {
			document.getElementById(name).style.display = 'none';

		}
		
		if(mode=='created'){
	        var f = document.modalForm;
	        f.graybtnname.innerHTML="후기등록"; 
	       
		}else if(mode=='update'){
			 var f = document.modalFormUpdate;
			 f.forreviewNum.value = reviewNum;
			
		    f.graybtnnameupdate.innerHTML="후기수정"; 
		    f.reviewcontetnUpdate.innerHTML = content;
	    	
		}
	};
</script>
	
	
</body>
</html>