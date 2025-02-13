package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.BusinessEntity;

@Repository
@Transactional
public interface BusinessRepository extends BaseRepository<BusinessEntity> {
}
