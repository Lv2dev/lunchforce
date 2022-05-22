<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.ArrayList" %>
<%request.setCharacterEncoding("UTF-8");%>
<%
	StoreDAO sdao = StoreDAO.getInstance();
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO mdto = (MemberDTO)session.getAttribute("memberDTO");
	String keyword = request.getParameter("search");
	int distance = Integer.parseInt(request.getParameter("distance"));
	String type = request.getParameter("type");
	ArrayList<StoreDTO> list = sdao.getSearchList(keyword, mdto.getAddressX(), mdto.getAddressY(), distance);
	
	if(list != null){
	for(StoreDTO i : list){
		%>
		<a href="StorePage.jsp?storeId=<%=i.getStoreId()%>"><%= i.getStoreName() %></a><br>
		<%
	}
	}else{
		out.println("없어요");
	}
%>