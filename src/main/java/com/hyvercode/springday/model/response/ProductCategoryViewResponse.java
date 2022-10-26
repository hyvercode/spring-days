package com.hyvercode.springday.model.response;

import com.hyvercode.springday.helpers.base.BaseResponse;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryViewResponse extends BaseResponse {
  private String productId;
  private String sku;
  private String productName;
  private BigDecimal price;
  private String productCategoryId;
  private String productCategoryName;
  private Boolean isActive;
}
