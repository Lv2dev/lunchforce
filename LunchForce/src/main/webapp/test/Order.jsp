<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%
//들어온 주문을 orderlist 테이블(장바구니 상태)에 추가

String[] optionPrice = request.getParameterValues("option"); //option아이디를 받아옴
MenuDTO menuDTO = (MenuDTO) session.getAttribute("menuDTO");

MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
StoreDTO storeDTO = (StoreDTO) session.getAttribute("storeDTO");

OrderDTO orderDTO;
OrderDAO orderDAO = OrderDAO.getInstance();

if(optionPrice != null){
	int optionId[] = new int[optionPrice.length]; 
	
	for (int i = 0; i < optionPrice.length; i++) {
		optionId[i] = Integer.parseInt(optionPrice[i]);
	}
	orderDAO.addShopping(memberDTO.getId(), storeDTO.getStoreId(), menuDTO.getMenuId(), optionId);
		//장바구니에 메뉴와 옵션 추가
}else{
	orderDAO.addShopping(memberDTO.getId(), storeDTO.getStoreId(), menuDTO.getMenuId(), new int[0]);
}
	response.sendRedirect("Menu.jsp?menuId=" + menuDTO.getMenuId());
%>