package com.lunchforce.servlet.store;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDAO;
import com.lunchforce.member.MemberDTO;
import com.lunchforce.store.MenuDAO;
import com.lunchforce.store.MenuDTO;
import com.lunchforce.store.StoreDTO;

/**
 * 메뉴를 수정하는 서블릿
 */
@WebServlet("/store/EditMenuProc")
public class EditMenuProc extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");

		request.setAttribute("login", 0); // 0 : 로그인되지 않은 상태

		// 이미 로그인 된 상태면
		// 일반회원: <member/main.jsp> 으로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		MemberDTO memberDTO = null;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 2) {// 일반 회원인 경우
				response.sendRedirect("../main/Main");
				return;
			} else if (memberDTO.getType() == 1) { // 사업자 회원인 경우
				request.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}

		// 세션에 storeDTO가 없으면 main으로 리턴
		if (null == session.getAttribute("storeDTO")) {
			response.sendRedirect("../store/Main");
			return;
		}

		StoreDTO storeDTO = (StoreDTO) session.getAttribute("storeDTO");

		// 세션의 가게정보와 유저정보가 일치하지 않으면 리턴
		if (!storeDTO.getUserId().equals(memberDTO.getId())) {
			response.sendRedirect("../store/Main");
			return;
		}

		// 세션에서 메뉴정보 가져옴
		if (null == session.getAttribute("menuDTO")) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		// 세션의 메뉴정보와 들어온 메뉴정보 비교
		if (((MenuDTO)session.getAttribute("menuDTO")).getMenuId() != Integer.parseInt(request.getParameter("menuId"))) {
			response.sendRedirect("../store/Main");
			return;
		}
		
		MenuDAO menuDAO = MenuDAO.getInstance();
		//MenuDTO 갱신
		MenuDTO menuDTO = null;
		try {
			menuDTO = menuDAO.getMenuInfo(((MenuDTO)session.getAttribute("menuDTO")).getMenuId());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		menuDTO.setStoreId(storeDTO.getStoreId()); //가게 아이디

		// 메뉴명
		if (request.getParameter("menuName") == null || request.getParameter("menuName") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		menuDTO.setMenuId(Integer.parseInt(request.getParameter("menuName")));

		// 가격
		if (request.getParameter("price") == null || request.getParameter("price") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		menuDTO.setPrice(Integer.parseInt(request.getParameter("price")));

		// 메뉴설명
		if (request.getParameter("notice") == null || request.getParameter("notice") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		menuDTO.setNotice(request.getParameter("menuName"));

		// 메뉴사진
		if (request.getParameter("pic") == null || request.getParameter("pic") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		menuDTO.setNotice(request.getParameter("pic"));

		// 카테고리
		if (request.getParameterValues("category") == null) {
			response.sendRedirect("../store/Main");
			return;
		}
		String[] tempCategory = request.getParameterValues("category");
		int category = 0;
		for (int i = 0; i < tempCategory.length; i++) { //비트연산용으로 변환
			switch (tempCategory[i]) {
			case "한식": {
				category += Math.pow(2.0, 0);
				break;
			}
			case "중식": {
				category += Math.pow(2.0, 1);
				break;
			}
			case "일식": {
				category += Math.pow(2.0, 2);
				break;
			}
			case "양식": {
				category += Math.pow(2.0, 3);
				break;
			}
			case "패스트푸드": {
				category += Math.pow(2.0, 4);
				break;
			}
			case "치킨": {
				category += Math.pow(2.0, 5);
				break;
			}
			case "피자": {
				category += Math.pow(2.0, 6);
				break;
			}
			case "아시안": {
				category += Math.pow(2.0, 7);
				break;
			}
			case "카페/디저트": {
				category += Math.pow(2.0, 8);
				break;
			}
			case "분식": {
				category += Math.pow(2.0, 9);
				break;
			}
			default:
				break;
			}
		}
		menuDTO.setCategory(category);

		try {
			//1. 메뉴 수정 수행
			menuDAO.editMenu(menuDTO); 
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2. 가게 화면으로 이동
		response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
		return;
	}
}
