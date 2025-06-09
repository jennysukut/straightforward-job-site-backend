package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.SkillEntity;

@Repository
@Transactional
public interface SkillRepository extends BaseRepository<SkillEntity> {

  SkillEntity findByName(String name);
}
