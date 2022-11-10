package com.hyvercode.springday.model.dto;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeDepositDto extends BaseRequest {
  private BigDecimal depositAmount;
  private Float depositInterestRate;
  private String periodeFrom;
  private String periodeTo;
  private String certificateNo;
  private BigDecimal interestIncome;
}
