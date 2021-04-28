package com.ers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;



public class ConnectionUtil {
	public static Connection getConnection() throws SQLException {

		Connection con = null;

		Driver postgresDriver = new Driver();

		DriverManager.registerDriver(postgresDriver);

		String url = System.getenv("db_url");
		String username = System.getenv("db_username");
		String password = System.getenv("db_password");
		
		con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
}
