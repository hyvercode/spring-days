package com.hyvercode.springday.model.response.product;

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
public class PageProductResponse extends BasePaginationResponse {
  private List<ProductResponse> data;
  private Pagination pagination;
}
