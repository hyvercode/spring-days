package com.hyvercode.springday.model.projection;

import java.math.BigDecimal;

public interface ProductCategoryView {
  String getProductId();
  String getSku();
  String getProductName();
  BigDecimal getPrice();
  String getProductCategoryId();
  String getProductCategoryName();
  Boolean getIsActive();
}
