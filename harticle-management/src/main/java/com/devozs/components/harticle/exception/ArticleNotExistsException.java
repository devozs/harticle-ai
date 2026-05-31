package com.devozs.components.harticle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "article not found")
public class ArticleNotExistsException extends Exception {
    public ArticleNotExistsException(UUID id) {
        super(String.format("article does not exist: %s", id));
    }
}