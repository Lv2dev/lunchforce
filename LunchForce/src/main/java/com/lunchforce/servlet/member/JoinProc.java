package com.lunchforce.servlet.member;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.*;

/**
 * 회원가입을 처리하는 서블릿
 */
@WebServlet("/member/JoinProc")
public class JoinProc extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 0) { // 일반회원인 경우
				response.sendRedirect("../member/Main");
				return;
			} else if (memberDTO.getType() == 1) {// 사업자회원인 경우
				response.sendRedirect("../store/Main");
				return;
			}
		}
		
		
		//회원가입 처리
		
		//memberDAO 객체 가져오기
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDTO = new MemberDTO();
		
		String dateStr = request.getParameter("bDay");
		Date date = Date.valueOf(dateStr);
		
		//request 값 가져오기

		//memberDTO에 값 저장
		memberDTO.setName(request.getParameter("name"));
		memberDTO.setNickname(request.getParameter("nickname"));
		memberDTO.setId(request.getParameter("id"));
		memberDTO.setPw(request.getParameter("pw"));
		memberDTO.setEmail(request.getParameter("email"));
		memberDTO.setbDay(Date.valueOf(request.getParameter("bDay")));
		memberDTO.setTel(request.getParameter("tel"));
		memberDTO.setQuestion(request.getParameter("question"));
		memberDTO.setAnswer(request.getParameter("answer"));
		memberDTO.setType(Integer.parseInt(request.getParameter("type")));
		memberDTO.setGender(Integer.parseInt(request.getParameter("gender")));
		
		//회원가입 메서드 수행
		try {
			if(!memberDAO.idCheck(memberDTO.getId())) {
				session.setAttribute("loginMessage", "id"); //login 페이지로 넘길 메시지
				response.sendRedirect("../member/Join");
				return;
			}
			if(memberDAO.joinMember(memberDTO)) { //성공 시
				session.setAttribute("loginMessage", "join"); //login 페이지로 넘길 메시지
				response.sendRedirect("../member/Login");
				return;
			} else { //실패 시
				session.setAttribute("joinMessage", "false"); //join 페이지로 넘길 실패 메시지
				response.sendRedirect("../member/Join");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
