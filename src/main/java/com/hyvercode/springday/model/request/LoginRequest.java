package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest extends BaseRequest {

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 30, message = "Max 30 characters")
  private String username;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 30, message = "Max 30 characters")
  private String password;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  private String captchaResponse;
}
