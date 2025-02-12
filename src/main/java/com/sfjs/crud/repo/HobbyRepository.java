package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.HobbyEntity;

@Repository
@Transactional
public interface HobbyRepository extends BaseRepository<HobbyEntity> {

}
