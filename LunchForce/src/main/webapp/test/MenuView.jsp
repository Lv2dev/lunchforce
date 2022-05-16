<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map.Entry"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	int storeId = sdto.getStoreId(); 
	int menuId = Integer.parseInt(request.getParameter("menuId"));
	MenuDAO mdao = MenuDAO.getInstance();
	MenuDTO mdto = mdao.getStoreInfo(storeId, menuId);
	MenuOptionDAO optionDao = MenuOptionDAO.getInstance();
	ArrayList<MenuOptionDTO> list = optionDao.getOptionList(menuId);
	session.setAttribute("menuDTO", mdto);
%>
메뉴정보<br>
메뉴명 : <%= mdto.getMenuName() %><br>
가격 : <%= mdto.getPrice() %><br>
메뉴설명 : <%= mdto.getNotice() %><br>
옵션들<br>
<%
	for(MenuOptionDTO i : list) { //for문을 통한 전체출력
	    out.print("이름 : " + i.getOptionName() + "가격 : " + i.getPrice() + "<br>" + 
	"<a href='DeleteOption.jsp?optionId=" + i.getId() + "'>삭제</a>");
	}

%>
<a href="NewMenuOption.jsp">메뉴옵션추가</a><br>
<a href="DeleteMenu.jsp">메뉴삭제</a>
