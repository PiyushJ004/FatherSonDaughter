package com.example.demo.exception;

public class BaseExceptionResponse {
	private String response;

	public BaseExceptionResponse(String response) {
		super();
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
