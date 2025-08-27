package com.hyvercode.springday.service;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.constant.ErrorConstant;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.entity.Role;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.model.request.UserRequest;
import com.hyvercode.springday.repository.RoleRepository;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final RoleRepository roleRepository;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.roleRepository = roleRepository;
  }

  public EmptyResponse create(UserRequest request) {

    Role role = roleRepository.findByRoleId(request.getRoleId());
    if (role==null) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", request.getRoleId());
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    Set<Role> roleSet = new HashSet<>();
    roleSet.add(role);
    
    User user = User.builder()
      .username(request.getUsername())
      .password(bCryptPasswordEncoder.encode(request.getPassword()))
      .name(request.getName())
      .email(request.getEmail())
      .phoneNumber(request.getPhoneNumber())
      .deviceId(request.getDeviceId())
      .roles(roleSet)
      .isActive(request.getIsActive())
      .build();
    userRepository.save(user);

    return new EmptyResponse();
  }
}
