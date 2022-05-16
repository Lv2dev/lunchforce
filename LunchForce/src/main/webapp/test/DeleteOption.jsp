<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	MenuDAO mdao = MenuDAO.getInstance();
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	int optionId = Integer.parseInt(request.getParameter("optionId"));
	MenuDTO mdto = (MenuDTO)session.getAttribute("menuDTO");
	MenuOptionDAO modao = MenuOptionDAO.getInstance();
	
	modao.delOption(optionId, mdto.getMenuId());
	
	response.sendRedirect("StoreView.jsp?storeId=" + sdto.getStoreId());
%>