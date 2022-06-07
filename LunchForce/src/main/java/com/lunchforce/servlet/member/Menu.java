package com.lunchforce.servlet.member;

import java.io.IOException;
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
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;

/**
 * 메뉴 페이지와 연결된 서블릿
 */
@WebServlet("/member/Menu")
public class Menu extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		MenuDAO menuDAO = MenuDAO.getInstance();

		// 이미 로그인 된 상태면
		// 일반회원: 탐색할 페이지로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
		MemberDTO memberDTO;
		if (session.getAttribute("memberDTO") != null) { //로그인 된 상태인 경우
			memberDTO = (MemberDTO) session.getAttribute("memberDTO"); //세션에서 memberDTO 가져옴
			if (memberDTO.getType() == 1) {// 사업자회원인 경우
				response.sendRedirect("../store/Main");
				return;
			} else if (memberDTO.getType() == 2) { // 일반 회원인 경우
				request.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}
		
		StoreDTO storeDTO = (StoreDTO)session.getAttribute("storeDTO");
		
		//1. 넘어온 메뉴id를 가져옴
		if(request.getParameter("menuId") == null) { //넘어온 메뉴id가 없으면
			response.sendRedirect("../member/Store?storeId=" + storeDTO.getStoreId());  //가게 페이지로 이동
			return;
		}
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		
		try {
			//2. 메뉴의 정보를 가져옴
			MenuDTO menuDTO = menuDAO.getMenuInfo(menuId);
			
			//3. 2가 실패한 경우 가게 화면으로 이동
			if(menuDTO == null) {
				response.sendRedirect("../member/Store?storeId=" + storeDTO.getStoreId());  //가게 페이지로 이동
				return;
			}
			
			//4. 메뉴의 정보를 request에 저장
			request.setAttribute("menuDTO", menuDTO);
			
			//5. 메뉴의 옵션 정보를 List로 가져오기
			MenuOptionDAO moDAO = MenuOptionDAO.getInstance(); //옵션들을 가져오기 위한 객체
			ArrayList<MenuOptionDTO> list = moDAO.getOptionList(menuId);
			
			//6. request에 저장
			request.setAttribute("optionList", list);
			request.setAttribute("storeName", storeDTO.getStoreName());
			
			if(list == null) {
				request.setAttribute("optionListSize", 0);
			}else {
				request.setAttribute("optionListSize", list.size()); //list의 크기
			}
			
			//세션에 menuDTO 저장
			session.setAttribute("menuDTO", menuDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		//페이지 이동
		request.getRequestDispatcher("../member/Menu.jsp").forward(request, response);
		return;
	}
}
