package com.sfjs.data;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class NumericMetricData extends BaseData {

  @Getter
  @Setter
  private BigDecimal metric;
}
