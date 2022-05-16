<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.MemberDTO"%>
<%@page import="com.lunchforce.member.MemberDAO"%>
<%@page import="com.lunchforce.store.StoreDAO"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
		MemberDAO memDAO = MemberDAO.getInstance();
		
		if(memDTO.getType() == 0){
	%>
		<!-- html -->
		<h3>관리자 페이지</h3>
			
	<%
		}else if(memDTO.getType() == 1){
	%>
			<!-- html -->
			<h3>사업자 페이지</h3>	
	<%
			response.sendRedirect("StoreMain.jsp");	
		}else{
	%>
			<!-- html -->
			<h3>일반회원 페이지</h3>		
	<%
			response.sendRedirect("NomalMain.html");
		}
	%>
</body>
</html>