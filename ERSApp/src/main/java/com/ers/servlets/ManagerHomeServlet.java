package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.model.Employee;

/**
 * Servlet implementation class ManagerHomeServlet
 */

public class ManagerHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerHomeServlet() {
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
			
			byte[] encodedHtml = Files.readAllBytes(Paths.get("C:\\Project1-ERS\\ERSApp\\src\\main\\webapp\\manager-homescreen.html"));
			String html = new String(encodedHtml, StandardCharsets.UTF_8);
			
			html = html.replace("placeholder_EmployeeName", manager.getFirstName());
			
			out.print(html);
		} else
			response.sendRedirect("/ERSApp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
