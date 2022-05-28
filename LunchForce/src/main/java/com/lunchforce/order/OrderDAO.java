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
	public synchronized boolean addShopping(String userId, int storeId, int menuId, int[] optionId)
			throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			// 장바구니가 이미 있는지 없는지 체크
			query.append("SELECT orderlist_id from orderlist where user_id = '" + userId + "'");
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			int cnt1 = 0;
			int orderId = 0;
			while (rs.next()) {
				cnt1++;
				orderId = rs.getInt("orderlist_id");
			}
			disconnectPstmt();
			if (cnt1 == 1) { // 장바구니가 이미 있으면
				// 다른 가게인지 체크
				query = new StringBuffer();
				query.append("SELECT orderlist_id from orderlist where store_id = " + storeId);
				pstmt = conn.prepareStatement(query.toString());
				rs = pstmt.executeQuery();
				int cnt2 = 0;

				while (rs.next()) {
					cnt2++;

				}
				disconnectPstmt();
				if (cnt2 != 1) {// 다른 가게이면
					// 기존에 있던 장바구니 삭제
					query = new StringBuffer();
					query.append("DELETE FROM orderlist ");
					query.append("WHERE orderlist_id= " + orderId);
					pstmt = conn.prepareStatement(query.toString());
					rs = pstmt.executeQuery();
					disconnectPstmt();
					// 새로운 장바구니 추가
					query = new StringBuffer();
					query.append("INSERT INTO orderlist (`user_Id`,`store_id`,`order_date`,`price`,`status`,`time`) ");
					query.append("VALUES(?,?,?,?,?,?)");
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, userId);
					pstmt.setInt(2, storeId);
					// 타임스탬프
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
			} else {// 장바구니가 없으면
					// 장바구니 새로 추가
				query = new StringBuffer();
				query.append("INSERT INTO orderlist (`user_Id`,`store_id`,`order_date`,`price`,`status`,`time`) ");
				query.append("VALUES(?,?,?,?,?,?)");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, userId);
				pstmt.setInt(2, storeId);
				// 타임스탬프
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
			// 장바구니에 추가한 내용 업데이트
			// 1. ordermenu 추가하기
			try {
				query = new StringBuffer();
				query.append("INSERT INTO ordermenu (`orderlist_id`,`menu_id`,`menu_name`,`price`) ");
				query.append("VALUES(");
				query.append("(select orderlist_id from orderlist where user_id = ? and status = 0),");
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
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("1. ordermenu 추가하기" + e.getMessage());
			}
			// 2. orderoption 추가하기
			if (optionId.length > 0) { // 들어온 옵션이 1개 이상이면 수행

				try {
					for (int i = 0; i < optionId.length; i++) {
						query = new StringBuffer();
						query.append("INSERT INTO orderoption (`ordermenu_id`,`option_id`,`option_name`,`price`) ");
						query.append("VALUES(");
						query.append(
								"(select * from (select ordermenu.id from orderlist inner join ordermenu on orderlist.orderlist_id = ordermenu.orderlist_id ");
						query.append("left join orderoption on orderoption.ordermenu_id = ordermenu.id ");
						query.append("and(orderlist.user_id = ? and orderlist.status = 0 and orderoption.ordermenu_id = null and ordermenu.menu_id = ?) limit 1) as a), ");
						//userId, menuId
						query.append("(select * from(select id from menuoption where id = ? and menu_id = ? limit 1) as b),");
						//optionId[i], menuId
						query.append(
								"(select * from(select option_name from menuoption where id = ? and menu_id = ? limit 1) as c),");
						//optionId[i], menuId
						query.append(
								"(select * from(select price from menuoption where id = ? and menu_id = ? limit 1) as d)) ");
						//optionId[i], menuId
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
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("2.orderoption 추가하기" + e.getMessage());
				}
			}
			// 3. order 테이블의 값 수정하기(가격계산해서)
			try {
				query = new StringBuffer();
				query.append(" update orderlist as ol1 set ");
				query.append(" price = ( ");
				query.append(" select sum1 from(");
				query.append(" select ifnull(sum(oo.price),0) as sum1 from (");
				query.append(
						" select om.id  from orderlist as ol left join ordermenu as om on ol.orderlist_id = om.orderlist_id and (ol.user_id=? and status = '0')"); // 유저아이디
																																									// 입력
				query.append(" )as A inner join orderoption as oo on A.id = oo.ordermenu_id");
				query.append(" )as D");
				query.append(" )+(");
				query.append(
						" select sum2 from (select sum(om.price) as sum2 from orderlist as ol inner join ordermenu as om on ol.orderlist_id = om.orderlist_id) as B");
				query.append(" )");
				query.append(" where ol1.orderlist_id  = (");
				query.append(" select idid from (");
				query.append(
						" select distinct ol3.orderlist_id as idid from orderlist as ol3 where user_id = ? and status = 0"); // 유저아이디
																																// 입력
				query.append(" ) as C");
				query.append(" );");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, userId);
				pstmt.setString(2, userId);
				if (pstmt.executeUpdate() > 0) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				System.out.println("3. order 테이블의 값 수정하기(가격계산해서)" + e.getMessage());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("장바구니 추가 에러" + e.getMessage());
			return false;
		} finally {

			disconnectPstmt();
		}
	}

	// orderDTO 가져오기 status:0 장바구니 status:1 주문대기 status:2 예약완료

	public synchronized OrderDTO getOrder(String userId, int status) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("select * from orderlist where user_id = ? and status = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, userId);
			pstmt.setInt(2, status);

			OrderDTO orderDTO = new OrderDTO();

			rs = pstmt.executeQuery();

			int cnt = 0;

			while (rs.next()) {
				orderDTO.setOrderlistId(rs.getInt("orderlist_id"));
				orderDTO.setUserId(rs.getString("user_id"));
				orderDTO.setStoreId(rs.getInt("store_id"));
				orderDTO.setOrderDate(rs.getDate("order_date"));
				orderDTO.setPrice(rs.getInt("price"));
				orderDTO.setStatus(rs.getInt("status"));
				orderDTO.setTime(rs.getInt("time"));
				cnt++;
			}

			if (cnt == 0) {
				return null;
			}
			return orderDTO;
		} catch (Exception e) {
			System.out.println("orderDTO가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 장바구니 내용을 가져오는 2차원 ArrayList
	// index 0 : ordermenu.id
	// index 1~ : orderoption.id
	public synchronized ArrayList<ArrayList<Integer>> getOrderList(String userId, int status)
			throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			// rs.next() 해서 menu_id가 다른게 나올 때까지 해당하는 key값에 arrayList안에 option_id들을 밀어넣음
			query.append("Select om.id as ordermenu_id, oo.id as orderoption_id ");
			query.append("FROM orderlist as ol INNER JOIN ordermenu as om ON ol.orderlist_id = om.orderlist_id ");
			query.append("LEFT JOIN orderoption as oo ON om.id = oo.ordermenu_id ");
			query.append("AND (ol.user_id = ? AND ol.status = ?)");
			//userId, status
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, userId);
			pstmt.setInt(2, status);

			ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
			rs = pstmt.executeQuery();

			int cnt = 0;
			while (rs.next()) {
				int tempOrdermenuId = rs.getInt("ordermenu_id");
				int temp = 0;
				//만약에 list안에 해당 ordermenu_id가 있으면 수행
				
				if(list.size() == 0) {//list 안에 들어간게 없는 경우
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					tempList.add(rs.getInt("ordermenu_id")); //ordermenu_id를 삽입
					//만약 딸린 옵션이 있으면 옵션도 삽입
					temp = 0;
					temp = rs.getInt("orderoption_id");
					if(temp > 0) {
						tempList.add(temp);
					}
					//list에 임시 arraylist를 삽입
					list.add(tempList);
				}else { //아닌경우
					for(int i = 0; i < list.size(); i++) {
						//list 안에 해당 ordermenu_id가 있는지 체크
						ArrayList<Integer> tempList = list.get(i);
						if(tempList.get(0) == tempOrdermenuId) {
							//있으면 옵션이 있을 때 해당 옵션 삽입
							temp = 0;
							temp = rs.getInt("orderoption_id");
							if(temp > 0) {
								tempList.add(temp);
								
							}
							break;
						}
						if(i == (list.size() - 1)){ //끝까지 돌았는데 ordermenu_id가 없으면
							tempList = new ArrayList<Integer>(); //새로운 arraylist 삽입
							tempList.add(rs.getInt("ordermenu_id"));
							//옵션이 있을 때 해당 옵션 삽입
							temp = 0;
							temp = rs.getInt("orderoption_id");
							if(temp > 0) {
								tempList.add(temp);
							}
							list.add(tempList);
							break;
						}
					}
				}
				
				cnt++;
			}
			
			//test
			for(ArrayList<Integer> a : list) {
				for(Integer b : a) {
					System.out.print(b + " ");
				}
				System.out.println();
			}

			if (cnt == 0) {
				return null;
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("주문내용 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}
	
	//ordermenu_id로 메뉴의 이름과 가격을 가져오는 메서드
	public synchronized ArrayList<String> getMenuInfo(int ordermenuId) throws SQLException{
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			
			query.append("Select m.menu_name, m.price FROM menu as m ");
			query.append("INNER JOIN ordermenu as om ON m.menu_id = om.menu_id ");
			query.append("AND om.id = ? ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setInt(1, ordermenuId);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()) {
				list.add(rs.getString("menu_name"));
				list.add(String.valueOf(rs.getInt("price")));
				cnt++;
			}
			if(cnt > 0) {
				return list;
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ordermenuId로 메뉴이름 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}
	
	//orderoption_id로 메뉴의 이름과 가격을 가져오는 메서드
		public synchronized ArrayList<String> getOptionInfo(int orderoptionId) throws SQLException{
			try {
				conn = dbConn.getConn();
				query = new StringBuffer();
				
				query.append("Select mo.option_name, mo.price FROM menuoption as mo ");
				query.append("INNER JOIN orderoption as oo ON mo.id = oo.option_id ");
				query.append("AND oo.id = ? ");
				
				pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setInt(1, orderoptionId);
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				ArrayList<String> list = new ArrayList<String>();
				while(rs.next()) {
					list.add(rs.getString("option_name"));
					list.add(String.valueOf(rs.getInt("price")));
					cnt++;
				}
				if(cnt > 0) {
					return list;
				}
				
				return null;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("orderoption_id로 옵션이름 가져오기 오류 " + e.getMessage());
				return null;
			} finally {
				disconnectPstmt();
			}
		}
	
	//ordermenu 제거
	public synchronized boolean delOrdermenu(int ordermenuId, String userId) throws SQLException{
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			
			query.append("DELETE om FROM ordermenu as om ");
			query.append("INNER JOIN orderlist as ol ");
			query.append("ON ol.orderlist_id = om.orderlist_id ");
			query.append("AND (ol.user_id = ? AND om.id = ?) ");
			
			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(1, userId);
			pstmt.setInt(2, ordermenuId);
			
			
			
			if(pstmt.executeUpdate() == 0) {
				updateOrderlist(userId);
				return false;
			}
			updateOrderlist(userId);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ordermenu 제거 오류 " + e.getMessage());
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//orderoption 제거
	public synchronized boolean delOrderoption(int orderoptionId, String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("DELETE oo FROM orderoption as oo ");
			query.append("INNER JOIN ordermenu as om ");
			query.append("ON oo.ordermenu_id = om.id ");
			query.append("INNER JOIN orderlist as ol ");
			query.append("ON ol.orderlist_id = om.orderlist_id ");
			query.append("AND (ol.user_id = ? AND oo.id = ?) ");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, userId);
			pstmt.setInt(2, orderoptionId);

			if (pstmt.executeUpdate() == 0) {
				updateOrderlist(userId);
				return false;
			}

			updateOrderlist(userId);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("orderoption 제거 오류 " + e.getMessage());
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//orderlist 계산하기
		public synchronized boolean updateOrderlist(String userId) throws SQLException {
			try {
				conn = dbConn.getConn();
				pstmt = conn.prepareStatement(userId);
				query = new StringBuffer();
				query.append(" update orderlist as ol1 set ");
				query.append(" price = ( ");
				query.append(" select sum1 from(");
				query.append(" select ifnull(sum(oo.price),0) as sum1 from (");
				query.append(
						" select om.id  from orderlist as ol left join ordermenu as om on ol.orderlist_id = om.orderlist_id and (ol.user_id=? and status = '0')"); // 유저아이디
																																									// 입력
				query.append(" )as A inner join orderoption as oo on A.id = oo.ordermenu_id");
				query.append(" )as D");
				query.append(" )+(");
				query.append(
						" select sum2 from (select sum(om.price) as sum2 from orderlist as ol inner join ordermenu as om on ol.orderlist_id = om.orderlist_id) as B");
				query.append(" )");
				query.append(" where ol1.orderlist_id  = (");
				query.append(" select idid from (");
				query.append(
						" select distinct ol3.orderlist_id as idid from orderlist as ol3 where user_id = ? and status = 0"); // 유저아이디
																																// 입력
				query.append(" ) as C");
				query.append(" );");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, userId);
				pstmt.setString(2, userId);
				
				if(pstmt.executeUpdate() > 0) {
					return true;
				}
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("orderlist 계산 오류 " + e.getMessage());
				return false;
			} finally {
				disconnectPstmt();
			}
		}
}
