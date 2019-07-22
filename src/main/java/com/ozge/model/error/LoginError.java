package com.ozge.model.error;

public class LoginError extends BaseError {

    public LoginError() {
        super("Invalid Credentials!");
    }

    public LoginError(String message) {
        super(message);
    }

}
