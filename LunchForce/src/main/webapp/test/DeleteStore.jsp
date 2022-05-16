<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO");
	StoreDAO sdao = StoreDAO.getInstance();
	
	if(sdao.deleteStore(sdto.getStoreId())){
		out.print("<script>alert(\"삭제 완료\");</script>"); //삭제 완료되면 alert
		
		response.sendRedirect("StoreMain.jsp"); //그 후에 리다이렉트
	}else{
		out.print("<script>alert(\"삭제 실패\");</script>"); //삭제 완료되면 alert
		
		response.sendRedirect("StoreMain.jsp"); //그 후에 리다이렉트
	}
%>