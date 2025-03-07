package com.sfjs.data.entity;

import com.sfjs.data.core.NumericMetric;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "numeric_metric")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class NumericMetricEntity extends NumericMetric {

}
