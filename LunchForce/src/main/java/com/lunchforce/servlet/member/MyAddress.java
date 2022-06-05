package com.lunchforce.servlet.member;

import java.io.IOException;
import java.sql.SQLException;

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
 * 주소관리 페이지와 연결된 서블릿
 */
@WebServlet("/member/MyAddress")
public class MyAddress extends HttpServlet {
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
			// 로그인 된 상태인 경우 로그인 정보를 전달
			request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
			memberDTO = (MemberDTO) session.getAttribute("memberDTO"); // 세션에서 memberDTO 가져오기
			if (memberDTO.getType() == 1) {// 사업자회원인 경우
				response.sendRedirect("../store/Main");
				return;
			} else if (memberDTO.getType() == 2) { // 일반 회원인 경우
				request.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		String notice = "주소를 설정해 주세요";
		try {
			//1. 이미 등록된 주소가 있으면 현재 등록된 주소를 보여줌
			if(memberDAO.isAddressAdded(memberDTO.getId())) {
				notice = memberDAO.getAddressInfo(memberDTO.getId()).getAddress();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2. request에 저장
		request.setAttribute("notice", notice);
		
		//3. 이동
		request.getRequestDispatcher("../member/MyAddress.jsp").forward(request, response);
		return;
	}
}
