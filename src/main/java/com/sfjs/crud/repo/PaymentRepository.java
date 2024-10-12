package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.crud.entity.PaymentEntity;

@Repository
@Transactional
public interface PaymentRepository extends BaseRepository<PaymentEntity> {
}
