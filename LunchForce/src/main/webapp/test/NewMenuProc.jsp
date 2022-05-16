<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	StoreDTO sDTO = (StoreDTO)session.getAttribute("storeDTO");
	MenuDAO mDAO = MenuDAO.getInstance();
	MenuDTO mDTO = new MenuDTO();
	
	mDTO.setStoreId(sDTO.getStoreId());
	mDTO.setMenuName(request.getParameter("menuName"));
	mDTO.setPrice(Integer.parseInt(request.getParameter("price")));
	mDTO.setNotice(request.getParameter("notice"));
	mDTO.setPic(null);
	mDTO.setAllergy(Integer.parseInt(request.getParameter("price"), 2));
	mDTO.setFeeling(Integer.parseInt(request.getParameter("feeling"), 2));
	mDTO.setCondition(Integer.parseInt(request.getParameter("condition"), 2));
	mDTO.setWeather(Integer.parseInt(request.getParameter("weather"), 2));
	mDTO.setTemperature(Integer.parseInt(request.getParameter("temperature"), 2));
	mDTO.setDust(Integer.parseInt(request.getParameter("dust"), 2));
	mDTO.setHumidity(Integer.parseInt(request.getParameter("humidity"), 2));
	mDTO.setFavor(Integer.parseInt(request.getParameter("favor"), 2));
	mDTO.setCalorie(Integer.parseInt(request.getParameter("calorie"), 2));
	mDTO.setHealth(Integer.parseInt(request.getParameter("health"), 2));
	mDTO.setCategory(Integer.parseInt(request.getParameter("category")));
	mDTO.setNutrient(Integer.parseInt(request.getParameter("nutrient"), 2));
	
	
	if(mDAO.addMenu(mDTO)){
		response.sendRedirect("StoreView.jsp?storeId=" + sDTO.getStoreId());
	}else{
		response.sendRedirect("StoreView.jsp?storeId=" + sDTO.getStoreId());
	}
%>