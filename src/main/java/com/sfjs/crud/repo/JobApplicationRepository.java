package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.JobApplicationEntity;

@Repository
@Transactional
public interface JobApplicationRepository extends BaseRepository<JobApplicationEntity> {
}
