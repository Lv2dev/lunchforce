<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	MemberDTO mdto1 = (MemberDTO)session.getAttribute("memberDTO");
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO mdto2 = mdao.getAddressInfo(mdto1.getId());
	if(mdto2 != null){
		out.print("주소 : " + mdto2.getAddress() + "<br>");
	}
	
%>
<a href='NewMemberAddress.jsp'>주소수정</a>