package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.InterviewProcessEntity;

@Repository
@Transactional
public interface InterviewProcessRepository extends BaseRepository<InterviewProcessEntity> {

}
