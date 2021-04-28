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
import com.ers.service.ReimbursementService;

/**
 * Servlet implementation class EmployeeHomeServlet
 */

public class EmployeeHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ReimbursementService reimbursementService;

	@Override
	public void init() throws ServletException {
		reimbursementService = new ReimbursementService();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeHomeServlet() {
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

		if (session.getAttribute("employee") != null) {
			try {

				PrintWriter out = response.getWriter();

				String reqShow = (String) session.getAttribute("reqShow");
				Employee employee = (Employee) session.getAttribute("employee");
				byte[] encodedHtml = Files.readAllBytes(
						Paths.get("C:\\Project1-ERS\\ERSApp\\src\\main\\webapp\\employee-homescreen.html"));
				// edit above file to format employee homescreen. can add javascript to support
				// ajax
				String html = new String(encodedHtml, StandardCharsets.UTF_8);
				html = html.replace("placeholder_EmployeeName", employee.getFirstName());

				String requests = "";

				if (reqShow.equals("1")) {

					System.out.println(employee.getId() + "\n+++++++++++++");
					System.out.println("Trying to show pending\n=======================");
					ArrayList<ReimbursementRequest> pendingRequest = reimbursementService
							.getPendingRequestsById(employee.getId());

					for (ReimbursementRequest reimbursementRequest : pendingRequest) {
						requests += "<tr><td>" + reimbursementRequest.getRequestId() + "</td>";
						requests += "<td>" + reimbursementRequest.getAmountRequested() + "</td>";
						requests += "<td>" + reimbursementRequest.getStatus() + "</td>";
						requests += "<td>" + reimbursementRequest.getReason() + "</td>";
						if (reimbursementRequest.getReceipt().length != 0)
							requests += "<td> RECIEPT LINK </td><tr>";
						else
							requests += "<td> N/A </td><tr>";
					}

				} else if (reqShow.equals("2")) {

					ArrayList<ReimbursementRequest> resolvedRequests = reimbursementService
							.getResolvedRequestsById(employee.getId());

					for (ReimbursementRequest reimbursementRequest : resolvedRequests) {
						// filter out the resolved requests that aren't accepted
						if (reimbursementRequest.getStatus().equals("Accepted")) {
							System.out.println("\nappending requested string for accepted$$$$$\n");
							requests += "<tr><td>" + reimbursementRequest.getAmountRequested() + "</td>";
							requests += "<td>" + reimbursementRequest.getStatus() + "</td>";
							requests += "<td>" + reimbursementRequest.getReason() + "</td>";
							if (reimbursementRequest.getReceipt().length != 0)
								requests += "<td> RECIEPT LINK </td><tr>";
							else
								requests += "<td> N/A </td><tr>";
						}
					}

				} else if (reqShow.equals("3")) {

					System.out.println(employee.getId() + "\n+++++++++++++");
					System.out.println("Trying to show rejected\n=======================");
					ArrayList<ReimbursementRequest> resolvedRequests = reimbursementService
							.getResolvedRequestsById(employee.getId());

					for (ReimbursementRequest reimbursementRequest : resolvedRequests) {
						// filter out the resolved requests that aren't rejected
						if (reimbursementRequest.getReason().equals("Rejected")) {
							requests += "<tr><td>" + reimbursementRequest.getAmountRequested() + "</td>";
							requests += "<td>" + reimbursementRequest.getStatus() + "</td>";
							requests += "<td>" + reimbursementRequest.getReason() + "</td>";
							if (reimbursementRequest.getReceipt().length != 0)
								requests += "<td> RECIEPT LINK </td><tr>";
							else
								requests += "<td> N/A </td><tr>";
						}
					}
				}
				html = html.replace("<tr id=\"placeholder_Requests\"><td>placeholder_Requests<td></tr>", requests);
				out.print(html);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			response.sendRedirect("/ERSApp");
	}
}
