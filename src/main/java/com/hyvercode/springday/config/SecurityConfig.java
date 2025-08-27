package com.hyvercode.springday.config;

import com.hyvercode.springday.auth.AuthenticationFilter;
import com.hyvercode.springday.auth.SecurityContextService;
import com.hyvercode.springday.helpers.constant.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final SecurityContextService authenticationService;

  public SecurityConfig(SecurityContextService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
      .disable()
      .cors()
      .and()
      // make sure we use stateless session; session won't be used to store user's
      // state.
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      // handle an authorized attempts (if we happen to have any other custom
      // response)
      .exceptionHandling()
      .authenticationEntryPoint(
        (request, response, authError) ->
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
      )
      .and()
      // Add a filter to validate the tokens with every request (service will take
      // care of that)
      .addFilterBefore(
        // This is where we hook our auth filter
        new AuthenticationFilter(authenticationService),
        UsernamePasswordAuthenticationFilter.class
      )

      // Authorization requests config
      .authorizeRequests()
      //Enabled login
      .antMatchers("/","/login/**", "/register/**","/public/storage/**","/h2-console/**")
      .permitAll()

      //enable camunda
      .antMatchers("/camunda/**", "/engine-rest/**","/actuator/**")
      .permitAll()

      // Enabled swagger end points
      .antMatchers("/api-docs", "/api-docs/**", "/configuration/**", "/swagger*/**", "/webjars/**")
      .permitAll()

      // Allow OPTIONS call for preflight requests
      .antMatchers(HttpMethod.OPTIONS)
      .permitAll()

      // Allow internal endpoints for service users only !
      .antMatchers("/internal/", "/internal/**")
      .hasAuthority(SecurityConstants.AuthenticationClaim.SYSTEM_SERVICE.toString())

      // Only registration claim should be able to register
      .antMatchers(HttpMethod.POST, "/")
      .hasAuthority(SecurityConstants.AuthenticationClaim.REGISTRATION.toString())

      // All others should have LOGIN claim
      .antMatchers("/**")
      .authenticated();

    return http.build();
  }
}
