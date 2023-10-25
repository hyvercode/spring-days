package com.hyvercode.springday.service;

import com.hyvercode.springday.component.TokenProvider;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.constant.ErrorConstant;
import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.model.request.LoginRequest;
import com.hyvercode.springday.model.response.AuthResponse;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AuthService {

  private final TokenProvider jwtTokenUtil;
  private final UserRepository userRepository;

  private final CaptchaValidator captchaValidator;

  public AuthService(TokenProvider jwtTokenUtil,
                     UserRepository userRepository,
                     CaptchaValidator captchaValidator) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.userRepository = userRepository;
    this.captchaValidator = captchaValidator;
  }

  public BaseResponse login(LoginRequest request) {
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

  public BaseResponse loginCaptcha(LoginRequest request) {

    boolean isValidCaptcha = captchaValidator.validateCaptcha(request.getCaptchaResponse());
    if (!isValidCaptcha) {
      throw new BusinessException(HttpStatus.FORBIDDEN, ErrorConstant.ERROR_CODE_02, ErrorConstant.ERROR_MESSAGE_02);
    }

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
}
