package com.hyvercode.springday.service;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.ErrorConstant;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.model.entity.UserPrincipal;
import com.hyvercode.springday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User users = userRepository
      .findByUsername(username.toLowerCase())
      .orElseThrow(() -> new BusinessException(HttpStatus.CONFLICT, ErrorConstant.ERROR_CODE_01, ErrorConstant.ERROR_MESSAGE_01));
    UserPrincipal userPrincipal = new UserPrincipal(users);
    return userPrincipal;
  }
}
