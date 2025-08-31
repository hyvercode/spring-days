package com.hyvercode.springday.config;

import com.hyvercode.springday.auth.AuthenticationFilter;
import com.hyvercode.springday.auth.SecurityContextService;
import com.hyvercode.springday.helpers.constant.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final SecurityContextService authenticationService;

  public SecurityConfig(SecurityContextService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Camunda Security Chain
  @Bean
  @Order(1)
  public SecurityFilterChain camundaSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .requestMatchers("/camunda/**", "/engine-rest/**", "/actuator/**", "/api-docs", "/api-docs/**", "/configuration/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/h2-console/**").permitAll()
          .anyRequest().authenticated()
      );

    return http.build();
  }

  // Main Application Security Chain
  @Bean
  @Order(2)
  public SecurityFilterChain applicationSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .cors(cors -> {})
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .exceptionHandling(exceptionHandling ->
        exceptionHandling.authenticationEntryPoint(
          (request, response, authError) ->
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        )
      )
      .addFilterBefore(
        new AuthenticationFilter(authenticationService),
        UsernamePasswordAuthenticationFilter.class
      )
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .requestMatchers("/", "/login/**", "/register/**", "/public/storage/**").permitAll()
          .requestMatchers(HttpMethod.OPTIONS).permitAll()
          .requestMatchers("/internal/", "/internal/**").hasAuthority(SecurityConstants.AuthenticationClaim.SYSTEM_SERVICE.toString())
          .requestMatchers(HttpMethod.POST, "/").hasAuthority(SecurityConstants.AuthenticationClaim.REGISTRATION.toString())
          .anyRequest().authenticated()
      );

    return http.build();
  }
}
