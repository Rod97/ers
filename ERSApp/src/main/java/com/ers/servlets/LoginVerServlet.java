package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.exceptions.EmployeeNotFoundException;
import com.ers.exceptions.InvalidIDException;
import com.ers.exceptions.WrongPasswordException;
import com.ers.model.Employee;
import com.ers.service.LoginService;

/**
 * Servlet implementation class LoginVerServlet
 */

public class LoginVerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		loginService = new LoginService();
	}

	// I guess I need to keep this here.
	public LoginVerServlet() {
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("login.html");

		try {

			Employee activeEmployee = loginService.verifyCredentials(request.getParameter("empID"),
					request.getParameter("pass"));

			HttpSession session = request.getSession();

			if (activeEmployee.isManager()) {
				session.setAttribute("manager", activeEmployee);
				response.sendRedirect(request.getContextPath() + "/manager");
			} else {
				session.setAttribute("employee", activeEmployee);
				session.setAttribute("reqShow", "1"); // 1 = pending, 2 = accepted, 3 = rejected
				response.sendRedirect(request.getContextPath() + "/employee");
			}
		} catch (InvalidIDException e) {
			// TODO Auto-generated catch block
			out.print("Invalid ID");
			rd.include(request, response);
		} catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			out.print("Employee not found");
			rd.include(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (WrongPasswordException e) {
			// TODO Auto-generated catch block
			out.print("Password is incorrect!");
			rd.include(request, response);
		}
	}

}
