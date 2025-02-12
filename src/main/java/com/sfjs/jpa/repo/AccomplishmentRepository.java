package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.AccomplishmentEntity;

@Repository
@Transactional
public interface AccomplishmentRepository extends BaseRepository<AccomplishmentEntity> {

}
