<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 회원 목록</title>
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<style>
	.userInfo img{
		width: 100px;
		height : 100px;
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
	<h1>전체 회원 목록</h1>
	
	<table border='1' class='userInfo'>
		<tr>
			<th>사용자이미지</th>
			<th>사용자아이디</th>
			<th>포인트</th>
		</tr>
	<c:forEach items="${requestScope.members }" var="member">
		<tr>
			<td><a href='memberview.do?uid=${member.userid }'><img src="${ member.userimg}" /></a></td>
			<td><b>${member.userid }</b></td>
			<td>${member.point }</td>
		</tr>
	</c:forEach>
	</table>
	
	${requestScope.members }
</body>
</html>