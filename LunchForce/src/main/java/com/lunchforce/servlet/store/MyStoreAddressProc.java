package com.lunchforce.servlet.store;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDTO;
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;

/**
 * Servlet implementation class MyStoreAddressProc
 */
@WebServlet("/store/MyStoreAddressProc")
public class MyStoreAddressProc extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		//1. 주소 값 가져오기
		//넘어온 값이 없으면 메인화면으로 리턴
		if(request.getParameter("x") == null || request.getParameter("x") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		if(request.getParameter("y") == null || request.getParameter("y") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		if(request.getParameter("address") == null || request.getParameter("address") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		if(request.getParameter("detail") == null || request.getParameter("detail") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		//주소값을 storeDTO에 저장
		storeDTO.setAddress(request.getParameter("address")  + request.getParameter("detail")); 
		storeDTO.setAddressX(Double.parseDouble(request.getParameter("x"))); //x값
		storeDTO.setAddressY(Double.parseDouble(request.getParameter("y"))); //y값
		
		try {
			//2. 주소 값 변경하기
			StoreDAO storeDAO = StoreDAO.getInstance();
			storeDAO.newAddress(storeDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3. 가게정보 페이지로 이동
		response.sendRedirect("../store/MyStore");
		return;
	}
}
