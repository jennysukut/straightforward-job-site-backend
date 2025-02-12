package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.ExtendedProfileEntity;

@Repository
@Transactional
public interface ProfileRepository extends BaseRepository<ExtendedProfileEntity> {

}
