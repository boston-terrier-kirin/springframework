package com.example.spring_batch.model;

public class Response {
	private long processId;
	private String jobName;
	private String message;

	public Response(long processId, String jobName, String message) {
		this.processId = processId;
		this.jobName = jobName;
		this.message = message;
	}

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
