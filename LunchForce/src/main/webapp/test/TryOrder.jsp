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
	//주문을 수행하는 페이지
	OrderDAO orderDAO = OrderDAO.getInstance();
	OrderDTO orderDTO = (OrderDTO)session.getAttribute("orderDTO");
	
	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	
	if(orderDAO.setState(memberDTO.getId(), 1)); {//orderlist의 status를 1로 바꿈{
		response.sendRedirect("ViewMyOrders.jsp");	//내 주문 내역으로 페이지 이동
	}
%>