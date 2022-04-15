package com.lunchforce.store;

import java.sql.SQLException;

import javax.swing.MenuElement;

import com.lunchforce.dbconnect.JDBConnect;
import com.lunchforce.member.MemberDTO;
import com.lunchforce.member.PersonalRecommendWeightDAO;
import com.lunchforce.member.PersonalRecommendWeightDTO;

public class MenuElementDAO extends JDBConnect{
	private static MenuElementDAO menuElementDAO = new MenuElementDAO();
	
	//생성자
	private MenuElementDAO() {
		
	}
	
	public static MenuElementDAO getInstance() {
		if (menuElementDAO == null) {
			menuElementDAO = new MenuElementDAO();
		}
		return menuElementDAO;
	}
	
	//method
	
	//메뉴 엘리먼트 추가(이름 비교 후 같은이름 있으면 안되게)
	public synchronized boolean addElement(MenuElementDTO menuElementDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO menuelement ");
			query.append("VAEUS(?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(2, menuElementDTO.getAllergy()); //userid
			pstmt.setInt(3, menuElementDTO.getFeeling()); //요소들의 가중치 조절 - 0~4 - 기본값은 2
			pstmt.setInt(4, menuElementDTO.getCondition());
			pstmt.setInt(5, menuElementDTO.getWeather());
			pstmt.setInt(6, menuElementDTO.getTemperature());
			pstmt.setInt(7, menuElementDTO.getDust());
			pstmt.setInt(8, menuElementDTO.getHumidity());
			pstmt.setInt(9, menuElementDTO.getFavor());
			pstmt.setInt(10, menuElementDTO.getCalorie());
			pstmt.setInt(11, menuElementDTO.getHealth());
			pstmt.setInt(12, menuElementDTO.getCategory());
			pstmt.setInt(13, menuElementDTO.getNutrition());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			disconnectPstmt();
			return false;
		}
	}
	
	//메뉴 엘리먼트 수정
	public synchronized boolean editElement(MenuElementDTO menuElementDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE menuelement ");
			query.append("SET allergy = " + "" + menuElementDTO.getAllergy() + ", " );
			query.append("SET feeling = " + "" + menuElementDTO.getFeeling() + ", " );
			query.append("SET condition = " + "" + menuElementDTO.getCondition() + ", " );
			query.append("SET weather = " + "" + menuElementDTO.getWeather() + ", " );
			query.append("SET temperature = " + "" + menuElementDTO.getTemperature() + ", " );
			query.append("SET dust = " + "" + menuElementDTO.getDust() + ", " );
			query.append("SET humidity = " + "" + menuElementDTO.getHumidity() + ", " );
			query.append("SET favor = " + "" + menuElementDTO.getFavor() + ", " );
			query.append("SET calorie = " + "" + menuElementDTO.getCalorie() + ", " );
			query.append("SET health = " + "" + menuElementDTO.getHealth() + ", " );
			query.append("SET category = " + "" + menuElementDTO.getCategory() + ", " );
			query.append("SET nutrition = " + "" + menuElementDTO.getNutrition() + ", " );
			query.append("WHERE id = " + "" + menuElementDTO.getId() + "");
			
			pstmt = conn.prepareStatement(query.toString());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//메뉴 엘리먼트 삭제
	public synchronized boolean deleteElement(String elementId) throws SQLException {
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM menuelement ");
			query.append("WHERE id = " + elementId + "");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("ElementDAO_Element삭제_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//메뉴 엘리먼트 가져오기
	public synchronized MenuElementDTO getElement(String elementId) throws SQLException{
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT id, allergy, feeling, condition, weather, temperature, dust, humidity, favor, calorie, health, category, nurition FROM menuelement");
			query.append("WHERE id = '" + elementId + "'");

			rs = stmt.executeQuery(query.toString());
			
			MenuElementDTO menuelementDTO = new MenuElementDTO();
			
			int cnt = 0;
			while (rs.next()) {
				menuelementDTO.setId(rs.getInt("id"));
				menuelementDTO.setAllergy(rs.getInt("allergy"));
				menuelementDTO.setFeeling(rs.getInt("feeling"));
				menuelementDTO.setCondition(rs.getInt("condition"));
				menuelementDTO.setWeather(rs.getInt("weather"));
				menuelementDTO.setTemperature(rs.getInt("temperature"));
				menuelementDTO.setDust(rs.getInt("dust")); 
				menuelementDTO.setHumidity(rs.getInt("humidity"));
				menuelementDTO.setFavor(rs.getInt("favor"));
				menuelementDTO.setCalorie(rs.getInt("calorie"));
				menuelementDTO.setHealth(rs.getInt("health"));
				menuelementDTO.setCategory(rs.getInt("category"));
				menuelementDTO.setNutrition(rs.getInt("nutrition"));
				cnt++;
			}

			if (cnt < 1) {
				return null;
			}
			
			return menuelementDTO;
		} catch (Exception e) {
			System.out.println("MemberDAO_getMemberInfo_ERROR");
			return null;
		} finally {
			disconnectStmt();
		}
	}
}
