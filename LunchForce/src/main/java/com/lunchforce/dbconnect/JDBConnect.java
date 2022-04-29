package com.lunchforce.dbconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.lunchforce.dbconnect.DBConnecter;
import java.sql.ResultSet;

//DAO를 위한 부모 클래스
public class JDBConnect {
	protected DBConnecter dbConn = DBConnecter.getDBConnecter();

	protected Connection conn;
	protected Statement stmt;
	protected ResultSet rs;
	protected PreparedStatement pstmt;
	protected StringBuffer query;
	
	// pstmt일 경우 연결 해제
	protected void disconnectPstmt() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		pstmt.close();
	}

	// stmt일 경우 연결 해제
	protected void disconnectStmt() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		stmt.close();
	}
}
