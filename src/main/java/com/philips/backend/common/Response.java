package com.philips.backend.common;

public class Response {
	private String message; 
	private int status;
	
	
	
	/**
	 * 
	 */
	public Response() {
		super();
	}
	/**
	 * @param message
	 * @param status
	 */
	public Response(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [message=" + message + ", status=" + status + "]";
	} 

}
