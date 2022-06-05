package com.lunchforce.servlet.member;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;

import com.lunchforce.member.MemberDTO;
import com.lunchforce.store.MenuDAO;
import com.lunchforce.store.MenuDTO;
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;

/**
 * Servlet implementation class Store
 */
@WebServlet("/member/Store")
public class Store extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		StoreDAO storeDAO = StoreDAO.getInstance();

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
		
		//1. 넘어온 가게 id를 가져옴
		if(request.getParameter("storeId") == null) { //가게 아이디가 넘어오지 않은 경우
			response.sendRedirect("../member/Main"); //메인 페이지로 이동
			return;
		}
		
		int storeId = Integer.parseInt(request.getParameter("storeId"));
		try {
			//2. 가게 정보를 가져옴
			StoreDTO storeDTO = storeDAO.getStoreInfo(storeId);
			if(storeDTO == null) {
				//가게 정보를 가져오는 데 실패했으면 메인 페이지로 이동
				response.sendRedirect("../member/Main");
			}
			
			//3. 넘어온 가게 정보를 request에 저장
			request.setAttribute("storeDTO", storeDTO);
			
			//4. 가게의 메뉴 리스트 가져오기
			MenuDAO menuDAO = MenuDAO.getInstance(); //가게의 메뉴 리스트 가져오기위한 객체 
			ArrayList<MenuDTO> list = new ArrayList<MenuDTO>(); //메뉴 리스트를 가져오기 위한 List
			list = menuDAO.getMenuList(storeId); //메뉴 리스트를 가져옴
			
			//5. 가게의 메뉴 리스트를 request에 저장
			request.setAttribute("menuList", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//페이지 이동
		request.getRequestDispatcher("../member/Store.jsp").forward(request, response);
	}
}
