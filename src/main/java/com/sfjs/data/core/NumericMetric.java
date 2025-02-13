package com.sfjs.data.core;

import java.math.BigDecimal;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class NumericMetric extends BaseObject {

  @Getter
  @Setter
  private BigDecimal metric;
}
