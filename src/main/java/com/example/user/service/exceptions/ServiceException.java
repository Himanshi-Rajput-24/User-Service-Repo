package com.example.user.service.exceptions;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message); // Pass the message to the parent class (RuntimeException)
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause); // You can pass a message and cause (another exception)
    }
}
