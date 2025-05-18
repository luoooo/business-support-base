package com.company.business.support.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final Object data;

    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    public BusinessException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
} 