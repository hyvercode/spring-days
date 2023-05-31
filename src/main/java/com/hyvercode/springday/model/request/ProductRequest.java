package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest extends BaseRequest {
    private String sku;
    private String productName;
    private BigDecimal price;
    private BigInteger stock;
    private String productCategoryId;
    private Boolean isActive;
}
