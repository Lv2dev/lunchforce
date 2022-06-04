<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<script defer>
setTimeout("location.reload()", 11000); //계속 새로고침
</script>
<%request.setCharacterEncoding("UTF-8");%>
<a href="StoreEndOrder.jsp">완료된 주문 보기</a><br>
<a href="StoreOKOrder.jsp">수락된 주문 보기</a><br>
<%
	OrderDAO orderDAO = OrderDAO.getInstance();
	MemberDAO memberDAO = MemberDAO.getInstance();

	StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	
	//주문대기상태인 주문 불러오기
	ArrayList<OrderDTO> list = orderDAO.getStoreOrderDTOList(storeDTO.getStoreId(), 1);
	
	//불러온 주문목록 출력 - 누르면 주문의 상세 페이지로 이동
	for(OrderDTO i : list){
		%>
		주문일시 : <%= String.valueOf(i.getOrderDate()) %><br>
		주문자ID : <%= i.getUserId() %><br>
		가격 : <%= i.getPrice() %><br>
		<a href="ViewStoreOrder.jsp?orderlistId=<%= i.getOrderlistId() %>">주문보기</a><br><br>
		<%
	}
%>