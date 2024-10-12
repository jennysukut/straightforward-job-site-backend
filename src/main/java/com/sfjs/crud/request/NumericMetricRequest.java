package com.sfjs.crud.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class NumericMetricRequest extends BaseRequest {

  @Getter
  @Setter
  private BigDecimal metric;
}
