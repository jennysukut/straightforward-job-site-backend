package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.NumericMetricEntity;

@Repository
@Transactional
public interface NumericMetricRepository extends BaseRepository<NumericMetricEntity> {
}
