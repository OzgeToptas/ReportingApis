package com.ozge.util;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import com.ozge.model.error.BaseError;
import com.ozge.model.error.BindingError;

public final class ErrorUtils {

	public ErrorUtils() {
		throw new IllegalAccessError("Final Utility Class");
	}

	public static List<BaseError> getBindingResultErrors(BindingResult bindingResult) {

		return bindingResult.getFieldErrors().stream()
				.map(error -> new BindingError(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

	}

}
