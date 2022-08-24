package com.hyvercode.springday.service;

import com.hyvercode.springday.model.entity.Role;
import com.hyvercode.springday.model.entity.User;
import com.hyvercode.springday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
  private final RoleService roleService;
  private final UserRepository userRepository;

  public UserService(RoleService roleService, UserRepository userRepository) {
    this.roleService = roleService;
    this.userRepository = userRepository;
  }

  @Autowired
  private BCryptPasswordEncoder bcryptEncoder;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }

    User user = userOptional.get();
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
  }

  private Set<SimpleGrantedAuthority> getAuthority(User user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    });
    return authorities;
  }

  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    userRepository.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  public User findOne(String username) {

    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }

    return userOptional.get();
  }

  public User save(User user) {

    User nUser = new User();
    nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

    Role role = roleService.findByName("USER");
    Set<Role> roleSet = new HashSet<>();
    roleSet.add(role);

    if (nUser.getEmail().split("@")[1].equals("admin")) {
      role = roleService.findByName("ADMIN");
      roleSet.add(role);
    }

    nUser.setRoles(roleSet);
    return userRepository.save(nUser);
  }
}
