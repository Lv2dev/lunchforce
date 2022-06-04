<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat" %>
<%request.setCharacterEncoding("UTF-8");%>

<%
	//진행중인 주문내역을 보여줌
	//시간나면 자동새로고침 기능도 추가(켜고 끌 수 있게 - autoreload되는 페이지 만들어서 거기로 이동되게 만들듯)
	OrderDAO orderDAO = OrderDAO.getInstance();

	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	
	
	SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
	
	
	
	//먼저 수락 대기중인 주문 먼저 출력함
	ArrayList<OrderDTO> list = orderDAO.getOrderDTOList(memberDTO.getId(), 1);
	
	for(OrderDTO i : list){
		//들어가서 이미 수락되어있는지 체크한 뒤 대기중일때만 상태변경(메서드 새로 만들기)
		%>
		<a href="CancelOrderProc.jsp">
			주문시간 : <%= String.valueOf(i.getOrderDate()) %> <br>
			상태 : 수락 대기중<br>
			가격 : <%= i.getPrice() %>
		</a><br><br>
		<%
	}
	
	list = orderDAO.getOrderDTOList(memberDTO.getId(), 2);//2 이니까 수락된 주문
	
	for(OrderDTO i : list){
		//주문완료는 이 페이지에 들어오면 페이지를 새로고침해서 
		%>
		<a href="ViewOrder.jsp">
			주문수락시간 : <%= String.valueOf(i.getOrderDate()) %> <br>
			예상소요시간 : <%= i.getTime() %><br>
			가격 : <%= i.getPrice() %>
		</a><br><br>
		<%
	}
%>