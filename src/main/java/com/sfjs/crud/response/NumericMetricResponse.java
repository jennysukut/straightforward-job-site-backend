package com.sfjs.crud.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class NumericMetricResponse extends BaseResponse {

  @Getter
  @Setter
  private BigDecimal metric;
}
