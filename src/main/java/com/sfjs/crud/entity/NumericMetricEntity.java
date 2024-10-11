package com.sfjs.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "numeric_metric")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class NumericMetricEntity extends BaseEntity {

  @Getter
  @Setter
  private BigDecimal metric;
}
