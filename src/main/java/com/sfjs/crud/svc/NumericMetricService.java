package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.crud.entity.NumericMetricEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.response.NumericMetricResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NumericMetricService extends BaseService<NumericMetricEntity, NumericMetricResponse> {

  @Autowired
  BaseRepository<NumericMetricEntity> repository;

  public NumericMetricService() {
    super(new BaseConverter<NumericMetricEntity, NumericMetricResponse>(NumericMetricResponse.class));
  }

  @Override
  public BaseRepository<NumericMetricEntity> getBaseRepository() {
    return this.repository;
  }
}
