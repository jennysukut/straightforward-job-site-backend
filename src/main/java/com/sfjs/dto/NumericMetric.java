package com.sfjs.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class NumericMetric extends BaseBody {

  @Getter
  @Setter
  private BigDecimal metric;
}
