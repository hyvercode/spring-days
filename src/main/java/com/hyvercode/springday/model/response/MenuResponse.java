package com.hyvercode.springday.model.response;

import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse extends BaseResponse {
  private String menuId;
  private String menuName;
  private Boolean isActive;
}
