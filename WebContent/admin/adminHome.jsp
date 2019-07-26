<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</head>
<body>

	<c:if test='${sessionScope.loginMember.isadmin != "y"}'>
		<script>
			alert('관리자만 출입이 가능합니다!');
			history.back();
		</script>
		
	</c:if>
	
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<!-- Brand/logo -->
		<a class="navbar-brand" href="../index.jsp">Home</a>

		<!-- Links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link"
				href="../memberManage.do">회원관리</a></li>
		</ul>
	</nav>
	
	<h1>관리자 홈</h1>
	
	
	

</body>
</html>