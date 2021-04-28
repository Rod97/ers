package com.ers.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.model.Employee;
import com.ers.model.ReimbursementRequest;
import com.ers.service.EmployeeService;
import com.ers.service.ReimbursementService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ManagerDisplayServlet
 */
public class ManagerDisplayServlet extends HttpServlet {
	EmployeeService employeeService;
	ReimbursementService reimbursementService;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		employeeService = new EmployeeService();
		reimbursementService = new ReimbursementService();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerDisplayServlet() {
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
		Employee manager = (Employee) session.getAttribute("manager");

		try {
			ArrayList<Employee> employees = employeeService.getEmployees();

			employees.remove(manager);

			JsonObjectBuilder listBuilder = Json.createObjectBuilder();
			JsonObject allEmployees;

			for (int i = 0; i < employees.size(); i++) {

				JsonObjectBuilder entryBuilder = Json.createObjectBuilder();

				entryBuilder.add("name", employees.get(i).getFirstName() + " " + employees.get(i).getLastName())
						.add("id", employees.get(i).getId()).add("email", employees.get(i).getEmail());
				listBuilder.add("emp" + i, entryBuilder);
			}

			allEmployees = listBuilder.build();
			try (OutputStream out = response.getOutputStream()) {
				JsonWriter jsonW = Json.createWriter(out);
				jsonW.write(allEmployees);
				jsonW.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee manager = (Employee) session.getAttribute("manager");

		if (manager.isManager()) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();

				StringBuilder sb = new StringBuilder();
				BufferedReader reader = request.getReader();

				try {
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line).append('\n');
					}
				} finally {
					reader.close();
				}

				JsonNode json = objectMapper.readTree(sb.toString());
				ArrayList<ReimbursementRequest> employeeRequests = reimbursementService
						.getPendingRequestsById(json.get("employeeID").asInt());

				JsonObject employeeRequestsJson;
				JsonObjectBuilder employeeRequestsJsonBuilder = Json.createObjectBuilder();

				for (int i = 0; i < employeeRequests.size(); i++) {
					JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
					requestBuilder.add("reqId", employeeRequests.get(i).getRequestId())
							.add("requesting", employeeRequests.get(i).getAmountRequested())
							.add("status", employeeRequests.get(i).getStatus())
							.add("reason", employeeRequests.get(i).getReason());
					if (employeeRequests.get(i).getReceipt().length == 0) {
						requestBuilder.add("receipt", "N/A");
					} else {
						requestBuilder.add("receipt", "RECEIPT LINK");
					}
					employeeRequestsJsonBuilder.add("pendReq" + i, requestBuilder);
				}
				employeeRequests = reimbursementService.getResolvedRequestsById(json.get("employeeID").asInt());
				for (int i = 0; i < employeeRequests.size(); i++) {
					JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
					requestBuilder.add("reqId", employeeRequests.get(i).getRequestId())
							.add("requesting", employeeRequests.get(i).getAmountRequested())
							.add("status", employeeRequests.get(i).getStatus())
							.add("reason", employeeRequests.get(i).getReason());

					if (employeeRequests.get(i).getReceipt().length == 0) {
						requestBuilder.add("receipt", "N/A");
					} else {
						requestBuilder.add("receipt", "RECEIPT LINK");
					}
					employeeRequestsJsonBuilder.add("resReq" + i, requestBuilder);
				}
				employeeRequestsJson = employeeRequestsJsonBuilder.build();
				try (OutputStream out = response.getOutputStream()) {
					JsonWriter jsonW = Json.createWriter(out);
					jsonW.write(employeeRequestsJson);
					jsonW.close();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
