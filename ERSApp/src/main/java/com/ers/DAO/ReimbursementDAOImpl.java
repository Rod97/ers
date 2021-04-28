package com.ers.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ers.model.ReimbursementRequest;

public class ReimbursementDAOImpl implements ReimbursementDAO {

	@Override
	public int submitReimbursementRequest(ArrayList<String> submission, Connection con)
			throws SQLException, IOException {
		File receipt = null;
		FileInputStream ris = null;
		if (submission.size() == 5) {
			receipt = new File("D:\\Pictures\\" + submission.get(4));
			ris = new FileInputStream(receipt);
		}
		String sql = "INSERT INTO ers_database.reimbursment_requests VALUES(DEFAULT,?,?,?,?,?);"; // reimbursement is
																									// spelled wrong on
																									// purpose do not
																									// change

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(submission.get(0)));
		ps.setString(2, submission.get(1));
		ps.setString(3, submission.get(2));
		ps.setString(4, submission.get(3));
		if (submission.size() == 5)
			ps.setBinaryStream(5, ris, receipt.length());
		else
			ps.setBinaryStream(5, ris);

		int count = ps.executeUpdate();

		if (count != 1)
			return -1;

		return count;
	}

	@Override
	public ArrayList<ReimbursementRequest> getPendingRequestsById(Integer id, Connection con) throws SQLException {
		ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>();
		ReimbursementRequest request;
		String sql = "SELECT * FROM ers_database.reimbursment_requests WHERE status = 'Pending' AND requestor_id = ?;";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int requestId = rs.getInt("request_id");
			int requestorId = rs.getInt("requestor_id");
			String amountRequested = rs.getString("amount_requested");
			String status = rs.getString("status");
			String reason = rs.getString("reason");
			byte[] receipt = rs.getBytes("receipt");

			request = new ReimbursementRequest(requestId, requestorId, amountRequested, status, reason, receipt);
			pendingRequests.add(request);
		}

		return pendingRequests;
	}

	@Override
	public ArrayList<ReimbursementRequest> getResolvedRequestsById(Integer id, Connection con) throws SQLException {
		ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>();
		System.out.println("inside resolved requests dao");
		ReimbursementRequest request;
		String sql = "SELECT * FROM ers_database.reimbursment_requests WHERE status<>'Pending' AND requestor_id = ?;";

		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int requestId = rs.getInt("request_id");
			int requestorId = rs.getInt("requestor_id");
			String amountRequested = rs.getString("amount_requested");
			String status = rs.getString("status");
			String reason = rs.getString("reason");
			byte[] receipt = rs.getBytes("receipt");

			request = new ReimbursementRequest(requestId, requestorId, amountRequested, status, reason, receipt);
			pendingRequests.add(request);
		}

		return pendingRequests;
	}

	public ArrayList<ReimbursementRequest> getResolvedRequests(Connection con) throws SQLException {
		ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>();
		ReimbursementRequest request;
		String sql = "SELECT * FROM ers_database.reimbursment_requests WHERE status<>'Pending';";

		PreparedStatement pstmt = con.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int requestId = rs.getInt("request_id");
			int requestorId = rs.getInt("requestor_id");
			String amountRequested = rs.getString("amount_requested");
			String status = rs.getString("status");
			String reason = rs.getString("reason");
			byte[] receipt = rs.getBytes("receipt");

			request = new ReimbursementRequest(requestId, requestorId, amountRequested, status, reason, receipt);
			pendingRequests.add(request);
		}

		return pendingRequests;
	}

	@Override
	public void updateStatusOfApplication(int[] applicationNumber, int[] decision, Connection con) throws SQLException {

		con.setAutoCommit(false);
		String sql;
		for (int i = 0; i < applicationNumber.length; i++) {
			sql = "UPDATE ers_database.reimbursment_requests SET status = ? WHERE request_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			String resolution = "";
			if (decision[i] == -1) {
				continue;
			} else if (decision[i] == 0) {
				resolution = "Rejected";
			} else if (decision[i] == 1) {
				resolution = "Accepted";
			}

			pstmt.setString(1, resolution);
			pstmt.setInt(2, applicationNumber[i]);

			int count = pstmt.executeUpdate();

			if (count != 1) {
				return;
			}
		}
		con.commit();
	}

	@Override
	public ArrayList<ReimbursementRequest> getPendingRequests(Connection con) throws SQLException {
		ArrayList<ReimbursementRequest> pendingRequests = new ArrayList<>();
		ReimbursementRequest request;
		String sql = "SELECT * FROM ers_database.reimbursment_requests WHERE status = 'Pending';";

		PreparedStatement pstmt = con.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int requestId = rs.getInt("request_id");
			int requestorId = rs.getInt("requestor_id");
			String amountRequested = rs.getString("amount_requested");
			String status = rs.getString("status");
			String reason = rs.getString("reason");
			byte[] receipt = rs.getBytes("receipt");

			request = new ReimbursementRequest(requestId, requestorId, amountRequested, status, reason, receipt);
			pendingRequests.add(request);
		}

		return pendingRequests;
	}
}
