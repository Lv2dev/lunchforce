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
	//주문의 세부정보 출력
	OrderDAO orderDAO = OrderDAO.getInstance();

	StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
	int orderlistId = Integer.parseInt(request.getParameter("orderlistId"));
	OrderDTO orderDTO = orderDAO.getStoreOrderDTO(storeDTO.getStoreId(), 1, orderlistId); 
	session.setAttribute("orderDTO", orderDTO);
	
	String price = "0";
	if(orderDTO != null){
		price = String.valueOf(orderDTO.getPrice());
	}
	
	//주문의 상세정보 출력
	ArrayList<ArrayList<Integer>> list = orderDAO.getStoreOrderList(storeDTO.getStoreId(), 1, orderlistId);
	
	if(list == null){
		out.print("리스트는 널이야(오류)");
	}
	if(list != null){
		out.print("리스트는널이 아니야");
		for(int i = 0; i < list.size(); i++){
			ArrayList<String> menuList = orderDAO.getMenuInfo(list.get(i).get(0));
			%>
				메뉴명 : <%= menuList.get(0) %><br>
				가격 : <%= menuList.get(1) %>
				<br>
			<%
			//옵션이 있는 경우 수행
			int size = list.get(i).size();
			if(size > 1){
				for(int j = 1; j < size; j++){
					ArrayList<String> optionList = orderDAO.getOptionInfo(list.get(i).get(j));
					%>
					옵션명 : <%= optionList.get(0) %><br>
					가격 : <%= optionList.get(1) %>
					<br><br>
					<%
				}
			}
		}
	}
	
	//총액 출력 후 수락, 거절 페이지로 이동하는 앵커 태그 추가
	session.setAttribute("orderDTO", orderDTO);
%>
총 <%= price %> 원<br><br>
<form action="StoreOrderOK.jsp?">
	<input type="text" name="time">분 후에 준비돼요<br>
	<input type="submit" value="주문수락!">
</form>
<a href="StoreOrderIgnore.jsp">주문거절</a><br>
