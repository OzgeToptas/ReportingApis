package com.ozge.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.ozge.exception.NullCustomerInfoException;
import com.ozge.model.error.ApiError;
import com.ozge.model.error.AuthorizationError;
import com.ozge.model.error.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity handleHttpServerErrorException(HttpServerErrorException exp) {
        log.error("Api was called wrongly -> status : {} - body : {}", exp.getStatusText(), exp.getResponseBodyAsString());

        return new ResponseEntity<>(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity handleHttpClientErrorException(HttpClientErrorException exp) {
        log.error("Api was called wrongly -> status : {} - message : {}", exp.getStatusText(), exp.getMessage());

        return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Expired!")), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(Exception exp) {
        log.error("Api was called wrongly -> message : {}", exp.getMessage());

        return new ResponseEntity<>(ErrorResponse.create(new ApiError(exp.getCause().getMessage().split("\n")[0])), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullCustomerInfoException.class)
    public ResponseEntity handleNullCustomerInfoException(Exception exp) {
        log.error("Api was called wrongly -> message : {}", exp.getMessage());

        return new ResponseEntity<>(ErrorResponse.create(new ApiError(exp.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
