<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<style>
	#memberInfo img{
		width: 300px;
		height : 300px;
	}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<!-- Brand/logo -->
		<a class="navbar-brand" href="../index.jsp">Home</a>

		<!-- Links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link"
				href="memberManage.do">회원관리</a></li>
		</ul>
	</nav>
	<h1>개인 회원 정보</h1>
	
	${requestScope.member }
	
	
	<div id='memberInfo'>
		<!-- 회원 기본 정보 -->
		<div class='userImg'><img src='${requestScope.member.userimg }'/></div>
		<div>아이디 : <b>${requestScope.member.userid }</b> 님 안녕하세요!</div>
		<div>포인트 : ${requestScope.member.point }</div>
		<div>가입일 : ${requestScope.member.registerdate }</div>
	</div>
	
	<div id='pointlog'>
		<!-- 회원의 포인트 로그 내역 -->
		<table border='1'>
		<tr>
			<th>포인트 가감일</th>
			<th>가감 포인트</th>
			<th>포인트 가감 사유</th>
		</tr>
			<c:forEach items="${requestScope.pointLog }" var = "log">
				<tr>
				<td>${log.givendate }</td>
				<td>${log.pointval }</td>
				<td>${log.why }</td>
				</tr>
			</c:forEach>
		</table>
		
	
	</div>
</body>
</html>