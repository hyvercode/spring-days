package com.hyvercode.springday.service;

import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.request.LoginRequest;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

  private final UserRepository  userRepository;

  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public BaseResponse login(LoginRequest request){
    return new BaseResponse();
  }
}
