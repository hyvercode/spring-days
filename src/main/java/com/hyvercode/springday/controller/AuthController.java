package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.request.LoginRequest;
import com.hyvercode.springday.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
  public BaseResponse login(@RequestBody @Valid LoginRequest request){
    return authService.login(request);
  }

  @PostMapping(value = "/captcha",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
  public BaseResponse loginCaptcha(@RequestBody @Valid LoginRequest request){
    return authService.loginCaptcha(request);
  }
}
