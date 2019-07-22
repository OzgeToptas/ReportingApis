package com.ozge.exception;

public class NullCustomerInfoException extends RuntimeException {

    public NullCustomerInfoException() {
        super("Customer Information cannot be null");
    }

}