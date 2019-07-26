<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈</title>
<link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	$(document).ready(function() {
		
	});
</script>
<style>
	.userImg  img{
		width: 100px;
		height : 100px;
		border-radius: 100px;
	}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<!-- Brand/logo -->
		<a class="navbar-brand" href="#">Home</a>

		<!-- Links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link"
				href="member/registerMember.jsp">회원가입</a></li>
			<li class="nav-item"><a class="nav-link" href="#">로그인</a></li>
			<li class="nav-item"><a class="nav-link" href="admin/adminHome.jsp">관리자페이지</a></li>
			<li class="nav-item"><a class="nav-link" href="#">게시판</a></li>
		</ul>
	</nav>
	
	
	
	<div class="container-fluid">
		<h3>홈 페이지</h3>
		<!-- 로그인 하지 않았을 때는 로그인을 할 수 있도록 한다. -->
		<c:if test="${sessionScope.loginMember == null }">
			<div id='withoutLoginDiv'>
				<form action="login.do" method="post">
					<div class="form-group">
						<label for="uid">아이디</label> <input type="text"
							class="form-control" id="uid" name="uid" required >
					</div>
					
					<div class="form-group">
						<label for="pwd1">비밀번호:</label> <input type="password"
							class="form-control" id="pwd1" name="pwd1" required>			
					</div>
					<button type="reset" class="btn btn-danger">취소</button>
					<button type="submit" class="btn btn-success">로그인</button>
					<button type="button" class="btn btn-warning" onclick="location.href='member/registerMember.jsp'">회원가입</button>
				</form>
			</div>
		</c:if>
		<!-- ------------------------------------------------------------ -->
		<!-- 로그인 했을 때의 정보 ------------------------------------------------------------>
		<c:if test="${sessionScope.loginMember != null }">
			<div id='loginDiv'>
				<div class='userImg'><img src='${sessionScope.loginMember.userimg }' /></div>
				<div>${sessionScope.loginMember.userid } 님 안녕하세요!</div>
				<div>point : ${sessionScope.loginMember.point }</div>
				<button type="button" class="btn btn-danger" onclick="location.href='logout.do';">로그아웃</button>
			</div>
		</c:if>
		<!-- ------------------------------------------------------------------------------- -->
	</div>
	
	${sessionScope.loginMember }

</body>
</html>