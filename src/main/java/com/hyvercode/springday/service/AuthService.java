package com.hyvercode.springday.service;

import com.hyvercode.springday.component.TokenProvider;
import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.request.LoginRequest;
import com.hyvercode.springday.model.response.AuthResponse;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;

  private final TokenProvider jwtTokenUtil;
  private final UserRepository userRepository;

  public AuthService(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userRepository = userRepository;
  }

  public BaseResponse login(LoginRequest request) {

    final Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtTokenUtil.generateToken(authentication);

    return AuthResponse.builder()
      .token(token)
      .build();
  }
}
