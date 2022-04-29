package com.lunchforce.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.lunchforce.dbconnect.DBConnecter;
import com.lunchforce.dbconnect.JDBConnect;

import java.sql.ResultSet;

public class PersonalMenuRecommendDAO extends JDBConnect{
	private static PersonalMenuRecommendDAO personalMenuRecommendDAO  = new PersonalMenuRecommendDAO();
	
	//생성자
	private PersonalMenuRecommendDAO() {}
	
	//인스턴스 getter
	public static PersonalMenuRecommendDAO getInstance() {
		if (personalMenuRecommendDAO == null) {
			personalMenuRecommendDAO = new PersonalMenuRecommendDAO();
		}
		return personalMenuRecommendDAO;
	}
	
	//method
	
	//개인화 데이터 없을때 추가하는(최초 메뉴추천 기능 실행 시 유저가 설정하게 됨) 메서드
	public synchronized boolean add(PersonalMenuRecommendDTO personalMenuRecommendDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO personal_menu_recommend ");
			query.append("VAEUS(?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(2, personalMenuRecommendDTO.getUserId());
			pstmt.setInt(3, personalMenuRecommendDTO.getFavor());
			pstmt.setInt(4, personalMenuRecommendDTO.getHate_favor());
			pstmt.setInt(3, personalMenuRecommendDTO.getCalorie());
			pstmt.setInt(3, personalMenuRecommendDTO.getNutrition());
			pstmt.setInt(3, personalMenuRecommendDTO.getCategory());
			pstmt.setInt(3, personalMenuRecommendDTO.getAllergy());
			pstmt.setInt(3, personalMenuRecommendDTO.getDistance());
			pstmt.setString(3, personalMenuRecommendDTO.getRecommendKeyword());
			pstmt.setString(3, personalMenuRecommendDTO.getNotRecommendKeyword());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("개인화 데이터 추가 error");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//개인화 데이터가 이미 있는지 확인하는 메서드
	public synchronized Boolean getQuestion(String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT id FROM personal_menu_recommend");
			query.append("WHERE id = '" + userId + "'");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				cnt++;
			}

			if (cnt != 1) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("개인화 데이터 있는지 확인 실패..");
			return null;
		} finally {
			disconnectStmt();
		}
	}
	
	//개인화 데이터를 수정하는 메서드
	public synchronized boolean deleteMember(PersonalMenuRecommendDTO personalMenuRecommendDTO) throws SQLException{
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE personal_menu_recommend ");
			query.append("SET favor = " + "" + personalMenuRecommendDTO.getFavor() + ", " );
			query.append(" hate_favor = " + "" + personalMenuRecommendDTO.getHate_favor() + ", " );
			query.append(" calorie = " + "" + personalMenuRecommendDTO.getCalorie() + ", " );
			query.append(" nutrition = " + "" + personalMenuRecommendDTO.getNutrition() + ", " );
			query.append(" category = " + "" + personalMenuRecommendDTO.getCategory() + ", " );
			query.append(" allergy = " + "" + personalMenuRecommendDTO.getAllergy() + ", " );
			query.append(" distance = " + "" + personalMenuRecommendDTO.getAllergy() + ", " );
			query.append(" recommend_keyword = " + "'" + personalMenuRecommendDTO.getRecommendKeyword() + "', " );
			query.append(" not_recommend_keyword = " + "'" + personalMenuRecommendDTO.getNotRecommendKeyword() + "' " );
			query.append("WHERE user_id = " + "'" + personalMenuRecommendDTO.getUserId() + "'");
			
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
	
}
