package com.xzfantom.homebrewery;

import java.sql.*;

public class DataStorage {
	private Connection c = null;

	public DataStorage() {

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:homebrewery.db");
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
	}

	public void CreateDatabase() {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE EVENTS "
					+ "(ID INT PRIMARY KEY     NOT NULL, "
					+ " NAME           TEXT    NOT NULL, "
					+ " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " 
					+ " SALARY         REAL)";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

}
