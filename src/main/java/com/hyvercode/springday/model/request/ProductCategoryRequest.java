package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductCategoryRequest extends BaseRequest {

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 60, message = "Max 60 characters")
  private String productCategoryName;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 100, message = "Max 100 characters")
  private String description;
  private Boolean isActive;
}
