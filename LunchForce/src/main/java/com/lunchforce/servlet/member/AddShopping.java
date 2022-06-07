package com.lunchforce.servlet.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.MemberDTO;
import com.lunchforce.order.OrderDAO;
import com.lunchforce.store.MenuDAO;
import com.lunchforce.store.MenuDTO;
import com.lunchforce.store.StoreDTO;

/**
 * 장바구니에 추가하는 서블릿
 */
@WebServlet("/member/AddShopping")
public class AddShopping extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		OrderDAO orderDAO = OrderDAO.getInstance();

		// 이미 로그인 된 상태면
		// 일반회원: 탐색할 페이지로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
		MemberDTO memberDTO = new MemberDTO();
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
		MenuDTO menuDTO = (MenuDTO)session.getAttribute("menuDTO");
		
		try {
			//option이 넘어온 경우
			if(request.getParameter("option") != null) {
				//1. 체크된 옵션들 가져옴
				String[] tempOption = request.getParameterValues("option"); 
				
				//2. 들어온 옵션들을 int형으로 변환
				int[] option = new int[tempOption.length];
				for(int i = 0; i < tempOption.length; i++) { //들어온 체크된 옵션들을 integer로 변환해서 저장
					option[i] = Integer.parseInt(tempOption[i]); 
				}
				
				//3. 장바구니에 추가
				orderDAO.addShopping(memberDTO.getId(), storeDTO.getStoreId(), menuDTO.getMenuId(), option); 
				
				//4. 페이지 이동
				response.sendRedirect("../member/Store?storeId=" + storeDTO.getStoreId());
				return;
			}else {
				//option이 안들어온 경우
				//1. 옵션 없이 메뉴만 장바구니에 추가 수행
				orderDAO.addShopping(memberDTO.getId(), storeDTO.getStoreId(), menuDTO.getMenuId(), new int[0]); 
				
				//2. 페이지 이동
				response.sendRedirect("../member/Store?storeId=" + storeDTO.getStoreId());
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
