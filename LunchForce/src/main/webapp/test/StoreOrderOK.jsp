<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	int time = Integer.parseInt(request.getParameter("time"));
	OrderDTO orderDTO = (OrderDTO)session.getAttribute("orderDTO");
	int orderlistId = orderDTO.getOrderlistId();
	
	OrderDAO orderDAO = OrderDAO.getInstance();
	StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
	
	if(orderDAO.setStoreOrderState(storeDTO.getStoreId(), orderlistId, 2, time)){
		//수락 성공 시
		response.sendRedirect("StoreOKOrder.jsp");
	}
%>