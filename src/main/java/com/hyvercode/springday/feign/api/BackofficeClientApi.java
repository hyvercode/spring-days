package com.hyvercode.springday.feign.api;

import com.hyvercode.springday.helpers.base.BaseResponseBuilder;
import com.hyvercode.springday.model.response.MenuResponse;
import feign.RequestLine;

import java.util.List;

public interface BackofficeClientApi {
  @RequestLine(value = "GET /v1/menu")
  BaseResponseBuilder<List<MenuResponse>> getMenus();
}
