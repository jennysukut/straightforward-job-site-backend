package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.JobListingEntity;

@Repository
@Transactional
public interface JobListingRepository extends BaseRepository<JobListingEntity> {
}
