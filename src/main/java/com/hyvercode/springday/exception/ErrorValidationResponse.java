package com.hyvercode.springday.exception;

import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ErrorValidationResponse extends BaseResponse {

  private String propertyName;
  private String errorMessage;
}
