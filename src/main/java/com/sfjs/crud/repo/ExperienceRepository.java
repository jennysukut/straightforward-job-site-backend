package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.ExperienceEntity;

@Repository
@Transactional
public interface ExperienceRepository extends BaseRepository<ExperienceEntity> {

}
