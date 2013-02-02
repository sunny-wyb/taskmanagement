package com.task.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	
	/*
	 * do some improvement
	 */
	private static boolean init = false;
	
	
	public static void init() throws ClassNotFoundException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException ex) {
			//TODO log
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public static synchronized Connection getConnection() throws ClassNotFoundException, SQLException {
		if (!init) {
			init();
			init = true;
		}
		
		try {
			Connection conn = DriverManager.getConnection
			("jdbc:mysql://localhost:3306/task?useUnicode=true&characterEncoding=GBK","root","root");
			return conn;
		} catch (SQLException e) {
			// TODO log
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			//TODO
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO log
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/*
	 * used for Prepared statement in clause
	 */
	public static String preparePlaceHolders(int length) {

	    StringBuilder builder = new StringBuilder();

	    for (int i = 0; i < length;) {

	        builder.append("?");

	        if (++i < length) {

	            builder.append(",");

	        }

	    }

	    return builder.toString();
	}
	
	public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {

	    for (int i = 0; i < values.length; i++) {

	        preparedStatement.setObject(i + 1, values[i]);

	    }

	}
}
