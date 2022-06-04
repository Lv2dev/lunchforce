package com.lunchforce.servlet.member;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.*;

@WebServlet("/member/Login")
public class Login extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 2) { // 일반회원인 경우
				response.sendRedirect("../member/Main");
				return;
			} else if (memberDTO.getType() == 1) {// 사업자회원인 경우
				response.sendRedirect("../store/Main");
				return;
			}
		}
		
		//로그인 된 상태가 아닌 경우
		String notice = "로그인";
		//loginMessage에 들어온 정보가 있으면 체크
		if(session.getAttribute("loginMessage") != null){ //loginMessage 세션이 null이 아니면
			String message = (String)session.getAttribute("loginMessage");
			if(message.equals("false")){ //로그인 실패 메시지가 넘어온 경우 "정보가 일치하지 않아요" 메시지 출력
				notice = "정보가 일치하지 않아요";
				session.removeAttribute("loginMessage"); //해당 세션 삭제
			}else if(message.equals("join")){ //회원가입 성공 후 넘어온 경우 "회원가입 성공" 메시지 출력
				notice = "회원가입 성공! 로그인 해주세요";
				session.removeAttribute("loginMessage"); //해당 세션 삭제
			}
		}
		request.setAttribute("notice", notice);
		
		//로그인 페이지로 이동
		request.getRequestDispatcher("../member/Login.jsp").forward(request, response);
		return;
	}
}
