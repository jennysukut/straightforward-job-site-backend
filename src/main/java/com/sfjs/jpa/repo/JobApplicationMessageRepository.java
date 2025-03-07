package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.JobApplicationMessageEntity;

@Repository
@Transactional
public interface JobApplicationMessageRepository extends BaseRepository<JobApplicationMessageEntity> {
}
