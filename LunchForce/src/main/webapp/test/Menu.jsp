<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.*"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	MemberDAO memberDAO = MemberDAO.getInstance();
	MenuDAO menuDAO = MenuDAO.getInstance();
	MenuOptionDAO moDAO = MenuOptionDAO.getInstance();
	
	int menuId = Integer.parseInt(request.getParameter("menuId"));
	MenuDTO menuDTO = menuDAO.getMenuInfo(menuId);
	MemberDTO memberDTO = (MemberDTO)session.getAttribute("memberDTO");
	session.setAttribute("menuDTO", menuDTO);
	
	ArrayList<MenuOptionDTO> list = moDAO.getOptionList(menuId);
	
	
%>
메뉴정보<br>
메뉴명 : <%= menuDTO.getMenuName() %><br>
가격 : <%= menuDTO.getPrice() %><br>
메뉴설명 : <%= menuDTO.getNotice() %><br>
옵션들<br>
<form action="Order.jsp">
<%
	for(MenuOptionDTO i : list) { //for문을 통한 전체출력
	%>
		<%=i.getOptionName() %> : <%= i.getPrice() %>원<input type="checkbox" name="option" value="<%=i.getId()%>"/><br>
	<%
	}

%>
<input type="submit" value="주문하기"/>
</form>
