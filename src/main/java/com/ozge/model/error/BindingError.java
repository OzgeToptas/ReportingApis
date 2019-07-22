package com.ozge.model.error;

public class BindingError extends BaseError {

	private String field;

	public BindingError() {
		super();
	}

	public BindingError(String message) {
		super(message);
	}

	public BindingError(String field, String message) {
		super(message);
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
