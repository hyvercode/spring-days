package com.hyvercode.springday.model.response.product_category;

import com.hyvercode.springday.helpers.base.BasePaginationResponse;
import com.hyvercode.springday.helpers.base.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageProductCategoryResponse extends BasePaginationResponse {
  private List<ProductCategoryResponse> data;
  private Pagination pagination;
}
