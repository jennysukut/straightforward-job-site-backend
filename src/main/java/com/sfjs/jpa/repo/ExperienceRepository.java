package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.ExperienceEntity;

@Repository
@Transactional
public interface ExperienceRepository extends BaseRepository<ExperienceEntity> {

}
