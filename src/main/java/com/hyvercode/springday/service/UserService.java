package com.hyvercode.springday.service;

import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.model.request.UserRequest;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public EmptyResponse create(UserRequest request) {
    User user = User.builder()
      .username(request.getUsername())
      .password(bCryptPasswordEncoder.encode(request.getPassword()))
      .name(request.getName())
      .email(request.getEmail())
      .phoneNumber(request.getPhoneNumber())
      .deviceId(request.getDeviceId())
      .isActive(request.getIsActive())
      .build();
    userRepository.save(user);

    return new EmptyResponse();
  }
}
