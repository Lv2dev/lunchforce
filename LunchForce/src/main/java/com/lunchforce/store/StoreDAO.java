package com.lunchforce.store;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.lunchforce.dbconnect.DBConnecter;
import com.lunchforce.dbconnect.JDBConnect;

public class StoreDAO extends JDBConnect{
	private static StoreDAO storeDAO = new StoreDAO();

	// 생성자
	private StoreDAO() {

	}

	// getters and setters

	// 인스턴스 getter
	public static StoreDAO getInstance() {
		if (storeDAO == null) {
			storeDAO = new StoreDAO();
		}
		return storeDAO;
	}

	// 가게 생성
	public synchronized boolean joinStore(StoreDTO storeDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO store ");
			query.append("VAEUS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, storeDTO.getStoreId());
			pstmt.setString(2, storeDTO.getUserId());
			pstmt.setString(3, storeDTO.getCategory());
			pstmt.setString(4, storeDTO.getNotice());
			pstmt.setString(5, storeDTO.getTel());
			pstmt.setString(6, storeDTO.getThumb());
			pstmt.setInt(7, storeDTO.getOpenTime());
			pstmt.setInt(8, storeDTO.getCloseTime());
			pstmt.setInt(9, storeDTO.getRestDay());
			pstmt.setInt(10, storeDTO.getBraketimeStart());
			pstmt.setInt(11, storeDTO.getBraketimeEnd());
			pstmt.setTimestamp(12, storeDTO.getJoinDay());
			pstmt.setString(13, storeDTO.getStoreName());
			pstmt.setInt(14, storeDTO.getStatus());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("memberDAO_memberJoin()_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 가게정보 수정
	// category, notice, tel, thumb, opentime, closetime, rest_day, braketime_start,
	// braketime_end 수정 가능
	public synchronized boolean editStoreInfo(StoreDTO storeDTO2) throws SQLException {
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE store ");
			query.append("SET category = " + "'" + storeDTO2.getCategory() + "', ");
			query.append("notice = " + "'" + storeDTO2.getNotice() + "', ");
			query.append("tel = " + "'" + storeDTO2.getTel() + "', ");
			query.append("thumb = " + "'" + storeDTO2.getThumb() + "', ");
			query.append("opentime = " + "" + storeDTO2.getOpenTime() + ", ");
			query.append("closetime = " + "'" + storeDTO2.getCloseTime() + ", ");
			query.append("rest_day = " + "'" + storeDTO2.getRestDay() + ", ");
			query.append("braketime_start = " + "'" + storeDTO2.getBraketimeStart() + ", ");
			query.append("braketime_end = " + "'" + storeDTO2.getBraketimeEnd() + " ");
			query.append("store_name = " + "'" + storeDTO2.getStoreName() + "', ");
			query.append("status = " + "0, ");
			query.append("WHERE store_id = " + "" + storeDTO2.getStoreId() + "");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("StoreDAO_가게정보수정_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 가게id와 유저id를 넣으면 가게정보 한번에 가져오기
	public synchronized StoreDTO getStoreInfo(int storeId, String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT * FROM store ");
			query.append("WHERE store_id = " + storeId + " and user_id = '" + userId + "'");

			rs = stmt.executeQuery(query.toString());

			StoreDTO storeDTO = new StoreDTO();

			int cnt = 0;
			while (rs.next()) {
				storeDTO.setStoreId(storeId);
				storeDTO.setUserId(rs.getString("user_id"));
				storeDTO.setCategory(rs.getString("category"));
				storeDTO.setNotice(rs.getString("notice"));
				storeDTO.setTel(rs.getString("tel"));
				storeDTO.setThumb(rs.getString("thumb"));
				storeDTO.setOpenTime(rs.getInt("opentime"));
				storeDTO.setCloseTime(rs.getInt("closetime"));
				storeDTO.setRestDay(rs.getInt("rest_day"));
				storeDTO.setBraketimeStart(rs.getInt("braketime_start"));
				storeDTO.setBraketimeEnd(rs.getInt("braketime_end"));
				storeDTO.setJoinDay(rs.getTimestamp("join_day"));
				storeDTO.setStoreName(rs.getString("store_name"));
				storeDTO.setStatus(rs.getInt("status"));
				cnt++;
			}

			if (cnt < 1) {
				return null;
			}

			return storeDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("storeDAO_가게정보한번에가져오기ERROR");
			return null;
		} finally {
			disconnectStmt();
		}
	}

	// 가게삭제 - 삭제 성공 시 true, 실패 시 false
	public synchronized boolean deleteStore(StoreDTO storeDTO) throws SQLException {
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM store ");
			query.append("WHERE store_id = '" + storeDTO.getStoreId() + "'");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("StoreDAO_가게삭제_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 가계통계 - 지정한 기간동안의 주문 수
	
	// 가게 목록 가져오기
		public synchronized LinkedHashMap<Integer, String> getStoreList(String userId) throws SQLException{
			LinkedHashMap<Integer, String> hash = new LinkedHashMap<Integer, String>();
			try {
				conn = dbConn.getConn();
				stmt = conn.createStatement();
				query = new StringBuffer();
				query.append("SELECT store_id, store_name from store ");
				query.append("WHERE user_id = '" + userId + "'");

				rs = stmt.executeQuery(query.toString());
				int cnt = 0;
				while(rs.next()){
					cnt++;
					hash.put(rs.getInt("store_id"), rs.getString("store_name"));
				}
				if(cnt == 0) {
					return null;
				}
				return hash;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}finally {
				disconnectStmt();
			}
		}
}
