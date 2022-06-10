package com.lunchforce.servlet.store;

import java.io.IOException;
import java.util.ArrayList;

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
 * 수락된 주문 내역을 가져오는 서블릿
 */
@WebServlet("/store/MyStoreAcceptOrder")
public class MyStoreAcceptOrder extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");

		request.setAttribute("login", 0); // 0 : 로그인되지 않은 상태

		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO = null;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 2) {// 일반 회원인 경우
				response.sendRedirect("../main/Main");
				return;
			} else if (memberDTO.getType() == 1) { // 사업자 회원인 경우
				request.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}

		// 세션에 storeDTO가 없으면 main으로 리턴
		if (null == session.getAttribute("storeDTO")) {
			response.sendRedirect("../store/Main");
			return;
		}

		StoreDTO storeDTO = (StoreDTO) session.getAttribute("storeDTO");

		// 세션의 가게정보와 유저정보가 일치하지 않으면 리턴
		if (!storeDTO.getUserId().equals(memberDTO.getId())) {
			response.sendRedirect("../store/Main");
			return;
		}

		// page가 넘어온 경우 page저장
		int page = 1;
		if (request.getParameter("page") != null && request.getParameter("page") != "") {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int pageCount = 10; // 한 페이지에 표시되는 주문내역의 수

		try {
			// 대기중인 주문정보 가져오기
			OrderDAO orderDAO = OrderDAO.getInstance();
			// 1. 수락 대기중인 주문의 양을 가져옴
			int resultCount = orderDAO.getStoreOrderDTOListCount(storeDTO.getStoreId(), 1);
			// 2. 수락 대기중인 주문의 페이징된 리스트를 가져옴
			ArrayList<OrderDTO> orderList = orderDAO.getStoreOrderDTOListpaging(storeDTO.getStoreId(), 1, page,
					pageCount);
			// 3. request에 넣어줌
			int pages = (int) Math.ceil((double) resultCount / 10);

			int end = (int) (Math.ceil((double) page / 10) * 10);
			if (end > pages) {
				end = pages;
			}
			int start = 1;
			if (end > 10) {
				start = end - 9;
			}
			request.setAttribute("orderList", orderList);
			request.setAttribute("page", page); // 현재 페이지
			request.setAttribute("pages", pages); // 전체 페이지 수
			request.setAttribute("end", end); // 마지막 페이지
			request.setAttribute("start", start); // 시작 페이지
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 페이지 이동
		request.getRequestDispatcher("../store/MyStoreAcceptOrder.jsp").forward(request, response);
		return;
	}
}
