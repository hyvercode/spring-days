package com.hyvercode.springday.feign.config;

import com.hyvercode.springday.feign.component.AuthorizationHeaderRequestInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignClientConfig {

  private final AuthorizationHeaderRequestInterceptor authorizationHeaderRequestInterceptor;

  public FeignClientConfig(AuthorizationHeaderRequestInterceptor authorizationHeaderRequestInterceptor) {
    this.authorizationHeaderRequestInterceptor = authorizationHeaderRequestInterceptor;
  }

  /**
   * Enable this bean if you want to add headers in HTTP request
   */
  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Content-Type", "application/json");
      requestTemplate.header("Accept", "application/json");
    };
  }

  @Bean
  public AuthorizationHeaderRequestInterceptor requestInterceptor(RequestTemplate requestTemplate) {
    return authorizationHeaderRequestInterceptor;
  }
}
