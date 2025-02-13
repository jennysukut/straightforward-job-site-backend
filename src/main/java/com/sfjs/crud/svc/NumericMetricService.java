package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.data.core.NumericMetric;
import com.sfjs.data.entity.NumericMetricEntity;
import com.sfjs.jpa.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NumericMetricService extends BaseService<NumericMetricEntity, NumericMetric> {

  @Autowired
  BaseRepository<NumericMetricEntity> repository;

  public NumericMetricService() {
    super(new BaseConverter<NumericMetricEntity, NumericMetric>(NumericMetric.class));
  }

  @Override
  public BaseRepository<NumericMetricEntity> getBaseRepository() {
    return this.repository;
  }
}
