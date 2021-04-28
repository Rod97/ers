package com.ers.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ers.model.Employee;

public interface EmployeeDAO {
	public Employee verifyCredentials(Integer id, String pass, Connection con) throws SQLException;
	public Employee getEmployeeInfo(Integer id, Connection con) throws SQLException;
	public ArrayList<Employee> getEmployees(Connection con) throws SQLException;
	public int registerNewEmployee(Employee employee, Connection con) throws SQLException;
	public Employee updateEmployee(int ID, String firstName, String lastName, String email, Connection con) throws SQLException;
}
