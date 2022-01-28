package com.abis.app.todoApplication.exception;

import java.util.Date;

public class ExceptionDetails {

	private Date timestamp;
	private String errorMessage;
	private String path;
	private String errorDetails;
	
	
	
	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ExceptionDetails(Date timestamp, String errorMessage, String path, String errorDetails) {
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
		this.path = path;
		this.errorDetails = errorDetails;
	}



}
