<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	//들어온 주문을 orderlist 테이블(장바구니 상태)에 추가
	
	String[] optionPrice = request.getParameterValues("option");
	MenuDTO menuDTO = (MenuDTO)session.getAttribute("menuDTO");
	
	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
	
	OrderDTO orderDTO;
	
	int optionId[] = new int[optionPrice.length];
	OrderDAO orderDAO = OrderDAO.getInstance();
	for(int i = 0 ; i < optionPrice.length ; i++){
		optionId[i] = Integer.parseInt(optionPrice[i]);
	}
		if(orderDAO.addShopping(memberDTO.getId(), storeDTO.getStoreId(), menuDTO.getMenuId(), optionId)){
			
		}
	
%>