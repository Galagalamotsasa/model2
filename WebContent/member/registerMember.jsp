<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script>

	var isValid = false; // 아이디의 중복 검사 결과
	function check() {
		// 회원 가입시 수행하는 유효성 검사
		var result = true;
		
		console.log(typeof isValid + " : " + isValid);
		
		var uid = $('#uid').val();
		var pwd1 = $('#pwd1').val();
		var pwd2 = $('#pwd2').val();
		
		if (uid.length < 6 || uid.length > 10) {
			$('#errorUID').html('아이디는 6~10자로 기입하세요..!');			
			result = false;
		} 
		if (pwd1.length < 6 || pwd1.length > 12) {
			$('#errorPwd').html('비밀번호는 6~12자리로 기입하세요..');
			result = false;
		} 
		if (pwd1 !== pwd2) {
			$('#errorPwd2').html('비밀번호가 서로 맞지 않습니다..');
			$('#pwd1').focus();
			result = false;
		}
		// 아이디가 중복 되지 않고, 위 유효성 검사를 모두 만족한다면 가입!
		// 유효성 검사 조건을 하나라도 만족하지 못하면 아이디가 중복 되지 않더라도 가입 안되어야 함
		// 또한 유효성 검사 조건이 충족 되더라도, 아이디가 중복이면 가입 안되어야 함.
		
		if (idisdup && result) {
			return true;
		} else {
			return false;
		}
		 
	    
	}
	
	
	function idisdup() {
			 // 아이디 텍스트박스에 키가 눌려졌을 때
			var uid = $('#uid').val();
			var result = true;
			if (uid.length >= 6) { // 6자 이상이 들어왔을 때만 중복 검사
				$.ajax({ // 서블릿에 전송하여 아이디가 중복인지 아닌지 결과를 가져와야 한다. (백 엔드 와의 비동기 통신)
					type : "GET",
					url : "../control.do?uid=" + uid,
					dataType : "json", // 서버에서 반환되는 데이터 타입
					success : function(data) {
						console.log(data);
						
						if (data.resultCode == 'true') {
							$('#errorUID').html('아이디가 중복됩니다~');
							result = false;
						} 
					},
					error : function(res) {
						console.log(res.responseText);
					},
					complete : function() {
						setTimeout(() => {
							$('#errorUID').empty()
						}, 500);
					}
				}); // ajax 끝
			}
			return result;
			
	}
	
	$(document).ready(function() {
		$('#uid').keyup(idisdup);
		
	});
</script>
<style>
	.error {
		color : red;
	}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<!-- Brand/logo -->
		<a class="navbar-brand" href="../index.jsp">Home</a>

		<!-- Links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="#">회원가입</a></li>
			<li class="nav-item"><a class="nav-link" href="#">로그인</a></li>
			<li class="nav-item"><a class="nav-link" href="#">게시판</a></li>
		</ul>
	</nav>

	<div class="container-fluid">
		<h3>회원가입 페이지</h3>


		<form action="../control.do" class="was-validated" method="post" onsubmit="return check();" enctype="multipart/form-data" 
		autocomplete="off">
			<div class="form-group">
				<label for="uid">아이디</label> <input type="text"
					class="form-control" id="uid" placeholder="아이디는 6~10자로 기입하세요.."
					name="uid" required a>
				<!--<div class="valid-feedback">Valid.</div>
				<div class="invalid-feedback">Please fill out this field.</div>-->
				<div id="errorUID" class="error"></div>
			</div>
			
			<div class="form-group">
				<label for="pwd1">비밀번호:</label> <input type="password"
					class="form-control" id="pwd1" placeholder="비밀번호는 6~12자리로 기입하세요.."
					name="pwd1" required>
				 <div id="errorPwd" class="error"></div>
				
			</div>
			<div class="form-group">
				<label for="pwd2">비밀번호 확인:</label> <input type="password"
					class="form-control" id="pwd2" placeholder="비밀번호 확인..."
					name="pwd2" />
					<div id="errorPwd2" class="error"></div>
					
			<div class="form-group">
				<label for="userImg">회원 이미지:</label> <input type="file"
					class="form-control" id="userImg" name="userImg" />
					<div id="errorImg" class="error"></div>	
			
			</div>
			<div class="form-group form-check">
				<label class="form-check-label"> <input
					class="form-check-input" type="checkbox" name="remember" required>
					회원가입 조항에 동의합니다.
					<div class="valid-feedback">Valid.</div>
					<div class="invalid-feedback">Check this checkbox to
						continue.</div>
				</label>
			</div>
			<button type="reset" class="btn btn-danger">취소</button>
			<button type="submit" class="btn btn-success">가입</button>
		</form>
	</div>


</body>
</head>
<body>

</body>
</html>