<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원가입</title>
<link rel="stylesheet"
   href="https://han3283.cafe24.com/js/lightslider/css/lightslider.css" />
<script
   src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
   src="https://han3283.cafe24.com/js/lightslider/js/lightslider.js"></script>
<style type="text/css">
.header {
   width: 100%;
   text-align: left;
}

.container {
   clear: both;
   width: 100%;
   text-align: left;
}

.body-container {
   clear: both;
   margin: 0px auto 15px;
   min-height: 450px;
}

.body-title {
   color: #424951;
   padding-top: 20px;
   padding-bottom: 5px;
   margin: 0 0 25px 0;
   border-bottom: 1px solid #dddddd;
}

.body-title h3 {
   font-size: 23px;
   min-width: 300px;
   font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
   font-weight: bold;
   margin: 0 0 -5px 0;
   padding-bottom: 5px;
   display: inline-block;
   border-bottom: 3px solid #424951;
}

.footer {
   clear: both;
   width: 100%;
}

/* header */
.header-top {
   margin: 0px auto;
   width: 100%;
   height: 70px;
   clear: both;
}

.header-left {
   width: 480px;
   height: 70px;
   float: left;
}

.header-right {
   width: 480px;
   height: 70px;
   float: left;
}

/* footer */
.footer {
   width: 100%;
   height: 50px;
   margin: 5px auto;
   text-align: center;
   background-color: #ffffff;
   line-height: 50px;
   border-top: 1px solid #cccccc;
}

/* menu */
.menu {
   margin: 0px auto;
   margin-bottom: 20px;
   width: 100%;
   min-height: 40px;
   clear: both;
   /*box-shadow: inset 0 1px 0 rgba(255,255,255,.15), 0 1px 5px rgba(0,0,0,.075);*/
   background-color: black;
   border: 1px solid #e7e7e7;
   box-sizing: border-box;
}

ul.nav {
   width: 100%;
   margin: 0;
   padding: 0;
   list-style: none;
}

ul.nav li {
   display: inline;
}

ul.nav li a {
   float: right;
   line-height: 70px;
   color: #555;
   text-decoration: none;
   margin: 0;
   padding: 0 10px;
}

ul.nav .current a, ul.nav li:hover>a {
   color: white;
   font-weight: 1200;
   text-decoration: none;
}

ul.nav ul {
   display: none;
}

ul.nav li:hover>ul {
   position: absolute;
   display: block;
   width: 960px;
   height: 40px;
   margin: 40px 0 0 -1px;
   background: #e9e9e9;
   border: 1px solid #e9e9e9;
   z-index: 101;
   box-sizing: border-box;
}

ul.nav li:hover>ul li a {
   float: left;
   line-height: 39px;
   color: #333;
   text-decoration: none;
   margin: 0;
   padding: 0 20px 0 20px;
   background: #e9e9e9;
}

ul.nav li:hover>ul li a:hover {
   color: #333;
   text-decoration: none;
}

/* navigation */
.navigation {
   width: 100%;
   height: 40px;
   clear: both;
   background: #ffffff;
   border-bottom: 1px solid #cccccc;
}

.nav-bar {
   width: 960px;
   height: 40px;
   clear: both;
   margin: 0px auto;
   line-height: 40px;
   text-align: right;
   background: #ffffff;
}

* {
   margin: 0;
   padding: 0;
}

body {
   font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
   font-size: 14px;
   /* line-height: 1.42857143; */
   /* box-sizing: border-box;*/ /* padding과 border는 크기에 포함되지 않음 */
   /* 스크롤바를 없앱니다. */
   overflow-y: hidden;
   overflow-x: hidden;
}

a {
   cursor: pointer;
   color: #000000;
   text-decoration: none;
   /* line-height: 150%; */
   /* box-sizing: border-box;*/ /* padding과 border는 크기에 포함되지 않음 */
}

a:hover, a:active {
   font-weight: 700;
}

.btn {
   color: #333333;
   font-weight: 500;
   font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
   border: 1px solid #cccccc;
   background-color: #fff;
   text-align: center;
   cursor: pointer;
   padding: 3px 10px 5px;
   border-radius: 4px;
}

.btn:active, .btn:focus, .btn:hover {
   background-color: #e6e6e6;
   border-color: #adadad;
   color: #333333;
}

.btn[disabled], fieldset[disabled] .btn {
   pointer-events: none;
   cursor: not-allowed;
   filter: alpha(opacity = 65);
   -webkit-box-shadow: none;
   box-shadow: none;
   opacity: .65;
}

