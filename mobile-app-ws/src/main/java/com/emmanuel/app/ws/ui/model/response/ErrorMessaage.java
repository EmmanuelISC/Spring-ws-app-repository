package com.emmanuel.app.ws.ui.model.response;

import java.util.Date;

public class ErrorMessaage {
	private Date timestamp;
	private String message;
	
	public ErrorMessaage() {	}

	public ErrorMessaage(Date timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}

	

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
