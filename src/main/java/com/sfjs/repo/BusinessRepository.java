package com.sfjs.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.BusinessEntity;

@Repository
@Transactional
public interface BusinessRepository extends BaseRepository<BusinessEntity> {
}
