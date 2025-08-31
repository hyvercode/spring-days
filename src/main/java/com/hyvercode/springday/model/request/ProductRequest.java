package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest extends BaseRequest {
  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 36, message = "Max 26 characters")
  private String sku;

  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 60, message = "Max 60 characters")
  private String productName;

  private BigDecimal price;
  private BigInteger stock;
  @NotNull(message = "Not null")
  @NotBlank(message = "Not blank")
  @NotEmpty(message = "Not empty")
  @Size(max = 36, message = "Max 36 characters")
  private String productCategoryId;
  private Boolean isActive;
}
