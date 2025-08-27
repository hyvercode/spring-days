package com.hyvercode.springday.model.response.product;

import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.dto.ProductCategoryDto;
import com.hyvercode.springday.model.dto.ProductInventoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private String productId;
    private String sku;
    private String productName;
    private BigDecimal price;
    private ProductCategoryDto productCategory;
    private ProductInventoryDto productInventory;
    private Boolean isActive;
}
