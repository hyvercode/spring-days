package com.hyvercode.springday.service;

import com.hyvercode.springday.feign.FeignClient;
import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.helpers.base.BaseResponseBuilder;
import com.hyvercode.springday.model.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
public class FeignService {
  private final FeignClient feignClient;

  public FeignService(FeignClient feignClient) {
    this.feignClient = feignClient;
  }

  @RequestMapping
  public BaseResponse get() {
    log.info("FEIGN "+feignClient.getRoles().toString());
    return feignClient.getRoles();
  }
}
