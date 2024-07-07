package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.response.RoleResponse;
import com.hyvercode.springday.service.FeignService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feigns")
public class FeignController {

  private final FeignService feignService;

  public FeignController(FeignService feignService) {
    this.feignService = feignService;
  }

  @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<RoleResponse> roleResponse(){
    return feignService.get();
  }

  @GetMapping(value = "/roles/{role-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BaseResponse roleResponse(@PathVariable("role-id") String roleId){
    return feignService.get(roleId);
  }
}
