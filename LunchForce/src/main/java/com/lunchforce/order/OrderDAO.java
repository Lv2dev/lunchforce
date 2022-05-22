package com.lunchforce.order;

import com.lunchforce.dbconnect.JDBConnect;
import com.lunchforce.store.MenuDTO;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class OrderDAO extends JDBConnect {
	private static OrderDAO orderDAO = new OrderDAO();

	// 생성자
	private OrderDAO() {
	}

	// 인스턴스 getter
	public static OrderDAO getInstance() {
		if (orderDAO == null) {
			orderDAO = new OrderDAO();
		}
		return orderDAO;
	}

	// method

	
	// 장바구니 정보 가져오기
	
	
	// 장바구니 추가
	public synchronized boolean addShopping(String userId, int storeId, int menuId, int[] optionId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			//장바구니가 이미 있는지 없는지 체크
			query.append("SELECT orderlist_id from orderlist where user_id = '" + userId + "'");
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			int cnt1 = 0;
			int orderId = 0;
			while(rs.next()) {
				cnt1++;
				orderId = rs.getInt("orderlist_id");
			}
			disconnectPstmt();
			if(cnt1 == 1) { //장바구니가 이미 있으면
				//다른 가게인지 체크
				query = new StringBuffer();
				query.append("SELECT orderlist_id from orderlist where store_id = " + storeId);
				pstmt = conn.prepareStatement(query.toString());
				rs = pstmt.executeQuery();
				int cnt2 = 0;
				
				while(rs.next()) {
					cnt2++;
					
				}
				disconnectPstmt();
				if(cnt2 != 1) {// 다른 가게이면 
					//기존에 있던 장바구니 삭제
					query = new StringBuffer();
					query.append("DELETE FROM orderlist ");
					query.append("WHERE orderlist_id= " + orderId);
					pstmt = conn.prepareStatement(query.toString());
					rs = pstmt.executeQuery();
					disconnectPstmt();
					//새로운 장바구니 추가
					query = new StringBuffer();
					query.append("INSERT INTO orderlist (`user_Id`,`store_id`,`order_date`,`price`,`status`,`time`) ");
					query.append("VALUES(?,?,?,?,?,?)");
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, userId);
					pstmt.setInt(2, storeId);
					//타임스탬프
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar cal = Calendar.getInstance();
					String today = null;
					today = formatter.format(cal.getTime());
					Timestamp ts = Timestamp.valueOf(today);
					pstmt.setTimestamp(3, ts);
					pstmt.setInt(4, 0);
					pstmt.setInt(5, 0);
					pstmt.setInt(6, 0);
					pstmt.executeUpdate();
					pstmt.close();
				}
			}else {//장바구니가 없으면
				//장바구니 새로 추가
				query = new StringBuffer();
				query.append("INSERT INTO orderlist (`user_Id`,`store_id`,`order_date`,`price`,`status`,`time`) ");
				query.append("VALUES(?,?,?,?,?,?)");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, userId);
				pstmt.setInt(2, storeId);
				//타임스탬프
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Calendar cal = Calendar.getInstance();
				String today = null;
				today = formatter.format(cal.getTime());
				Timestamp ts = Timestamp.valueOf(today);
				pstmt.setTimestamp(3, ts);
				pstmt.setInt(4, 0);
				pstmt.setInt(5, 0);
				pstmt.setInt(6, 0);
				pstmt.executeUpdate();
				pstmt.close();
			}
			//장바구니에 추가한 내용 업데이트
			//1. ordermenu 추가하기
			query = new StringBuffer();
			query.append("INSERT INTO ordermenu (`orderlist_id`,`menu_id`,`menu_name`,`price`) ");
			query.append("VALUES(");
			query.append("(select orderlist_id from orderlist where user_id = ? and state=0),");
			query.append("(select menu_id from menu where menu_id = ?),");
			query.append("(select menu_name from menu where menu_id = ?),");
			query.append("(select price from menu where menu_id = ?))");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			pstmt.setInt(2, menuId);
			pstmt.setInt(3, menuId);
			pstmt.setInt(4, menuId);
			pstmt.executeUpdate();
			pstmt.close();
			//2. orderoption 추가하기
			if(optionId.length > 0) { //들어온 옵션이 1개 이상이면 수행
				for(int i = 0; i < optionId.length; i++) {
					query = new StringBuffer();
					query.append("INSERT INTO orderoption (`ordermenu_Id`,`option_id`,`menu_name`,`price`) ");
					query.append("VALUES(");
					query.append("(select distinct ordermenu.id from orderlist inner join ordermenu on orderlist.orderlist_id = ordermenu.orderlist_id and(orderlist.user_id = ? and orderlist.status = 0 and ordermenu.menu_id = ?)),");
					query.append("(select id from menuoption where id = ? and menu_id = ?),");
					query.append("(select price from menuoption where id = ? and menu_id = ?),");
					query.append("(select option_name from menuoption where id = ? and menu_id = ?))");
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, userId);
					pstmt.setInt(2, menuId);
					pstmt.setInt(3, optionId[i]);
					pstmt.setInt(4, menuId);
					pstmt.setInt(5, optionId[i]);
					pstmt.setInt(6, menuId);
					pstmt.setInt(7, optionId[i]);
					pstmt.setInt(8, menuId);
					pstmt.executeUpdate();
					pstmt.close();
				}
			}
			//3. order 테이블의 값 수정하기(가격계산해서)
			query = new StringBuffer();
			query.append(" update orderlist as ol1 set ");
			query.append(" price = ( ");
			query.append(" select sum1 from(");
			query.append(" select sum(oo.price) as sum1 from (");
			query.append(" select om.id  from orderlist as ol left join ordermenu as om on ol.orderlist_id = om.orderlist_id and (ol.user_id=? and status = '0')"); //유저아이디 입력
			query.append(" )as A inner join orderoption as oo on A.id = oo.ordermenu_id");
			query.append(" )as D");
			query.append(" )+(");
			query.append(" select sum2 from (select sum(om.price) as sum2 from orderlist as ol inner join ordermenu as om on ol.orderlist_id = om.orderlist_id) as B");
			query.append(" )");
			query.append(" where ol1.orderlist_id  = (");
			query.append(" select idid from (");
			query.append(" select distinct ol3.orderlist_id as idid from orderlist as ol3 where user_id = ? and status = 0"); //유저아이디 입력
			query.append(" ) as C");
			query.append(" );");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			if(pstmt.executeUpdate() > 0){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("장바구니 추가 에러");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//
}
