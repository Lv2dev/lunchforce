<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	StoreDAO sdao = StoreDAO.getInstance();
	
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	
	String address = request.getParameter("address") + request.getParameter("detail");
	sdto.setAddress(address);
	sdto.setAddressX(Double.parseDouble(request.getParameter("x")));
	sdto.setAddressY(Double.parseDouble(request.getParameter("y")));
	sdto.setStoreId(sdto.getStoreId());
	
	sdao.newAddress(sdto); 
	response.sendRedirect("StoreView.jsp?storeId="+sdto.getStoreId());
%>