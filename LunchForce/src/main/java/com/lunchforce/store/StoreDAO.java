package com.lunchforce.store;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.lunchforce.dbconnect.DBConnecter;
import com.lunchforce.dbconnect.JDBConnect;

public class StoreDAO extends JDBConnect {
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
			query.append(
					"INSERT INTO store (`user_id`, `category`, `notice`, `tel`, `thumb`,`opentime`, `closetime`, `rest_day`, `braketime_start`, `braketime_end`, `join_day`, `store_name`, `status`) ");
			query.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(query.toString());

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			String today = null;
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);

			pstmt.setString(1, storeDTO.getUserId());
			pstmt.setInt(2, storeDTO.getCategory());
			pstmt.setString(3, storeDTO.getNotice());
			pstmt.setString(4, storeDTO.getTel());
			pstmt.setString(5, storeDTO.getThumb());
			pstmt.setInt(6, storeDTO.getOpenTime());
			pstmt.setInt(7, storeDTO.getCloseTime());
			pstmt.setInt(8, storeDTO.getRestDay());
			pstmt.setInt(9, storeDTO.getBraketimeStart());
			pstmt.setInt(10, storeDTO.getBraketimeEnd());
			pstmt.setTimestamp(11, ts);
			pstmt.setString(12, storeDTO.getStoreName());
			pstmt.setInt(13, storeDTO.getStatus());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("StoreDAO 가게삽입 에러");
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
			query.append("closetime = " + "" + storeDTO2.getCloseTime() + ", ");
			query.append("rest_day = " + "" + storeDTO2.getRestDay() + ", ");
			query.append("braketime_start = " + "" + storeDTO2.getBraketimeStart() + ", ");
			query.append("braketime_end = " + "" + storeDTO2.getBraketimeEnd() + ", ");
			query.append("store_name = " + "'" + storeDTO2.getStoreName() + "', ");
			query.append("status = " + "0 ");
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
	public synchronized StoreDTO getStoreInfo(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();

			query = new StringBuffer();

			query.append("SELECT * FROM store ");
			query.append("WHERE store_id = ?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, storeId);
			rs = pstmt.executeQuery();

			StoreDTO storeDTO = new StoreDTO();

			int cnt = 0;
			while (rs.next()) {
				storeDTO.setStoreId(storeId);
				storeDTO.setUserId(rs.getString("user_id"));
				storeDTO.setCategory(rs.getInt("category"));
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
				storeDTO.setAddress(rs.getString("address"));
				storeDTO.setAddressX(rs.getDouble("address_x"));
				storeDTO.setAddressY(rs.getDouble("address_y"));
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
			disconnectPstmt();
		}
	}

	// 가게삭제 - 삭제 성공 시 true, 실패 시 false
	public synchronized boolean deleteStore(int storeId) throws SQLException {
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM store ");
			query.append("WHERE store_id = " + storeId + "");

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}

			pstmt.close();

			// 메뉴삭제
			MenuDAO mdao = MenuDAO.getInstance();
			LinkedHashMap<Integer, String> hash = mdao.getAllMenu(storeId);
			String sql;

			sql = "DELETE FROM menu WHERE store_id" + storeId;
			pstmt = conn.prepareStatement(sql);
			pstmt.close();

			// 메뉴의 옵션들 삭제하기
			if (hash != null) {
				for (Entry<Integer, String> entrySet : hash.entrySet()) {
					// entrySet.getKey(), entrySet.getValue()
					sql = "DELETE FROM menuoption WHERE menu_id = " + entrySet.getKey();
					pstmt = conn.prepareStatement(sql);
					pstmt.close();
				}
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
	public synchronized LinkedHashMap<Integer, String> getStoreList(String userId) throws SQLException {
		LinkedHashMap<Integer, String> hash = new LinkedHashMap<Integer, String>();
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();
			query.append("SELECT store_id, store_name from store ");
			query.append("WHERE user_id = '" + userId + "'");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				cnt++;
				hash.put(rs.getInt("store_id"), rs.getString("store_name"));
			}
			if (cnt == 0) {
				return null;
			}
			return hash;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			disconnectStmt();
		}
	}

	// 가게명 검색해서 검색결과 List으로 가져오기 - 키워드, 위도, 경도, 반경
	public synchronized ArrayList<StoreDTO> getSearchList(String keyword, double x, double y, int distance)
			throws SQLException {
		ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();
			query.append(" select distinct store.store_id, store.store_name,");
			query.append(" st_distance_sphere(point(" + x + ", " + y
					+ "), point(store.address_x, store.address_y)) as distance");
			query.append(" from store join menu");
			query.append(" on store.store_id = menu.store_id and ((store.store_name like '%" + keyword
					+ "%' or menu.menu_name like '%" + keyword + "%') and");
			query.append(" st_distance_sphere(point(" + x + ", " + y + "), point(store.address_x, store.address_y)))<"
					+ (distance * 1000));
			query.append(" order by distance;"); // 반경순 정렬
			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				cnt++;
				StoreDTO dto = new StoreDTO();
				dto.setStoreId(rs.getInt("store_id"));
				dto.setStoreName(rs.getString("store_name"));
				list.add(dto);
			}
			if (cnt == 0) {
				return list;
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			disconnectStmt();
		}
	}

	// 가게명 검색해서 검색결과 List으로 가져오기 - 키워드만
	public synchronized ArrayList<StoreDTO> getSearchList(String keyword) throws SQLException {
		ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select distinct store.store_id, store.store_name, store.thumb ");
			query.append("from store join menu ");
			query.append("on store.store_id = menu.store_id and ");
			query.append("(store.store_name like ? or menu.menu_name like ?) order by store_name");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");

			rs = pstmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				cnt++;
				StoreDTO dto = new StoreDTO();
				dto.setStoreId(rs.getInt("store_id"));
				dto.setStoreName(rs.getString("store_name"));
				dto.setThumb(rs.getString("thumb"));
				list.add(dto);
			}
			if (cnt == 0) {
				return list;
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 전체 검색 결과의 갯수를 리턴하는 메서드
	public synchronized int getSearchCount(String keyword) throws SQLException {
		ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select distinct store.store_id, store.store_name, store.thumb ");
			query.append("from store left join menu ");
			query.append("on store.store_id = menu.store_id and ");
			query.append("(store.store_name like '%" + keyword + "%' or menu.menu_name like '%" + keyword
					+ "%') order by store_name");

			pstmt = conn.prepareStatement(query.toString());

			rs = pstmt.executeQuery();
			int cnt = 0;
			while (rs.next()) {
				cnt++;
			}
			return cnt;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("검색결과의 갯수를 리턴하는 메서드 오류 " + e.getMessage());
			return 0;
		} finally {
			disconnectPstmt();
		}
	}

	// 가게명 검색해서 검색결과 페이징 해서 List로 가져오기
	// keyword : 검색어
	public synchronized ArrayList<StoreDTO> getSearchList(String keyword, int page, int pageCount) throws SQLException {
		ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
		try {
			conn = dbConn.getConn();

			query = new StringBuffer();
			query.append("select * from ( ");
			query.append(
					"select distinct st.store_id, st.store_name, st.thumb, row_number() over(order by store_name) as num ");
			query.append("from store as st left join menu as m  ");
			query.append("on st.store_id = m.store_id where ");
			query.append("(st.store_name like '%" + keyword + "%' or m.menu_name like '%" + keyword
					+ "%') group by st.store_id order by store_name limit ? ) as temp ");
			query.append("where num > ? order by num ");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, pageCount);
			pstmt.setInt(2, (page - 1) * pageCount);

			rs = pstmt.executeQuery();
			int cnt = 0;
			while (rs.next()) {
				cnt++;
				StoreDTO dto = new StoreDTO();
				dto.setStoreId(rs.getInt("store_id"));
				dto.setStoreName(rs.getString("store_name"));
				dto.setThumb(rs.getString("thumb"));
				list.add(dto);
			}
			if (cnt == 0) {
				return list;
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 주소추가
	public synchronized boolean newAddress(StoreDTO sdto) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("update store set ");
			query.append("address = ?, address_x = ?, address_y = ? where store_Id = " + sdto.getStoreId());

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, sdto.getAddress());
			pstmt.setDouble(2, sdto.getAddressX());
			pstmt.setDouble(3, sdto.getAddressY());

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

	// 주소 가져오기
	public synchronized StoreDTO getAddressInfo(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT * FROM store ");
			query.append("WHERE store_id = " + storeId + " and address IS NOT null");

			rs = stmt.executeQuery(query.toString());

			int cnt = 0;
			StoreDTO sDTO = new StoreDTO();
			while (rs.next()) {
				sDTO.setStoreId(rs.getInt("store_id"));
				sDTO.setAddressX(rs.getDouble("address_x"));
				sDTO.setAddressY(rs.getDouble("address_y"));
				sDTO.setAddress(rs.getString("address"));
				cnt++;
			}

			if (cnt != 1) {
				return null;
			}

			return sDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("주소가져오기 에러 \n" + e.getMessage());
			return null;
		} finally {
			disconnectStmt();
		}
	}

	// 가게의 현재 상태 가져오기
	public synchronized int getState(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			stmt = conn.createStatement();

			query.append("SELECT * FROM store ");
			query.append("WHERE store_id = " + storeId);

			rs = stmt.executeQuery(query.toString());

			int cnt = 0;
			int state = 99;
			while (rs.next()) {
				state = rs.getInt("status");
				cnt++;
			}

			if (cnt != 1) {
				return 99;
			}

			System.out.println(state);
			return state;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("가게의 현재 상태 가져오기 에러 \n" + e.getMessage());
			return 99;
		} finally {
			disconnectStmt();
		}
	}

	// 가게 오픈하기
	public synchronized void setOpen(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("update store set ");
			query.append("status = 1 where store_Id = " + storeId);

			pstmt = conn.prepareStatement(query.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("가게오픈에러");
		} finally {
			disconnectPstmt();
		}
	}

	// 가게닫기
	public synchronized void setClose(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("update store set ");
			query.append("status = 0 where store_Id = " + storeId);

			pstmt = conn.prepareStatement(query.toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("가게닫기에러");
		} finally {
			disconnectPstmt();
		}
	}

	// 카테고리별로 가져오기
	public synchronized ArrayList<StoreDTO> searchCategoryStorePaging(int category, int page, int pageCount)
			throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("select * from ( ");
			query.append("   select store_id, store_name, thumb, row_number() over(order by store_name) as num ");
			query.append("   from store ");
			query.append("   where category & ? = 1"); // 비트연산
			query.append(") as temp where num between ? and ?");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, category);
			pstmt.setInt(3, ((page - 1) * pageCount) + pageCount - 1);
			pstmt.setInt(2, (page - 1) * pageCount);

			rs = pstmt.executeQuery();

			ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
			while (rs.next()) {
				StoreDTO dto = new StoreDTO();
				dto.setStoreId(rs.getInt("store_id"));
				dto.setStoreName(rs.getString("store_name"));
				dto.setThumb(rs.getString("thumb"));
				list.add(dto);
			}

			return list;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("카테고리별로 가게 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 카테고리별 검색 갯수 가져오기
	public synchronized int searchCategoryStoreCount(int category) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("   select count(store_id) as cnt ");
			query.append("   from store ");
			query.append("   where category & ? = 1"); // 비트연산

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, category);

			rs = pstmt.executeQuery();

			int cnt = 0;
			while (rs.next()) {
				cnt = rs.getInt("cnt");
			}

			return cnt;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("카테고리별 가게 갯수 가져오기 오류 " + e.getMessage());
			return 0;
		} finally {
			disconnectPstmt();
		}
	}
	
	public synchronized ArrayList<StoreDTO> getMyStoreList(String memberId) throws SQLException{
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from store where user_id = ?");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			
			ArrayList<StoreDTO> list = new ArrayList<StoreDTO>();
			while(rs.next()) {
				StoreDTO storeDTO = new StoreDTO();
				storeDTO.setStoreName(rs.getString("store_name"));
				storeDTO.setStoreId(rs.getInt("store_id"));
				storeDTO.setUserId(rs.getString("user_id"));
				storeDTO.setThumb(rs.getString("thumb"));
				list.add(storeDTO);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("가게 정보 list로 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}
}
