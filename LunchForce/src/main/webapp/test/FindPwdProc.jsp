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
		MemberDTO memDTO = new MemberDTO();
		String result = "";
		result = request.getParameter("result");
		if(result == "yes"){
			%>
				비밀번호는 <%= session.getAttribute("pwd").toString()%><br>
			<%
		}else if(result=="no"){
			%>
				답변틀림<br>
			<%
		}
	%>
<form action="FindPwdProc2.jsp">
	id입력 <input type="text" name="id">
	<input type="submit" value="제출">
</form>
</body>
</html>