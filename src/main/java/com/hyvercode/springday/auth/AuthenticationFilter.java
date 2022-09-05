package com.hyvercode.springday.auth;

import com.hyvercode.springday.component.JWTTokenProvider;
import com.hyvercode.springday.helpers.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {


  private SecurityContextService authenticationService;

  @Autowired
  private JWTTokenProvider jwtTokenProvider;

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
      SecurityContextHolder.getContext()
        .setAuthentication(authenticationService.decodeServiceApiSecret(apiSecretHeader));
    }

    filterChain.doFilter(request, response);
  }
}
