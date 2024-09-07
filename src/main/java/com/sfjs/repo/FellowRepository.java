package com.sfjs.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.FellowEntity;

@Repository
@Transactional
public interface FellowRepository extends BaseRepository<FellowEntity> {
}
