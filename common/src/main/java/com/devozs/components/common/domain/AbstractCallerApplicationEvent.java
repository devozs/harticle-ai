package com.devozs.components.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class AbstractCallerApplicationEvent extends ApplicationEvent {
    private String caller;

    public AbstractCallerApplicationEvent(Object source, String caller) {
        super(source);
        this.caller = caller;
    }
}
