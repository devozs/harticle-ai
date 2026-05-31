package com.devozs.components.common.exception;

import java.util.UUID;

public class DataKubeJobNotExistException extends Exception {
    public DataKubeJobNotExistException(UUID id) {
        super(String.format("DataKubeJobNotExistException is not exist: %s", id));
    }
}
