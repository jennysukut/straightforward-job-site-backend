package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.JobApplicationNoteEntity;

@Repository
@Transactional
public interface JobApplicationNoteRepository extends BaseRepository<JobApplicationNoteEntity> {
}
