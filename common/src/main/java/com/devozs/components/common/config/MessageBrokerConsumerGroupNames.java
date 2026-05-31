package com.devozs.components.common.config;

import lombok.Getter;

@Getter
public class MessageBrokerConsumerGroupNames {
    private MessageBrokerConsumerGroupNames() {}

    public static final String MANAGEMENT_CONSUMER_GROUP = "management_consumer_group";
}
