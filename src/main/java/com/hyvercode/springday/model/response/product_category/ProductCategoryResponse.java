package com.hyvercode.springday.model.response.product_category;

import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryResponse extends BaseResponse {
  private String productCategoryId;
  private String productCategoryName;
  private String description;
  private Boolean isActive;
}
