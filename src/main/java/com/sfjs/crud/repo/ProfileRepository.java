package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.ExtendedProfileEntity;

@Repository
@Transactional
public interface ProfileRepository extends BaseRepository<ExtendedProfileEntity> {

}
