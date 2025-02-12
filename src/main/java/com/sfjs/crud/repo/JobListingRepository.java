package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.JobListingEntity;

@Repository
@Transactional
public interface JobListingRepository extends BaseRepository<JobListingEntity> {
}
