package com.devozs.components.common.dto;

import lombok.Getter;

@Getter
public enum KubeJobStatusDto {
    ACTIVE("active"),
    SUCCEEDED("succeeded"),
    FAILED("failed");

    private final String value;

    KubeJobStatusDto(String value) {
        this.value = value;
    }
}
