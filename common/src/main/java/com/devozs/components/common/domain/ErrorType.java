package com.devozs.components.common.domain;

import lombok.Getter;

@Getter
public enum ErrorType {
    COMMUNICATION("communication error"),
    AUTHENTICATION("authentication error"),
    LACK_OF_RESOURCES("could not allocate the necessary resource"),
    INTERNAL("unknown internal error"),
    EVENT_TRACER("error caught by event tracer");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }
}
