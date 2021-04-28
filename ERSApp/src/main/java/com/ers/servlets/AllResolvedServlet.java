package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.model.Employee;
import com.ers.model.ReimbursementRequest;
import com.ers.service.EmployeeService;
import com.ers.service.ReimbursementService;

/**
 * Servlet implementation class AllResolvedServlet
 */

public class AllResolvedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReimbursementService reimbursementService;
	EmployeeService employeeService;

	@Override
	public void init() throws ServletException {
		reimbursementService = new ReimbursementService();
		employeeService = new EmployeeService();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AllResolvedServlet() {
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
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		if (session.getAttribute("manager") != null) {
			Employee manager = (Employee) session.getAttribute("manager");
			PrintWriter out = response.getWriter();

			byte[] encodedHtml = Files
					.readAllBytes(Paths.get("C:\\Project1-ERS\\ERSApp\\src\\main\\webapp\\all-resolved.html"));
			String html = new String(encodedHtml, StandardCharsets.UTF_8);

			try {
				ArrayList<ReimbursementRequest> allResolvedRequests = reimbursementService.getResolvedRequests();

				html = html.replace("placeholder_EmployeeName", manager.getFirstName());
				String requests="";
				for(ReimbursementRequest resolved : allResolvedRequests) {
					String empFirstLast = employeeService.getEmployeeInfo(resolved.getRequestorId()).getFirstName() + " "
							+ employeeService.getEmployeeInfo(resolved.getRequestorId()).getLastName();
					requests += "<tr><td>" + resolved.getRequestId() + "</td>";
					requests += "<td>" + resolved.getRequestorId() + "</td>";
					requests += "<td>" + empFirstLast + "</td>";
					requests += "<td>" + resolved.getAmountRequested() + "</td>";
					requests += "<td>" + resolved.getStatus() + "</td>";
					requests += "<td>" + resolved.getReason() + "</td>";
					if (resolved.getReceipt().length != 0)
						requests += "<td> RECIEPT LINK </td><tr>";
					else
						requests += "<td> N/A </td><tr>";
				}
				html = html.replace("<tr id=\"placeholder_Requests\"><td>placeholder_Requests<td></tr>", requests);
				out.print(html);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
			response.sendRedirect("/ERSApp");
	}

}
