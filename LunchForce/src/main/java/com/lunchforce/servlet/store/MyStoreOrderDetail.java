package com.lunchforce.servlet.store;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TryCatchFinally;

import com.lunchforce.member.MemberDAO;
import com.lunchforce.member.MemberDTO;
import com.lunchforce.order.OrderDAO;
import com.lunchforce.order.OrderDTO;
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;

/**
 * 가게에 들어온 주문의 세부정보를 가져오는 서블릿
 */
@WebServlet("/store/MyStoreOrderDetail")
public class MyStoreOrderDetail extends HttpServlet {
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
		
		//주문ID 가져오기
		if(request.getParameter("orderId") == null || request.getParameter("orderId") == "") {
			response.sendRedirect("../store/Main");
			return;
		}
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		
		try {
			//1. 주문ID 이용해서 주문 정보 가져오기
			OrderDAO orderDAO = OrderDAO.getInstance();
			OrderDTO orderDTO = orderDAO.getStoreOrderDTO(orderId);
			request.setAttribute("orderDTO", orderDTO);
			
			//2. 해당 주문의 메뉴정보와 옵션정보 가져오기
			ArrayList<ArrayList<Object>> list = orderDAO.getOrderList(orderId);
			request.setAttribute("orderList", list);
			
			//3. 가게정보 가져오기
			StoreDTO orderStoreDTO = StoreDAO.getInstance().getStoreInfo(orderDTO.getStoreId());
			request.setAttribute("storeDTO", orderStoreDTO);
			
			//4. 회원정보 가져오기
			MemberDTO orderMemberDTO = MemberDAO.getInstance().getMemberInfo(orderDTO.getUserId());
			request.setAttribute("memberDTO", orderMemberDTO);
			
			//5. 세션에 orderDTO 저장
			session.setAttribute("orderDTO", orderDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//페이지이동
		request.getRequestDispatcher("../store/MyStoreOrderDetail.jsp").forward(request, response);
		return;
	}
}
