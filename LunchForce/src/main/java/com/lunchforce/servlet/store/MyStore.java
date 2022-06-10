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
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;

/**
 * 선택한 내 가게의 세부 정보를 볼 수 있는 페이지
 */
@WebServlet("/main/MyStore")
public class MyStore extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
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
		
		//가게의 userId와 세션에 저장된 userId가 다르면 main으로 되돌림
		
		//1. 넘어온 가게 id 값 가져오기
		if(request.getParameter("storeId") == null || request.getParameter("storeId") == "") {
			response.sendRedirect("../store/Main"); //넘어온 값 없으면 메인화면으로
			return;
		}
		int storeId = Integer.parseInt(request.getParameter("storeId"));
		
		//2. 가게 정보 가져오기
		StoreDAO storeDAO = StoreDAO.getInstance();
		StoreDTO storeDTO = null;
		try {
			storeDTO = storeDAO.getStoreInfo(storeId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.setAttribute("storeDTO", storeDTO); //가게 정보를 세션에 저장
		
		//3. 가게 정보의 userId와 세션의 userId 비교해서 다르면 Main화면으로
		if(!storeDTO.getUserId().equals(memberDTO.getId())){
			response.sendRedirect("../store/Main");
			return;
		}
		
		//4. 가게의 메뉴 리스트 가져오기
		MenuDAO menuDAO = MenuDAO.getInstance(); //가게의 메뉴 리스트 가져오기위한 객체 
		ArrayList<MenuDTO> menuList = new ArrayList<MenuDTO>(); //메뉴 리스트를 가져오기 위한 List
		try {
			menuList = menuDAO.getMenuList(storeId);//메뉴 리스트를 가져옴
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		//4. 가게 정보와 메뉴 정보를 가지고 MyStore.jsp로 이동
		request.setAttribute("storeDTO", storeDTO);
		request.setAttribute("menuList", menuList);
		request.getRequestDispatcher("../store/MyStore.jsp");
		return;
		
	};
}
