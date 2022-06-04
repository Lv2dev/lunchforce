<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="com.lunchforce.order.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	//수락된 주문들을 보는 페이지
	StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
	OrderDAO orderDAO = OrderDAO.getInstance();
	MemberDAO memberDAO = MemberDAO.getInstance();
	ArrayList<OrderDTO> list = orderDAO.getStoreOrderDTOList(storeDTO.getStoreId(), 2);
	SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
	
	if(list != null){
		for(OrderDTO i : list){
			OrderDTO orderDTO;
			MemberDTO m = memberDAO.getMemberInfo(i.getUserId());
			%>
			고객명 : <%= m.getName() %><br> 
			주문일시 : <%= String.valueOf(i.getOrderDate()) %><br>
			고객연락처 : <%= m.getTel() %><br>
			주문대기시간 : <%= i.getTime() %><br>
			<%
			orderDTO = orderDAO.getStoreOrderDTO(storeDTO.getStoreId(), 1, i.getOrderlistId()); 
			session.setAttribute("orderDTO", orderDTO);
			
			String price = "0";
			if(orderDTO != null){
				price = String.valueOf(orderDTO.getPrice());
			}
			
			//주문의 상세정보 출력
			ArrayList<ArrayList<Integer>> list2 = orderDAO.getStoreOrderList(storeDTO.getStoreId(), 2, i.getOrderlistId());
			
			if(list2 == null){
				out.print("리스트는 널이야(오류)");
			}
			if(list2 != null){
				for(int j = 0; j < list2.size(); j++){
					ArrayList<String> menuList = orderDAO.getMenuInfo(list2.get(j).get(0));
					%>
						메뉴명 : <%= menuList.get(0) %><br>
						가격 : <%= menuList.get(1) %>
						<br>
					<%
					//옵션이 있는 경우 수행
					int size = list2.get(j).size();
					if(size > 1){
						for(int q = 1; q < size; q++){
							ArrayList<String> optionList = orderDAO.getOptionInfo(list2.get(j).get(q)); 
							%>
							옵션명 : <%= optionList.get(0) %><br>
							가격 : <%= optionList.get(1) %>
							<br><br>
							<%
						}
					}
				}
				%>
				<br><br><br>
				<%
			}
			%>
			<br><br>
			<%
		}
	}
%>