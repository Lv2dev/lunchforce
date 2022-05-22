<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO mdtoAdd = new MemberDTO();
	
	MemberDTO mdto = (MemberDTO)session.getAttribute("memberDTO");
	
	mdtoAdd.setAddress(request.getParameter("address"));
	mdtoAdd.setAddressX(Double.parseDouble(request.getParameter("x")));
	mdtoAdd.setAddressY(Double.parseDouble(request.getParameter("y")));
	mdtoAdd.setId(mdto.getId());
	
	mdao.newAddress(mdtoAdd);
	session.setAttribute("memberDTO", mdao.getMemberInfo(mdto.getId())); //세션의 memberDTO 갱신
	response.sendRedirect("MemberAddress.jsp");
%>