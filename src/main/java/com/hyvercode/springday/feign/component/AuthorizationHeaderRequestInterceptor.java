package com.hyvercode.springday.feign.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorizationHeaderRequestInterceptor implements RequestInterceptor {
  public static final String AUTHORIZATION_HEADER_KEY = "Authorization";

  @Override
  public void apply(RequestTemplate requestTemplate) {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      return;
    }
    HttpServletRequest request = requestAttributes.getRequest();
    String authorization = request.getHeader(AUTHORIZATION_HEADER_KEY);

    if (authorization == null) {
      return;
    }
    requestTemplate.header(AUTHORIZATION_HEADER_KEY,authorization);
  }
}
