package com.devozs.components.harticle.config;

import com.devozs.components.harticle.controller.ArticleBaseURLS;
import com.devozs.components.harticle.scraper.controller.ScraperURLS;
import com.devozs.components.harticle.training.controller.TrainingAgentURLS;
import com.devozs.components.harticle.training.controller.TrainingURLS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // Spring Boot forwards thrown exceptions to /error to render the
            // response. That forward re-enters this filter chain, so /error must
            // be permitted — otherwise EVERY error (401/404/500) is masked as an
            // anonymous-denied 403 with an empty body, hiding the real status.
            "/error",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/" + ArticleBaseURLS.URL,
            "/" + ArticleBaseURLS.URL + "/**",
            "/" + ScraperURLS.URL,
            ScraperURLS.URL_PATTERN,
            // Agent protocol authenticates by its own bearer token (see
            // TrainingAgentController); admin/FE endpoints sit behind the FE
            // passphrase gate. Both permitAll at the Spring layer, like the scraper.
            TrainingAgentURLS.URL_PATTERN,
            TrainingURLS.URL_PATTERN
    };

    public static final List<String> ORIGIN_PATTERNS = List.of(
            "http://localhost:*",
            "http://127.0.0.1:*",
            "chrome-extension://*",
            "https://devozs.com",
            "https://harticle.devozs.com"
    );

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(ORIGIN_PATTERNS);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
