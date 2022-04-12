package com.lunchforce.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//공통 Connection 싱글톤 클래스
public class DBConnecter {
	private static DBConnecter dbConnecter = new DBConnecter();
	private Connection conn = null;
	private Context context;
	private PreparedStatement pstmt = null;
	private DataSource dataSource;
	
	//생성자
	public DBConnecter(){
		try {
			//Connection pool 사용
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");
			conn = dataSource.getConnection();
		} catch (Exception e) {
			System.out.println("DBConnection ERROR");
		}
	}
	
	//DBConnection 싱글톤 객체 getter
	public static DBConnecter getDBConnecter() {
		if(dbConnecter == null) {
			System.out.println("dbConn 오류");
			return null;
		}
		else {
			return dbConnecter;
		}
	}
	public Connection getConn() {
		return conn;
	}
}
