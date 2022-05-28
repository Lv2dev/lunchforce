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
	//1. a태그로 해서 클릭하면 삭제되고 이 페이지를 새로고침
	//2. 메뉴 아래에 옵션하나 즉 ordermenu -> orderoption
	//3. LinkedHashMap으로 ordermenu_id, menu_id와 option_id 가져와서 옆에 가격 붙이기
	//4. 마지막에 합계 가격 출력
	
	//1. 2차원 ArrayList 가져오기
	
	OrderDAO orderDAO = OrderDAO.getInstance();
	StoreDAO storeDAO = StoreDAO.getInstance();
	MenuOptionDAO moDAO = MenuOptionDAO.getInstance();
	
	MenuDAO menuDAO = MenuDAO.getInstance();
	
	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	String userId = memberDTO.getId();
	int status = 0;
	
	ArrayList<ArrayList<Integer>> list = orderDAO.getOrderList(userId, status);
	OrderDTO orderDTO = orderDAO.getOrder(userId, 0);
	
%>
<%
	if(list == null){
		out.print("리스트는 널이야");
	}
	if(list != null){
		out.print("리스트는널이 아니야");
		for(int i = 0; i < list.size(); i++){
			ArrayList<String> menuList = orderDAO.getMenuInfo(list.get(i).get(0));
			%>
				메뉴명 : <br>
				<a href = 'DelMenu.jsp?ordermenuId=<%= list.get(i).get(0) %>'>
					<%= menuList.get(0) %>
				</a>
				가격 : <%= menuList.get(1) %>
				<br>
			<%
			//옵션이 있는 경우 수행
			int size = list.get(i).size();
			if(size > 1){
				for(int j = 1; j < size; j++){
					ArrayList<String> optionList = orderDAO.getOptionInfo(list.get(i).get(j));
					%>
					옵션명 : <br>
					<a href = 'DelOption.jsp?orderoptionId=<%= list.get(i).get(j) %>'>
						<%= optionList.get(0) %>
					</a>
					가격 : <%= optionList.get(1) %>
					<br>
					<%
				}
			}
		}
	}
%>
<br>
총 <%= orderDTO.getPrice() %> 원