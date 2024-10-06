package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.NumericMetricEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.NumericMetricRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NumericMetricPersist extends BasePersist<NumericMetricEntity> {

  @Autowired
  NumericMetricRepository repository;

  @Override
  public BaseRepository<NumericMetricEntity> getBaseRepository() {
    return this.repository;
  }
}
