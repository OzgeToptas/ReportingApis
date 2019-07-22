package com.ozge.model.error;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.ozge.util.ErrorUtils;

public class ErrorResponse {

	private List<BaseError> errors;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(List<BaseError> errors) {
		super();
		this.errors = errors;
	}

	public static ErrorResponse create() {
		return ErrorResponse.create(new ApiError());
	}

	public static ErrorResponse create(BaseError error) {
		return new ErrorResponse(Collections.singletonList(error));
	}

	public static ErrorResponse create(List<BaseError> error) {
		return new ErrorResponse(error);
	}

	public static ErrorResponse create(BindingResult bindingResult) {
		return new ErrorResponse(ErrorUtils.getBindingResultErrors(bindingResult));
	}

	public List<BaseError> getErrors() {
		return errors;
	}

}
