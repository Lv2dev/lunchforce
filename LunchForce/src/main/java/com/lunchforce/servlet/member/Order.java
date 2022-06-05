package com.lunchforce.servlet.member;

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
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;

/**
 * 회원의 주문 내역을 가져오는 서블릿
 */
@WebServlet("/member/Order")
public class Order extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		OrderDAO orderDAO = OrderDAO.getInstance();

		MemberDTO memberDTO = new MemberDTO();
		// 로그인 된 상태가 아닌 경우 로그인 페이지로 이동
		if (null == session.getAttribute("memberDTO")) {
			response.sendRedirect("../member/Login");
			return;
		} else {
			// 로그인 된 상태인 경우
			request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
			memberDTO = (MemberDTO) session.getAttribute("memberDTO"); // 세션에서 memberDTO 가져오기
			if (memberDTO.getType() == 1) {// 사업자회원인 경우
				response.sendRedirect("../store/Main");
				return;
			} else if (memberDTO.getType() == 2) { // 일반 회원인 경우
				request.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}

		// page가 넘어온 경우 page저장
		int page = 1;
		if (request.getParameter("page") != null && request.getParameter("page") != "") {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int pageCount = 10; // 한 페이지에 표시되는 주문내역의 수

		ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();
		try {
			// 1. 총 주문 내역의 갯수를 가져옴
			int resultCount = orderDAO.getOrderCount(memberDTO.getId());

			// 2. 현재 페이지에 해당하는 주문 목록을 가져옴
			list = orderDAO.getPagingOrderlist(memberDTO.getId(), page, pageCount);

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
			request.setAttribute("orderList", list);
			request.setAttribute("page", page); // 현재 페이지
			request.setAttribute("pages", pages); // 전체 페이지 수
			request.setAttribute("end", end); // 마지막 페이지
			request.setAttribute("start", start); // 시작 페이지
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4. 이동
		request.getRequestDispatcher("../member/Order.jsp").forward(request, response);
		return;
	}
}
