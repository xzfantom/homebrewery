package com.xzfantom.homebrewery;

import java.sql.*;

public class DataStorage {
	private Connection c = null;
	private String fileName;
	
	public DataStorage() {

	}

	public boolean Connect() {
		boolean result;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + fileName);
			System.out.println("Opened database successfully");
			result = true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			result = false;
		}
		return result;
	}
	
	public void setFileName(String S){
		fileName = S.replace("\\", "/");
	}
	
	public boolean Connect(String fn) {
		setFileName(fn);
		return Connect();
	}

	private void CreateDatabase() {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE EVENTS "
					+ "(ID INT PRIMARY KEY     NOT NULL, "
					+ " TYPE           INT    NOT NULL, "
					+ " DATE           DATE     NOT NULL, "
					+ " MESSAGE        TEXT";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void getData(String S) {
		//TODO saving data on disk
	}

}
