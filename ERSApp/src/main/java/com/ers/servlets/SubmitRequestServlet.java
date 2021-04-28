package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.model.Employee;
import com.ers.service.ReimbursementService;

/**
 * Servlet implementation class SubmitRequestServlet
 */

public class SubmitRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReimbursementService reimbursementService;

	@Override
	public void init() throws ServletException {
		reimbursementService = new ReimbursementService();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitRequestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (session.getAttribute("employee") != null) {
			Employee employee = (Employee) session.getAttribute("employee");
			byte[] encodedString = Files
					.readAllBytes(Paths.get("C:\\Project1-ERS\\ERSApp\\src\\main\\webapp\\request-form.html"));
			String html = new String(encodedString, StandardCharsets.UTF_8);

			html = html.replace("value=\"placeHolder_EmpID\"", "value=\"" + employee.getId() + "\"");

			out.print(html);
		} else
			response.sendRedirect("/ERSApp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		

		ArrayList<String> submission = new ArrayList<>();;

		System.out.println(req.getParameter("receipt").length());

		submission.add(req.getParameter("employeeid"));
		submission.add(req.getParameter("currency-field"));
		submission.add("Pending");
		submission.add(req.getParameter("reason"));
		if (req.getParameter("receipt") != null) {
			System.out.println(req.getParameter("receipt"));
			if(req.getParameter("receipt").length()>4)
				submission.add(req.getParameter("receipt"));
		}

		try {
			int status = reimbursementService.submitReimbursementRequest(submission);
			if (status == 1) {
				// request is submitted, alert the user somehow
				resp.sendRedirect("request");
			}
		} catch (SQLException e) { // TODO Auto-generated catchblock
			e.printStackTrace();
		}
	}

}
