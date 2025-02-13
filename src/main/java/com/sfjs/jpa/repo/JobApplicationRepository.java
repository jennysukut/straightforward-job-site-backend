package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.JobApplicationEntity;

@Repository
@Transactional
public interface JobApplicationRepository extends BaseRepository<JobApplicationEntity> {
}
