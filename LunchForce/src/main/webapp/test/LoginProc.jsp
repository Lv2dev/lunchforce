<%@page import="com.lunchforce.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<%
	MemberDAO memDAO = MemberDAO.getInstance();
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	
	Boolean bool = memDAO.memberLogin(id, pw);
	
	if(bool == true){
		out.println("로그인성공");
		session.setAttribute("memberDTO", memDAO.getMemberInfo(id)); //세션변수로 멤버정보 저장
		response.sendRedirect("MainProc.jsp");
	}else{
		response.sendRedirect("login.html");
	}
%>