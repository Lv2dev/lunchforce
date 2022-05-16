package com.lunchforce.store;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import com.lunchforce.dbconnect.JDBConnect;

public class StoreAddressDAO extends JDBConnect{
	private static StoreAddressDAO storeAddressDAO = new StoreAddressDAO();

	// 생성자
	private StoreAddressDAO() {

	}

	// getters and setters

	// 인스턴스 getter
	public static StoreAddressDAO getInstance() {
		if (storeAddressDAO == null) {
			storeAddressDAO = new StoreAddressDAO();
		}
		return storeAddressDAO;
	}
	
	//주소추가
	public synchronized boolean newAddress(StoreAddressDTO addressDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO storerocation (`store_Id`, `address`, `address_x`, `address_y`) ");
			query.append("VALUES(?, ?, ?, ?)");

			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setInt(1, addressDTO.getStore_id());
			pstmt.setString(2, addressDTO.getAddress());
			pstmt.setDouble(3, addressDTO.getAddressX());
			pstmt.setDouble(4, addressDTO.getAddressY());
			

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("주소추가에러");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//주소 가져오기
	public synchronized StoreAddressDTO getAddressInfo(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT * FROM storerocation ");
			query.append("WHERE store_id = " + storeId );

			rs = stmt.executeQuery(query.toString());

			int cnt = 0;
			StoreAddressDTO addressDTO = new StoreAddressDTO();
			while (rs.next()) {
				addressDTO.setStore_id(rs.getInt("store_id"));
				addressDTO.setAddressX(rs.getDouble("address_x"));
				addressDTO.setAddressX(rs.getDouble("address_y"));
				addressDTO.setAddress(rs.getString("address"));
				cnt++;
			}

			if (cnt != 1) {
				return null;
			}

			return addressDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("주소가져오기 에러 \n" + e.getMessage());
			return null;
		} finally {
			disconnectStmt();
		}
	}
}
