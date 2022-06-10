package com.lunchforce.servlet.store;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * 가게의 메뉴정보 보는 페이지
 */
@WebServlet("/member/MyStoreMenu")
public class MyStoreMenu extends HttpServlet {
	@Override
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
		
		//1. 넘어온 메뉴id 가져오기
		if(request.getParameter("menuId") == null || request.getParameter("menuId") == "") {
			//넘어온 menuId가 없으면 메인화면으로 이동
			response.sendRedirect("../store/Main");
			return;
		}
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		
		//2. 메뉴의 정보 가져오기
		MenuDAO menuDAO = MenuDAO.getInstance();
		MenuDTO menuDTO = null;
		try {
			menuDTO = menuDAO.getMenuInfo(menuId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//메뉴의 가게정보와 세션의 가게정보가 일치하지 않으면 메인으로 리턴
		if(menuDTO.getStoreId() != storeDTO.getStoreId()) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		//메뉴 정보를 세션에 저장
		session.setAttribute("menuDTO", menuDTO);
		
		//3. 해당 메뉴의 옵션 정보 가져오기
		MenuOptionDAO moDAO = MenuOptionDAO.getInstance();
		ArrayList<MenuOptionDTO> optionList = null;
		try {
			optionList = moDAO.getOptionList(menuId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//4. 옵션 정보와 메뉴 정보 담아서 이동
		request.setAttribute("optionList", optionList);
		request.setAttribute("menuDTO", menuDTO);
		request.getRequestDispatcher("../store/MyStoreMenu.jsp").forward(request, response);
		return;
	}
}
