
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
String id = (String)session.getAttribute("id");
String answer = (String)request.getParameter("answer");
String pwd = null;
pwd = memDAO.getPassword(id, answer);
if(pwd == null){
	%>
		
		답변 틀림<%= answer %><br><button onclick="location='login.html'">로그인창으로</button>
	<%
}else{
 %>
	비밀번호는 <%= pwd %><br><button onclick="location='login.html'">로그인창으로</button>
<%
}
%>
</body>
</html>