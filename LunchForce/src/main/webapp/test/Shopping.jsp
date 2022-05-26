<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
	//1. a태그로 해서 클릭하면 삭제되고 이 페이지를 새로고침
	//2. 메뉴 아래에 옵션하나 즉 ordermenu -> orderoption
	//3. 2차원 ArrayList로 menu_id와 option_id 가져와서 옆에 가격 붙이기
	//4. 마지막에 합계 가격 출력
	
	//1. 2차원 ArrayList 가져오기
	
%>