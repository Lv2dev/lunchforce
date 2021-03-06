package com.lunchforce.order;

import com.lunchforce.dbconnect.JDBConnect;
import com.lunchforce.store.MenuDTO;
import com.lunchforce.store.MenuOptionDAO;
import com.lunchforce.store.MenuOptionDTO;
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
			query.append("SELECT orderlist_id from orderlist where user_id = ? AND status = 0");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
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
				query.append("SELECT orderlist_id from orderlist where store_id = ? and status = 0");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setInt(1, storeId);
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
					pstmt.executeUpdate();
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
					query = new StringBuffer();
					query.append("set @tempMenu = (select a.id from orderlist as ol inner join ( ");
					query.append(
							"select om.id as id, om.orderlist_id as orderlist_id, om.menu_id as menu_id from ordermenu as om left join orderoption as oo  ");
					query.append("on om.id = oo.ordermenu_id where oo.id is null) as a ");
					query.append(
							"on ol.orderlist_id = a.orderlist_id and (ol.user_id = ? and ol.status = 0 and a.menu_id = ?) limit 1)");

					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, userId);
					pstmt.setInt(2, menuId);
					pstmt.executeUpdate();

					query = new StringBuffer();
					query.append("INSERT INTO orderoption (`ordermenu_id`,`option_id`,`option_name`,`price`) ");
					query.append("VALUES ");
					for (int i = 0; i < optionId.length; i++) {
						query.append("((@tempMenu), ");
						// userId, menuId
						query.append(
								"(select * from(select id from menuoption where id = ? and menu_id = ? limit 1) as b),");
						// optionId[i], menuId
						query.append(
								"(select * from(select option_name from menuoption where id = ? and menu_id = ? limit 1) as c),");
						// optionId[i], menuId
						query.append(
								"(select * from(select price from menuoption where id = ? and menu_id = ? limit 1) as d)) ");
						// optionId[i], menuId
						if (optionId.length - 1 > i) {
							query.append(" , ");
						}
						System.out.println("빙빙돌아가는 쿼리 " + i);
					}
					pstmt = conn.prepareStatement(query.toString());
					int j = 0;
					for (int i = 0; i < optionId.length; i++) {
						System.out.println("빙빙돌아가는 pstmt " + i);
						pstmt.setInt(j + 1, optionId[i]);
						pstmt.setInt(j + 2, menuId);
						pstmt.setInt(j + 3, optionId[i]);
						pstmt.setInt(j + 4, menuId);
						pstmt.setInt(j + 5, optionId[i]);
						pstmt.setInt(j + 6, menuId);

						j += 6;
					}
					pstmt.executeUpdate();
					pstmt.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("2.orderoption 추가하기" + e.getMessage());
				}
			}
			// 3. order 테이블의 값 수정하기(가격계산해서)
			try {
				updateOrderlist(userId);
				return true;
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

	// orderDTO 가져오기 status:0 장바구니 status:1 주문대기 status:2 예약완료 status:3 주문취소

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
				orderDTO.setOrderDate(rs.getTimestamp("order_date"));
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
	public synchronized ArrayList<ArrayList<Object>> getOrderList(String userId, int status) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			// rs.next() 해서 menu_id가 다른게 나올 때까지 해당하는 key값에 arrayList안에 option_id들을 밀어넣음
			query.append(
					"select temp1.menu_name as menu_name, temp1.menu_id as menu_id, temp1.menu_price as menu_price, temp2.option_name as option_name, temp2.option_id as option_id, temp2.option_price as option_price ");
			query.append("from ( ");
			query.append(
					"select omm.ordermenu_id, m.menu_id, m.menu_name, m.price as menu_price from menu as m inner join (select om.id as ordermenu_id, om.menu_id from orderlist as ol inner join ordermenu as om on ol.orderlist_id = om.orderlist_id where ol.user_id = 'ilban' and ol.status = 0) as omm on m.menu_id = omm.menu_id ");
			query.append(") as temp1 ");
			query.append(
					"left join (select o.price as option_price, o.option_name as option_name, o.id as option_id, oo.ordermenu_id as ordermenu_id from menuoption as o inner join orderoption as oo on o.id = oo.option_id) as temp2 ");
			query.append("on temp1.ordermenu_id = temp2.ordermenu_id ");
			// userId, status
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, userId);
			pstmt.setInt(2, status);

			ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 1. list에 아무것도 없는 경우
				if (list.size() == 0) {
					ArrayList<Object> tempList = new ArrayList<Object>();
					// 1-1. menu 정보 넣기
					MenuDTO menuDTO = new MenuDTO();
					menuDTO.setMenuId(rs.getInt("menu_id"));
					menuDTO.setMenuName(rs.getString("menu_name"));
					menuDTO.setPrice(rs.getInt("menu_price"));
					tempList.add(menuDTO);
					int temp = 0;
					temp = rs.getInt("option_id");
					if (temp != 0) { // 1-2. option이 들어있으면 옵션정보 넣기
						MenuOptionDTO moDTO = new MenuOptionDTO();
						moDTO.setId(rs.getInt("option_id"));
						moDTO.setOptionName(rs.getString("option_name"));
						moDTO.setPrice(rs.getInt("option_price"));
						tempList.add(moDTO);
					}
				} else { // 뭔가 있을 때
					int i = 0;
					for (ArrayList<Object> each : list) { // 첫번째 들어간 menuDTO의 id와 검사해서 같은게 있는지 비교
						MenuDTO tempDTO = (MenuDTO) each.get(0);
						if (tempDTO.getMenuId() == rs.getInt("menu_id")) {
							break;
						}
						i++;
					}
					if (i >= list.size()) { // 같은게 없으면
						// 새로운 menu + option 을 삽입
						// 1. menu 정보 넣기
						ArrayList<Object> tempList = new ArrayList<Object>();
						MenuDTO menuDTO = new MenuDTO();
						menuDTO.setMenuId(rs.getInt("menu_id"));
						menuDTO.setMenuName(rs.getString("menu_name"));
						menuDTO.setPrice(rs.getInt("menu_price"));
						tempList.add(menuDTO);
						int temp = 0;
						temp = rs.getInt("option_id");
						if (temp != 0) { // 1-2. option이 들어있으면 옵션정보 넣기
							MenuOptionDTO moDTO = new MenuOptionDTO();
							moDTO.setId(rs.getInt("option_id"));
							moDTO.setOptionName(rs.getString("option_name"));
							moDTO.setPrice(rs.getInt("option_price"));
							tempList.add(moDTO);
						}
					} else { // 같은게 있으면
						// 그 위치의 ArrayList를 가져와서 option만 삽입
						MenuOptionDTO modto = new MenuOptionDTO();
						modto.setId(rs.getInt("option_id"));
						modto.setOptionName(rs.getString("option_name"));
						modto.setPrice(rs.getInt("option_price"));
						list.get(i).add(modto);
					}
				}
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

	// ordermenu_id로 메뉴의 이름과 가격을 가져오는 메서드
	public synchronized ArrayList<String> getMenuInfo(int ordermenuId) throws SQLException {
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
			while (rs.next()) {
				list.add(rs.getString("menu_name"));
				list.add(String.valueOf(rs.getInt("price")));
				cnt++;
			}
			if (cnt > 0) {
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

	// orderoption_id로 메뉴의 이름과 가격을 가져오는 메서드
	public synchronized ArrayList<String> getOptionInfo(int orderoptionId) throws SQLException {
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
			while (rs.next()) {
				list.add(rs.getString("option_name"));
				list.add(String.valueOf(rs.getInt("price")));
				cnt++;
			}
			if (cnt > 0) {
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

	// ordermenu 제거
	public synchronized boolean delOrdermenu(int ordermenuId, String userId) throws SQLException {
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

			if (pstmt.executeUpdate() == 0) {
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

	// orderoption 제거
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

	// orderlist 계산하기
	public synchronized boolean updateOrderlist(String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			pstmt = conn.prepareStatement(userId);
			query = new StringBuffer();

			query.append(" update orderlist set price = ( ");
			query.append(" select sum(ifnull(temp2.option_price, 0)) + sum(temp1.menu_price) as sumsum from ( ");
			query.append(
					" select omm.menu_price, omm.ordermenu_id, omm.orderlist_id from orderlist as ol inner join ( ");
			query.append(
					" select m.price as menu_price, om.id as ordermenu_id, om.orderlist_id from menu as m inner join ordermenu as om on m.menu_id = om.menu_id ");
			query.append(" ) as omm ");
			query.append(" on ol.orderlist_id = omm.orderlist_id ");
			query.append(" where ol.user_id = ? AND ol.status = 0 ");
			query.append(" ) as temp1 ");
			query.append(
					" left join (select oo.ordermenu_id, o.price as option_price from menuoption as o inner join orderoption as oo on oo.option_id = o.id) as temp2 on temp1.ordermenu_id = temp2.ordermenu_id ");
			query.append(" ) ");
			query.append(" where user_id = ? AND status = 0; ");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);

			if (pstmt.executeUpdate() > 0) {
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

	// orderlist의 status를 변경하는 메서드
	public synchronized boolean setState(String userId, int status) throws SQLException {
		try {
			conn = dbConn.getConn();
			StringBuffer query = new StringBuffer();
			query.append("update orderlist set ");
			query.append("status = ?, "); // status
			query.append("order_date = ? "); // 날짜
			query.append("where user_id = ? and status = 0"); // userId

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, 1);

			// 타임스탬프
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			String today = null;
			today = formatter.format(cal.getTime());
			Timestamp ts = Timestamp.valueOf(today);
			pstmt.setTimestamp(2, ts);

			pstmt.setString(3, userId);

			if (pstmt.executeUpdate() == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("orderlist의 status 변경 오류 " + e.getMessage());
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 우리 가게에 들어온 수락되지 않은 주문 현황을 2차원 ArrayList으로 가져오는 메서드 - 들어온 시간이 오래된 순으로 정렬 -
	// status : 1
	// 장바구니 내용을 가져오는 2차원 ArrayList
	// index 0 : ordermenu.id
	// index 1~ : orderoption.id
	public synchronized ArrayList<ArrayList<Integer>> getStoreOrderList(int storeId, int status, int orderlistId)
			throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			// rs.next() 해서 menu_id가 다른게 나올 때까지 해당하는 key값에 arrayList안에 option_id들을 밀어넣음
			query.append("Select om.id as ordermenu_id, oo.id as orderoption_id ");
			query.append("FROM orderlist as ol INNER JOIN ordermenu as om ON ol.orderlist_id = om.orderlist_id ");
			query.append("AND (ol.store_id = ? AND ol.status = ? AND ol.orderlist_id = ?) ");
			query.append("LEFT JOIN orderoption as oo ON om.id = oo.ordermenu_id ");

			// userId, status
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, storeId);
			pstmt.setInt(2, status);
			pstmt.setInt(3, orderlistId);

			ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
			rs = pstmt.executeQuery();

			int cnt = 0;
			while (rs.next()) {
				int tempOrdermenuId = rs.getInt("ordermenu_id");
				int temp = 0;
				// 만약에 list안에 해당 ordermenu_id가 있으면 수행

				if (list.size() == 0) {// list 안에 들어간게 없는 경우
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					tempList.add(rs.getInt("ordermenu_id")); // ordermenu_id를 삽입
					// 만약 딸린 옵션이 있으면 옵션도 삽입
					temp = 0;
					temp = rs.getInt("orderoption_id");
					if (temp > 0) {
						tempList.add(temp);
					}
					// list에 임시 arraylist를 삽입
					list.add(tempList);
				} else { // 아닌경우
					for (int i = 0; i < list.size(); i++) {
						// list 안에 해당 ordermenu_id가 있는지 체크
						ArrayList<Integer> tempList = list.get(i);
						if (tempList.get(0) == tempOrdermenuId) {
							// 있으면 옵션이 있을 때 해당 옵션 삽입
							temp = 0;
							temp = rs.getInt("orderoption_id");
							if (temp > 0) {
								tempList.add(temp);

							}
							break;
						}
						if (i == (list.size() - 1)) { // 끝까지 돌았는데 ordermenu_id가 없으면
							tempList = new ArrayList<Integer>(); // 새로운 arraylist 삽입
							tempList.add(rs.getInt("ordermenu_id"));
							// 옵션이 있을 때 해당 옵션 삽입
							temp = 0;
							temp = rs.getInt("orderoption_id");
							if (temp > 0) {
								tempList.add(temp);
							}
							list.add(tempList);
							break;
						}
					}
				}

				cnt++;
			}

			// test
			for (ArrayList<Integer> a : list) {
				for (Integer b : a) {
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
			System.out.println("가게 주문내용 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 가게에 들어온 orderDTO를 List로 가져오는 메서드
	public synchronized ArrayList<OrderDTO> getStoreOrderDTOList(int storeId, int status) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from orderlist ");
			query.append("where store_id = ? AND status = ? order by order_date");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, storeId);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			int cnt = 0;

			ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();
			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderlistId(rs.getInt("orderlist_id"));
				orderDTO.setUserId(rs.getString("user_id"));
				orderDTO.setStoreId(rs.getInt("store_id"));
				orderDTO.setOrderDate(rs.getTimestamp("order_date"));
				orderDTO.setPrice(rs.getInt("price"));
				orderDTO.setStatus(rs.getInt("status"));
				orderDTO.setTime(rs.getInt("time"));
				list.add(orderDTO);
				cnt++;
			}

			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가게에들어온 주문을 list로 가져오는 메서드 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 가게에 들어온 orderDTO를 단일로 가져오는 메서드
	public synchronized OrderDTO getStoreOrderDTO(int storeId, int status, int orderlistId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from orderlist ");
			query.append("where store_id = ? AND status = ? AND orderlist_id = ? order by order_date");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, storeId);
			pstmt.setInt(2, status);
			pstmt.setInt(3, orderlistId);
			rs = pstmt.executeQuery();
			int cnt = 0;

			OrderDTO orderDTO = new OrderDTO();
			while (rs.next()) {
				orderDTO.setOrderlistId(rs.getInt("orderlist_id"));
				orderDTO.setUserId(rs.getString("user_id"));
				orderDTO.setStoreId(rs.getInt("store_id"));
				orderDTO.setOrderDate(rs.getTimestamp("order_date"));
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
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가게에들어온 주문을 단일로 가져오는 메서드 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 내 주문의 orderDTO를 List로 가져오는 메서드
	public synchronized ArrayList<OrderDTO> getOrderDTOList(String userId, int status) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from orderlist ");
			query.append("where user_id = ? AND status = ? order by order_date");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			int cnt = 0;

			ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();
			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderlistId(rs.getInt("orderlist_id"));
				orderDTO.setUserId(rs.getString("user_id"));
				orderDTO.setStoreId(rs.getInt("store_id"));
				orderDTO.setOrderDate(rs.getTimestamp("order_date"));
				orderDTO.setPrice(rs.getInt("price"));
				orderDTO.setStatus(rs.getInt("status"));
				orderDTO.setTime(rs.getInt("time"));
				list.add(orderDTO);
				cnt++;
			}

			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("내 주문을 list로 가져오는 메서드 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 들어온 주문의 상태를 변경하는 메서드
	public synchronized boolean setStoreOrderState(int storeId, int orderlistId, int status, int time)
			throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("update orderlist set ");
			query.append("status = ?,  "); // status
			query.append("time = ? "); // time
			query.append("where store_id = ? AND orderlist_id = ?"); // storeId, orderlistId

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, status);
			pstmt.setInt(2, time);
			pstmt.setInt(3, storeId);
			pstmt.setInt(4, orderlistId);

			if (pstmt.executeUpdate() == 0) {
				return false;
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가게에 들어온 주문의 상태를 변경하는 메서드 오류 " + e.getMessage());
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 총 주문 량을 가져오는 메서드
	public synchronized int getOrderCount(String userId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("select orderlist_id from orderlist where user_id = ?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			int cnt = 0;

			while (rs.next()) {
				cnt++;
			}

			return cnt;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("사용자의 총 주문량 가져오기 오류 " + e.getMessage());
			return 0;
		} finally {
			disconnectPstmt();
		}
	}

	// 현재 페이지에 해당하는 주문 목록 가져오는 메서드
	public synchronized ArrayList<OrderDTO> getPagingOrderlist(String userId, int page, int pageCount)
			throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			query.append("select * from ( ");
			query.append(
					"select ol.orderlist_id, ol.user_id, ol.store_id, ol.order_date, ol.price, ol.status, ol.time, st.store_name ,row_number() over(order by store_name) as num ");
			query.append("from orderlist as ol inner join store as st on ol.store_id = st.store_id ");
			query.append("AND (ol.user_id = ? AND ol.status > 0) order by order_date) as temp "); // userId
			query.append("where temp.num > ? order by temp.num limit ? "); // (page - 1) * pageCount, pageCount

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, userId);
			pstmt.setInt(2, (page - 1) * pageCount);
			pstmt.setInt(3, pageCount);

			rs = pstmt.executeQuery();

			ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();
			while (rs.next()) {
				OrderDTO dto = new OrderDTO();
				dto.setOrderlistId(rs.getInt("orderlist_id"));
				dto.setUserId(rs.getString("user_id"));
				dto.setStoreId(rs.getInt("store_id"));
				dto.setOrderDate(rs.getTimestamp("order_date"));
				dto.setPrice(rs.getInt("price"));
				dto.setStatus(rs.getInt("status"));
				dto.setTime(rs.getInt("time"));
				dto.setStoreName(rs.getString("store_name"));
				list.add(dto);
			}

			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("페이징 된 주문목록 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 완료되거나 취소된 주문 가져오기 (가게)
	public synchronized ArrayList<OrderDTO> getStoreCompleteOrderList(int storeId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append(
					"select * from orderlist where store_id = ? and ( status = 2 or status = 3 ) order by order_date ");

			pstmt = conn.prepareStatement(query.toString());

			rs = pstmt.executeQuery();

			ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();

			while (rs.next()) {
				OrderDTO i = new OrderDTO();
				i.setOrderlistId(rs.getInt("orderlist_id"));
				i.setUserId(rs.getString("user_id"));
				i.setStoreId(rs.getInt("store_id"));
				i.setOrderDate(rs.getTimestamp("order_date"));
				i.setPrice(rs.getInt("price"));
				i.setStatus(rs.getInt("status"));
				i.setTime(rs.getInt("time"));
				list.add(i);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("완료된 주문 가져오기 (가게) 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// orderId로 주문정보 가져오기
	public synchronized OrderDTO getStoreOrderDTO(int orderId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from orderlist where orderlist_id = ?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, orderId);

			rs = pstmt.executeQuery();

			OrderDTO dto = new OrderDTO();

			while (rs.next()) {
				dto.setOrderlistId(rs.getInt("orderlist_id"));
				dto.setUserId(rs.getString("user_id"));
				dto.setStoreId(rs.getInt("store_id"));
				dto.setOrderDate(rs.getTimestamp("order_date"));
				dto.setPrice(rs.getInt("price"));
				dto.setStatus(rs.getInt("status"));
				dto.setTime(rs.getInt("time"));
			}

			return dto;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("orderId 하나로 주문정보 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// OrderId 하나로 장바구니 내용을 가져오는 2차원 ArrayList
	// index 0 : ordermenu.id
	// index 1~ : orderoption.id
	public synchronized ArrayList<ArrayList<Object>> getOrderList(int orderId) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();

			// rs.next() 해서 menu_id가 다른게 나올 때까지 해당하는 key값에 arrayList안에 option_id들을 밀어넣음
			query.append(
					"select temp1.menu_name as menu_name, temp1.menu_id as menu_id, temp1.menu_price as menu_price, temp2.option_name as option_name, temp2.option_id as option_id, temp2.option_price as option_price ");
			query.append("from ( ");
			query.append(
					"select omm.ordermenu_id, m.menu_id, m.menu_name, m.price as menu_price from menu as m inner join (select om.id as ordermenu_id, om.menu_id from orderlist as ol inner join ordermenu as om on ol.orderlist_id = om.orderlist_id where ol.orderlist_id = ?) as omm on m.menu_id = omm.menu_id ");
			query.append(") as temp1 ");
			query.append(
					"left join (select o.price as option_price, o.option_name as option_name, o.id as option_id, oo.ordermenu_id as ordermenu_id from menuoption as o inner join orderoption as oo on o.id = oo.option_id) as temp2 ");
			query.append("on temp1.ordermenu_id = temp2.ordermenu_id ");
			// userId, status
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setInt(1, orderId);

			ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 1. list에 아무것도 없는 경우
				if (list.size() == 0) {
					ArrayList<Object> tempList = new ArrayList<Object>();
					// 1-1. menu 정보 넣기
					MenuDTO menuDTO = new MenuDTO();
					menuDTO.setMenuId(rs.getInt("menu_id"));
					menuDTO.setMenuName(rs.getString("menu_name"));
					menuDTO.setPrice(rs.getInt("menu_price"));
					tempList.add(menuDTO);
					int temp = 0;
					temp = rs.getInt("option_id");
					if (temp != 0) { // 1-2. option이 들어있으면 옵션정보 넣기
						MenuOptionDTO moDTO = new MenuOptionDTO();
						moDTO.setId(rs.getInt("option_id"));
						moDTO.setOptionName(rs.getString("option_name"));
						moDTO.setPrice(rs.getInt("option_price"));
						tempList.add(moDTO);
					}
				} else { // 뭔가 있을 때
					int i = 0;
					for (ArrayList<Object> each : list) { // 첫번째 들어간 menuDTO의 id와 검사해서 같은게 있는지 비교
						MenuDTO tempDTO = (MenuDTO) each.get(0);
						if (tempDTO.getMenuId() == rs.getInt("menu_id")) {
							break;
						}
						i++;
					}
					if (i >= list.size()) { // 같은게 없으면
						// 새로운 menu + option 을 삽입
						// 1. menu 정보 넣기
						ArrayList<Object> tempList = new ArrayList<Object>();
						MenuDTO menuDTO = new MenuDTO();
						menuDTO.setMenuId(rs.getInt("menu_id"));
						menuDTO.setMenuName(rs.getString("menu_name"));
						menuDTO.setPrice(rs.getInt("menu_price"));
						tempList.add(menuDTO);
						int temp = 0;
						temp = rs.getInt("option_id");
						if (temp != 0) { // 1-2. option이 들어있으면 옵션정보 넣기
							MenuOptionDTO moDTO = new MenuOptionDTO();
							moDTO.setId(rs.getInt("option_id"));
							moDTO.setOptionName(rs.getString("option_name"));
							moDTO.setPrice(rs.getInt("option_price"));
							tempList.add(moDTO);
						}
					} else { // 같은게 있으면
						// 그 위치의 ArrayList를 가져와서 option만 삽입
						MenuOptionDTO modto = new MenuOptionDTO();
						modto.setId(rs.getInt("option_id"));
						modto.setOptionName(rs.getString("option_name"));
						modto.setPrice(rs.getInt("option_price"));
						list.get(i).add(modto);
					}
				}
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("orderId 하나로 주문내용 가져오기 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}

	// 수락 대기중인 주문의 양 가져오기
	public synchronized int getStoreOrderDTOListCount(int storeId, int status) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from orderlist ");
			query.append("where store_id = ? AND status = ? order by order_date");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, storeId);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			int cnt = 0;

			while (rs.next()) {
				cnt++;
			}

			return cnt;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("수락 대기중인 주문의 양 가져오기 " + e.getMessage());
			return 0;
		} finally {
			disconnectPstmt();
		}
	}

	// 가게에 들어온 orderDTO를 페이징 된 List로 가져오는 메서드
	public synchronized ArrayList<OrderDTO> getStoreOrderDTOListpaging(int storeId, int status, int page, int pageCount) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("select * from ( ");
			query.append("select orderlist_id, user_id, store_id, order_date, price, status, time,  row_number() over(order by order_date desc) as num from orderlist ");
			query.append("where store_id = ? AND status = ? order by order_date");
			query.append(") as temp where num between ? and ? order by num");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, storeId);
			pstmt.setInt(2, status);
			pstmt.setInt(3, (page - 1) * pageCount);
			pstmt.setInt(4, ((page - 1) * pageCount) + pageCount - 1);
			rs = pstmt.executeQuery();
			int cnt = 0;

			ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();
			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderlistId(rs.getInt("orderlist_id"));
				orderDTO.setUserId(rs.getString("user_id"));
				orderDTO.setStoreId(rs.getInt("store_id"));
				orderDTO.setOrderDate(rs.getTimestamp("order_date"));
				orderDTO.setPrice(rs.getInt("price"));
				orderDTO.setStatus(rs.getInt("status"));
				orderDTO.setTime(rs.getInt("time"));
				list.add(orderDTO);
				cnt++;
			}

			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("가게에들어온 주문을 페이징된 list로 가져오는 메서드 오류 " + e.getMessage());
			return null;
		} finally {
			disconnectPstmt();
		}
	}
}
