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
import com.lunchforce.store.StoreDAO;
import com.lunchforce.store.StoreDTO;


/**
 * Servlet implementation class CategorySearch
 */
@WebServlet("/member/CategorySearch")
public class CategorySearch extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		StoreDAO storeDAO = StoreDAO.getInstance();

		// 이미 로그인 된 상태면
		// 일반회원: 탐색할 페이지로 보냄
		// 사업자회원: <store/main.jsp> 으로 보냄
		request.setAttribute("login", 0); // 0 : 로그인되지 않은 상태
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

		// page가 넘어온 경우 page저장
		int page = 1;
		if (request.getParameter("page") != null && request.getParameter("page") != "") {
			page = Integer.parseInt(request.getParameter("page"));
		}

		// 넘어온 카테고리를 저장
		int category = 99;
		if (request.getParameter("category") != null && request.getParameter("category") != "") {
			category = Integer.parseInt(request.getParameter("category"));
		}

		// 넘어온 카테고리가 없으면 메인으로이동
		if (category == 99) {
			response.sendRedirect("../member/Main");
			return;
		}

		int pageCount = 10; // 한 페이지에 표시되는 가게의 수

		// 카테고리별로 가게 검색하기
		ArrayList<StoreDTO> list = null;
		try {
			// 1. 총 검색결과 갯수를 가져옴
			int resultCount = storeDAO.searchCategoryStoreCount(category);

			// 2. 현재 페이지에 해당하는 글 목록을 가져옴
			list = storeDAO.searchCategoryStorePaging(category, page, pageCount);

			// 3. request에 넣어줌
			int pages = (int) Math.ceil((double) resultCount / 10);
			int end = (int) (Math.ceil((double) page / 10) * 10);
			if (end > pages) {
				end = pages;
			}
			int start = 1;
			if (end > 10) {
				start = end - 9;
			}
			System.out.println(list.size());
			request.setAttribute("searchList", list);
			request.setAttribute("page", page); // 현재 페이지
			request.setAttribute("pages", pages); // 전체 페이지 수
			request.setAttribute("end", end); // 마지막 페이지
			request.setAttribute("start", start); // 시작 페이지
			request.setAttribute("category", category); // 카테고리

			String str = ""; // 어떤 카테고리를 선택했는지 보여주기 위한 string
			switch (category) {
			case 1: {
				str = "한식";
				break;
			}
			case 2: {
				str = "중식";
				break;
			}
			case 4: {
				str = "일식";
				break;
			}
			case 8: {
				str = "양식";
				break;
			}
			case 16: {
				str = "패스트푸드";
				break;
			}
			case 32: {
				str = "치킨";
				break;
			}
			case 64: {
				str = "피자";
				break;
			}
			case 128: {
				str = "아시안";
				break;
			}
			case 256: {
				str = "카페/디저트";
				break;
			}
			case 512: {
				str = "분식";
				break;
			}
			default:
				break;
			}

			request.setAttribute("categoryStr", str); // 카테고리 텍스트
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 4. 이동
		request.getRequestDispatcher("../member/CategorySearch.jsp").forward(request, response);
		return;
	}
}
