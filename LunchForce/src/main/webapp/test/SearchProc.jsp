<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.ArrayList" %>
<%request.setCharacterEncoding("UTF-8");%>
<%
	StoreDAO sdao = StoreDAO.getInstance();
	String keyword = request.getParameter("search");
	ArrayList<StoreDTO> list = sdao.getSearchList(keyword);
	for(StoreDTO i : list){
		%>
		<a href="StorePage.jsp?storeId=<%=i.getStoreId()%>"><%= i.getStoreName() %></a><br>
		<%
	}
%>