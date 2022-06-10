package com.lunchforce.servlet.store;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDTO;
import com.lunchforce.store.StoreDTO;

/**
 * 가게의 세부 정보를 보고 수정하는 페이지
 */
@WebServlet("/store/MyStoreDetail")
public class MyStoreDetail extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");

		request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
		
		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO = null;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 2) {// 일반 회원인 경우
				response.sendRedirect("../main/Main");
				return;
			}else if(memberDTO.getType() == 1) { // 사업자 회원인 경우
				request.setAttribute("login", 1); //1 : 로그인 된 상태
			}
		}
		
		//세션에 storeDTO가 없으면 main으로 리턴
		if(null == session.getAttribute("storeDTO")) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
		
		
		//세션의 가게정보와 유저정보가 일치하지 않으면 리턴
		if(!storeDTO.getUserId().equals(memberDTO.getId())) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		//가게의 세부정보를 request에 저장
		request.setAttribute("storeDTO", storeDTO);
		
		//페이지 이동
		request.getRequestDispatcher("../store/MyStoreDetail.jsp").forward(request, response);
	}
}
