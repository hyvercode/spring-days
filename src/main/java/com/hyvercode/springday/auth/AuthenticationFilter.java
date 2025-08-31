package com.hyvercode.springday.auth;

import com.hyvercode.springday.helpers.constant.SecurityConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationFilter extends OncePerRequestFilter {


  private final SecurityContextService authenticationService;

  public AuthenticationFilter(SecurityContextService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authenticationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER_KEY);
    String apiSecretHeader = request.getHeader(SecurityConstants.API_SECRET_HEADER_KEY);

    if (authenticationHeader != null && authenticationHeader.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
      // User token
      SecurityContextHolder.getContext()
        .setAuthentication(
          authenticationService.decodeUserToken(
            // Remove header prefix of "Bearer"
            authenticationHeader.replace(SecurityConstants.BEARER_TOKEN_PREFIX, "").trim()
          )
        );
    } else if (apiSecretHeader != null) {
      // Service key
      SecurityContextHolder.getContext().setAuthentication(authenticationService.decodeServiceApiSecret(apiSecretHeader));
    }

    filterChain.doFilter(request, response);
  }
}
