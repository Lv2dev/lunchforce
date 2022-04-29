package com.lunchforce.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.lunchforce.dbconnect.DBConnecter;
import com.lunchforce.dbconnect.JDBConnect;
import com.lunchforce.store.MenuElementDTO;

import java.sql.ResultSet;

public class PersonalRecommendWeightDAO extends JDBConnect{
	private static PersonalRecommendWeightDAO personalRecommendWeightDAO = new PersonalRecommendWeightDAO();
	
	//생성자
	private PersonalRecommendWeightDAO() {}
	
	// 인스턴스 getter
	public static PersonalRecommendWeightDAO getInstance() {
		if (personalRecommendWeightDAO == null) {
			personalRecommendWeightDAO = new PersonalRecommendWeightDAO();
		}
		return personalRecommendWeightDAO;
	}
	
	//method
	
	//새 레이블 추가 후 기본 가중치로 초기화 - 회원가입 시 수행되도록 함
	public synchronized boolean init(String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO personal_recommend_weight ");
			query.append("VAEUS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(2, userId); //userid
			pstmt.setInt(3, 2); //요소들의 가중치 조절 - 0~4 - 기본값은 2
			pstmt.setInt(4, 2);
			pstmt.setInt(5, 2);
			pstmt.setInt(6, 2);
			pstmt.setInt(7, 2);
			pstmt.setInt(8, 2);
			pstmt.setInt(9, 2);
			pstmt.setInt(10, 2);
			pstmt.setInt(11, 2);
			pstmt.setInt(12, 2);
			pstmt.setInt(13, 2);
			pstmt.setInt(14, 2);
			pstmt.setInt(15, 2);
			pstmt.setInt(16, 2);
			pstmt.setInt(17, 2);
			pstmt.setInt(18, 2);
			pstmt.setInt(19, 2); //random도 0~4

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			disconnectPstmt();
			return false;
		}
	}
	
	//개인화 가중치 수정하기
	public synchronized boolean editMemberInfo(PersonalRecommendWeightDTO weightDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE personal_recommend_weight ");
			query.append("SET allergy = " + "" + weightDTO.getAllergy() + ", " );
			query.append("SET recommend_keyword = " + "" + weightDTO.getRecommend_keyword() + ", " );
			query.append("SET feeling = " + "" + weightDTO.getFeeling() + ", " );
			query.append("SET condition = " + "" + weightDTO.getCondition() + ", " );
			query.append("SET health = " + "" + weightDTO.getHealth() + ", " );
			query.append("SET weather = " + "" + weightDTO.getWeather() + ", " );
			query.append("SET temperature = " + "" + weightDTO.getTemperature() + ", " );
			query.append("SET dust = " + "" + weightDTO.getDust() + ", " );
			query.append("SET humidity = " + "" + weightDTO.getHumidity() + ", " );
			query.append("SET favor = " + "" + weightDTO.getFavor() + ", " );
			query.append("SET order_list = " + "" + weightDTO.getOrderList() + ", " );
			query.append("SET calorie = " + "" + weightDTO.getCalorie() + ", " );
			query.append("SET nutrition = " + "" + weightDTO.getNutrition() + ", " );
			query.append("SET category = " + "" + weightDTO.getCategory() + ", " );
			query.append("SET score = " + "" + weightDTO.getScore() + ", " );
			query.append("SET distance = " + "" + weightDTO.getDistance() + ", " );
			query.append("SET random = " + "" + weightDTO.getRandom() + ", " );
			query.append("WHERE user_id = " + "'" + weightDTO.getUserId() + "'");
			
			pstmt = conn.prepareStatement(query.toString());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//개인화 가중치 삭제하기 - 회원탈퇴 시 함께 수행하도록 함
	public synchronized boolean deleteWeight(String userId) throws SQLException{
		try {
			
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM personal_recommend_weight ");
			query.append("WHERE id = '" + userId + "'");
			
			pstmt = conn.prepareStatement(query.toString());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//개인화 가중치 가져오기
	public synchronized PersonalRecommendWeightDTO delPRW(String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT id, user_id, allergy, recommend_keyword, feeling, ");
			query.append("condition, health, favor, weather, temperature, dust, humidity, order_list");
			query.append("calorie, nutrition, category, score, distance, random FROM personal_remommend_weight ");
			query.append("WHERE user_id = '" + userId + "'");

			rs = stmt.executeQuery(query.toString());
			
			PersonalRecommendWeightDTO personalRecommendWeightDTO = new PersonalRecommendWeightDTO();
			
			int cnt = 0;
			while (rs.next()) {
				personalRecommendWeightDTO.setId(rs.getInt("id"));
				personalRecommendWeightDTO.setUserId(rs.getString("user_id"));
				personalRecommendWeightDTO.setAllergy(rs.getInt("allergy"));
				personalRecommendWeightDTO.setRecommend_keyword(rs.getInt("recommend_keyword"));
				personalRecommendWeightDTO.setFeeling(rs.getInt("feeling"));
				personalRecommendWeightDTO.setCondition(rs.getInt("condition"));
				personalRecommendWeightDTO.setHealth(rs.getInt("health"));
				personalRecommendWeightDTO.setFavor(rs.getInt("favor"));
				personalRecommendWeightDTO.setWeather(rs.getInt("weather"));
				personalRecommendWeightDTO.setTemperature(rs.getInt("temperature"));
				personalRecommendWeightDTO.setDust(rs.getInt("dust"));
				personalRecommendWeightDTO.setHumidity(rs.getInt("humidity"));
				personalRecommendWeightDTO.setOrderList(rs.getInt("order_list"));
				personalRecommendWeightDTO.setCalorie(rs.getInt("calorie"));
				personalRecommendWeightDTO.setNutrition(rs.getInt("nutrition"));
				personalRecommendWeightDTO.setCategory(rs.getInt("category"));
				personalRecommendWeightDTO.setScore(rs.getInt("score"));
				personalRecommendWeightDTO.setDistance(rs.getInt("distance"));
				personalRecommendWeightDTO.setRandom(rs.getInt("random"));
				cnt++;
			}

			if (cnt < 1) {
				return null;
			}
			
			return personalRecommendWeightDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnectStmt();
		}
	}
}
