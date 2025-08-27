package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.request.UserRequest;
import com.hyvercode.springday.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public EmptyResponse postCreateUser(@RequestBody @Validated UserRequest request) {
    return userService.create(request);
  }
}
