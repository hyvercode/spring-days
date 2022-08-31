package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductCategoryRequest extends BaseRequest {
  @NotNull
  private String productCategoryName;
  @NotNull
  private String description;
  private Boolean isActive;
}
