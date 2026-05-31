package com.devozs.components.common.domain;

import lombok.Getter;

@Getter
public enum TaskStatus {
    INITIALIZING("initializing"),
    IN_PROGRESS("in progress"),
    COMPLETED("completed"),
    ERROR("error"),
    DELETED("deleted");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }
}
