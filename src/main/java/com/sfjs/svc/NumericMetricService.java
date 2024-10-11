package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.NumericMetric;
import com.sfjs.dto.response.NumericMetricResponse;
import com.sfjs.entity.NumericMetricEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.NumericMetricPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NumericMetricService extends BaseService<NumericMetricEntity, NumericMetric, NumericMetricResponse> {

  @Autowired
  NumericMetricPersist repository;

  public NumericMetricService() {
    super(new BaseConverter<NumericMetricEntity, NumericMetric, NumericMetricResponse>(NumericMetricEntity.class,
        NumericMetricResponse.class));
  }

  @Override
  public BasePersist<NumericMetricEntity> getBaseRepository() {
    return this.repository;
  }
}
