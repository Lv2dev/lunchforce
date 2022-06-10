package com.lunchforce.servlet.store;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
 * 내 가게의 세부정보를 업데이트하는 서블릿
 */
@WebServlet("/store/MyStoreUpdate")
public class MyStoreUpdate extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		// 1. 넘어온 정보들을 체크 및 가져오기

		// 가게명
		if (request.getParameter("storeName") == null || request.getParameter("storeName") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String storeName = request.getParameter("storeName");
		// 카테고리
		if (request.getParameterValues("category") == null) {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String[] tempCategory = request.getParameterValues("category");
		int category = 0;
		for (int i = 0; i < tempCategory.length; i++) {
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
		// 공지사항
		if (request.getParameter("notice") == null || request.getParameter("notice") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String notice = request.getParameter("notice");
		// 전화번호
		if (request.getParameter("tel") == null || request.getParameter("tel") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String tel = request.getParameter("tel");
		// 가게이미지
		if (request.getParameter("thumb") == null || request.getParameter("thumb") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String thumb = request.getParameter("thumb");
		// 오픈시간
		if (request.getParameter("openTime") == null || request.getParameter("openTime") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		// 변환해서 저장
		String tempOpenTime = request.getParameter("openTime");
		Timestamp ts = Timestamp.valueOf(tempOpenTime);
		String hour = new SimpleDateFormat("HH").format(ts); // 시
		String min = new SimpleDateFormat("mm").format(ts); // 분
		int openTime = Integer.parseInt(hour) * 60 + Integer.parseInt(min); // 총 몇분인지 구함

		// 폐점시간
		if (request.getParameter("closeTime") == null || request.getParameter("closeTime") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		// 변환해서 저장
		String tempCloseTime = request.getParameter("openTime");
		ts = Timestamp.valueOf(tempCloseTime);
		hour = new SimpleDateFormat("HH").format(ts); // 시
		min = new SimpleDateFormat("mm").format(ts); // 분
		int closeTime = Integer.parseInt(hour) * 60 + Integer.parseInt(min); // 총 몇분인지 구함
		// 쉬는 날
		if (request.getParameterValues("restDay") == null) {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String[] tempRestDay = request.getParameterValues("restDay");
		int restDay = 0;
		for (int i = 0; i < tempRestDay.length; i++) {
			switch (tempRestDay[i]) {
			case "월": {
				restDay += Math.pow(2.0, 0);
				break;
			}
			case "화": {
				restDay += Math.pow(2.0, 1);
				break;
			}
			case "수": {
				restDay += Math.pow(2.0, 2);
				break;
			}
			case "목": {
				restDay += Math.pow(2.0, 3);
				break;
			}
			case "금": {
				restDay += Math.pow(2.0, 4);
				break;
			}
			case "토": {
				restDay += Math.pow(2.0, 5);
				break;
			}
			case "일": {
				restDay += Math.pow(2.0, 6);
				break;
			}
			default:
				break;
			}
		}
		//쉬는 시간 시작
		if (request.getParameter("braketimeStart") == null || request.getParameter("braketimeStart") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String tempBraketimeStart = request.getParameter("braketimeStart");
		ts = Timestamp.valueOf(tempCloseTime);
		hour = new SimpleDateFormat("HH").format(ts); // 시
		min = new SimpleDateFormat("mm").format(ts); // 분
		int breakeTimeStart = Integer.parseInt(hour) * 60 + Integer.parseInt(min); // 총 몇분인지 구함
		
		//쉬는시간 끝
		if (request.getParameter("braketimeEnd") == null || request.getParameter("braketimeEnd") == "") {
			response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
			return;
		}
		String tempBraketimeEnd = request.getParameter("braketimeEnd");
		ts = Timestamp.valueOf(tempCloseTime);
		hour = new SimpleDateFormat("HH").format(ts); // 시
		min = new SimpleDateFormat("mm").format(ts); // 분
		int braketimeEnd = Integer.parseInt(hour) * 60 + Integer.parseInt(min); // 총 몇분인지 구함
		
		//DTO에 저장
		StoreDAO storeDAO = StoreDAO.getInstance();
		//현재 가게 정보 가져오기
		StoreDTO tempStoreDTO = null;
		try {
			tempStoreDTO = storeDAO.getStoreInfo(storeDTO.getStoreId());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tempStoreDTO.setStoreName(storeName); //가게명
		tempStoreDTO.setCategory(category); //카테고리
		tempStoreDTO.setNotice(notice); //공지사항
		tempStoreDTO.setTel(tel); //전화번호
		tempStoreDTO.setThumb(thumb); //가게이미지
		tempStoreDTO.setOpenTime(openTime); //오픈
		tempStoreDTO.setCloseTime(closeTime); //폐점
		tempStoreDTO.setRestDay(restDay); //쉬는날
		tempStoreDTO.setBraketimeStart(breakeTimeStart); //쉬는시간
		tempStoreDTO.setBraketimeEnd(braketimeEnd); //쉬는시간끝
		
		try { 
			//2. 가게 정보 업데이트
			storeDAO.editStoreInfo(tempStoreDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//가게정보 페이지로 이동
		response.sendRedirect("../store/MyStore?storeId=" + storeDTO.getStoreId());
		return;
	}
}
