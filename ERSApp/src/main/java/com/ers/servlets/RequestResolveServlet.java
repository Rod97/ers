package com.ers.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ers.service.ReimbursementService;

/**
 * Servlet implementation class RequestResolveServlet
 */

public class RequestResolveServlet extends HttpServlet {
	
	ReimbursementService reimbursementService;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestResolveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		reimbursementService = new ReimbursementService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			
			String[] requestIds = request.getParameterValues("requestId");
			String[] decisions = request.getParameterValues("decision");
			
			try {
				reimbursementService.updateStatusOfApplication(requestIds, decisions);
				response.sendRedirect("manager");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
