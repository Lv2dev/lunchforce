<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lunchforce.member.*"%>
<%@page import="com.lunchforce.store.*"%>
<%request.setCharacterEncoding("UTF-8");%>
<%

	MemberDTO memDTO = (MemberDTO)session.getAttribute("memberDTO");
	MemberDAO memDAO = MemberDAO.getInstance();
	StoreDAO storeDAO = StoreDAO.getInstance();
	StoreDTO sdto = (StoreDTO)session.getAttribute("storeDTO"); //기존 세션 정보
	StoreDTO storeDTO = new StoreDTO();
	
	storeDTO.setStoreId(sdto.getStoreId());
	storeDTO.setStoreName(request.getParameter("storeName"));
	storeDTO.setCategory(request.getParameter("category"));
	storeDTO.setNotice(request.getParameter("notice"));
	storeDTO.setTel(request.getParameter("tel")); //썸네일은 나중에 추가
	storeDTO.setOpenTime(Integer.parseInt(request.getParameter("openTime")));
	storeDTO.setCloseTime(Integer.parseInt(request.getParameter("closeTime")));
	storeDTO.setBraketimeStart(Integer.parseInt(request.getParameter("brakeTimeStart")));
	storeDTO.setBraketimeEnd(Integer.parseInt(request.getParameter("brakeTimeEnd")));
	storeDTO.setStatus(sdto.getStatus());
	
	storeDTO.setUserId(memDTO.getId());
	
	if(storeDAO.editStoreInfo(storeDTO)){
		int storeId = sdto.getStoreId();
		String userId = memDTO.getId();
		session.setAttribute("storeDTO", storeDAO.getStoreInfo(storeId, userId));
		response.sendRedirect("StoreView.jsp?storeId=" + sdto.getStoreId());
	}else{
		response.sendRedirect("StoreMain.jsp");
	}
%>