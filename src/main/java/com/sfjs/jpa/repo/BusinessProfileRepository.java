package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.BusinessProfileEntity;

@Repository
@Transactional
public interface BusinessProfileRepository extends BaseRepository<BusinessProfileEntity> {

}
