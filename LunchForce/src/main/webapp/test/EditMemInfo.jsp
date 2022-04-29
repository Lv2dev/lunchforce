<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	MemberDAO memDAO = MemberDAO.getInstance();
	MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
	String sex = "";
	if(0 == memDTO.getGender()){
		sex ="남";
	}else{
		sex="여";
	}
%>
	<form action="EditMemProc.jsp">
		id : <%= memDTO.getId() %><br>
		이름 : <%= memDTO.getName() %><br>
		닉네임 : <input name="nickname" type="text" value="<%= memDTO.getNickname()%>"><br>
		전화번호 : <input name="tel" type="text" value="<%= memDTO.getTel()%>"><br>
		이메일 : <input type="text" value="<%= memDTO.getEmail()%>"><br> 
		생일 : <%= memDTO.getbDay().toString() %><br>
		가입일 : <%= memDTO.getjDay().toString() %><br>
		성별 : <%= memDTO.getjDay().toString() %><br>
		<input type="submit" value="수정">
	</form>
</body>
</html>