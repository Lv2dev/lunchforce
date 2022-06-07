<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>점심특공대</title>
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css">
<style>
body {
	font-family: 나눔스퀘어, 'NanumSquare', sans-serif;
}
</style>
<!-- bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<meta name="theme-color" content="#7952b3">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.box {
	position: relative;
}

.box:before {
	content: "";
	display: block;
	padding-top: 80%; /* 1:1 비율 */
}

.content {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
}

.bi {
	vertical-align: -.125em;
	fill: currentColor;
}

.text-small {
	font-size: 85%;
}

.dropdown-toggle {
	outline: 0;
}

@media ( min-width : 768px) {
	.header {
		font-size: 3.5rem;
	}
}
</style>
</head>
<body>
	<!--  <div class="container">
		<header
			class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
			<a href="main.jsp"
				class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
				<span class="fs-4">김예약</span>
			</a>
			<ul class="nav nav-pills">
				<li class="nav-item"><a href="#" class="nav-link">내주변</a></li>
				<li class="nav-item"><a href="#" class="nav-link">예약</a></li>
				<li class="nav-item"><a href="#" class="nav-link">내정보</a></li>
				<li class="nav-item"><a href="#" class="nav-link">즐겨찾기</a></li>
				<li class="nav-item"><a href="#" class="nav-link">리뷰</a></li>
				<button type="button" class="btn btn-primary">로그인</button>
		}				%>
			</ul>
		</header>-->
	<header>
		<!-- nav -->
		<nav class="navbar navbar-expand-lg navbar-light"
			style="background-color: #e3f2fd;">
			<div class="container-fluid">
				<a class="navbar-brand" href="../member/Main"><b>점심</b>특공대</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse " id="navbarSupportedContent">
					<ul class="navbar-nav ms-auto mb-2 mb-lg-0 ">
						<li class="nav-item"><a class="nav-link active"
							aria-current="page" href="#">내정보</a></li>
						<li class="nav-item"><a class="nav-link active"
							aria-current="page" href="../member/Order">주문관리</a></li>
						<li class="nav-item"><a class="nav-link active"
							aria-current="page" href="#">리뷰관리</a></li>
						<li class="nav-item"><a class="nav-link active"
							aria-current="page" href="#">점특추천</a></li>
						<li class="nav-item"><a class="nav-link active"
							aria-current="page" href="../member/MyAddress">주소관리</a></li>
						<c:if test="${login == 0 }">
							<li class="nav-item mx-lg-3 mx-0 mt-1 mt-lg-0"><button
									type="button" class="btn btn-primary"
									onclick="location.href='../member/Login';">로그인</button></li>
							</li>
						</c:if>
						<c:if test="${login == 1 }">
							<li class="nav-item mx-lg-3 mx-0 mt-1 mt-lg-0"><button
									type="button" class="btn btn-primary"
									onclick="location.href='../member/Logout';">로그아웃</button></li>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	</div>
	<main class="row justify-content-center">
		<section class="py-5 px-0 mx-0 text-center container">
			<div class="pt-lg-5 pb-lg-3 px-0 mx-0 text-center">
				<div class="col-lg-6 col-md-8 mx-auto my-auto">
					<h1 class="fw-light">점심특공대</h1>
					<p class="lead text-muted">LunchForce</p>
				</div>
			</div>
		</section>
		<div class=" pb-5 mb-5 row justify-content-center col-lg-8 col-md-12">
			<form class="row justify-content-center container col-12" action="../member/Search">
				<div class="col-8">
					<input type="search" class="form-control h-3" style="height: 100%;"
						placeholder="검색어 입력" aria-label="Search" name="keyword">
				</div>
				<div class="col-4">
					<button class="w-100 btn btn-lg btn-outline-primary"
						style="height: 100%;" type="submit">검색</button>
				</div>
			</form>
		</div>
		<div
			class="px-md-0 px-lg-5 mt-5 mb-3 row justify-content-center container col-12">
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">한식</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">중식</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">일식</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">양식</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">패스트푸드</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">치킨</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">피자</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">아시안</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">카페/디저트</button>
			</div>
			<div class="box px-0 col-5 col-md-2 mx-1 mb-2">
				<button class="btn btn-lg btn-secondary content" type="button">분식</button>
			</div>

		</div>
	</main>
</body>
</html>