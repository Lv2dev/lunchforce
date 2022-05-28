<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	//당연히 실제 구현할땐 세션 확인하고 로그인 되어있는지 체크해야 함!!
	//ordermenuId에 해당하는 id가 본인이 맞는지 먼저 체크한 뒤 수행

	int ordermenuId = Integer.parseInt(request.getParameter("ordermenuId")); //ordermenu_id
	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	
	//1. 해당하는 ordermenu_id를 delete
	
	OrderDAO orderDAO = OrderDAO.getInstance();
	
	if(orderDAO.delOrdermenu(ordermenuId, memberDTO.getId())){ //삭제가 성공하면
		response.sendRedirect("Shopping.jsp"); //장바구니 페이지로 다시 이동
	}
%>