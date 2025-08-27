package com.hyvercode.springday.service;

import com.hyvercode.springday.feign.api.FeignClientApi;
import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Service
public class FeignService {
  private final FeignClientApi feignClient;

  public FeignService(FeignClientApi feignClient) {
    this.feignClient = feignClient;
  }

  @RequestMapping
  public List<RoleResponse> get() {
    log.info("FEIGN " + feignClient.getRoles().toString());
    return feignClient.getRoles().getContent();
  }

  @RequestMapping
  public BaseResponse get(String roleId) {
    log.info("Get roles by id {} " + roleId);
    return feignClient.getRole(roleId).getContent();
  }
}
