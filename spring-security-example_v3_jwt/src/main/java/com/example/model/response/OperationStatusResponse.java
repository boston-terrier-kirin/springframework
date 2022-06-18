package com.example.model.response;

public class OperationStatusResponse {
	private String operationStatus;
	private String operationName;

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationResult) {
		this.operationStatus = operationResult;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}
