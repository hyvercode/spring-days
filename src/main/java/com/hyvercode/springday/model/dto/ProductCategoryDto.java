package com.hyvercode.springday.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryDto implements Serializable {
  private String productCategoryId;
  private String productCategoryName;
  private String description;
}
