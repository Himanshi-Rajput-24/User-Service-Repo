package com.example.user.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, Object>> handleServiceException(ServiceException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "SERVICE_ERROR");
        response.put("message", "Error occurred while saving user: " + ex.getMessage());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(CustomExceptions.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomExceptions ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ex.getErrorType().name());
        response.put("message", ex.getErrorType().getMessage());
        response.put("status", ex.getErrorType().getHttpStatus().value());
        return ResponseEntity.status(ex.getErrorType().getHttpStatus()).body(response);
    }
}
