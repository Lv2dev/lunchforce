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

/**
 * 주소를 업데이트하는 서블릿
 */
@WebServlet("/member/UpdateMyAddress")
public class UpdateMyAddress extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
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
		
		//1. 넘어온 값들을 가져옴
		
		//좌표의 x값
		Double x = Double.parseDouble(request.getParameter("x"));
		//좌표의 y값
		Double y = Double.parseDouble(request.getParameter("y"));
		//주소 텍스트
		String address = request.getParameter("address");
		
		MemberDAO memberDAO = MemberDAO.getInstance(); //주소 삽입을 위한 MemberDAO 객체
		
		try {
			//2. 주소 업데이트
			memberDAO.newAddress(x, y, address, memberDTO.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//3. 페이지 이동
		response.sendRedirect("../member/MyAddress");
	}
}
