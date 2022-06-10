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
 * 주문의 상세정보를 가져오는 서블릿
 */
@WebServlet("/main/DetailOrder")
public class DetailOrder extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		//1. orderId 가져오기
		if(request.getParameter("orderId") == "" || request.getParameter("orderId") == null) {
			response.sendRedirect("../store/Main");
			return;
		}
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		
		try {
			//2. 주문정보 가져오기
			OrderDTO orderDTO = orderDAO.getStoreOrderDTO(orderId);
			request.setAttribute("orderDTO", orderDTO);
			//3. 가게정보 가져오기
			StoreDTO storeDTO = StoreDAO.getInstance().getStoreInfo(orderDTO.getStoreId());
			request.setAttribute("storeDTO", storeDTO);
			//4. 메뉴, 옵션 정보 가져오기
			ArrayList<ArrayList<Object>> list = orderDAO.getOrderList(orderId);
			request.setAttribute("orderList", list);
			//5. 세션에 orderDTO 저장
			session.setAttribute("orderDTO", orderDTO);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//6. 주문 상세보기 페이지로 이동
		request.getRequestDispatcher("../member/DetailOrder.jsp").forward(request, response);
		return;
	}
}
