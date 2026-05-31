package com.devozs.components.common.exception;

public class AsyncTaskIdNotExistException extends Exception {
    public AsyncTaskIdNotExistException() { super();}

    public AsyncTaskIdNotExistException(String message) {
        super(message);
    }

    public AsyncTaskIdNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
