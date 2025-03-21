package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.NumericMetricEntity;

@Repository
@Transactional
public interface NumericMetricRepository extends BaseRepository<NumericMetricEntity> {

  NumericMetricEntity findByName(String name);
}
