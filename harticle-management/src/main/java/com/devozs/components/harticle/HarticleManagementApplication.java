package com.devozs.components.harticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.devozs")
@EnableJpaRepositories(basePackages = "com.devozs")
public class HarticleManagementApplication {

//    @Bean
//    public JwtUtils jwtUtils() { return new JwtUtils(); }

    public static void main(String[] args) {
        SpringApplication.run(HarticleManagementApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

