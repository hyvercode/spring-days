package com.hyvercode.springday.service;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.constant.ErrorConstant;
import com.hyvercode.springday.model.entity.Role;
import com.hyvercode.springday.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role findByName(String name) {
    Optional<Role> role = roleRepository.findRoleByName(name);
    if (role.isEmpty()) {
      log.info(ErrorConstant.ERROR_MESSAGE_01 + "{}", name);
      throw new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01);
    }
    return role.get();
  }
}
