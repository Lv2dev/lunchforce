<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.sql.Date"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가게추가처리</title>
<%
	MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
	MemberDAO memDAO = MemberDAO.getInstance();
	StoreDAO storeDAO = StoreDAO.getInstance();
	StoreDTO storeDTO = new StoreDTO();
	
	storeDTO.setStoreName(request.getParameter("storeName"));
	storeDTO.setCategory(request.getParameter("category"));
	storeDTO.setNotice(request.getParameter("notice"));
	storeDTO.setTel(request.getParameter("tel")); //썸네일은 나중에 추가
	storeDTO.setOpenTime(Integer.parseInt(request.getParameter("openTime")));
	storeDTO.setCloseTime(Integer.parseInt(request.getParameter("closeTime")));
	storeDTO.setBraketimeStart(Integer.parseInt(request.getParameter("brakeTimeStart")));
	storeDTO.setBraketimeEnd(Integer.parseInt(request.getParameter("brakeTimeEnd")));
	storeDTO.setStatus(0);
	
	storeDTO.setUserId(memDTO.getId());
	
	storeDAO.joinStore(storeDTO);
	
	response.sendRedirect("StoreMain.jsp");
%>
</head>
<body>

</body>
</html>