package com.lunchforce.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.lunchforce.dbconnect.DBConnecter;
import com.lunchforce.dbconnect.JDBConnect;
import com.lunchforce.store.StoreDTO;

import java.sql.ResultSet;

//DB를 사용해 멤버정보 관리 기능을구현한 클래스
public class MemberDAO extends JDBConnect{
	private static MemberDAO memberDAO = new MemberDAO();

	// 생성자
	private MemberDAO() {

	}

	// getters and setters

	// 인스턴스 getter
	public static MemberDAO getInstance() {
		if (memberDAO == null) {
			memberDAO = new MemberDAO();
		}
		return memberDAO;
	}

	// method

	// 아이디 중복 체크, 중복없음 true, 중복있음 false;
	public synchronized boolean idCheck(MemberDTO memberDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT id FROM USER ");
			query.append("WHERE id = '" + memberDTO.getId() + "'");

			rs = stmt.executeQuery(query.toString());

			int cnt = 0;
			while (rs.next()) {
				cnt++;
			}

			if (cnt != 0) {
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("memberDAO_idcheck_error");
			return false;
		} finally {
			disconnectStmt();
		}
	}

	// 멤버 가입 성공 시 true, 실패 시 false
	public synchronized boolean joinMember(MemberDTO memberDTO) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("INSERT INTO user ");
			query.append("VAEUS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPw());
			pstmt.setString(3, memberDTO.getName());
			pstmt.setString(4, memberDTO.getNickname());
			pstmt.setString(5, memberDTO.getTel());
			pstmt.setString(6, memberDTO.getEmail());
			pstmt.setTimestamp(7, memberDTO.getbDay());
			pstmt.setTimestamp(8, memberDTO.getjDay());
			pstmt.setInt(9, memberDTO.getType());
			pstmt.setInt(10, memberDTO.getGender());

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
	//현재 비빌번호와 입력한 비밀번호 비교하는 메서드
	
	
	//비밀번호 수정
	public synchronized boolean replacePw(MemberDTO memberDTO, String pw) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE user ");
			query.append("SET pw = " + "'" + pw + "' " );
			query.append("WHERE id = " + "'" + memberDTO.getId() + "'");

			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(2, memberDTO.getPw());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO_replacePw()_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}

	// 로그인
	public synchronized boolean memberLogin(String id, String pw) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT id, pw FROM USER ");
			query.append("WHERE id = '" + id + "' AND pw = '" + pw + "'");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			while (rs.next()) {
				cnt++;
			}

