package com.example.user.service.exceptions;

import com.example.user.service.common.ErrorType;

public class CustomExceptions extends RuntimeException{
    private final ErrorType errorType;

    public CustomExceptions(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CustomExceptions(ErrorType errorType, Throwable cause) {
        super(errorType.getMessage(), cause);
        this.errorType = errorType;// You can pass a message and cause (another exception)
    }
    public ErrorType getErrorType() {
        return errorType;
    }
}
