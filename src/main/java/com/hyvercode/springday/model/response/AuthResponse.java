package com.hyvercode.springday.model.response;

import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse extends BaseResponse {

  private String token;
}
