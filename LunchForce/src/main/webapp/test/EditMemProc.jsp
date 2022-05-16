<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<%
	MemberDAO memDAO = MemberDAO.getInstance();
	MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
	
	String nickname = request.getParameter("nickname");
	String tel = request.getParameter("tel");
	String email = request.getParameter("email");
	boolean bool;
	if(nickname != memDTO.getNickname() || tel != memDTO.getTel() || email != memDTO.getEmail()){
		memDTO.setNickname(nickname);
		memDTO.setTel(tel);
		memDTO.setEmail(email);
		memDAO.joinMember(memDTO);
		session.setAttribute("memberDTO", memDAO.getMemberInfo(memDTO.getId()));
	}
	response.sendRedirect("MainProc.jsp");
%>