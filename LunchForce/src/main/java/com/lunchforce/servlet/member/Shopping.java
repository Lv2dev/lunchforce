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

/**
 * 장바구니와 연결된 서블릿
 */
@WebServlet("/member/Shopping")
public class Shopping extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

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
		
		try {
			//1. 장바구니 가져오기
			OrderDAO orderDAO = OrderDAO.getInstance(); //장바구니를 가져오는데 사용할 객체
			OrderDTO orderDTO = orderDAO.getOrder(memberDTO.getId(), 0); // 0: 장바구니 상태
			
			//2. 장바구니에 들어간 메뉴와 옵션들 가져오기
			ArrayList<ArrayList<Object>> list = orderDAO.getOrderList(memberDTO.getId(), 0); //0:장바구니 상태
			
			//3. request에 저장
			request.setAttribute("shoppingList", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//4. 페이지 이동
		request.getRequestDispatcher("../member/Shopping.jsp").forward(request, response);
	}
}
