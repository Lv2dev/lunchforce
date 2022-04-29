<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ page import="com.lunchforce.member.*" %>

<%
	MemberDAO memDAO = MemberDAO.getInstance();
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String message = "";
	
	if(memDAO.memberLogin(id, pw)){
		message = "로그인성공";
	}
%>

<h3><%= message %></h3>