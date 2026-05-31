package com.devozs.components.common.dto;

import lombok.Getter;

@Getter
public enum ServiceStatus {
    NOT_STARTED("NOT_STARTED"),
    INITIATED("INITIATED"),
    FAILED("FAILED"),
    RUNNING("RUNNING");

    private final String value;

    ServiceStatus(String value) {
        this.value = value;
    }
}
