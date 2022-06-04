package com.lunchforce.servlet.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDTO;

/**
 * 회원가입 서블릿
 */
@WebServlet("/member/Join")
public class Join extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		
		//회원가입 실패한 경우 안내하는 부분
		String notice = "회원가입";
		if(session.getAttribute("joinMessage") != null){ //회원가입 실패 메시지가 넘어온 경우
			notice = (String)session.getAttribute("joinMessage");
			if(notice.equals("false")) { //회원가입 실패 메시지 넘어왔을 때
				notice = "회원가입 실패! 다시 시도해 주세요 ㅠㅠ";
			}else if(notice.equals("id")) {
				notice = "아이디를 확인해 주세요";
			}
			session.removeAttribute("joinMessage"); //해당 세션 삭제
		}
		request.setAttribute("notice", notice);

		// 회원가입 페이지로 이동
		request.getRequestDispatcher("../member/Join.jsp").forward(request, response);
		return;
	}
}
