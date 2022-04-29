package com.lunchforce.store;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import com.lunchforce.dbconnect.JDBConnect;

public class MenuOptionDAO extends JDBConnect{
	private static MenuOptionDAO menuOptionDAO = new MenuOptionDAO();
	
	//생성자
	private MenuOptionDAO() {
		
	}
	
	//인스턴스 getter
	public static MenuOptionDAO getInstance() {
		if (menuOptionDAO == null) {
			menuOptionDAO = new MenuOptionDAO();
		}
		return menuOptionDAO;
	}
	
	//method
	
	//메뉴의 옵션 추가 - 자동으로 맨 뒤에 추가됨
	public synchronized boolean addOption(MenuOptionDTO menuOptionDTO) throws SQLException{
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			StringBuffer query2 = new StringBuffer();
			int rows = 0; //이미 있는 옵션의 갯수를 카운팅 하는 변수
			
			query2.append("SELECT id FROM menuoption WHERE menu_id = " + menuOptionDTO.getMenuId());
			rs = stmt.executeQuery(query2.toString());
			
			while(rs.next()) {
				rows++;
			}
			
			stmt = conn.createStatement();
			query.append("INSERT INTO menuoption ");
			query.append("VAEUS(?,?,?,?,?)");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(2, menuOptionDTO.getMenuId());
			pstmt.setString(3, menuOptionDTO.getOptionName());
			pstmt.setInt(4, rows);
			pstmt.setInt(5, menuOptionDTO.getPrice());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			
			disconnectPstmt();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnectStmt();
			disconnectPstmt();
		}
	}
	
	//메뉴의 옵션 삭제 - 해당 순서 삭제되면 차례로 앞으로 땡겨짐!
	
	//메뉴의 옵션 순서 변경
	
	//메뉴의 옵션 가져오기(return DTO)
	
	//모든 메뉴 LinkedHash로 가져오기
	public synchronized ArrayList<MenuOptionDTO> getOptionList(int menuId) throws SQLException{
		ArrayList<MenuOptionDTO> list = new ArrayList<MenuOptionDTO>();
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();
			query.append("SELECT * from menuoption ");
			query.append("WHERE menu_id = " + menuId + " order by option_number");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while(rs.next()){
				MenuOptionDTO dto = new MenuOptionDTO();
				dto.setId(rs.getInt("id"));
				dto.setMenuId(rs.getInt("menu_id"));
				dto.setOptionName(rs.getString("option_name"));
				dto.setOptionNumber(rs.getInt("option_number"));
				dto.setPrice(rs.getInt("price"));
				
				list.add(dto);
				cnt++;
			}
			if(cnt == 0) {
				return list;
			}
			return list;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("메뉴옵션전부가져오기 오류");
			return null;
		}finally {
			disconnectStmt();
		}
	}
}
