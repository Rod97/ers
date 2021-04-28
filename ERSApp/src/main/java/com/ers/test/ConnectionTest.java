package com.ers.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ers.util.ConnectionUtil;

public class ConnectionTest {

	public static void main(String[] args) throws SQLException {
		Connection con = ConnectionUtil.getConnection();	
		
		String sql = "SELECT * FROM ers_database.employees;";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getBoolean(3));
			System.out.println(rs.getString(4));
		}
		
		
		System.out.println(con.toString());
	}

}
