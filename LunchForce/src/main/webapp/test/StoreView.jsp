<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<!DOCTYPE html>
<html>
<head>
<%
	Integer storeId = Integer.parseInt(request.getParameter("storeId"));
	MemberDTO mdto = (MemberDTO)session.getAttribute("memberDTO");
	StoreDAO sdao = StoreDAO.getInstance();
	StoreDTO sdto = sdao.getStoreInfo(storeId, mdto.getId());
	MenuDAO mdao = MenuDAO.getInstance();
	session.setAttribute("storeDTO", sdto);
	
	LinkedHashMap<Integer, String> hash = mdao.getAllMenu(storeId);
%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	가게이름 : <%= sdto.getStoreName() %> <br>
	가게공지 : <%= sdto.getNotice() %><br>
	가게오픈 : <%= sdto.getOpenTime() %><br> 
	가게폐점 : <%= sdto.getCloseTime() %><br>
	쉬는시간 : <%= sdto.getBraketimeStart() %><br>
	가게메뉴 <br>
	<%
	if(hash != null){
		for (Entry<Integer, String> entrySet : hash.entrySet()) {
			%>
				<a href="MenuView.jsp?menuId=<%= entrySet.getKey() %>"><%= entrySet.getValue() %></a><br>
			<%
			}
	}
	%>
	
</body>
</html>