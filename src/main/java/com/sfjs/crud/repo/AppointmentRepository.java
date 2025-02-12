package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.InterviewAppointmentEntity;

@Repository
@Transactional
public interface AppointmentRepository extends BaseRepository<InterviewAppointmentEntity> {
}
