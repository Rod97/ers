package com.ers.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ers.DAO.ReimbursementDAO;
import com.ers.DAO.ReimbursementDAOImpl;
import com.ers.model.ReimbursementRequest;
import com.ers.util.ConnectionUtil;

public class ReimbursementService {
	ReimbursementDAO reimbursementDAO = new ReimbursementDAOImpl();

	public int submitReimbursementRequest(ArrayList<String> submission) throws SQLException, IOException {
		// status refers to the request submission status
		// 1 : success
		// -1 : failure
		int status;
		try (Connection con = ConnectionUtil.getConnection()) {

			status = reimbursementDAO.submitReimbursementRequest(submission, con);
		}

		return status;
	}

	public ArrayList<ReimbursementRequest> getPendingRequestsById(int i) throws NumberFormatException, SQLException {
		ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnection()) {
			pendingRequests = reimbursementDAO.getPendingRequestsById(i, con);
		}

		return pendingRequests;

	}

	public ArrayList<ReimbursementRequest> getResolvedRequestsById(Integer id) throws SQLException {
		ArrayList<ReimbursementRequest> resolvedRequests = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnection()) {
			resolvedRequests = reimbursementDAO.getResolvedRequestsById(id, con);
		}

		return resolvedRequests;
	}

	// Just in case I call it directly from form input.
	public ArrayList<ReimbursementRequest> getPendingRequestsById(String i) throws NumberFormatException, SQLException {
		ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnection()) {
			System.out.println(i);
			pendingRequests = reimbursementDAO.getPendingRequestsById(Integer.parseInt(i), con);
		}

		return pendingRequests;

	}

	public void updateStatusOfApplication(String[] requestIds, String[] decisions) throws SQLException{
		
		try(Connection con = ConnectionUtil.getConnection()){
			int[] reqIds = new int[requestIds.length];
			int[] decs = new int[decisions.length];
			for(int i = 0; i<requestIds.length; i++)
			{
				reqIds[i] = Integer.parseInt(requestIds[i]);
				decs[i] = Integer.parseInt(decisions[i]);
			}
			reimbursementDAO.updateStatusOfApplication(reqIds, decs, con);
		}
	}

	public ArrayList<ReimbursementRequest> getResolvedRequests() throws SQLException {
		ArrayList<ReimbursementRequest> allResolvedRequests = new ArrayList<>();
		
		try(Connection con = ConnectionUtil.getConnection()){
			allResolvedRequests = reimbursementDAO.getResolvedRequests(con);
		}
		return allResolvedRequests;
	}

	public ArrayList<ReimbursementRequest> getPendingRequests() throws SQLException {
ArrayList<ReimbursementRequest> allResolvedRequests = new ArrayList<>();
		
		try(Connection con = ConnectionUtil.getConnection()){
			allResolvedRequests = reimbursementDAO.getPendingRequests(con);
		}
		return allResolvedRequests;
	}
}
