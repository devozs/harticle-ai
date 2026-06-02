package com.devozs.components.harticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.devozs")
@EnableJpaRepositories(basePackages = "com.devozs")
@EnableAsync
@EnableScheduling
public class HarticleManagementApplication {

//    @Bean
//    public JwtUtils jwtUtils() { return new JwtUtils(); }

    public static void main(String[] args) {
        SpringApplication.run(HarticleManagementApplication.class, args);
    }

    // Default client for internal calls (e.g. the engine on localhost, which is
    // in NO_PROXY and must stay direct). @Primary so it remains the by-type
    // default now that the scraper adds a second, proxied RestTemplate bean.
    @Bean
    @Primary
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

