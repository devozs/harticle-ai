package com.devozs.components.harticle.api.identityresolver.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Intercepts requests to Identity and adds common headers to the request
 */
@Component
public class IdentityResolverInterceptor implements ClientHttpRequestInterceptor {
  @Override
  @NonNull
  public ClientHttpResponse intercept(@NonNull HttpRequest request,
                                      @NonNull byte[] body,
                                      @NonNull ClientHttpRequestExecution execution)
    throws IOException {

    return execution.execute(request, body);
  }
}
