<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map.Entry"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<%
	MemberDAO memDAO = MemberDAO.getInstance();
	MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
	
	StoreDAO sDAO = StoreDAO.getInstance();
	LinkedHashMap<Integer, String> storeHash = sDAO.getStoreList(memDTO.getId()); 
	
%>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	가게 목록<br>
<%
	if(storeHash != null){
		for (Entry<Integer, String> entrySet : storeHash.entrySet()) {
			%>
				<a href="StoreView.jsp?storeId=<%= entrySet.getKey() %>"><%= entrySet.getValue() %></a><br>
			<%
			}
	}
%>
<button onclick="location='NewStore.jsp'">가게 생성</button>
</body>
</html>