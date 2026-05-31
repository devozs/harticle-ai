package com.devozs.components.harticle.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Reporter {
    DORON_BEN_DOR("Doron Ben Dor"),
    RAZ_AMIR("Raz Amir");

    @Getter private String value;
}
