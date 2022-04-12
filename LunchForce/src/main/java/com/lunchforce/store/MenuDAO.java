package com.lunchforce.store;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lunchforce.dbconnect.DBConnecter;

public class MenuDAO {
	private static MenuDAO menuDAO = new MenuDAO();
	private DBConnecter dbConn = DBConnecter.getDBConnecter();

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private StringBuffer query;

	// 생성자
	private MenuDAO() {
	}

	// 메뉴추가
	public synchronized boolean addMenu(MenuDTO menuDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO menu ");
			query.append("VAEUS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, menuDTO.getMenuId());
			pstmt.setInt(2, menuDTO.getStoreId());
			pstmt.setString(3, menuDTO.getMenuName());
			pstmt.setInt(4, menuDTO.getPrice());
			pstmt.setString(5, menuDTO.getNotice());
			pstmt.setString(6, menuDTO.getPic());
			pstmt.setInt(7, menuDTO.getAllergy());
			pstmt.setInt(8, menuDTO.getFeeling());
			pstmt.setInt(9, menuDTO.getCondition());
			pstmt.setInt(10, menuDTO.getWeather());
			pstmt.setInt(11, menuDTO.getTemperature());
			pstmt.setInt(12, menuDTO.getDust());
			pstmt.setInt(13, menuDTO.getHumidity());
			pstmt.setInt(14, menuDTO.getFavor());
			pstmt.setInt(15, menuDTO.getCalorie());
			pstmt.setInt(16, menuDTO.getHealth());
			pstmt.setInt(17, menuDTO.getCategory());

			if (pstmt.executeUpdate() == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("memberDAO_memberJoin()_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 메뉴수정 - 메뉴아이디, 스토어아이디 빼고전부
	public synchronized boolean editMenu(MenuDTO menuDTO) throws SQLException {
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE menu ");
			query.append("SET menu_name = " + "'" + menuDTO.getMenuName() + "', ");
			query.append("price = " + "" + menuDTO.getPrice() + ", ");
			query.append("notice = " + "'" + menuDTO.getNotice() + "', ");
			query.append("pic = " + "'" + menuDTO.getPic() + "', ");
			query.append("allergy = " + "" + menuDTO.getAllergy() + ", ");
			query.append("feeling = " + "" + menuDTO.getFeeling() + ", ");
			query.append("condition = " + "" + menuDTO.getCondition() + ", ");
			query.append("weather = " + "" + menuDTO.getWeather() + ", ");
			query.append("temperature = " + "" + menuDTO.getTemperature() + ", ");
			query.append("dust = " + "" + menuDTO.getDust() + ", ");
			query.append("humidity = " + "" + menuDTO.getHumidity() + ", ");
			query.append("favor = " + "" + menuDTO.getFavor() + ", ");
			query.append("calorie = " + "" + menuDTO.getCalorie() + ", ");
			query.append("health = " + "" + menuDTO.getHealth() + ", ");
			query.append("category = " + "" + menuDTO.getCategory() + ", ");
			query.append("WHERE menu_id = " + "" + menuDTO.getMenuId() + "");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("MenuDAO_editMenu_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//메뉴 삭제
	public synchronized boolean delMenu(MenuDTO menuDTO) throws SQLException{
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM menu ");
			query.append("WHERE menu_id = '" + menuDTO.getMenuId() + "'");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("MenuDAO_메뉴삭제_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 메뉴들 ArrayList로 가져오기
	public synchronized ArrayList<MenuDTO> getAllMenu(int storeId) throws SQLException {
		ArrayList<MenuDTO> arr = new ArrayList<MenuDTO>();
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT menu_id, menu_name FROM menu");
			query.append("WHERE store_id = '" + storeId + "' ORDER BY menu_id");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				MenuDTO menuDTO = new MenuDTO();
				menuDTO.setMenuId(rs.getInt("menu_id"));
				menuDTO.setMenuName(rs.getString("menu_name"));
				arr.add(menuDTO);
				cnt++;
			}

			return arr;
		} catch (Exception e) {
			System.out.println("MenuDAO_메뉴목록 가져오기 에러");
			return null;
		} finally {
			disconnectStmt();
		}
	}

	// 인스턴스 getter
	public static MenuDAO getInstance() {
		if (menuDAO == null) {
			menuDAO = new MenuDAO();
		}
		return menuDAO;
	}

	// pstmt일 경우 연결 해제
	public void disconnectPstmt() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		pstmt.close();
		conn.close();
	}

	// stmt일 경우 연결 해제
	public void disconnectStmt() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		stmt.close();
		conn.close();
	}
}
