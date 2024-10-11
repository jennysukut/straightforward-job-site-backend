package com.sfjs.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class NumericMetricRequest extends BaseRequest {

  @Getter
  @Setter
  private BigDecimal metric;
}
