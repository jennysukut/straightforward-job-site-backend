package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.BusinessEntity;

@Repository
@Transactional
public interface BusinessRepository extends BaseRepository<BusinessEntity> {
}