			if (cnt == 1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberLoginDAO_Login_ERROR");
			return false;
		} finally {
			disconnectStmt();
		}
	}
	
	//비밀번호 찾기 전 질문 가져오기
	public synchronized String getQuestion(String id) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT id, question, answer FROM USER ");
			query.append("WHERE id = '" + id + "'");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			String question = "";
			while (rs.next()) {
				question = rs.getString("question");
				cnt++;
			}

			if (cnt != 1) {
				return null;
			}
			
			return question;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberLoginDAO_getQuestion_ERROR");
			return null;
		} finally {
			disconnectStmt();
		}
	}
	
	//질문 가져온 후 비밀번호 가져오기
	public synchronized String getPassword(String id, String answer) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT pw FROM user ");
			query.append("WHERE id = '" + id + "'" + " AND answer = '" + answer + "'");

			rs = stmt.executeQuery(query.toString());
			int cnt = 0;
			String password = "";
			while (rs.next()) {
				password = rs.getString("pw");
				cnt++;
			}

			if (cnt != 1) {
				return null;
			}
			
			return password;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberLoginDAO_getPW_ERROR");
			return null;
		} finally {
			disconnectStmt();
		}
	}
	
	//로그인한 회원의 정보를 한번에 가져오는 메서드
	public synchronized MemberDTO getMemberInfo(String id) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT pw, name, nickname, tel, email, bday, jday, type, gender, question, answer FROM USER ");
			query.append("WHERE id = '" + id + "'");

			rs = stmt.executeQuery(query.toString());
			
			MemberDTO memberDTO = new MemberDTO();
			
			int cnt = 0;
			while (rs.next()) {
				memberDTO.setId(id);
				memberDTO.setPw(rs.getString("pw"));
				memberDTO.setName(rs.getString("name"));
				memberDTO.setNickname(rs.getString("nickname"));
				memberDTO.setTel(rs.getString("tel"));
				memberDTO.setEmail(rs.getString("email"));
				memberDTO.setbDay(rs.getTimestamp("bday"));
				memberDTO.setjDay(rs.getTimestamp("jday"));
				memberDTO.setType(rs.getInt("type"));
				memberDTO.setGender(rs.getInt("gender"));
				memberDTO.setQuestion(rs.getString("question"));
				memberDTO.setAnswer(rs.getString("answer"));
				cnt++;
			}

			if (cnt < 1) {
				return null;
			}
			
			return memberDTO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO_getMemberInfo_ERROR");
			return null;
		} finally {
			disconnectStmt();
		}
	}
	
	//회원정보수정 메서드 - name, nickname, tel, email, question, answer만 수정가능
	// memberDTO1 : 원래 memberDTO, memberDTO2 : 변경할 memberDTO
	//수정 성공하면 바뀐 내용의 memberDTO 리턴, 수정 실패하면 원래 내용의 memberDTO return
	public synchronized boolean editMemberInfo(MemberDTO memberDTO2) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("UPDATE user ");
			query.append("SET name = " + "'" + memberDTO2.getName() + "', " );
			query.append(" nickname = " + "'" + memberDTO2.getNickname() + "', " );
			query.append(" tel = " + "'" + memberDTO2.getTel() + "', " );
			query.append(" email = " + "'" + memberDTO2.getEmail() + "', " );
			query.append(" question = " + "'" + memberDTO2.getQuestion() + "' " );
			query.append(" answer = " + "'" + memberDTO2.getAnswer() + "' " );
			query.append("WHERE id = " + "'" + memberDTO2.getId() + "'");
			
			pstmt = conn.prepareStatement(query.toString());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO_editMemberInfo()_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//회원탈퇴추가하기
	public synchronized boolean deleteMember(MemberDTO memberDTO) throws SQLException{
		try {
			
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("DELETE FROM user ");
			query.append("WHERE id = '" + memberDTO.getId() + "'");
			
			pstmt = conn.prepareStatement(query.toString());
			
			if (pstmt.executeUpdate() != 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MemberDAO_editMemberInfo()_ERROR");
			return false;
		} finally {
			disconnectPstmt();
		}
	}
	
	//주소추가
	public synchronized boolean newAddress(MemberDTO mdto) throws SQLException {
		try {
			conn = dbConn.getConn();
			query = new StringBuffer();
			query.append("update user set ");
			query.append("address = ?, address_x = ?, address_y = ? where id = '" + mdto.getId() + "'");

			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(1, mdto.getAddress());
			pstmt.setDouble(2, mdto.getAddressX());
			pstmt.setDouble(3, mdto.getAddressY());
			

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
	public synchronized MemberDTO getAddressInfo(String id) throws SQLException {
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			query = new StringBuffer();

			query.append("SELECT * FROM user ");
			query.append("WHERE id = '" + id + "' and address IS NOT null");

			rs = stmt.executeQuery(query.toString());

			int cnt = 0;
			MemberDTO mdto = new MemberDTO();
			while (rs.next()) {
				mdto.setId(rs.getString("id"));
				mdto.setAddressX(rs.getDouble("address_x"));
				mdto.setAddressX(rs.getDouble("address_y"));
				mdto.setAddress(rs.getString("address"));
				cnt++;
			}

			if (cnt != 1) {
				return null;
			}

			return mdto;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("주소가져오기 에러 \n" + e.getMessage());
			return null;
		} finally {
			disconnectStmt();
		}
	}
}
