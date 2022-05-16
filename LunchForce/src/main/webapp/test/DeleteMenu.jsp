<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	MenuDTO mdto = (MenuDTO)session.getAttribute("menuDTO");
	MenuDAO mdao = MenuDAO.getInstance();
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	
	if(mdao.delMenu(mdto.getMenuId())){
		response.sendRedirect("StoreView.jsp?storeId=" + sdto.getStoreId());
	}
%>