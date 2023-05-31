package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import com.hyvercode.springday.helpers.constant.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest  extends BaseRequest {

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 30, message = "Max 30 characters")
  @Size(min= 4, message = "Min 4 characters")
  private String username;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 30, message = "Max 60 characters")
  @Pattern(regexp = AppConstants.PASS_PATTERN_CHARACTERS,
    message = "Password must contain min 6 to max 30 characters at least one digit, lower, upper case and one special character."
  )
  private String password;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 60, message = "Max 60 characters")
  private String name;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 30, message = "Max 30 characters")
  @Pattern(regexp = AppConstants.EMAIL_PATTERN, message = "Invalid email pattern")
  private String email;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 12, message = "Max 12 characters")
  private String phoneNumber;

  private String deviceId;

  private Boolean isActive;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  private String roleId;

}
