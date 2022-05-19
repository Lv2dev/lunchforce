<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	MemberDAO mdao = MemberDAO.getInstance();
	
	MemberDTO mdto = (MemberDTO)session.getAttribute("memberDTO");
	
	mdto.setAddress(request.getParameter("address"));
	mdto.setAddressX(Double.parseDouble(request.getParameter("x")));
	mdto.setAddressY(Double.parseDouble(request.getParameter("y")));
	mdto.setId(mdto.getId());
	
	mdao.newAddress(mdto); 
	response.sendRedirect("address.jsp");
%>