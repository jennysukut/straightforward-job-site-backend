package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.NumericMetricRequest;
import com.sfjs.dto.response.NumericMetricResponse;
import com.sfjs.entity.NumericMetricEntity;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NumericMetricService extends BaseService<NumericMetricEntity, NumericMetricRequest, NumericMetricResponse> {

  @Autowired
  BaseRepository<NumericMetricEntity> repository;

  public NumericMetricService() {
    super(new BaseConverter<NumericMetricEntity, NumericMetricRequest, NumericMetricResponse>(NumericMetricEntity.class,
        NumericMetricResponse.class));
  }

  @Override
  public BaseRepository<NumericMetricEntity> getBaseRepository() {
    return this.repository;
  }
}
