package com.hyvercode.springday.service;

import com.hyvercode.springday.component.JWTTokenProvider;
import com.hyvercode.springday.component.TokenProvider;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.ErrorConstant;
import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.model.entity.UserPrincipal;
import com.hyvercode.springday.model.request.LoginRequest;
import com.hyvercode.springday.model.response.AuthResponse;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService{

  private final TokenProvider jwtTokenUtil;
  private final UserRepository userRepository;

  private AuthenticationManager authenticationManager;
  private JWTTokenProvider jwtTokenProvider;

  public AuthService(TokenProvider jwtTokenUtil, UserRepository userRepository, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public BaseResponse login2(LoginRequest request) {
    User users = userRepository
      .findByUsername(request.getUsername().toLowerCase())
      .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01));

    if (!BCrypt.checkpw(request.getPassword(), users.getPassword())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    return AuthResponse.builder()
      .token(jwtTokenUtil.generateToken(users))
      .build();
  }

  public BaseResponse login(LoginRequest request) {
    User users = userRepository
      .findByUsername(request.getUsername().toLowerCase())
      .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01));

    authenticate(request.getUsername(), request.getPassword());
    UserPrincipal userPrincipal = new UserPrincipal(users);
    return AuthResponse.builder()
      .token(jwtTokenProvider.generateJwtToken(userPrincipal))
      .build();
  }

  private void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}
