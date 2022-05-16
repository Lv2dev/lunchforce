<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	StoreAddressDAO sadao = StoreAddressDAO.getInstance();
	StoreAddressDTO sadto = new StoreAddressDTO();
	
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	
	sadto.setAddress(request.getParameter("address"));
	sadto.setAddressX(Double.parseDouble(request.getParameter("x")));
	sadto.setAddressY(Double.parseDouble(request.getParameter("y")));
	sadto.setStore_id(sdto.getStoreId());
	
	sadao.newAddress(sadto);
	response.sendRedirect("StoreView.jsp?storeId=" + sdto.getStoreId());
%>