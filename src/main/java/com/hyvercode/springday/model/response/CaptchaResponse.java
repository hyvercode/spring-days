package com.hyvercode.springday.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse extends BaseResponse {
  private Boolean success;
  private Date timestamp;
  private String hostname;
  @JsonProperty("error-codes")
  private List<String> errorCodes;
}
