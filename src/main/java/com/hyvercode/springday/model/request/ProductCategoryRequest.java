package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


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
