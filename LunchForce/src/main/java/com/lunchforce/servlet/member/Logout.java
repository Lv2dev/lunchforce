package com.lunchforce.servlet.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 로그아웃 서블릿
 */
@WebServlet("/member/Logout")
public class Logout extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		if(session.getAttribute("memberDTO") != null) { //memberDTO가 null이 아닌 경우
			session.removeAttribute("memberDTO"); //memberDTO session 삭제
		}
		
		resp.sendRedirect("../member/Main"); //메인화면으로 이동
		return;
	}
}
