package com.hyvercode.springday.feign;

import com.hyvercode.springday.helpers.base.BaseResponseBuilder;
import com.hyvercode.springday.model.response.RoleResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@org.springframework.cloud.openfeign.FeignClient(name = "bfi-treasury-auth-api",configuration = FeignClientConfig.class, url = "${client.post.baseUrl}")
public interface FeignClient {
  @RequestMapping("/v1/roles")
  BaseResponseBuilder<List<RoleResponse>> getRoles();
}
