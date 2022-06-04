package com.lunchforce.servlet.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.*;

/**
 * 메인 페이지와 연결된 서블릿
 */
@WebServlet("/member/Main")
public class Main extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		resp.setContentType("text/html;charset=UTF-8");

		req.setAttribute("login", 0); //0 : 로그인되지 않은 상태
		
		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 1) {// 사업자회원인 경우
				resp.sendRedirect("../store/Main");
				return;
			}else if(memberDTO.getType() == 2) { //일반 회원인 경우
				req.setAttribute("login", 1); //1 : 로그인 된 상태
			}
		}
		
		//Main.jsp로 이동
		req.getRequestDispatcher("../member/Main.jsp").forward(req, resp);
		return;
	}
}
