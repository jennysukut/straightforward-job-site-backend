package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.AwardEntity;

@Repository
@Transactional
public interface AwardRepository extends BaseRepository<AwardEntity> {

}
