<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="generator" content="Hugo 0.84.0">
<title>로그인</title>

<link rel="canonical"
	href="https://getbootstrap.kr/docs/5.0/examples/sign-in/">



<!-- Bootstrap core CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<!-- Favicons -->
<link rel="apple-touch-icon"
	href="/docs/5.0/assets/img/favicons/apple-touch-icon.png"
	sizes="180x180">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon-32x32.png"
	sizes="32x32" type="image/png">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon-16x16.png"
	sizes="16x16" type="image/png">
<link rel="manifest" href="/docs/5.0/assets/img/favicons/manifest.json">
<link rel="mask-icon"
	href="/docs/5.0/assets/img/favicons/safari-pinned-tab.svg"
	color="#7952b3">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon.ico">
<meta name="theme-color" content="#7952b3">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://kit.fontawesome.com/487e80ebce.js" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css">
<style>
body {
	font-family: 나눔스퀘어, 'NanumSquare', sans-serif;
}
</style>


<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>


<!-- Custom styles for this template -->
<link href="login.css" rel="stylesheet">
</head>
<body class="text-center">

	<main class="form-signin">
		<form action="../member/LoginProc" method="post">
			<div class="mb-4">
				<i class="fa-solid fa-right-to-bracket fa-5x"></i>
			</div>
			<h1 class="h3 mb-3 fw-normal">${ notice }</h1>

			<div class="form-floating mb-2">
				<input type="text" class="form-control" id="floatingInput"
					placeholder="ID" name="id"> <label for="floatingInput">아이디</label>
			</div>
			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="floatingPassword"
					placeholder="Password" name="pw"> <label
					for="floatingPassword">비밀번호</label>
			</div>

			<div class="button mb-3">
				<input class="w-100 btn btn-lg btn-primary" type="submit"
					value="로그인" />
			</div>
			<div class="button mb-3">
				<button class="w-100 btn btn-lg btn-primary" type="button"
					onclick="location.href='../member/Join';">회원가입</button>
			</div>

			<p class="mt-5 mb-3 text-muted">© 17831050 이지훈</p>
		</form>
	</main>

</body>
</html>