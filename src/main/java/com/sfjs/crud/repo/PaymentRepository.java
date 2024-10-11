package com.sfjs.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.PaymentEntity;

@Repository
@Transactional
public interface PaymentRepository extends BaseRepository<PaymentEntity> {
}
