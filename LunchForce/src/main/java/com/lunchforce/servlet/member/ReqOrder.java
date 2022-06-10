package com.lunchforce.servlet.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDAO;
import com.lunchforce.member.MemberDTO;
import com.lunchforce.order.OrderDAO;

/**
 * 주문을 요청하는 서블릿
 */
@WebServlet("/member/ReqOrder")
public class ReqOrder extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		try {
			//1. 주문 요청을 수행 - 상태를 1로 변경
			orderDAO.setState(memberDTO.getId(), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2. 주문내역 페이지로 이동
		response.sendRedirect("../member/Order");
		return;
	}
}
