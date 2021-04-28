//package com.ers.servlets;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Base64;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.ers.model.ReimbursementRequest;
//import com.ers.service.ReimbursementService;
//
///**
// * Servlet implementation class Test
// */
//public class Test extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	ReimbursementService reimbursementService;
//
//	@Override
//	public void init() throws ServletException {
//		reimbursementService = new ReimbursementService();
//	}
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public Test() {
//		super();
//		// TODO CURRENT TEST: request submission
//	}
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//
//		int size = request.getParameterMap().size();
//		String[] submission = new String[size];
//
//		submission[0] = request.getParameter("employeeid");
//		submission[1] = request.getParameter("amount");
//		submission[2] = "Pending";
//		submission[3] = request.getParameter("reason");
//		if (size == 5)
//			submission[4] = request.getParameter("receipt");
//		
//
//		try {
//			int status = reimbursementService.submitReimbursementRequest(submission);
//			if(status==1) {
//				response.sendRedirect("/employee");
//			}
//		} catch (SQLException e) { // TODO Auto-generated catchblock
//			e.printStackTrace();
//		}
//
//		// retrieving reimbursement info from database
//		// I don't like this implementation but the basic idea worked. have to figure
//		// out how to limit how big the image is/can be
//		/*
//		 * ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>(); try {
//		 * pendingRequests = reimbursementService.getPendingRequestsById("123456789");
//		 * out.print("<br/><br/><br/><br/>");
//		 * out.print("<img id=\"receipt\" src=\"\">"); out.
//		 * print("<script>document.getElementById(\"receipt\").src = \"data:image/png;base64,"
//		 * + Base64.getEncoder().encodeToString(pendingRequests.get(13).getReceipt()) +
//		 * "\""); //WORKS FOR DISPLAYING IMAGE BUT IMPLEMENT IN A BETTER WAY THIS UGLY
//		 * out.print("</script>"); out.print("</body></html>");
//		 * 
//		 * } catch (NumberFormatException | SQLException e) { // TODO Auto-generated
//		 * catch block e.printStackTrace(); }
//		 */
//
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//	}
//
//}