.btnConfirm {
   font-size: 15px;
   border: none;
   color: #ffffff;
   background: #507CD1;
   width: 360px;
   height: 50px;
   line-height: 50px;
   border-radius: 4px;
}

.boxTF {
   border: 1px solid #999999;
   padding: 3px 5px 5px;
   border-radius: 4px;
   background-color: #ffffff;
   font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}

.boxTF[readonly] {
   background-color: #eeeeee;
   /* border: none;*/
}

.selectField {
   border: 1px solid #999999;
   padding: 2px 5px 4px;
   border-radius: 4px;
   font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}

.boxTA {
   border: 1px solid #999999;
   height: 150px;
   padding: 3px 5px;
   border-radius: 4px;
   background-color: #ffffff;
   font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}

/*이미지 슬라이드 */
ul {
   list-style: none outside none;
   padding-left: 0;
   margin: 0;
}

.slide-content .slide-content {
   margin-bottom: 6px;
}

.slider li {
   text-align: center;
   color: #FFF;
   background-size: cover;
   background-position: center;
}

.slider h3 {
   margin: 0;
   padding: 100px 0;
   height: 250px;
}

.slide-content {
   width: 305%;
   height: 300px;
}

.item1 {
   background-image: url('http://han3283.cafe24.com/images/strawberry.jpg');
}

.item2 {
   background-image: url('http://han3283.cafe24.com/images/cherry.jpg');
}

.item3 {
   background-image: url('http://han3283.cafe24.com/images/strawberry.jpg');
}

.item4 {
   background-image: url('http://han3283.cafe24.com/images/grape.jpg');
}

.item5 {
   background-image: url('http://han3283.cafe24.com/images/lemon.jpg');
}

.item6 {
   background-image: url('http://han3283.cafe24.com/images/grapefruit.jpg');
}

/*상단바 버튼 처리*/
.button {
   width: 140px;
   height: 45px;
   font-family: 'Roboto', sans-serif;
   font-size: 11px;
   text-transform: uppercase;
   letter-spacing: 2.5px;
   font-weight: 500;
   color: #000;
   background-color: #fff;
   border: none;
   border-radius: 45px;
   box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
   transition: all 0.3s ease 0s;
   cursor: pointer;
   outline: none;
}
/*상단바 버튼 후버~  */
.button:hover {
   background-color: #2EE59D;
   box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
   color: #fff;
   transform: translateY(-7px);
}

/*테이블 중앙정렬*/
.tb1 {
   position: absolute;
   top: 60%;
   left: 50%;
   margin-top: -100px;
   margin-left: -100px;
   width: 150px;
   height: 100px;
}

/*input폼*/
/*로그인 화인 input폼 */
.if1 {
   background-color: #eeeeee;
   border-radius: 10px;
   width: 250px;
   height: 30px;
}

.if2 {
   background-color: #eeeeee;
   border-radius: 10px;
   width: 250px;
   height: 25px;
}

/*인폿품이 들어잇는 네모 바디1(로그인) */
.ipb {
   background-color: rgba(128, 128, 130, 0.5);
   position: absolute;
   top: 45%;
   left: 37%;
   margin-top: -100px;
   margin-left: -100px;
   width: 600px;
   height: 350px;
}
/*인폿품이 들어잇는 네모 바디2(회원가입)) */
.ipb2 {
   background-color: rgba(128, 128, 130, 0.5);
   position: absolute;
   top: 33%;
   left: 45%;
   margin-top: -100px;
   margin-left: -100px;
   width: 400px;
   height: 550px;
}

/* 테이블1*/
.tb1 {
   position: absolute;
   top: 60%;
   left: 40%;
   margin-top: -100px;
   margin-left: -100px;
   width: 150px;
   height: 100px;
}
/* 테이블2*/
.tb2 {
   position: absolute;
   top: 30%;
   left: 41%;
   margin-top: -100px;
   margin-left: -100px;
   width: 150px;
   height: 100px;
   border-spacing: 11px;
   color: white;
}
/*로그인 버튼*/
.bt1 {
   background-color: #eeeeee;
   border-radius: 10px;
   height: 40px;
   width: 100%;
   margin-top: 10px;
   margin-bottom: 10px;
}

/* 등록하기 버튼 표준*/
.bt2 {
   background-color: #444444;
   border: 1px solid #444444;
   border-radius: 5px;
   color: #fff;
   display: block;
   font-size: 15px;
   font-weight: 500;
   margin: 5px auto;
   padding: 10px 30px;
   position: relative;
   text-transform: uppercase;
}

.bt2:hover {
   color: white;
   font-weight: bold;
   transform: translateY(-3px);
}
</style>