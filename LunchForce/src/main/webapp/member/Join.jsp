<!-- 회원가입 페이지 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="generator" content="Hugo 0.84.0">
<title>회원가입</title>

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

</head>
<body class="bg-light">

	<div class="container">
		<main>
			<div class="py-5 text-center">
				<div class="mb-4">
				<i class="fa-solid fa-user-group"></i>
				</div>
				<h2>회원가입</h2>
				<p class="lead">점심특공대에 가입하고 더 많은 혜택을 누려보세요</p>
			</div>

			<div class="row justify-content-center">
				<div class="col-md-7 col-lg-8">
					<h4 class="mb-3">${ notice }</h4>
					<form class="needs-validation" novalidate="" action="../member/JoinProc" method="post">
						<div class="row g-3">

							<!-- 이름 -->
							<div class="col-sm-6">
								<label for="name" class="form-label">이름</label> <input
									type="text" class="form-control" id="name" placeholder="이름"
									value="" required="" name="name">
								<div class="invalid-feedback">이름을 입력해 주세요</div>
							</div>

							<!-- 닉네임 -->
							<div class="col-12">
								<label for="nickname" class="form-label">닉네임</label>
								<div class="input-group has-validation">
									<span class="input-group-text">@</span> <input type="text"
										class="form-control" id="nickname" placeholder="닉네임"
										required="" name="nickname">
									<div class="invalid-feedback">닉네임을 입력해 주세요</div>
								</div>
							</div>

							<!-- 아이디 -->
							<div class="col-12">
								<label for="id" class="form-label">아이디</label> <input
									type="text" class="form-control" id="id" placeholder="ID"
									name="id" required="">
								<div class="invalid-feedback">아이디를 입력해 주세요</div>
							</div>

							<!-- 비밀번호 -->
							<div class="col-12">
								<label for="pw" class="form-label">비밀번호</label> <input
									type="password" class="form-control" id="pw" placeholder=""
									name="pw" required="">
								<div class="invalid-feedback">비밀번호를 입력해 주세요</div>
							</div>

							<!-- 비밀번호 확인 -->
							<div class="col-12">
								<label for="pw2" class="form-label">비밀번호 확인</label> <input
									type="password" class="form-control" id="pw2" placeholder="" onchange="check_pw()" required=""><br>
								<label for="pw2" class="form-label" id="pwChk"></label>
								<div class="invalid-feedback">비밀번호 확인 칸을 입력해 주세요</div>
							</div>

							<!-- 전화번호 -->
							<div class="col-12">
								<label for="tel" class="form-label">전화번호</label> <input
									type="tel" class="form-control" id="tel"
									placeholder="01000000000" name="tel" required="">
								<div class="invalid-feedback">비밀번호가 다릅니다</div>
							</div>

							<!-- 이메일 -->
							<div class="col-12">
								<label for="email" class="form-label">이메일</label> <input
									type="email" class="form-control" id="email"
									placeholder="email@kimyeyak.com" name="email" required="">
								<div class="invalid-feedback">이메일을 입력해 주세요</div>
							</div>

							<!-- 생일 -->
							<div class="col-12">
								<label for="bDay" class="form-label">생일</label> <input
									type="date" class="form-control" id="bDay" name="bDay" required="" pattern="yyyy-MM-dd">
								<div class="invalid-feedback">생일을 입력해 주세요</div>
							</div>

							<!-- 타입 -->
							<div class="col-12">
								<label for="type" class="form-label">타입</label>
								<div class="my-3">
									<div class="form-check">
										<input id="credit" name="type" type="radio"
											class="form-check-input" checked="" required="" value="2"> <label
											class="form-check-label" for="type0">일반 회원</label>
									</div>
									<div class="form-check">
										<input id="debit" name="type" type="radio"
											class="form-check-input" required="" value="1"> <label
											class="form-check-label" for="type1">사업자 회원</label>
									</div>
								</div>
							</div>
							
							<!-- 성별 -->
							<div class="col-12">
								<label for="gender" class="form-label">성별</label>
								<div class="my-3">
									<div class="form-check">
										<input id="credit" name="gender" type="radio"
											class="form-check-input" checked="" required="" value="0"> <label
											class="form-check-label" for="gender0">남</label>
									</div>
									<div class="form-check">
										<input id="debit" name="gender" type="radio"
											class="form-check-input" required="" value="1"> <label
											class="form-check-label" for="gender1">여</label>
									</div>
								</div>
							</div>
							
							<!-- 본인확인 질문 -->
							<div class="col-12">
								<label for="question" class="form-label">본인확인 질문</label> <input
									type="text" class="form-control" id="question" placeholder="question"
									name="question" required="">
								<div class="invalid-feedback">본인확인 질문을 작성해 주세요</div>
							</div>
							
							<!-- 본인확인 답변 -->
							<div class="col-12">
								<label for="answer" class="form-label">본인확인 답변</label> <input
									type="text" class="form-control" id="answer" placeholder="answer"
									name="answer" required="">
								<div class="invalid-feedback">본인확인 답변을 작성해 주세요</div>
							</div>
							
						</div>

						<hr class="my-4">

						<button class="w-100 btn btn-primary btn-lg" type="submit">Continue
							to checkout</button>
					</form>
				</div>
			</div>
		</main>

		<footer class="my-5 pt-5 text-muted text-center text-small">
			<p class="mb-1">© 17831050 이지훈</p>
			<ul class="list-inline">
				<li class="list-inline-item"><a href="#">연락 안받음</a></li>
				<li class="list-inline-item"><a href="#">내 정보는 비밀임</a></li>
				<li class="list-inline-item"><a href="#">찾아오는길 없음</a></li>
			</ul>
		</footer>
	</div>


	<script src="/docs/5.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script src="join.js"></script>
</body>
</html>