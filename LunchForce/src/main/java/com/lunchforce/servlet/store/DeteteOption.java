package com.lunchforce.servlet.store;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDTO;
import com.lunchforce.store.MenuDAO;
import com.lunchforce.store.MenuDTO;
import com.lunchforce.store.MenuOptionDAO;
import com.lunchforce.store.MenuOptionDTO;
import com.lunchforce.store.StoreDTO;

/**
 * 메뉴의 옵션을 삭제하는 서블릿
 */
@WebServlet("/store/DeteteOption")
public class DeteteOption extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		//세션에서 메뉴정보 가져옴
		if(null == session.getAttribute("menuDTO")) {
			response.sendRedirect("../store/Main");
			return;
		}
		MenuDTO menuDTO = (MenuDTO)session.getAttribute("menuDTO");
		MenuDAO menuDAO = MenuDAO.getInstance();
		
		//넘어온 메뉴아이디가 없으면 메인화면으로 리턴
		if(request.getParameter("menuId") == null || request.getParameter("menuId") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		
		int reqMenuID = Integer.parseInt(request.getParameter("menuId"));
		
		//넘어온 옵션아이디가 없으면 메인화면으로 리턴
		if(request.getParameter("optionId") == null || request.getParameter("optionId") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		int optionId = Integer.parseInt(request.getParameter("optionId"));
		
		try {
			//1. 옵션 삭제 수행
			MenuOptionDAO moDAO = MenuOptionDAO.getInstance();
			moDAO.delOption(optionId, menuDTO.getMenuId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//2. 가게 정보 화면으로 이동
		request.getRequestDispatcher("../store/MyStore?storeId=" + storeDTO.getStoreId());
		return;
	}
}
