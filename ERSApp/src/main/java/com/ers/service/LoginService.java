package com.ers.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ers.DAO.EmployeeDAO;
import com.ers.DAO.EmployeeDAOImpl;
import com.ers.exceptions.EmployeeNotFoundException;
import com.ers.exceptions.InvalidIDException;
import com.ers.exceptions.WrongPasswordException;
import com.ers.model.Employee;
import com.ers.util.ConnectionUtil;

public class LoginService {
	EmployeeDAO employeeDAO = new EmployeeDAOImpl();

	public Employee verifyCredentials(String id, String pass) throws SQLException, InvalidIDException, EmployeeNotFoundException, WrongPasswordException {
		Employee employee = null;
		int empID = Integer.parseInt(id);
		
		if(empID>=1000000000 || empID <=99999999)
		{
			throw new InvalidIDException();
		}
		try(Connection con = ConnectionUtil.getConnection()){
			employee = employeeDAO.verifyCredentials(empID, pass, con);
		}
		
		if(employee == null) {
			throw new EmployeeNotFoundException();
		}else if(employee.getFirstName().equals("")) {
			throw new WrongPasswordException();
		}
		return employee;
	}
}
