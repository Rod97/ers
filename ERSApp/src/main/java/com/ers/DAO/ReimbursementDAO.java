package com.ers.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ers.model.ReimbursementRequest;

public interface ReimbursementDAO {
	public int submitReimbursementRequest(ArrayList<String> submission, Connection con)
			throws SQLException, FileNotFoundException, IOException; 
	public ArrayList<ReimbursementRequest> getPendingRequestsById(Integer id, Connection con) throws SQLException;

	public ArrayList<ReimbursementRequest> getResolvedRequestsById(Integer id, Connection con) throws SQLException;
	public ArrayList<ReimbursementRequest> getResolvedRequests(Connection con) throws SQLException;
	public void updateStatusOfApplication(int[] requestIds, int[] decisions, Connection con) throws SQLException;
	public ArrayList<ReimbursementRequest> getPendingRequests(Connection con) throws SQLException;
	
}
