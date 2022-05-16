<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>

<%
	MenuDTO mdto = (MenuDTO)session.getAttribute("menuDTO");
	String optionName = request.getParameter("optionName");
	int price = Integer.parseInt(request.getParameter("price"));
	MenuOptionDAO modao = MenuOptionDAO.getInstance();
	MenuOptionDTO modto = new MenuOptionDTO();
	
	modto.setMenuId(mdto.getMenuId());
	modto.setOptionName(optionName);
	modto.setPrice(price);
	
	modao.addOption(modto);
	
	response.sendRedirect("MenuView.jsp?menuId=" + mdto.getMenuId());
%>