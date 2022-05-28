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
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query2.toString());
			
			while(rs.next()) {
				rows++;
			}
		
			query = new StringBuffer();
			query.append("INSERT INTO menuoption (`menu_id`, `option_name`, `option_number`, `price`) ");
			query.append("VALUES(?,?,?,?)");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, menuOptionDTO.getMenuId());
			pstmt.setString(2, menuOptionDTO.getOptionName());
			pstmt.setInt(3, rows + 1);
			pstmt.setInt(4, menuOptionDTO.getPrice());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			
			disconnectPstmt();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//메뉴의 옵션 삭제
	public synchronized boolean delOption(int optionId, int menuId) throws SQLException{
		try {

			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM menuoption ");
			query.append("WHERE menu_id = " + menuId + " and id = " + optionId);

			pstmt = conn.prepareStatement(query.toString());

			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("옵션삭제 에러");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//메뉴의 옵션 순서 변경
	
	//메뉴의 옵션 가져오기(return DTO)
	public synchronized MenuOptionDTO getOptionInfo(int optionId) throws SQLException{
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			
			query.append("SELECT * FROM menuoption ");
			query.append("WHERE id = ?"); //optionId
			
			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setInt(1, optionId);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			MenuOptionDTO moDTO = new MenuOptionDTO();
			while(rs.next()) {
				moDTO.setId(rs.getInt("id"));
				moDTO.setMenuId(rs.getInt("menu_id"));
				moDTO.setOptionName(rs.getString("option_name"));
				moDTO.setOptionNumber(rs.getInt("option_number"));
				moDTO.setPrice(rs.getInt("price"));
			}
			return moDTO;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("옵션정보 가져오기 오류 " + e.getMessage());
			return null;
		}
	}
	
	//모든 메뉴 ArrayList로 가져오기
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
