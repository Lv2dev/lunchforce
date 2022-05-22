<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	StoreDAO sdao = StoreDAO.getInstance();
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	
	sdao.setClose(sdto.getStoreId());
	response.sendRedirect("StoreView.jsp?storeId="+sdto.getStoreId());
%>