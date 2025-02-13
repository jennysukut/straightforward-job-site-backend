package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.InterviewProcessEntity;

@Repository
@Transactional
public interface InterviewProcessRepository extends BaseRepository<InterviewProcessEntity> {

}
