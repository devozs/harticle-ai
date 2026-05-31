package com.devozs.components.harticle.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ArticleType {
    MAIN("main"),
    SECONDARY("secondary");

    @Getter private String value;
}
