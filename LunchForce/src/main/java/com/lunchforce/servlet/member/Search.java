package com.lunchforce.servlet.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lunchforce.member.*;
import com.lunchforce.store.*;

/**
 * 검색 기능 서블릿
 */
@WebServlet("/member/Search")
public class Search extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		StoreDAO storeDAO = StoreDAO.getInstance();

		// 이미 로그인 된 상태면
		// 일반회원: 탐색할 페이지로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		request.setAttribute("login", 0); //0 : 로그인되지 않은 상태
		MemberDTO memberDTO;
		if (session.getAttribute("memberDTO") != null) {
			memberDTO = (MemberDTO) session.getAttribute("memberDTO");
			if (memberDTO.getType() == 1) {// 사업자회원인 경우
				response.sendRedirect("../store/Main");
				return;
			} else if (memberDTO.getType() == 2) { // 일반 회원인 경우
				request.setAttribute("login", 1); // 1 : 로그인 된 상태
			}
		}
		
		//page가 넘어온 경우 page저장
		int page = 1;
		if(request.getParameter("page") != null && request.getParameter("page") != "") {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//넘어온 keyword를 저장
		String keyword = "";
		if(request.getParameter("keyword") != null) {
			keyword = request.getParameter("keyword");
		}
		
		int pageCount = 10; //한 페이지에 표시되는 가게의 수
		
		ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
		try {
			//1. 총 검색결과 갯수를 가져옴
			int resultCount = storeDAO.getSearchCount(keyword);
			
			//2. 현재 페이지에 해당하는 글 목록을 가져옴
			list = storeDAO.getSearchList(keyword, page, pageCount);
			
			//3. request에 넣어줌
			int pages = (int) Math.ceil((double) resultCount / 10);
			int end = (int) (Math.ceil((double) page/10) * 10);
			if(end > pages) {
				end = pages;
			}
			int start = 1;
			if(end > 10) {
				start = end - 9;
			}
			request.setAttribute("searchList", list);
			request.setAttribute("page", page); //현재 페이지
			request.setAttribute("pages", pages); //전체 페이지 수
			request.setAttribute("end", end); //마지막 페이지
			request.setAttribute("start", start); //시작 페이지
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//4. 이동
		request.getRequestDispatcher("../member/Search.jsp").forward(request, response);
		return;
	}

}
