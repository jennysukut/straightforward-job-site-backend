package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.EducationEntity;

@Repository
@Transactional
public interface EducationRepository extends BaseRepository<EducationEntity> {

}
