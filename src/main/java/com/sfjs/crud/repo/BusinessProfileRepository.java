package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.BusinessProfileEntity;

@Repository
@Transactional
public interface BusinessProfileRepository extends BaseRepository<BusinessProfileEntity> {

}
