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
import com.ers.service.ReimbursementService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class RequestShowSetterServlet
 */

public class RequestShowSetterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReimbursementService reimbursementService;

	@Override
	public void init() throws ServletException {
		reimbursementService = new ReimbursementService();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestShowSetterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

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

			JsonObjectBuilder requestsJson = Json.createObjectBuilder();

			String showStatus = json.get("show").asText();

			Employee employee = (Employee) session.getAttribute("employee");

			if (showStatus.equals("1")) {

				
				ArrayList<ReimbursementRequest> pendingRequests = reimbursementService
						.getPendingRequestsById(employee.getId());
				session.setAttribute("reqShow", "1");

				JsonObject pendingRequestsJson;

				for (int i = 0; i < pendingRequests.size(); i++) {
					JsonObjectBuilder indvRequestsJson = Json.createObjectBuilder();
					indvRequestsJson.add("reqId", pendingRequests.get(i).getRequestId())
							.add("requesting", pendingRequests.get(i).getAmountRequested())
							.add("status", pendingRequests.get(i).getStatus())
							.add("reason", pendingRequests.get(i).getReason());
					if (!(pendingRequests.get(i).getReceipt().length == 0)) {
						indvRequestsJson.add("reciept", "RECEIPT LINK");
					} else
						indvRequestsJson.add("reciept", "N/A");
					requestsJson.add("req" + i, indvRequestsJson);
				}

				pendingRequestsJson = requestsJson.build();

				try (OutputStream out = response.getOutputStream()) {
					JsonWriter jsonW = Json.createWriter(out);
					jsonW.write(pendingRequestsJson);
					jsonW.close();
				}

			} else if (showStatus.equals("2")) {

				ArrayList<ReimbursementRequest> pendingRequests = reimbursementService
						.getResolvedRequestsById(employee.getId());
				session.setAttribute("reqShow", "2");

				JsonObject pendingRequestsJson;

				
				for (int i = 0; i < pendingRequests.size(); i++) {

					if (pendingRequests.get(i).getStatus().equals("Accepted")) {
						JsonObjectBuilder indvRequestsJson = Json.createObjectBuilder();
						indvRequestsJson.add("reqId", pendingRequests.get(i).getRequestId())
								.add("requesting", pendingRequests.get(i).getAmountRequested())
								.add("status", pendingRequests.get(i).getStatus())
								.add("reason", pendingRequests.get(i).getReason());
						if (!(pendingRequests.get(i).getReceipt().length == 0)) {
							indvRequestsJson.add("reciept", "RECEIPT LINK");
						} else
							indvRequestsJson.add("reciept", "N/A");
						requestsJson.add("req" + i, indvRequestsJson);
					}
				}

				pendingRequestsJson = requestsJson.build();

				try (OutputStream out = response.getOutputStream()) {
					JsonWriter jsonW = Json.createWriter(out);
					jsonW.write(pendingRequestsJson);
					jsonW.close();
				}

			}else if (showStatus.equals("3")) {
				
				ArrayList<ReimbursementRequest> pendingRequests = reimbursementService
						.getResolvedRequestsById(employee.getId());
				session.setAttribute("reqShow", "3");

				JsonObject pendingRequestsJson;

				for (int i = 0; i < pendingRequests.size(); i++) {

					if (pendingRequests.get(i).getStatus().equals("Rejected")) {
						JsonObjectBuilder indvRequestsJson = Json.createObjectBuilder();
						indvRequestsJson.add("reqId", pendingRequests.get(i).getRequestId())
								.add("requesting", pendingRequests.get(i).getAmountRequested())
								.add("status", pendingRequests.get(i).getStatus())
								.add("reason", pendingRequests.get(i).getReason());
						if (!(pendingRequests.get(i).getReceipt().length == 0)) {
							indvRequestsJson.add("reciept", "RECEIPT LINK");
						} else
							indvRequestsJson.add("reciept", "N/A");
						requestsJson.add("req" + i, indvRequestsJson);
					}
				}

				pendingRequestsJson = requestsJson.build();

				try (OutputStream out = response.getOutputStream()) {
					JsonWriter jsonW = Json.createWriter(out);
					jsonW.write(pendingRequestsJson);
					jsonW.close();
				}

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
