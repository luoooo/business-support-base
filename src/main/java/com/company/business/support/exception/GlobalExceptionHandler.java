package com.company.business.support.exception;

import com.company.business.support.config.BusinessSupportProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    private final BusinessSupportProperties properties;
    
    public GlobalExceptionHandler(BusinessSupportProperties properties) {
        this.properties = properties;
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException e) {
        log.error("Business error: {}", e.getMessage(), properties.getGlobalException().isPrintStackTrace() ? e : null);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", properties.getGlobalException().getErrorCodePrefix() + e.getCode());
        response.put("message", e.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException e) {
        log.error("Validation error: {}", e.getMessage(), properties.getGlobalException().isPrintStackTrace() ? e : null);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", "VALIDATION_ERROR");
        response.put("message", e.getMessage());
        response.put("errors", e.getErrors());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Validation error: {}", e.getMessage(), properties.getGlobalException().isPrintStackTrace() ? e : null);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", "VALIDATION_ERROR");
        response.put("message", "Validation failed");
        response.put("errors", e.getConstraintViolations());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), properties.getGlobalException().isPrintStackTrace() ? e : null);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", "INTERNAL_ERROR");
        response.put("message", "An unexpected error occurred");
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 