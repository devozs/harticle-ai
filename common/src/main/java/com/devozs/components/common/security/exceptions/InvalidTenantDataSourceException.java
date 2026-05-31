package com.devozs.components.common.security.exceptions;

public class InvalidTenantDataSourceException extends Exception {
    public InvalidTenantDataSourceException(String message) {
        super(message);
    }

    public InvalidTenantDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
