package com.devozs.components.harticle.scraper.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.time.Duration;

/**
 * Dedicated HTTP client for outbound scraping of external news sites.
 *
 * <p>This laptop sits behind the Intel/Fortinet corporate proxy
 * ({@code HTTPS_PROXY=http://proxy-dmz.intel.com:912}). Browsers and curl honor
 * that env var automatically; Java's {@link RestTemplate} does NOT — it only
 * reads JVM system properties — so a direct connection to e.g. sport5.co.il
 * times out. This bean routes through the env proxy when one is set.
 *
 * <p>It is a SEPARATE, {@code @Qualifier("scraperRestTemplate")} bean so the
 * existing default {@code RestTemplate} (which calls the engine on localhost —
 * in NO_PROXY and must stay direct) is left untouched.
 */
@Configuration
@Slf4j
public class ScraperHttpConfig {

    public static final String SCRAPER_REST_TEMPLATE = "scraperRestTemplate";

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(30);
    // Some sites reject the default Java user agent; present a browser-like one.
    private static final String USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/124.0.0.0 Safari/537.36";

    @Bean(SCRAPER_REST_TEMPLATE)
    public RestTemplate scraperRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) CONNECT_TIMEOUT.toMillis());
        factory.setReadTimeout((int) READ_TIMEOUT.toMillis());

        Proxy proxy = resolveEnvProxy();
        if (proxy != null) {
            factory.setProxy(proxy);
            log.info("scraper HTTP client using proxy {}", proxy.address());
        } else {
            log.info("scraper HTTP client using direct connection (no proxy env var set)");
        }

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("User-Agent", USER_AGENT);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    /** Parse HTTPS_PROXY / HTTP_PROXY (either case) into a {@link Proxy}, or null if unset. */
    private Proxy resolveEnvProxy() {
        String raw = firstNonBlank(
                System.getenv("HTTPS_PROXY"), System.getenv("https_proxy"),
                System.getenv("HTTP_PROXY"), System.getenv("http_proxy"));
        if (raw == null) {
            return null;
        }
        try {
            // Accept "host:port" or a full "http://host:port" URL.
            URI uri = raw.contains("://") ? URI.create(raw) : URI.create("http://" + raw);
            String host = uri.getHost();
            int port = uri.getPort() != -1 ? uri.getPort() : 80;
            if (host == null) {
                log.warn("could not parse proxy from '{}', using direct connection", raw);
                return null;
            }
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        } catch (Exception e) {
            log.warn("invalid proxy value '{}': {} — using direct connection", raw, e.getMessage());
            return null;
        }
    }

    private String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v.trim();
            }
        }
        return null;
    }
}
