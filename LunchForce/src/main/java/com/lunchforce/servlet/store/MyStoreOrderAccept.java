package com.lunchforce.servlet.store;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDTO;
import com.lunchforce.order.OrderDAO;
import com.lunchforce.order.OrderDTO;
import com.lunchforce.store.StoreDTO;

/**
 * 가게에 들어온 수락대기 상태의 주문을 수락하는 서블릿
 */
@WebServlet("/store/MyStoreOrderAccept")
public class MyStoreOrderAccept extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");

		request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
		
		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO = null;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 2) {// 일반 회원인 경우
				response.sendRedirect("../main/Main");
				return;
			}else if(memberDTO.getType() == 1) { // 사업자 회원인 경우
				request.setAttribute("login", 1); //1 : 로그인 된 상태
			}
		}
		
		//세션에 storeDTO가 없으면 main으로 리턴
		if(null == session.getAttribute("storeDTO")) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
		
		
		//세션의 가게정보와 유저정보가 일치하지 않으면 리턴
		if(!storeDTO.getUserId().equals(memberDTO.getId())) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		//주문ID 가져오기
		if(request.getParameter("orderId") == null || request.getParameter("orderId") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		
		//세션의 orderDTO와 비교
		if(session.getAttribute("orderDTO") == null) {
			response.sendRedirect("../store/Main");
			return;
		}
		if(((OrderDTO)session.getAttribute("orderDTO")).getOrderlistId() != orderId) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		//예상시간 받기
		if(request.getParameter("time") == null || request.getParameter("time") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		int time = Integer.parseInt(request.getParameter("time"));
		
		try {
			//1. 주문 수락 수행
			OrderDAO.getInstance().setStoreOrderState(storeDTO.getStoreId(), orderId, 1, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2. 수락 대기 주문 보는 페이지로 이동
		response.sendRedirect("../store/MyStoreWaitOrder");
		return;
	}
}
