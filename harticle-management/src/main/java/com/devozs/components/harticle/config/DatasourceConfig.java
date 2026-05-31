package com.devozs.components.harticle.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class DatasourceConfig {

    private final long idleTimeout;
    private final int maxPoolSize;
    private final int minIdle;
    private final String userName;

    @Autowired
    public DatasourceConfig(@Value("${spring.datasource.hikari.maximum-pool-size}") final int maxPoolSize,
                            @Value("${spring.datasource.hikari.idle-timeout}") final long idleTimeout,
                            @Value("${spring.datasource.hikari.minimum-idle}") final int minIdle,
                            @Value("${spring.datasource.username}") String userName) {
        this.idleTimeout = idleTimeout;
        this.maxPoolSize = maxPoolSize;
        this.minIdle = minIdle;
        this.userName = userName;
    }
}
