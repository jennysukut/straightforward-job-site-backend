package com.sfjs.data.core;

import java.math.BigDecimal;

import com.sfjs.data.NamedBaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class NumericMetric extends NamedBaseObject {

  @Getter
  @Setter
  private BigDecimal metric;
}
