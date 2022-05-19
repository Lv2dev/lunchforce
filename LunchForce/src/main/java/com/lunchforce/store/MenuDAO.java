package com.lunchforce.store;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lunchforce.dbconnect.DBConnecter;
import com.lunchforce.dbconnect.JDBConnect;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.util.LinkedHashMap;

public class MenuDAO extends JDBConnect{
	private static MenuDAO menuDAO = new MenuDAO();

	// 생성자
	private MenuDAO() {
	}

	// 메뉴추가
	public synchronized boolean addMenu(MenuDTO menuDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			
			query.append("select * from menu where store_id = " + menuDTO.getStoreId());
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int row = 0;
			while(rs.next()) {
				row++;
			}
			
			query = new StringBuffer();
			
			query.append("INSERT INTO menu (`store_id`,`menu_name`,`price`,`notice`,`pic`,`allergy`,`feeling`,`condition`,`weather`,`temperature`,`dust`,`humidity`,`favor`,`calorie`,`health`,`category`,`menu_number`, `nutrient`) ");
			query.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, menuDTO.getStoreId());
			pstmt.setString(2, menuDTO.getMenuName());
			pstmt.setInt(3, menuDTO.getPrice());
			pstmt.setString(4, menuDTO.getNotice());
			pstmt.setString(5, menuDTO.getPic());
			pstmt.setInt(6, menuDTO.getAllergy());
			pstmt.setInt(7, menuDTO.getFeeling());
			pstmt.setInt(8, menuDTO.getCondition());
			pstmt.setInt(9, menuDTO.getWeather());
			pstmt.setInt(10, menuDTO.getTemperature());
			pstmt.setInt(11, menuDTO.getDust());
			pstmt.setInt(12, menuDTO.getHumidity());
			pstmt.setInt(13, menuDTO.getFavor());
			pstmt.setInt(14, menuDTO.getCalorie());
			pstmt.setInt(15, menuDTO.getHealth());
			pstmt.setInt(16, menuDTO.getCategory());
			pstmt.setInt(17, row+1); //menu_number
			pstmt.setInt(18, menuDTO.getNutrient());

			if (pstmt.executeUpdate() == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("메뉴추가에러");
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
			query.append("menu_number = " + menuDTO.getMenuNumber() + " ");
			query.append("nutrient = " + menuDTO.getNutrient() + " ");
			query.append("WHERE menu_id = " + "" + menuDTO.getMenuId() + "");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MenuDAO_editMenu_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//메뉴 삭제
	public synchronized boolean delMenu(int menuId) throws SQLException{
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM menu ");
			query.append("WHERE menu_id = " + menuId + "");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MenuDAO_메뉴삭제_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 메뉴들 LinkedHashMap로 가져오기
	public synchronized LinkedHashMap<Integer, String> getAllMenu(int storeId) throws SQLException {
		LinkedHashMap<Integer, String> hash = new LinkedHashMap<Integer, String>();
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT menu_id, menu_name FROM menu ");
			query.append("WHERE store_id = " + storeId + " ORDER BY menu_number");

			rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				hash.put(rs.getInt("menu_id"), rs.getString("menu_name"));
			}
			return hash;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MenuDAO_메뉴목록 가져오기 에러");
			return null;
		} finally {
			disconnectStmt();
		}
	}
	
	
	//가게아이디와 메뉴아이디 넣으면 메뉴정보 가져오기
	public synchronized MenuDTO getStoreInfo(int storeId, int menuId) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT * FROM menu ");
			query.append("WHERE menu_id = " + menuId + " and store_id = " + storeId);

			rs = stmt.executeQuery(query.toString());

			MenuDTO menuDTO = new MenuDTO();

			int cnt = 0;
			while (rs.next()) {
				menuDTO.setStoreId(storeId);
				menuDTO.setMenuId(menuId);
				menuDTO.setMenuName(rs.getString("menu_name"));
				menuDTO.setPrice(rs.getInt("price"));
				menuDTO.setNotice(rs.getString("notice"));
				menuDTO.setPic(rs.getString("pic"));
				menuDTO.setAllergy(rs.getInt("allergy"));
				menuDTO.setFeeling(rs.getInt("feeling"));
				menuDTO.setCondition(rs.getInt("condition"));
				menuDTO.setWeather(rs.getInt("weather"));
				menuDTO.setTemperature(rs.getInt("temperature"));
				menuDTO.setDust(rs.getInt("dust"));
				menuDTO.setHumidity(rs.getInt("humidity"));
				menuDTO.setFavor(rs.getInt("favor"));
				menuDTO.setCalorie(rs.getInt("calorie"));
				menuDTO.setHealth(rs.getInt("health"));
				menuDTO.setCategory(rs.getInt("category"));
				menuDTO.setMenuNumber(rs.getInt("menu_number"));
				cnt++;
			}

			if (cnt < 1) {
				return null;
			}

			return menuDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MenuDTO_메뉴정보가져오기_ERROR");
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
}
