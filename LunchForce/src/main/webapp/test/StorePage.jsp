<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	StoreDAO sdao = StoreDAO.getInstance();
	int storeId = Integer.parseInt(request.getParameter("storeId"));
	StoreDTO sdto = sdao.getStoreInfo(storeId);
	MenuDAO mdao = MenuDAO.getInstance();
	session.setAttribute("storeDTO", sdto);//세션에 storeDTO 추가
	LinkedHashMap<Integer, String> hash = mdao.getAllMenu(storeId);
	
%>
	<img src="<%=sdto.getThumb()%>" width="200" height="200"/><br>
	가게명 : <%=sdto.getStoreName()%><br>
	가게설명 : <%= sdto.getNotice() %><br>
	<%
		int state = sdao.getState(storeId);
		if(state == 0){
			out.print("가게가 열려있어요.<br>");
		}
		else if(state == 1){
			out.print("가게가 닫혀있어요.<br>");
		}else{
			out.print("주문불가<br>");
		}
	%>
	메뉴<br>
	<%
	if(hash != null){
		for (Entry<Integer, String> entrySet : hash.entrySet()) {
			%>
				<a href="Menu.jsp?menuId=<%= entrySet.getKey() %>"><%= entrySet.getValue() %></a><br>
			<%
			}
	}
	%>
	<br>
<a href="Shopping.jsp">장바구니</a>
	