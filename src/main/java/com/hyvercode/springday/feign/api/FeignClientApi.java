package com.hyvercode.springday.feign.api;

import com.hyvercode.springday.helpers.base.BaseResponseBuilder;
import com.hyvercode.springday.model.response.RoleResponse;
import feign.Param;
import feign.RequestLine;
import java.util.List;

public interface FeignClientApi {
  @RequestLine(value = "GET /v1/roles")
  BaseResponseBuilder<List<RoleResponse>> getRoles();

  @RequestLine(value = "GET /v1/roles/{role-id}")
  BaseResponseBuilder<RoleResponse> getRole(@Param("role-id") String roleId);
}
