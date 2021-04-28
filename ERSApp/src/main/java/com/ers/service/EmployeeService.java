package com.ers.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ers.DAO.EmployeeDAO;
import com.ers.DAO.EmployeeDAOImpl;
import com.ers.exceptions.InfoUpdateException;
import com.ers.exceptions.InvalidInputException;
import com.ers.model.Employee;
import com.ers.util.ConnectionUtil;

public class EmployeeService {
	
	EmployeeDAO employeeDAO = new EmployeeDAOImpl();
	
	public Employee updateEmployeeInfo(int empId, String nameFirstLast, String email) throws SQLException, InvalidInputException, InfoUpdateException {
		Employee employee = null;
		
		nameFirstLast = nameFirstLast.trim(); //remove whitespace at the beginning and end
		if(!nameFirstLast.contains(" "))
			throw new InvalidInputException();
		
		if (nameFirstLast.length() >= 3) {
			String empFirst = nameFirstLast.substring(0, nameFirstLast.indexOf(" "));
			String empLast = nameFirstLast.substring(nameFirstLast.indexOf(" "));

			try (Connection con = ConnectionUtil.getConnection()) {
			
				employee = employeeDAO.updateEmployee(empId,empFirst, empLast, email, con);
				
				if(employee==null) {
					throw new InfoUpdateException();
				}
			}
		}

		return employee;
	}

	public ArrayList<Employee> getEmployees() throws SQLException{
		ArrayList<Employee> employees = new ArrayList<>();
		
		try(Connection con = ConnectionUtil.getConnection()){
			employees = employeeDAO.getEmployees(con);
		}
		
		return employees;
	}
	
	public Employee getEmployeeInfo(Integer id) throws SQLException{
		Employee employee = new Employee();
		try(Connection con = ConnectionUtil.getConnection()){
			employee = employeeDAO.getEmployeeInfo(id, con);
		}
		return employee;
	}
}
