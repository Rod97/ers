package com.ers.DAO;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.ers.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public Employee verifyCredentials(Integer id, String pass, Connection con) throws SQLException {
		Employee employee = null;

		String sql = "SELECT * FROM ers_database.employees WHERE employee_id = ?;";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {

			if (pass.equals(rs.getString(("employee_password")))) {
				int empID = rs.getInt("employee_id");
				boolean isManager = rs.getBoolean("is_manager");
				String email = rs.getString("email");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				employee = new Employee(empID, isManager, email, firstName, lastName);
			} else
				employee = new Employee();
		}

		return employee;
	}

	@Override
	public Employee getEmployeeInfo(Integer id, Connection con) throws SQLException {
		Employee employee = null;
		String sql = "SELECT * FROM ers_database.employees WHERE employee_id = ?;";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {

			int empID = rs.getInt("employee_id");
			boolean isManager = rs.getBoolean("is_manager");
			String email = rs.getString("email");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");

			employee = new Employee(empID, isManager, email, firstName, lastName);

		}

		return employee;

	}

	@Override
	public ArrayList<Employee> getEmployees(Connection con) throws SQLException {
		ArrayList<Employee> employees = new ArrayList<>();

		String sql = "SELECT * FROM ers_database.employees;";

		PreparedStatement pstmt = con.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			int id = rs.getInt("employee_id");
			boolean isManager = rs.getBoolean("is_manager");
			String email = rs.getString("email");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");

			Employee employee = new Employee(id, isManager, email, firstName, lastName);
			employees.add(employee);
		}

		return employees;
	}

	@Override
	public int registerNewEmployee(Employee employee, Connection con) throws SQLException {
		// Password generated here
		byte[] seed = new byte[8];
		new Random().nextBytes(seed);
		String password = new String(seed, Charset.forName("UTF-8"));

		String sql = "INSERT INTO ers_databse.employees VALUES(?,?,?,?,?,?);";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, employee.getId());
		pstmt.setString(2, password);
		pstmt.setBoolean(3, employee.isManager());
		pstmt.setString(4, employee.getEmail());
		pstmt.setString(5, employee.getFirstName());
		pstmt.setString(6, employee.getLastName());

		int status = pstmt.executeUpdate();

		return status;
	}

	@Override
	public Employee updateEmployee(int ID, String firstName, String lastName, String email, Connection con) throws SQLException {
		Employee employeeUpdated = null;
		con.setAutoCommit(false);
		String sql = "UPDATE ers_database.employees SET first_name=?,last_name=?,email=? WHERE employee_id=?;";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, firstName);
		pstmt.setString(2, lastName);
		pstmt.setString(3, email);
		pstmt.setInt(4, ID);
		
		int status = pstmt.executeUpdate();
		
		if(status == 1) {
			con.commit();
			sql = "SELECT * FROM ers_database.employees WHERE employee_id=?;";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,ID);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boolean isManager=rs.getBoolean("is_manager");
				employeeUpdated=new Employee(ID, isManager, email, firstName, lastName);
			}
		}
		return employeeUpdated;
	}

}
