package com.hyvercode.springday.service;

import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.entity.Role;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.model.request.UserRequest;
import com.hyvercode.springday.repository.RoleRepository;
import com.hyvercode.springday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final RoleRepository roleRepository;


  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.roleRepository = roleRepository;
  }

  public EmptyResponse create(UserRequest request) {

    Role role = roleRepository.findByRoleId(request.getRoleId());
    Set<Role> roleSet = new HashSet<>();
    roleSet.add(role);

    User user = User.builder()
      .username(request.getUsername())
      .password(bCryptPasswordEncoder.encode(request.getPassword()))
      .name(request.getName())
      .email(request.getEmail())
      .phoneNumber(request.getPhoneNumber())
      .deviceId(request.getDeviceId())
      .isActive(request.getIsActive())
      .build();
    user.setRoles(roleSet);
    userRepository.save(user);

    return new EmptyResponse();
  }
}
