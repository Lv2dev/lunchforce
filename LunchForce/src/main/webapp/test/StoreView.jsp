<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<%
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	MemberDTO mdto = (MemberDTO)session.getAttribute("memberDTO");
	StoreDAO sdao = StoreDAO.getInstance();
	MenuDAO mdao = MenuDAO.getInstance();
	int storeId;
	storeId = Integer.parseInt(request.getParameter("storeId"));
	sdto = sdao.getStoreInfo(storeId);
	session.setAttribute("storeDTO", sdto);//세션에 storeDTO 추가
	
	int state = sdao.getState(storeId);//가게의 현재상태 가져오기

	
	LinkedHashMap<Integer, String> hash = mdao.getAllMenu(storeId);
%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	가게이름 : <%= sdto.getStoreName() %> <br>
	가게공지 : <%= sdto.getNotice() %><br>
	가게오픈 : <%= sdto.getOpenTime() %><br> 
	가게폐점 : <%= sdto.getCloseTime() %><br>
	쉬는시간 : <%= sdto.getBraketimeStart() %><br>
	가게메뉴 <br>
	<%
	if(hash != null){
		for (Entry<Integer, String> entrySet : hash.entrySet()) {
			%>
				<a href="MenuView.jsp?menuId=<%= entrySet.getKey() %>"><%= entrySet.getValue() %></a><br>
			<%
			}
	}
	%>
<br>
<% 
	//주소정보가 이미 있으면 주소가 뜨게 하고 없으면 주소추가 버튼 뜨게 하기
	sdto = sdao.getAddressInfo(storeId);
	if(sdto == null){
%>
	<a href="NewStoreAddress.jsp">주소추가</a><br>
<%
	}else{
		%>
		<%=sdto.getAddress() %><br>
		<a href="NewStoreAddress.jsp">주소수정</a><br>
		<%
	}
%>
<a href="DeleteStore.jsp?">가게삭제</a><br> 
<a href="EditStore.jsp">가게수정</a><br> 
<a href="NewMenu.jsp">메뉴추가</a><br>
<a href="EditMenuNumber.jsp">메뉴순서변경</a><br>
<%
	
	if(state == 1){
		%>
		<a href="StoreClose.jsp">가게가 열려있어요. 눌러서 닫기</a>
		<%
	}else if(state == 0){
		%>
		<a href="StoreOpen.jsp">가게가 닫혀있어요. 눌러서 열기</a>
		<%
	}
%>
<!-- 메뉴순서변경은 나중에... -->
<!-- 가게 상태 보여주는 것과 가게 열기닫기 버튼 추가하기 -->
</body>
</html>