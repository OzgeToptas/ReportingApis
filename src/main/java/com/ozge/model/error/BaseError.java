package com.ozge.model.error;

public class BaseError {

	private String message;

	public BaseError() {
		super();
	}

	public BaseError(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
