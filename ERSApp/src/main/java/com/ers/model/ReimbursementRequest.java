package com.ers.model;

import java.util.Arrays;

public class ReimbursementRequest {
	
	private int requestId;
	private int requestorId;
	private String amountRequested;
	private String status;
	private String reason;
	private byte[] receipt;
	
	public ReimbursementRequest() {
		this.status = "not created";
	}
	
	public ReimbursementRequest(int requestId, int requestorId, String amountRequested, String status, String reason,
			byte[] receipt) {
		super();
		this.requestId = requestId;
		this.requestorId = requestorId;
		this.amountRequested = amountRequested;
		this.status = status;
		this.reason = reason;
		this.receipt = receipt;	
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(int requestorId) {
		this.requestorId = requestorId;
	}

	public String getAmountRequested() {
		return amountRequested;
	}

	public void setAmountRequested(String amountRequested) {
		this.amountRequested = amountRequested;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public byte[] getReceipt() {
		if(this.receipt==null) {
			return new byte[0];
		}
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountRequested == null) ? 0 : amountRequested.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + requestId;
		result = prime * result + requestorId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementRequest other = (ReimbursementRequest) obj;
		if (amountRequested == null) {
			if (other.amountRequested != null)
				return false;
		} else if (!amountRequested.equals(other.amountRequested))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (requestId != other.requestId)
			return false;
		if (requestorId != other.requestorId)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementRequest [requestId=" + requestId + ", requestorId=" + requestorId + ", amountRequested="
				+ amountRequested + ", status=" + status + ", reason=" + reason + ", receipt="
				+ Arrays.toString(receipt) + "]";
	}
	
	
}