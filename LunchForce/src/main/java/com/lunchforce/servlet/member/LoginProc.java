package com.lunchforce.servlet.member;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.*;

@WebServlet("/member/LoginProc")
public class LoginProc extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
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

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		MemberDAO memberDAO = MemberDAO.getInstance();

		// 아이디와 비밀번호가 맞지 않으면 다시 Login.jsp로. 보낼 때 세션으로 로그인 실패 값 전달
		// 값이 맞으면 세션에 MemberDTO 저장 후 main.jsp로
		try {
			if (!memberDAO.memberLogin(id, pw)) { // 값이 맞지 않으면 - member/Login으로 다시 이동
				session.setAttribute("loginMessage", "false"); //로그인 메시지 전달
				response.sendRedirect("../member/Login");
				return;
			} else { // 값이 맞으면 로그인 성공 
				memberDTO = memberDAO.getMemberInfo(id);
				session.setAttribute("memberDTO", memberDTO); //세션에 memberDTO 저장
				if(memberDTO.getType() == 2) { //일반 회원인 경우
					response.sendRedirect("../member/Main"); //member/main으로 이동
					return;
				}else if(memberDTO.getType() == 1) { //사업자회원인 경우
					response.sendRedirect("../store/Main"); //member/main으로 이동
					return;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
