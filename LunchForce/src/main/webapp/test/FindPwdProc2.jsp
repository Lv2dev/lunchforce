<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		MemberDAO memDAO = MemberDAO.getInstance();
		String id = request.getParameter("id");
		session.setAttribute("id", id);
	%>
	<form action="FindPwdProc3.jsp" method="post">
		질문 : <%= memDAO.getQuestion(id) %><br>
		답변 : <input type="text" name="answer"><br>
		<input type="submit">
	</form>
</body>
</html>