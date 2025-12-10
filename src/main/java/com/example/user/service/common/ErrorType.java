package com.example.user.service.common;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    USER_NOT_FOUND("User not found in the system.", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("Invalid username or password.", HttpStatus.UNAUTHORIZED),
    DATABASE_ERROR("Unable to save the user due to a database error.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ACCESS("You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
    RESOURCE_NOT_FOUND("Requested resource not found.", HttpStatus.NOT_FOUND),
    INVALID_EMAIL("Invalid email format.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELDS("Required fields are missing.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorType(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }


    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
