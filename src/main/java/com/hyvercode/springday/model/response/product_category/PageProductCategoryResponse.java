package com.hyvercode.springday.model.response.product_category;

import com.hyvercode.springday.helpers.base.BasePaginationResponse;
import com.hyvercode.springday.helpers.base.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageProductCategoryResponse extends BasePaginationResponse {
  private Set<ProductCategoryResponse> data;
  private Pagination pagination;
}
