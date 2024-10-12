package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.FellowEntity;

@Repository
@Transactional
public interface FellowRepository extends BaseRepository<FellowEntity> {
}
