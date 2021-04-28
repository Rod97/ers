package com.ers.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.exceptions.InfoUpdateException;
import com.ers.exceptions.InvalidInputException;
import com.ers.model.Employee;
import com.ers.service.EmployeeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class EmployeeInfoServlet
 */
public class EmployeeInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EmployeeService employeeService;

	@Override
	public void init() throws ServletException {
		employeeService = new EmployeeService();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeInfoServlet() {
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

		if (session.getAttribute("employee") != null || session.getAttribute("manager") != null) {
			Employee employee;
			if(session.getAttribute("manager")==null) {
				employee = (Employee) session.getAttribute("employee");
			}else
				employee = (Employee) session.getAttribute("manager");
			PrintWriter out = response.getWriter();

			byte[] encodedString = Files
					.readAllBytes(Paths.get("C:\\Project1-ERS\\ERSApp\\src\\main\\webapp\\employee-info.html"));
			String html = new String(encodedString, StandardCharsets.UTF_8);

			String employeeName = employee.getFirstName() + " " + employee.getLastName();

			if (employee.isManager())
				html = html.replace("placeholder_EmployeeName", "Manager " + employee.getFirstName());
			else
				html = html.replace("placeholder_EmployeeName", employee.getFirstName());
			html = html.replace("placeholder_EmpNameFirstLast", employeeName);
			html = html.replace("placeholder_EmpID", String.valueOf(employee.getId()));
			html = html.replace("placeholder_EmpEmail", employee.getEmail());

			out.print(html);
		} else
			response.sendRedirect("/ERSApp");
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();

		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}

		// reading the json from the request
		JsonNode json = objectMapper.readTree(sb.toString());

		// used to create the response json object
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String empName = json.get("name").asText();
		String empEmail = json.get("email").asText();

		System.out.println(empName + empEmail);

		Employee empUpdate;
		Employee employee = (Employee) session.getAttribute("employee");

		int empId = employee.getId();

		try {
			empUpdate = employeeService.updateEmployeeInfo(empId, empName, empEmail);
			session.setAttribute("employee", empUpdate);

			System.out.println(empUpdate);

			JsonObject empObject = builder.add("name", empUpdate.getFirstName() + " " + empUpdate.getLastName())
					.add("email", empUpdate.getEmail()).build();

			try (OutputStream out = resp.getOutputStream()) {
				JsonWriter jsonW = Json.createWriter(out);
				jsonW.write(empObject);
				jsonW.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InfoUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
