package com.hyvercode.springday.model.response;

import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse extends BaseResponse {
  private String roleId;
  private String roleName;
  private Boolean isActive;
}
