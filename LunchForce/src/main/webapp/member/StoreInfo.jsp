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
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css">
<style>
body {
	font-family: 나눔스퀘어, 'NanumSquare', sans-serif;
}
</style>
<style>
.box {
	position: relative;
}

.box:before {
	content: "";
	display: block;
	padding-top: 100%; /* 1:1 비율 */
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
<body class="row justify-content-center">
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
							aria-current="page" href="../member/Recommend">점특추천</a></li>
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
	<main class="row justify-content-center container">
		<section class="py-5 px-0 mx-0 text-center container">
			<div class="pt-lg-5 pb-lg-3 px-0 mx-0 text-center">
				<div class="col-lg-6 col-md-8 mx-auto my-5">
					<h1 class="m-0 mb-3" style="font-weight: 800;">${ storeDTO.storeName }</h1>
					<p class="lead text-muted" style="font-weight: 400;">
						<c:if test="${ storeDTO.status == 0 }">
						오픈 준비중
						</c:if>
						<c:if test="${ storeDTO.status > 0 }">
						메뉴를 눌러서 장바구니에 추가할 수 있습니다.
						</c:if>
					</p>
				</div>
			</div>
		</section>
		<div
			class="px-md-0 px-lg-5 p-0 mx-0 mb-3  row justify-content-center container col-11 col-md-7">

			<div
				class="col-12 p-0 px-1 m-0 container row justify-content-center">
				<!-- 가게이미지 -->
				<div
					class="col-12 col-md-12 mb-2 py-1 mx-2 container row justify-content-center align-items-center"
					style="clear: both;">
					<div
						class="col-4 col-md-3 m-1 text-center shadow-lg rounded-lg bg-body ">
						<img alt="가게이미지" src="${ storeDTO.thumb }">
					</div>
				</div>
			</div>
			<div
				class="col-12 p-0 px-1 m-0 container row justify-content-center mt-5">
				<div
					class="col-12 col-md-12 mb-2 container row justify-content-left align-items-center m-0 pl-auto"
					style="font-weight: 400">
					<h4>상세 정보</h4>
				</div>
			</div>
			<!-- 상세정보들 -->
			<div
				class="col-12 col-md-12 mb-2  mx-2 container row justify-content-center shadow-lg rounded-lg bg-body align-items-center mt-5 py-3"
				style="clear: both;">
				<div class="col-11 col-md-10 m-1 text-left">
					<h3>공지사항</h3>
					<br>
					<h5>${ storeDTO.notice }</h5>
				</div>
			</div>
			<!-- 24시간 영업이 아닌 경우 -->
			<c:if test="${ openTimeChk == 0 }">
			<div
				class="col-12 col-md-12 mb-2  mx-2 container row justify-content-center shadow-lg rounded-lg bg-body align-items-center py-3"
				style="clear: both;">
				<div
					class="col-12 p-0 px-1 m-0 container row justify-content-center ">
					<div class="col-11 col-md-10 m-1 text-left">
						<h3>영업시간</h3>
						<br>
						<h5>${ openTime }~${ closeTime }</h5>
					</div>
				</div>
				</div>
			</c:if>
			<!-- 24시간 영업인 경우 -->
			<c:if test="${ openTimeChk == 1 }">
			<div
				class="col-12 col-md-12 mb-2  mx-2 container row justify-content-center shadow-lg rounded-lg bg-body align-items-center py-3"
				style="clear: both;">
				<div
					class="col-12 p-0 px-1 m-0 container row justify-content-center ">
					<div class="col-11 col-md-10 m-1 text-left">
						<h3>영업시간</h3>
						<br>
						<h5>24시간 영업</h5>
					</div>
				</div>
				</div>
			</c:if>
			<!-- 휴무 시간이 있는 경우 -->
			<c:if test="${ breakTimeChk == 0 }">
			<div
				class="col-12 col-md-12 mb-2  mx-2 container row justify-content-center shadow-lg rounded-lg bg-body align-items-center py-3"
				style="clear: both;">
				<div
					class="col-12 p-0 px-1 m-0 container row justify-content-center ">
					<div class="col-11 col-md-10 m-1 text-left">
						<h3>휴무시간</h3>
						<br>
						<h5>${ breakTimeStart }~${ breakTimeEnd }</h5>
					</div>
				</div>
				</div>
			</c:if>
			<!-- 쉬는 요일 -->
			<c:if test="${ not empty restDay }">
			<div
				class="col-12 col-md-12 mb-2  mx-2 container row justify-content-center shadow-lg rounded-lg bg-body align-items-center py-3"
				style="clear: both;">
				<div
					class="col-12 p-0 px-1 m-0 container row justify-content-center">
					<div class="col-11 col-md-10 m-1 text-left">
						<h3>휴일</h3>
						<br>
						<h5>${ restDay }</h5>
					</div>
				</div>
				</div>
			</c:if>
			<section class="py-5 px-0 mx-0 text-center container">
			<div class="pt-lg-5 pb-lg-3 px-0 mx-0 text-center">
				<div class="col-lg-6 col-md-8 mx-auto my-5">
					<h1 class="m-0 mb-3" style="font-weight: 800;">찾아오시는 길</h1>
					<p class="lead text-muted" style="font-weight: 400;">
						<!-- 담긴 주소가 없을 때 -->
						<c:if test="${ addressChk == 0 }">
						주소가 입력되어 있지 않은 가게입니다.
						</c:if>
						<!-- 담긴 주소가 있을 때 -->
						<c:if test="${ addressChk == 1 }">
						${ addressDTO.address }
						</c:if>
					</p>
				</div>
			</div>
		</section>
		<!-- 지도가 표시되는 곳 -->
		<c:if test="${ addressChk == 1 }">
			<div
				class="box px-md-0 px-lg-5 p-0 mx-0 mb-3  row justify-content-center container col-10 col-md-8"
				id="map"></div>
		</c:if>
		</div>
	</main>

<c:if test="${ addressChk == 1 }">
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=60fe788f0ea06f351b62582019d41e56&libraries=services"></script>
	<script>
var x, y, address;
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption);
//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();
//마커를 미리 생성
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});


//해당 주소에 대한 좌표를 받아서
var coords = new daum.maps.LatLng(${addressDTO.addressY}, ${addressDTO.addressX});
// 지도를 보여준다.
mapContainer.style.display = "block";
map.relayout();
// 지도 중심을 변경한다.
map.setCenter(coords);
// 마커를 결과값으로 받은 위치로 옮긴다.
marker.setPosition(coords);
</script>
</c:if>
</body>
</html>