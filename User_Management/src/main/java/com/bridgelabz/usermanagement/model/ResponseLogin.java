package com.bridgelabz.usermanagement.model;

public class ResponseLogin {
 
	private Integer statusCode;
	private String message;
	private Object data;
	private String token;
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ResponseLogin(Integer statusCode, String message, Object data, String token) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
		this.token = token;
	}
}
