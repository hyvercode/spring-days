package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest  extends BaseRequest {

  private String username;

  private String password;

  private String name;

  private String email;

  private String phoneNumber;

  private String deviceId;

  private Boolean isActive;

  private String roleId;

}
