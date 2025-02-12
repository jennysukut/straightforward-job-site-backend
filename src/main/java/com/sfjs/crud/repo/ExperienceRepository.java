package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.ExperienceEntity;

@Repository
@Transactional
public interface ExperienceRepository extends BaseRepository<ExperienceEntity> {

}
